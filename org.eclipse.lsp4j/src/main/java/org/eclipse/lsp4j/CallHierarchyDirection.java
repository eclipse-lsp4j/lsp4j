/******************************************************************************
 * Copyright (c) 2019 TypeFox and others.
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
 * The direction of a call hierarchy.
 */
public enum CallHierarchyDirection {

	CallsFrom(1),

	CallsTo(2);

	private final int value;

	private CallHierarchyDirection(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static InsertTextFormat forValue(int value) {
		InsertTextFormat[] allValues = InsertTextFormat.values();
		if (value < 1 || value > allValues.length) {
			throw new IllegalArgumentException("Illegal enum value: " + value);
		}
		return allValues[value - 1];
	}

}
