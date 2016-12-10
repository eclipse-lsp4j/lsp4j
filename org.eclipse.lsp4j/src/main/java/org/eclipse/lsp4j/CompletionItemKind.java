/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonEnum;

public enum CompletionItemKind implements WrappedJsonEnum {
	
	Text(1),
	
	Method(2),
	
	Function(3),
	
	Constructor(4),
	
	Field(5),
	
	Variable(6),
	
	Class(7),
	
	Interface(8),
	
	Module(9),
	
	Property(10),
	
	Unit(11),
	
	Value(12),
	
	Enum(13),
	
	Keyword(14),
	
	Snippet(15),
	
	Color(16),
	
	File(17),
	
	Reference(18);
	
	private final int value;
	
	CompletionItemKind(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static CompletionItemKind forValue(int value) {
		CompletionItemKind[] allValues = CompletionItemKind.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
