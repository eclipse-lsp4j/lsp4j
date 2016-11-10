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
 * The rename request is sent from the client to the server to do a workspace wide rename of a symbol.
 */
@LanguageServerAPI
interface RenameParams {
	
	/**
	 * The document in which to find the symbol.
	 */
	def TextDocumentIdentifier getTextDocument()
	
	/**
	 * The position at which this request was send.
	 */
	def Position getPosition()
	
	/**
	 * The new name of the symbol. If the given name is not valid the request must return a
	 * ResponseError with an appropriate message set.
	 */
	def String getNewName()
	
}