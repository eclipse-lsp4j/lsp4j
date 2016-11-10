/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI
import java.util.List

/**
 * Represents a collection of completion items to be presented in the editor.
 */
@LanguageServerAPI
interface CompletionList {
	
	/**
     * This list it not complete. Further typing should result in recomputing this list.
     */
    def boolean isIncomplete()
    
    /**
     * The completion items.
     */
    def List<? extends CompletionItem> getItems()
    
}