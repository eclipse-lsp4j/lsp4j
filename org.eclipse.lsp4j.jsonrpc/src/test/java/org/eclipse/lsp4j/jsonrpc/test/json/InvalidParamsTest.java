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
package org.eclipse.lsp4j.jsonrpc.test.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageIssueException;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageProducer;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.MessageIssue;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

public class InvalidParamsTest {

	public static class Position {
		public int line;
	}

	public interface Client {
	}

	public static class Server {
		final AtomicInteger requests = new AtomicInteger();
		final AtomicInteger notifications = new AtomicInteger();

		@JsonRequest
		public CompletableFuture<Integer> query(Position position) {
			requests.incrementAndGet();
			return CompletableFuture.completedFuture(position.line);
		}

		@JsonNotification
		public void changed(Position position) {
			notifications.incrementAndGet();
		}
	}

	private final MessageJsonHandler handler = new MessageJsonHandler(Map.of(
			"query", JsonRpcMethod.request("query", Integer.class, Position.class),
			"changed", JsonRpcMethod.notification("changed", Position.class),
			"list", JsonRpcMethod.request("list", Integer.class, new TypeToken<List<Integer>>() {}.getType()),
			"pair", JsonRpcMethod.request("pair", Integer.class, Integer.class, Integer.class),
			"either", JsonRpcMethod.request("either", Integer.class, new TypeToken<Either<String, Integer>>() {}.getType())));

	/**
	 * Lets JSON in this test be written with single quotes instead of escaped double quotes.
	 */
	private static String json(String singleQuoted) {
		return singleQuoted.replace('\'', '"');
	}

	/**
	 * Creates a request with its members in the given order. The id and the parameters are JSON,
	 * which may use single quotes.
	 */
	private static String request(String id, String method, String params, List<String> order) {
		final var message = new JsonObject();
		message.addProperty("jsonrpc", "2.0");
		Map<String, JsonElement> members = Map.of(
				"id", JsonParser.parseString(id),
				"method", new JsonPrimitive(method),
				"params", JsonParser.parseString(params));
		order.forEach(name -> message.add(name, members.get(name)));
		return message.toString();
	}

	/**
	 * Parses input that must be rejected with exactly one issue of the expected code, and returns
	 * the message that the issue is reported for.
	 */
	private <T extends Message> T assertRejected(String input, ResponseErrorCode expected, Class<T> messageType) {
		final var error = assertThrows(input, MessageIssueException.class, () -> handler.parseMessage(input));
		assertEquals(input, List.of(expected.getValue()),
				error.getIssues().stream().map(MessageIssue::getIssueCode).collect(Collectors.toList()));
		Message message = error.getRpcMessage();
		assertEquals(input, messageType, message.getClass());
		return messageType.cast(message);
	}

	private Object parsedParams(String input) {
		return ((RequestMessage) handler.parseMessage(input)).getParams();
	}

	@Test
	public void invalidParameterTypesRetainTheIdInEveryFieldOrder() {
		List<List<String>> orders = List.of(
				List.of("id", "method", "params"), List.of("id", "params", "method"),
				List.of("method", "id", "params"), List.of("method", "params", "id"),
				List.of("params", "id", "method"), List.of("params", "method", "id"));
		List<String> invalidParams = List.of("{'line': 'bad'}", "{'line': {}}", "{'line': []}", "true",
				"[{'line': 'bad'}]", "[{'line': 1}, {'line': 2}]", "[]");
		Map<String, Either<String, Number>> ids = Map.of(
				"7", Either.forRight(7),
				"'request-7'", Either.forLeft("request-7"));
		for (List<String> order : orders) {
			for (String params : invalidParams) {
				ids.forEach((id, expectedId) -> {
					String input = request(id, "query", params, order);
					final var message = assertRejected(input, ResponseErrorCode.InvalidParams, RequestMessage.class);
					assertEquals(input, "query", message.getMethod());
					assertEquals(input, expectedId, message.getRawId());
				});
			}
		}
	}

	@Test
	public void aParameterTypeAdapterErrorWithoutACauseIsAnInvalidParamsError() {
		// EitherTypeAdapter rejects a token that matches neither alternative with a JsonParseException without a cause.
		// Parameters after the method are converted while the message is read, and parameters before it afterwards.
		for (List<String> order : List.of(List.of("id", "method", "params"), List.of("id", "params", "method"))) {
			String input = request("7", "either", "true", order);
			final var message = assertRejected(input, ResponseErrorCode.InvalidParams, RequestMessage.class);
			assertEquals(input, Either.forRight(7), message.getRawId());
		}
	}

