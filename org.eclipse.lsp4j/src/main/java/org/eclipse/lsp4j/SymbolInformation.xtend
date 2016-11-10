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
 * Represents information about programming constructs like variables, classes, interfaces etc.
 */
@LanguageServerAPI
interface SymbolInformation {
	
	/**
	 * The name of this symbol.
	 */
	def String getName()
	
	/**
	 * The kind of this symbol.
	 */
	def SymbolKind getKind()
	
	/**
	 * The location of this symbol.
	 */
	def Location getLocation()
	
	/**
	 * The name of the symbol containing this symbol.
	 */
	@Nullable
	def String getContainerName()
	
}