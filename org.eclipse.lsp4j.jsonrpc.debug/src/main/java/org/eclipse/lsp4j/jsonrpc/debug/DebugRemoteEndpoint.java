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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugNotificationMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugRequestMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugResponseMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;

public class DebugRemoteEndpoint extends RemoteEndpoint {
	private final AtomicInteger nextSeqId = new AtomicInteger();

	public DebugRemoteEndpoint(MessageConsumer out, Endpoint localEndpoint) {
		super(out, localEndpoint);
	}

	public DebugRemoteEndpoint(MessageConsumer out, Endpoint localEndpoint,
			Function<Throwable, ResponseError> exceptionHandler) {
		super(out, localEndpoint, exceptionHandler);
	}

	@Override
	protected DebugRequestMessage createRequestMessage(String method, Object parameter) {
		DebugRequestMessage requestMessage = new DebugRequestMessage();
		requestMessage.setId(nextSeqId.incrementAndGet());
		requestMessage.setMethod(method);
		requestMessage.setParams(parameter);
		return requestMessage;
	}

	@Override
	protected DebugResponseMessage createResponseMessage(RequestMessage requestMessage) {
		DebugResponseMessage responseMessage = new DebugResponseMessage();
		responseMessage.setResponseId(nextSeqId.incrementAndGet());
		responseMessage.setRawId(requestMessage.getRawId());
		responseMessage.setMethod(requestMessage.getMethod());
		return responseMessage;
	}

	@Override
	protected DebugNotificationMessage createNotificationMessage(String method, Object parameter) {
		DebugNotificationMessage notificationMessage = new DebugNotificationMessage();
		notificationMessage.setId(nextSeqId.incrementAndGet());
		notificationMessage.setMethod(method);
		notificationMessage.setParams(parameter);
		return notificationMessage;
	}
}
