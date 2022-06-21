/******************************************************************************
 * Copyright (c) 2019 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.Session;

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
	private ConcurrentLinkedQueue<String> messageQueue;
	private WebSocketSendHandler handler;
	
	public WebSocketMessageConsumer(Session session, MessageJsonHandler jsonHandler) {
		this.session = session;
		this.jsonHandler = jsonHandler;
		this.messageQueue = new ConcurrentLinkedQueue<>();
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
				messageQueue.add(message);
				handleNextMessage();
			} else {
				int currentOffset = 0;
				while (currentOffset < length) {
					int currentEnd = Math.min(currentOffset + session.getMaxTextMessageBufferSize(), length);
					session.getBasicRemote().sendText(message.substring(currentOffset, currentEnd), currentEnd == length);
					currentOffset = currentEnd;
				}
			}
		} else {
			LOG.info("Ignoring message due to closed session: " + message);
		}
	}
	
	class WebSocketSendHandler implements SendHandler {
		@Override
		public void onResult(SendResult result) {
			handler = null;
			handleNextMessage();
		}
	}
	
	private void handleNextMessage() {
		if (handler == null && !messageQueue.isEmpty() && session.isOpen()) {
			handler = new WebSocketSendHandler();
			session.getAsyncRemote().sendText(messageQueue.poll(), handler);
		}
	}
	
}
