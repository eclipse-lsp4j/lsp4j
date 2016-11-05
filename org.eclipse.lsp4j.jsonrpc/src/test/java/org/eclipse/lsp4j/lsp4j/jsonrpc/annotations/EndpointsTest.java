package org.eclipse.lsp4j.lsp4j.jsonrpc.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.RpcMethod;
import org.eclipse.lsp4j.jsonrpc.annotations.Endpoints;
import org.eclipse.lsp4j.jsonrpc.annotations.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.annotations.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.annotations.JsonSegment;
import org.junit.Test;

public class EndpointsTest {
	
	@JsonSegment("foo")
	public static interface Foo {
		@JsonRequest
		public CompletableFuture<String> doStuff(String arg);
		
		@JsonNotification
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
		Foo foo = Endpoints.toServiceObject(endpoint, Foo.class);
		foo.myNotification("notificationParam");
		assertEquals("result", foo.doStuff("param").get());
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
		Foo intermediateFoo = Endpoints.toServiceObject(endpoint, Foo.class);
		Endpoint secondEndpoint = Endpoints.toEndpoint(intermediateFoo); 
		Foo foo = Endpoints.toServiceObject(secondEndpoint, Foo.class);
		foo.myNotification("notificationParam");
		assertEquals("result", foo.doStuff("param").get());
	}
	
	@Test public void testRpcMethods() {
		Map<String, RpcMethod> methods = Endpoints.getSupportedMethods(Foo.class);
		
		assertEquals("foo/doStuff", methods.get("foo/doStuff").getMethodName());
		assertEquals(String.class, methods.get("foo/doStuff").getParameterType());
		assertFalse(methods.get("foo/doStuff").isNotification());
		
		assertEquals("foo/myNotification", methods.get("foo/myNotification").getMethodName());
		assertEquals(String.class, methods.get("foo/myNotification").getParameterType());
		assertTrue(methods.get("foo/myNotification").isNotification());
	}
}
