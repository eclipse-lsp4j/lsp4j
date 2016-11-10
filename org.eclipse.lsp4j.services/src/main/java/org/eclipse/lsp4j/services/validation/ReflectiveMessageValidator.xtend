/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.validation

import org.eclipse.lsp4j.Message
import java.util.LinkedList
import java.util.List
import javax.annotation.Nullable

class ReflectiveMessageValidator implements IMessageValidator {
	
	static val NAMESPACE = 'org.eclipse.lsp4j'
	
	override validate(Message message) {
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
		return result
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
			// Every field that is not explicitly nullable must have a non-null value
			if (value === null && field.getAnnotation(Nullable) === null)
				issues += new MessageIssue('''The property '«field.name»' must have a non-null value.''')
			else if (value !== null && value.class.name.startsWith(NAMESPACE) && !(value instanceof Enum<?>))
				validate(value, issues, objectStack)
		}
		objectStack.pop()
	}
	
}