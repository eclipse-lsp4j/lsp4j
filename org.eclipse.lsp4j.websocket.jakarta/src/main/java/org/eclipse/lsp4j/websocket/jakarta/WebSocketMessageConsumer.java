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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.websocket.Session;

import org.eclipse.lsp4j.jsonrpc.JsonRpcException;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.Message;

/**
 * Message consumer that sends messages via a WebSocket session.
 */
public class WebSocketMessageConsumer implements MessageConsumer {

	private static final Logger LOG = Logger.getLogger(WebSocketMessageConsumer.class.getName());
	
	private final Session session;
	private final MessageJsonHandler jsonHandler;
	
	public WebSocketMessageConsumer(Session session, MessageJsonHandler jsonHandler) {
		this.session = session;
		this.jsonHandler = jsonHandler;
	}
	
	public Session getSession() {
		return session;
	}
	
	@Override
	public void consume(Message message) {
		String content = jsonHandler.serialize(message);
		try {
			sendMessage(content);
		} catch (IOException exception) {
			throw new JsonRpcException(exception);
		}
	}
	
	protected void sendMessage(String message) throws IOException {
		if (session.isOpen()) {
			int length = message.length();
			if (length <= session.getMaxTextMessageBufferSize()) {
				session.getAsyncRemote().sendText(message);
			} else {
				int currentOffset = 0;
				while (currentOffset < length) {
					int currentEnd = Math.min(currentOffset + session.getMaxTextMessageBufferSize(), length);
					session.getBasicRemote().sendText(message.substring(currentOffset, currentEnd), currentEnd == length);
					currentOffset = currentEnd;
				}
			}
		} else {
			LOG.log(Level.INFO, "Ignoring message due to closed session: {0}", message);
		}
	}
	
}
