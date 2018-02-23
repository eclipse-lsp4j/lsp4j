/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc;

import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;

/**
 * An exception thrown in order to send a response with an attached {@code error} object.
 */
public class ResponseErrorException extends RuntimeException {

	private static final long serialVersionUID = -5970739895395246885L;
	private ResponseError responseError;

	public ResponseErrorException(ResponseError responseError) {
		this.responseError = responseError;
	}

	@Override
	public String getMessage() {
		return responseError.getMessage();
	}
	
	public ResponseError getResponseError() {
		return responseError;
	}
}
