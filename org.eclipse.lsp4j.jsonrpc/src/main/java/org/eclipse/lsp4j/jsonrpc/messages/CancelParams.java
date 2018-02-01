/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * To cancel a request a notification message with the following properties is sent.
 */
public class CancelParams {
	
	/**
	 * The request id to cancel.
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
		CancelParams other = (CancelParams) obj;
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
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

}
