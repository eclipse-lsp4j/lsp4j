/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.lsp4j.jsonrpc.json.InvalidMessageException;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.jsonrpc.validation.ReflectiveMessageValidator;
import org.junit.Assert;
import org.junit.Test;

public class ReflectiveMessageValidatorTest {
	
	static class Foo {
		// a non exposed self reference
		Foo self = this;
		
		Foo nested;
		String nonNullString;
		String nullableString;
		List<Foo> foos;
		
		public Foo getNested() {
			return nested;
		}
		 
		public @NonNull String getNonNullString() {
			return nonNullString;
		}
		
		public String getNullableString() {
			return nullableString;
		}
		
		public List<Foo> getFoos() {
			return foos;
		}
	}
	
	@Test public void testNonNullViolated() {
		ReflectiveMessageValidator validator = new ReflectiveMessageValidator((message) -> {});
		
		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		message.setParams(new Foo());
		try {
			validator.consume(message);
			Assert.fail();
		} catch (InvalidMessageException e) {
			Assert.assertEquals("The accessor 'getNonNullString' must return a non-null value.", ((ResponseError)e.getMessageObject()).getMessage());
		}
	}
	
	@Test public void testNonNullViolated_nested() {
		ReflectiveMessageValidator validator = new ReflectiveMessageValidator((message) -> {});
		
		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		Foo foo = new Foo();
		foo.nonNullString = "test";
		foo.nested = new Foo();
		message.setParams(foo);
		try {
			validator.consume(message);
			Assert.fail();
		} catch (InvalidMessageException e) {
			Assert.assertEquals("The accessor 'getNonNullString' must return a non-null value.", ((ResponseError)e.getMessageObject()).getMessage());
		}
	}
	
	@Test public void testNoViolation() {
		ReflectiveMessageValidator validator = new ReflectiveMessageValidator((message) -> {});
		
		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		Foo foo = new Foo();
		foo.nonNullString = "test";
		foo.nested = new Foo() {{
			nonNullString = "something";
		}};
		message.setParams(foo);
		validator.consume(message);
	}
	
	@Test public void testRecursionViolation() {
		ReflectiveMessageValidator validator = new ReflectiveMessageValidator((message) -> {});
		
		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		Foo foo = new Foo();
		foo.nonNullString = "test";
		foo.nested = foo;
		message.setParams(foo);
		try {
			validator.consume(message);
			Assert.fail();
		} catch (InvalidMessageException e) {
			Assert.assertEquals("An element of the message has a direct or indirect reference to itself.", ((ResponseError)e.getMessageObject()).getMessage());
		}
	}
	
	@Test public void testReflectionOnPropertiesOnly() {
		ReflectiveMessageValidator validator = new ReflectiveMessageValidator((message) -> {});
		
		NotificationMessage message = new NotificationMessage();
		message.setMethod("foo");
		Foo foo = new Foo();
		foo.nonNullString = "test";
		foo.nested = new Foo() {{
			nonNullString = "something";
		}};
		foo.foos = new ArrayList<>();
		foo.foos.add(new Foo());
		message.setParams(foo);
		try {
			validator.consume(message);
			Assert.fail();
		} catch (InvalidMessageException e) {
			Assert.assertEquals("The accessor 'getNonNullString' must return a non-null value.", ((ResponseError)e.getMessageObject()).getMessage());
		}
	}
}
