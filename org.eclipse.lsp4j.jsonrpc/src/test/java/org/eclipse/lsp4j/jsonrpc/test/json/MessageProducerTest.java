/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
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
	
	@Test(expected=JsonRpcException.class)
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

}
