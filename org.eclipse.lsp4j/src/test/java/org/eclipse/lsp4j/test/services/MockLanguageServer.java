/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.test.services;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.WorkDoneProgressCancelParams;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

public class MockLanguageServer implements LanguageServer, TextDocumentService, WorkspaceService {

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exit() {
	}

	@Override
	public TextDocumentService getTextDocumentService() {
		return this;
	}

	@Override
	public WorkspaceService getWorkspaceService() {
		return this;
	}

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
	}

	@Override
	public void cancelProgress(WorkDoneProgressCancelParams params) {
	}
}
