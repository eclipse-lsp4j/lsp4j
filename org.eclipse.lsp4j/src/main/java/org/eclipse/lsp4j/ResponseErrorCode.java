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
