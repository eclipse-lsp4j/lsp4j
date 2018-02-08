/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.MessageIssue;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.junit.Test;

public class RemoteEndpointTest {
	
	private static final long TIMEOUT = 2000;
	
	static class TestEndpoint implements Endpoint {
		
		List<NotificationMessage> notifications = new ArrayList<>();
		Map<RequestMessage, CompletableFuture<Object>> requests = new LinkedHashMap<>();
		
		public void notify(String method, Object parameter) {
			notifications.add(init(new NotificationMessage(), it -> {
				it.setMethod(method);
				it.setParams(parameter);
			}));
		}
		
		@Override
		public CompletableFuture<Object> request(String method, Object parameter) {
			CompletableFuture<Object> completableFuture = new CompletableFuture<Object>();
			requests.put(init(new RequestMessage(), it -> {
				it.setMethod(method);
				it.setParams(parameter);
			}), completableFuture);
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
	
	static <T> T init(T value, Consumer<T> initializer) {
		initializer.accept(value);
		return value;
	}
	
	@Test
	public void testNotification() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
		endpoint.consume(init(new NotificationMessage(), it -> {
			it.setMethod("foo");
			it.setParams("myparam");
		}));
		
		NotificationMessage notificationMessage = endp.notifications.get(0);
		assertEquals("foo", notificationMessage.getMethod());
		assertEquals("myparam", notificationMessage.getParams());
		assertTrue(consumer.messages.isEmpty());
	}
	
	@Test
	public void testRequest1() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
		endpoint.consume(init(new RequestMessage(), it -> {
			it.setId("1");
			it.setMethod("foo");
			it.setParams("myparam");
		}));
		
		Entry<RequestMessage, CompletableFuture<Object>> entry = endp.requests.entrySet().iterator().next();
		entry.getValue().complete("success");
		assertEquals("foo", entry.getKey().getMethod());
		assertEquals("myparam", entry.getKey().getParams());
		ResponseMessage responseMessage = (ResponseMessage) consumer.messages.get(0);
		assertEquals("success", responseMessage.getResult());
		assertEquals(Either.forLeft("1"), responseMessage.getRawId());
	}
	
	@Test
	public void testRequest2() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
		endpoint.consume(init(new RequestMessage(), it -> {
			it.setId(1);
			it.setMethod("foo");
			it.setParams("myparam");
		}));
		
