/******************************************************************************
 * Copyright (c) 2019, 2021 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.websocket.jakarta;

import java.util.Collection;

import jakarta.websocket.Session;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;

/**
 * JSON-RPC launcher builder for use in {@link WebSocketEndpoint}.
 *
 * @param <T> remote service interface type
 */
public class WebSocketLauncherBuilder<T> extends Launcher.Builder<T> {
	
	protected Session session;
	
	public Collection<Object> getLocalServices() {
		return localServices;
	}
	
	public WebSocketLauncherBuilder<T> setSession(Session session) {
		this.session = session;
		return this;
	}
	
	@Override
	public Launcher<T> create() {
		if (localServices == null)
			throw new IllegalStateException("Local service must be configured.");
		if (remoteInterfaces == null)
			throw new IllegalStateException("Remote interface must be configured.");
		
		MessageJsonHandler jsonHandler = createJsonHandler();
		RemoteEndpoint remoteEndpoint = createRemoteEndpoint(jsonHandler);
		addMessageHandlers(jsonHandler, remoteEndpoint);
		T remoteProxy = createProxy(remoteEndpoint);
		return createLauncher(null, remoteProxy, remoteEndpoint, null);
	}
	
	@Override
	protected RemoteEndpoint createRemoteEndpoint(MessageJsonHandler jsonHandler) {
		MessageConsumer outgoingMessageStream = new WebSocketMessageConsumer(session, jsonHandler);
		outgoingMessageStream = wrapMessageConsumer(outgoingMessageStream);
		Endpoint localEndpoint = ServiceEndpoints.toEndpoint(localServices);
		RemoteEndpoint remoteEndpoint;
		if (exceptionHandler == null)
			remoteEndpoint = new RemoteEndpoint(outgoingMessageStream, localEndpoint);
		else
			remoteEndpoint = new RemoteEndpoint(outgoingMessageStream, localEndpoint, exceptionHandler);
		jsonHandler.setMethodProvider(remoteEndpoint);
		return remoteEndpoint;
	}
	
	protected void addMessageHandlers(MessageJsonHandler jsonHandler, RemoteEndpoint remoteEndpoint) {
		MessageConsumer messageConsumer = wrapMessageConsumer(remoteEndpoint);
		session.addMessageHandler(new WebSocketMessageHandler(messageConsumer, jsonHandler, remoteEndpoint));
	}
	
}
