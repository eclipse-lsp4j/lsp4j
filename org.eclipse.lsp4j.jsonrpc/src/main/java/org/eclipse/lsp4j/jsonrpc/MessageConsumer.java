/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc;

import org.eclipse.lsp4j.jsonrpc.messages.Message;

public interface MessageConsumer {
	
	/**
	 * Consume a single message.
	 * 
	 * @throws MessageIssueException when an issue is found that prevents further processing of the message
	 * @throws JsonRpcException when accessing the JSON-RPC communication channel fails
	 */
	void consume(Message message) throws MessageIssueException, JsonRpcException;
	
}
