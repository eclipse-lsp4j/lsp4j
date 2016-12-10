/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

import com.google.gson.JsonObject;

public class ResponseMessage extends Message {
	
	public ResponseMessage() {
		super();
	}

	public ResponseMessage(JsonObject jsonObject) {
		super(jsonObject);
	}

	private static WrappedJsonProperty<String> idProperty = new WrappedJsonProperty<>("id",WrappedJsonConverter.stringConverter);
	private static WrappedJsonProperty<Object> resultProperty = new WrappedJsonProperty<>("result",WrappedJsonConverter.noConverter);
	private static WrappedJsonProperty<ResponseError> errorProperty = new WrappedJsonProperty<>("error",WrappedJsonConverter.objectConverter(ResponseError.class));

	/**
	 * The request id.
	 */
	@NonNull
	public String getId() {
		return idProperty.get(jsonObject);
	}

	public void setId(String id) {
		idProperty.set(jsonObject, id);
	}

	/**
	 * The result of a request. This can be omitted in the case of an error.
	 */
	public Object getResult() {
		return resultProperty.get(jsonObject);
	}

	public void setResult(Object result) {
		resultProperty.set(jsonObject, result);
	}

	/**
	 * The error object in case a request fails.
	 */
	public ResponseError getError() {
		return errorProperty.get(jsonObject);
	}

	public void setError(ResponseError error) {
		errorProperty.set(jsonObject, error);
	}
	
}
