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

import jakarta.websocket.MessageHandler;

import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.MessageIssueException;
import org.eclipse.lsp4j.jsonrpc.MessageIssueHandler;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.Message;

/**
 * WebSocket message handler that parses JSON messages and forwards them to a {@link MessageConsumer}.
 */
public class WebSocketMessageHandler implements MessageHandler.Whole<String> {
	
	private final MessageConsumer callback;
	private final MessageJsonHandler jsonHandler;
	private final MessageIssueHandler issueHandler;
	
	public WebSocketMessageHandler(MessageConsumer callback, MessageJsonHandler jsonHandler, MessageIssueHandler issueHandler) {
		this.callback = callback;
		this.jsonHandler = jsonHandler;
		this.issueHandler = issueHandler;
	}
	
	public void onMessage(String content) {
		try {
			Message message = jsonHandler.parseMessage(content);
			callback.consume(message);
		} catch (MessageIssueException exception) {
			// An issue was found while parsing or validating the message
			issueHandler.handle(exception.getRpcMessage(), exception.getIssues());
		}
	}
	
}
