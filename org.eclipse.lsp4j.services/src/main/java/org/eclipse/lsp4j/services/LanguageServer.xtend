/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services

import org.eclipse.lsp4j.InitializeParams
import org.eclipse.lsp4j.InitializeResult
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

/**
 * Interface for implementations of
 * https://github.com/Microsoft/vscode-languageserver-protocol
 */
interface LanguageServer {
    
    /**
     * The initialize request is sent as the first request from the client to the server.
     */
    def CompletableFuture<InitializeResult> initialize(InitializeParams params)
    
    /**
     * The shutdown request is sent from the client to the server. It asks the server to shutdown, but to not exit 
     * (otherwise the response might not be delivered correctly to the client). There is a separate exit notification
     * that asks the server to exit.
     **/
    def void shutdown()
    
    /**
     * A notification to ask the server to exit its process.
     */
    def void exit();
    
    /**
     * The telemetry notification is sent from the server to the client to ask the client to log a telemetry event.
     */
    def void onTelemetryEvent(Consumer<Object> callback)
    
    /**
     * Provides access to the textDocument services.
     */
    def TextDocumentService getTextDocumentService();
    
    /**
     * Provides access to the workspace services.
     */
    def WorkspaceService getWorkspaceService();
    
    /**
     * Provides access to the window services.
     */
    def WindowService getWindowService();
    
}
