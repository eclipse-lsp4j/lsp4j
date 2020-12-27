/******************************************************************************
 * Copyright (c) 2020 TypeFox and others.
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
 * How whitespace and indentation is handled during completion
 * item insertion.
 *
 * Since 3.16.0
 */
public enum InsertTextMode {
	
	/**
	 * The insertion or replace strings is taken as it is. If the
	 * value is multi line the lines below the cursor will be
	 * inserted using the indentation defined in the string value.
	 * The client will not apply any kind of adjustments to the
	 * string.
	 */
	AsIs(1),
	
	/**
	 * The editor adjusts leading whitespace of new lines so that
	 * they match the indentation up to the cursor of the line for
	 * which the item is accepted.
	 *
	 * Consider a line like this: [2tabs][cursor][3tabs]foo. Accepting a
	 * multi line completion item is indented using 2 tabs and all
	 * following lines inserted will be indented using 2 tabs as well.
	 */
	AdjustIndentation(2);
	
	private final int value;
	
	InsertTextMode(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static InsertTextMode forValue(int value) {
		InsertTextMode[] allValues = InsertTextMode.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
