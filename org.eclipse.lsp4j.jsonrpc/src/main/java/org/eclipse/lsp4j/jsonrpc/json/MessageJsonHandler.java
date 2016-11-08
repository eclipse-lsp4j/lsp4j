/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EnumTypeAdapterFactory;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MessageJsonHandler {
    
	public static GsonBuilder getDefaultGsonBuilder() {
	    return new GsonBuilder()
	    	.registerTypeAdapterFactory(new CollectionTypeAdapterFactory())
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
			result = parseRequest(json, idElement.getAsString(), methodElement.getAsString());
		else if (idElement != null && (json.get("result") != null || json.get("error") != null))
			result = parseResponse(json, idElement.getAsString());
		else if (methodElement != null)
			result = parseNotification(json, methodElement.getAsString());
		else
			throw new IllegalStateException("Unknown message "+json);
		JsonElement jsonRpcElement = json.get("jsonrpc");
		if (jsonRpcElement != null)
			result.setJsonrpc(jsonRpcElement.getAsString());
		return result;
	}
	
	protected RequestMessage parseRequest(JsonObject json, String requestId, String method) {
		try {
			RequestMessage result = new RequestMessage();
			result.setId(requestId);
			result.setMethod(method);
			JsonElement paramsElement = json.get("params");
			if (paramsElement != null) {
				Type paramType = null;
				JsonRpcMethod jsonRpcMethod = supportedMethods.get(method);
				if (jsonRpcMethod != null)
					paramType = jsonRpcMethod.getParameterType();
				result.setParams(gson.fromJson(paramsElement, paramType != null ? paramType : Object.class));
			}
			return result;
		} catch (Exception e) {
			throw new InvalidMessageException("Could not parse request: " + e.getMessage(), json, requestId, e);
		}
	}
	
	protected ResponseMessage parseResponse(JsonObject json, String responseId) {
		if (methodProvider == null)
			throw new IllegalStateException("Response methods are not accepted.");
		try {
			ResponseMessage result = new ResponseMessage();
			result.setId(responseId);
			JsonElement resultElem = json.get("result");
			if (resultElem != null) {
				Type resultType = null;
				JsonRpcMethod jsonRpcMethod = supportedMethods.get(methodProvider.resolveMethod(responseId));
				if (jsonRpcMethod != null) {
					resultType = jsonRpcMethod.getReturnType();
					if (resultType != null && resultElem.isJsonArray()) {
						JsonArray arrayElem = resultElem.getAsJsonArray();
						List<?> list = Lists.newArrayListWithExpectedSize(arrayElem.size());
						for (JsonElement e : arrayElem) {
							list.add(gson.fromJson(e, resultType));
						}
						result.setResult(list);
						return result;
					}
				}
				result.setResult(gson.fromJson(resultElem, resultType != null ? resultType : Object.class));
			} else {
				JsonElement errorElement = json.get("error");
				if (errorElement != null) {
					result.setError(gson.fromJson(errorElement, ResponseError.class));
				}
			}
			return result;
		} catch (Exception e) {
			throw new InvalidMessageException("Could not parse response: " + e.getMessage(), json, responseId, e);
		}
	}
	
	protected NotificationMessage parseNotification(JsonObject json, String method) {
		try {
			NotificationMessage result = new NotificationMessage();
			result.setMethod(method);
			JsonElement paramsElement = json.get("params");
			if (paramsElement != null) {
				Type paramType = null;
				JsonRpcMethod jsonRpcMethod = supportedMethods.get(method);
				if (jsonRpcMethod != null) {
					paramType = jsonRpcMethod.getParameterType();
				}
				result.setParams(gson.fromJson(paramsElement, paramType != null ? paramType : Object.class));
			}
			return result;
		} catch (Exception e) {
			throw new InvalidMessageException("Could not parse notification: " + e.getMessage(), json, null, e);
		}
	}
	
	public String serialize(Message message) {
		StringWriter writer = new StringWriter();
		serialize(message, writer);
		return writer.toString();
	}
	
	public void serialize(Message message, Writer output) {
		gson.toJson(message, output);
	}
	
}
