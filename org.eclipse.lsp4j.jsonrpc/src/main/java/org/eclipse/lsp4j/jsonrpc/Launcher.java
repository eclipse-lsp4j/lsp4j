/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
	 * @param localService - an object on which classes RPC methods are looked up
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - inputstream to listen for incoming messages
	 * @param out - outputstream to send outgoing messages
	 */
	static <T> Launcher<T> createLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out) {
		return createLauncher(localService, remoteInterface, in, out, false, null);
	}
	
	/**
	 * Create a new Launcher for a given local service object, a given remote interface and an input and output stream,
	 * and set up message validation and tracing.
	 * 
	 * @param localService - an object on which classes RPC methods are looked up
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - inputstream to listen for incoming messages
	 * @param out - outputstream to send outgoing messages
	 * @param validate - whether messages should be validated with the {@link ReflectiveMessageValidator}
	 * @param trace - a writer to which incoming and outgoing messages are traced, or {@code null} to disable tracing
	 */
	static <T> Launcher<T> createLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out,
			boolean validate, PrintWriter trace) {
		Function<MessageConsumer, MessageConsumer> wrapper = consumer -> {
			MessageConsumer result = consumer;
			if (trace != null) {
				result = message -> {
					trace.println(message);
					trace.flush();
					consumer.consume(message);
				};
			}
			if (validate) {
				result = new ReflectiveMessageValidator(result);
			}
			return result;
		};
		return createIoLauncher(localService, remoteInterface, in, out, Executors.newCachedThreadPool(), wrapper);
	}
	
	/**
	 * Create a new Launcher for a given local service object, a given remote interface and an input and output stream.
	 * Threads are started with the given executor service. The wrapper function is applied to the incoming and
	 * outgoing message streams so additional message handling such as validation and tracing can be included.
	 * 
	 * @param localService - an object on which classes RPC methods are looked up
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - inputstream to listen for incoming messages
	 * @param out - outputstream to send outgoing messages
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
	 * @param localService - an object on which classes RPC methods are looked up
	 * @param remoteInterface - an interface on which RPC methods are looked up
	 * @param in - inputstream to listen for incoming messages
	 * @param out - outputstream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 */
	static <T> Launcher<T> createIoLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out,
			ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		Consumer<GsonBuilder> configureGson = gsonBuilder -> {};
		return createIoLauncher(localService, remoteInterface, in, out, executorService, wrapper, configureGson);
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
	 * @param in - inputstream to listen for incoming messages
	 * @param out - outputstream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 * @param configureGson - a function for Gson configuration
	 */
	static <T> Launcher<T> createIoLauncher(Object localService, Class<T> remoteInterface, InputStream in, OutputStream out,
			ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper, Consumer<GsonBuilder> configureGson) {
		Collection<Object> localServiceList = Collections.singletonList(localService);
		Collection<Class<?>> remoteInterfaceList = Collections.singletonList(remoteInterface);
		MessageJsonHandler jsonHandler = setupJsonHandler(localServiceList, remoteInterfaceList, configureGson);
		RemoteEndpoint remoteEndpoint = setupRemoteEndpoint(localServiceList, out, jsonHandler, wrapper);
		
		MessageConsumer messageConsumer = wrapper.apply(remoteEndpoint);
		StreamMessageProducer reader = new StreamMessageProducer(in, jsonHandler);
		
		T remoteProxy = ServiceEndpoints.toServiceObject(remoteEndpoint, remoteInterface);
		
		return new Launcher<T> () {
			@Override
			public Future<?> startListening() {
				return ConcurrentMessageProcessor.startProcessing(reader, messageConsumer, executorService);
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
	 * @param in - inputstream to listen for incoming messages
	 * @param out - outputstream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 * @param configureGson - a function for Gson configuration
	 */
	static Launcher<Object> createIoLauncher(Collection<Object> localServices, Collection<Class<?>> remoteInterfaces, ClassLoader classLoader,
			InputStream in, OutputStream out, ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper,
			Consumer<GsonBuilder> configureGson) {
		MessageJsonHandler jsonHandler = setupJsonHandler(localServices, remoteInterfaces, configureGson);
		RemoteEndpoint remoteEndpoint = setupRemoteEndpoint(localServices, out, jsonHandler, wrapper);
		
		MessageConsumer messageConsumer = wrapper.apply(remoteEndpoint);
		StreamMessageProducer reader = new StreamMessageProducer(in, jsonHandler);
		
		Object remoteProxy = ServiceEndpoints.toServiceObject(remoteEndpoint, remoteInterfaces, classLoader);
		
		return new Launcher<Object> () {
			@Override
			public Future<?> startListening() {
				return ConcurrentMessageProcessor.startProcessing(reader, messageConsumer, executorService);
			}
			
			@Override
			public Object getRemoteProxy() {
				return remoteProxy;
			}
			
			@Override
			public RemoteEndpoint getRemoteEndpoint() {
				return remoteEndpoint;
			}
		};
	}
	
	/**
	 * Set up the JSON handler for messages between the given local and remote services.
	 */
	static MessageJsonHandler setupJsonHandler(Collection<Object> localServices, Collection<Class<?>> remoteInterfaces, Consumer<GsonBuilder> configureGson) {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<String, JsonRpcMethod>();
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
		
		if (configureGson != null)
			return new MessageJsonHandler(supportedMethods, configureGson);
		else
			return new MessageJsonHandler(supportedMethods);
	}
	
	/**
	 * Set up the remote endpoint that communicates with the local services.
	 */
	static RemoteEndpoint setupRemoteEndpoint(Collection<Object> localServices, OutputStream out, MessageJsonHandler jsonHandler,
			Function<MessageConsumer, MessageConsumer> wrapper) {
		MessageConsumer outgoingMessageStream = new StreamMessageConsumer(out, jsonHandler);
		outgoingMessageStream = wrapper.apply(outgoingMessageStream);
		RemoteEndpoint remoteEndpoint = new RemoteEndpoint(outgoingMessageStream, ServiceEndpoints.toEndpoint(localServices));
		jsonHandler.setMethodProvider(remoteEndpoint);
		return remoteEndpoint;
	}
	
	
	//---------------------------- Interface Methods ----------------------------//
	
	/**
	 * Start a thread that listens to the input stream. The thread terminates when the stream is closed.
	 * 
	 * @return a future that returns {@code null} when the listener thread is terminated
	 */
	Future<?> startListening();
	
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
