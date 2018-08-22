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

/**
 * The file event type.
 */
public enum FileChangeType {
	
	/**
	 * The file got created.
	 */
	Created(1),
	
	/**
	 * The file got changed.
	 */
	Changed(2),
	
	/**
	 * The file got deleted.
	 */
	Deleted(3);
	
	private final int value;
	
	FileChangeType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static FileChangeType forValue(int value) {
		FileChangeType[] allValues = FileChangeType.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
