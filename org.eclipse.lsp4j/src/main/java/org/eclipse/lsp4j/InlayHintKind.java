/******************************************************************************
 * Copyright (c) 2016-2018 TypeFox and others.
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
 * Inlay hint kinds.
 * <p>
 * Since 3.17.0
 */
@Beta
public enum InlayHintKind {
	/**
	 * An inlay hint that for a type annotation.
	 */
	Type(1),
	
	/**
	 * An inlay hint that is for a parameter.
	 */
	Parameter(2);
	
	private final int value;
	
	InlayHintKind(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static InlayHintKind forValue(int value) {
		InlayHintKind[] allValues = InlayHintKind.values();
		if (value < 1 || value > allValues.length) {
			throw new IllegalArgumentException("Illegal enum value: " + value);
		}
		return allValues[value - 1];
	}
}
