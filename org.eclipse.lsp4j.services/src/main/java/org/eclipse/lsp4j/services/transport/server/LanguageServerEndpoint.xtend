/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.transport.server

import org.eclipse.lsp4j.CancelParams
import org.eclipse.lsp4j.CodeActionParams
import org.eclipse.lsp4j.CodeLens
import org.eclipse.lsp4j.CodeLensParams
import org.eclipse.lsp4j.CompletionItem
import org.eclipse.lsp4j.DidChangeConfigurationParams
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.DidChangeWatchedFilesParams
import org.eclipse.lsp4j.DidCloseTextDocumentParams
import org.eclipse.lsp4j.DidOpenTextDocumentParams
import org.eclipse.lsp4j.DidSaveTextDocumentParams
import org.eclipse.lsp4j.DocumentFormattingParams
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams
import org.eclipse.lsp4j.DocumentRangeFormattingParams
import org.eclipse.lsp4j.DocumentSymbolParams
import org.eclipse.lsp4j.InitializeParams
import org.eclipse.lsp4j.Message
import org.eclipse.lsp4j.NotificationMessage
import org.eclipse.lsp4j.ReferenceParams
import org.eclipse.lsp4j.RenameParams
import org.eclipse.lsp4j.RequestMessage
import org.eclipse.lsp4j.ResponseErrorCode
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.WorkspaceSymbolParams
import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.lsp4j.services.transport.AbstractLanguageEndpoint
import java.util.Map
import java.util.concurrent.CompletionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors

class LanguageServerEndpoint extends AbstractLanguageEndpoint {

    @Accessors(PROTECTED_GETTER)
    val LanguageServer delegate

    val Map<String, Future<?>> requestFutures = newHashMap

    AtomicBoolean shutdownReceived = new AtomicBoolean(false)

    new(LanguageServer delegate) {
        this(delegate, Executors.newCachedThreadPool)
    }

    new(LanguageServer delegate, ExecutorService executorService) {
        super(executorService)
        this.delegate = delegate
        registerCallbacks()
    }

    protected def void registerCallbacks() {
        delegate.onTelemetryEvent [
            sendNotification(TELEMETRY_EVENT, it)
        ]
        delegate.textDocumentService.onPublishDiagnostics [
            sendNotification(SHOW_DIAGNOSTICS, it)
        ]
        delegate.windowService.onLogMessage [
            sendNotification(LOG_MESSAGE, it)
        ]
        delegate.windowService.onShowMessage [
            sendNotification(SHOW_MESSAGE, it)
        ]
        delegate.windowService.onShowMessageRequest [
            sendNotification(SHOW_MESSAGE_REQUEST, it)
        ]
    }

    protected def void checkAlive(Integer processId) {
        if(processId === null) return;

        val executor = Executors.newSingleThreadScheduledExecutor
        executor.scheduleAtFixedRate([
            if (!processId.alive) {
                exit
            }
        ], 3000, 3000, TimeUnit.MILLISECONDS)
    }

