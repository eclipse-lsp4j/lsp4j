/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json;

public class InvalidMessageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Object messageObject;
	private String requestId;

	public InvalidMessageException(String message, Object messageObject, String requestId) {
		this(message, messageObject, requestId, null);
	}
	
	public InvalidMessageException(String message, Object messageObject, String requestId, Throwable cause) {
		super(message, cause);
		this.messageObject = messageObject;
		this.requestId = requestId;
	}
	
	public Object getMessageObject() {
		return messageObject;
	}
	
	public String getRequestId() {
		return requestId;
	}
}
