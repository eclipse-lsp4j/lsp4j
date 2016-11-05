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
 * An event describing a change to a text document. If range and rangeLength are omitted the new text is considered
 * to be the full content of the document.
 */
@LanguageServerAPI
class TextDocumentContentChangeEvent {
	
	/**
	 * The range of the document that changed.
	 */
	Range range
	
	/**
	 * The length of the range that got replaced.
	 */
	Integer rangeLength
	
	/**
	 * The new text of the document.
	 */
	@NonNull
	String text
	
}