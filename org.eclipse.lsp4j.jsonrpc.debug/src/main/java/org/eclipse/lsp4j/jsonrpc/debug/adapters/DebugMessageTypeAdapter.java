/******************************************************************************
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.debug.adapters;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;

import org.eclipse.lsp4j.jsonrpc.MessageIssueException;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugNotificationMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugRequestMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugResponseMessage;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.MethodProvider;
import org.eclipse.lsp4j.jsonrpc.json.adapters.MessageTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.MessageIssue;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

/**
 * The {@link DebugMessageTypeAdapter} provides an adapter that maps Debug
 * Server Protocol style JSON to/from LSP4J's JSONRPC implementation. The Debug
 * Server Protocol (DSP) has its own message format that is quite similar to
 * JSON-RPC 2.0. The DSP is defined in a <a href=
 * "https://github.com/Microsoft/vscode-debugadapter-node/blob/master/debugProtocol.json">JSON
 * schema in the VS Code Debug Adapter</a>. This section documents how LSP4J's
 * jsonrpc classes maps to the Debug Protocol, using some extensions in the DSP
 * code to the lsp4j's {@link Message}s.
 * <p>
 *
 * <pre>
	"ProtocolMessage": { // implemented by {@link Message}
		"type": "object",
		"description": "Base class of requests, responses, and events.",
		"properties": {
			"seq": { // implemented by (depending on type, with conversion to/from String):
			         //  {@link DebugRequestMessage#getId()}, or
			         //  {@link DebugNotificationMessage#getId()} or
			         //  {@link DebugResponseMessage#getResponseId()}
				"type": "integer",
				"description": "Sequence number."
			},
			"type": { // implicit in type of subclass of {@link Message}
				"type": "string",
				"description": "Message type.",
				"_enum": [ "request", "response", "event" ]
			}
		},
		"required": [ "seq", "type" ]
	},

	"Request": { // implemented by {@link DebugRequestMessage}
		"allOf": [ { "$ref": "#/definitions/ProtocolMessage" }, {
			"type": "object",
			"description": "A client or server-initiated request.",
			"properties": {
				"type": { // implicit by being of type {@link DebugRequestMessage}
					"type": "string",
					"enum": [ "request" ]
				},
				"command": { // implemented by {@link DebugRequestMessage#getMethod()}
					"type": "string",
					"description": "The command to execute."
				},
				"arguments": { // implemented by {@link DebugRequestMessage#getParams()}
					"type": [ "array", "boolean", "integer", "null", "number" , "object", "string" ],
					"description": "Object containing arguments for the command."
				}
			},
			"required": [ "type", "command" ]
		}]
	},

	"Event": { // implemented by {@link DebugNotificationMessage}
		"allOf": [ { "$ref": "#/definitions/ProtocolMessage" }, {
			"type": "object",
			"description": "Server-initiated event.",
			"properties": {
				"type": { // implicit by being of type {@link DebugNotificationMessage}
					"type": "string",
					"enum": [ "event" ]
				},
				"event": { // implemented by {@link DebugNotificationMessage#getMethod()}
					"type": "string",
					"description": "Type of event."
				},
				"body": { // implemented by {@link DebugNotificationMessage#getParams()}
					"type": [ "array", "boolean", "integer", "null", "number" , "object", "string" ],
					"description": "Event-specific information."
				}
			},
			"required": [ "type", "event" ]
		}]
	},

	"Response": { // implemented by {@link DebugResponseMessage}
		"allOf": [ { "$ref": "#/definitions/ProtocolMessage" }, {
			"type": "object",
			"description": "Response to a request.",
			"properties": {
				"type": { // implicit by being of type {@link DebugResponseMessage}
					"type": "string",
					"enum": [ "response" ]
				},
				"request_seq": { // implemented by {@link DebugResponseMessage#getId()}
					"type": "integer",
					"description": "Sequence number of the corresponding request."
				},
				"success": { // implemented by {@link DebugResponseMessage#getError()} == null
					"type": "boolean",
					"description": "Outcome of the request."
				},
				"command": { // implemented by {@link DebugResponseMessage#getMethod()}
					"type": "string",
					"description": "The command requested."
				},
				"message": { // implemented by {@link ResponseError#getMessage()}
					"type": "string",
					"description": "Contains error message if success == false."
				},
				"body": { // implemented by {@link DebugResponseMessage#getResult()} for success and {@link ResponseError#getData()} for error
					"type": [ "array", "boolean", "integer", "null", "number" , "object", "string" ],
					"description": "Contains request result if success is true and optional error details if success is false."
				}
			},
			"required": [ "type", "request_seq", "success", "command" ]
		}]
	},
 * </pre>
 *
 */
