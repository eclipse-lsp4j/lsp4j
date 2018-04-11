/*******************************************************************************
 * Copyright (c) 2016-2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.debug.launch;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.debug.DebugLauncher;
import org.eclipse.lsp4j.jsonrpc.validation.ReflectiveMessageValidator;

/**
 * Specialized launcher for the debug protocol.
 */
public final class DSPLauncher {
	
	private DSPLauncher() {}

	/**
	 * Create a new Launcher for a debug server and an input and output stream.
	 * 
	 * @param server - the server that receives method calls from the remote client
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 */
	public static Launcher<IDebugProtocolClient> createServerLauncher(IDebugProtocolServer server, InputStream in,
			OutputStream out) {
		return DebugLauncher.createLauncher(server, IDebugProtocolClient.class, in, out);
	}

	/**
	 * Create a new Launcher for a debug server and an input and output stream, and set up message validation and tracing.
	 * 
	 * @param server - the server that receives method calls from the remote client
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param validate - whether messages should be validated with the {@link ReflectiveMessageValidator}
	 * @param trace - a writer to which incoming and outgoing messages are traced, or {@code null} to disable tracing
	 */
	public static Launcher<IDebugProtocolClient> createServerLauncher(IDebugProtocolServer server, InputStream in,
			OutputStream out, boolean validate, PrintWriter trace) {
		return DebugLauncher.createLauncher(server, IDebugProtocolClient.class, in, out, validate, trace);
	}

	/**
	 * Create a new Launcher for a debug server and an input and output stream. Threads are started with the given
	 * executor service. The wrapper function is applied to the incoming and outgoing message streams so additional
	 * message handling such as validation and tracing can be included.
	 * 
	 * @param server - the server that receives method calls from the remote client
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 */
	public static Launcher<IDebugProtocolClient> createServerLauncher(IDebugProtocolServer server, InputStream in,
			OutputStream out, ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		return DebugLauncher.createLauncher(server, IDebugProtocolClient.class, in, out, executorService, wrapper);
	}

	/**
	 * Create a new Launcher for a debug client and an input and output stream.
	 * 
	 * @param client - the client that receives method calls from the remote server
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 */
	public static Launcher<IDebugProtocolServer> createClientLauncher(IDebugProtocolClient client, InputStream in,
			OutputStream out) {
		return DebugLauncher.createLauncher(client, IDebugProtocolServer.class, in, out);
	}

	/**
	 * Create a new Launcher for a debug client and an input and output stream, and set up message validation and tracing.
	 * 
	 * @param client - the client that receives method calls from the remote server
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param validate - whether messages should be validated with the {@link ReflectiveMessageValidator}
	 * @param trace - a writer to which incoming and outgoing messages are traced, or {@code null} to disable tracing
	 */
	public static Launcher<IDebugProtocolServer> createClientLauncher(IDebugProtocolClient client, InputStream in,
			OutputStream out, boolean validate, PrintWriter trace) {
		return DebugLauncher.createLauncher(client, IDebugProtocolServer.class, in, out, validate, trace);
	}

	/**
	 * Create a new Launcher for a debug client and an input and output stream. Threads are started with the given
	 * executor service. The wrapper function is applied to the incoming and outgoing message streams so additional
	 * message handling such as validation and tracing can be included.
	 * 
	 * @param client - the client that receives method calls from the remote server
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 */
	public static Launcher<IDebugProtocolServer> createClientLauncher(IDebugProtocolClient client, InputStream in,
			OutputStream out, ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		return DebugLauncher.createLauncher(client, IDebugProtocolServer.class, in, out, executorService, wrapper);
	}

}
