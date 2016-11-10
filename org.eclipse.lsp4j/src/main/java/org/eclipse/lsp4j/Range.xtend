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
 * A range in a text document expressed as (zero-based) start and end positions.
 */
@LanguageServerAPI
interface Range {
	
	/**
	 * The range's start position
	 */
	def Position getStart()
	
	/**
	 * The range's end position
	 */
	def Position getEnd()
	
}