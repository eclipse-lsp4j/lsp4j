/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.test

import org.eclipse.lsp4j.DiagnosticSeverity
import org.eclipse.lsp4j.TextDocumentSyncKind
import org.eclipse.lsp4j.impl.CompletionItemImpl
import org.eclipse.lsp4j.impl.CompletionOptionsImpl
import org.eclipse.lsp4j.impl.DiagnosticImpl
import org.eclipse.lsp4j.impl.InitializeResultImpl
import org.eclipse.lsp4j.impl.PositionImpl
import org.eclipse.lsp4j.impl.PublishDiagnosticsParamsImpl
import org.eclipse.lsp4j.impl.RangeImpl
import org.eclipse.lsp4j.impl.ServerCapabilitiesImpl
import org.eclipse.lsp4j.services.json.LanguageServerProtocol
import org.eclipse.lsp4j.services.json.LanguageServerToJsonAdapter
import org.eclipse.lsp4j.services.json.MessageJsonHandler
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

import static extension org.eclipse.lsp4j.services.test.LineEndings.*
import org.eclipse.lsp4j.services.transport.trace.MessageTracer
import org.eclipse.lsp4j.Message
import org.eclipse.lsp4j.services.transport.trace.NullMessageTracer

class LanguageServerToJsonAdapterTest {
	
	static val TIMEOUT = 2000
	static val BUFFER_SIZE = 256
	
	MockedLanguageServer mockedServer
	LanguageServerToJsonAdapter adapter
	OutputStream adapterInput
	ByteArrayOutputStream adapterOutput
	
	@Before
	def void setup() {
		mockedServer = new MockedLanguageServer
		val pipe = new PipedInputStream(BUFFER_SIZE)
		adapterOutput = new ByteArrayOutputStream
		val jsonHandler = new MessageJsonHandler => [
			validateMessages = false
		]
		adapter = new LanguageServerToJsonAdapter(mockedServer, jsonHandler) => [
		    afterExit = []
		]
		adapterInput = new PipedOutputStream(pipe)
		adapter.messageTracer = new NullMessageTracer() {

            override onError(String message, Throwable t) {
                if (!(t instanceof MockedLanguageServer.ForcedException)) {
                    if (t !== null)
                        t.printStackTrace()
                    else if (message !== null)
                        System.err.println(message)
                }
            }

        }
		adapter.connect(pipe, adapterOutput)
	}
	
	@After
	def void teardown() {
		adapter.exit()
	}
	
	protected def void writeMessage(String content) {
		val responseBytes = content.bytes
		val headerBuilder = new StringBuilder
		headerBuilder.append(LanguageServerProtocol.H_CONTENT_LENGTH).append(': ').append(responseBytes.length).append('\r\n\r\n')
		adapterInput.write(headerBuilder.toString.bytes)
		adapterInput.write(responseBytes)
		adapterInput.flush()
	}
	
	protected def void assertOutput(String expected) {
		val startTime = System.currentTimeMillis
		val trimmed = expected.trim
		val targetSize = trimmed.bytes.length
		while (adapterOutput.size < targetSize) {
			Thread.sleep(10)
			assertTrue(adapterOutput.toString.toSystemLineEndings, System.currentTimeMillis - startTime < TIMEOUT)
		}
		assertEquals(trimmed, adapterOutput.toString.toSystemLineEndings)
	}
	
	protected def void assertMethodCall(String method, String params) {
		val startTime = System.currentTimeMillis
		while (mockedServer.methodCalls.get(method).empty) {
			Thread.sleep(10)
			assertTrue(System.currentTimeMillis - startTime < TIMEOUT)
		}
		if (params !== null)
			assertEquals(params.trim, String.valueOf(mockedServer.methodCalls.get(method).head).toSystemLineEndings)
	}
	
	@Test
	def void testInitialize() {
		mockedServer.response = new InitializeResultImpl => [
			capabilities = new ServerCapabilitiesImpl => [
				textDocumentSync = TextDocumentSyncKind.Incremental
				completionProvider = new CompletionOptionsImpl => [
					resolveProvider = true
				]
			]
		]
		writeMessage('''
			{
				"jsonrpc":"2.0",
				"id": "0",
				"method": "initialize",
				"params": {
					"processId": 123,
					"rootPath": "file:///tmp/"
				}
			}
		''')
		assertOutput('''
			Content-Length: 121
			
			{"id":"0","result":{"capabilities":{"textDocumentSync":2,"completionProvider":{"resolveProvider":true}}},"jsonrpc":"2.0"}
		''')
		assertMethodCall('initialize', '''
			InitializeParamsImpl [
			  processId = 123
			  rootPath = "file:///tmp/"
			  initializationOptions = null
			  capabilities = null
			  clientName = null
			]
		''')
	}
	
	@Test
	def void testExit() {
		val startTime = System.currentTimeMillis
		while (!adapter.isActive) {
			Thread.sleep(10)
			assertTrue(System.currentTimeMillis - startTime < TIMEOUT)
		}
		writeMessage('''
			{
				"jsonrpc": "2.0",
				"id": "0",
				"method": "exit"
			}
		''')
		while (adapter.isActive) {
			Thread.sleep(10)
			assertTrue(System.currentTimeMillis - startTime < TIMEOUT)
		}
	}
	
