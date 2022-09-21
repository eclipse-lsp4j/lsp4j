/******************************************************************************
 * Copyright (c) 2022 KamasamaK and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.services;

import org.eclipse.lsp4j.DidChangeNotebookDocumentParams;
import org.eclipse.lsp4j.DidCloseNotebookDocumentParams;
import org.eclipse.lsp4j.DidOpenNotebookDocumentParams;
import org.eclipse.lsp4j.DidSaveNotebookDocumentParams;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

@JsonSegment("notebookDocument")
public interface NotebookDocumentService {
	/**
	 * The open notification is sent from the client to the server when a notebook document
	 * is opened. It is only sent by a client if the server requested the synchronization
	 * mode {@code notebook} in its {@link ServerCapabilities#notebookDocumentSync} capability.
	 * <p>
	 * Registration Options: {@link org.eclipse.lsp4j.NotebookDocumentSyncRegistrationOptions}
	 * <p>
	 * Since 3.17.0
	 */
	@JsonNotification
	void didOpen(DidOpenNotebookDocumentParams params);

	/**
	 * The change notification is sent from the client to the server when a notebook document changes.
	 * It is only sent by a client if the server requested the synchronization mode {@code notebook}
	 * in its {@link ServerCapabilities#notebookDocumentSync} capability.
	 * <p>
	 * Registration Options: {@link org.eclipse.lsp4j.NotebookDocumentSyncRegistrationOptions}
	 * <p>
	 * Since 3.17.0
	 */
	@JsonNotification
	void didChange(DidChangeNotebookDocumentParams params);

	/**
	 * The save notification is sent from the client to the server when a notebook document is saved.
	 * It is only sent by a client if the server requested the synchronization mode {@code notebook}
	 * in its {@link ServerCapabilities#notebookDocumentSync} capability.
	 * <p>
	 * Registration Options: {@link org.eclipse.lsp4j.NotebookDocumentSyncRegistrationOptions}
	 * <p>
	 * Since 3.17.0
	 */
	@JsonNotification
	void didSave(DidSaveNotebookDocumentParams params);

	/**
	 * The close notification is sent from the client to the server when a notebook document is closed.
	 * It is only sent by a client if the server requested the synchronization mode {@code notebook}
	 * in its {@link ServerCapabilities#notebookDocumentSync} capability.
	 * <p>
	 * Registration Options: {@link org.eclipse.lsp4j.NotebookDocumentSyncRegistrationOptions}
	 * <p>
	 * Since 3.17.0
	 */
	@JsonNotification
	void didClose(DidCloseNotebookDocumentParams params);
}
