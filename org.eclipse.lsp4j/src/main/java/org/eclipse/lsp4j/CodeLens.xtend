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
 * A code lens represents a command that should be shown along with source text, like the number of references,
 * a way to run tests, etc.
 *
 * A code lens is <em>unresolved</em> when no command is associated to it. For performance reasons the creation of a
 * code lens and resolving should be done to two stages.
 */
@LanguageServerAPI
class CodeLens {
	
	/**
	 * The range in which this code lens is valid. Should only span a single line.
	 */
	@NonNull
	Range range
	
	/**
	 * The command this code lens represents.
	 */
	Command command
	
	/**
	 * An data entry field that is preserved on a code lens item between a code lens and a code lens resolve request.
	 */
	Object data
	
}