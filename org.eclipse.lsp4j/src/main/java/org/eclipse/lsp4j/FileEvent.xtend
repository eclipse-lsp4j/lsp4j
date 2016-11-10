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
 * An event describing a file change.
 */
@LanguageServerAPI
interface FileEvent {
	
	/**
	 * The file's uri.
	 */
	def String getUri()
	
	/**
	 * The change type.
	 */
	def FileChangeType getType()
	
}