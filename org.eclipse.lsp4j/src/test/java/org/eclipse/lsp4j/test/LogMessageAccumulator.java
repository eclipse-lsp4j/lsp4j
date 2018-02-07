/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.Assert;

public class LogMessageAccumulator extends Handler {
	
	private static final long TIMEOUT = 2000;
	
	private final List<LogRecord> records = new ArrayList<>();
	
	private final List<Logger> registeredLoggers = new ArrayList<>();
	
	public Logger registerTo(Class<?> clazz) {
		return registerTo(clazz.getName());
	}
	
	public Logger registerTo(String name) {
		Logger logger = Logger.getLogger(name);
		logger.setUseParentHandlers(false);
		logger.addHandler(this);
		registeredLoggers.add(logger);
		return logger;
	}
	
	public void unregister() {
		for (Logger logger : registeredLoggers) {
			logger.removeHandler(this);
			logger.setUseParentHandlers(true);
		}
		registeredLoggers.clear();
	}
	
	public List<LogRecord> getRecords() {
		return records;
	}
	
	public LogRecord findRecord(Level level, String message) {
		synchronized (records) {
			for (LogRecord r : records) {
				if (level.equals(r.getLevel()) && message.equals(r.getMessage()))
					return r;
			}
			return null;
		}
	}
	
	public Optional<LogRecord> match(Predicate<LogRecord> predicate) {
		synchronized (records) {
			return records.stream().filter(predicate).findFirst();
		}
	} 

	@Override
	public void publish(LogRecord record) {
		synchronized (records) {
			records.add(record);
		}
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}

	public void await(Predicate<LogRecord> predicate) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		while (!match(predicate).isPresent()) {
			Thread.sleep(20);
			if (System.currentTimeMillis() - startTime > TIMEOUT) {
				synchronized (records) {
					String records = this.records.stream().map(r -> r.getLevel() + ": " + r.getMessage()).reduce((a, a2) -> a + '\n' + a2).get();
					Assert.fail("Timeout elapsed while waiting for logging, logged:\n" + records);
				}
			}
		}
	}
	
	public void await(Level level, String message) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		while (findRecord(level, message) == null) {
			Thread.sleep(20);
			if (System.currentTimeMillis() - startTime > TIMEOUT)
				Assert.fail("Timeout elapsed while waiting for " + level + ": " + message);
		}
	}

}
