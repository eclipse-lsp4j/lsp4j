/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import java.util.ArrayList
import java.util.List
import org.eclipse.lsp4j.annotations.LanguageServerAPI
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

/**
 * The document change notification is sent from the client to the server to signal changes to a text document.
 */
@LanguageServerAPI
class DidChangeTextDocumentParams {
	
	/**
	 * The document that did change. The version number points to the version after all provided content changes have
	 * been applied.
	 */
	@NonNull
	VersionedTextDocumentIdentifier textDocument
	
	/**
	 * Legacy property to support protocol version 1.0 requests.
	 */
	@Deprecated
	String uri
	
	/**
	 * The actual content changes.
	 */
	@NonNull
	List<TextDocumentContentChangeEvent> contentChanges = new ArrayList
	
}