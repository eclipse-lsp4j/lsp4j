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

public enum DiagnosticSeverity {
	
	/**
	 * Reports an error.
	 */
	Error(1),
	
	/**
	 * Reports a warning.
	 */
	Warning(2),
	
	/**
	 * Reports an information.
	 */
	Information(3),
	
	/**
	 * Reports a hint.
	 */
	Hint(4);
	
	private final int value;
	
	DiagnosticSeverity(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static DiagnosticSeverity forValue(int value) {
		DiagnosticSeverity[] allValues = DiagnosticSeverity.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