public class DebugMessageTypeAdapter extends MessageTypeAdapter {

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
			return (TypeAdapter<T>) new DebugMessageTypeAdapter(handler, gson);
		}

	}

	private final MessageJsonHandler handler;
	private final Gson gson;

	public DebugMessageTypeAdapter(MessageJsonHandler handler, Gson gson) {
		super(handler, gson);
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
		String messageType = null, method = null, message = null;
		int seq = 0, request_seq = 0;
		Boolean rawSuccess = null;
		Object rawParams = null;
		Object rawBody = null;

		try {

			while (in.hasNext()) {
				String name = in.nextName();
				switch (name) {
				case "seq": {
					seq = in.nextInt();
					break;
				}
				case "request_seq": {
					// on responses we treat the request_seq as the id
					request_seq = in.nextInt();
					break;
				}
				case "type": {
					messageType = in.nextString();
					break;
				}
				case "success": {
					rawSuccess = in.nextBoolean();
					break;
				}
				case "command": {
					method = in.nextString();
					break;
				}
				case "event": {
					method = in.nextString();
					break;
				}
				case "message": {
					if (in.peek() == JsonToken.NULL) {
						in.nextNull();
					} else {
						message = in.nextString();
					}
					break;
				}
				case "arguments": {
					rawParams = parseParams(in, method);
					break;
				}
				case "body": {
					rawBody = parseBody(in, messageType, request_seq, method, rawSuccess);
					break;
				}
				default:
					in.skipValue();
				}
			}
			boolean success = rawSuccess != null ? rawSuccess : false;
			Object params = parseParams(rawParams, method);
			Object body = parseBody(rawBody, messageType, request_seq, method, success);

			in.endObject();
			return createMessage(messageType, seq, request_seq, method, success, message, params, body);

		} catch (JsonSyntaxException | MalformedJsonException | EOFException exception) {
			if ("request".equals(messageType) || "event".equals(messageType) || "response".equals(messageType)) {
				// Create a message and bundle it to an exception with an issue that wraps the original exception
				boolean success = rawSuccess != null ? rawSuccess : false;
				Message resultMessage = createMessage(messageType, seq, request_seq, method, success, message, rawParams, rawBody);
				MessageIssue issue = new MessageIssue("Message could not be parsed.", ResponseErrorCode.ParseError.getValue(), exception);
				throw new MessageIssueException(resultMessage, issue);
			} else {
				throw exception;
			}
		}
	}

	/**
	 * Convert the json input into the body object corresponding to the type of
	 * message.
	 *
	 * If the type of message or any other necessary field is not known until after
	 * parsing, call {@link #parseBody(Object, String, int, String, Boolean)} on
	 * the return value of this call for a second chance conversion.
	 *
	 * @param in
	 *            json input to read from
	 * @param messageType
	 *            message type if known
	 * @param request_seq
	 *            seq id of request message if known
	 * @param method
	 *            event/method being called
	 * @param success
	 *            if success of a response is known
	 * @return correctly typed object if the correct expected type can be
	 *         determined, or a JsonElement representing the body
	 */
	protected Object parseBody(JsonReader in, String messageType, int request_seq, String method, Boolean success)
			throws IOException {
		if ("event".equals(messageType)) {
			return parseParams(in, method);
		} else if ("response".equals(messageType) && success != null && success) {
			return super.parseResult(in, Integer.toString(request_seq));
		} else {
			return JsonParser.parseReader(in);
		}
	}

	/**
	 * Convert the JsonElement into the body object corresponding to the type of
	 * message. If the rawBody is already converted, does nothing.
	 *
	 * @param rawBody
	 *            json element to read from
	 * @param messageType
	 *            message type if known
	 * @param request_seq
	 *            seq id of request message if known
	 * @param method
	 *            event/method being called
	 * @param success
	 *            if success of a response is known
	 * @return correctly typed object if the correct expected type can be
	 *         determined, or rawBody unmodified if no conversion can be done.
	 */
	protected Object parseBody(Object rawBody, String messageType, int request_seq, String method, Boolean success) {
		if ("event".equals(messageType)) {
			return parseParams(rawBody, method);
		} else if ("response".equals(messageType)) {
			if (success != null && success) {
				return super.parseResult(rawBody, Integer.toString(request_seq));
			}
			if (isNull(rawBody)) {
				return null;
			}
			if (!(rawBody instanceof JsonElement)) {
				return rawBody;
			}
			JsonElement rawJsonParams = (JsonElement) rawBody;
			return fromJson(rawJsonParams, Object.class);
		}
		return rawBody;
	}

	private Message createMessage(String messageType, int seq, int request_seq, String method, boolean success,
			String errorMessage, Object params, Object body) throws JsonParseException {
		if (messageType == null) {
			throw new JsonParseException("Unable to identify the input message. Missing 'type' field.");
		}
		switch (messageType) {
		case "request": {
			DebugRequestMessage message = new DebugRequestMessage();
			message.setId(seq);
			message.setMethod(method);
			message.setParams(params);
			return message;
		}
		case "event": {
			DebugNotificationMessage message = new DebugNotificationMessage();
			message.setId(seq);
			message.setMethod(method);
			message.setParams(body);
			return message;
		}
		case "response": {
			DebugResponseMessage message = new DebugResponseMessage();
			message.setId(request_seq);
			message.setResponseId(seq);
			message.setMethod(method);
			if (!success) {
				ResponseError error = new ResponseError();
				error.setCode(ResponseErrorCode.UnknownErrorCode);
				error.setData(body);
				if (errorMessage == null) {
					// Some debug servers/clients don't provide a "message" field on an error.
					// Generally in those cases the body has some extra details to figure out
					// what went wrong.
					errorMessage = "Unset error message.";
				}
				error.setMessage(errorMessage);
				message.setError(error);
			} else {
				if (body instanceof JsonElement) {
					// Type of result could not be resolved - try again with the parsed JSON tree
					MethodProvider methodProvider = handler.getMethodProvider();
					if (methodProvider != null) {
						String resolvedMethod = methodProvider.resolveMethod(Integer.toString(request_seq));
						if (resolvedMethod != null) {
							JsonRpcMethod jsonRpcMethod = handler.getJsonRpcMethod(resolvedMethod);
							if (jsonRpcMethod != null) {
								TypeAdapter<?> typeAdapter = null;
								Type returnType = jsonRpcMethod.getReturnType();
								if (jsonRpcMethod.getReturnTypeAdapterFactory() != null)
									typeAdapter = jsonRpcMethod.getReturnTypeAdapterFactory().create(gson, TypeToken.get(returnType));
								JsonElement jsonElement = (JsonElement) body;
								if (typeAdapter != null)
									body = typeAdapter.fromJsonTree(jsonElement);
								else
									body = gson.fromJson(jsonElement, returnType);
							}
						}
					}
				}
				message.setResult(body);
			}
			return message;
		}
		default:
			throw new JsonParseException("Unable to identify the input message.");
		}
	}

	@Override
	public void write(JsonWriter out, Message message) throws IOException {
		out.beginObject();
		if (message instanceof DebugRequestMessage) {
			DebugRequestMessage requestMessage = (DebugRequestMessage) message;
			out.name("type");
			out.value("request");
			out.name("seq");
			writeIntId(out, requestMessage.getRawId());
			out.name("command");
			out.value(requestMessage.getMethod());
			Object params = requestMessage.getParams();
			if (params != null) {
				out.name("arguments");		
				gson.toJson(params, params.getClass(), out);
			}
		} else if (message instanceof DebugResponseMessage) {
			DebugResponseMessage responseMessage = (DebugResponseMessage) message;
			out.name("type");
			out.value("response");
			out.name("seq");
			writeIntId(out, responseMessage.getRawResponseId());
			out.name("request_seq");
			writeIntId(out, responseMessage.getRawId());
			out.name("command");
			out.value(responseMessage.getMethod());
			ResponseError error = responseMessage.getError();
			if (error != null) {
				out.name("success");
				out.value(false);
				String errorMessage = error.getMessage();
				out.name("message");
				if (errorMessage == null)
					writeNullValue(out);
				else
					gson.toJson(errorMessage, errorMessage.getClass(), out);

				Object errorData = error.getData();
				if (errorData != null) {
					out.name("body");
					gson.toJson(errorData, errorData.getClass(), out);
				}
			} else {
				out.name("success");
				out.value(true);
				Object result = responseMessage.getResult();
				if (result != null) {
					out.name("body");
					gson.toJson(result, result.getClass(), out);
				}
			}
		} else if (message instanceof DebugNotificationMessage) {
			DebugNotificationMessage notificationMessage = (DebugNotificationMessage) message;
			out.name("type");
			out.value("event");
			out.name("seq");
			writeIntId(out, notificationMessage.getRawId());
			out.name("event");
			out.value(notificationMessage.getMethod());
			Object params = notificationMessage.getParams();
			if (params != null) {
				out.name("body");
				gson.toJson(params, params.getClass(), out);
			}
		}

		out.endObject();
	}

	private void writeIntId(JsonWriter out, Either<String, Number> id) throws IOException {
		if (id == null)
			writeNullValue(out);
		else if (id.isLeft())
			out.value(Integer.parseInt(id.getLeft()));
		else if (id.isRight())
			out.value(id.getRight());
	}
}
