/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.jsonrpc.validation;

import java.lang.reflect.Field;
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
		} catch (NoClassDefFoundError e) {
			// Skip validation if Nullable annotations are not available
			if (!e.getMessage().endsWith("Nullable"))
				throw e;
		}
		if (result.isEmpty()) {
			delegate.consume(message);
			return;
		}
		ResponseError error = new ResponseError(ResponseErrorCode.InvalidParams,
				result.stream().collect(Collectors.joining(", ")), message);
		throw new InvalidMessageException(error.getMessage(), error, null);
	}

	protected void validate(Object object, List<String> issues, LinkedList<Object> objectStack) throws Exception {
		if (objectStack.contains(object)) {
			issues.add("An element of the message has a direct or indirect reference to itself.");
			return;
		}
		objectStack.push(object);
		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value = field.get(object);
			if (value == null && field.getAnnotation(NonNull.class) != null) {
				issues.add("The property '" + field.getName() + "' must have a non-null value.");
			} else if (value != null && !value.getClass().getName().startsWith("java.")
					&& !(value instanceof Enum<?>)) {
				validate(value, issues, objectStack);
			}
		}
		objectStack.pop();
	}
}
