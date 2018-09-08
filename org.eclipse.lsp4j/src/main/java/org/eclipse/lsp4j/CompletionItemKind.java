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

public enum CompletionItemKind {
	
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
	
	Reference(18),
	
	Folder(19),
	
	EnumMember(20),
	
	Constant(21),
	
	Struct(22),
	
	Event(23),
	
	Operator(24),
	
	TypeParameter(25);
	
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
