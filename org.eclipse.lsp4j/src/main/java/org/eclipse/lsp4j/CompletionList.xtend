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
 * Represents a collection of completion items to be presented in the editor.
 */
@LanguageServerAPI
class CompletionList {
	
	/**
     * This list it not complete. Further typing should result in recomputing this list.
     */
    boolean isIncomplete
    
    /**
     * The completion items.
     */
    @NonNull
    List<CompletionItem> items = newArrayList
    
}