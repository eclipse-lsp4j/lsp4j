/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * Describes an issue found while parsing or validating a message.
 */
public class MessageIssue {

	@NonNull
	private String message;

	private int code;
	
	private Exception cause;
	
	public MessageIssue(@NonNull String message) {
		this(message, 0, null);
	}

	public MessageIssue(@NonNull String message, int code) {
		this(message, code, null);
	}
	
	public MessageIssue(@NonNull String message, int code, Exception cause) {
		this.message = message;
		this.code = code;
		this.cause = cause;
	}
	
	@NonNull
	public String getMessage() {
		return message;
	}
	
	public int getIssueCode() {
		return code;
	}

	public Exception getCause() {
		return cause;
	}
	
}
