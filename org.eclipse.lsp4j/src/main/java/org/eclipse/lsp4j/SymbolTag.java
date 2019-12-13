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

/**
 * Symbol tags are extra annotations that tweak the rendering of a symbol.
 * @since 3.15
 */
public enum SymbolTag {

	Deprecated(1);

	private final int value;
	
	SymbolTag(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}