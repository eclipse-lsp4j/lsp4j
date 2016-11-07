package org.eclipse.lsp4j.lsp4j.jsonrpc.validation;

import org.eclipse.lsp4j.jsonrpc.json.InvalidMessageException;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.jsonrpc.validation.ReflectiveMessageValidator;
import org.junit.Assert;
import org.junit.Test;

public class ReflectiveMessageValidatorTest {
	
	static class Foo {
		Foo nested;
		@NonNull String nonNullString;
		String nullableString;
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
			Assert.assertEquals("The property 'nonNullString' must have a non-null value.", ((ResponseError)e.getMessageObject()).getMessage());
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
			Assert.assertEquals("The property 'nonNullString' must have a non-null value.", ((ResponseError)e.getMessageObject()).getMessage());
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
}
