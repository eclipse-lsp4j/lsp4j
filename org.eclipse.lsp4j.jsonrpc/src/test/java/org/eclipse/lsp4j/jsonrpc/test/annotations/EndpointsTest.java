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

	@Test public void testProxy() throws Exception {
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
	
	@Test public void testRpcMethods() {
		Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(Foo.class);
		
		assertEquals("foo/doStuff", methods.get("foo/doStuff").getMethodName());
		assertEquals(String.class, methods.get("foo/doStuff").getParameterType());
		assertFalse(methods.get("foo/doStuff").isNotification());
		
		assertEquals("foo/myNotification", methods.get("foo/myNotification").getMethodName());
		assertEquals(String.class, methods.get("foo/myNotification").getParameterType());
		assertTrue(methods.get("foo/myNotification").isNotification());
		
		assertEquals("hubba", methods.get("hubba").getMethodName());
		assertEquals(String.class, methods.get("hubba").getParameterType());
		assertTrue(methods.get("hubba").isNotification());
	}
}
