/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.test

import org.eclipse.lsp4j.DiagnosticSeverity
import org.eclipse.lsp4j.Message
import org.eclipse.lsp4j.ResponseErrorCode
import org.eclipse.lsp4j.builders.CompletionListBuilder
import org.eclipse.lsp4j.builders.DocumentFormattingParamsBuilder
import org.eclipse.lsp4j.builders.RequestMessageBuilder
import org.eclipse.lsp4j.builders.ResponseMessageBuilder
import org.eclipse.lsp4j.impl.CodeLensImpl
import org.eclipse.lsp4j.impl.DiagnosticImpl
import org.eclipse.lsp4j.impl.DidChangeTextDocumentParamsImpl
import org.eclipse.lsp4j.impl.HoverImpl
import org.eclipse.lsp4j.impl.MarkedStringImpl
import org.eclipse.lsp4j.impl.NotificationMessageImpl
import org.eclipse.lsp4j.impl.PositionImpl
import org.eclipse.lsp4j.impl.PublishDiagnosticsParamsImpl
import org.eclipse.lsp4j.impl.RangeImpl
import org.eclipse.lsp4j.impl.RequestMessageImpl
import org.eclipse.lsp4j.impl.ResponseErrorImpl
import org.eclipse.lsp4j.impl.ResponseMessageImpl
import org.eclipse.lsp4j.impl.TextDocumentContentChangeEventImpl
import org.eclipse.lsp4j.impl.TextDocumentIdentifierImpl
import org.eclipse.lsp4j.impl.TextDocumentPositionParamsImpl
import org.eclipse.lsp4j.impl.TextEditImpl
import org.eclipse.lsp4j.impl.VersionedTextDocumentIdentifierImpl
import org.eclipse.lsp4j.impl.WorkspaceEditImpl
import org.eclipse.lsp4j.services.json.MessageJsonHandler
import org.eclipse.lsp4j.services.transport.InvalidMessageException
import org.eclipse.lsp4j.services.transport.MessageMethods
import java.util.ArrayList
import java.util.HashMap
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

import static extension org.eclipse.lsp4j.services.test.LineEndings.*
import org.eclipse.lsp4j.MarkedString

class JsonSerializeTest {
	
	MessageJsonHandler jsonHandler
	
	@Before
	def void setup() {
		val gsonBuilder = MessageJsonHandler.defaultGsonBuilder.setPrettyPrinting
		jsonHandler = new MessageJsonHandler(gsonBuilder.create())
	}
	
	private def assertSerialize(Message message, CharSequence expected) {
		assertEquals(expected.toString.trim, jsonHandler.serialize(message).toSystemLineEndings)
	}
	
	private def void assertIssues(Message message, CharSequence expectedIssues) {
		try {
			jsonHandler.serialize(message)
			fail('''Expected exception: «InvalidMessageException.name»''')
		} catch (InvalidMessageException e) {
			val expected = expectedIssues.toString
			val actual = e.message.toSystemLineEndings
			// The expectation may be a prefix of the actual exception message
			if (!actual.startsWith(expected))
				assertEquals(expected, actual)
		}
	}
	
