/*******************************************************************************
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.debug.messages;

import org.eclipse.lsp4j.jsonrpc.debug.adapters.DebugMessageTypeAdapter;
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
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
