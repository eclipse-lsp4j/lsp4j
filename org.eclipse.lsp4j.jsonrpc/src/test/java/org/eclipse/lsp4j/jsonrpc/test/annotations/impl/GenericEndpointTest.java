/******************************************************************************
 * Copyright (c) 2016, 2024 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.annotations.impl;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.logging.Level;

import org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint;
import org.eclipse.lsp4j.jsonrpc.services.JsonDelegate;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;
import org.eclipse.lsp4j.jsonrpc.test.LogMessageAccumulator;
import org.junit.Assert;
import org.junit.Test;

public class GenericEndpointTest {

	public static class Foo implements MyIf, OtherThing {

		public int calls = 0;

		@Override
		public void myNotification() {
			calls++;
		}

		@Override
		public OtherThing doDelegate() {
			return this;
		}

	}

	public static class DelegateThrows {
		@JsonDelegate
		public OtherThing doDelegate() throws Exception {
			throw new Exception("Exception in doDelegate");
		}
	}

	public static class DuplicateNames1 {
		@JsonNotification
		public void duplicateName() {
			Assert.fail("Should be unreachable");
		}
	}
	public static class DuplicateNames2 {
		@JsonNotification
		public void duplicateName() {
			Assert.fail("Should be unreachable");
		}
	}
	
	public static class Bar {
		
		public int calls = 0;
		
		@JsonNotification
		public void barrr() {
			calls++;
		}
		
	}

	public interface MyIf {

		@JsonNotification void myNotification();

		@JsonDelegate OtherThing doDelegate();
	}

	@JsonSegment("other")
	public interface OtherThing {

		@JsonNotification void myNotification();
	}

	@Test
	public void testSimple() {
		Foo foo = new Foo();
		GenericEndpoint endpoint = new GenericEndpoint(foo);
		endpoint.notify("myNotification", null);
		endpoint.notify("other/myNotification", null);

		Assert.assertEquals(2, foo.calls);
	}

	@SuppressWarnings("unused")
	@Test
	public void testDelegateThrows() {
		DelegateThrows delegateThrows = new DelegateThrows();
		try {
			new GenericEndpoint(delegateThrows);
			Assert.fail("Find delegate methods did not have expected exception raised when FooThrows.doDelegate was called.");
		} catch (IllegalStateException e) {
			Throwable t = e;
			do {
				if ("Exception in doDelegate".equals(t.getMessage())) {
					// test passes
					break;
				}
			} while ((t = t.getCause()) != null);
			Assert.assertNotNull("Failed to find excepted exception in cause chain", t);
		}
	}

	@Test
	public void testMultiServices() {
		Foo foo = new Foo();
		Bar bar = new Bar();
		GenericEndpoint endpoint = new GenericEndpoint(Arrays.asList(foo, bar));
		endpoint.notify("myNotification", null);
		endpoint.notify("barrr", null);
		endpoint.notify("other/myNotification", null);
		
		Assert.assertEquals(2, foo.calls);
		Assert.assertEquals(1, bar.calls);
	}

	@SuppressWarnings("unused")
	@Test
	public void testDuplicateNamesGeneratesError() {
		DuplicateNames1 duplicateNames1 = new DuplicateNames1();
		DuplicateNames2 duplicateNames2 = new DuplicateNames2();
		try {
			new GenericEndpoint(Arrays.asList(duplicateNames1, duplicateNames2));
			Assert.fail("Expected exception about duplicated names not thrown");
		} catch (IllegalStateException e) {
			// test passes
		}
	}

	@Test
	public void testUnexpectedParams() {
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(GenericEndpoint.class);
			
			Foo foo = new Foo();
			GenericEndpoint endpoint = new GenericEndpoint(foo);
			Assert.assertEquals(0, foo.calls);
	
			endpoint.notify("myNotification", new Object());
			Assert.assertEquals(1, foo.calls);
		} finally {
			logMessages.unregister();
		}
	}
	
	@Test
	public void testZeroParams_01() throws Exception {
		testZeroParams("foo", m -> m.contains("Unexpected params 'foo'"));
	}
	
	@Test
	public void testZeroParams_02() throws Exception {
		testZeroParams(null);
	}
	
	@Test
	public void testZeroParams_03() throws Exception {
		testZeroParams(Arrays.asList("foo"), m -> m.contains("Unexpected params '[foo]'"));
	}
	
	@Test
	public void testZeroParams_04() throws Exception {
		testZeroParams(Arrays.asList("foo", "bar"), m -> m.contains("Unexpected params '[foo, bar]'"));
	}
	
	protected void testZeroParams(Object params) throws Exception {
		testZeroParams(params, null);
	}

	protected void testZeroParams(Object params, Predicate<String> predicate) throws Exception {
		LogMessageAccumulator logMessages = null;
		try {
			if (predicate != null) {
				logMessages = new LogMessageAccumulator();
				logMessages.registerTo(GenericEndpoint.class);
			}
			GenericEndpoint endpoint = new GenericEndpoint(new Object() {

				@JsonNotification
				public void myNotification() {
				}

			});
		
			endpoint.notify("myNotification", params);

			if (predicate != null && logMessages != null) {
				logMessages.await(r -> Level.WARNING == r.getLevel() && predicate.test(r.getMessage()));
			}
		} finally {
			if (logMessages != null) {
				logMessages.unregister();
			}
		}
	}
	
	@Test
	public void testSingleParams_01() throws Exception {
		testSingleParams("foo", "foo");
	}
	
	@Test
	public void testSingleParams_02() throws Exception {
		testSingleParams(null, null);
	}
	
	@Test
	public void testSingleParams_03() throws Exception {
		testSingleParams(Arrays.asList("foo"), "foo");
	}
	
	@Test
	public void testSingleParams_04() throws Exception {
		testSingleParams(Arrays.asList("foo", "bar"), "foo", m -> m.contains("Unexpected params 'bar'"));
	}
	
	protected void testSingleParams(Object params, String expectedString) throws Exception {
		this.testSingleParams(params, expectedString, null);
	}

	protected void testSingleParams(Object params, String expectedString, Predicate<String> predicate) throws Exception {
		LogMessageAccumulator logMessages = null;
		try {
			if (predicate != null) {
				logMessages = new LogMessageAccumulator();
				logMessages.registerTo(GenericEndpoint.class);
			}
			GenericEndpoint endpoint = new GenericEndpoint(new Object() {

				@JsonRequest
				public CompletableFuture<String> getStringValue(String stringValue) {
					return CompletableFuture.completedFuture(stringValue);
				}

			});

			Assert.assertEquals(expectedString, endpoint.request("getStringValue", params).get());

			if (predicate != null && logMessages != null) {
				logMessages.await(r -> Level.WARNING == r.getLevel() && predicate.test(r.getMessage()));
			}
		} finally {
			if (logMessages != null) {
				logMessages.unregister();
			}
		}
	}

	@Test
	public void testMultiParams_01() throws Exception {
		testMultiParams(Arrays.asList("foo", 1), "foo", 1);
	}

	@Test
	public void testMultiParams_02() throws Exception {
		testMultiParams(Arrays.asList("foo"), "foo", null);
	}

	@Test
	public void testMultiParams_03() throws Exception {
		testMultiParams(Arrays.asList("foo", 1, "bar", 2), "foo", 1, m -> m.contains("Unexpected params 'bar', '2'"));
	}

	@Test
	public void testMultiParams_04() throws Exception {
		testMultiParams("foo", "foo", null);
	}
	
	protected void testMultiParams(Object params, String expectedString, Integer expectedInt) throws Exception {
		testMultiParams(params, expectedString, expectedInt, null);
	}

	protected void testMultiParams(Object params, String expectedString, Integer expectedInt, Predicate<String> predicate) throws Exception {
		LogMessageAccumulator logMessages = null;
		try {
			if (predicate != null) {
				logMessages = new LogMessageAccumulator();
				logMessages.registerTo(GenericEndpoint.class);
			}

			GenericEndpoint endpoint = new GenericEndpoint(new Object() {

				String stringValue;
				Integer intValue;

				@JsonRequest
				public CompletableFuture<String> getStringValue() {
					return CompletableFuture.completedFuture(stringValue);
				}

				@JsonRequest
				public CompletableFuture<Integer> getIntValue() {
					return CompletableFuture.completedFuture(intValue);
				}

				@JsonNotification
				public void myNotification(String stringValue, Integer intValue) {
					this.stringValue = stringValue;
					this.intValue = intValue;
				}

			});
			endpoint.notify("myNotification", params);

			if (predicate != null && logMessages != null) {
				logMessages.await(r -> Level.WARNING == r.getLevel() && predicate.test(r.getMessage()));
			}

			Assert.assertEquals(expectedString, endpoint.request("getStringValue", null).get());
			Assert.assertEquals(expectedInt, endpoint.request("getIntValue", null).get());
		} finally {
			if (logMessages != null) {
				logMessages.unregister();
			}
		}
	}

}
