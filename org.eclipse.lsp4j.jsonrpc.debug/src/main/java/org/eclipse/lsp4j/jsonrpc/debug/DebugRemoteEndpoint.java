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
package org.eclipse.lsp4j.jsonrpc.debug;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.JsonRpcRequestFuture;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugNotificationMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugRequestMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugResponseMessage;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.CancelParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * A {@link RemoteEndpoint} for the Debug Adapter Protocol, which requires the {@code seq} of each message
 * that an endpoint sends to be one greater than the {@code seq} of the message it sent before.
 * <p>
 * A notification or a response takes its {@code seq} when it is sent, and a request takes it when it is
 * created, since {@link RemoteEndpoint} records the request by its {@code seq} before sending it. Either
 * way, no other message is sent in between, so messages that several threads send at once still go out
 * in {@code seq} order.
 * <p>
 * A request is cancelled with the Debug Adapter Protocol's {@code cancel} request, whose {@code requestId}
 * is the {@code seq} of the request to cancel.
 */
public class DebugRemoteEndpoint extends RemoteEndpoint {
	private static final String CANCEL_REQUEST = "cancel";
	private static final String REQUEST_ID = "requestId";

	private final SequencingMessageConsumer sequencer;

	public DebugRemoteEndpoint(MessageConsumer out, Endpoint localEndpoint) {
		this(new SequencingMessageConsumer(out), localEndpoint, DEFAULT_EXCEPTION_HANDLER);
	}

	public DebugRemoteEndpoint(MessageConsumer out, Endpoint localEndpoint,
			Function<Throwable, ResponseError> exceptionHandler) {
		this(new SequencingMessageConsumer(out), localEndpoint, exceptionHandler);
	}

	private DebugRemoteEndpoint(SequencingMessageConsumer sequencer, Endpoint localEndpoint,
			Function<Throwable, ResponseError> exceptionHandler) {
		super(sequencer, localEndpoint, exceptionHandler);
		this.sequencer = sequencer;
	}

	@Override
	public JsonRpcRequestFuture<Object> request(String method, Object parameter) {
		// The request takes its seq when it is created, so no other message may take one until it has been sent
		synchronized (sequencer) {
			return super.request(method, parameter);
		}
	}

	/**
	 * Cancels a request that the remote endpoint handles, with a {@code cancel} request. The Debug Adapter
	 * Protocol has no {@code $/cancelRequest} notification, which would go out as an event.
	 */
	@Override
	protected void sendCancelNotification(Either<String, Number> id) {
		request(CANCEL_REQUEST, Map.of(REQUEST_ID, id.isRight() ? id.getRight() : id.getLeft()));
	}

	/**
	 * Cancels the request that a {@code cancel} request names, if the local endpoint is still handling it, as
	 * {@link RemoteEndpoint} does for the {@code $/cancelRequest} notification. The {@code cancel} request then
	 * goes to the local endpoint like any other, so an endpoint that implements it still answers it.
	 */
	@Override
	protected void handleRequest(RequestMessage requestMessage) {
		if (CANCEL_REQUEST.equals(requestMessage.getMethod())) {
			String requestId = requestId(requestMessage.getParams());
			if (requestId != null) {
				CancelParams cancelParams = new CancelParams();
				cancelParams.setId(requestId);
				NotificationMessage cancellation = new NotificationMessage();
				cancellation.setMethod(MessageJsonHandler.CANCEL_METHOD.getMethodName());
				cancellation.setParams(cancelParams);
				handleCancellation(cancellation);
			}
		}
		super.handleRequest(requestMessage);
	}

	/**
	 * Returns the {@code requestId} of the arguments of a {@code cancel} request, and null if they have none.
	 * The arguments are a {@link JsonElement} when the local endpoint does not implement {@code cancel}.
	 */
	private String requestId(Object arguments) {
		if (arguments == null) {
			return null;
		}
		JsonElement json = arguments instanceof JsonElement ? (JsonElement) arguments : new Gson().toJsonTree(arguments);
		if (!json.isJsonObject()) {
			return null;
		}
		JsonElement requestId = ((JsonObject) json).get(REQUEST_ID);
		return requestId != null && requestId.isJsonPrimitive() ? requestId.getAsString() : null;
	}

	@Override
	protected DebugRequestMessage createRequestMessage(String method, Object parameter) {
		DebugRequestMessage requestMessage = new DebugRequestMessage();
		requestMessage.setJsonHandler(getJsonHandler());
		requestMessage.setId(sequencer.nextSeq());
		requestMessage.setMethod(method);
		requestMessage.setParams(parameter);
		return requestMessage;
	}

	@Override
	protected DebugResponseMessage createResponseMessage(RequestMessage requestMessage) {
		// The sequencer sets the seq of the response when it sends it
		DebugResponseMessage responseMessage = new DebugResponseMessage();
		responseMessage.setJsonHandler(getJsonHandler());
		responseMessage.setRawId(requestMessage.getRawId());
		responseMessage.setMethod(requestMessage.getMethod());
		return responseMessage;
	}

	@Override
	protected DebugNotificationMessage createNotificationMessage(String method, Object parameter) {
		// The sequencer sets the seq of the notification when it sends it
		DebugNotificationMessage notificationMessage = new DebugNotificationMessage();
		notificationMessage.setJsonHandler(getJsonHandler());
		notificationMessage.setMethod(method);
		notificationMessage.setParams(parameter);
		return notificationMessage;
	}

	/**
	 * Sends the messages of a {@link DebugRemoteEndpoint} one at a time, and gives each notification and
	 * response the next {@code seq} as it sends it.
	 */
	private static class SequencingMessageConsumer implements MessageConsumer {
		private final MessageConsumer out;

		// The seq of the last message, guarded by this
		private int seq;

		SequencingMessageConsumer(MessageConsumer out) {
			this.out = Objects.requireNonNull(out, "out");
		}

		synchronized int nextSeq() {
			return ++seq;
		}

		@Override
		public synchronized void consume(Message message) {
			if (message instanceof DebugNotificationMessage) {
				((DebugNotificationMessage) message).setId(nextSeq());
			} else if (message instanceof DebugResponseMessage) {
				((DebugResponseMessage) message).setResponseId(nextSeq());
			}
			out.consume(message);
		}
	}
}
