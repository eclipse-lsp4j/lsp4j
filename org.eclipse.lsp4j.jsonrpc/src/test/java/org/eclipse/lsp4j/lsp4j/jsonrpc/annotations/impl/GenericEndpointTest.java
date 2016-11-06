package org.eclipse.lsp4j.lsp4j.jsonrpc.annotations.impl;

import org.eclipse.lsp4j.jsonrpc.annotations.JsonDelegate;
import org.eclipse.lsp4j.jsonrpc.annotations.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.annotations.JsonSegment;
import org.eclipse.lsp4j.jsonrpc.annotations.impl.GenericEndpoint;
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
}
