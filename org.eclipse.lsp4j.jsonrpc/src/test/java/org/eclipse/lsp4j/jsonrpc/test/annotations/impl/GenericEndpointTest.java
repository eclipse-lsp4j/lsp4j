/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.annotations.impl;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
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

	public static interface MyIf {

		@JsonNotification
		public void myNotification();

		@JsonDelegate
		public OtherThing doDelegate();
	}

	@JsonSegment("other")
	public static interface OtherThing {

		@JsonNotification
		public void myNotification();
	}

	@Test
	public void testSimple() {

		Foo foo = new Foo();
		GenericEndpoint endpoint = new GenericEndpoint(foo);
		endpoint.notify("myNotification", null);
		endpoint.notify("other/myNotification", null);

		Assert.assertEquals(2, foo.calls);
	}

	@Test
	public void testUnexpectedParams() {
		Foo foo = new Foo();
		GenericEndpoint endpoint = new GenericEndpoint(foo);
		Assert.assertEquals(0, foo.calls);

		endpoint.notify("myNotification", new Object());
		Assert.assertEquals(1, foo.calls);
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
		testMultiParams(Arrays.asList("foo", 1, "bar", 2), "foo", 1,
				"Unexpected params '[bar, 2]' for 'public void org.eclipse.lsp4j.jsonrpc.test.annotations.impl.GenericEndpointTest$1.myNotification(java.lang.String,java.lang.Integer)' is ignored");
	}

	@Test
	public void testMultiParams_04() throws Exception {
		testMultiParams("foo", "foo", null);
	}

	protected void testMultiParams(Object params, String expectedString, Integer expectedInt,
			String... expectedWarnings) throws Exception {
		LogMessageAccumulator logMessages = new LogMessageAccumulator();
		try {
			logMessages.registerTo(GenericEndpoint.class.getName());

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

			for (String expectedWarning : expectedWarnings) {
				logMessages.await(Level.WARNING, expectedWarning);
			}

			Assert.assertEquals(expectedString, endpoint.request("getStringValue", null).get());
			Assert.assertEquals(expectedInt, endpoint.request("getIntValue", null).get());
		} finally {
			logMessages.unregister();
		}
	}

}
