/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters;

import java.io.IOException;
import java.lang.reflect.Type;

import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageConstants;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.MethodProvider;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
public class MessageTypeAdapterFactory implements TypeAdapterFactory {
	
	private final MessageJsonHandler handler;
	
	public MessageTypeAdapterFactory(MessageJsonHandler handler) {
		this.handler = handler;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
		if (!Message.class.isAssignableFrom(typeToken.getRawType()))
			return null;
		return (TypeAdapter<T>) new Adapter(handler, gson);
	}
	
	private static class Adapter extends TypeAdapter<Message> {
		
		private final MessageJsonHandler handler;
		private final Gson gson;
		
		Adapter(MessageJsonHandler handler, Gson gson) {
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
			String jsonrpc = null, id = null, method = null;
			Object params = null, result = null;
			ResponseError error = null;
			while (in.hasNext()) {
				String name = in.nextName();
				switch (name) {
				case "jsonrpc": {
					jsonrpc = in.nextString();
					break;
				}
				case "id": {
					id = in.nextString();
					break;
				}
				case "method": {
					method = in.nextString();
					break;
				}
				case "params": {
					Type type = null;
					if (method != null) {
						JsonRpcMethod jsonRpcMethod = handler.getJsonRpcMethod(method);
						if (jsonRpcMethod != null)
							type = jsonRpcMethod.getParameterType();
					}
					if (type == null)
						params = new JsonParser().parse(in);
					else
						params = gson.fromJson(in, type);
					break;
				}
				case "result": {
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
			
			return createMessage(jsonrpc, id, method, params, result, error);
		}
		
		private Message createMessage(String jsonrpc, String id, String method, Object params, Object result, ResponseError error) {
			if (id != null && method != null) {
				RequestMessage message = new RequestMessage();
				message.setJsonrpc(jsonrpc);
				message.setId(id);
				message.setMethod(method);
				if (params instanceof JsonElement) {
					// Type of params could not be resolved - try again with the parsed JSON tree
					JsonRpcMethod jsonRpcMethod = handler.getJsonRpcMethod(method);
					if (jsonRpcMethod != null)
						params = gson.fromJson((JsonElement) params, jsonRpcMethod.getParameterType());
				}
				message.setParams(params);
				return message;
			} else if (id != null) {
				ResponseMessage message = new ResponseMessage();
				message.setJsonrpc(jsonrpc);
				message.setId(id);
				if (error != null) {
					message.setError(error);
				} else {
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
					message.setResult(result);
				}
				return message;
			} else if (method != null) {
				NotificationMessage message = new NotificationMessage();
				message.setJsonrpc(jsonrpc);
				message.setMethod(method);
				if (params instanceof JsonElement) {
					// Type of params could not be resolved - try again with the parsed JSON tree
					JsonRpcMethod jsonRpcMethod = handler.getJsonRpcMethod(method);
					if (jsonRpcMethod != null)
						params = gson.fromJson((JsonElement) params, jsonRpcMethod.getParameterType());
				}
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
				out.value(requestMessage.getId());
				out.name("method");
				out.value(requestMessage.getMethod());
				out.name("params");
				Object params = requestMessage.getParams();
				if (params == null)
					out.nullValue();
				else
					gson.toJson(params, params.getClass(), out);
			} else if (message instanceof ResponseMessage) {
				ResponseMessage responseMessage = (ResponseMessage) message;
				out.name("id");
				out.value(responseMessage.getId());
				if (responseMessage.getError() != null) {
					out.name("error");
					gson.toJson(responseMessage.getError(), ResponseError.class, out);
				} else {
					out.name("result");
					Object result = responseMessage.getResult();
					if (result == null)
						out.nullValue();
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
					out.nullValue();
				else
					gson.toJson(params, params.getClass(), out);
			}
			
			out.endObject();
		}
		
	}
}
