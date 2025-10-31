/******************************************************************************
 * Copyright (c) 2016, 2024 TypeFox and others.
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

import static org.junit.Assert.*;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.JsonRpcException;
import org.eclipse.lsp4j.jsonrpc.JsonRpcRequestFuture;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.CancelParams;
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

		@Override
		public void notify(String method, Object parameter) {
			notifications.add(init(new NotificationMessage(), it -> {
				it.setMethod(method);
				it.setParams(parameter);
			}));
		}

		@Override
		public CompletableFuture<Object> request(String method, Object parameter) {
			final var completableFuture = new CompletableFuture<>();
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
		final var endp = new TestEndpoint();
		final var consumer = new TestMessageConsumer();
		final var endpoint = new RemoteEndpoint(consumer, endp);

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
		final var endp = new TestEndpoint();
		final var consumer = new TestMessageConsumer();
		final var endpoint = new RemoteEndpoint(consumer, endp);

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
		final var endp = new TestEndpoint();
		final var consumer = new TestMessageConsumer();
		final var endpoint = new RemoteEndpoint(consumer, endp);

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
		final var endp = new TestEndpoint();
		final var consumer = new TestMessageConsumer();
		final var endpoint = new RemoteEndpoint(consumer, endp);

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
		final var endp = new TestEndpoint();
		final var consumer = new TestMessageConsumer();
		final var endpoint = new RemoteEndpoint(consumer, endp);

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
		final var logMessages = new LogMessageAccumulator();
		try {
			// Don't show the exception in the test execution log
			logMessages.registerTo(RemoteEndpoint.class);

			final var endp = new TestEndpoint() {
				@Override
				public CompletableFuture<Object> request(String method, Object parameter) {
					throw new RuntimeException("BAAZ");
				}
			};
			final var consumer = new TestMessageConsumer();
			final var endpoint = new RemoteEndpoint(consumer, endp);

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
	public void testResponseErrorExceptionInEndpoint() {
		final var logMessages = new LogMessageAccumulator();
		try {
			// Don't show the exception in the test execution log
			logMessages.registerTo(RemoteEndpoint.class);

			TestEndpoint endp = new TestEndpoint() {
				@Override
				public CompletableFuture<Object> request(String method, Object parameter) {
					throw new ResponseErrorException(new ResponseError(ResponseErrorCode.InvalidParams, "Direct Throw", "data"));
				}
			};
			final var consumer = new TestMessageConsumer();
			final var endpoint = new RemoteEndpoint(consumer, endp);

			endpoint.consume(init(new RequestMessage(), it -> {
				it.setId("1");
				it.setMethod("foo");
				it.setParams("myparam");
			}));

			ResponseMessage response = (ResponseMessage) consumer.messages.get(0);
			assertEquals("Direct Throw", response.getError().getMessage());
			assertEquals(ResponseErrorCode.InvalidParams.getValue(), response.getError().getCode());
			String data = (String) response.getError().getData();
			assertEquals("data", data);
		} finally {
			logMessages.unregister();
		}
	}

	@Test
	public void testResponseErrorExceptionFromFutureInEndpoint() {
		final var logMessages = new LogMessageAccumulator();
		try {
			// Don't show the exception in the test execution log
			logMessages.registerTo(RemoteEndpoint.class);

			TestEndpoint endp = new TestEndpoint() {
				@Override
				public CompletableFuture<Object> request(String method, Object parameter) {
					CompletableFuture<Object> future = new CompletableFuture<>();
					future.completeExceptionally(new ResponseErrorException(
							new ResponseError(ResponseErrorCode.InvalidParams, "completeExceptionally", "data")));
					return future;
				}
			};
			final var consumer = new TestMessageConsumer();
			final var endpoint = new RemoteEndpoint(consumer, endp);

			endpoint.consume(init(new RequestMessage(), it -> {
				it.setId("1");
				it.setMethod("foo");
				it.setParams("myparam");
			}));

			final var response = (ResponseMessage) consumer.messages.get(0);
			assertEquals("completeExceptionally", response.getError().getMessage());
			assertEquals(ResponseErrorCode.InvalidParams.getValue(), response.getError().getCode());
			String data = (String) response.getError().getData();
			assertEquals("data", data);
		} finally {
			logMessages.unregister();
		}
	}

	@Test
	public void testExceptionInConsumer() throws Exception {
		final var endp = new TestEndpoint();
		MessageConsumer consumer = message -> {
			throw new RuntimeException("BAAZ");
		};
		final var endpoint = new RemoteEndpoint(consumer, endp);

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
		final var endp = new TestEndpoint();
		final var consumer = new TestMessageConsumer();
		final var endpoint = new RemoteEndpoint(consumer, endp);

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
		final var logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(RemoteEndpoint.class);

			final var endp = new TestEndpoint();
			final var consumer = new MessageConsumer() {
				@Override
				public void consume(Message message) throws JsonRpcException {
					throw new JsonRpcException(new SocketException("Permission denied: connect"));
				}
			};
			final var endpoint = new RemoteEndpoint(consumer, endp);
			endpoint.notify("foo", null);

			logMessages.await(Level.WARNING, "Failed to send notification message.");
		} finally {
			logMessages.unregister();
		}
	}

	@Test
	public void testOutputStreamClosed() throws Exception {
		final var logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(RemoteEndpoint.class);

			final var endp = new TestEndpoint();
			final var consumer = new MessageConsumer() {
				@Override
				public void consume(Message message) throws JsonRpcException {
					throw new JsonRpcException(new SocketException("Socket closed"));
				}
			};
			final var endpoint = new RemoteEndpoint(consumer, endp);
			endpoint.notify("foo", null);

			logMessages.await(Level.INFO, "Failed to send notification message.");
		} finally {
			logMessages.unregister();
		}
	}

	@Test
	public void testExceptionHandlerMisbehaving1() {
		final var logMessages = new LogMessageAccumulator();
		try {
			// Don't show the exception in the test execution log
			logMessages.registerTo(RemoteEndpoint.class);

			final var endp = new TestEndpoint() {
				@Override
				public CompletableFuture<Object> request(String method, Object parameter) {
					throw new RuntimeException("BAAZ");
				}
			};
			final var consumer = new TestMessageConsumer();
			// Misbehaving exception handler that returns null
			final var endpoint = new RemoteEndpoint(consumer, endp, e -> null);

			endpoint.consume(init(new RequestMessage(), it -> {
				it.setId("1");
				it.setMethod("foo");
				it.setParams("myparam");
			}));

			assertEquals("Check some response received", 1, consumer.messages.size());
			final var response = (ResponseMessage) consumer.messages.get(0);
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
		final var logMessages = new LogMessageAccumulator();
		try {
			// Don't show the exception in the test execution log
			logMessages.registerTo(RemoteEndpoint.class);

			final var endp = new TestEndpoint() {
				@Override
				public CompletableFuture<Object> request(String method, Object parameter) {
					return CompletableFuture.supplyAsync(() -> "baz");
				}
			};
			final var consumer = new TestMessageConsumer2();
			// Misbehaving exception handler that returns null
			final var endpoint = new RemoteEndpoint(consumer, endp, e -> null);

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

	@Test
	public void testEndpointReturningNull() {
		final var logMessages = new LogMessageAccumulator();
		try {
			// Don't show the exception in the test execution log
			logMessages.registerTo(RemoteEndpoint.class);

			final var endp = new TestEndpoint() {
				@Override
				public CompletableFuture<Object> request(String method, Object parameter) {
					return null;
				}
			};
			final var consumer = new TestMessageConsumer();
			final var endpoint = new RemoteEndpoint(consumer, endp);

			endpoint.consume(init(new RequestMessage(), it -> {
				it.setId("1");
				it.setMethod("foo");
				it.setParams("myparam");
			}));

			assertEquals("Check some response received", 1, consumer.messages.size());
			final var response = (ResponseMessage) consumer.messages.get(0);
			assertNotNull("Check response has error", response.getError());
			assertEquals(ResponseErrorCode.InternalError.getValue(), response.getError().getCode());
		} finally {
			logMessages.unregister();
		}
	}

	@Test
	public void testCancellationAfterTransformation() {
		final var endp = new TestEndpoint();
		final var consumer = new TestMessageConsumer();
		final var endpoint = new RemoteEndpoint(consumer, endp);

		/*
		 * Outbound request: should synchronously send a RequestMessage to consumer
		 */
		JsonRpcRequestFuture<Object> root = endpoint.request("foo", "myparam");
		assertEquals("Request should be sent once", 1, consumer.messages.size());
		assertEquals(root, root.getRoot());
		Message sent = consumer.messages.get(0);
		assertTrue(sent instanceof RequestMessage);

		var requestId = ((RequestMessage) sent).getRawId();

		/*
		 * Transform the future and cancel the transformed stage only (but not the original future)
		 */
		JsonRpcRequestFuture<Object> transformed1 = root.thenApply(x -> x);
		assertEquals(root, transformed1.getRoot());
		boolean cancelled = transformed1.cancel(true);
		assertTrue("Transformed future should report cancelled", cancelled);
		// Cancelling transformed stage does NOT auto-send protocol cancel
		assertEquals("No cancel notification sent on substage cancel(true)", 1, consumer.messages.size());
		assertFalse("Root future should not be cancelled by substage cancel(true)", root.isCancelled());

		/*
		 * Explicitly cancel the remote request from a transformed stage (wire-only)
		 */
		JsonRpcRequestFuture<Void> transformed2 = root.thenApply(x -> x).thenAccept(x -> {
		});
		assertEquals(root, transformed2.getRoot());
		transformed2.cancelRequest();
		assertEquals("Cancel notification should now be sent", 2, consumer.messages.size());
		Message maybeCancel = consumer.messages.get(1);
		assertTrue("Second message should be a NotificationMessage", maybeCancel instanceof NotificationMessage);
		var cancelNotif = (NotificationMessage) maybeCancel;
		assertEquals("Cancel method name should match",
				MessageJsonHandler.CANCEL_METHOD.getMethodName(), cancelNotif.getMethod());
		assertNotNull("Cancel params should carry the original request id", cancelNotif.getParams());
		assertEquals("Cancel id should match original request id", requestId,
				((CancelParams) cancelNotif.getParams()).getRawId());
		assertFalse("Root future should NOT be cancelled by cancelRequest()", root.isCancelled());
		assertFalse("Substage should NOT be cancelled by cancelRequest()", transformed2.isCancelled());

		/*
		 * Cancel the root future locally and verify that cancelRequest() has been sent exactly once
		 */
		transformed2.getRoot().cancel(true);
		assertTrue("Root future should now be cancelled", root.isCancelled());
		assertTrue("Dependent substage should complete exceptionally after root cancel", transformed2.isCompletedExceptionally());
		assertEquals("No additional cancel notification should be sent", 2, consumer.messages.size());
	}

	@Test
	public void testFastCancellationAfterTransformation() {
		final var endp = new TestEndpoint();
		final var consumer = new TestMessageConsumer();
		final var endpoint = new RemoteEndpoint(consumer, endp);

		/*
		 * Outbound request: should synchronously send a RequestMessage to consumer
		 */
		JsonRpcRequestFuture<Object> root = endpoint.request("foo", "myparam");
		assertEquals("Request should be sent once", 1, consumer.messages.size());
		assertEquals(root, root.getRoot());
		Message sent = consumer.messages.get(0);
		assertTrue(sent instanceof RequestMessage);

		var requestId = ((RequestMessage) sent).getRawId();

		JsonRpcRequestFuture<Object> transformed1 = root.thenApply(x -> x);
		assertEquals(root, transformed1.getRoot());

		/*
		 * Fast-cancel: cancel the root future locally, ensuring cancelRequest() has been sent exactly once
		 */
		transformed1.getRoot().cancel(true);
		assertTrue("Root future should be cancelled after fast-cancel", root.isCancelled());
		assertTrue("Dependent substage should be cancelled after root fast-cancel", transformed1.isCompletedExceptionally());
		assertEquals("Cancel notification should now be sent", 2, consumer.messages.size());
		Message maybeCancel = consumer.messages.get(1);
		assertTrue("Second message should be a NotificationMessage", maybeCancel instanceof NotificationMessage);
		var cancelNotif = (NotificationMessage) maybeCancel;
		assertEquals("Cancel method name should match",
				MessageJsonHandler.CANCEL_METHOD.getMethodName(), cancelNotif.getMethod());
		assertNotNull("Cancel params should carry the original request id", cancelNotif.getParams());
		assertEquals("Cancel id should match original request id", requestId,
				((CancelParams) cancelNotif.getParams()).getRawId());
	}

	@Test
	public void testServerCancellationCompletesExceptionally() {
		final var endp = new TestEndpoint();
		final var consumer = new TestMessageConsumer();
		final var endpoint = new RemoteEndpoint(consumer, endp);

		// Send outbound request and capture id
		JsonRpcRequestFuture<Object> root = endpoint.request("foo", "myparam");
		assertEquals("Request should be sent once", 1, consumer.messages.size());
		Message sent = consumer.messages.get(0);
		assertTrue(sent instanceof RequestMessage);
		var requestId = ((RequestMessage) sent).getRawId();

		// Derive a dependent stage and send wire-only cancel
		JsonRpcRequestFuture<Void> transformed = root.thenAccept(x -> {
		});
		assertEquals(root, transformed.getRoot());
		boolean sentCancel = transformed.cancelRequest();
		assertTrue("Cancel notification should be sent", sentCancel);
		assertEquals("Cancel notification should now be sent", 2, consumer.messages.size());

		// Simulate server replying with RequestCancelled error for the same id
		endpoint.consume(init(new ResponseMessage(), it -> {
			it.setRawId(requestId);
			it.setJsonrpc("2.0");
			final var err = new ResponseError(ResponseErrorCode.RequestCancelled, "cancelled", null);
			it.setError(err);
		}));

		// Root and dependent should complete exceptionally (not locally cancelled)
		assertTrue("Root should complete exceptionally due to RequestCancelled", root.isCompletedExceptionally());
		assertTrue("Dependent should complete exceptionally due to RequestCancelled", transformed.isCompletedExceptionally());

		// Exception type should be ResponseErrorException with code RequestCancelled
		try {
			root.join();
			fail("Expected completion exception");
		} catch (CompletionException ex) {
			assertTrue(ex.getCause() instanceof ResponseErrorException);
			var ree = (ResponseErrorException) ex.getCause();
			assertEquals(ResponseErrorCode.RequestCancelled.getValue(), ree.getResponseError().getCode());
		}
	}
}
