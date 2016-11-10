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
 * A document highlight is a range inside a text document which deserves special attention. Usually a document highlight
 * is visualized by changing the background color of its range.
 */
@LanguageServerAPI
interface DocumentHighlight {
	
	/**
	 * The range this highlight applies to.
	 */
	def Range getRange()
	
	/**
	 * The highlight kind, default is {@link DocumentHighlightKind#Text}.
	 */
	@Nullable
	def DocumentHighlightKind getKind()
	
}