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
	private String text;

	private int code;
	
	private Exception cause;
	
	public MessageIssue(@NonNull String text) {
		this(text, 0, null);
	}

	public MessageIssue(@NonNull String text, int code) {
		this(text, code, null);
	}
	
	public MessageIssue(@NonNull String text, int code, Exception cause) {
		this.text = text;
		this.code = code;
		this.cause = cause;
	}
	
	@NonNull
	public String getText() {
		return text;
	}
	
	public int getIssueCode() {
		return code;
	}

	public Exception getCause() {
		return cause;
	}
	
	@Override
	public String toString() {
		return getText();
	}
	
}
