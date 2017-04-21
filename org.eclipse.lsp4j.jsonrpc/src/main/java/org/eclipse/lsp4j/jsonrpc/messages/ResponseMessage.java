/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * Response Message sent as a result of a request. If a request doesn't provide
 * a result value the receiver of a request still needs to return a response
 * message to conform to the JSON RPC specification. The result property of the
 * ResponseMessage should be set to null in this case to signal a successful
 * request.
 */
public class ResponseMessage extends Message {

	/**
	 * The request id.
	 */
	@NonNull
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * The result of a request. This can be omitted in the case of an error.
	 */
	private Object result;

	public Object getResult() {
		return this.result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * The error object in case a request fails.
	 */
	private ResponseError error;

	public ResponseError getError() {
		return this.error;
	}

	public void setError(ResponseError error) {
		this.error = error;
	}
	
}
