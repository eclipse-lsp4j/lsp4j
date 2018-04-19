/*******************************************************************************
 * Copyright (c) 2016-2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.launch;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.validation.ReflectiveMessageValidator;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;

/**
 * Specialized launcher for the Language Server Protocol.
 */
public final class LSPLauncher {
	
	private LSPLauncher() {}
	
	/**
	 * Create a new Launcher for a language server and an input and output stream.
	 * 
	 * @param server - the server that receives method calls from the remote client
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 */
	public static Launcher<LanguageClient> createServerLauncher(LanguageServer server, InputStream in, OutputStream out) {
		return new Builder<LanguageClient>()
				.setLocalService(server)
				.setRemoteInterface(LanguageClient.class)
				.setInput(in)
				.setOutput(out)
				.create();
	}
	
	/**
	 * Create a new Launcher for a language server and an input and output stream, and set up message validation and tracing.
	 * 
	 * @param server - the server that receives method calls from the remote client
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param validate - whether messages should be validated with the {@link ReflectiveMessageValidator}
	 * @param trace - a writer to which incoming and outgoing messages are traced, or {@code null} to disable tracing
	 */
	public static Launcher<LanguageClient> createServerLauncher(LanguageServer server, InputStream in, OutputStream out,
			boolean validate, PrintWriter trace) {
		return new Builder<LanguageClient>()
				.setLocalService(server)
				.setRemoteInterface(LanguageClient.class)
				.setInput(in)
				.setOutput(out)
				.validateMessages(validate)
				.traceMessages(trace)
				.create();
	}
	
	/**
	 * Create a new Launcher for a language server and an input and output stream. Threads are started with the given
	 * executor service. The wrapper function is applied to the incoming and outgoing message streams so additional
	 * message handling such as validation and tracing can be included.
	 * 
	 * @param server - the server that receives method calls from the remote client
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 */
	public static Launcher<LanguageClient> createServerLauncher(LanguageServer server, InputStream in, OutputStream out,
			ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		return new Builder<LanguageClient>()
				.setLocalService(server)
				.setRemoteInterface(LanguageClient.class)
				.setInput(in)
				.setOutput(out)
				.setExecutorService(executorService)
				.wrapMessages(wrapper)
				.create();
	}
	
	/**
	 * Create a new Launcher for a language client and an input and output stream.
	 * 
	 * @param client - the client that receives method calls from the remote server
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 */
	public static Launcher<LanguageServer> createClientLauncher(LanguageClient client, InputStream in, OutputStream out) {
		return new Builder<LanguageServer>()
				.setLocalService(client)
				.setRemoteInterface(LanguageServer.class)
				.setInput(in)
				.setOutput(out)
				.create();
	}
	
	/**
	 * Create a new Launcher for a language client and an input and output stream, and set up message validation and tracing.
	 * 
	 * @param client - the client that receives method calls from the remote server
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param validate - whether messages should be validated with the {@link ReflectiveMessageValidator}
	 * @param trace - a writer to which incoming and outgoing messages are traced, or {@code null} to disable tracing
	 */
	public static Launcher<LanguageServer> createClientLauncher(LanguageClient client, InputStream in, OutputStream out,
			boolean validate, PrintWriter trace) {
		return new Builder<LanguageServer>()
				.setLocalService(client)
				.setRemoteInterface(LanguageServer.class)
				.setInput(in)
				.setOutput(out)
				.validateMessages(validate)
				.traceMessages(trace)
				.create();
	}
	
	/**
	 * Create a new Launcher for a language client and an input and output stream. Threads are started with the given
	 * executor service. The wrapper function is applied to the incoming and outgoing message streams so additional
	 * message handling such as validation and tracing can be included.
	 * 
	 * @param client - the client that receives method calls from the remote server
	 * @param in - input stream to listen for incoming messages
	 * @param out - output stream to send outgoing messages
	 * @param executorService - the executor service used to start threads
	 * @param wrapper - a function for plugging in additional message consumers
	 */
	public static Launcher<LanguageServer> createClientLauncher(LanguageClient client, InputStream in, OutputStream out,
			ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		return new Builder<LanguageServer>()
				.setLocalService(client)
				.setRemoteInterface(LanguageServer.class)
				.setInput(in)
				.setOutput(out)
				.setExecutorService(executorService)
				.wrapMessages(wrapper)
				.create();
	}
	
	/**
	 * Launcher builder for the Language Server Protocol.
	 */
	public static class Builder<T> extends Launcher.Builder<T> {
		
	}

}
