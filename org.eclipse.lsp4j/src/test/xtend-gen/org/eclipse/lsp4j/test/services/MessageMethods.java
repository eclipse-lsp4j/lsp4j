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
  public final static String INITIALIZE = "initialize";
  
  public final static String SHUTDOWN = "shutdown";
  
  public final static String EXIT = "exit";
  
  public final static String DOC_COMPLETION = "textDocument/completion";
  
  public final static String RESOLVE_COMPLETION = "completionItem/resolve";
  
  public final static String DOC_HOVER = "textDocument/hover";
  
  public final static String DOC_SIGNATURE_HELP = "textDocument/signatureHelp";
  
  public final static String DOC_DEFINITION = "textDocument/definition";
  
  public final static String DOC_HIGHLIGHT = "textDocument/documentHighlight";
  
  public final static String DOC_REFERENCES = "textDocument/references";
  
  public final static String DOC_SYMBOL = "textDocument/documentSymbol";
  
  public final static String DOC_CODE_ACTION = "textDocument/codeAction";
  
  public final static String DOC_CODE_LENS = "textDocument/codeLens";
  
  public final static String RESOLVE_CODE_LENS = "codeLens/resolve";
  
  public final static String DOC_FOLDING_RANGE = "textDocument/foldingRange";
  
  public final static String DOC_FORMATTING = "textDocument/formatting";
  
  public final static String DOC_RANGE_FORMATTING = "textDocument/rangeFormatting";
  
  public final static String DOC_TYPE_FORMATTING = "textDocument/onTypeFormatting";
  
  public final static String DOC_RENAME = "textDocument/rename";
  
  public final static String WORKSPACE_SYMBOL = "workspace/symbol";
  
  public final static String DID_OPEN_DOC = "textDocument/didOpen";
  
  public final static String DID_CLOSE_DOC = "textDocument/didClose";
  
  public final static String DID_CHANGE_DOC = "textDocument/didChange";
  
  public final static String DID_SAVE_DOC = "textDocument/didSave";
  
  public final static String DID_CHANGE_CONF = "workspace/didChangeConfiguration";
  
  public final static String DID_CHANGE_FILES = "workspace/didChangeWatchedFiles";
  
  public final static String SHOW_DIAGNOSTICS = "textDocument/publishDiagnostics";
  
  public final static String SHOW_MESSAGE = "window/showMessage";
  
  public final static String SHOW_MESSAGE_REQUEST = "window/showMessageRequest";
  
  public final static String LOG_MESSAGE = "window/logMessage";
  
  public final static String TELEMETRY_EVENT = "telemetry/event";
}
