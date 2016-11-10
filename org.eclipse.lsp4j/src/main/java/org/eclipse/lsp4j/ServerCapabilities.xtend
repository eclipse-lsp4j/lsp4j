/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI
import javax.annotation.Nullable

@LanguageServerAPI
interface ServerCapabilities {
	
	/**
	 * Defines how text documents are synced.
	 */
	@Nullable
	def TextDocumentSyncKind getTextDocumentSync()
	
	/**
	 * The server provides hover support.
	 */
	@Nullable
	def Boolean isHoverProvider()
	
	/**
	 * The server provides completion support.
	 */
	@Nullable
	def CompletionOptions getCompletionProvider()
	
	/**
	 * The server provides signature help support.
	 */
	@Nullable
	def SignatureHelpOptions getSignatureHelpProvider()
	
	/**
	 * The server provides goto definition support.
	 */
	@Nullable
	def Boolean isDefinitionProvider()
	
	/**
	 * The server provides find references support.
	 */
	@Nullable
	def Boolean isReferencesProvider()
	
	/**
	 * The server provides document highlight support.
	 */
	@Nullable
	def Boolean isDocumentHighlightProvider()
	
	/**
	 * The server provides document symbol support.
	 */
	@Nullable
	def Boolean isDocumentSymbolProvider()
	
	/**
	 * The server provides workspace symbol support.
	 */
	@Nullable
	def Boolean isWorkspaceSymbolProvider()
	
	/**
	 * The server provides code actions.
	 */
	@Nullable
	def Boolean isCodeActionProvider()
	
	/**
	 * The server provides code lens.
	 */
	@Nullable
	def CodeLensOptions getCodeLensProvider()
	
	/**
	 * The server provides document formatting.
	 */
	@Nullable
	def Boolean isDocumentFormattingProvider()
	
	/**
	 * The server provides document range formatting.
	 */
	@Nullable
	def Boolean isDocumentRangeFormattingProvider()
	
	/**
	 * The server provides document formatting on typing.
	 */
	@Nullable
	def DocumentOnTypeFormattingOptions getDocumentOnTypeFormattingProvider()
	
	/**
	 * The server provides rename support.
	 */
	@Nullable
	def Boolean isRenameProvider()
	
}