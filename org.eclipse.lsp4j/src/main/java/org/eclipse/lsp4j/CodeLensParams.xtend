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
 * The code lens request is sent from the client to the server to compute code lenses for a given text document.
 */
@LanguageServerAPI
class CodeLensParams {
	
	/**
	 * The document to request code lens for.
	 */
	@NonNull
	TextDocumentIdentifier textDocument
	
}