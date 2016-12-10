/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Map;

import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EnumTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MessageJsonHandler {
    
	public static GsonBuilder getDefaultGsonBuilder() {
	    return new GsonBuilder()
	    	.registerTypeAdapterFactory(new CollectionTypeAdapterFactory())
	    	.registerTypeAdapterFactory(new EitherTypeAdapterFactory())
            .registerTypeAdapterFactory(new EnumTypeAdapterFactory());
	}
	
	private final JsonParser jsonParser = new JsonParser();
	private final Gson gson;
	
	private final Map<String, JsonRpcMethod> supportedMethods;
	
	private MethodProvider methodProvider;
	
	public MessageJsonHandler(Map<String, JsonRpcMethod> supportedMethods) {
		this(getDefaultGsonBuilder().create(), supportedMethods);
	}
	
	public MessageJsonHandler(Gson gson, Map<String, JsonRpcMethod> supportedMethods) {
		this.gson = gson;
		this.supportedMethods = supportedMethods;
	}
	
	public void setMethodProvider(MethodProvider methodProvider) {
		this.methodProvider = methodProvider;
	}
	
	public Message parseMessage(CharSequence input) {
		return parseMessage(new StringReader(input.toString()));
	}
	
	public Message parseMessage(Reader input) {
		JsonObject json = jsonParser.parse(input).getAsJsonObject();
		JsonElement idElement = json.get("id");
		JsonElement methodElement = json.get("method");
		Message result;
		if (idElement != null && methodElement != null)
			result = new RequestMessage(json);
		else if (idElement != null && (json.get("result") != null || json.get("error") != null))
			result = new ResponseMessage(json);
		else if (methodElement != null)
			result = new NotificationMessage(json);
		else
			throw new IllegalStateException("Unknown message "+json);
		return result;
	}
	
	public String serialize(Message message) {
		StringWriter writer = new StringWriter();
		serialize(message, writer);
		return writer.toString();
	}
	
	public void serialize(Message message, Writer output) {
		if (message instanceof ResponseMessage) {
			ResponseMessage responseMessage = (ResponseMessage) message;
			try {
				output.append("{\"id\":"+responseMessage.getId());
				output.append(",\"jsonrpc\":"+responseMessage.getJsonrpc());
				if (responseMessage.getError() != null) {
					output.append(",\"error\":");
					gson.toJson(responseMessage.getError().getWrapped(), output);
				} else {
					output.append(",\"result\":");
					gson.toJson(responseMessage.getResult(), output);
				}
				output.append("}");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			gson.toJson(message.getWrapped(), output);
		}
	}
	
}