    protected def boolean isAlive(int processId) {
        val process = Runtime.runtime.exec(#['kill', '-0', String.valueOf(processId)])
        val exitCode = process.waitFor
        return exitCode == 0
    }

    def void exit() {
        try {
            delegate.exit()
            super.close()
        } finally {
            afterExit?.accept(shutdownReceived.get)
        }
    }

    @Accessors Consumer<Boolean> afterExit = [ shutdownReceived |
        System.exit(if(shutdownReceived) 0 else 1)
    ]

    override accept(Message message) {
        doAccept(message)
    }

    protected def dispatch void doAccept(RequestMessage message) {
        try {
            val future = switch message.method {
                case INITIALIZE: {
                    val params = message.params as InitializeParams
                    checkAlive(params.processId)
                    delegate.initialize(params)
                }
                case DOC_COMPLETION:
                    delegate.textDocumentService.completion(message.params as TextDocumentPositionParams)
                case RESOLVE_COMPLETION:
                    delegate.textDocumentService.resolveCompletionItem(message.params as CompletionItem)
                case DOC_HOVER:
                    delegate.textDocumentService.hover(message.params as TextDocumentPositionParams)
                case DOC_SIGNATURE_HELP:
                    delegate.textDocumentService.signatureHelp(message.params as TextDocumentPositionParams)
                case DOC_DEFINITION:
                    delegate.textDocumentService.definition(message.params as TextDocumentPositionParams)
                case DOC_HIGHLIGHT:
                    delegate.textDocumentService.documentHighlight(message.params as TextDocumentPositionParams)
                case DOC_REFERENCES:
                    delegate.textDocumentService.references(message.params as ReferenceParams)
                case DOC_SYMBOL:
                    delegate.textDocumentService.documentSymbol(message.params as DocumentSymbolParams)
                case DOC_CODE_ACTION:
                    delegate.textDocumentService.codeAction(message.params as CodeActionParams)
                case DOC_CODE_LENS:
                    delegate.textDocumentService.codeLens(message.params as CodeLensParams)
                case RESOLVE_CODE_LENS:
                    delegate.textDocumentService.resolveCodeLens(message.params as CodeLens)
                case DOC_FORMATTING:
                    delegate.textDocumentService.formatting(message.params as DocumentFormattingParams)
                case DOC_RANGE_FORMATTING:
                    delegate.textDocumentService.rangeFormatting(message.params as DocumentRangeFormattingParams)
                case DOC_TYPE_FORMATTING:
                    delegate.textDocumentService.onTypeFormatting(message.params as DocumentOnTypeFormattingParams)
                case DOC_RENAME:
                    delegate.textDocumentService.rename(message.params as RenameParams)
                case WORKSPACE_SYMBOL:
                    delegate.workspaceService.symbol(message.params as WorkspaceSymbolParams)
                case SHUTDOWN: {
                    shutdownReceived.set(true)
                    delegate.shutdown()
                    null
                }
                case EXIT: {
                    exit()
                    null
                }
                default: {
                    sendResponseError(message.id, "Invalid method: " + message.method, ResponseErrorCode.MethodNotFound)
                    null
                }
            }
            if (future !== null) {
                synchronized (requestFutures) {
                    requestFutures.put(message.id, future)
                }
                future.whenComplete [ result, exception |
                    synchronized (requestFutures) {
                        requestFutures.remove(message.id)
                    }
                    if (!future.isCancelled) {
                        if (result !== null)
                            sendResponse(message.id, result)
                        else if (exception instanceof CompletionException)
                            handleRequestError(message, exception.cause)
                        else if (exception !== null)
                            handleRequestError(message, exception)
                    }
                ]
            }
        } catch (Exception e) {
            handleRequestError(message, e)
        }
    }

    protected def dispatch void doAccept(NotificationMessage message) {
        try {
            switch message.method {
                case DID_OPEN_DOC:
                    delegate.textDocumentService.didOpen(message.params as DidOpenTextDocumentParams)
                case DID_CHANGE_DOC:
                    delegate.textDocumentService.didChange(message.params as DidChangeTextDocumentParams)
                case DID_CLOSE_DOC:
                    delegate.textDocumentService.didClose(message.params as DidCloseTextDocumentParams)
                case DID_SAVE_DOC:
                    delegate.textDocumentService.didSave(message.params as DidSaveTextDocumentParams)
                case DID_CHANGE_CONF:
                    delegate.workspaceService.didChangeConfiguraton(message.params as DidChangeConfigurationParams)
                case DID_CHANGE_FILES:
                    delegate.workspaceService.didChangeWatchedFiles(message.params as DidChangeWatchedFilesParams)
                case CANCEL:
                    cancel(message.params as CancelParams)
            }
        } catch (Exception e) {
            logError(e)
        }
    }

    protected def cancel(CancelParams params) {
        synchronized (requestFutures) {
            val future = requestFutures.get(params.id)
            if (future !== null)
                future.cancel(true)
        }
    }

}