	@Test
	public void invalidNotificationRemainsANotification() {
		assertRejected(json("{'params': {'line': 'bad'}, 'method': 'changed', 'jsonrpc': '2.0'}"),
				ResponseErrorCode.InvalidParams, NotificationMessage.class);
	}

	@Test
	public void anIdInsideTheParametersIsNotTheMessageId() {
		assertRejected(json("{'method': 'changed', 'params': {'line': 'bad', 'id': 3, 'nested': {'id': 4}}}"),
				ResponseErrorCode.InvalidParams, NotificationMessage.class);

		final var request = assertRejected(
				json("{'method': 'query', 'params': [{'line': 'bad', 'id': 3}, {'id': 4}], 'id': 7}"),
				ResponseErrorCode.InvalidParams, RequestMessage.class);
		assertEquals(Either.forRight(7), request.getRawId());
	}

	@Test
	public void malformedJsonIsStillAParseError() {
		final var prefix = "{'jsonrpc': '2.0', 'id': 7, 'method': 'query', 'params': ";
		for (String suffix : List.of("{'line': }}", "{'line': 'bad'}]", "{'line': 'bad'}}}", "{'line': 'bad'}}{'id': 9}")) {
			String input = json(prefix + suffix);
			final var message = assertRejected(input, ResponseErrorCode.ParseError, RequestMessage.class);
			assertEquals(input, Either.forRight(7), message.getRawId());
		}
	}

	@Test
	public void validObjectAndPositionalParametersRetainTheirDecoding() {
		List<String> methodFirst = List.of("id", "method", "params");
		List<String> paramsFirst = List.of("params", "id", "method");
		for (List<String> order : List.of(methodFirst, paramsFirst)) {
			assertEquals(12, ((Position) parsedParams(request("7", "query", "{'line': 12}", order))).line);
			assertEquals(List.of(1, 2), parsedParams(request("7", "pair", "[1, 2]", order)));
		}
		// The existing decoder unwraps the positional argument array when method precedes params,
		// and accepts a bare list when params precede method.
		assertEquals(List.of(1, 2), parsedParams(request("7", "list", "[[1, 2]]", methodFirst)));
		assertEquals(List.of(1, 2), parsedParams(request("7", "list", "[1, 2]", paramsFirst)));
		assertEquals(12, ((Position) parsedParams(request("7", "query", "[{'line': 12}]", methodFirst))).line);
	}

	@Test
	public void invalidResponseResultIsNotAnInvalidParamsError() {
		handler.setMethodProvider(id -> "query");
		for (String input : List.of("{'id': 7, 'result': {}}", "{'result': {}, 'id': 7}")) {
			assertRejected(json(input), ResponseErrorCode.ParseError, ResponseMessage.class);
		}
	}

	@Test
	public void framedStreamRejectsBadParametersAndProcessesFollowingMessages() throws Exception {
		final var framed = new ByteArrayOutputStream();
		for (String message : List.of(
				"{'jsonrpc': '2.0', 'method': 'query', 'params': {'line': 'bad'}, 'id': 7}",
				"{'jsonrpc': '2.0', 'method': 'changed', 'params': {'line': 'bad'}}",
				"{'jsonrpc': '2.0', 'params': {'line': 12}, 'id': 8, 'method': 'query'}",
				"{'jsonrpc': '2.0', 'params': {'line': 12}, 'method': 'changed'}")) {
			byte[] content = json(message).getBytes(StandardCharsets.UTF_8);
			framed.write(("Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.US_ASCII));
			framed.write(content);
		}
		final var output = new ByteArrayOutputStream();
		final var server = new Server();
		Launcher<Client> launcher = Launcher.createLauncher(server, Client.class,
				new ByteArrayInputStream(framed.toByteArray()), output);
		launcher.startListening().get(5, TimeUnit.SECONDS);

		final var responses = new ArrayList<Message>();
		handler.setMethodProvider(id -> "query");
		try (var producer = new StreamMessageProducer(new ByteArrayInputStream(output.toByteArray()), handler)) {
			producer.listen(responses::add);
		}
		assertEquals(2, responses.size()); // Notifications must never receive a response.
		final var rejected = (ResponseMessage) responses.get(0);
		assertEquals(Either.forRight(7), rejected.getRawId());
		assertEquals(ResponseErrorCode.InvalidParams.getValue(), rejected.getError().getCode());
		final var accepted = (ResponseMessage) responses.get(1);
		assertEquals(Either.forRight(8), accepted.getRawId());
		assertEquals(12, accepted.getResult());
		assertEquals(1, server.requests.get());
		assertEquals(1, server.notifications.get());
	}
}
