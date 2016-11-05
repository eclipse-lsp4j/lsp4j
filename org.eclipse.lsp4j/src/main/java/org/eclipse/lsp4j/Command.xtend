/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import java.util.List
import org.eclipse.lsp4j.annotations.LanguageServerAPI
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

/**
 * Represents a reference to a command. Provides a title which will be used to represent a command in the UI and,
 * optionally, an array of arguments which will be passed to the command handler function when invoked.
 */
@LanguageServerAPI
class Command {
	
	/**
	 * Title of the command, like `save`.
	 */
	@NonNull
	String title
	
	/**
	 * The identifier of the actual command handler.
	 */
	@NonNull
	String command
	
	/**
	 * Arguments that the command handler should be invoked with.
	 */
	List<Object> arguments
	
}