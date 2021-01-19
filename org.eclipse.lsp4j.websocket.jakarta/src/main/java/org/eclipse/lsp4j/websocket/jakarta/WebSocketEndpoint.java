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

import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;

import org.eclipse.lsp4j.jsonrpc.Launcher;

/**
 * WebSocket endpoint implementation that connects to a JSON-RPC service.
 *
 * @param <T> remote service interface type
 */
public abstract class WebSocketEndpoint<T> extends Endpoint {
	
	@Override
	public void onOpen(Session session, EndpointConfig config) {
		WebSocketLauncherBuilder<T> builder = new WebSocketLauncherBuilder<>();
		builder.setSession(session);
		configure(builder);
		Launcher<T> launcher = builder.create();
		connect(builder.getLocalServices(), launcher.getRemoteProxy());
	}
	
	/**
	 * Configure the JSON-RPC launcher. Implementations should set at least the
	 * {@link Launcher.Builder#setLocalService(Object) local service} and the
	 * {@link Launcher.Builder#setRemoteInterface(Class) remote interface}.
	 */
	protected abstract void configure(Launcher.Builder<T> builder);
	
	/**
	 * Override this in order to connect the local services to the remote service proxy.
	 */
	protected void connect(Collection<Object> localServices, T remoteProxy) {
	}
	
}
