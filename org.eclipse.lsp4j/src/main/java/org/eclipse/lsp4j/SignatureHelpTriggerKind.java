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
 * How a signature help was triggered.
 * 
 * Since 3.15.0
 */
public enum SignatureHelpTriggerKind {
	
	/**
	 * Signature help was invoked manually by the user or by a command.
	 */
	Invoked(1),
	
	/**
	 * Signature help was triggered by a trigger character.
	 */
	TriggerCharacter(2),
	
	/**
	 * Signature help was triggered by the cursor moving or by the document content changing.
	 */
	ContentChange(3);
	
	private final int value;
	
	SignatureHelpTriggerKind(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static SignatureHelpTriggerKind forValue(int value) {
		SignatureHelpTriggerKind[] allValues = SignatureHelpTriggerKind.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
