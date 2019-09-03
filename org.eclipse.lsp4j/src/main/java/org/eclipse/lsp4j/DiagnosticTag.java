/******************************************************************************
 * Copyright (c) 2019 Mcirosoft.
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

public enum DiagnosticTag {
	
	/**
	 * Unused or unnecessary code.
     * 
     * Clients are allowed to render diagnostics with this tag faded out instead of having
	 * an error squiggle.
	 */
    Unnecessary(1),
    
	/**
	 * Deprecated or obsolete code.
     * 
     * Clients are allowed to rendered diagnostics with this tag strike through.
	 */
    Deprecated(2);

	private final int value;
	
	DiagnosticTag(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static DiagnosticTag forValue(int value) {
		DiagnosticTag[] allValues = DiagnosticTag.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}
}