	@Test
	def void testDidOpen() {
		writeMessage('''
			{
				"jsonrpc":"2.0",
				"method": "textDocument/didOpen",
				"params": {
					"textDocument": {
						"uri": "file:///tmp/foo",
						"text": "bla bla"
					}
				}
			}
		''')
		assertMethodCall('didOpen', '''
			DidOpenTextDocumentParamsImpl [
			  textDocument = TextDocumentItemImpl [
			    uri = "file:///tmp/foo"
			    languageId = null
			    version = 0
			    text = "bla bla"
			  ]
			  text = null
			  uri = null
			]
		''')
	}
	
	@Test
	def void testCompletion() {
		mockedServer.response = newArrayList(
			new CompletionItemImpl => [
				insertText = "foo"
			],
			new CompletionItemImpl => [
				insertText = "bar"
			]
		)
		writeMessage('''
			{
				"jsonrpc": "2.0",
				"id": "0",
				"method": "textDocument/completion",
				"params": {
					"textDocument": {
						"uri": "file:///tmp/foo"
					},
					"position": {
						"line": 4,
						"character": 7
					}
				}
			}
		''')
		assertOutput('''
			Content-Length: 79
			
			{"id":"0","result":[{"insertText":"foo"},{"insertText":"bar"}],"jsonrpc":"2.0"}
		''')
		assertMethodCall('completion', '''
			TextDocumentPositionParamsImpl [
			  textDocument = TextDocumentIdentifierImpl [
			    uri = "file:///tmp/foo"
			  ]
			  uri = null
			  position = PositionImpl [
			    line = 4
			    character = 7
			  ]
			]
		''')
	}
	
	@Test
	def void testDelayedCompletion() {
		mockedServer.response = newArrayList(
			new CompletionItemImpl => [
				insertText = "foo"
			],
			new CompletionItemImpl => [
				insertText = "bar"
			]
		)
		mockedServer.blockResponse = true
		writeMessage('''
			{
				"jsonrpc": "2.0",
				"id": "0",
				"method": "textDocument/completion",
				"params": {
					"textDocument": {
						"uri": "file:///tmp/foo"
					},
					"position": {
						"line": 4,
						"character": 7
					}
				}
			}
		''')
		Thread.sleep(150)
		assertEquals('', adapterOutput.toString)
		mockedServer.blockResponse = false
		assertOutput('''
			Content-Length: 79
			
			{"id":"0","result":[{"insertText":"foo"},{"insertText":"bar"}],"jsonrpc":"2.0"}
		''')
	}
	
	@Test
	def void testPublishDiagnostics() {
		mockedServer.textDocumentService.publishDiagnostics(new PublishDiagnosticsParamsImpl => [
			diagnostics = newArrayList(
				new DiagnosticImpl => [
					range = new RangeImpl => [
						start = new PositionImpl(4, 22)
						end = new PositionImpl(4, 26)
					]
					severity = DiagnosticSeverity.Error
					message = "Couldn't resolve reference to State 'bard'."
				]
			)
			uri = "file:///tmp/foo"
		])
		assertOutput('''
			Content-Length: 273
			
			{"method":"textDocument/publishDiagnostics","params":{"uri":"file:///tmp/foo","diagnostics":[{"range":{"start":{"line":4,"character":22},"end":{"line":4,"character":26}},"severity":1,"message":"Couldn\u0027t resolve reference to State \u0027bard\u0027."}]},"jsonrpc":"2.0"}
		''')
	}
	
	@Test
	def void testCancel() {
		mockedServer.response = 'dummy'
		mockedServer.blockResponse = true
		writeMessage('''
			{
				"jsonrpc": "2.0",
				"id": "0",
				"method": "textDocument/completion",
				"params": {
					"textDocument": {
						"uri": "file:///tmp/foo"
					},
					"position": {
						"line": 4,
						"character": 7
					}
				}
			}
		''')
		Thread.sleep(50)
		writeMessage('''
			{
				"jsonrpc": "2.0",
				"method": "$/cancelRequest",
				"params": {
					"id": "0"
				}
			}
		''')
		Thread.sleep(150)
		assertEquals('', adapterOutput.toString)
		mockedServer.blockResponse = false
	}
	
	@Test
	def void testError() {
		mockedServer.generateError = 'Foo!'
		writeMessage('''
			{
				"jsonrpc": "2.0",
				"id": "0",
				"method": "textDocument/completion",
				"params": {
					"textDocument": {
						"uri": "file:///tmp/foo"
					},
					"position": {
						"line": 4,
						"character": 7
					}
				}
			}
		''')
		assertOutput('''
			Content-Length: 67
			
			{"id":"0","error":{"code":-32600,"message":"Foo!"},"jsonrpc":"2.0"}
		''')
	}

	@Test
	def void testMessageExceedsBuffer() {
		mockedServer.response = new InitializeResultImpl
		writeMessage('''
			{
				"jsonrpc":"2.0",
				"id": "0",
				"method": "initialize",
				"params": {
					"processId": 123,
					"rootPath": "file:///very/long/path/aaaabbbbccccddddaaaabbbbccccddddaaaabbbbccccddddaaaabbbbccccddddaaaabbbbccccddddaaaabbbbccccddddaaaabbbbccccddddaaaabbbbccccdddd"
				}
			}
		''')
		assertOutput('''
			Content-Length: 38
			
			{"id":"0","result":{},"jsonrpc":"2.0"}
		''')
	}
	
}