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
import org.eclipse.lsp4j.CompletionItem
import org.eclipse.lsp4j.CompletionList
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
import org.eclipse.lsp4j.InitializeResult
import org.eclipse.lsp4j.ServerCapabilities
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler
import org.eclipse.lsp4j.jsonrpc.messages.Message
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints
import org.eclipse.lsp4j.services.LanguageServer
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

import static extension org.eclipse.lsp4j.test.services.LineEndings.*
import org.eclipse.lsp4j.jsonrpc.messages.Either

class JsonSerializeTest {
	
	MessageJsonHandler jsonHandler
	
	@Before
	def void setup() {
		val methods = ServiceEndpoints.getSupportedMethods(LanguageServer)
		jsonHandler = new MessageJsonHandler(methods) {
			override getDefaultGsonBuilder() {
				super.defaultGsonBuilder.setPrettyPrinting
			}
		}
	}
	
	private def assertSerialize(Message message, CharSequence expected) {
		assertEquals(expected.toString.trim, jsonHandler.serialize(message).toSystemLineEndings)
	}
	
	@Test
	def void testCompletion() {
		val message = new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.DOC_COMPLETION
			params = new TextDocumentPositionParams => [
				textDocument = new TextDocumentIdentifier("file:///tmp/foo")
				position = new Position(4, 22)
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "1",
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
		''')
	}

	@Test
    def void testInit() {
        val message = new RequestMessage => [
            jsonrpc = "2.0"
            id = "1"
            method = MessageMethods.DOC_COMPLETION
            params = new InitializeResult => [
                capabilities = new ServerCapabilities()
            ]
        ]
        message.assertSerialize('''
            {
              "jsonrpc": "2.0",
              "id": "1",
              "method": "textDocument/completion",
              "params": {
                "capabilities": {}
              }
            }
        ''')
    }
	
	@Test
	def void testDidChange() {
		val message = new NotificationMessage => [
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
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "method": "textDocument/didChange",
			  "params": {
			    "textDocument": {
			      "version": 0,
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
		''')
	}
	
	@Test
	def void testPublishDiagnostics() {
		val message = new NotificationMessage => [
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
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "method": "textDocument/publishDiagnostics",
			  "params": {
			    "uri": "file:///tmp/foo",
			    "diagnostics": [
			      {
			        "range": {
			          "start": {
			            "line": 4,
			            "character": 22
			          },
			          "end": {
			            "line": 4,
			            "character": 25
			          }
			        },
			        "severity": 1,
			        "message": "Couldn\u0027t resolve reference to State \u0027bar\u0027."
			      }
			    ]
			  }
			}
		''')
	}
	
    @Test
    def void testRename() {
        val message = new ResponseMessage => [
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
        ]
        message.assertSerialize('''
            {
              "jsonrpc": "2.0",
              "id": "12",
              "result": {
                "changes": {
                  "file:///tmp/foo": [
                    {
                      "range": {
                        "start": {
                          "line": 3,
                          "character": 32
                        },
                        "end": {
                          "line": 3,
                          "character": 35
                        }
                      },
                      "newText": "foobar"
                    },
                    {
                      "range": {
                        "start": {
                          "line": 4,
                          "character": 22
                        },
                        "end": {
                          "line": 4,
                          "character": 25
                        }
                      },
                      "newText": "foobar"
                    }
                  ]
                }
              }
            }
        ''')
    }
    
    @Test
    def void testHoverResponse() {
        val message = new ResponseMessage => [
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
        ]
        message.assertSerialize('''
            {
              "jsonrpc": "2.0",
              "id": "12",
              "result": {
                "contents": [
                  "foo",
                  "boo shuby doo"
                ],
                "range": {
                  "start": {
                    "line": 3,
                    "character": 32
                  },
                  "end": {
                    "line": 3,
                    "character": 35
                  }
                }
              }
            }
        ''')
    }
    
    @Test
    def void testResponseError() {
        val message = new ResponseMessage => [
            jsonrpc = "2.0"
            id = "12"
            error = new ResponseError => [
                code = ResponseErrorCode.InvalidRequest
                message = "Could not parse request."
            ]
        ]
        message.assertSerialize('''
            {
              "jsonrpc": "2.0",
              "id": "12",
              "error": {
                "code": -32600,
                "message": "Could not parse request."
              }
            }
        ''')
    }
        
	@Test
	def void testBuildCompletionList() {
		val message = new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = new CompletionList => [
				isIncomplete = true
				items = #[
					new CompletionItem => [
						label = 'foo'	
					]
				]
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "12",
			  "result": {
			    "isIncomplete": true,
			    "items": [
			      {
			        "label": "foo"
			      }
			    ]
			  }
			}
		''')
	}
	
	@Test
	def void testBuildDocumentFormattingParams() {
		val message = new RequestMessage => [
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
		]
		message.assertSerialize('''
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
		''')
	}
	
	@Test
	def void testTelemetry() {
		val message = new NotificationMessage => [
			jsonrpc = "2.0"
			method = MessageMethods.TELEMETRY_EVENT
			params = new TestObject
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "method": "telemetry/event",
			  "params": {
			    "foo": 12.3,
			    "bar": "qwertz"
			  }
			}
		''')
	}
	
	private static class TestObject {
		package double foo = 12.3
		package String bar = "qwertz"
	}
	
}
