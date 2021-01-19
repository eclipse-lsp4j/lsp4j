/******************************************************************************
 * Copyright (c) 2019, 2021 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.websocket.jakarta.test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import jakarta.websocket.CloseReason;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Endpoint;
import jakarta.websocket.Extension;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.MessageHandler.Partial;
import jakarta.websocket.MessageHandler.Whole;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.SendHandler;
import jakarta.websocket.SendResult;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

public class MockSession implements Session {
	
	public static final int MAX_CHUNK_SIZE = 100;
	
	private final BasicRemote basicRemote = new BasicRemote();
	private final AsyncRemote asyncRemote = new AsyncRemote();
	private final Set<MessageHandler> messageHandlers = new HashSet<>();
	private Endpoint endpoint;
	private MockSession connectedSession;
	private boolean isClosed;
	private StringBuilder partialMessage;
	
	public void connect(MockSession other) {
		this.connectedSession = other;
		other.connectedSession = this;
	}

	@Override
	public RemoteEndpoint.Async getAsyncRemote() {
		return asyncRemote;
	}

	@Override
	public RemoteEndpoint.Basic getBasicRemote() {
		return basicRemote;
	}
	
	public void open(Endpoint endpoint) {
		this.endpoint = endpoint;
		endpoint.onOpen(this, new MockEndpointConfig());
	}

	@Override
	public void close() throws IOException {
		close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "OK"));
	}

	@Override
	public void close(CloseReason closeReason) throws IOException {
		isClosed = true;
		endpoint.onClose(this, closeReason);
	}

	@Override
	public void addMessageHandler(MessageHandler handler) throws IllegalStateException {
		if (!messageHandlers.add(handler))
			throw new IllegalStateException();
	}
	

	@Override
	public <T> void addMessageHandler(Class<T> clazz, Whole<T> handler) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> void addMessageHandler(Class<T> clazz, Partial<T> handler) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<MessageHandler> getMessageHandlers() {
		return messageHandlers;
	}

	@Override
	public void removeMessageHandler(MessageHandler handler) {
		messageHandlers.remove(handler);
	}
	
	@SuppressWarnings("unchecked")
	protected void dispatch(String message, boolean lastChunk) {
		if (lastChunk) {
			String wholeMessage = message;
			if (partialMessage != null) {
				partialMessage.append(message);
				wholeMessage = partialMessage.toString();
				partialMessage = null;
			}
			for (MessageHandler h : connectedSession.messageHandlers) {
				if (h instanceof MessageHandler.Whole<?>)
					((MessageHandler.Whole<String>) h).onMessage(wholeMessage);
				else
					((MessageHandler.Partial<String>) h).onMessage(message, true);
			};
		} else {
			if (partialMessage == null) {
				partialMessage = new StringBuilder();
			}
			for (MessageHandler h : connectedSession.messageHandlers) {
				if (h instanceof MessageHandler.Partial<?>)
					((MessageHandler.Partial<String>) h).onMessage(message, false);
			};
			partialMessage.append(message);
		}
	}

	@Override
	public WebSocketContainer getContainer() {
		return null;
	}

	@Override
	public String getProtocolVersion() {
		return "13";
	}

	@Override
	public String getNegotiatedSubprotocol() {
		return null;
	}

	@Override
	public List<Extension> getNegotiatedExtensions() {
		return Collections.emptyList();
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public boolean isOpen() {
		return !isClosed;
	}

	@Override
	public long getMaxIdleTimeout() {
		return 10000;
	}

	@Override
	public void setMaxIdleTimeout(long milliseconds) {
	}

	@Override
	public void setMaxBinaryMessageBufferSize(int length) {
	}

	@Override
	public int getMaxBinaryMessageBufferSize() {
		return 100;
	}

	@Override
	public void setMaxTextMessageBufferSize(int length) {
	}

	@Override
	public int getMaxTextMessageBufferSize() {
		return MAX_CHUNK_SIZE;
	}

	@Override
	public String getId() {
		return "mock";
	}

	@Override
	public URI getRequestURI() {
		try {
			return new URI("http://localhost:8080/mock");
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, List<String>> getRequestParameterMap() {
		return Collections.emptyMap();
	}

	@Override
	public String getQueryString() {
		return "";
	}

	@Override
	public Map<String, String> getPathParameters() {
		return Collections.emptyMap();
	}

	@Override
	public Map<String, Object> getUserProperties() {
		return Collections.emptyMap();
	}

	@Override
	public Principal getUserPrincipal() {
		return null;
	}

	@Override
	public Set<Session> getOpenSessions() {
		return Collections.singleton(this);
	}
	
	private class BasicRemote extends AbstractRemoteEndpoint implements RemoteEndpoint.Basic {

		@Override
		public void sendText(String text) throws IOException {
			dispatch(text, true);
		}

		@Override
		public void sendBinary(ByteBuffer data) throws IOException {
		}

		@Override
		public void sendText(String partialMessage, boolean isLast) throws IOException {
			dispatch(partialMessage, isLast);
		}

		@Override
		public void sendBinary(ByteBuffer partialByte, boolean isLast) throws IOException {
		}

		@Override
		public OutputStream getSendStream() throws IOException {
			return null;
		}

		@Override
		public Writer getSendWriter() throws IOException {
			return null;
		}

		@Override
		public void sendObject(Object data) throws IOException, EncodeException {
		}
		
	}
	
	private class AsyncRemote extends AbstractRemoteEndpoint implements RemoteEndpoint.Async {

		@Override
		public long getSendTimeout() {
			return 1000;
		}

		@Override
		public void setSendTimeout(long timeoutmillis) {
		}

		@Override
		public void sendText(String text, SendHandler handler) {
			sendText(text).thenRun(() -> {
				handler.onResult(new SendResult());
			});
		}

		@Override
		public CompletableFuture<Void> sendText(String text) {
			return CompletableFuture.runAsync(() -> {
				dispatch(text, true);
			});
		}

		@Override
		public Future<Void> sendBinary(ByteBuffer data) {
			return null;
		}

		@Override
		public void sendBinary(ByteBuffer data, SendHandler handler) {
		}

		@Override
		public Future<Void> sendObject(Object data) {
			return null;
		}

		@Override
		public void sendObject(Object data, SendHandler handler) {
		}
		
	}
	
	private static abstract class AbstractRemoteEndpoint implements RemoteEndpoint {

		@Override
		public void setBatchingAllowed(boolean allowed) throws IOException {
		}

		@Override
		public boolean getBatchingAllowed() {
			return false;
		}

		@Override
		public void flushBatch() throws IOException {
		}

		@Override
		public void sendPing(ByteBuffer applicationData) throws IOException, IllegalArgumentException {
		}

		@Override
		public void sendPong(ByteBuffer applicationData) throws IOException, IllegalArgumentException {
		}
		
	}

}
