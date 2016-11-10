/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI
import java.util.Map
import javax.annotation.Nullable

/**
 * Value-object describing what options formatting should use.
 */
@LanguageServerAPI
interface FormattingOptions {
	
	/**
	 * Size of a tab in spaces.
	 */
	def int getTabSize()
	
	/**
	 * Prefer spaces over tabs.
	 */
	def boolean isInsertSpaces()
	
	/**
	 * Signature for further properties.
	 */
	@Nullable
	def Map<String, String> getProperties()
	
}