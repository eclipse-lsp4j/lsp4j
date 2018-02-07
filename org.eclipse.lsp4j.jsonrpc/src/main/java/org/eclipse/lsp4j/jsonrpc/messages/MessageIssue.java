/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

/**
 * An instance of this class is attached to a message in case the message could not be parsed
 * correctly or message validation yields one or more issues.
 */
public class MessageIssue {

	private final String message;
	private final int code;
	private final Exception cause;
	
	public MessageIssue(String message) {
		this(message, 0, null);
	}

	public MessageIssue(String message, int code) {
		this(message, code, null);
	}
	
	public MessageIssue(String message, int code, Exception cause) {
		this.message = message;
		this.code = code;
		this.cause = cause;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getIssueCode() {
		return code;
	}

	public Exception getCause() {
		return cause;
	}
	
	/**
	 * An exception thrown when a message issue cannot be handled by downstream consumers, but
	 * should be reported back to the caller.
	 */
	public static class InvalidMessageException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public InvalidMessageException(String message) {
			super(message);
		}
		
		public InvalidMessageException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
	/**
	 * Create an {@link InvalidMessageException} with the information given in this issue.
	 */
	public InvalidMessageException asThrowable() {
		if (cause == null)
			return new InvalidMessageException(message);
		else
			return new InvalidMessageException(message, cause);
	}
	
}
