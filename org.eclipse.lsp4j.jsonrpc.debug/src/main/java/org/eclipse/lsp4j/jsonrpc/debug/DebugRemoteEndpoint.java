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

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugNotificationMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugRequestMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugResponseMessage;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;

/**
 * A {@link RemoteEndpoint} for the Debug Adapter Protocol, which requires the {@code seq} of each message
 * that an endpoint sends to be one greater than the {@code seq} of the message it sent before.
 * <p>
 * Each message takes its {@code seq} when it is sent, and no other message is sent in between, so messages
 * that several threads send at once still go out in {@code seq} order. A request is recorded by its
 * {@code seq} after it takes it and before it is sent, so that its response can be handled.
 */
public class DebugRemoteEndpoint extends RemoteEndpoint {
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
	protected void sendRequest(RequestMessage requestMessage, Runnable recordRequest) {
		sequencer.send(requestMessage, recordRequest);
	}

	@Override
	protected DebugRequestMessage createRequestMessage(String method, Object parameter) {
		// The sequencer sets the seq of the request when it sends it
		DebugRequestMessage requestMessage = new DebugRequestMessage();
		requestMessage.setJsonHandler(getJsonHandler());
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
	 * Sends the messages of a {@link DebugRemoteEndpoint} one at a time, and gives each one the next
	 * {@code seq} as it sends it.
	 */
	private static class SequencingMessageConsumer implements MessageConsumer {
		private final MessageConsumer out;

		// The seq of the last message, guarded by this
		private int seq;

		SequencingMessageConsumer(MessageConsumer out) {
			this.out = Objects.requireNonNull(out, "out");
		}

		/**
		 * Sends a request, after running {@code recordRequest} once the request has its {@code seq}.
		 */
		synchronized void send(RequestMessage requestMessage, Runnable recordRequest) {
			requestMessage.setId(++seq);
			recordRequest.run();
			out.consume(requestMessage);
		}

		@Override
		public synchronized void consume(Message message) {
			if (message instanceof DebugNotificationMessage) {
				((DebugNotificationMessage) message).setId(++seq);
			} else if (message instanceof DebugResponseMessage) {
				((DebugResponseMessage) message).setResponseId(++seq);
			}
			out.consume(message);
		}
	}
}
