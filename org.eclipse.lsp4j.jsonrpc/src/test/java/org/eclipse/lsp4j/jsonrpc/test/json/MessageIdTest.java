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

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageIssueException;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageProducer;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.junit.Test;

public class MessageIdTest {

	public interface Client {
	}

	public static class Server {
		@JsonRequest
		public CompletableFuture<Integer> echo(Integer value) {
			return CompletableFuture.completedFuture(value);
		}
	}

	private final MessageJsonHandler handler = new MessageJsonHandler(Map.of(
			"echo", JsonRpcMethod.request("echo", Integer.class, Integer.class)));

	private static String request(String id, String params) {
		return "{\"jsonrpc\":\"2.0\",\"id\":" + id + ",\"method\":\"echo\",\"params\":" + params + "}";
	}

	@Test
	public void aNumericIdInTheIntRangeIsAnInteger() {
		for (int id : List.of(0, 7, -7, Integer.MAX_VALUE, Integer.MIN_VALUE)) {
			final var message = (RequestMessage) handler.parseMessage(request(String.valueOf(id), "5"));
			assertEquals(Either.forRight(id), message.getRawId());
			assertEquals(String.valueOf(id), message.getId());
		}
	}

	@Test
	public void aNumericIdOutsideTheIntRangeIsALong() {
		for (long id : List.of(Integer.MAX_VALUE + 1L, Integer.MIN_VALUE - 1L, 1779401475267L, Long.MAX_VALUE, Long.MIN_VALUE)) {
			final var message = (RequestMessage) handler.parseMessage(request(String.valueOf(id), "5"));
			assertEquals(Either.forRight(id), message.getRawId());
			assertEquals(String.valueOf(id), message.getId());
		}
	}

	@Test
	public void aResponseKeepsALongId() {
		handler.setMethodProvider(id -> "echo");
		final var message = (ResponseMessage) handler.parseMessage("{\"jsonrpc\":\"2.0\",\"id\":1779401475267,\"result\":5}");
		assertEquals(Either.forRight(1779401475267L), message.getRawId());
		assertEquals(5, message.getResult());
	}

	@Test
	public void invalidParametersKeepALongId() {
		// The id is read before the parameters, or after them once their conversion failed
		for (String input : List.of(request("1779401475267", "\"bad\""),
				"{\"jsonrpc\":\"2.0\",\"method\":\"echo\",\"params\":\"bad\",\"id\":1779401475267}")) {
			final var error = assertThrows(input, MessageIssueException.class, () -> handler.parseMessage(input));
			assertEquals(input, ResponseErrorCode.InvalidParams.getValue(), error.getIssues().get(0).getIssueCode());
			assertEquals(input, Either.forRight(1779401475267L), ((RequestMessage) error.getRpcMessage()).getRawId());
		}
	}

	@Test
	public void aRequestWithALongIdIsAnsweredWithThatId() throws Exception {
		String request = request("1779401475267", "5");
		byte[] content = request.getBytes(StandardCharsets.UTF_8);
		final var framed = new ByteArrayOutputStream();
		framed.write(("Content-Length: " + content.length + "\r\n\r\n").getBytes(StandardCharsets.US_ASCII));
		framed.write(content);
		final var output = new ByteArrayOutputStream();
		Launcher.createLauncher(new Server(), Client.class, new ByteArrayInputStream(framed.toByteArray()), output)
				.startListening().get(5, TimeUnit.SECONDS);

		final var responses = new ArrayList<Message>();
		handler.setMethodProvider(id -> "echo");
		try (var producer = new StreamMessageProducer(new ByteArrayInputStream(output.toByteArray()), handler)) {
			producer.listen(responses::add);
		}
		assertEquals(1, responses.size());
		final var response = (ResponseMessage) responses.get(0);
		assertEquals(Either.forRight(1779401475267L), response.getRawId());
		assertEquals(5, response.getResult());
	}
}
