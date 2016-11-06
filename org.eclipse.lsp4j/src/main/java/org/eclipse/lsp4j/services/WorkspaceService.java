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
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

@JsonSegment("workspace")
public interface WorkspaceService {
	/**
	 * The workspace symbol request is sent from the client to the server to
	 * list project-wide symbols matching the query string.
	 */
	@JsonRequest
	public abstract CompletableFuture<List<? extends SymbolInformation>> symbol(final WorkspaceSymbolParams params);

	/**
	 * A notification sent from the client to the server to signal the change of
	 * configuration settings.
	 */
	@JsonNotification
	public abstract void didChangeConfiguration(final DidChangeConfigurationParams params);

	/**
	 * The watched files notification is sent from the client to the server when
	 * the client detects changes to file watched by the language client.
	 */
	@JsonNotification
	public abstract void didChangeWatchedFiles(final DidChangeWatchedFilesParams params);
}
