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
package org.eclipse.lsp4j.jsonrpc.test.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint;
import org.eclipse.lsp4j.jsonrpc.services.JsonDelegate;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.junit.Assert;
import org.junit.Test;

public class EndpointsTest {
	
	private static final long TIMEOUT = 2000;
	
	@JsonSegment("foo")
	public interface Foo {
		@JsonRequest CompletableFuture<String> doStuff(String arg);
		
		@JsonNotification void myNotification(String someArg);
		
		@JsonDelegate Delegated getDelegate();
	}
	
	public interface Delegated {
		
		@JsonNotification("hubba") void myNotification(String someArg);
	}
	
	@JsonSegment("bar")
	public interface Bar {
		@JsonRequest CompletableFuture<String> doStuff2(String arg, Integer arg2);
		
		@JsonNotification void myNotification2(String someArg, Integer someArg2);
		
		@JsonDelegate BarDelegated getDelegate2();
	}
	
	public interface BarDelegated {
		
		@JsonNotification("hubba") void myNotification(String someArg, Integer someArg2);
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
				assertEquals("bar/doStuff2", method);
				assertEquals("[param, 2]", parameter.toString());
				return CompletableFuture.completedFuture("result");
			}
			
			@Override
			public void notify(String method, Object parameter) {
				assertEquals("bar/myNotification2", method);
				assertEquals("[notificationParam, 1]", parameter.toString());
			}
		};
		Bar bar = ServiceEndpoints.toServiceObject(endpoint, Bar.class);
		bar.myNotification2("notificationParam", 1);
		assertEquals("result", bar.doStuff2("param", 2).get(TIMEOUT, TimeUnit.MILLISECONDS));
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
	
	@Test public void testMultipleInterfaces() throws Exception {
		final Map<String, Object> requests = new HashMap<>();
		final Map<String, Object> notifications = new HashMap<>();
		Endpoint endpoint = new Endpoint() {
			@Override
			public CompletableFuture<?> request(String method, Object parameter) {
				requests.put(method, parameter);
				switch (method) {
				case "foo/doStuff":
					assertEquals("paramFoo", parameter);
					return CompletableFuture.completedFuture("resultFoo");
				case "bar/doStuff2":
					assertEquals(Arrays.asList("paramBar", 77), parameter);
					return CompletableFuture.completedFuture("resultBar");
				default:
					Assert.fail("Unexpected method: " + method);
					return null;
				}
			}
			
			@Override
			public void notify(String method, Object parameter) {
				notifications.put(method, parameter);
			}
		};
		ClassLoader classLoader = getClass().getClassLoader();
		Object proxy = ServiceEndpoints.toServiceObject(endpoint, Arrays.asList(Foo.class, Bar.class), classLoader);
		Foo foo = (Foo) proxy;
		foo.myNotification("notificationParamFoo");
		assertEquals("resultFoo", foo.doStuff("paramFoo").get(TIMEOUT, TimeUnit.MILLISECONDS));
		Bar bar = (Bar) proxy;
		bar.myNotification2("notificationParamBar", 42);
		assertEquals("resultBar", bar.doStuff2("paramBar", 77).get(TIMEOUT, TimeUnit.MILLISECONDS));
		
		assertEquals(2, requests.size());
		assertEquals(2, notifications.size());
		assertEquals("notificationParamFoo", notifications.get("foo/myNotification"));
		assertEquals(Arrays.asList("notificationParamBar", 42), notifications.get("bar/myNotification2"));
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
		
		final JsonRpcMethod requestMethod = methods.get("bar/doStuff2");
		assertEquals("bar/doStuff2", requestMethod.getMethodName());
		assertEquals(2, requestMethod.getParameterTypes().length);
		assertEquals(String.class, requestMethod.getParameterTypes()[0]);
		assertEquals(Integer.class, requestMethod.getParameterTypes()[1]);
		assertFalse(requestMethod.isNotification());
		
		final JsonRpcMethod notificationMethod = methods.get("bar/myNotification2");
		assertEquals("bar/myNotification2", notificationMethod.getMethodName());
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
	
	@JsonSegment("consumer")
	public interface StringConsumer extends Consumer<String> {
		@JsonNotification
		@Override
		void accept(String message);
	}
	
	@Test public void testIssue106() {
		Foo foo = ServiceEndpoints.toServiceObject(new GenericEndpoint(new Object()), Foo.class);
		assertEquals(foo, foo);
	}
	
	@Test public void testIssue107() {
		Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(StringConsumer.class);
		final JsonRpcMethod method = methods.get("consumer/accept");
		assertEquals("consumer/accept", method.getMethodName());
		assertEquals(1, method.getParameterTypes().length);
		assertEquals(String.class, method.getParameterTypes()[0]);
		assertTrue(method.isNotification());
	}
	
}
