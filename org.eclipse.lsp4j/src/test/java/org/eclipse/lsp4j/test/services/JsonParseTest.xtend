/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.test.services

import java.util.ArrayList
import java.util.HashMap
import org.eclipse.lsp4j.Diagnostic
import org.eclipse.lsp4j.DiagnosticSeverity
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.DocumentFormattingParams
import org.eclipse.lsp4j.FormattingOptions
import org.eclipse.lsp4j.Hover
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.PublishDiagnosticsParams
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.TextDocumentContentChangeEvent
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.TextEdit
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier
import org.eclipse.lsp4j.WorkspaceEdit
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.jsonrpc.messages.Message
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageServer
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

class JsonParseTest {
	
	MessageJsonHandler jsonHandler
	
	@Before
	def void setup() {
		val methods = ServiceEndpoints.getSupportedMethods(LanguageServer)
		val clientMethods = ServiceEndpoints.getSupportedMethods(LanguageClient)
		val all = new HashMap
		all.putAll(methods)
		all.putAll(clientMethods)
		jsonHandler = new MessageJsonHandler(all)
	}
	
	private def void assertParse(CharSequence json, Message expected) {
		assertEquals(expected.toString, jsonHandler.parseMessage(json).toString)
	}
	
	@Test
	def void testCompletion() {
		'''
			{
				"jsonrpc": "2.0",
				"id": 1,
				"method": "textDocument/completion",
				"params": {
					"textDocument": {
						"uri": "file:///tmp/foo"
					},
					"position": {
						"line": 4,
						"character": 22
					}
				}
			}
		'''.assertParse(new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.DOC_COMPLETION
			params = new TextDocumentPositionParams => [
				textDocument = new TextDocumentIdentifier => [
					uri = "file:///tmp/foo"
				]
				position = new Position => [
					line = 4
					character = 22 
				]
			]
		])
	}
	
	@Test
	def void testDidChange() {
		'''
			{
				"jsonrpc": "2.0",
				"method": "textDocument/didChange",
				"params": {
					"textDocument": {
						"uri": "file:///tmp/foo"
					},
					"contentChanges": [
						{
							"range": {
								"start": {
									"line": 7,
									"character": 12
								},
								"end": {
									"line": 8,
									"character": 16
								}
							},
							"rangeLength": 20,
							"text": "bar"
						}
					]
				}
			}
		'''.assertParse(new NotificationMessage => [
			jsonrpc = "2.0"
			method = MessageMethods.DID_CHANGE_DOC
			params = new DidChangeTextDocumentParams => [
				textDocument = new VersionedTextDocumentIdentifier => [
					uri = "file:///tmp/foo"
				]
				contentChanges = new ArrayList => [
					add(new TextDocumentContentChangeEvent => [
						range = new Range => [
							start = new Position(7, 12)
							end = new Position(8, 16)
						]
						rangeLength = 20
						text = "bar"
					])
				]
			]
		])
	}
	
	@Test
	def void testPublishDiagnostics() {
		'''
			{
				"jsonrpc": "2.0",
				"method": "textDocument/publishDiagnostics",
				"params": {
					"uri": "file:///tmp/foo",
					"diagnostics": [
						{
							"message": "Couldn\u0027t resolve reference to State \u0027bar\u0027.",
							"range": {
								"start": {
									"character": 22,
									"line": 4
								},
								"end": {
									"character": 25,
									"line": 4
								}
							},
							"severity": 1
						}
					]
				}
			}
		'''.assertParse(new NotificationMessage => [
			jsonrpc = "2.0"
			method = MessageMethods.SHOW_DIAGNOSTICS
			params = new PublishDiagnosticsParams => [
				uri = "file:///tmp/foo"
				diagnostics = new ArrayList => [
					add(new Diagnostic => [
						range = new Range => [
							start = new Position(4, 22)
							end = new Position(4, 25)
						]
						severity = DiagnosticSeverity.Error
						message = "Couldn't resolve reference to State 'bar'."
					])
				]
			]
		])
	}
	
	@Test
	def void testRenameResponse() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_RENAME
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": {
					"changes": {
						"file:///tmp/foo": [
							{
								"range": {
									"start": {
										"character": 32,
										"line": 3
									},
									"end": {
										"character": 35,
										"line": 3
									}
								},
								"newText": "foobar"
							},
							{
								"range": {
									"start": {
										"character": 22,
										"line": 4
									},
									"end": {
										"character": 25,
										"line": 4
									}
								},
								"newText": "foobar"
							}
						]
					}
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = new WorkspaceEdit => [
				changes = new HashMap => [
					put("file:///tmp/foo", newArrayList(
						new TextEdit => [
							range = new Range => [
								start = new Position(3, 32)
								end = new Position(3, 35)
							]
							newText = "foobar"
						],
						new TextEdit => [
							range = new Range => [
								start = new Position(4, 22)
								end = new Position(4, 25)
							]
							newText = "foobar"
						]
					))
				]
			]
		])
	}
	
	@Test
	def void testResponseError() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12':
					'textDocument/rename'
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"error": {
					code = -32600,
					message = "Could not parse request."
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			error = new ResponseError => [
				code = ResponseErrorCode.InvalidRequest
				message = "Could not parse request."
			]
		])
	}
	
	@Test
	def void testTelemetry() {
		'''
			{
				"jsonrpc": "2.0",
				"method": "telemetry/event",
				"params": {
					"foo": 12.3,
					"bar": "qwertz"
				}
			}
		'''.assertParse(new NotificationMessage => [
			jsonrpc = "2.0"
			method = MessageMethods.TELEMETRY_EVENT
			params = newLinkedHashMap('foo' -> 12.3, 'bar' -> 'qwertz')
		])
	}
	
    @Test
    def void testHoverResponse() {
        jsonHandler.methodProvider = [ id |
            switch id {
                case '12': MessageMethods.DOC_HOVER
            }
        ]
        '''
            {
                "jsonrpc": "2.0",
                "id": "12",
                "result": {
                    "range": {
                        "start": {
                            "character": 32,
                            "line": 3
                        },
                        "end": {
                            "character": 35,
                            "line": 3
                        }
                    },
                    "contents": [
                        'foo',
                        'boo shuby doo'
                    ]
                }
            }
        '''.assertParse(new ResponseMessage => [
            jsonrpc = "2.0"
            id = "12"
            result = new Hover => [
                range = new Range => [
                    start = new Position(3, 32)
                    end = new Position(3, 35)
                ]
                contents = newArrayList(
                    Either.forLeft("foo"),
                    Either.forLeft("boo shuby doo")
                )
            ]
        ])
    }
    
	@Test
	def void testDocumentFormatting() {
		'''
			{
			  "jsonrpc": "2.0",
			  "id": "12",
			  "method": "textDocument/formatting",
			  "params": {
			    "textDocument": {
			      "uri": "file:///tmp/foo"
			    },
			    "options": {
			      "tabSize": 4,
			      "insertSpaces": false
			    }
			  }
			}
		'''.assertParse(new RequestMessage => [
			jsonrpc = "2.0"
			id = "12"
			method = MessageMethods.DOC_FORMATTING
			params = new DocumentFormattingParams => [
				textDocument = new TextDocumentIdentifier("file:///tmp/foo")
				options = new FormattingOptions => [
					tabSize = 4
					insertSpaces = false
				]
			]
		])
	}
    
}
