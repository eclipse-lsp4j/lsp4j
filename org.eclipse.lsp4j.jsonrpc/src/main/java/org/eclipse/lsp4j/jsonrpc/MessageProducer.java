/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc;

public interface MessageProducer {
	
	/**
	 * Listen to a message source and forward all messages to the given consumer. Typically this method
	 * blocks until the message source is unable to deliver more messages.
	 * 
	 * @throws JsonRpcException when accessing the JSON-RPC communication channel fails
	 */
	void listen(MessageConsumer messageConsumer) throws JsonRpcException;
	
}
