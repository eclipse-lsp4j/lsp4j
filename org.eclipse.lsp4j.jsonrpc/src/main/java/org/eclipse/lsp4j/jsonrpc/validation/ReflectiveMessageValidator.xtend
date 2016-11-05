/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.validation

import java.util.LinkedList
import java.util.List
import org.eclipse.lsp4j.jsonrpc.MessageConsumer
import org.eclipse.lsp4j.jsonrpc.json.InvalidMessageException
import org.eclipse.lsp4j.jsonrpc.messages.Message
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode

class ReflectiveMessageValidator implements MessageConsumer {
	
	static val NAMESPACE = 'org.eclipse.lsp4j'
	
	private MessageConsumer delegate;
	
	new(MessageConsumer delegate) {
		this.delegate = delegate;
	}
	
	override consume(Message message) {
		val result = newArrayList
		try {
			validate(message, result, newLinkedList)
		} catch (Exception e) {
			result += new MessageIssue('''Error during message validation: «e.message»''')
		} catch (NoClassDefFoundError e) {
			// Skip validation if Nullable annotations are not available
			if (!e.message.endsWith('Nullable'))
				throw e
		}
		if (result.isEmpty) {
			delegate.consume(message);
			return;
		}
		val error = new ResponseError(ResponseErrorCode.InvalidParams, result.join(', ') [text], message);
		throw new InvalidMessageException(error.message, error, null);
	}
	
	private def void validate(Object object, List<MessageIssue> issues, LinkedList<Object> objectStack) throws Exception {
		if (objectStack.contains(object)) {
			issues += new MessageIssue('An element of the message has a direct or indirect reference to itself.')
			return
		}
		objectStack.push(object)
		for (field : object.class.declaredFields) {
			field.accessible = true
			val value = field.get(object)
			if (value === null && field.getAnnotation(NonNull) !== null)
				issues += new MessageIssue('''The property '«field.name»' must have a non-null value.''')
			else if (value !== null && value.class.name.startsWith(NAMESPACE) && !(value instanceof Enum<?>))
				validate(value, issues, objectStack)
		}
		objectStack.pop()
	}
	
}
