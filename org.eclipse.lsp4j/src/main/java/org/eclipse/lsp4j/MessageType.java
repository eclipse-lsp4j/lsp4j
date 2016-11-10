/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

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
	Log(1);
	
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
