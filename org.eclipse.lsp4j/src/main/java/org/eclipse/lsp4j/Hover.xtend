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
 * The result of a hover request.
 */
@LanguageServerAPI
class Hover {
	
	/**
	 * The hover's content as markdown
	 */
	@NonNull
	List<String> contents = newArrayList()
	
	/**
	 * An optional range
	 */
	Range range
	
}