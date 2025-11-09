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

import static java.util.Collections.emptyMap;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.JsonRpcException;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.MessageIssueException;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.TracingMessageConsumer;
import org.eclipse.lsp4j.jsonrpc.TracingMessageConsumer.RequestMetadata;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageConsumer;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.junit.Test;

public class TracingMessageConsumerTest {
	private static final RemoteEndpoint TEST_REMOTE_ENDPOINT = new EmptyRemoteEndpoint();
	private static final MessageJsonHandler TEST_JSON_HANDLER = new MessageJsonHandler(emptyMap(), gsonBuilder -> {
		gsonBuilder.registerTypeHierarchyAdapter(Path.class, new PathTypeAdapter());
	});
	private static final StreamMessageConsumer TEST_STREAM_MESSAGE_CONSUMER =
			new StreamMessageConsumer(
					new ByteArrayOutputStream(), TEST_JSON_HANDLER);
	private static final Clock TEST_CLOCK_1 =
			Clock.fixed(Instant.parse("2019-06-26T22:07:30.00Z"), ZoneId.of("America/New_York"));
	private static final Clock TEST_CLOCK_2 =
			Clock.fixed(Instant.parse("2019-06-26T22:07:30.10Z"), ZoneId.of("America/New_York"));
	private static final String FILE_SEPARATOR_JSON_ESCAPED = File.separatorChar == '\\' ? "\\\\" : File.separator;

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
	public void testReceivedErrorResponseWithCustomDataAdapter() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Map<String, RequestMetadata> sentRequests = new HashMap<>();
		sentRequests.put("1", new RequestMetadata("foo", TEST_CLOCK_1.instant()));

		TracingMessageConsumer consumer =
			new TracingMessageConsumer(
				TEST_REMOTE_ENDPOINT, sentRequests, new HashMap<>(), printWriter, TEST_CLOCK_2, Locale.US);
		consumer.setJsonHandler(TEST_JSON_HANDLER);

		ResponseMessage message = new ResponseMessage();
		message.setId("1");
		message.setError(new ResponseError(-32600, "bar", Paths.get("foo/Bar.java")));

		consumer.consume(message);

		String actualTrace = stringWriter.toString();
		String expectedTrace = "" +
			"[Trace - 06:07:30 PM] Received response 'foo - (1)' in 100ms\n" +
			"Result: null\n" +
			"Error: {\n" +
			"  \"code\": -32600,\n" +
			"  \"message\": \"bar\",\n" +
			"  \"data\": \"foo" + FILE_SEPARATOR_JSON_ESCAPED + "Bar.java\"\n" +
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

	@Test
	public void testSendingNotificationWithCustomAdapter() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		TracingMessageConsumer consumer =
			new TracingMessageConsumer(
				TEST_STREAM_MESSAGE_CONSUMER, emptyMap(), emptyMap(), printWriter, TEST_CLOCK_1, Locale.US);
		consumer.setJsonHandler(TEST_JSON_HANDLER);

		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		message.setParams(Paths.get("foo/Bar.java"));

		consumer.consume(message);

		String actualTrace = stringWriter.toString();
		String expectedTrace = "" +
			"[Trace - 06:07:30 PM] Sending notification 'foo'\n" +
			"Params: \"foo" + FILE_SEPARATOR_JSON_ESCAPED + "Bar.java\"\n" +
			"\n" +
			"\n";

		assertEquals(expectedTrace, actualTrace);
	}

	@Test
	public void testNoUnmatchedResponseWhenOutgoingIsNotStreamConsumer() {
		// Simulate an outgoing transport that is not StreamMessageConsumer (e.g., websocket)
		var outgoingTrace = new StringWriter();
		var outgoingPrintWriter = new PrintWriter(outgoingTrace);

		var incomingTrace = new StringWriter();
		var incomingPrintWriter = new PrintWriter(incomingTrace);

		var sentRequests = new HashMap<String, RequestMetadata>();
		var receivedRequests = new HashMap<String, RequestMetadata>();
		
		// Pass a non-RemoteEndpoint consumer so TracingMessageConsumer treats messages as outgoing ("Sending")
		var outgoingTracing = new TracingMessageConsumer(new EmptyMessageConsumer(), sentRequests, receivedRequests, outgoingPrintWriter, TEST_CLOCK_1, Locale.US);

		// Send a request
		var req = new RequestMessage();
		req.setId("1");
		req.setMethod("foo");
		outgoingTracing.consume(req);

		// Prepare to capture warnings from TracingMessageConsumer
		var logger = java.util.logging.Logger.getLogger(TracingMessageConsumer.class.getName());
		var warnings = new ArrayList<String>();
		var handler = new java.util.logging.Handler() {
			@Override
			public void publish(java.util.logging.LogRecord record) {
				if (record.getLevel() == java.util.logging.Level.WARNING)
					warnings.add(record.getMessage());
			}

			@Override
			public void flush() {
			}

			@Override
			public void close() throws SecurityException {
			}
		};
		logger.addHandler(handler);
		try {
			// Now receive a response on the RemoteEndpoint path. Expected behavior (desired):
			// no warnings and a proper response trace even when the outgoing transport
			// is not a StreamMessageConsumer (e.g., websockets).
			var incomingTracing = new TracingMessageConsumer(TEST_REMOTE_ENDPOINT, sentRequests, receivedRequests, incomingPrintWriter, TEST_CLOCK_2, Locale.US);

			var resp = new ResponseMessage();
			resp.setId("1");
			resp.setResult("ok");
			incomingTracing.consume(resp);

			// Assert NO warning and a proper trace line for the response
			boolean sawUnmatched = warnings.stream().anyMatch(m -> m.contains("Unmatched response message"));
			assertFalse("Did not expect unmatched response warning", sawUnmatched);
			String expectedTrace = "" +
					"[Trace - 06:07:30 PM] Received response 'foo - (1)' in 100ms\n" +
					"Result: \"ok\"\n" +
					"Error: null\n" +
					"\n" +
					"\n";
			assertEquals(expectedTrace, incomingTrace.toString());
		} finally {
			logger.removeHandler(handler);
		}
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
