/*******************************************************************************
 * Copyright (c) 2016, 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageConstants;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.MethodProvider;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * The type adapter for messages dispatches between the different message types: {@link RequestMessage},
 * {@link ResponseMessage}, and {@link NotificationMessage}.
 */
public class MessageTypeAdapter extends TypeAdapter<Message> {
	
	public static class Factory implements TypeAdapterFactory {
		
		private final MessageJsonHandler handler;
		
		public Factory(MessageJsonHandler handler) {
			this.handler = handler;
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			if (!Message.class.isAssignableFrom(typeToken.getRawType()))
				return null;
			return (TypeAdapter<T>) new MessageTypeAdapter(handler, gson);
		}
		
	}
	
	private static Type[] EMPTY_TYPE_ARRAY = {};

	private final MessageJsonHandler handler;
	private final Gson gson;
	
	public MessageTypeAdapter(MessageJsonHandler handler, Gson gson) {
		this.handler = handler;
		this.gson = gson;
	}

	@Override
	public Message read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		
		in.beginObject();
		String jsonrpc = null, method = null;
		Either<String, Integer> id = null;
		Object rawParams = null;
		Object rawResult = null;
		ResponseError error = null;
		while (in.hasNext()) {
			String name = in.nextName();
			switch (name) {
			case "jsonrpc": {
				jsonrpc = in.nextString();
				break;
			}
			case "id": {
				if (in.peek() == JsonToken.NUMBER)
					id = Either.forRight(in.nextInt());
				else
					id = Either.forLeft(in.nextString());
				break;
			}
			case "method": {
				method = in.nextString();
				break;
			}
			case "params": {
				rawParams = parseParams(in, method);
				break;
			}
			case "result": {
				rawResult = parseResult(in, id != null ? id.get().toString() : null);
				break;
			}
			case "error": {
				error = gson.fromJson(in, ResponseError.class);
				break;
			}
			default:
				in.skipValue();
			}
		}
		in.endObject();
		Object params = parseParams(rawParams, method);
		Object result = parseResult(rawResult, id != null ? id.get().toString() : null);
		return createMessage(jsonrpc, id, method, params, result, error);
	}

	/**
	 * Convert the json input into the result object corresponding to the call made
	 * by id.
	 *
	 * If the id is not known until after parsing, call
	 * {@link #parseResult(Object, String)} on the return value of this call for a
	 * second chance conversion.
	 *
	 * @param in
	 *            json input to read from
	 * @param id
	 *            id of request message this is in response to
	 * @return correctly typed object if the correct expected type can be
	 *         determined, or a JsonElement representing the result
	 */
	protected Object parseResult(JsonReader in, String id) {
		Object result;
		Type type = null;
		MethodProvider methodProvider = handler.getMethodProvider();
		if (methodProvider != null && id != null) {
			String resolvedMethod = methodProvider.resolveMethod(id);
			if (resolvedMethod != null) {
				JsonRpcMethod jsonRpcMethod = handler.getJsonRpcMethod(resolvedMethod);
				if (jsonRpcMethod != null)
					type = jsonRpcMethod.getReturnType();
			}
		}
		if (type == null)
			result = new JsonParser().parse(in);
		else
			result = gson.fromJson(in, type);
		return result;
	}

	/**
	 * Convert the JsonElement into the result object corresponding to the call made
	 * by id. If the result is already converted, does nothing.
	 *
	 * @param result
	 *            json element to read from
	 * @param id
	 *            id of request message this is in response to
	 * @return correctly typed object if the correct expected type can be
	 *         determined, or result unmodified if no conversion can be done.
	 */
	protected Object parseResult(Object result, String id) {
		if (isNull(result)) {
			return null;
		}
		if (result instanceof JsonElement) {
			// Type of result could not be resolved - try again with the parsed JSON tree
			MethodProvider methodProvider = handler.getMethodProvider();
			if (methodProvider != null) {
				String resolvedMethod = methodProvider.resolveMethod(id);
				if (resolvedMethod != null) {
					JsonRpcMethod jsonRpcMethod = handler.getJsonRpcMethod(resolvedMethod);
					if (jsonRpcMethod != null)
						result = gson.fromJson((JsonElement) result, jsonRpcMethod.getReturnType());
				}
			}
		}
		return result;
	}

	/**
	 * Convert the json input into the parameters object corresponding to the call
	 * made by method.
	 *
	 * If the method is not known until after parsing, call
	 * {@link #parseParams(Object, String)} on the return value of this call for a
	 * second chance conversion.
	 *
	 * @param in
	 *            json input to read from
	 * @param method
	 *            method name of request
	 * @return correctly typed object if the correct expected type can be
	 *         determined, or a JsonElement representing the parameters
	 */
	protected Object parseParams(JsonReader in, String method) throws IOException {
		JsonToken next = in.peek();
		if (next == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		Type[] parameterTypes = getParameterTypes(method);
		if (parameterTypes.length == 1) {
			return fromJson(in, parameterTypes[0]);
		}
		if (parameterTypes.length > 1 && next == JsonToken.BEGIN_ARRAY) {
			List<Object> parameters = new ArrayList<Object>(parameterTypes.length);
			int index = 0;
			in.beginArray();
			while (in.hasNext()) {
				Type parameterType = index < parameterTypes.length ? parameterTypes[index] : null;
				Object parameter = fromJson(in, parameterType);
				parameters.add(parameter);
				index++;
			}
			in.endArray();
			while (index < parameterTypes.length) {
				parameters.add(null);
				index++;
			}
			return parameters;
		}
		return new JsonParser().parse(in);
	}

	/**
	 * Convert the JsonElement into the parameters object corresponding to the call made
	 * by method. If the result is already converted, does nothing.
	 *
	 * @param params
	 *            json element to read from
	 * @param method
	 *            method name of request
	 * @return correctly typed object if the correct expected type can be
	 *         determined, or params unmodified if no conversion can be done.
	 */
	protected Object parseParams(Object params, String method) {
		if (isNull(params)) {
			return null;
		}
		if (!(params instanceof JsonElement)) {
			return params;
		}
		JsonElement rawParams = (JsonElement) params;
		Type[] parameterTypes = getParameterTypes(method);
		if (parameterTypes.length == 1) {
			return fromJson(rawParams, parameterTypes[0]);
		}
		if (parameterTypes.length > 1 && rawParams instanceof JsonArray) {
			JsonArray array = (JsonArray) rawParams;
			List<Object> parameters = new ArrayList<Object>(Math.max(array.size(), parameterTypes.length));
			int index = 0;
			Iterator<JsonElement> iterator = array.iterator();
			while (iterator.hasNext()) {
				Type parameterType = index < parameterTypes.length ? parameterTypes[index] : null;  
				Object parameter = fromJson(iterator.next(), parameterType);
				parameters.add(parameter);
				index++;
			}
			while (index < parameterTypes.length) {
				parameters.add(null);
				index++;
			}
			return parameters;
		}
		return rawParams;
	}

	protected Object fromJson(JsonReader in, Type type) {
		if (isNullOrVoidType(type)) {
			return new JsonParser().parse(in);
		}
		return gson.fromJson(in, type);
	}
	
	protected Object fromJson(JsonElement element, Type type) {
		if (isNull(element)) {
			return null;
		}
		if (isNullOrVoidType(type)) {
			return element;
		}
		Object value = gson.fromJson(element, type);
		if (isNull(value)) {
			return null;
		}
		return value;
	}

	protected boolean isNull(Object value) {
		return value == null || value instanceof JsonNull;
	}
	
	protected boolean isNullOrVoidType(Type type) {
		return type == null || Void.class == type;
	}

	protected Type[] getParameterTypes(String method) {
		if (method != null) {
			JsonRpcMethod jsonRpcMethod = handler.getJsonRpcMethod(method);
			if (jsonRpcMethod != null)
				return jsonRpcMethod.getParameterTypes();
		}
		return EMPTY_TYPE_ARRAY;
	}
	
	protected Message createMessage(String jsonrpc, Either<String, Integer> id, String method, Object params, Object result, ResponseError error) {
		if (id != null && method != null) {
			RequestMessage message = new RequestMessage();
			message.setJsonrpc(jsonrpc);
			message.setRawId(id);
			message.setMethod(method);
			message.setParams(params);
			return message;
		} else if (id != null) {
			ResponseMessage message = new ResponseMessage();
			message.setJsonrpc(jsonrpc);
			message.setRawId(id);
			if (error != null) {
				message.setError(error);
			} else {
				message.setResult(result);
			}
			return message;
		} else if (method != null) {
			NotificationMessage message = new NotificationMessage();
			message.setJsonrpc(jsonrpc);
			message.setMethod(method);
			message.setParams(params);
			return message;
		} else {
			throw new JsonParseException("Unable to identify the input message.");
		}
	}

	@Override
	public void write(JsonWriter out, Message message) throws IOException {
		out.beginObject();
		out.name("jsonrpc");
		out.value(message.getJsonrpc() == null ? MessageConstants.JSONRPC_VERSION : message.getJsonrpc());
		
		if (message instanceof RequestMessage) {
			RequestMessage requestMessage = (RequestMessage) message;
			out.name("id");
			writeId(out, requestMessage.getRawId());
			out.name("method");
			out.value(requestMessage.getMethod());
			out.name("params");
			Object params = requestMessage.getParams();
			if (params == null)
				writeNullValue(out);
			else
				gson.toJson(params, params.getClass(), out);
		} else if (message instanceof ResponseMessage) {
			ResponseMessage responseMessage = (ResponseMessage) message;
			out.name("id");
			writeId(out, responseMessage.getRawId());
			if (responseMessage.getError() != null) {
				out.name("error");
				gson.toJson(responseMessage.getError(), ResponseError.class, out);
			} else {
				out.name("result");
				Object result = responseMessage.getResult();
				if (result == null)
					writeNullValue(out);
				else
					gson.toJson(result, result.getClass(), out);
			}
		} else if (message instanceof NotificationMessage) {
			NotificationMessage notificationMessage = (NotificationMessage) message;
			out.name("method");
			out.value(notificationMessage.getMethod());
			out.name("params");
			Object params = notificationMessage.getParams();
			if (params == null)
				writeNullValue(out);
			else
				gson.toJson(params, params.getClass(), out);
		}
		
		out.endObject();
	}
	
	protected void writeId(JsonWriter out, Either<String, Integer> id) throws IOException {
		if (id == null)
			writeNullValue(out);
		else if (id.isLeft())
			out.value(id.getLeft());
		else if (id.isRight())
			out.value(id.getRight());
	}
	
	/**
	 * Use this method to write a {@code null} value even if the JSON writer is set to not serialize {@code null}.
	 */
	protected void writeNullValue(JsonWriter out) throws IOException {
		boolean previousSerializeNulls = out.getSerializeNulls();
		out.setSerializeNulls(true);
		out.nullValue();
		out.setSerializeNulls(previousSerializeNulls);
	}
	
}
