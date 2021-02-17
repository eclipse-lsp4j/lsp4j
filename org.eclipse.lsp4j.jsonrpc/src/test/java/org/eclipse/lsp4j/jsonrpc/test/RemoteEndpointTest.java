/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.JsonRpcException;
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
import org.junit.Assert;
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
	public void testHandleRequestIssues() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
		endpoint.handle(init(new RequestMessage(), it -> {
			it.setId("1");
			it.setMethod("foo");
			it.setParams("myparam");
		}), Collections.singletonList(new MessageIssue("bar")));
		
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
			assertEquals(expected, exception.replaceAll("\\r", "").substring(0, expected.length()));
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
			Assert.fail("Expected an ExecutionException.");
		} catch (ExecutionException exception) {
			assertEquals("java.lang.RuntimeException: BAAZ", exception.getMessage());
		}
	}
	
	@Test
	public void testExceptionInCompletableFuture() throws Exception {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
		CompletableFuture<Object> future = endpoint.request("foo", "myparam");
		CompletableFuture<Void> chained = future.thenAccept(result -> {
			throw new RuntimeException("BAAZ");
		});
		endpoint.consume(init(new ResponseMessage(), it -> {
			it.setId("1");
			it.setResult("Bar");
		}));
		try {
			chained.get(TIMEOUT, TimeUnit.MILLISECONDS);
			Assert.fail("Expected an ExecutionException.");
		} catch (ExecutionException exception) {
			assertEquals("java.lang.RuntimeException: BAAZ", exception.getMessage());
		}
	}
	
	@Test
	public void testExceptionInOutputStream() throws Exception {
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(RemoteEndpoint.class);
			
			TestEndpoint endp = new TestEndpoint();
			MessageConsumer consumer = new MessageConsumer() {
				@Override
				public void consume(Message message) throws JsonRpcException {
					throw new JsonRpcException(new SocketException("Permission denied: connect"));
				}
			};
			RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
			endpoint.notify("foo", null);
			
			logMessages.await(Level.WARNING, "Failed to send notification message.");
		} finally {
			logMessages.unregister();
		}
	}
	
	@Test
	public void testOutputStreamClosed() throws Exception {
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(RemoteEndpoint.class);
			
			TestEndpoint endp = new TestEndpoint();
			MessageConsumer consumer = new MessageConsumer() {
				@Override
				public void consume(Message message) throws JsonRpcException {
					throw new JsonRpcException(new SocketException("Socket closed"));
				}
			};
			RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
			endpoint.notify("foo", null);
			
			logMessages.await(Level.INFO, "Failed to send notification message.");
		} finally {
			logMessages.unregister();
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

			long timeout = System.currentTimeMillis() + TIMEOUT;
			while (consumer.messages.isEmpty()) {
				Thread.sleep(20);
				if (System.currentTimeMillis() > timeout) {
					fail("Timedout waiting for messages");
				}
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