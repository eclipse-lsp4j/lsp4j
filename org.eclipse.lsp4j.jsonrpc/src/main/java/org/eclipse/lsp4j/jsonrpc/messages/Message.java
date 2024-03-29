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
package org.eclipse.lsp4j.jsonrpc.messages;

import java.lang.reflect.Modifier;

import org.eclipse.lsp4j.jsonrpc.json.MessageConstants;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.util.ToStringBuilder;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

import com.google.gson.JsonIOException;

/**
 * A general message as defined by JSON-RPC. The language server protocol always
 * uses "2.0" as the jsonrpc version.
 */
public abstract class Message {

	private transient MessageJsonHandler jsonHandler;

	// Note: 'getJsonHandler' is not used as the name of the accessor method
	// to avoid treating 'jsonHandler' as a general property of the message
	// by reflective code such as ReflectiveMessageValidator.

	public MessageJsonHandler jsonHandler() {
		return jsonHandler;
	}

	public void setJsonHandler(MessageJsonHandler jsonHandler) {
		this.jsonHandler = jsonHandler;
	}

	@NonNull
	private String jsonrpc = MessageConstants.JSONRPC_VERSION;

	@NonNull
	public String getJsonrpc() {
		return this.jsonrpc;
	}

	public void setJsonrpc(@NonNull String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	@Override
	public String toString() {
		try {
			return jsonHandler != null ? jsonHandler.format(this) : MessageJsonHandler.toString(this);
		} catch (JsonIOException e) {
			return toStringFallback();
		}
	}

	protected String toStringFallback() {
		final var builder = new ToStringBuilder(this);
		builder.addAllFields(field -> !Modifier.isTransient(field.getModifiers()));
		return builder.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final var other = (Message) obj;
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
		result = prime * result + (this.jsonrpc == null ? 0 : this.jsonrpc.hashCode());
		return result;
	}

}
