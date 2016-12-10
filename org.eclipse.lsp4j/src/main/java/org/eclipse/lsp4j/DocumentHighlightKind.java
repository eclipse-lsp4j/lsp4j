/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonEnum;

/**
 * A document highlight kind.
 */
public enum DocumentHighlightKind implements WrappedJsonEnum {
	
	/**
	 * A textual occurrance.
	 */
	Text(1),
	
	/**
	 * Read-access of a symbol, like reading a variable.
	 */
	Read(2),
	
	/**
	 * Write-access of a symbol, like writing to a variable.
	 */
	Write(3);
	
	private final int value;
	
	DocumentHighlightKind(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static DocumentHighlightKind forValue(int value) {
		DocumentHighlightKind[] allValues = DocumentHighlightKind.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
