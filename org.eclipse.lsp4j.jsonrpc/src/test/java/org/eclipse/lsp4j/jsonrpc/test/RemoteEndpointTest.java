/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoteEndpointTest {
	
	static class TestEndpoint implements Endpoint {
		
		List<NotificationMessage> notifications = new ArrayList<>();
		Map<RequestMessage, CompletableFuture<Object>> requests = new LinkedHashMap<>();
		
		public void notify(String method, Object parameter) {
			notifications.add(new NotificationMessage() {{
				setMethod(method);
				setParams(parameter);
			}});
		}
		
		@Override
		public CompletableFuture<Object> request(String method, Object parameter) {
			CompletableFuture<Object> completableFuture = new CompletableFuture<Object>();
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
	
	@Test public void testNotification() {
		TestEndpoint endp = new TestEndpoint();
		TestMessageConsumer consumer = new TestMessageConsumer();
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
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
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
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
		RemoteEndpoint endpoint = new RemoteEndpoint(consumer, endp);
		
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
	
	@Test public void testException() {
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
			
			endpoint.consume(new RequestMessage() {{
				setId("1");
				setMethod("foo");
				setParams("myparam");
			}});
			
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
	
}