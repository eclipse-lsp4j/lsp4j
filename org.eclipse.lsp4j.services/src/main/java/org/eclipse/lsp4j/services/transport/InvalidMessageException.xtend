/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.transport

import org.eclipse.lsp4j.ResponseErrorCode
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class InvalidMessageException extends RuntimeException {
	
	val String requestId
	
	val ResponseErrorCode errorCode
	
	new(String message) {
		super(message)
		this.requestId = null
		this.errorCode = ResponseErrorCode.InvalidRequest
	}
	
	new(String message, String requestId) {
		super(message)
		this.requestId = requestId
		this.errorCode = ResponseErrorCode.InvalidRequest
	}
	
	new(String message, String requestId, Throwable cause) {
		super(message, cause)
		this.requestId = requestId
		this.errorCode = ResponseErrorCode.InvalidRequest
	}
	
	new(String message, String requestId, ResponseErrorCode errorCode) {
		super(message)
		this.requestId = requestId
		this.errorCode = errorCode
	}
	
}
