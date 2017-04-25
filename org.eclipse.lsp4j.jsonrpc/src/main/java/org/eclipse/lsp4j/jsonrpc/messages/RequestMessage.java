/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * A request message to describe a request between the client and the server.
 * Every processed request must send a response back to the sender of the
 * request.
 */
public class RequestMessage extends Message {

	/**
	 * The request id.
	 */
	@NonNull
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * The method to be invoked.
	 */
	@NonNull
	private String method;

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * The method's params.
	 */
	private Object params;

	public Object getParams() {
		return this.params;
	}

	public void setParams(Object params) {
		this.params = params;
	}
	
}
