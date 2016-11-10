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
import java.util.ArrayList
import java.util.HashMap
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

import static extension org.eclipse.lsp4j.services.test.LineEndings.*
import org.eclipse.lsp4j.services.transport.InvalidMessageException
import org.eclipse.lsp4j.services.transport.MessageMethods
import org.eclipse.lsp4j.MarkedString

class JsonParseTest {
	
	MessageJsonHandler jsonHandler
	
	@Before
	def void setup() {
		jsonHandler = new MessageJsonHandler
	}
	
	private def void assertParse(CharSequence json, Message expected) {
		assertEquals(expected.toString, jsonHandler.parseMessage(json).toString)
	}
	
	private def void assertIssues(CharSequence json, CharSequence expectedIssues) {
		try {
			jsonHandler.parseMessage(json)
			fail('''Expected exception: «InvalidMessageException.name»''')
		} catch (InvalidMessageException e) {
			assertEquals(expectedIssues.toString, e.message.toSystemLineEndings)
		}
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
		'''.assertParse(new RequestMessageImpl => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.DOC_COMPLETION
			params = new TextDocumentPositionParamsImpl => [
				textDocument = new TextDocumentIdentifierImpl("file:///tmp/foo")
				position = new PositionImpl(4, 22)
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
		'''.assertParse(new NotificationMessageImpl => [
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
		'''.assertParse(new NotificationMessageImpl => [
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
		])
	}
	
	@Test
	def void testRenameResponse() {
		jsonHandler.methodResolver = [ id |
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
		'''.assertParse(new ResponseMessageImpl => [
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
		])
	}
	
	@Test
	def void testResponseError() {
		jsonHandler.methodResolver = [ id |
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
		'''.assertParse(new ResponseMessageImpl => [
			jsonrpc = "2.0"
			id = "12"
			error = new ResponseErrorImpl => [
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
		'''.assertParse(new NotificationMessageImpl => [
			jsonrpc = "2.0"
			method = MessageMethods.TELEMETRY_EVENT
			params = newLinkedHashMap('foo' -> 12.3, 'bar' -> 'qwertz')
		])
	}
	
    @Test
    def void testHoverResponse() {
        jsonHandler.methodResolver = [ id |
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
                        {
                            "language": "foolang",
                            "value": "boo shuby doo"
                        }
                    ]
                }
            }
        '''.assertParse(new ResponseMessageImpl => [
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
        ])
    }
    
	@Test
	def void testInvalidCompletion() {
		'''
			{
				"jsonrpc": "2.0",
				"id": 1,
				"method": "textDocument/completion",
				"params": {
					"textDocument": {
						"uri": "file:///tmp/foo"
					}
				}
			}
		'''.assertIssues('''
			Error: The property 'position' must have a non-null value.
			The message was:
				{"jsonrpc":"2.0","id":1,"method":"textDocument/completion","params":{"textDocument":{"uri":"file:///tmp/foo"}}}
		''')
	}
	
}