/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json;

import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EnumTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.json.adapters.MessageTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.messages.CancelParams;
import org.eclipse.lsp4j.jsonrpc.messages.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A wrapper around Gson that includes configuration required for JSON-RPC messages.
 * Override {@link #getDefaultGsonBuilder()} to replace or extend the default configuration.
 */
public class MessageJsonHandler {
	
	public static final JsonRpcMethod CANCEL_METHOD = JsonRpcMethod.notification("$/cancelRequest", CancelParams.class);
	
	private final Gson gson;
	
	private final Map<String, JsonRpcMethod> supportedMethods;
	
	private MethodProvider methodProvider;
	
	public MessageJsonHandler(Map<String, JsonRpcMethod> supportedMethods) {
		this.supportedMethods = supportedMethods;
		this.gson = getDefaultGsonBuilder().create();
	}
	
	public MessageJsonHandler(Map<String, JsonRpcMethod> supportedMethods, Consumer<GsonBuilder> configureGson) {
		this.supportedMethods = supportedMethods;
		GsonBuilder gsonBuilder = getDefaultGsonBuilder();
		configureGson.accept(gsonBuilder);
		this.gson = gsonBuilder.create();
	}
    
	public GsonBuilder getDefaultGsonBuilder() {
	    return new GsonBuilder()
	    	.registerTypeAdapterFactory(new CollectionTypeAdapterFactory())
	    	.registerTypeAdapterFactory(new EitherTypeAdapterFactory())
            .registerTypeAdapterFactory(new EnumTypeAdapterFactory())
            .registerTypeAdapterFactory(new MessageTypeAdapterFactory(this));
	}
	
	public JsonRpcMethod getJsonRpcMethod(String name) {
		JsonRpcMethod result = supportedMethods.get(name);
		if (result != null)
			return result;
		else if (CANCEL_METHOD.getMethodName().equals(name))
			return CANCEL_METHOD;
		return null;
	}
	
	public MethodProvider getMethodProvider() {
		return methodProvider;
	}
	
	public void setMethodProvider(MethodProvider methodProvider) {
		this.methodProvider = methodProvider;
	}
	
	public Message parseMessage(CharSequence input) {
		return gson.fromJson(input.toString(), Message.class);
	}
	
	public Message parseMessage(Reader input) {
		return gson.fromJson(input, Message.class);
	}
	
	public String serialize(Message message) {
		return gson.toJson(message, Message.class);
	}
	
	public void serialize(Message message, Writer output) {
		gson.toJson(message, Message.class, output);
	}
	
	
	private static MessageJsonHandler toStringInstance;
	
	/**
	 * Perform JSON serialization of the given object using the default configuration of JSON-RPC messages
	 * enhanced with the pretty printing option.
	 */
	public static String toString(Object object) {
		if (toStringInstance == null) {
			toStringInstance = new MessageJsonHandler(Collections.emptyMap()) {
				@Override
				public GsonBuilder getDefaultGsonBuilder() {
					return super.getDefaultGsonBuilder().setPrettyPrinting();
				}
			};
		}
		return toStringInstance.gson.toJson(object);
	}
	
}
