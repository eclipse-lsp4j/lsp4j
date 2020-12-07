/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
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
 * Defines whether the insert text in a completion item should be interpreted as
 * plain text or a snippet.
 */
public enum InsertTextFormat {
	
	/**
	 * The primary text to be inserted is treated as a plain string.
	 */
	PlainText(1),
	
	/**
	 * The primary text to be inserted is treated as a snippet.
	 *
	 * A snippet can define tab stops and placeholders with `$1`, `$2`
	 * and `${3:foo}`. `$0` defines the final tab stop, it defaults to
	 * the end of the snippet. Placeholders with equal identifiers are linked,
	 * that is typing in one will update others too.
	 */
	Snippet(2);
	
	private final int value;
	
	InsertTextFormat(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static InsertTextFormat forValue(int value) {
		InsertTextFormat[] allValues = InsertTextFormat.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
