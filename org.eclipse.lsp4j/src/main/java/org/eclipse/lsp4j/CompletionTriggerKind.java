/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

/**
 * How a completion was triggered
 */
public enum CompletionTriggerKind {
	
	/**
	 * Completion was triggered by typing an identifier (24x7 code
	 * complete), manual invocation (e.g Ctrl+Space) or via API.
	 */
	Invoked(1),
	
	/**
	 * Completion was triggered by a trigger character specified by
	 * the `triggerCharacters` properties of the `CompletionRegistrationOptions`.
	 */
	TriggerCharacter(2),
	
	/**
	 * Completion was re-triggered as the current completion list is incomplete.
	 */
	TriggerForIncompleteCompletions(3);
	
	private final int value;
	
	CompletionTriggerKind(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static FileChangeType forValue(int value) {
		FileChangeType[] allValues = FileChangeType.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
