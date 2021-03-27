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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowDocumentParams;
import org.eclipse.lsp4j.ShowDocumentResult;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.services.LanguageClient;

public class MockLanguageClient implements LanguageClient {

	@Override
	public void telemetryEvent(Object object) {
	}

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
	}

	@Override
	public void showMessage(MessageParams messageParams) {
	}

	@Override
	public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CompletableFuture<ShowDocumentResult> showDocument(ShowDocumentParams requestParams) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void logMessage(MessageParams message) {
	}

	@Override
	public CompletableFuture<List<WorkspaceFolder>> workspaceFolders() {
		throw new UnsupportedOperationException();
	}
	
}
