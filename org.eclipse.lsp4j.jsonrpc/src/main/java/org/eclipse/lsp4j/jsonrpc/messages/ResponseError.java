/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

public class ResponseError {
	
	/**
	 * A number indicating the error type that occured.
	 */
	@NonNull
	private ResponseErrorCode code;

	public ResponseErrorCode getCode() {
		return this.code;
	}

	public void setCode(ResponseErrorCode code) {
		this.code = code;
	}

	/**
	 * A string providing a short decription of the error.
	 */
	@NonNull
	private String message;

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * A Primitive or Structured value that contains additional information
	 * about the error. Can be omitted.
	 */
	private Object data;

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResponseError() {
	}
	
	public ResponseError(ResponseErrorCode code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	@Override
	public String toString() {
		return MessageJsonHandler.getDefaultGsonBuilder()
				.setPrettyPrinting()
				.create()
				.toJson(this);
	}
	
}
