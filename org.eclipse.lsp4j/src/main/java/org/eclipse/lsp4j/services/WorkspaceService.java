/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

import com.google.common.annotations.Beta;

@JsonSegment("workspace")
public interface WorkspaceService {
	/**
	 * The workspace/executeCommand request is sent from the client to the
	 * server to trigger command execution on the server. In most cases the
	 * server creates a WorkspaceEdit structure and applies the changes to the
	 * workspace using the request workspace/applyEdit which is sent from the
	 * server to the client.
	 *
	 * Registration Options: ExecuteCommandRegistrationOptions
	 */
	@JsonRequest
	default CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The workspace symbol request is sent from the client to the server to
	 * list project-wide symbols matching the query string.
	 *
	 * Registration Options: void
	 */
	@JsonRequest
	CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params);

	/**
	 * A notification sent from the client to the server to signal the change of
	 * configuration settings.
	 */
	@JsonNotification
	void didChangeConfiguration(DidChangeConfigurationParams params);

	/**
	 * The watched files notification is sent from the client to the server when
	 * the client detects changes to file watched by the language client.
	 */
	@JsonNotification
	void didChangeWatchedFiles(DidChangeWatchedFilesParams params);

	/**
	 * The workspace/didChangeWorkspaceFolders notification is sent from the client
	 * to the server to inform the server about workspace folder configuration changes.
	 * The notification is sent by default if both ServerCapabilities/workspaceFolders
	 * and ClientCapabilities/workspace/workspaceFolders are true; or if the server has
	 * registered to receive this notification it first.
	 *
	 * This API is a <b>proposal</b> from LSP and may change.
	 */
	@JsonNotification
	@Beta
	default void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {}
}
