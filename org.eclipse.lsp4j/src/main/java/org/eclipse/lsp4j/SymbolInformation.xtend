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
 * Represents information about programming constructs like variables, classes, classs etc.
 */
@LanguageServerAPI
class SymbolInformation {
	
	/**
	 * The name of this symbol.
	 */
	@NonNull
	String name
	
	/**
	 * The kind of this symbol.
	 */
	@NonNull
	SymbolKind kind
	
	/**
	 * The location of this symbol.
	 */
	@NonNull
	Location location
	
	/**
	 * The name of the symbol containing this symbol.
	 */
	String containerName
	
}