/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI
import javax.annotation.Nullable

/**
 * The initialize request is sent as the first request from the client to the server.
 */
@LanguageServerAPI
interface InitializeParams {
	
	/**
	 * The process Id of the parent process that started the server.
	 */
	@Nullable
	def Integer getProcessId()
	
	/**
	 * The rootPath of the workspace. Is null if no folder is open.
	 */
	@Nullable
	def String getRootPath()
	
	/**
	 * User provided initialization options.
	 */
	@Nullable
	def Object getInitializationOptions()
	
	/**
	 * The capabilities provided by the client (editor)
	 */
	@Nullable
	def ClientCapabilities getCapabilities()
	
	/**
	 * An optional extension to the protocol.
	 * To tell the server what client (editor) is talking to it.
	 */
	@Nullable
	def String getClientName()

}