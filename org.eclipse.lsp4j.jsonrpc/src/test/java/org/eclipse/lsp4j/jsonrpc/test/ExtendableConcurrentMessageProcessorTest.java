/******************************************************************************
 * Copyright (c) 2018 Red Hat Inc. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.MessageProducer;
import org.eclipse.lsp4j.jsonrpc.Launcher.Builder;
import org.eclipse.lsp4j.jsonrpc.json.ConcurrentMessageProcessor;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.test.ExtendableConcurrentMessageProcessorTest.MessageContextStore.MessageContext;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.junit.Assert;
import org.junit.Test;

/**
 * This is a test to verify that it is easy for a client to override the construction
 * of the ConcurrentMessageProcessor, so that an extender making use of 
 * lsp4j.jsonrpc might be able to use these bootstrapping classes
 * for different protocols that may need to identify which client is making
 * each and every request. 
 *
 */
public class ExtendableConcurrentMessageProcessorTest {
	
	private static final long TIMEOUT = 2000;

	/**
	 * Test that an adopter making use of these APIs is able to 
	 * identify which client is making any given request. 
	 */
	@Test
	public void testIdentifyClientRequest() throws Exception {
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();
		
		in.connect(out2);
		out.connect(in2);
		
		MyClient client = new MyClientImpl();
		Launcher<MyServer> clientSideLauncher = Launcher.createLauncher(client, MyServer.class, in, out);
		
		// create server side
		MyServer server = new MyServerImpl();
		MessageContextStore<MyClient> contextStore = new MessageContextStore<>();
		Launcher<MyClient> serverSideLauncher = createLauncher(createBuilder(contextStore), server, MyClient.class, in2, out2);
		
		TestContextWrapper.setMap(contextStore);
		
		clientSideLauncher.startListening();
		serverSideLauncher.startListening();
		
		CompletableFuture<MyParam> fooFuture = clientSideLauncher.getRemoteProxy().askServer(new MyParam("FOO"));
		CompletableFuture<MyParam> barFuture = serverSideLauncher.getRemoteProxy().askClient(new MyParam("BAR"));
		
		Assert.assertEquals("FOO", fooFuture.get(TIMEOUT, TimeUnit.MILLISECONDS).value);
		Assert.assertEquals("BAR", barFuture.get(TIMEOUT, TimeUnit.MILLISECONDS).value);
		Assert.assertFalse(TestContextWrapper.error);
	}
	
	/*
	 * Copy the createLauncher method, but pass in a custom builder
	 */
	static <T> Launcher<T> createLauncher(Builder<T> builder, Object localService, Class<T> remoteInterface, InputStream in, OutputStream out) {
		return builder.setLocalService(localService)
				.setRemoteInterface(remoteInterface)
				.setInput(in).setOutput(out)
				.create();
	}
	
	/*
	 * The custom builder to be used when creating a launcher
	 */
	static <T> Builder<T> createBuilder(MessageContextStore<T> store) {
		return new Builder<T>() {
			protected ConcurrentMessageProcessor createMessageProcessor(MessageProducer reader, 
					MessageConsumer messageConsumer, T remoteProxy) {
				return new CustomConcurrentMessageProcessor<T>(reader, messageConsumer, remoteProxy, store);
			}
		};
	}
	
	/*
	 * The custom message processor, which can make sure to persist which clients are 
	 * making a given request before propagating those requests to the server implementation. 
	 */
	public static class CustomConcurrentMessageProcessor<T> extends ConcurrentMessageProcessor {

		private T remoteProxy;
		private final MessageContextStore<T> threadMap;
		public CustomConcurrentMessageProcessor(MessageProducer reader, MessageConsumer messageConsumer,
				T remoteProxy, MessageContextStore<T> threadMap) {
			super(reader, messageConsumer);
			this.remoteProxy = remoteProxy;
			this.threadMap = threadMap;
		}

		protected void processingStarted() {
			super.processingStarted();
			if (threadMap != null) {
				threadMap.setContext(new MessageContext<T>(remoteProxy));
			}
		}

		protected void processingEnded() {
			super.processingEnded();
			if (threadMap != null)
				threadMap.clear();

		}
	}

	/*
	 * Server and client interfaces are below, along with any parameters required
	 */

	public static interface MyServer {
		@JsonRequest
		CompletableFuture<MyParam> askServer(MyParam param);
	}
	
	public static interface MyClient {
		@JsonRequest
		CompletableFuture<MyParam> askClient(MyParam param);
	}
	public static class MyParam {
		private MyParam nested;
		private Either<String, Integer> either;

		public MyParam() {}
		
		public MyParam(@NonNull String string) {
			this.value = string;
		}

		@NonNull
		private String value;
		
		@NonNull
		public String getValue() {
			return value;
		}
		
		public void setValue(@NonNull String value) {
			this.value = value;
		}
		
		public MyParam getNested() {
			return nested;
		}

		public void setNested(MyParam nested) {
			this.nested = nested;
		}

		public Either<String, Integer> getEither() {
			return either;
		}

		public void setEither(Either<String, Integer> either) {
			this.either = either;
		}
	}

	public static class MyServerImpl implements MyServer {
		@Override
		public CompletableFuture<MyParam> askServer(MyParam param) {
			MessageContext<MyClient> context = TestContextWrapper.store.getContext();
			MyClient client = context.getRemoteProxy();
			if( client == null )
				TestContextWrapper.setError(true);
			else
				TestContextWrapper.setError(false);
			return CompletableFuture.completedFuture(param);
		}
	};

	
	public static class MyClientImpl implements MyClient {
		@Override
		public CompletableFuture<MyParam> askClient(MyParam param) {
			return CompletableFuture.completedFuture(param);
		}
	};
	
	
	/*
	 * A custom class for storing the context for any given message
	 */
	public static class MessageContextStore<T> {
		private ThreadLocal<MessageContext<T>> messageContext = new ThreadLocal<>();

		public void setContext(MessageContext<T> context) {
			messageContext.set(context);
		}
		
		/**
		 * Get the context for the current request
		 * @return
		 */
		public MessageContext<T> getContext() {
			return messageContext.get();
		}
		
		/**
		 * Remove the context for this request. 
		 * Any new requests will need to set their context anew.
		 */
		public void clear() {
			messageContext.remove();
		}
		
		/**
		 * This object can be extended to include whatever other context
		 * from the raw message we may consider making available to implementations.
		 * At a minimum, it should make available the remote proxy, so a given
		 * request knows which remote proxy is making the request. 
		 */
		public static class MessageContext<T> {
			T remoteProxy;
			public MessageContext(T remoteProxy) {
				this.remoteProxy = remoteProxy;
			}
			
			public T getRemoteProxy() {
				return this.remoteProxy;
			}
		};
	}
	
	/*
	 * A class used to store the results of the test (success or failure)
	 */
	public static class TestContextWrapper {
		public static MessageContextStore<MyClient> store;
		public static boolean error = false;
		public static void setMap(MessageContextStore<MyClient> store2) {
			store= store2;
		}
		
		public static void setError(boolean error2) {
			error = error2;
		}
	}

}
