/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.annotations.impl;

import org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint;
import org.eclipse.lsp4j.jsonrpc.services.JsonDelegate;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;
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

	@Test public void testSimple() {
		
		Foo foo = new Foo();
		GenericEndpoint endpoint = new GenericEndpoint(foo);
		endpoint.notify("myNotification", null);
		endpoint.notify("other/myNotification", null);
		
		Assert.assertEquals(2, foo.calls);
	}
	
	@Test public void testUnexpectedParams() {
		Foo foo = new Foo();
		GenericEndpoint endpoint = new GenericEndpoint(foo);
		Assert.assertEquals(0, foo.calls);

		endpoint.notify("myNotification", new Object());
		Assert.assertEquals(1, foo.calls);
	}
}
