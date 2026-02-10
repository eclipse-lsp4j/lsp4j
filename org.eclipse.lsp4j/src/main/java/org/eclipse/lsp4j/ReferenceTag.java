/******************************************************************************
 * Copyright (c) 2026 Advantest Europe GmbH and others.
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


/**
 * <p>Reference tags represent additional details in CallHierarchyItems and References to adapt their rendering.</p>
 * 
 * <p>This is an LSP <b>proposal</b>. See <a href="https://github.com/microsoft/language-server-protocol/pull/2226">PR</a></p>
 */
@ProtocolDraft
public enum ReferenceTag {

	/**
	 * Render a CallHierarchyItem or Reference as read access, e.g. in a call hierarchy.
	 */
	Read(1),
	
	/**
	 * Render a CallHierarchyItem or Reference as write access, e.g. in a call hierarchy.
	 */
	Write(2);


	private final int value;
	
	ReferenceTag(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public static ReferenceTag forValue(int value) {
		ReferenceTag[] allValues = ReferenceTag.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}
}