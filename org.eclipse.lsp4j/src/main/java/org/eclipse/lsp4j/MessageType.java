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

import org.eclipse.lsp4j.jsonrpc.ProtocolDraft;
import org.eclipse.lsp4j.jsonrpc.ProtocolSince;

public enum MessageType {
	
	/**
	 * An error message.
	 */
	Error(1),
	
	/**
	 * A warning message.
	 */
	Warning(2),
	
	/**
	 * An information message.
	 */
	Info(3),
	
	/**
	 * A log message.
	 */
	Log(4),
	
	/**
	 * A debug message.
	 */
	@ProtocolDraft
	@ProtocolSince("3.18.0")
	Debug(5);
	
	private final int value;
	
	MessageType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public static MessageType forValue(int value) {
		MessageType[] allValues = MessageType.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}

}
