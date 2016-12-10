/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonEnum;

public enum SymbolKind implements WrappedJsonEnum {
	
	File(1),
	
	Module(2),
	
	Namespace(3),
	
	Package(4),
	
	Class(5),
	
	Method(6),
	
	Property(7),
	
	Field(8),
	
	Constructor(9),
	
	Enum(10),
	
	Interface(11),
	
	Function(12),
	
	Variable(13),
	
	Constant(14),
	
	String(15),
	
	Number(16),
	
	Boolean(17),
	
	Array(18);
	
	private final int value;
	
	SymbolKind(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static SymbolKind forValue(int value) {
		SymbolKind[] allValues = SymbolKind.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
