/**
 * Copyright (c) 2016 TypeFox and others.
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

@SuppressWarnings("all")
public interface MessageMethods {
  public static final String INITIALIZE = "initialize";
  
  public static final String SHUTDOWN = "shutdown";
  
  public static final String EXIT = "exit";
  
  public static final String DOC_COMPLETION = "textDocument/completion";
  
  public static final String RESOLVE_COMPLETION = "completionItem/resolve";
  
  public static final String DOC_HOVER = "textDocument/hover";
  
  public static final String DOC_SIGNATURE_HELP = "textDocument/signatureHelp";
  
  public static final String DOC_DECLARATION = "textDocument/declaration";
  
  public static final String DOC_DEFINITION = "textDocument/definition";
  
  public static final String DOC_TYPE_DEFINITION = "textDocument/typeDefinition";
  
  public static final String DOC_IMPLEMENTATION = "textDocument/implementation";
  
  public static final String DOC_HIGHLIGHT = "textDocument/documentHighlight";
  
  public static final String DOC_REFERENCES = "textDocument/references";
  
  public static final String DOC_SYMBOL = "textDocument/documentSymbol";
  
  public static final String DOC_CODE_ACTION = "textDocument/codeAction";
  
  public static final String DOC_CODE_LENS = "textDocument/codeLens";
  
  public static final String RESOLVE_CODE_LENS = "codeLens/resolve";
  
  public static final String DOC_FOLDING_RANGE = "textDocument/foldingRange";
  
  public static final String DOC_FORMATTING = "textDocument/formatting";
  
  public static final String DOC_RANGE_FORMATTING = "textDocument/rangeFormatting";
  
  public static final String DOC_TYPE_FORMATTING = "textDocument/onTypeFormatting";
  
  public static final String DOC_RENAME = "textDocument/rename";
  
  public static final String WORKSPACE_SYMBOL = "workspace/symbol";
  
  public static final String DID_OPEN_DOC = "textDocument/didOpen";
  
  public static final String DID_CLOSE_DOC = "textDocument/didClose";
  
  public static final String DID_CHANGE_DOC = "textDocument/didChange";
  
  public static final String DID_SAVE_DOC = "textDocument/didSave";
  
  public static final String DID_CHANGE_CONF = "workspace/didChangeConfiguration";
  
  public static final String DID_CHANGE_FILES = "workspace/didChangeWatchedFiles";
  
  public static final String SHOW_DIAGNOSTICS = "textDocument/publishDiagnostics";
  
  public static final String SHOW_MESSAGE = "window/showMessage";
  
  public static final String SHOW_MESSAGE_REQUEST = "window/showMessageRequest";
  
  public static final String LOG_MESSAGE = "window/logMessage";
  
  public static final String TELEMETRY_EVENT = "telemetry/event";
}
