/*******************************************************************************
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.debug;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.debug.json.DebugMessageJsonHandler;
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
 * This is the entry point for applications that use DSP4J. A DebugLauncher does
 * all the wiring that is necessary to connect your endpoint via an input stream
 * and an output stream.
 */
public interface DebugLauncher<T> extends Launcher<T> {

	/**
	 * Create a new Launcher for a given local service object, a given remote
	 * interface and an input and output stream.
	 *
	 * @param localService
	 *            - an object on which classes RPC methods are looked up
	 * @param remoteInterface
	 *            - an interface on which RPC methods are looked up
	 * @param in
	 *            - inputstream to listen for incoming messages
	 * @param out
	 *            - outputstream to send outgoing messages
	 */
	static <T> DebugLauncher<T> createLauncher(Object localService, Class<T> remoteInterface, InputStream in,
			OutputStream out) {
		return createLauncher(localService, remoteInterface, in, out, false, null);
	}

	/**
	 * Create a new Launcher for a given local service object, a given remote
	 * interface and an input and output stream, and set up message validation and
	 * tracing.
	 *
	 * @param localService
	 *            - an object on which classes RPC methods are looked up
	 * @param remoteInterface
	 *            - an interface on which RPC methods are looked up
	 * @param in
	 *            - inputstream to listen for incoming messages
	 * @param out
	 *            - outputstream to send outgoing messages
	 * @param validate
	 *            - whether messages should be validated with the
	 *            {@link ReflectiveMessageValidator}
	 * @param trace
	 *            - a writer to which incoming and outgoing messages are traced, or
	 *            {@code null} to disable tracing
	 */
	static <T> DebugLauncher<T> createLauncher(Object localService, Class<T> remoteInterface, InputStream in,
			OutputStream out, boolean validate, PrintWriter trace) {
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
	 * Create a new Launcher for a given local service object, a given remote
	 * interface and an input and output stream. Threads are started with the given
	 * executor service. The wrapper function is applied to the incoming and
	 * outgoing message streams so additional message handling such as validation
	 * and tracing can be included.
	 *
	 * @param localService
	 *            - an object on which classes RPC methods are looked up
	 * @param remoteInterface
	 *            - an interface on which RPC methods are looked up
	 * @param in
	 *            - inputstream to listen for incoming messages
	 * @param out
	 *            - outputstream to send outgoing messages
	 * @param executorService
	 *            - the executor service used to start threads
	 * @param wrapper
	 *            - a function for plugging in additional message consumers
	 */
	static <T> DebugLauncher<T> createLauncher(Object localService, Class<T> remoteInterface, InputStream in,
			OutputStream out, ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		return createIoLauncher(localService, remoteInterface, in, out, executorService, wrapper);
	}

	/**
	 * Create a new Launcher for a given local service object, a given remote
	 * interface and an input and output stream. Threads are started with the given
	 * executor service. The wrapper function is applied to the incoming and
	 * outgoing message streams so additional message handling such as validation
	 * and tracing can be included.
	 *
	 * @param localService
	 *            - an object on which classes RPC methods are looked up
	 * @param remoteInterface
	 *            - an interface on which RPC methods are looked up
	 * @param in
	 *            - inputstream to listen for incoming messages
	 * @param out
	 *            - outputstream to send outgoing messages
	 * @param executorService
	 *            - the executor service used to start threads
	 * @param wrapper
	 *            - a function for plugging in additional message consumers
	 */
	static <T> DebugLauncher<T> createIoLauncher(Object localService, Class<T> remoteInterface, InputStream in,
			OutputStream out, ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		Consumer<GsonBuilder> configureGson = gsonBuilder -> {
		};
		return createIoLauncher(localService, remoteInterface, in, out, executorService, wrapper, configureGson);
	}

	/**
	 * Create a new Launcher for a given local service object, a given remote
	 * interface and an input and output stream. Threads are started with the given
	 * executor service. The wrapper function is applied to the incoming and
	 * outgoing message streams so additional message handling such as validation
	 * and tracing can be included. The {@code configureGson} function can be used
	 * to register additional type adapters in the {@link GsonBuilder} in order to
	 * support protocol classes that cannot be handled by Gson's reflective
	 * capabilities.
	 *
	 * @param localService
	 *            - an object on which classes RPC methods are looked up
	 * @param remoteInterface
	 *            - an interface on which RPC methods are looked up
	 * @param in
	 *            - inputstream to listen for incoming messages
	 * @param out
	 *            - outputstream to send outgoing messages
	 * @param executorService
	 *            - the executor service used to start threads
	 * @param wrapper
	 *            - a function for plugging in additional message consumers
	 * @param configureGson
	 *            - a function for Gson configuration
	 */
	static <T> DebugLauncher<T> createIoLauncher(Object localService, Class<T> remoteInterface, InputStream in,
			OutputStream out, ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper,
			Consumer<GsonBuilder> configureGson) {
		Map<String, JsonRpcMethod> supportedMethods = new LinkedHashMap<String, JsonRpcMethod>();
		supportedMethods.putAll(ServiceEndpoints.getSupportedMethods(remoteInterface));

		if (localService instanceof JsonRpcMethodProvider) {
			JsonRpcMethodProvider rpcMethodProvider = (JsonRpcMethodProvider) localService;
			supportedMethods.putAll(rpcMethodProvider.supportedMethods());
		} else {
			supportedMethods.putAll(ServiceEndpoints.getSupportedMethods(localService.getClass()));
		}

		MessageJsonHandler jsonHandler = new DebugMessageJsonHandler(supportedMethods, configureGson);
		MessageConsumer outGoingMessageStream = new StreamMessageConsumer(out, jsonHandler);
		outGoingMessageStream = wrapper.apply(outGoingMessageStream);
		RemoteEndpoint serverEndpoint = new DebugRemoteEndpoint(outGoingMessageStream,
				ServiceEndpoints.toEndpoint(localService));
		jsonHandler.setMethodProvider(serverEndpoint);
		// wrap incoming message stream
		MessageConsumer messageConsumer = wrapper.apply(serverEndpoint);
		StreamMessageProducer reader = new StreamMessageProducer(in, jsonHandler);

		T remoteProxy = ServiceEndpoints.toServiceObject(serverEndpoint, remoteInterface);

		return new DebugLauncher<T>() {

			@Override
			public Future<?> startListening() {
				return ConcurrentMessageProcessor.startProcessing(reader, messageConsumer, executorService);
			}

			@Override
			public T getRemoteProxy() {
				return remoteProxy;
			}

		};
	}
}
