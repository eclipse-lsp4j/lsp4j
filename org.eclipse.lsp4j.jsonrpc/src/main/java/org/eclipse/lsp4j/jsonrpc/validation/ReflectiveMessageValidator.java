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
import java.util.stream.Collectors;

import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.json.InvalidMessageException;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;

public class ReflectiveMessageValidator implements MessageConsumer {

	private MessageConsumer delegate;

	public ReflectiveMessageValidator(final MessageConsumer delegate) {
		this.delegate = delegate;
	}

	@Override
	public void consume(Message message) {
		List<String> result = new ArrayList<>();
		try {
			validate(message, result, new LinkedList<>());
		} catch (Exception e) {
			result.add("Error during message validation: " + e.getMessage());
		}
		if (!result.isEmpty()) {
			ResponseError error = new ResponseError(ResponseErrorCode.InvalidParams,
					result.stream().collect(Collectors.joining(", ")), message);
			throw new InvalidMessageException(error.getMessage(), error, null);
		} else if (delegate != null) {
			delegate.consume(message);
		}
	}

	protected void validate(Object object, List<String> issues, LinkedList<Object> objectStack) throws Exception {
		if (object == null 
				|| object instanceof Enum<?> 
				|| object instanceof String 
				|| object instanceof Number
				|| object instanceof Boolean) {
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
					issues.add("Lists must not container null references.");
				}
				validate(obj, issues, objectStack);
			}
		} else {
			for (Method method : object.getClass().getMethods()) {
				if (isGetter(method)) {
					method.setAccessible(true);
					Object value = method.invoke(object);
					if (value == null && method.getAnnotation(NonNull.class) != null) {
						issues.add("The accessor '" + method.getName() + "' must return a non-null value.");
					}
					validate(value, issues, objectStack);
				}
			}
		}
		objectStack.pop();
	}

	protected boolean isGetter(Method method) {
		return !Modifier.isStatic(method.getModifiers()) && method.getName().startsWith("get")
				&& method.getParameterCount() == 0 && method.getDeclaringClass() != Object.class;
	}
}
