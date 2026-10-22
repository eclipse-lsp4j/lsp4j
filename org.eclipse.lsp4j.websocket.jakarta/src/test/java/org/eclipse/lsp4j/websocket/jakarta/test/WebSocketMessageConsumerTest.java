/******************************************************************************
 * Copyright (c) 2026 Marcus Lagergren and others.
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

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.SendHandler;
import jakarta.websocket.SendResult;

import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.websocket.jakarta.WebSocketMessageConsumer;
import org.junit.Assert;
import org.junit.Test;

public class WebSocketMessageConsumerTest {

	private static final int ROUNDS = 100;
	private static final int THREADS = 8;
	private static final int MESSAGES_PER_THREAD = 200;

	/**
	 * The consumer sends one message at a time, and each thread that queues a message tries to send the next
	 * one. A thread could see a message in the queue that another thread then sent, and send {@code null} in its
	 * place. {@code sendText} rejects {@code null}, so nothing was sent again. A session that notifies the send
	 * handler before {@code sendText} returns makes this likely within a few rounds.
	 */
	@Test
	public void testMessagesFromManyThreadsAreAllSent() throws Exception {
		for (int round = 0; round < ROUNDS; round++) {
			final var session = new InlineSendSession();
			final var consumer = new WebSocketMessageConsumer(session, new MessageJsonHandler(Collections.emptyMap()));
			final var executor = Executors.newFixedThreadPool(THREADS);
			try {
				final var senders = IntStream.range(0, THREADS)
						.mapToObj(thread -> CompletableFuture.runAsync(() -> sendMessages(consumer), executor))
						.toArray(CompletableFuture<?>[]::new);
				CompletableFuture.allOf(senders).get(10, TimeUnit.SECONDS);
			} finally {
				executor.shutdown();
			}
			Assert.assertEquals("messages sent in round " + round, THREADS * MESSAGES_PER_THREAD, session.sentMessages.get());
		}
	}

	private static void sendMessages(WebSocketMessageConsumer consumer) {
		for (int i = 0; i < MESSAGES_PER_THREAD; i++) {
			final var message = new NotificationMessage();
			message.setMethod("foo");
			consumer.consume(message);
		}
	}

	/**
	 * Counts the text messages sent asynchronously, and notifies the send handler before {@code sendText} returns.
	 */
	private static class InlineSendSession extends MockSession {

		final AtomicInteger sentMessages = new AtomicInteger();
		private final RemoteEndpoint.Async asyncRemote = new InlineAsyncRemote();

		@Override
		public RemoteEndpoint.Async getAsyncRemote() {
			return asyncRemote;
		}

		private class InlineAsyncRemote extends AbstractRemoteEndpoint implements RemoteEndpoint.Async {

			@Override
			public void sendText(String text, SendHandler handler) {
				// As RemoteEndpoint.Async specifies
				if (text == null) {
					throw new IllegalArgumentException("The text is null.");
				}
				sentMessages.incrementAndGet();
				handler.onResult(new SendResult());
			}

			@Override
			public long getSendTimeout() {
				return 1000;
			}

			@Override
			public void setSendTimeout(long timeoutmillis) {
			}

			@Override
			public Future<Void> sendText(String text) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Future<Void> sendBinary(ByteBuffer data) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void sendBinary(ByteBuffer data, SendHandler handler) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Future<Void> sendObject(Object data) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void sendObject(Object data, SendHandler handler) {
				throw new UnsupportedOperationException();
			}

		}

	}

}
