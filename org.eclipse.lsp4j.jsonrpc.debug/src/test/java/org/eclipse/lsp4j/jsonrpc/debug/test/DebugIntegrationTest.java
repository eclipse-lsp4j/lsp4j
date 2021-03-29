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

import static org.eclipse.lsp4j.jsonrpc.json.MessageConstants.CONTENT_LENGTH_HEADER;
import static org.eclipse.lsp4j.jsonrpc.json.MessageConstants.CRLF;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.ResponseErrorException;
import org.eclipse.lsp4j.jsonrpc.debug.DebugLauncher;
import org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.validation.ReflectiveMessageValidator;
import org.junit.Assert;
import org.junit.Test;

public class DebugIntegrationTest {

	private static final long TIMEOUT = 2000;

	public static class MyParam {
		public MyParam(String string) {
			this.value = string;
		}

		String value;
	}

	public static interface MyServer {
		@JsonRequest
		CompletableFuture<MyParam> askServer(MyParam param);
	}

	public static interface MyClient {
		@JsonRequest
		CompletableFuture<MyParam> askClient(MyParam param);
	}

	@Test
	public void testBothDirectionRequests() throws Exception {
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();

		in.connect(out2);
		out.connect(in2);

		MyClient client = new MyClient() {
			@Override
			public CompletableFuture<MyParam> askClient(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyServer> clientSideLauncher = DebugLauncher.createLauncher(client, MyServer.class, in, out);

		// create server side
		MyServer server = new MyServer() {
			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyClient> serverSideLauncher = DebugLauncher.createLauncher(server, MyClient.class, in2, out2);

		clientSideLauncher.startListening();
		serverSideLauncher.startListening();

		CompletableFuture<MyParam> fooFuture = clientSideLauncher.getRemoteProxy().askServer(new MyParam("FOO"));
		CompletableFuture<MyParam> barFuture = serverSideLauncher.getRemoteProxy().askClient(new MyParam("BAR"));

		Assert.assertEquals("FOO", fooFuture.get(TIMEOUT, TimeUnit.MILLISECONDS).value);
		Assert.assertEquals("BAR", barFuture.get(TIMEOUT, TimeUnit.MILLISECONDS).value);
	}

	@Test
	public void testCancellation() throws Exception {
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();

		in.connect(out2);
		out.connect(in2);

		boolean[] inComputeAsync = new boolean[1];
		boolean[] cancellationHappened = new boolean[1];

		MyClient client = new MyClient() {
			@Override
			public CompletableFuture<MyParam> askClient(MyParam param) {
				return CompletableFutures.computeAsync(cancelToken -> {
					try {
						long startTime = System.currentTimeMillis();
						inComputeAsync[0] = true;
						do {
							cancelToken.checkCanceled();
							Thread.sleep(50);
						} while (System.currentTimeMillis() - startTime < TIMEOUT);
					} catch (CancellationException e) {
						cancellationHappened[0] = true;
					} catch (InterruptedException e) {
						Assert.fail("Thread was interrupted unexpectedly.");
					}
					return param;
				});
			}
		};
		Launcher<MyServer> clientSideLauncher = DebugLauncher.createLauncher(client, MyServer.class, in, out);

		// create server side
		MyServer server = new MyServer() {
			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyClient> serverSideLauncher = DebugLauncher.createLauncher(server, MyClient.class, in2, out2);

		clientSideLauncher.startListening();
		serverSideLauncher.startListening();

		CompletableFuture<MyParam> future = serverSideLauncher.getRemoteProxy().askClient(new MyParam("FOO"));

		long startTime = System.currentTimeMillis();
		while (!inComputeAsync[0]) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - startTime > TIMEOUT)
				Assert.fail("Timeout waiting for client to start computing.");
		}
		future.cancel(true);

		startTime = System.currentTimeMillis();
		while (!cancellationHappened[0]) {
			Thread.sleep(50);
			if (System.currentTimeMillis() - startTime > TIMEOUT)
				Assert.fail("Timeout waiting for confirmation of cancellation.");
		}
		try {
			future.get(TIMEOUT, TimeUnit.MILLISECONDS);
			Assert.fail("Expected cancellation.");
		} catch (CancellationException e) {
		}
	}

	@Test
	public void testCancellationResponse() throws Exception {
		// create client messages
		String requestMessage = "{\"type\":\"request\","
				+ "\"seq\":1,\n"
				+ "\"command\":\"askServer\",\n"
				+ "\"arguments\": { value: \"bar\" }\n"
				+ "}";
		String cancellationMessage = "{\"type\":\"event\","
				+ "\"event\":\"$/cancelRequest\",\n"
				+ "\"body\": { id: 1 }\n"
				+ "}";
		String clientMessages = getHeader(requestMessage.getBytes().length) + requestMessage
				+ getHeader(cancellationMessage.getBytes().length) + cancellationMessage;

		// create server side
		ByteArrayInputStream in = new ByteArrayInputStream(clientMessages.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		MyServer server = new MyServer() {
			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFutures.computeAsync(cancelToken -> {
					try {
						long startTime = System.currentTimeMillis();
						do {
							cancelToken.checkCanceled();
							Thread.sleep(50);
						} while (System.currentTimeMillis() - startTime < TIMEOUT);
					} catch (InterruptedException e) {
						Assert.fail("Thread was interrupted unexpectedly.");
					}
					return param;
				});
			}
		};
		Launcher<MyClient> serverSideLauncher = DebugLauncher.createLauncher(server, MyClient.class, in, out);
		serverSideLauncher.startListening().get(TIMEOUT, TimeUnit.MILLISECONDS);

		Assert.assertEquals("Content-Length: 163\r\n\r\n" +
				"{\"type\":\"response\",\"seq\":1,\"request_seq\":1,\"command\":\"askServer\",\"success\":false,\"message\":\"The request (id: 1, method: \\u0027askServer\\u0027) has been cancelled\"}",
				out.toString());
	}


	@Test
	public void testVersatility() throws Exception {
		Logger.getLogger(RemoteEndpoint.class.getName()).setLevel(Level.OFF);
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();

		// See https://github.com/eclipse/lsp4j/issues/510 for full details.
		// Make sure that the thread that writes to the PipedOutputStream stays alive
		// until the read from the PipedInputStream. Using a cached thread pool
		// does not 100% guarantee that, but increases the probability that the
		// selected thread will exist for the lifetime of the test.
		ExecutorService executor = Executors.newCachedThreadPool();

		in.connect(out2);
		out.connect(in2);

		MyClient client = new MyClient() {
			private int tries = 0;

			@Override
			public CompletableFuture<MyParam> askClient(MyParam param) {
				if (tries == 0) {
					tries++;
					throw new UnsupportedOperationException();
				}
				return CompletableFutures.computeAsync(executor, cancelToken -> {
					if (tries++ == 1)
						throw new UnsupportedOperationException();
					return param;
				});
			}
		};
		Launcher<MyServer> clientSideLauncher = DebugLauncher.createLauncher(client, MyServer.class, in, out);

		// create server side
		MyServer server = new MyServer() {
			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyClient> serverSideLauncher = DebugLauncher.createLauncher(server, MyClient.class, in2, out2);

		clientSideLauncher.startListening();
		serverSideLauncher.startListening();

		CompletableFuture<MyParam> errorFuture1 = serverSideLauncher.getRemoteProxy().askClient(new MyParam("FOO"));
		try {
			System.out.println(errorFuture1.get());
			Assert.fail();
		} catch (ExecutionException e) {
			Assert.assertNotNull(((ResponseErrorException)e.getCause()).getResponseError().getMessage());
		}
		CompletableFuture<MyParam> errorFuture2 = serverSideLauncher.getRemoteProxy().askClient(new MyParam("FOO"));
		try {
			errorFuture2.get();
			Assert.fail();
		} catch (ExecutionException e) {
			Assert.assertNotNull(((ResponseErrorException)e.getCause()).getResponseError().getMessage());
		}
		CompletableFuture<MyParam> goodFuture = serverSideLauncher.getRemoteProxy().askClient(new MyParam("FOO"));
		Assert.assertEquals("FOO", goodFuture.get(TIMEOUT, TimeUnit.MILLISECONDS).value);
	}

	@Test
	public void testUnknownMessages() throws Exception {
		// intercept log messages
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(GenericEndpoint.class.getName());

			// create client messages
			String clientMessage1 = "{\"type\":\"event\","
					+ "\"event\":\"foo1\",\n"
					+ " \"body\":\"bar\"\n"
					+ "}";
			String clientMessage2 = "{\"type\":\"request\","
					+ "\"seq\":1,\n"
					+ "\"command\":\"foo2\",\n"
					+ " \"arguments\":\"bar\"\n"
					+ "}";
			String clientMessages = getHeader(clientMessage1.getBytes().length) + clientMessage1
					+ getHeader(clientMessage2.getBytes().length) + clientMessage2;

			// create server side
			ByteArrayInputStream in = new ByteArrayInputStream(clientMessages.getBytes());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			MyServer server = new MyServer() {
				@Override
				public CompletableFuture<MyParam> askServer(MyParam param) {
					return CompletableFuture.completedFuture(param);
				}
			};
			Launcher<MyClient> serverSideLauncher = DebugLauncher.createLauncher(server, MyClient.class, in, out);
			serverSideLauncher.startListening().get(TIMEOUT, TimeUnit.MILLISECONDS);

			logMessages.await(Level.WARNING, "Unsupported notification method: foo1");
			logMessages.await(Level.WARNING, "Unsupported request method: foo2");

			Assert.assertEquals("Content-Length: 121\r\n\r\n" +
					"{\"type\":\"response\",\"seq\":1,\"request_seq\":1,\"command\":\"foo2\",\"success\":false,\"message\":\"Unsupported request method: foo2\"}",
					out.toString());
		} finally {
			logMessages.unregister();
		}
	}

	@Test
	public void testUnknownOptionalMessages() throws Exception {
		// intercept log messages
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(GenericEndpoint.class.getName());

			// create client messages
			String clientMessage1 = "{\"type\":\"event\","
					+ "\"event\":\"$/foo1\",\n"
					+ " \"body\":\"bar\"\n"
					+ "}";
			String clientMessage2 = "{\"type\":\"request\","
					+ "\"seq\":1,\n"
					+ "\"command\":\"$/foo2\",\n"
					+ " \"arguments\":\"bar\"\n"
					+ "}";
			String clientMessages = getHeader(clientMessage1.getBytes().length) + clientMessage1
					+ getHeader(clientMessage2.getBytes().length) + clientMessage2;

			// create server side
			ByteArrayInputStream in = new ByteArrayInputStream(clientMessages.getBytes());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			MyServer server = new MyServer() {
				@Override
				public CompletableFuture<MyParam> askServer(MyParam param) {
					return CompletableFuture.completedFuture(param);
				}
			};
			Launcher<MyClient> serverSideLauncher = DebugLauncher.createLauncher(server, MyClient.class, in, out);
			serverSideLauncher.startListening().get(TIMEOUT, TimeUnit.MILLISECONDS);

			logMessages.await(Level.INFO, "Unsupported notification method: $/foo1");
			logMessages.await(Level.INFO, "Unsupported request method: $/foo2");

			Assert.assertEquals("Content-Length: 77\r\n\r\n" +
					"{\"type\":\"response\",\"seq\":1,\"request_seq\":1,\"command\":\"$/foo2\",\"success\":true}",
					out.toString());
		} finally {
			logMessages.unregister();
		}
	}

	@Test
	public void testResponse() throws Exception {
		String clientMessage = "{\"type\":\"request\","
				+ "\"seq\":1,\n"
				+ "\"command\":\"askServer\",\n"
				+ " \"arguments\": { value: \"bar\" }\n"
				+ "}";
		String clientMessages = getHeader(clientMessage.getBytes().length) + clientMessage;

		// create server side
		ByteArrayInputStream in = new ByteArrayInputStream(clientMessages.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		MyServer server = new MyServer() {
			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyClient> serverSideLauncher = DebugLauncher.createLauncher(server, MyClient.class, in, out);
		serverSideLauncher.startListening().get(TIMEOUT, TimeUnit.MILLISECONDS);

		Assert.assertEquals("Content-Length: 103\r\n\r\n" +
				"{\"type\":\"response\",\"seq\":1,\"request_seq\":1,\"command\":\"askServer\",\"success\":true,\"body\":{\"value\":\"bar\"}}",
				out.toString());
	}

	public static interface UnexpectedParamsTestServer {
		@JsonNotification
		void myNotification();
	}

	@Test
	public void testUnexpectedParams() throws Exception {
		// intercept log messages
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(GenericEndpoint.class.getName());

			// create client messages
			String notificationMessage = "{\"type\":\"event\","
					+ "\"event\":\"myNotification\",\n"
					+ "\"body\": { \"value\": \"foo\" }\n"
					+ "}";
			String clientMessages = getHeader(notificationMessage.getBytes().length) + notificationMessage;

			// create server side
			ByteArrayInputStream in = new ByteArrayInputStream(clientMessages.getBytes());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			UnexpectedParamsTestServer server = new UnexpectedParamsTestServer() {
				public void myNotification() {
				}
			};
			Launcher<MyClient> serverSideLauncher = DebugLauncher.createLauncher(server, MyClient.class, in, out);
			serverSideLauncher.startListening();

			logMessages.await(Level.WARNING, "Unexpected params '{\"value\":\"foo\"}' for " + "'public abstract void "
					+ UnexpectedParamsTestServer.class.getName() + ".myNotification()' is ignored");
		} finally {
			logMessages.unregister();
		}
	}

    protected String getHeader(int contentLength) {
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append(CONTENT_LENGTH_HEADER).append(": ").append(contentLength).append(CRLF);
        headerBuilder.append(CRLF);
        return headerBuilder.toString();
    }

    /**
     * Test a fully connected design with the {@link ReflectiveMessageValidator} enabled.
     */
	@Test
	public void testValidatedRequests() throws Exception {
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();

		in.connect(out2);
		out.connect(in2);

		MyClient client = new MyClient() {
			@Override
			public CompletableFuture<MyParam> askClient(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyServer> clientSideLauncher = DebugLauncher.createLauncher(client, MyServer.class, in, out, true, null);

		// create server side
		MyServer server = new MyServer() {
			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}
		};
		Launcher<MyClient> serverSideLauncher = DebugLauncher.createLauncher(server, MyClient.class, in2, out2, true, null);

		clientSideLauncher.startListening();
		serverSideLauncher.startListening();

		CompletableFuture<MyParam> fooFuture = clientSideLauncher.getRemoteProxy().askServer(new MyParam("FOO"));
		CompletableFuture<MyParam> barFuture = serverSideLauncher.getRemoteProxy().askClient(new MyParam("BAR"));

		Assert.assertEquals("FOO", fooFuture.get(TIMEOUT, TimeUnit.MILLISECONDS).value);
		Assert.assertEquals("BAR", barFuture.get(TIMEOUT, TimeUnit.MILLISECONDS).value);
	}


}
