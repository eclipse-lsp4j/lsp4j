/******************************************************************************
 * Copyright (c) 2016-2017 TypeFox and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.debug.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.debug.DebugRemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugNotificationMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugRequestMessage;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugResponseMessage;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.junit.Test;

import com.google.gson.JsonObject;

public class DebugRemoteEndpointTest {

	static class TestEndpoint implements Endpoint {

		List<NotificationMessage> notifications = new ArrayList<>();
		Map<RequestMessage, CompletableFuture<Object>> requests = new LinkedHashMap<>();

		@Override
		public void notify(String method, Object parameter) {
			notifications.add(new NotificationMessage() {{
				setMethod(method);
				setParams(parameter);
			}});
		}

		@Override
		public CompletableFuture<Object> request(String method, Object parameter) {
			final var completableFuture = new CompletableFuture<>();
			requests.put(new RequestMessage() {{
				setMethod(method);
				setParams(parameter);
			}}, completableFuture);
			return completableFuture;
		}

	}

	static class TestMessageConsumer implements MessageConsumer {

		List<Message> messages = new ArrayList<>();

		@Override
		public void consume(Message message) {
			messages.add(message);
		}

	}

	/**
	 * Answers each request at once.
	 */
	static class AnsweringEndpoint implements Endpoint {

		@Override
		public void notify(String method, Object parameter) {
		}

		@Override
		public CompletableFuture<?> request(String method, Object parameter) {
			return CompletableFuture.completedFuture("success");
		}

	}

	/**
	 * Records the seq of each message it is given. It holds the first message until it is given another one,
	 * or for half a second, so that an endpoint that does not send its messages in seq order sends the other
	 * one meanwhile.
	 */
	static class SeqRecordingConsumer implements MessageConsumer {

		List<String> seqs = Collections.synchronizedList(new ArrayList<>());
		CompletableFuture<Void> firstMessage = new CompletableFuture<>();
		CompletableFuture<Void> secondMessage = new CompletableFuture<>();

		@Override
		public void consume(Message message) {
			if (firstMessage.complete(null)) {
				secondMessage.completeOnTimeout(null, 500, TimeUnit.MILLISECONDS).join();
				seqs.add(seq(message));
			} else {
				seqs.add(seq(message));
				secondMessage.complete(null);
			}
		}

		private static String seq(Message message) {
			if (message instanceof DebugRequestMessage) {
				return ((DebugRequestMessage) message).getId();
			} else if (message instanceof DebugNotificationMessage) {
				return ((DebugNotificationMessage) message).getId();
			} else {
				return ((DebugResponseMessage) message).getResponseId();
			}
		}

	}

	@Test public void testNotification() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new DebugRemoteEndpoint(consumer, endp);

		endpoint.consume(new NotificationMessage() {{
			setMethod("foo");
			setParams("myparam");
		}});

		NotificationMessage notificationMessage = endp.notifications.get(0);
		assertEquals("foo", notificationMessage.getMethod());
		assertEquals("myparam", notificationMessage.getParams());
		assertTrue(consumer.messages.isEmpty());
	}

	@Test public void testRequest() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new DebugRemoteEndpoint(consumer, endp);

		endpoint.consume(new RequestMessage() {{
			setId("1");
			setMethod("foo");
			setParams("myparam");
		}});

		Entry<RequestMessage, CompletableFuture<Object>> entry = endp.requests.entrySet().iterator().next();
		entry.getValue().complete("success");
		assertEquals("foo", entry.getKey().getMethod());
		assertEquals("myparam", entry.getKey().getParams());
		assertEquals("success", ((ResponseMessage)consumer.messages.get(0)).getResult());
	}

	@Test public void testCancellation() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new DebugRemoteEndpoint(consumer, endp);

		endpoint.consume(new RequestMessage() {{
			setId("1");
			setMethod("foo");
			setParams("myparam");
		}});

		Entry<RequestMessage, CompletableFuture<Object>> entry = endp.requests.entrySet().iterator().next();
		entry.getValue().cancel(true);
		ResponseMessage message = (ResponseMessage) consumer.messages.get(0);
		assertNotNull(message);
		ResponseError error = message.getError();
		assertNotNull(error);
		assertEquals(error.getCode(), ResponseErrorCode.RequestCancelled.getValue());
		assertEquals(error.getMessage(), "The request (id: 1, method: 'foo') has been cancelled");

	}

	@Test public void testCancellingARequestSendsACancelRequest() {
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new DebugRemoteEndpoint(consumer, new TestEndpoint());

		CompletableFuture<Object> result = endpoint.request("foo", "myparam");
		result.cancel(true);

		assertEquals(2, consumer.messages.size());
		DebugRequestMessage request = (DebugRequestMessage) consumer.messages.get(0);
		DebugRequestMessage cancel = (DebugRequestMessage) consumer.messages.get(1);
		assertEquals("cancel", cancel.getMethod());
		assertEquals(Map.of("requestId", Integer.valueOf(request.getId())), cancel.getParams());
	}

	@Test public void testCancelRequestCancelsTheRequestItNames() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new DebugRemoteEndpoint(consumer, endp);

		endpoint.consume(new DebugRequestMessage() {{
			setId(1);
			setMethod("foo");
			setParams("myparam");
		}});
		JsonObject arguments = new JsonObject();
		arguments.addProperty("requestId", 1);
		endpoint.consume(new DebugRequestMessage() {{
			setId(2);
			setMethod("cancel");
			setParams(arguments);
		}});

		CompletableFuture<Object> foo = endp.requests.values().iterator().next();
		assertTrue(foo.isCancelled());
		DebugResponseMessage response = (DebugResponseMessage) consumer.messages.get(0);
		assertEquals("1", response.getId());
		assertEquals(ResponseErrorCode.RequestCancelled.getValue(), response.getError().getCode());
		// the cancel request still reaches the local endpoint, which answers it
		Entry<RequestMessage, CompletableFuture<Object>> cancel = List.copyOf(endp.requests.entrySet()).get(1);
		assertEquals("cancel", cancel.getKey().getMethod());
		assertSame(arguments, cancel.getKey().getParams());
	}

	@Test public void testNotificationAndRequestAreSentInSeqOrder() throws Exception {
		assertSentInSeqOrder(DebugRemoteEndpointTest::sendNotification, DebugRemoteEndpointTest::sendRequest);
	}

	@Test public void testRequestAndResponseAreSentInSeqOrder() throws Exception {
		assertSentInSeqOrder(DebugRemoteEndpointTest::sendRequest, DebugRemoteEndpointTest::sendResponse);
	}

	@Test public void testResponseAndNotificationAreSentInSeqOrder() throws Exception {
		assertSentInSeqOrder(DebugRemoteEndpointTest::sendResponse, DebugRemoteEndpointTest::sendNotification);
	}

	/**
	 * Sends the second message from this thread while another thread is sending the first one, which has
	 * taken seq 1 by then.
	 */
	private static void assertSentInSeqOrder(Consumer<RemoteEndpoint> first, Consumer<RemoteEndpoint> second) throws Exception {
		final var consumer = new SeqRecordingConsumer();
		final var endpoint = new DebugRemoteEndpoint(consumer, new AnsweringEndpoint());

		final var firstSent = CompletableFuture.runAsync(() -> first.accept(endpoint));
		consumer.firstMessage.get(5, TimeUnit.SECONDS);
		second.accept(endpoint);
		firstSent.get(5, TimeUnit.SECONDS);

		assertEquals(List.of("1", "2"), consumer.seqs);
	}

	private static void sendRequest(RemoteEndpoint endpoint) {
		endpoint.request("foo", "myparam");
	}

	private static void sendNotification(RemoteEndpoint endpoint) {
		endpoint.notify("foo", "myparam");
	}

	private static void sendResponse(RemoteEndpoint endpoint) {
		// AnsweringEndpoint answers the request at once, on this thread
		endpoint.consume(new DebugRequestMessage() {{
			setId(7);
			setMethod("foo");
			setParams("myparam");
		}});
	}

}