	@Test
	def void testCompletion() {
		val message = new RequestMessageImpl => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.DOC_COMPLETION
			params = new TextDocumentPositionParamsImpl => [
				textDocument = new TextDocumentIdentifierImpl("file:///tmp/foo")
				position = new PositionImpl(4, 22)
			]
		]
		message.assertSerialize('''
			{
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
			  },
			  "jsonrpc": "2.0"
			}
		''')
	}
	
	@Test
	def void testDidChange() {
		val message = new NotificationMessageImpl => [
			jsonrpc = "2.0"
			method = MessageMethods.DID_CHANGE_DOC
			params = new DidChangeTextDocumentParamsImpl => [
				textDocument = new VersionedTextDocumentIdentifierImpl => [
					uri = "file:///tmp/foo"
				]
				contentChanges = new ArrayList => [
					add(new TextDocumentContentChangeEventImpl => [
						range = new RangeImpl => [
							start = new PositionImpl(7, 12)
							end = new PositionImpl(8, 16)
						]
						rangeLength = 20
						text = "bar"
					])
				]
			]
		]
		message.assertSerialize('''
			{
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
			  },
			  "jsonrpc": "2.0"
			}
		''')
	}
	
	@Test
	def void testPublishDiagnostics() {
		val message = new NotificationMessageImpl => [
			jsonrpc = "2.0"
			method = MessageMethods.SHOW_DIAGNOSTICS
			params = new PublishDiagnosticsParamsImpl => [
				uri = "file:///tmp/foo"
				diagnostics = new ArrayList => [
					add(new DiagnosticImpl => [
						range = new RangeImpl => [
							start = new PositionImpl(4, 22)
							end = new PositionImpl(4, 25)
						]
						severity = DiagnosticSeverity.Error
						message = "Couldn't resolve reference to State 'bar'."
					])
				]
			]
		]
		message.assertSerialize('''
			{
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
			  },
			  "jsonrpc": "2.0"
			}
		''')
	}
	
    @Test
    def void testRename() {
        val message = new ResponseMessageImpl => [
            jsonrpc = "2.0"
            id = "12"
            result = new WorkspaceEditImpl => [
                changes = new HashMap => [
                    put("file:///tmp/foo", newArrayList(
                        new TextEditImpl => [
                            range = new RangeImpl => [
                                start = new PositionImpl(3, 32)
                                end = new PositionImpl(3, 35)
                            ]
                            newText = "foobar"
                        ],
                        new TextEditImpl => [
                            range = new RangeImpl => [
                                start = new PositionImpl(4, 22)
                                end = new PositionImpl(4, 25)
                            ]
                            newText = "foobar"
                        ]
                    ))
                ]
            ]
        ]
        message.assertSerialize('''
            {
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
              },
              "jsonrpc": "2.0"
            }
        ''')
    }
    
    @Test
    def void testHoverResponse() {
        val message = new ResponseMessageImpl => [
            jsonrpc = "2.0"
            id = "12"
            result = new HoverImpl => [
                range = new RangeImpl => [
                    start = new PositionImpl(3, 32)
                    end = new PositionImpl(3, 35)
                ]
                contents = newArrayList(
                    new MarkedStringImpl(MarkedString.PLAIN_STRING, "foo"),
                    new MarkedStringImpl("foolang", "boo shuby doo")
                )
            ]
        ]
        message.assertSerialize('''
            {
              "id": "12",
              "result": {
                "contents": [
                  "foo",
                  {
                    "language": "foolang",
                    "value": "boo shuby doo"
                  }
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
              },
              "jsonrpc": "2.0"
            }
        ''')
    }
    
    @Test
    def void testResponseError() {
        val message = new ResponseMessageImpl => [
            jsonrpc = "2.0"
            id = "12"
            error = new ResponseErrorImpl => [
                code = ResponseErrorCode.InvalidRequest
                message = "Could not parse request."
            ]
        ]
        message.assertSerialize('''
            {
              "id": "12",
              "error": {
                "code": -32600,
                "message": "Could not parse request."
              },
              "jsonrpc": "2.0"
            }
        ''')
    }
        
	@Test
	def void testBuildCompletionList() {
		val message = new ResponseMessageBuilder [
			jsonrpc("2.0")
			id("12")
			result(new CompletionListBuilder[
				isIncomplete(true)
				item[
					label('foo')
				]
			].build)
		].build
		message.assertSerialize('''
			{
			  "id": "12",
			  "result": {
			    "isIncomplete": true,
			    "items": [
			      {
			        "label": "foo"
			      }
			    ]
			  },
			  "jsonrpc": "2.0"
			}
		''')
	}
	
	@Test
	def void testBuildDocumentFormattingParams() {
		val message = new RequestMessageBuilder [
			jsonrpc("2.0")
			id("12")
			method(MessageMethods.DOC_FORMATTING)
			params(new DocumentFormattingParamsBuilder[
				textDocument[
					uri("file:///tmp/foo")
				]
				options[
					tabSize(4)
					insertSpaces(false)
				]
			].build)
		].build
		message.assertSerialize('''
			{
			  "id": "12",
			  "method": "textDocument/formatting",
			  "params": {
			    "textDocument": {
			      "uri": "file:///tmp/foo"
			    },
			    "options": {
			      "tabSize": 4,
			      "insertSpaces": false,
			      "properties": {}
			    }
			  },
			  "jsonrpc": "2.0"
			}
		''')
	}
	
	@Test
	def void testTelemetry() {
		val message = new NotificationMessageImpl => [
			jsonrpc = "2.0"
			method = MessageMethods.TELEMETRY_EVENT
			params = new TestObject
		]
		message.assertSerialize('''
			{
			  "method": "telemetry/event",
			  "params": {
			    "foo": 12.3,
			    "bar": "qwertz"
			  },
			  "jsonrpc": "2.0"
			}
		''')
	}
	
	private static class TestObject {
		package double foo = 12.3
		package String bar = "qwertz"
	}
	
	@Test
	def void testInvalidCompletion() {
		val message = new RequestMessageImpl => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.DOC_COMPLETION
			params = new TextDocumentPositionParamsImpl => [
				textDocument = new TextDocumentIdentifierImpl("file:///tmp/foo")
			]
		]
		message.assertIssues('Error: The property \'position\' must have a non-null value.')
	}
	
	@Test
	def void testInvalidCodeLens() {
		val message = new ResponseMessageImpl => [
			jsonrpc = "2.0"
			id = "1"
			result = new CodeLensImpl => [
				range = new RangeImpl => [
					start = new PositionImpl(3, 32)
					end = new PositionImpl(3, 35)
				]
				data = it
			]
		]
		message.assertIssues('Error: An element of the message has a direct or indirect reference to itself.')
	}
	
}