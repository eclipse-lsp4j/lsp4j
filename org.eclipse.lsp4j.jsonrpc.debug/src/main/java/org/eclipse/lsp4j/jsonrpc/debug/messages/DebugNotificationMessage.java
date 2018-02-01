/*******************************************************************************
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.debug.messages;

import org.eclipse.lsp4j.jsonrpc.debug.adapters.DebugMessageTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * DSP specific version of NotificationMessage.
 *
 * @see DebugMessageTypeAdapter
 */
public class DebugNotificationMessage extends NotificationMessage {

	/**
	 * The notification id.
	 */
	@NonNull
	private Either<String, Integer> id;

	public String getId() {
		if (id == null)
			return null;
		if (id.isLeft())
			return id.getLeft();
		if (id.isRight())
			return id.getRight().toString();
		return null;
	}
	
	public Either<String, Integer> getRawId() {
		return id;
	}

	public void setId(String id) {
		this.id = Either.forLeft(id);
	}
	
	public void setId(int id) {
		this.id = Either.forRight(id);
	}
	
	public void setRawId(Either<String, Integer> id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!super.equals(obj))
			return false;
		DebugNotificationMessage other = (DebugNotificationMessage) obj;
		if (this.id == null) {
			if (other.id != null)
				return false;
		} else if (!this.id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

}
