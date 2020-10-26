/******************************************************************************
 * Copyright (c) 2016-2019 TypeFox and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test;

import org.eclipse.lsp4j.jsonrpc.*;
import org.eclipse.lsp4j.jsonrpc.TracingMessageConsumer.RequestMetadata;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageConsumer;
import org.eclipse.lsp4j.jsonrpc.messages.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;

public class TracingMessageConsumerTest {
	private static final RemoteEndpoint TEST_REMOTE_ENDPOINT = new EmptyRemoteEndpoint();
	private static final StreamMessageConsumer TEST_STREAM_MESSAGE_CONSUMER =
			new StreamMessageConsumer(
					new ByteArrayOutputStream(), new MessageJsonHandler(emptyMap()));
	private static final Clock TEST_CLOCK_1 =
			Clock.fixed(Instant.parse("2019-06-26T22:07:30.00Z"), ZoneId.of("America/New_York"));
	private static final Clock TEST_CLOCK_2 =
			Clock.fixed(Instant.parse("2019-06-26T22:07:30.10Z"), ZoneId.of("America/New_York"));

	@Test
	public void testReceivedRequest() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		TracingMessageConsumer consumer =
				new TracingMessageConsumer(
						TEST_REMOTE_ENDPOINT, new HashMap<>(), new HashMap<>(), printWriter, TEST_CLOCK_1, Locale.US);

		RequestMessage message = new RequestMessage();
		message.setId("1");
		message.setMethod("foo");
		message.setParams("bar");

		consumer.consume(message);

		String actualTrace = stringWriter.toString();
		String expectedTrace = "" +
				"[Trace - 06:07:30 PM] Received request 'foo - (1)'\n" +
				"Params: \"bar\"\n" +
				"\n" +
				"\n";

		assertEquals(expectedTrace, actualTrace);
	}

	@Test
	public void testReceivedResultResponse() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Map<String, RequestMetadata> sentRequests = new HashMap<>();
		sentRequests.put("1", new RequestMetadata("foo", TEST_CLOCK_1.instant()));

		TracingMessageConsumer consumer =
				new TracingMessageConsumer(
						TEST_REMOTE_ENDPOINT, sentRequests, new HashMap<>(), printWriter, TEST_CLOCK_2, Locale.US);

		ResponseMessage message = new ResponseMessage();
		message.setId("1");
		message.setResult("bar");

		consumer.consume(message);

		String actualTrace = stringWriter.toString();
		String expectedTrace = "" +
				"[Trace - 06:07:30 PM] Received response 'foo - (1)' in 100ms\n" +
				"Result: \"bar\"\n" +
				"Error: null\n" +
				"\n" +
				"\n";

		assertEquals(expectedTrace, actualTrace);
	}

	@Test
	public void testReceivedErrorResponse() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Map<String, RequestMetadata> sentRequests = new HashMap<>();
		sentRequests.put("1", new RequestMetadata("foo", TEST_CLOCK_1.instant()));

		TracingMessageConsumer consumer =
				new TracingMessageConsumer(
						TEST_REMOTE_ENDPOINT, sentRequests, new HashMap<>(), printWriter, TEST_CLOCK_2, Locale.US);

		ResponseMessage message = new ResponseMessage();
		message.setId("1");
		message.setError(new ResponseError(-32600, "bar", null));

		consumer.consume(message);

		String actualTrace = stringWriter.toString();
		String expectedTrace = "" +
				"[Trace - 06:07:30 PM] Received response 'foo - (1)' in 100ms\n" +
				"Result: null\n" +
				"Error: {\n" +
				"  \"code\": -32600,\n" +
				"  \"message\": \"bar\"\n" +
				"}\n" +
				"\n" +
				"\n";

		assertEquals(expectedTrace, actualTrace);
	}

	@Test
	public void testReceivedNotification() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		TracingMessageConsumer consumer =
				new TracingMessageConsumer(
						TEST_REMOTE_ENDPOINT, new HashMap<>(), new HashMap<>(), printWriter, TEST_CLOCK_1, Locale.US);

		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		message.setParams("bar");

		consumer.consume(message);

		String actualTrace = stringWriter.toString();
		String expectedTrace = "" +
				"[Trace - 06:07:30 PM] Received notification 'foo'\n" +
				"Params: \"bar\"\n" +
				"\n" +
				"\n";

		assertEquals(expectedTrace, actualTrace);
	}

	@Test
	public void testSendingRequest() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		TracingMessageConsumer consumer =
				new TracingMessageConsumer(
						TEST_STREAM_MESSAGE_CONSUMER, new HashMap<>(), new HashMap<>(), printWriter, TEST_CLOCK_1, Locale.US);

		RequestMessage message = new RequestMessage();
		message.setId("1");
		message.setMethod("foo");
		message.setParams("bar");

		consumer.consume(message);

		String actualTrace = stringWriter.toString();
		String expectedTrace = "" +
				"[Trace - 06:07:30 PM] Sending request 'foo - (1)'\n" +
				"Params: \"bar\"\n" +
				"\n" +
				"\n";

		assertEquals(expectedTrace, actualTrace);
	}

	@Test
	public void testSendingResponse() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Map<String, RequestMetadata> receivedRequests = new HashMap<>();
		receivedRequests.put("1", new RequestMetadata("foo", TEST_CLOCK_1.instant()));

		TracingMessageConsumer consumer =
				new TracingMessageConsumer(
						TEST_STREAM_MESSAGE_CONSUMER, new HashMap<>(), receivedRequests, printWriter, TEST_CLOCK_2, Locale.US);

		ResponseMessage message = new ResponseMessage();
		message.setId("1");
		message.setResult("bar");

		consumer.consume(message);

		String actualTrace = stringWriter.toString();
		String expectedTrace = "" +
				"[Trace - 06:07:30 PM] Sending response 'foo - (1)'. Processing request took 100ms\n" +
				"Result: \"bar\"\n" +
				"\n" +
				"\n";

		assertEquals(expectedTrace, actualTrace);
	}

	@Test
	public void testSendingNotification() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		TracingMessageConsumer consumer =
				new TracingMessageConsumer(
						TEST_STREAM_MESSAGE_CONSUMER, emptyMap(), emptyMap(), printWriter, TEST_CLOCK_1, Locale.US);

		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		message.setParams("bar");

		consumer.consume(message);

		String actualTrace = stringWriter.toString();
		String expectedTrace = "" +
				"[Trace - 06:07:30 PM] Sending notification 'foo'\n" +
				"Params: \"bar\"\n" +
				"\n" +
				"\n";

		assertEquals(expectedTrace, actualTrace);
	}
}

class EmptyRemoteEndpoint extends RemoteEndpoint {
	EmptyRemoteEndpoint() {
		super(new EmptyMessageConsumer(), new EmptyEndpoint());
	}

	@Override
	protected void handleResponse(ResponseMessage responseMessage) {
		// no-op
	}
}

class EmptyMessageConsumer implements MessageConsumer {
	@Override
	public void consume(Message message) throws MessageIssueException, JsonRpcException {
		// no-op
	}
}

class EmptyEndpoint implements Endpoint {
	@Override
	public CompletableFuture<?> request(String method, Object parameter) {
		return CompletableFuture.completedFuture(new Object());
	}

	@Override
	public void notify(String method, Object parameter) {
		// no-op
	}
}
