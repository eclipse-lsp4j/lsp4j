/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

import com.google.gson.JsonObject;

public class ResponseError extends WrappedJsonObject {

	public ResponseError() {
	}
	
	public ResponseError(JsonObject jsonObject) {
		super(jsonObject);
	}
	
	public ResponseError(ResponseErrorCode code, String message, Object data) {
		this.setCode(code);
		this.setMessage(message);
		this.setData(data);
	}

	private static WrappedJsonProperty<ResponseErrorCode> codeProperty = new WrappedJsonProperty<>("code", WrappedJsonConverter.enumConverter(ResponseErrorCode.class));
	private static WrappedJsonProperty<String> messageProperty = new WrappedJsonProperty<>("message", WrappedJsonConverter.stringConverter);
	private static WrappedJsonProperty<Object> dataProperty = new WrappedJsonProperty<>("data", WrappedJsonConverter.noConverter);
	
	/**
	 * A number indicating the error type that occured.
	 */
	@NonNull public ResponseErrorCode getCode() {
		return codeProperty.get(jsonObject);
	}

	public void setCode(@NonNull ResponseErrorCode code) {
		codeProperty.set(jsonObject, code);
	}

	/**
	 * A string providing a short decription of the error.
	 */
	@NonNull
	public String getMessage() {
		return messageProperty.get(jsonObject);
	}

	public void setMessage(String message) {
		messageProperty.set(jsonObject, message);
	}

	/**
	 * A Primitive or Structured value that contains additional information
	 * about the error. Can be omitted.
	 */
	public Object getData() {
		return dataProperty.get(jsonObject);
	}

	public void setData(Object data) {
		dataProperty.set(jsonObject, data);
	}

	
}
