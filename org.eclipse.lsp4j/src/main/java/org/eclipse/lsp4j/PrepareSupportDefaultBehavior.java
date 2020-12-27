/******************************************************************************
 * Copyright (c) 2020 TypeFox and others.
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
 * The value indicates the default behavior used by the
 * client.
 *
 * Since version 3.16.0
 */
public enum PrepareSupportDefaultBehavior {
	
	/**
	 * The client's default behavior is to select the identifier
	 * according the to language's syntax rule.
	 */
	Identifier(1);
	
	private final int value;
	
	PrepareSupportDefaultBehavior(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static PrepareSupportDefaultBehavior forValue(int value) {
		PrepareSupportDefaultBehavior[] allValues = PrepareSupportDefaultBehavior.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
