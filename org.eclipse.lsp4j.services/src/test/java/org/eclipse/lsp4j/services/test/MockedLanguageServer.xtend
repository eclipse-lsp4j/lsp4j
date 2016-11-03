/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.test

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
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
import org.eclipse.lsp4j.MessageParams
import org.eclipse.lsp4j.PublishDiagnosticsParams
import org.eclipse.lsp4j.ReferenceParams
import org.eclipse.lsp4j.RenameParams
import org.eclipse.lsp4j.ShowMessageRequestParams
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.WorkspaceSymbolParams
import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.lsp4j.services.TextDocumentService
import org.eclipse.lsp4j.services.WindowService
import org.eclipse.lsp4j.services.WorkspaceService
import org.eclipse.lsp4j.services.json.InvalidMessageException
import java.util.List
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

class MockedLanguageServer implements LanguageServer {
	
	static class ForcedException extends InvalidMessageException {
		new(String message) {
			super(message)
		}
	}
	
	val textDocumentService = new MockedTextDocumentService(this)
	
	val windowService = new MockedWindowService
	
	val workspaceService = new MockedWorkspaceService(this)
	
	@Accessors
	val Multimap<String, Object> methodCalls = HashMultimap.create
	
	@Accessors(PUBLIC_SETTER)
	Object response
	
	@Accessors(PUBLIC_SETTER)
	boolean blockResponse
	
	@Accessors(PUBLIC_SETTER)
	String generateError
	
	val List<Consumer<Object>> telemetryEventCallbacks = newArrayList
	
	protected def <T> CompletableFuture<T> getPromise() {
		if (generateError !== null) {
			CompletableFuture.supplyAsync[
				throw new ForcedException(generateError)
			]
		} else if (blockResponse) {
			CompletableFuture.supplyAsync[
				while (blockResponse) {
					Thread.sleep(100)
				}
				return response as T
			]
		} else {
			CompletableFuture.completedFuture(response as T)
		}
	}
	
	override MockedTextDocumentService getTextDocumentService() {
		textDocumentService
	}
	
	override MockedWindowService getWindowService() {
		windowService
	}
	
	override MockedWorkspaceService getWorkspaceService() {
		workspaceService
	}
	
	override initialize(InitializeParams params) {
		methodCalls.put('initialize', params)
		return promise
	}
	
	override shutdown() {
		methodCalls.put('shutdown', new Object)
	}
	
	override exit() {
		methodCalls.put('exit', new Object)
	}
	
	override onTelemetryEvent(Consumer<Object> callback) {
		telemetryEventCallbacks.add(callback)
	}
	
	def void telemetryEvent(Object params) {
		for (c : telemetryEventCallbacks) {
			c.accept(params)
		}
	}
	
	@FinalFieldsConstructor
	static class MockedTextDocumentService implements TextDocumentService {
		
		val MockedLanguageServer server
		
		val List<Consumer<PublishDiagnosticsParams>> publishDiagnosticCallbacks = newArrayList
		
		override completion(TextDocumentPositionParams position) {
			server.methodCalls.put('completion', position)
			return server.promise
		}
		
		override resolveCompletionItem(CompletionItem unresolved) {
			server.methodCalls.put('resolveCompletionItem', unresolved)
			return server.promise
		}
		
		override hover(TextDocumentPositionParams position) {
			server.methodCalls.put('hover', position)
			return server.promise
		}
		
		override signatureHelp(TextDocumentPositionParams position) {
			server.methodCalls.put('signatureHelp', position)
			return server.promise
		}
		
		override definition(TextDocumentPositionParams position) {
			server.methodCalls.put('definition', position)
			return server.promise
		}
		
		override references(ReferenceParams params) {
			server.methodCalls.put('references', params)
			return server.promise
		}
		
		override documentHighlight(TextDocumentPositionParams position) {
			server.methodCalls.put('documentHighlight', position)
			return server.promise
		}
		
		override documentSymbol(DocumentSymbolParams params) {
			server.methodCalls.put('documentSymbol', params)
			return server.promise
		}
		
		override codeAction(CodeActionParams params) {
			server.methodCalls.put('codeAction', params)
			return server.promise
		}
		
		override codeLens(CodeLensParams params) {
			server.methodCalls.put('codeLens', params)
			return server.promise
		}
		
		override resolveCodeLens(CodeLens unresolved) {
			server.methodCalls.put('resolveCodeLens', unresolved)
			return server.promise
		}
		
		override formatting(DocumentFormattingParams params) {
			server.methodCalls.put('formatting', params)
			return server.promise
		}
		
		override rangeFormatting(DocumentRangeFormattingParams params) {
			server.methodCalls.put('rangeFormatting', params)
			return server.promise
		}
		
		override onTypeFormatting(DocumentOnTypeFormattingParams params) {
			server.methodCalls.put('onTypeFormatting', params)
			return server.promise
		}
		
		override rename(RenameParams params) {
			server.methodCalls.put('rename', params)
			return server.promise
		}
		
		override didOpen(DidOpenTextDocumentParams params) {
			server.methodCalls.put('didOpen', params)
		}
		
		override didChange(DidChangeTextDocumentParams params) {
			server.methodCalls.put('didChange', params)
		}
		
		override didClose(DidCloseTextDocumentParams params) {
			server.methodCalls.put('didClose', params)
		}
		
		override didSave(DidSaveTextDocumentParams params) {
			server.methodCalls.put('didSave', params)
		}
		
		override onPublishDiagnostics(Consumer<PublishDiagnosticsParams> callback) {
			publishDiagnosticCallbacks.add(callback)
		}
		
		def void publishDiagnostics(PublishDiagnosticsParams params) {
			for (c : publishDiagnosticCallbacks) {
				c.accept(params)
			}
		}
		
	}
	
	static class MockedWindowService implements WindowService {
		
		val List<Consumer<MessageParams>> showMessageCallbacks = newArrayList
		
		val List<Consumer<ShowMessageRequestParams>> showMessageRequestCallbacks = newArrayList
		
		val List<Consumer<MessageParams>> logMessageCallbacks = newArrayList
		
		override onShowMessage(Consumer<MessageParams> callback) {
			showMessageCallbacks.add(callback)
		}
		
		def void showMessage(MessageParams params) {
			for (c : showMessageCallbacks) {
				c.accept(params)
			}
		}
		
		override onShowMessageRequest(Consumer<ShowMessageRequestParams> callback) {
			showMessageRequestCallbacks.add(callback)
		}
		
		def void showMessageRequest(ShowMessageRequestParams params) {
			for (c : showMessageRequestCallbacks) {
				c.accept(params)
			}
		}
		
		override onLogMessage(Consumer<MessageParams> callback) {
			logMessageCallbacks.add(callback)
		}
		
		def void logMessage(MessageParams params) {
			for (c : logMessageCallbacks) {
				c.accept(params)
			}
		}
		
	}
	
	@FinalFieldsConstructor
	static class MockedWorkspaceService implements WorkspaceService {
		
		val MockedLanguageServer server
		
		override symbol(WorkspaceSymbolParams params) {
			server.methodCalls.put('symbol', params)
			return server.promise
		}
		
		override didChangeConfiguration(DidChangeConfigurationParams params) {
			server.methodCalls.put('didChangeConfiguraton', params)
		}
		
		override didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
			server.methodCalls.put('didChangeWatchedFiles', params)
		}
		
	}
	
}