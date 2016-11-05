/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import java.util.Map
import org.eclipse.lsp4j.annotations.LanguageServerAPI

/**
 * Value-object describing what options formatting should use.
 */
@LanguageServerAPI
class FormattingOptions {
	
	/**
	 * Size of a tab in spaces.
	 */
	int tabSize
	
	/**
	 * Prefer spaces over tabs.
	 */
	boolean insertSpaces
	
	/**
	 * Signature for further properties.
	 */
	Map<String, String> properties
	
}