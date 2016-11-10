/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.transport

interface MessageMethods {
	
	public static val INITIALIZE = 'initialize'
	public static val SHUTDOWN = 'shutdown'
	public static val EXIT = 'exit'
	public static val DOC_COMPLETION = 'textDocument/completion'
	public static val RESOLVE_COMPLETION = 'completionItem/resolve'
	public static val DOC_HOVER = 'textDocument/hover'
	public static val DOC_SIGNATURE_HELP = 'textDocument/signatureHelp'
	public static val DOC_DEFINITION = 'textDocument/definition'
	public static val DOC_HIGHLIGHT = 'textDocument/documentHighlight'
	public static val DOC_REFERENCES = 'textDocument/references'
	public static val DOC_SYMBOL = 'textDocument/documentSymbol'
	public static val DOC_CODE_ACTION = 'textDocument/codeAction'
	public static val DOC_CODE_LENS = 'textDocument/codeLens'
	public static val RESOLVE_CODE_LENS = 'codeLens/resolve'
	public static val DOC_FORMATTING = 'textDocument/formatting'
	public static val DOC_RANGE_FORMATTING = 'textDocument/rangeFormatting'
	public static val DOC_TYPE_FORMATTING = 'textDocument/onTypeFormatting'
	public static val DOC_RENAME = 'textDocument/rename'
	public static val WORKSPACE_SYMBOL = 'workspace/symbol'
	
	public static val DID_OPEN_DOC = 'textDocument/didOpen'
	public static val DID_CLOSE_DOC = 'textDocument/didClose'
	public static val DID_CHANGE_DOC = 'textDocument/didChange'
	public static val DID_SAVE_DOC = 'textDocument/didSave'
	public static val DID_CHANGE_CONF = 'workspace/didChangeConfiguration'
	public static val DID_CHANGE_FILES = 'workspace/didChangeWatchedFiles'
	public static val SHOW_DIAGNOSTICS = 'textDocument/publishDiagnostics'
	public static val SHOW_MESSAGE = 'window/showMessage'
	public static val SHOW_MESSAGE_REQUEST = 'window/showMessageRequest'
	public static val LOG_MESSAGE = 'window/logMessage'
	public static val TELEMETRY_EVENT = 'telemetry/event'
	
	public static val CANCEL = '$/cancelRequest'
	
}