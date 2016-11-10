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
 * Position in a text document expressed as zero-based line and character offset.
 */
@LanguageServerAPI
interface Position {
	
	/**
	 * Line position in a document (zero-based).
	 */
	def int getLine()
	
	/**
	 * Character offset on a line in a document (zero-based).
	 */
	def int getCharacter()
	
}