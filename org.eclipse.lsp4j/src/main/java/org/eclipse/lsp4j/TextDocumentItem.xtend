/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

/**
 * An item to transfer a text document from the client to the server.
 */
@LanguageServerAPI
class TextDocumentItem {
	
	/**
	 * The text document's uri.
	 */
	@NonNull
	String uri
	
	/**
	 * The text document's language identifier
	 */
	@NonNull
	String languageId
	
	/**
	 * The version number of this document (it will strictly increase after each change, including undo/redo).
	 */
	int version
	
	/**
	 * The content of the opened  text document.
	 */
	@NonNull
	String text
	
}