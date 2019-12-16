/******************************************************************************
 * Copyright (c) 2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.test

import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionException
import java.util.concurrent.TimeUnit
import org.eclipse.lsp4j.DidChangeConfigurationParams
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.DidChangeWatchedFilesParams
import org.eclipse.lsp4j.DidCloseTextDocumentParams
import org.eclipse.lsp4j.DidOpenTextDocumentParams
import org.eclipse.lsp4j.DidSaveTextDocumentParams
import org.eclipse.lsp4j.InitializeParams
import org.eclipse.lsp4j.MessageParams
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.PublishDiagnosticsParams
import org.eclipse.lsp4j.ShowMessageRequestParams
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.jsonrpc.services.GenericEndpoint
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.lsp4j.services.TextDocumentService
import org.eclipse.lsp4j.services.WorkspaceService
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.Test

import static org.junit.Assert.*

class LSPEndpointTest {
	
	static val TIMEOUT = 2000
	
	@Test
	def void testIssue152() throws Exception {
		val client = new DummyClient
		val in = new PipedInputStream
		val responseStream = new PipedOutputStream
		in.connect(responseStream)
		val responseWriter = new OutputStreamWriter(responseStream)
		val out = new ByteArrayOutputStream
		val launcher = LSPLauncher.createClientLauncher(client, in, out, true, null)
		val future = launcher.startListening()
		
		val hoverResult = launcher.remoteProxy.textDocumentService.hover(new TextDocumentPositionParams => [
			textDocument = new TextDocumentIdentifier('foo')
			position = new Position(0, 0)
		])
		responseWriter.write('Content-Length: 60\r\n\r\n')
		responseWriter.write('{"jsonrpc":"2.0","id":"1","result":{"contents":[null,null]}}')
		responseWriter.flush()
		
		try {
			hoverResult.join()
			fail('Expected a CompletionException to be thrown.')
		} catch (CompletionException exception) {
			assertEquals('''
				Lists must not contain null references. Path: $.result.contents[0]
				Lists must not contain null references. Path: $.result.contents[1]
			'''.toString.trim, exception.cause.message)
			assertFalse(future.isDone)
		} finally {
			in.close()
		}
	}
	
	@Test
	def void testDiagnosticCodeIsStringOrNumber() throws Exception {
		val client = new DummyClient
		val in = new PipedInputStream
		val responseStream = new PipedOutputStream
		in.connect(responseStream)
		val responseWriter = new OutputStreamWriter(responseStream)
		val out = new ByteArrayOutputStream
		val launcher = LSPLauncher.createClientLauncher(client, in, out, true, null)
		launcher.startListening()

		responseWriter.write('Content-Length: 202\r\n\r\n')
		responseWriter.write('{"jsonrpc":"2.0","method":"textDocument/publishDiagnostics","params":{"uri":"any","diagnostics":[{"range":{"start":{"line":41,"character":0},"end":{"line":41,"character":9}},code:1,message:"message"}]}}')
		responseWriter.flush()

		assertEquals(1, client.publishedDiagnostics.get(TIMEOUT, TimeUnit.MILLISECONDS).diagnostics.get(0).getCode().getRight().intValue());
	}

	@Test
	def void testIssue346() throws Exception {
		val logMessages = new LogMessageAccumulator
		try {
			logMessages.registerTo(GenericEndpoint)
			
			val server = new DummyServer
			val in = new PipedInputStream
			val messageStream = new PipedOutputStream
			in.connect(messageStream)
			val messageWriter = new OutputStreamWriter(messageStream)
			val out = new ByteArrayOutputStream
			val launcher = LSPLauncher.createServerLauncher(server, in, out, true, null)
			launcher.startListening()
			
			messageWriter.write('Content-Length: 48\r\n\r\n')
			messageWriter.write('{"jsonrpc": "2.0","method": "exit","params": {}}')
			messageWriter.flush()
			
			server.exited.get(TIMEOUT, TimeUnit.MILLISECONDS)
			assertTrue(logMessages.records.join('\n', [message]), logMessages.records.isEmpty)
		} finally {
			logMessages.unregister();
		}
	}
	
	@Accessors
	private static class DummyServer implements LanguageServer {
		
		val textDocumentService = new TextDocumentService {
			override didChange(DidChangeTextDocumentParams params) {}
			override didClose(DidCloseTextDocumentParams params) {}
			override didOpen(DidOpenTextDocumentParams params) {}
			override didSave(DidSaveTextDocumentParams params) {}
		}
		
		val workspaceService = new WorkspaceService {
			override didChangeConfiguration(DidChangeConfigurationParams params) {}
			override didChangeWatchedFiles(DidChangeWatchedFilesParams params) {}
		}
		
		override initialize(InitializeParams params) {}
		override shutdown() {}
		
		val exited = new CompletableFuture<Void>
		override exit() {
			exited.complete(null)
		}
	}
	
	private static class DummyClient implements LanguageClient {
		override logMessage(MessageParams message) {}
		val publishedDiagnostics= new CompletableFuture<PublishDiagnosticsParams>
		override publishDiagnostics(PublishDiagnosticsParams diagnostics) {
                    publishedDiagnostics.complete(diagnostics)
                }
		override showMessage(MessageParams messageParams) {}
		override showMessageRequest(ShowMessageRequestParams requestParams) {}
		override telemetryEvent(Object object) {}
	}

}