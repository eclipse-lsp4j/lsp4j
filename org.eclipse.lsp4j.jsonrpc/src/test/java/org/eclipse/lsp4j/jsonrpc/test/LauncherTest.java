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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class LauncherTest {
	
	private static final long TIMEOUT = 2000;
	
	static class Param {
		Param() {
		}
		Param(String message) {
			this.message = message;
		}
		public String message;
	}
	
	interface A {
		@JsonNotification void say(Param p);
	}
	
	interface B {
		@JsonRequest CompletableFuture<String> ask(Param p);
	}

	@Test public void testDone() throws Exception {
		A a = new A() {
			@Override
			public void say(Param p) {
			}
		};
		Launcher<A> launcher = Launcher.createLauncher(a, A.class, new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());
		Future<?> startListening = launcher.startListening();
		startListening.get(TIMEOUT, TimeUnit.MILLISECONDS);
		Assert.assertTrue(startListening.isDone());
		Assert.assertFalse(startListening.isCancelled());
	}
	
	@Test public void testCanceled() throws Exception {
		A a = new A() {
			@Override
			public void say(Param p) {
			}
		};
		Launcher<A> launcher = Launcher.createLauncher(a, A.class, new InputStream() {
			@Override
			public int read() throws IOException {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				return '\n';
			}
		}, new ByteArrayOutputStream());
		Future<?> startListening = launcher.startListening();
		startListening.cancel(true);
		Assert.assertTrue(startListening.isDone());
		Assert.assertTrue(startListening.isCancelled());
	}
	
	@Test public void testCustomGson() throws Exception {
		A a = new A() {
			@Override
			public void say(Param p) {
			}
		};
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		TypeAdapter<Param> typeAdapter = new TypeAdapter<Param>() {
			@Override
			public void write(JsonWriter out, Param value) throws IOException {
				out.beginObject();
				out.name("message");
				out.value("bar");
				out.endObject();
			}
			@Override
			public Param read(JsonReader in) throws IOException {
				return null;
			}
		};
		Launcher<A> launcher = Launcher.createIoLauncher(a, A.class, new ByteArrayInputStream("".getBytes()), out,
				Executors.newCachedThreadPool(), c -> c,
				gsonBuilder -> {gsonBuilder.registerTypeAdapter(Param.class, typeAdapter);});
		A remoteProxy = launcher.getRemoteProxy();
		
		remoteProxy.say(new Param("foo"));
		Assert.assertEquals("Content-Length: 59\r\n\r\n"
				+ "{\"jsonrpc\":\"2.0\",\"method\":\"say\",\"params\":{\"message\":\"bar\"}}",
				out.toString());
	}
	
	@Test public void testMultipleServices() throws Exception {
		final String[] paramA = new String[1];
		A a = new A() {
			@Override
			public void say(Param p) {
				paramA[0] = p.message;
			}
		};
		final String[] paramB = new String[1];
		B b = new B() {
			@Override
			public CompletableFuture<String> ask(Param p) {
				paramB[0] = p.message;
				return CompletableFuture.completedFuture("echo " + p.message);
			}
		};
		String inputMessages = "Content-Length: 60\r\n\r\n"
			+ "{\"jsonrpc\":\"2.0\",\"method\":\"say\",\"params\":{\"message\":\"foo1\"}}"
			+ "Content-Length: 69\r\n\r\n"
			+ "{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"method\":\"ask\",\"params\":{\"message\":\"bar1\"}}";
		ByteArrayInputStream in = new ByteArrayInputStream(inputMessages.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		ClassLoader classLoader = getClass().getClassLoader();
		Launcher<Object> launcher = Launcher.createIoLauncher(Arrays.asList(a, b), Arrays.asList(A.class, B.class),
				classLoader, in, out, Executors.newCachedThreadPool(), c -> c, null);
		
		launcher.startListening().get(TIMEOUT, TimeUnit.MILLISECONDS);
		assertEquals("foo1", paramA[0]);
		assertEquals("bar1", paramB[0]);
		
		Object remoteProxy = launcher.getRemoteProxy();
		((A) remoteProxy).say(new Param("foo2"));
		((B) remoteProxy).ask(new Param("bar2"));
		Assert.assertEquals("Content-Length: 47\r\n\r\n" 
				+ "{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"result\":\"echo bar1\"}"
				+ "Content-Length: 60\r\n\r\n"
				+ "{\"jsonrpc\":\"2.0\",\"method\":\"say\",\"params\":{\"message\":\"foo2\"}}"
				+ "Content-Length: 69\r\n\r\n"
				+ "{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"method\":\"ask\",\"params\":{\"message\":\"bar2\"}}",
				out.toString());
	}
	
}
