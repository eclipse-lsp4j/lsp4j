/*******************************************************************************
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.debug.messages;

import org.eclipse.lsp4j.jsonrpc.debug.adapters.DebugMessageTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * DSP specific version of ResponseMessage.
 *
 * @see DebugMessageTypeAdapter
 */
public class DebugResponseMessage extends ResponseMessage {

	/**
	 * The response id.
	 *
	 * The {@link #getId()} field is the id of the message being replied to.
	 */
	@NonNull
	private String responseId;

	public String getResponseId() {
		return this.responseId;
	}

	public void setResponseId(String id) {
		this.responseId = id;
	}

	/**
	 * The method that was invoked.
	 */
	@NonNull
	private String method;

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
