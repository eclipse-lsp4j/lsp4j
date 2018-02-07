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

public class ResponseError {

	/**
	 * A number indicating the error type that occured.
	 */
	@NonNull
	private int code;

	@NonNull
	public int getCode() {
		return this.code;
	}

	public void setCode(@NonNull int code) {
		this.code = code;
	}

	public void setCode(ResponseErrorCode code) {
		this.setCode(code.getValue());
	}

	/**
	 * A string providing a short decription of the error.
	 */
	@NonNull
	private String message;

	@NonNull
	public String getMessage() {
		return this.message;
	}

	public void setMessage(@NonNull String message) {
		this.message = message;
	}

	/**
	 * A Primitive or Structured value that contains additional information
	 * about the error. Can be omitted.
	 */
	private Object data;

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResponseError() {
	}

	public ResponseError(ResponseErrorCode code, String message, Object data) {
		this(code.getValue(), message, data);
	}

	public ResponseError(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
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
		ResponseError other = (ResponseError) obj;
		if (other.code != this.code)
			return false;
		if (this.message == null) {
			if (other.message != null)
				return false;
		} else if (!this.message.equals(other.message))
			return false;
		if (this.data == null) {
			if (other.data != null)
				return false;
		} else if (!this.data.equals(other.data))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.code;
		result = prime * result + ((this.message == null) ? 0 : this.message.hashCode());
		result = prime * result + ((this.data == null) ? 0 : this.data.hashCode());
		return result;
	}

}
