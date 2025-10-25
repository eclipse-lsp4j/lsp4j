/******************************************************************************
 * Copyright (c) 2018 TypeFox and others.
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.jsonrpc.JsonRpcException;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageProducer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MessageProducerTest {

	private static final long TIMEOUT = 2000;

	private ExecutorService executorService;
	private Level logLevel;

	@Before
	public void setup() {
		executorService = Executors.newCachedThreadPool();
		Logger logger = Logger.getLogger(StreamMessageProducer.class.getName());
		logLevel = logger.getLevel();
		logger.setLevel(Level.SEVERE);
	}

	@After
	public void teardown() {
		executorService.shutdown();
		Logger logger = Logger.getLogger(StreamMessageProducer.class.getName());
		logger.setLevel(logLevel);
	}

	@Test
	public void testStopOnInterrrupt() throws Exception {
		executorService.submit(() -> {
			InputStream input = new InputStream() {
				@Override
				public int read() throws IOException {
					throw new InterruptedIOException();
				}
			};
			MessageJsonHandler jsonHandler = new MessageJsonHandler(Collections.emptyMap());
			StreamMessageProducer messageProducer = new StreamMessageProducer(input, jsonHandler);
			messageProducer.listen(message -> {});
			messageProducer.close();
		}).get(TIMEOUT, TimeUnit.MILLISECONDS);
	}

	@Test
	public void testStopOnChannelClosed() throws Exception {
		executorService.submit(() -> {
			InputStream input = new InputStream() {
				@Override
				public int read() throws IOException {
					throw new ClosedChannelException();
				}
			};
			MessageJsonHandler jsonHandler = new MessageJsonHandler(Collections.emptyMap());
			StreamMessageProducer messageProducer = new StreamMessageProducer(input, jsonHandler);
			messageProducer.listen(message -> {});
			messageProducer.close();
		}).get(TIMEOUT, TimeUnit.MILLISECONDS);
	}

	@Test
	public void testStopOnSocketClosed() throws Throwable {
		executorService.submit(() -> {
			InputStream input = new InputStream() {
				@Override
				public int read() throws IOException {
					throw new SocketException("Socket closed");
				}
			};
			MessageJsonHandler jsonHandler = new MessageJsonHandler(Collections.emptyMap());
			StreamMessageProducer messageProducer = new StreamMessageProducer(input, jsonHandler);
			messageProducer.listen(message -> {});
			messageProducer.close();
		}).get(TIMEOUT, TimeUnit.MILLISECONDS);
	}

	@Test(expected = JsonRpcException.class)
	public void testIOException() throws Throwable {
		try {
			executorService.submit(() -> {
				InputStream input = new InputStream() {
					@Override
					public int read() throws IOException {
						throw new SocketException("Permission denied: connect");
					}
				};
				MessageJsonHandler jsonHandler = new MessageJsonHandler(Collections.emptyMap());
				StreamMessageProducer messageProducer = new StreamMessageProducer(input, jsonHandler);
				messageProducer.listen(message -> {});
				messageProducer.close();
			}).get(TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (ExecutionException e) {
			throw e.getCause();
		}
	}

	@Test
	public void testSpuriousSingleCRLFBetweenTwoMessages() throws Exception {
		executorService.submit(() -> {
			// Two valid framed messages with a single extra CRLF inserted between
			// the end of content of the first and the start of the next header block.
			String msg1 = "{\"jsonrpc\":\"2.0\",\"method\":\"ping\",\"params\":null}";
			String msg2 = "{\"jsonrpc\":\"2.0\",\"method\":\"pong\",\"params\":null}";
			String inputStr = header(msg1.length()) + msg1
					+ "\r\n" // single extra newline between messages
					+ header(msg2.length()) + msg2;

			// Override fireError to surface the bug as a failure if triggered.
			class TestProducer extends StreamMessageProducer {
				public TestProducer(InputStream input, MessageJsonHandler jsonHandler) {
					super(input, jsonHandler);
				}

				@Override
				protected void fireError(Throwable error) {
					throw new AssertionError("fireError was called: " + error, error);
				}
			}

			var jsonHandler = new MessageJsonHandler(Collections.emptyMap());
			var producer = new TestProducer( new ByteArrayInputStream(inputStr.getBytes()), jsonHandler);
			var received = new ArrayList<>();
			producer.listen(received::add);
			producer.close();

			assertEquals("Both messages should be delivered", 2, received.size());
		}).get(TIMEOUT, TimeUnit.MILLISECONDS);
	}

	private static String header(int contentLength) {
		return "Content-Length: " + contentLength + "\r\n\r\n";
	}

}
