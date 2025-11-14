/**
 * Copyright (c) 2016-2019 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

package org.eclipse.lsp4j.test.services;

public interface MessageMethods {
	static final String INITIALIZE = "initialize";
	static final String SHUTDOWN = "shutdown";
	static final String EXIT = "exit";
	static final String DOC_COMPLETION = "textDocument/completion";
	static final String RESOLVE_COMPLETION = "completionItem/resolve";
	static final String DOC_HOVER = "textDocument/hover";
	static final String DOC_SIGNATURE_HELP = "textDocument/signatureHelp";
	static final String DOC_DECLARATION = "textDocument/declaration";
	static final String DOC_DEFINITION = "textDocument/definition";
	static final String DOC_TYPE_DEFINITION = "textDocument/typeDefinition";
	static final String DOC_IMPLEMENTATION = "textDocument/implementation";
	static final String DOC_HIGHLIGHT = "textDocument/documentHighlight";
	static final String DOC_REFERENCES = "textDocument/references";
	static final String DOC_SYMBOL = "textDocument/documentSymbol";
	static final String DOC_CODE_ACTION = "textDocument/codeAction";
	static final String DOC_CODE_LENS = "textDocument/codeLens";
	static final String RESOLVE_CODE_LENS = "codeLens/resolve";
	static final String DOC_FOLDING_RANGE = "textDocument/foldingRange";
	static final String DOC_FORMATTING = "textDocument/formatting";
	static final String DOC_RANGE_FORMATTING = "textDocument/rangeFormatting";
	static final String DOC_TYPE_FORMATTING = "textDocument/onTypeFormatting";
	static final String DOC_PREPARE_RENAME = "textDocument/prepareRename";
	static final String DOC_RENAME = "textDocument/rename";
	static final String DOC_DIAGNOSTIC = "textDocument/diagnostic";
	static final String DOC_INLINE_VALUE = "textDocument/inlineValue";
	static final String WORKSPACE_SYMBOL = "workspace/symbol";
	static final String WORKSPACE_DIAGNOSTIC = "workspace/diagnostic";
	static final String DID_OPEN_DOC = "textDocument/didOpen";
	static final String DID_CLOSE_DOC = "textDocument/didClose";
	static final String DID_CHANGE_DOC = "textDocument/didChange";
	static final String DID_SAVE_DOC = "textDocument/didSave";
	static final String DID_CHANGE_CONF = "workspace/didChangeConfiguration";
	static final String DID_CHANGE_FILES = "workspace/didChangeWatchedFiles";
	static final String SHOW_DIAGNOSTICS = "textDocument/publishDiagnostics";
	static final String SHOW_MESSAGE = "window/showMessage";
	static final String SHOW_MESSAGE_REQUEST = "window/showMessageRequest";
	static final String LOG_MESSAGE = "window/logMessage";
	static final String PROGRESS_NOTIFY = "$/progress";
	static final String PROGRESS_CREATE = "window/workDoneProgress/create";
	static final String PROGRESS_CANCEL = "window/workDoneProgress/cancel";
	static final String TELEMETRY_EVENT = "telemetry/event";
}