		Entry<RequestMessage, CompletableFuture<Object>> entry = endp.requests.entrySet().iterator().next();
		entry.getValue().complete("success");
		assertEquals("foo", entry.getKey().getMethod());
		assertEquals("myparam", entry.getKey().getParams());
		ResponseMessage responseMessage = (ResponseMessage) consumer.messages.get(0);
		assertEquals("success", responseMessage.getResult());
		assertEquals(Either.forRight(1), responseMessage.getRawId());
	}
	
	@Test
	public void testRequest3() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
		endpoint.consume(init(new RequestMessage(), it -> {
			it.setId("1");
			it.setMethod("foo");
			it.setParams("myparam");
			it.setIssue(new MessageIssue("bar"));
		}));
		
		ResponseMessage responseMessage = (ResponseMessage) consumer.messages.get(0);
		assertNotNull(responseMessage.getError());
		assertEquals("bar", responseMessage.getError().getMessage());
	}
	
	@Test
	public void testCancellation() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
		endpoint.consume(init(new RequestMessage(), it -> {
			it.setId("1");
			it.setMethod("foo");
			it.setParams("myparam");
		}));
		
		Entry<RequestMessage, CompletableFuture<Object>> entry = endp.requests.entrySet().iterator().next();
		entry.getValue().cancel(true);
		ResponseMessage message = (ResponseMessage) consumer.messages.get(0);
		assertNotNull(message);
		ResponseError error = message.getError();
		assertNotNull(error);
		assertEquals(error.getCode(), ResponseErrorCode.RequestCancelled.getValue());
		assertEquals(error.getMessage(), "The request (id: 1, method: 'foo') has been cancelled");
	}
	
	@Test
	public void testExceptionInEndpoint() {
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			// Don't show the exception in the test execution log
			logMessages.registerTo(RemoteEndpoint.class);
			
			TestEndpoint endp = new TestEndpoint() {
				@Override
				public CompletableFuture<Object> request(String method, Object parameter) {
					throw new RuntimeException("BAAZ");
				}
			};
			TestMessageConsumer consumer = new TestMessageConsumer();
			RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
			
			endpoint.consume(init(new RequestMessage(), it -> {
				it.setId("1");
				it.setMethod("foo");
				it.setParams("myparam");
			}));
			
			ResponseMessage response = (ResponseMessage) consumer.messages.get(0);
			assertEquals("Internal error.", response.getError().getMessage());
			assertEquals(ResponseErrorCode.InternalError.getValue(), response.getError().getCode());
			String exception = (String) response.getError().getData();
			String expected = "java.lang.RuntimeException: BAAZ\n\tat org.eclipse.lsp4j.jsonrpc.test.RemoteEndpointTest";
			assertEquals(expected, exception.substring(0, expected.length()));
		} finally {
			logMessages.unregister();
		}
	}
	
	@Test
	public void testExceptionInConsumer() throws Exception {
		TestEndpoint endp = new TestEndpoint();
		MessageConsumer consumer = message -> {
			throw new RuntimeException("BAAZ");
		};
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
		CompletableFuture<Object> future = endpoint.request("foo", "myparam");
		future.whenComplete((result, exception) -> {
			assertNull(result);
			assertNotNull(exception);
			assertEquals("BAAZ", exception.getMessage());
		});
		try {
			future.get(TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (ExecutionException exception) {
			assertEquals("java.lang.RuntimeException: BAAZ", exception.getMessage());
		}
	}

	@Test
	public void testExceptionHandlerMisbehaving1() {
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			// Don't show the exception in the test execution log
			logMessages.registerTo(RemoteEndpoint.class);

			TestEndpoint endp = new TestEndpoint() {
				@Override
				public CompletableFuture<Object> request(String method, Object parameter) {
					throw new RuntimeException("BAAZ");
				}
			};
			TestMessageConsumer consumer = new TestMessageConsumer();
			// Misbehaving exception handler that returns null
			RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp, (e) -> null);

			endpoint.consume(init(new RequestMessage(), it -> {
				it.setId("1");
				it.setMethod("foo");
				it.setParams("myparam");
			}));

			assertEquals("Check some response received", 1, consumer.messages.size());
			ResponseMessage response = (ResponseMessage) consumer.messages.get(0);
			assertEquals(ResponseErrorCode.InternalError.getValue(), response.getError().getCode());
		} finally {
			logMessages.unregister();
		}
	}

	static class TestMessageConsumer2 implements MessageConsumer {

		boolean sentException = false;
		List<Message> messages = new ArrayList<>();

		@Override
		public void consume(Message message) {
			if (sentException) {
				messages.add(message);
			} else {
				// throw an exception only for the first message
				sentException = true;
				throw new RuntimeException("Exception in consumer");
			}
		}

	}
	
	@Test
	public void testExceptionHandlerMisbehaving2() throws Exception {
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			// Don't show the exception in the test execution log
			logMessages.registerTo(RemoteEndpoint.class);

			TestEndpoint endp = new TestEndpoint() {
				@Override
				public CompletableFuture<Object> request(String method, Object parameter) {
					return CompletableFuture.supplyAsync(() -> "baz");
				}
			};
			TestMessageConsumer2 consumer = new TestMessageConsumer2();
			// Misbehaving exception handler that returns null
			RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp, (e) -> null);

			endpoint.consume(init(new RequestMessage(), it -> {
				it.setId("1");
				it.setMethod("foo");
				it.setParams("myparam");
			}));

			while (consumer.messages.isEmpty()) {
				Thread.sleep(20);
			}
			assertEquals("Check some response received", 1, consumer.messages.size());
			ResponseMessage response = (ResponseMessage) consumer.messages.get(0);
			assertNotNull("Check response has error", response.getError());
			assertEquals(ResponseErrorCode.InternalError.getValue(), response.getError().getCode());
		} finally {
			logMessages.unregister();
		}
	}
}