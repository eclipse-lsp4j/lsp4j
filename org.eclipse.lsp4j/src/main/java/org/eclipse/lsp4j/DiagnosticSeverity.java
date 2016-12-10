/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonEnum;

public enum DiagnosticSeverity implements WrappedJsonEnum {
	
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
