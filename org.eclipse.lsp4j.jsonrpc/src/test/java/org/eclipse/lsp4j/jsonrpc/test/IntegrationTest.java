/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test;

import static org.eclipse.lsp4j.jsonrpc.json.MessageConstants.CONTENT_LENGTH_HEADER;
import static org.eclipse.lsp4j.jsonrpc.json.MessageConstants.CRLF;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.junit.Assert;
import org.junit.Test;

public class IntegrationTest {

	public static class MyParam {
		public MyParam(String string) {
			this.value = string;
		}

		String value;
	}

	public static interface MyServer {
		@JsonRequest
		CompletableFuture<MyParam> askServer(MyParam param);
	}

	public static interface MyClient {
		@JsonRequest
		CompletableFuture<MyParam> askClient(MyParam param);
	}

	@Test
	public void testBothDirectionRequests() throws Exception {
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();
		
		in.connect(out2);
		out.connect(in2);
		
		MyClient client = new MyClient() {
			@Override
			public CompletableFuture<MyParam> askClient(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyServer> clientSideLauncher = Launcher.createLauncher(client, MyServer.class, in, out);
		
		// create server side
		MyServer server = new MyServer() {
			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyClient> serverSideLauncher = Launcher.createLauncher(server, MyClient.class, in2, out2);
		
		clientSideLauncher.startListening();
		serverSideLauncher.startListening();
		
		CompletableFuture<MyParam> fooFuture = clientSideLauncher.getRemoteProxy().askServer(new MyParam("FOO"));
		CompletableFuture<MyParam> barFuture = serverSideLauncher.getRemoteProxy().askClient(new MyParam("BAR"));
		
		Assert.assertEquals("FOO", fooFuture.get().value);
		Assert.assertEquals("BAR", barFuture.get().value);
	}

	@Test
	public void testCancellation() throws Exception {
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();
		
		in.connect(out2);
		out.connect(in2);
		
		boolean[] cancellationHappened = new boolean[1];
		
		MyClient client = new MyClient() {
			@Override
			public CompletableFuture<MyParam> askClient(MyParam param) {
				return CompletableFutures.computeAsync(cancelToken -> {
					try {
						long startTime = System.currentTimeMillis();
						do {
							cancelToken.checkCanceled();
							Thread.sleep(50);
						} while (System.currentTimeMillis() - startTime < 2000);
					} catch (CancellationException e) {
						cancellationHappened[0] = true;
					} catch (InterruptedException e) {
						Assert.fail("Thread was interrupted unexpectedly.");
					}
					return param;
				});
			}
		};
		Launcher<MyServer> clientSideLauncher = Launcher.createLauncher(client, MyServer.class, in, out);
		
		// create server side
		MyServer server = new MyServer() {
			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyClient> serverSideLauncher = Launcher.createLauncher(server, MyClient.class, in2, out2);
		
		clientSideLauncher.startListening();
		serverSideLauncher.startListening();
		
		CompletableFuture<MyParam> future = serverSideLauncher.getRemoteProxy().askClient(new MyParam("FOO"));
		future.cancel(true);
		long startTime = System.currentTimeMillis();
		while (!cancellationHappened[0]) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - startTime > 2000)
				Assert.fail("Timeout waiting for confirmation of cancellation.");
		}
		try {
			future.get();
			Assert.fail("Expected cancellation.");
		} catch (CancellationException e) {
		}
	}
	
	
	@Test
	public void testVersatility() throws Exception {
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();
		
		in.connect(out2);
		out.connect(in2);
		
		MyClient client = new MyClient() {
			private int tries = 0;
			
			@Override
			public CompletableFuture<MyParam> askClient(MyParam param) {
				if (tries == 0) {
					tries++;
					throw new UnsupportedOperationException();
				}
				return CompletableFutures.computeAsync(cancelToken -> {
					if (tries++ == 1)
						throw new UnsupportedOperationException();
					return param;
				});
			}
		};
		Launcher<MyServer> clientSideLauncher = Launcher.createLauncher(client, MyServer.class, in, out);
		
		// create server side
		MyServer server = new MyServer() {
			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyClient> serverSideLauncher = Launcher.createLauncher(server, MyClient.class, in2, out2);
		
		clientSideLauncher.startListening();
		serverSideLauncher.startListening();
		
		CompletableFuture<MyParam> errorFuture1 = serverSideLauncher.getRemoteProxy().askClient(new MyParam("FOO"));
		try {
			System.out.println(errorFuture1.get());
			Assert.fail();
		} catch (ExecutionException e) {
			Assert.assertNotNull(((ResponseErrorException)e.getCause()).getResponseError().getMessage());
		}
		CompletableFuture<MyParam> errorFuture2 = serverSideLauncher.getRemoteProxy().askClient(new MyParam("FOO"));
		try {
			errorFuture2.get();
			Assert.fail();
		} catch (ExecutionException e) {
			Assert.assertNotNull(((ResponseErrorException)e.getCause()).getResponseError().getMessage());
		}
		CompletableFuture<MyParam> goodFuture = serverSideLauncher.getRemoteProxy().askClient(new MyParam("FOO"));
		Assert.assertEquals("FOO", goodFuture.get().value);
	}

	@Test
	public void testUnknownMessages() throws Exception {
		// intercept log messages
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(GenericEndpoint.class.getName());
			
			// create client messages
			String clientMessage1 = "{\"jsonrpc\":\"2.0\","
					+ "\"method\":\"foo1\",\n" 
					+ " \"params\":\"bar\"\n"
					+ "}";
			String clientMessage2 = "{\"jsonrpc\":\"2.0\","
					+ "\"id\":\"1\",\n" 
					+ "\"method\":\"foo2\",\n" 
					+ " \"params\":\"bar\"\n"
					+ "}";
			String clientMessages = getHeader(clientMessage1.getBytes().length) + clientMessage1
					+ getHeader(clientMessage2.getBytes().length) + clientMessage2;
			
			// create server side
			ByteArrayInputStream in = new ByteArrayInputStream(clientMessages.getBytes());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			MyServer server = new MyServer() {
				@Override
				public CompletableFuture<MyParam> askServer(MyParam param) {
					return CompletableFuture.completedFuture(param);
				}
			};
			Launcher<MyClient> serverSideLauncher = Launcher.createLauncher(server, MyClient.class, in, out);
			serverSideLauncher.startListening();
			
			logMessages.await(Level.WARNING, "Unsupported notification method: foo1");
			logMessages.await(Level.WARNING, "Unsupported request method: foo2");
			
			Assert.assertEquals("Content-Length: 95" + CRLF + CRLF
					+ "{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"error\":{\"code\":-32600,\"message\":\"Unsupported request method: foo2\"}}",
					out.toString());
		} finally {
			logMessages.unregister();
		}
	}
    
    protected String getHeader(int contentLength) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(CONTENT_LENGTH_HEADER).append(": ").append(contentLength).append(CRLF);
        headerBuilder.append(CRLF);
        return headerBuilder.toString();
    }
	
}
