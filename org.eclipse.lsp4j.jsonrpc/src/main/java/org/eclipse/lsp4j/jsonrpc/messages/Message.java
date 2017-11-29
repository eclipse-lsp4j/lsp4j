/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.json.MessageConstants;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * A general message as defined by JSON-RPC. The language server protocol always
 * uses "2.0" as the jsonrpc version.
 */
public abstract class Message {

	@NonNull
	private String jsonrpc = MessageConstants.JSONRPC_VERSION;

	public String getJsonrpc() {
		return this.jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	@Override
	public String toString() {
		return MessageJsonHandler.toString(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (this.jsonrpc == null) {
			if (other.jsonrpc != null)
				return false;
		} else if (!this.jsonrpc.equals(other.jsonrpc))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.jsonrpc == null) ? 0 : this.jsonrpc.hashCode());
		return result;
	}

}
