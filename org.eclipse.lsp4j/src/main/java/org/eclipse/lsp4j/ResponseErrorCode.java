/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

/**
 * A number indicating the error type that occured.
 * 
 * @deprecated Use {@link org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode} instead
 */
@Deprecated
public enum ResponseErrorCode {
	
	ParseError(-32700),
	
	InvalidRequest(-32600),
	
	MethodNotFound(-32601),
	
	InvalidParams(-32602),
	
	InternalError(-32603),
	
	serverErrorStart(-32099),
	
	serverErrorEnd(-32000);
	
	private final int value;
	
	ResponseErrorCode(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

}
