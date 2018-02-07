/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.jsonrpc.validation;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.MessageIssue;
import org.eclipse.lsp4j.jsonrpc.messages.MessageIssue.InvalidMessageException;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;

import com.google.gson.JsonElement;

/**
 * Validates messages and forwards them to other message consumers. In case an issue is found,
 * a {@link MessageIssue} is attached to the message and the downstream consumers are responsible
 * for handling it.
 */
public class ReflectiveMessageValidator implements MessageConsumer {

	private static final Logger LOG = Logger.getLogger(ReflectiveMessageValidator.class.getName());

	private final MessageConsumer delegate;
	
	/**
	 * When a validator is created without a downstream consumer, an exception is thrown whenever
	 * an issue is found.
	 */
	public ReflectiveMessageValidator() {
		this.delegate = null;
	}

	/**
	 * Forward all messages to the given consumer. When an issue is found, it is attached to the
	 * corresponding message and the downstream consumer is responsible for handling it.
	 */
	public ReflectiveMessageValidator(MessageConsumer delegate) {
		this.delegate = delegate;
	}

	@Override
	public void consume(Message message) throws InvalidMessageException {
		List<String> result = new ArrayList<>();
		try {
			validate(message, result, new LinkedList<>());
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error during message validation: " + e.getMessage(), e);
			result.add("Message validation failed, please check the logs of the remote endpoint.");
		}
		
		if (!result.isEmpty() && message.getIssue() == null) {
			String errorMessage = result.stream().collect(Collectors.joining(", "));
			message.setIssue(new MessageIssue(errorMessage, ResponseErrorCode.InvalidParams.getValue()));
		}
		
		if (delegate != null) {
			// If an issue was found, the downstream consumer is responsible for handling it
			delegate.consume(message);
		} else if (message.getIssue() != null) {
			// This validator is the last member of the consumer chain - throw an exception to notify the caller
			throw message.getIssue().asThrowable();
		}
	}
	
	/**
	 * Validate all fields of the given object.
	 */
	protected void validate(Object object, List<String> issues, LinkedList<Object> objectStack) throws Exception {
		if (object == null 
				|| object instanceof Enum<?> 
				|| object instanceof String 
				|| object instanceof Number
				|| object instanceof Boolean
				|| object instanceof JsonElement
				|| object instanceof Throwable) {
			return;
		}
		if (objectStack.contains(object)) {
			issues.add("An element of the message has a direct or indirect reference to itself.");
			return;
		}
		objectStack.push(object);
		if (object instanceof List<?>) {
			for (Object obj : (List<?>) object) {
				if (obj == null) {
					issues.add("Lists must not contain null references.");
				}
				validate(obj, issues, objectStack);
			}
		} else {
			for (Method method : object.getClass().getMethods()) {
				if (isGetter(method)) {
					method.setAccessible(true);
					Object value = method.invoke(object);
					if (value == null && method.getAnnotation(NonNull.class) != null) {
						issues.add("The accessor '" + method.getDeclaringClass().getSimpleName() + "."
								+ method.getName() + "()' must return a non-null value.");
					}
					validate(value, issues, objectStack);
				}
			}
		}
		objectStack.pop();
	}

	protected boolean isGetter(Method method) {
		return method.getParameterCount() == 0 && method.getName().startsWith("get")
				&& method.getDeclaringClass() != Object.class && !Modifier.isStatic(method.getModifiers());
	}
}
