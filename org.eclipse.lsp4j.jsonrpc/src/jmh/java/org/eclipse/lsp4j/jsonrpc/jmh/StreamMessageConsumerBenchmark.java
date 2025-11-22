/******************************************************************************
 * Copyright (c) 2025 Kichwa Coders Canada, Inc. and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.jmh;

import static java.util.Collections.emptyMap;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageConsumer;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class StreamMessageConsumerBenchmark {
	private StreamMessageConsumer consumer;
	private RequestMessage message;

	@SuppressWarnings("resource")
	@Setup
	public void setup() {
		consumer = new StreamMessageConsumer(OutputStream.nullOutputStream(), new MessageJsonHandler(emptyMap()));
		message = new RequestMessage();
		message.setId("1");
		message.setMethod("foo");
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < 100; i++) {
			map.put(String.valueOf(i), "X".repeat(i));
		}
		message.setParams(map);
	}

	@Benchmark
	public void measure() {
		consumer.consume(message);
	}
}
