/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonEnum;

/**
 * A number indicating the error type that occurred.
 */
public enum ResponseErrorCode implements WrappedJsonEnum {
	
	ParseError(-32700),
	
	InvalidRequest(-32600),
	
	MethodNotFound(-32601),
	
	InvalidParams(-32602),
	
	InternalError(-32603),
	
	serverErrorStart(-32099),
	
	serverErrorEnd(-32000),
	
	serverNotInitialized(-32001);
	
	private final int value;
	
	ResponseErrorCode(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public static ResponseErrorCode forValue(int value) {
		ResponseErrorCode[] allValues = ResponseErrorCode.values();
		if (value < 1 || value > allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value - 1];
	}
}
