/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.MessageIssueHandler;
import org.eclipse.lsp4j.jsonrpc.MessageProducer;

/**
 * This class connects a message producer with a message consumer by listening for new messages in a dedicated thread.
 */
public class ConcurrentMessageProcessor implements Runnable {
	
	/**
	 * Start a thread that listens for messages in the message producer and forwards them to the message consumer.
	 * 
	 * <em>Note:</em> No issue handler is set up with this method, so when erroneous requests are received,
	 * no response will be created for them.
	 * 
	 * @param messageProducer - produces messages, e.g. by reading from an input channel
	 * @param messageConsumer - processes messages and potentially forwards them to other consumers
	 * @param executorService - the thread is started using this service
	 * @return a future that is resolved when the started thread is terminated, e.g. by closing a stream
	 */
	public static Future<Void> startProcessing(MessageProducer messageProducer, MessageConsumer messageConsumer,
			ExecutorService executorService) {
		return startProcessing(messageProducer, messageConsumer, null);
	}

	/**
	 * Start a thread that listens for messages in the message producer, forwards them to the message consumer,
	 * and reports issues to the issue handler
	 * 
	 * @param messageProducer - produces messages, e.g. by reading from an input channel
	 * @param messageConsumer - processes messages and potentially forwards them to other consumers
	 * @param issueHandler - issues found in produced messages are reported to this handler
	 * @param executorService - the thread is started using this service
	 * @return a future that is resolved when the started thread is terminated, e.g. by closing a stream
	 */
	public static Future<Void> startProcessing(MessageProducer messageProducer, MessageConsumer messageConsumer,
			MessageIssueHandler issueHandler, ExecutorService executorService) {
		ConcurrentMessageProcessor reader = new ConcurrentMessageProcessor(messageProducer, messageConsumer, issueHandler);
		final Future<?> result = executorService.submit(reader);
		return new Future<Void>() {

			@Override
			public Void get() throws InterruptedException, ExecutionException {
				return (Void) result.get();
			}

			@Override
			public Void get(long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException, TimeoutException {
				return (Void) result.get(timeout, unit);
			}

			@Override
			public boolean isDone() {
				return result.isDone();
			}

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				if (mayInterruptIfRunning && messageProducer instanceof Closeable) {
					try {
						((Closeable) messageProducer).close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
				return result.cancel(mayInterruptIfRunning);
			}

			@Override
			public boolean isCancelled() {
				return result.isCancelled();
			}
		};
	}

	private static final Logger LOG = Logger.getLogger(ConcurrentMessageProcessor.class.getName());

	private boolean isRunning;

	private final MessageProducer messageProducer;
	private final MessageConsumer messageConsumer;
	private final MessageIssueHandler issueHandler;
	
	public ConcurrentMessageProcessor(MessageProducer messageProducer, MessageConsumer messageConsumer) {
		this(messageProducer, messageConsumer, null);
	}

	public ConcurrentMessageProcessor(MessageProducer messageProducer, MessageConsumer messageConsumer,
			MessageIssueHandler issueHandler) {
		this.messageProducer = messageProducer;
		this.messageConsumer = messageConsumer;
		this.issueHandler = issueHandler;
	}

	public void run() {
		if (isRunning) {
			throw new IllegalStateException("The message processor is already running.");
		}
		isRunning = true;
		try {
			messageProducer.listen(messageConsumer, issueHandler);
		} catch (Exception e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			isRunning = false;
		}
	}

}
