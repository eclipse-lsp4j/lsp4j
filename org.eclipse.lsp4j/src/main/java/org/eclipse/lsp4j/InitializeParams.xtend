/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI

/**
 * The initialize request is sent as the first request from the client to the server.
 */
@LanguageServerAPI
class InitializeParams {
	
	/**
	 * The process Id of the parent process that started the server.
	 */
	Integer processId
	
	/**
	 * The rootPath of the workspace. Is null if no folder is open.
	 */
	String rootPath
	
	/**
	 * User provided initialization options.
	 */
	Object initializationOptions
	
	/**
	 * The capabilities provided by the client (editor)
	 */
	ClientCapabilities capabilities
	
	/**
	 * An optional extension to the protocol.
	 * To tell the server what client (editor) is talking to it.
	 */
	String clientName

}