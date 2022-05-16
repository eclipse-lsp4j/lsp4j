/******************************************************************************
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j;

/**
 * The reason why code actions were requested.
 * <p>
 * Since 3.17.0
 */
public enum CodeActionTriggerKind {
	/**
	 * Code actions were explicitly requested by the user or by an extension.
	 */
	Invoked(1),
	
	/**
	 * Code actions were requested automatically.
	 * <p>
	 * This typically happens when current selection in a file changes, but can
	 * also be triggered when file content changes.
	 */
	Automatic(2);
	
	private final int value;
	
	CodeActionTriggerKind(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static CodeActionTriggerKind forValue(int value) {
		CodeActionTriggerKind[] allValues = CodeActionTriggerKind.values();
		if (value < 1 || value > allValues.length) {
			throw new IllegalArgumentException("Illegal enum value: " + value);
		}
		return allValues[value - 1];
	}
}
