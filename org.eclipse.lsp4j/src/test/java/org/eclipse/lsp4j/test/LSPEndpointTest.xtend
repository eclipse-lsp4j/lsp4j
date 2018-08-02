/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.test

import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.util.concurrent.CompletionException
import org.eclipse.lsp4j.MessageParams
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.PublishDiagnosticsParams
import org.eclipse.lsp4j.ShowMessageRequestParams
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageClient
import org.junit.Test

import static org.junit.Assert.*

class LSPEndpointTest {
	
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
	
	private static class DummyClient implements LanguageClient {
		override logMessage(MessageParams message) {}
		override publishDiagnostics(PublishDiagnosticsParams diagnostics) {}
		override showMessage(MessageParams messageParams) {}
		override showMessageRequest(ShowMessageRequestParams requestParams) {}
		override telemetryEvent(Object object) {}
	}

}