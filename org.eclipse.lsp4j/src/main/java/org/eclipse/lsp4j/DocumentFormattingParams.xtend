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
 * The document formatting request is sent from the server to the client to format a whole document.
 */
@LanguageServerAPI
interface DocumentFormattingParams {
	
	/**
	 * The document to format.
	 */
	def TextDocumentIdentifier getTextDocument()
	
	/**
	 * The format options
	 */
	def FormattingOptions getOptions()
	
}