/******************************************************************************
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.json.ConcurrentMessageProcessor;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethodProvider;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageConsumer;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageProducer;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.jsonrpc.validation.ReflectiveMessageValidator;

import com.google.gson.GsonBuilder;

/**
 * This is the entry point for applications that use LSP4J. A Launcher does all the wiring that is necessary to connect
 * your endpoint via an input stream and an output stream.
 */
public interface Launcher<T> {
	
	/**
	 * Create a new Launcher for a given local service object, a given remote interface and an input and output stream.
	 * 
	 * @param localService - the object that receives method calls from the remote service
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 */
	static <T> Launcher<T> createLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out) {
		return new Builder<T>()
				.setLocalService(localService)
				.setRemoteInterface(remoteInterface)
				.setInput(in)
				.setOutput(out)
				.create();
	}
	
	/**
	 * Create a new Launcher for a given local service object, a given remote interface and an input and output stream,
	 * and set up message validation and tracing.
	 * 
	 * @param localService - the object that receives method calls from the remote service
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param validate - whether messages should be validated with the {@link ReflectiveMessageValidator}
	 * @param trace - a writer to which incoming and outgoing messages are traced, or {@code null} to disable tracing
	 */
	static <T> Launcher<T> createLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out,
			boolean validate, PrintWriter trace) {
		return new Builder<T>()
				.setLocalService(localService)
				.setRemoteInterface(remoteInterface)
				.setInput(in)
				.setOutput(out)
				.validateMessages(validate)
				.traceMessages(trace)
				.create();
	}
	
	/**
	 * Create a new Launcher for a given local service object, a given remote interface and an input and output stream.
	 * Threads are started with the given executor service. The wrapper function is applied to the incoming and
	 * outgoing message streams so additional message handling such as validation and tracing can be included.
	 * 
	 * @param localService - the object that receives method calls from the remote service
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 */
	static <T> Launcher<T> createLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out,
			ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		return createIoLauncher(localService, remoteInterface, in, out, executorService, wrapper);
	}
	
	/**
	 * Create a new Launcher for a given local service object, a given remote interface and an input and output stream.
	 * Threads are started with the given executor service. The wrapper function is applied to the incoming and
	 * outgoing message streams so additional message handling such as validation and tracing can be included.
	 * 
	 * @param localService - the object that receives method calls from the remote service
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 */
	static <T> Launcher<T> createIoLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out,
			ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		return new Builder<T>()
				.setLocalService(localService)
				.setRemoteInterface(remoteInterface)
				.setInput(in)
				.setOutput(out)
				.setExecutorService(executorService)
				.wrapMessages(wrapper)
				.create();
	}
	
	/**
	 * Create a new Launcher for a given local service object, a given remote interface and an input and output stream.
	 * Threads are started with the given executor service. The wrapper function is applied to the incoming and
	 * outgoing message streams so additional message handling such as validation and tracing can be included.
	 * The {@code configureGson} function can be used to register additional type adapters in the {@link GsonBuilder}
	 * in order to support protocol classes that cannot be handled by Gson's reflective capabilities.
	 * 
	 * @param localService - the object that receives method calls from the remote service
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 * @param configureGson - a function for Gson configuration
	 */
	static <T> Launcher<T> createIoLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out,
			ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper, Consumer<GsonBuilder> configureGson) {
		return new Builder<T>()
				.setLocalService(localService)
				.setRemoteInterface(remoteInterface)
				.setInput(in)
				.setOutput(out)
				.setExecutorService(executorService)
				.wrapMessages(wrapper)
				.configureGson(configureGson)
				.create();
	}
	
	/**
	 * Create a new Launcher for a given local service object, a given remote interface and an input and output stream.
	 * Threads are started with the given executor service. The wrapper function is applied to the incoming and
	 * outgoing message streams so additional message handling such as validation and tracing can be included.
	 * The {@code configureGson} function can be used to register additional type adapters in the {@link GsonBuilder}
	 * in order to support protocol classes that cannot be handled by Gson's reflective capabilities.
	 * 
	 * @param localService - the object that receives method calls from the remote service
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param validate - whether messages should be validated with the {@link ReflectiveMessageValidator}
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 * @param configureGson - a function for Gson configuration
	 */
	static <T> Launcher<T> createIoLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out, boolean validate,
			ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper, Consumer<GsonBuilder> configureGson) {
		return new Builder<T>()
				.setLocalService(localService)
				.setRemoteInterface(remoteInterface)
				.setInput(in)
				.setOutput(out)
				.validateMessages(validate)
				.setExecutorService(executorService)
				.wrapMessages(wrapper)
				.configureGson(configureGson)
				.create();
	}
	
	/**
	 * Create a new Launcher for a collection of local service objects, a collection of remote interfaces and an
	 * input and output stream. Threads are started with the given executor service. The wrapper function is applied
	 * to the incoming and outgoing message streams so additional message handling such as validation and tracing
	 * can be included. The {@code configureGson} function can be used to register additional type adapters in
	 * the {@link GsonBuilder} in order to support protocol classes that cannot be handled by Gson's reflective
	 * capabilities.
	 * 
	 * @param localServices - the objects that receive method calls from the remote services
	 * @param remoteInterfaces - interfaces on which RPC methods are looked up
	 * @param classLoader - a class loader that is able to resolve all given interfaces
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 * @param configureGson - a function for Gson configuration
	 */
	static Launcher<Object> createIoLauncher(Collection<Object> localServices, Collection<Class<?>> remoteInterfaces, ClassLoader classLoader,
			InputStream in, OutputStream out, ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper,
			Consumer<GsonBuilder> configureGson) {
		return new Builder<Object>()
				.setLocalServices(localServices)
				.setRemoteInterfaces(remoteInterfaces)
				.setClassLoader(classLoader)
				.setInput(in)
				.setOutput(out)
				.setExecutorService(executorService)
				.wrapMessages(wrapper)
				.configureGson(configureGson)
				.create();
	}
	
	
	//------------------------------ Builder Class ------------------------------//
	
	/**
	 * The launcher builder wires up all components for JSON-RPC communication.
	 */
	public static class Builder<T> {
		
		protected Collection<Object> localServices;
		protected Collection<Class<? extends T>> remoteInterfaces;
		protected InputStream input;
		protected OutputStream output;
		protected ExecutorService executorService;
		protected Function<MessageConsumer, MessageConsumer> messageWrapper;
		protected boolean validateMessages;
		protected PrintWriter messageTracer;
		protected Consumer<GsonBuilder> configureGson;
		protected ClassLoader classLoader;
		
		public Builder<T> setLocalService(Object localService) {
			this.localServices = Collections.singletonList(localService);
			return this;
		}

		public Builder<T> setLocalServices(Collection<Object> localServices) {
			this.localServices = localServices;
			return this;
		}

		public Builder<T> setRemoteInterface(Class<? extends T> remoteInterface) {
			this.remoteInterfaces = Collections.singletonList(remoteInterface);
			return this;
		}
		
		public Builder<T> setRemoteInterfaces(Collection<Class<? extends T>> remoteInterfaces) {
			this.remoteInterfaces = remoteInterfaces;
			return this;
		}

		public Builder<T> setInput(InputStream input) {
			this.input = input;
			return this;
		}

		public Builder<T> setOutput(OutputStream output) {
			this.output = output;
			return this;
		}

		public Builder<T> setExecutorService(ExecutorService executorService) {
			this.executorService = executorService;
			return this;
		}

		public Builder<T> setClassLoader(ClassLoader classLoader) {
			this.classLoader = classLoader;
			return this;
		}

		public Builder<T> wrapMessages(Function<MessageConsumer, MessageConsumer> wrapper) {
			this.messageWrapper = wrapper;
			return this;
		}
		
		public Builder<T> validateMessages(boolean validate) {
			this.validateMessages = validate;
			return this;
		}
		
		public Builder<T> traceMessages(PrintWriter tracer) {
			this.messageTracer = tracer;
			return this;
		}

		public Builder<T> configureGson(Consumer<GsonBuilder> configureGson) {
			this.configureGson = configureGson;
			return this;
		}

		@SuppressWarnings("unchecked")
		public Launcher<T> create() {
			if (input == null)
				throw new IllegalStateException("Input stream must be configured.");
			if (output == null)
				throw new IllegalStateException("Output stream must be configured.");
			if (localServices == null)
				throw new IllegalStateException("Local service must be configured.");
			if (remoteInterfaces == null)
				throw new IllegalStateException("Remote interface must be configured.");
			
			MessageJsonHandler jsonHandler = createJsonHandler();
			RemoteEndpoint remoteEndpoint = createRemoteEndpoint(jsonHandler);
			T remoteProxy;
			if (localServices.size() == 1 && remoteInterfaces.size() == 1) {
				remoteProxy = ServiceEndpoints.toServiceObject(remoteEndpoint, remoteInterfaces.iterator().next());
			} else {
				remoteProxy = (T) ServiceEndpoints.toServiceObject(remoteEndpoint, (Collection<Class<?>>) (Object) remoteInterfaces, classLoader);
			}
			StreamMessageProducer reader = new StreamMessageProducer(input, jsonHandler, remoteEndpoint);
			MessageConsumer messageConsumer = wrapMessageConsumer(remoteEndpoint);
			ExecutorService execService = executorService != null ? executorService : Executors.newCachedThreadPool();
			
			return new Launcher<T> () {
				@Override
				public Future<Void> startListening() {
					return ConcurrentMessageProcessor.startProcessing(reader, messageConsumer, execService);
				}

				@Override
				public T getRemoteProxy() {
					return remoteProxy;
				}

				@Override
				public RemoteEndpoint getRemoteEndpoint() {
					return remoteEndpoint;
				}
			};
		}
		
		protected MessageConsumer wrapMessageConsumer(MessageConsumer consumer) {
			MessageConsumer result = consumer;
			if (messageTracer != null) {
				final PrintWriter tracer = messageTracer;
				result = message -> {
					tracer.println(message);
					tracer.flush();
					consumer.consume(message);
				};
			}
			if (validateMessages) {
				result = new ReflectiveMessageValidator(result);
			}
			if (messageWrapper != null) {
				result = messageWrapper.apply(result);
			}
			return result;
		}
		
		/**
		 * Gather all JSON-RPC methods from the local and remote services.
		 */
		protected Map<String, JsonRpcMethod> getSupportedMethods() {
			Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<>();
			// Gather the supported methods of remote interfaces
			for (Class<?> interface_ : remoteInterfaces) {
				supportedMethods.putAll(ServiceEndpoints.getSupportedMethods(interface_));
			}
			
			// Gather the supported methods of local services
			for (Object localService : localServices) {
				if (localService instanceof JsonRpcMethodProvider) {
					JsonRpcMethodProvider rpcMethodProvider = (JsonRpcMethodProvider) localService;
					supportedMethods.putAll(rpcMethodProvider.supportedMethods());
				} else {
					supportedMethods.putAll(ServiceEndpoints.getSupportedMethods(localService.getClass()));
				}
			}
			
			return supportedMethods;
		}
		
		/**
		 * Create the JSON handler for messages between the local and remote services.
		 */
		protected MessageJsonHandler createJsonHandler() {
			Map<String, JsonRpcMethod> supportedMethods = getSupportedMethods();
			if (configureGson != null)
				return new MessageJsonHandler(supportedMethods, configureGson);
			else
				return new MessageJsonHandler(supportedMethods);
		}
		
		/**
		 * Create the remote endpoint that communicates with the local services.
		 */
		protected RemoteEndpoint createRemoteEndpoint(MessageJsonHandler jsonHandler) {
			MessageConsumer outgoingMessageStream = new StreamMessageConsumer(output, jsonHandler);
			outgoingMessageStream = wrapMessageConsumer(outgoingMessageStream);
			RemoteEndpoint remoteEndpoint = new RemoteEndpoint(outgoingMessageStream, ServiceEndpoints.toEndpoint(localServices));
			jsonHandler.setMethodProvider(remoteEndpoint);
			return remoteEndpoint;
		}
	}
	
	
	//---------------------------- Interface Methods ----------------------------//
	
	/**
	 * Start a thread that listens to the input stream. The thread terminates when the stream is closed.
	 * 
	 * @return a future that returns {@code null} when the listener thread is terminated
	 */
	Future<Void> startListening();
	
	/**
	 * Returns the proxy instance that implements the remote service interfaces.
	 */
	T getRemoteProxy();
	
	/**
	 * Returns the remote endpoint. Use this one to send generic {@code request} or {@code notify} methods
	 * to the remote services.
	 */
	RemoteEndpoint getRemoteEndpoint();
	
}
