/*******************************************************************************
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
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

public class DSPLauncher {

	public static Launcher<IDebugProtocolClient> createServerLauncher(IDebugProtocolServer server, InputStream in,
			OutputStream out) {
		return DebugLauncher.createLauncher(server, IDebugProtocolClient.class, in, out);
	}

	public static Launcher<IDebugProtocolClient> createServerLauncher(IDebugProtocolServer server, InputStream in,
			OutputStream out, boolean validate, PrintWriter trace) {
		return DebugLauncher.createLauncher(server, IDebugProtocolClient.class, in, out, validate, trace);
	}

	public static Launcher<IDebugProtocolClient> createServerLauncher(IDebugProtocolServer server, InputStream in,
			OutputStream out, ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		return DebugLauncher.createLauncher(server, IDebugProtocolClient.class, in, out, executorService, wrapper);
	}

	public static Launcher<IDebugProtocolServer> createClientLauncher(IDebugProtocolClient client, InputStream in,
			OutputStream out) {
		return DebugLauncher.createLauncher(client, IDebugProtocolServer.class, in, out);
	}

	public static Launcher<IDebugProtocolServer> createClientLauncher(IDebugProtocolClient client, InputStream in,
			OutputStream out, boolean validate, PrintWriter trace) {
		return DebugLauncher.createLauncher(client, IDebugProtocolServer.class, in, out, validate, trace);
	}

	public static Launcher<IDebugProtocolServer> createClientLauncher(IDebugProtocolClient client, InputStream in,
			OutputStream out, ExecutorService executorService, Function<MessageConsumer, MessageConsumer> wrapper) {
		return DebugLauncher.createLauncher(client, IDebugProtocolServer.class, in, out, executorService, wrapper);
	}

}
