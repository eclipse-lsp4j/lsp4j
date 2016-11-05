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
 * A parameter literal used in requests to pass a text document and a position inside that document.
 */
@LanguageServerAPI
class TextDocumentPositionParams {
	
	/**
	 * The text document.
	 */
	@NonNull
	TextDocumentIdentifier textDocument
	
	/**
	 * Legacy property to support protocol version 1.0 requests.
	 */
	@Deprecated
	String uri
	
	/**
	 * The position inside the text document.
	 */
	@NonNull
	Position position
	
}