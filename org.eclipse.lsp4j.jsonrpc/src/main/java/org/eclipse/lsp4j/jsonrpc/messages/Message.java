/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import org.eclipse.lsp4j.jsonrpc.json.MessageConstants;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

public abstract class Message {
	@NonNull
	private String jsonrpc = MessageConstants.JSONRPC_VERSION;

	@Pure
	@NonNull
	public String getJsonrpc() {
		return this.jsonrpc;
	}

	public void setJsonrpc(@NonNull final String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).addAllFields().skipNulls().toString();
	}
}
