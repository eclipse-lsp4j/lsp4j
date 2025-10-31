/******************************************************************************
 * Copyright (c) 2019 Microsoft Corporation and others.
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

import com.google.common.annotations.Beta;

/**
 * Symbol tags are extra annotations that tweak the rendering of a symbol.
 * Since 3.16
 */
public enum SymbolTag {

	/**
	 * Render a symbol as obsolete, usually using a strike-out.
	 * Since 3.16
	 */
	Deprecated(1),
	
	/**
	 * Render a symbol with visibility / access modifier "private".
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Private(2),
	
	/**
	 * Render a symbol with visibility "package private", e.g. in Java.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Package(3),
	
	/**
	 * Render a symbol with visibility / access modifier "protected".
	 * The modifier could be combined e.g. with "internal" or "private" in languages like C#.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Protected(4),
	
	/**
	 * Render a symbol with visibility / access modifier "public".
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Public(5),
	
	/**
	 * Render a symbol with visibility / access modifier "internal", e.g. in C# or Kotlin.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Internal(6),
	
	/**
	 * Render a symbol with visibility / access modifier "file", e.g. in C#.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	File(7),
	
	/**
	 * Render a symbol as "static".
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Static(8),
	
	/**
	 * Render a symbol as "abstract".
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Abstract(9),
	
	/**
	 * Render a symbol as "final".
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Final(10),
	
	/**
	 * Render a symbol as "sealed", e.g. classes and interfaces in Kotlin.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Sealed(11),
	
	/**
	 * Render a symbol as "transient", e.g. in Java.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Transient(12),
	
	/**
	 * Render a symbol as "volatile", e.g. in Java.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Volatile(13),
	
	/**
	 * Render a symbol as "synchronized", e.g. in Java.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Synchronized(14),
	
	/**
	 * Render a symbol as "virtual", e.g. in C++.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Virtual(15),
	
	/**
	 * Render a symbol as "nullable", e.g. types with '?' in Kotlin.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Nullable(16),
	
	/**
	 * Render a symbol as "never null", e.g. types without '?' in Kotlin.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	NonNull(17),
	
	/**
	 * Render a symbol as declaration.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Declaration(18),
	
	/**
	 * Render a symbol as definition (in contrast to declaration), e.g. in header files in C++.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	Definition(19),
	
	/**
	 * Render a symbol as "read-only", i.e. variables / properties that cannot be changed.
	 * 
	 * This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a>
	 */
	@Beta
	ReadOnly(20);

	private final int value;
	
	SymbolTag(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public static SymbolTag forValue(int value) {
		SymbolTag[] allValues = SymbolTag.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}
}