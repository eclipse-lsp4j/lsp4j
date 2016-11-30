/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.services;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.jsonrpc.services.JsonDelegate;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

/**
 * Interface for implementations of
 * https://github.com/Microsoft/vscode-languageserver-protocol
 */
public interface LanguageServer {
	/**
	 * The initialize request is sent as the first request from the client to
	 * the server.
	 */
	@JsonRequest
	CompletableFuture<InitializeResult> initialize(InitializeParams params);

	/**
	 * The shutdown request is sent from the client to the server. It asks the
	 * server to shutdown, but to not exit (otherwise the response might not be
	 * delivered correctly to the client). There is a separate exit notification
	 * that asks the server to exit.
	 */
	@JsonRequest
	CompletableFuture<Object> shutdown();

	/**
	 * A notification to ask the server to exit its process.
	 */
	@JsonNotification
	void exit();

	/**
	 * Provides access to the textDocument services.
	 */
	@JsonDelegate
	TextDocumentService getTextDocumentService();

	/**
	 * Provides access to the workspace services.
	 */
	@JsonDelegate
	WorkspaceService getWorkspaceService();
	
}
