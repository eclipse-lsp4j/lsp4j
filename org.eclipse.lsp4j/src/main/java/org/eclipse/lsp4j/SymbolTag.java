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

import org.eclipse.lsp4j.jsonrpc.ProtocolDraft;
import org.eclipse.lsp4j.jsonrpc.ProtocolSince;


/**
 * Symbol tags are extra annotations that tweak the rendering of a symbol.
 */
@ProtocolSince("3.16.0")
public enum SymbolTag {

	/**
	 * Render a symbol as obsolete, usually using a strike-out.
	 */
	Deprecated(1),
	
	/**
	 * <p>Render a symbol with visibility / access modifier "private".</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Private(2),
	
	/**
	 * <p>Render a symbol with visibility "package private", e.g. in Java.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Package(3),
	
	/**
	 * <p>Render a symbol with visibility / access modifier "protected".
	 * The modifier could be combined e.g. with "internal" or "private" in languages like C#.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Protected(4),
	
	/**
	 * <p>Render a symbol with visibility / access modifier "public".</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Public(5),
	
	/**
	 * <p>Render a symbol with visibility / access modifier "internal", e.g. in C# or Kotlin.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Internal(6),
	
	/**
	 * <p>Render a symbol with visibility / access modifier "file", e.g. in C#.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	File(7),
	
	/**
	 * <p>Render a symbol as "static".</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Static(8),
	
	/**
	 * <p>Render a symbol as "abstract".</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Abstract(9),
	
	/**
	 * <p>Render a symbol as "final".</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Final(10),
	
	/**
	 * <p>Render a symbol as "sealed", e.g. classes and interfaces in Kotlin.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Sealed(11),
	
	/**
	 * <p>Render a symbol as "transient", e.g. in Java.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Transient(12),
	
	/**
	 * <p>Render a symbol as "volatile", e.g. in Java.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Volatile(13),
	
	/**
	 * <p>Render a symbol as "synchronized", e.g. in Java.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Synchronized(14),
	
	/**
	 * <p>Render a symbol as "virtual", e.g. in C++.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Virtual(15),
	
	/**
	 * <p>Render a symbol as "nullable", e.g. types with '?' in Kotlin.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Nullable(16),
	
	/**
	 * <p>Render a symbol as "never null", e.g. types without '?' in Kotlin.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	NonNull(17),
	
	/**
	 * <p>Render a symbol as declaration.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Declaration(18),
	
	/**
	 * <p>Render a symbol as definition (in contrast to declaration), e.g. in header files in C++.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
	Definition(19),
	
	/**
	 * <p>Render a symbol as "read-only", i.e. variables / properties that cannot be changed.</p>
	 * 
	 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2003">PR</a></p>
	 */
	@ProtocolDraft
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