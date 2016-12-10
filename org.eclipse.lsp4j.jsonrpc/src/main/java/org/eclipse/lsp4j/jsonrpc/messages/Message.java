/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.json.MessageConstants;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

import com.google.gson.JsonObject;

public abstract class Message extends WrappedJsonObject {
	
	private static WrappedJsonProperty<String> jsonrpcProperty = new WrappedJsonProperty<>("jsonrpc",WrappedJsonConverter.stringConverter);
	
	public Message() {
		this(new JsonObject());
	}
	public Message(JsonObject jsonObject) {
		super(jsonObject);
		setJsonrpc(MessageConstants.JSONRPC_VERSION);
	}
	
	@NonNull public String getJsonrpc() {
		return jsonrpcProperty.get(jsonObject);
	}

	public void setJsonrpc(@NonNull String jsonrpc) {
		jsonrpcProperty.set(jsonObject, jsonrpc);
	}
	
}
