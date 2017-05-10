/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.jsonrpc.services.JsonDelegate;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;
import org.junit.Test;

public class EndpointsTest {
	
	private static final long TIMEOUT = 2000;
	
	@JsonSegment("foo")
	public static interface Foo {
		@JsonRequest
		public CompletableFuture<String> doStuff(String arg);
		
		@JsonNotification
		public void myNotification(String someArg);
		
		@JsonDelegate
		public Delegated getDelegate();
	}
	
	public static interface Delegated {
		
		@JsonNotification("hubba")
		public void myNotification(String someArg);
	}
	
	@JsonSegment("bar")
	public static interface Bar {
		@JsonRequest
		public CompletableFuture<String> doStuff(String arg, Integer arg2);
		
		@JsonNotification
		public void myNotification(String someArg, Integer someArg2);
		
		@JsonDelegate
		public BarDelegated getDelegate();
	}
	
	public static interface BarDelegated {
		
		@JsonNotification("hubba")
		public void myNotification(String someArg, Integer someArg2);
	}

	@Test public void testProxy_01() throws Exception {
		Endpoint endpoint = new Endpoint() {
			
			@Override
			public CompletableFuture<?> request(String method, Object parameter) {
				assertEquals("foo/doStuff", method);
				assertEquals("param", parameter.toString());
				return CompletableFuture.completedFuture("result");
			}
			
			@Override
			public void notify(String method, Object parameter) {
				assertEquals("foo/myNotification", method);
				assertEquals("notificationParam", parameter.toString());
			}
		};
		Foo foo = ServiceEndpoints.toServiceObject(endpoint, Foo.class);
		foo.myNotification("notificationParam");
		assertEquals("result", foo.doStuff("param").get(TIMEOUT, TimeUnit.MILLISECONDS));
	}
	
	@Test public void testProxy_02() throws Exception {
		Endpoint endpoint = new Endpoint() {
			
			@Override
			public CompletableFuture<?> request(String method, Object parameter) {
				assertEquals("bar/doStuff", method);
				assertEquals("[param, 2]", parameter.toString());
				return CompletableFuture.completedFuture("result");
			}
			
			@Override
			public void notify(String method, Object parameter) {
				assertEquals("bar/myNotification", method);
				assertEquals("[notificationParam, 1]", parameter.toString());
			}
		};
		Bar bar = ServiceEndpoints.toServiceObject(endpoint, Bar.class);
		bar.myNotification("notificationParam", 1);
		assertEquals("result", bar.doStuff("param", 2).get(TIMEOUT, TimeUnit.MILLISECONDS));
	}
	
	@Test public void testBackAndForth() throws Exception {
		Endpoint endpoint = new Endpoint() {
			
			@Override
			public CompletableFuture<?> request(String method, Object parameter) {
				assertEquals("foo/doStuff", method);
				assertEquals("param", parameter.toString());
				return CompletableFuture.completedFuture("result");
			}
			
			@Override
			public void notify(String method, Object parameter) {
				assertEquals("foo/myNotification", method);
				assertEquals("notificationParam", parameter.toString());
			}
		};
		Foo intermediateFoo = ServiceEndpoints.toServiceObject(endpoint, Foo.class);
		Endpoint secondEndpoint = ServiceEndpoints.toEndpoint(intermediateFoo); 
		Foo foo = ServiceEndpoints.toServiceObject(secondEndpoint, Foo.class);
		foo.myNotification("notificationParam");
		assertEquals("result", foo.doStuff("param").get(TIMEOUT, TimeUnit.MILLISECONDS));
	}
	
	@Test public void testRpcMethods_01() {
		Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(Foo.class);
		
		assertEquals("foo/doStuff", methods.get("foo/doStuff").getMethodName());
		assertEquals(String.class, methods.get("foo/doStuff").getParameterTypes()[0]);
		assertFalse(methods.get("foo/doStuff").isNotification());
		
		assertEquals("foo/myNotification", methods.get("foo/myNotification").getMethodName());
		assertEquals(String.class, methods.get("foo/myNotification").getParameterTypes()[0]);
		assertTrue(methods.get("foo/myNotification").isNotification());
		
		assertEquals("hubba", methods.get("hubba").getMethodName());
		assertEquals(String.class, methods.get("hubba").getParameterTypes()[0]);
		assertTrue(methods.get("hubba").isNotification());
	}
	
	@Test public void testRpcMethods_02() {
		Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(Bar.class);
		
		final JsonRpcMethod requestMethod = methods.get("bar/doStuff");
		assertEquals("bar/doStuff", requestMethod.getMethodName());
		assertEquals(2, requestMethod.getParameterTypes().length);
		assertEquals(String.class, requestMethod.getParameterTypes()[0]);
		assertEquals(Integer.class, requestMethod.getParameterTypes()[1]);
		assertFalse(requestMethod.isNotification());
		
		final JsonRpcMethod notificationMethod = methods.get("bar/myNotification");
		assertEquals("bar/myNotification", notificationMethod.getMethodName());
		assertEquals(2, notificationMethod.getParameterTypes().length);
		assertEquals(String.class, notificationMethod.getParameterTypes()[0]);
		assertEquals(Integer.class, notificationMethod.getParameterTypes()[1]);
		assertTrue(notificationMethod.isNotification());
		
		final JsonRpcMethod delegateMethod = methods.get("hubba");
		assertEquals("hubba", delegateMethod.getMethodName());
		assertEquals(2, delegateMethod.getParameterTypes().length);
		assertEquals(String.class, delegateMethod.getParameterTypes()[0]);
		assertEquals(Integer.class, delegateMethod.getParameterTypes()[1]);
		assertTrue(delegateMethod.isNotification());
	}
}
