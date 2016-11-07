/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

public class ResponseMessage extends Message {

	/**
	 * The request id.
	 */
	@NonNull
	private String id;

	@Pure
	@NonNull
	public String getId() {
		return this.id;
	}

	public void setId(@NonNull final String id) {
		this.id = id;
	}

	/**
	 * The result of a request. This can be omitted in the case of an error.
	 */
	private Object result;

	@Pure
	public Object getResult() {
		return this.result;
	}

	public void setResult(final Object result) {
		this.result = result;
	}

	/**
	 * The error object in case a request fails.
	 */
	private ResponseError error;

	@Pure
	public ResponseError getError() {
		return this.error;
	}

	public void setError(final ResponseError error) {
		this.error = error;
	}
}
