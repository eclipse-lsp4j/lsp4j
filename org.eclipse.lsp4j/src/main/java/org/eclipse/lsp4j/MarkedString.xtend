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

@LanguageServerAPI
interface MarkedString {
	
	/**
	 * Value for <code>language</code> denoting this is a plain string.
	 * 
	 * LSP defines MarkedString as a union type  
	 * <pre>
	 * type MarkedString = string | { language: string; value: string };
	 * </pre>
	 * which does not go well with Java.
	 * 
	 * In Monaco's hovers, such <code>MarkedStrings</code> will be rendered as markdown.
	 */
	static val PLAIN_STRING = 'plain_string' 
	
	@Nullable
	def String getLanguage()
	
	def String getValue()
	
}