/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services

import org.eclipse.lsp4j.DidChangeConfigurationParams
import org.eclipse.lsp4j.DidChangeWatchedFilesParams
import org.eclipse.lsp4j.SymbolInformation
import org.eclipse.lsp4j.WorkspaceSymbolParams
import java.util.List
import java.util.concurrent.CompletableFuture

interface WorkspaceService {
    
    /**
     * The workspace symbol request is sent from the client to the server to list project-wide symbols matching
     * the query string.
     */
    def CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params)
    
    /**
     * A notification sent from the client to the server to signal the change of configuration settings.
     */
    def void didChangeConfiguraton(DidChangeConfigurationParams params)
    
    /**
     * The watched files notification is sent from the client to the server when the client detects changes to
     * file watched by the language client.
     */
    def void didChangeWatchedFiles(DidChangeWatchedFilesParams params)
    
}