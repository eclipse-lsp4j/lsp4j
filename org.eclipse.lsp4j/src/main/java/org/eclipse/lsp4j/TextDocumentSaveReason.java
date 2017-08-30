/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

/**
 * Represents reasons why a text document is saved.
 */
public enum TextDocumentSaveReason {

	/**
	 * Manually triggered, e.g. by the user pressing save, by starting debugging,
     * or by an API call.
	 */
	Manual(1),

	/**
	 * Automatic after a delay.
	 */
	AfterDelay(2),
	
	/**
	 * When the editor lost focus.
	 */
	FocusOut(3);

	private final int value;
	
	TextDocumentSaveReason(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static TextDocumentSaveReason forValue(int value) {
		TextDocumentSaveReason[] allValues = TextDocumentSaveReason.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
