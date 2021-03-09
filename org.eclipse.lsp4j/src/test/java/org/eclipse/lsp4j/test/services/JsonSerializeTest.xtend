/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.test.services

import com.google.gson.JsonObject
import java.util.ArrayList
import java.util.HashMap
import org.eclipse.lsp4j.ClientCapabilities
import org.eclipse.lsp4j.CodeActionCapabilities
import org.eclipse.lsp4j.CodeLens
import org.eclipse.lsp4j.CodeLensCapabilities
import org.eclipse.lsp4j.ColorProviderCapabilities
import org.eclipse.lsp4j.Command
import org.eclipse.lsp4j.CompletionCapabilities
import org.eclipse.lsp4j.CompletionItem
import org.eclipse.lsp4j.CompletionItemCapabilities
import org.eclipse.lsp4j.CompletionItemKind
import org.eclipse.lsp4j.CompletionItemKindCapabilities
import org.eclipse.lsp4j.CompletionList
import org.eclipse.lsp4j.DefinitionCapabilities
import org.eclipse.lsp4j.Diagnostic
import org.eclipse.lsp4j.DiagnosticSeverity
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.DocumentFormattingParams
import org.eclipse.lsp4j.DocumentHighlightCapabilities
import org.eclipse.lsp4j.DocumentLinkCapabilities
import org.eclipse.lsp4j.DocumentSymbolCapabilities
import org.eclipse.lsp4j.FormattingCapabilities
import org.eclipse.lsp4j.FormattingOptions
import org.eclipse.lsp4j.Hover
import org.eclipse.lsp4j.HoverCapabilities
import org.eclipse.lsp4j.ImplementationCapabilities
import org.eclipse.lsp4j.InitializeParams
import org.eclipse.lsp4j.InitializeResult
import org.eclipse.lsp4j.MarkupContent
import org.eclipse.lsp4j.MarkupKind
import org.eclipse.lsp4j.OnTypeFormattingCapabilities
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.ProgressParams
import org.eclipse.lsp4j.PublishDiagnosticsParams
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.RangeFormattingCapabilities
import org.eclipse.lsp4j.ReferencesCapabilities
import org.eclipse.lsp4j.RenameCapabilities
import org.eclipse.lsp4j.ServerCapabilities
import org.eclipse.lsp4j.SignatureHelpCapabilities
import org.eclipse.lsp4j.SignatureInformationCapabilities
import org.eclipse.lsp4j.SymbolKind
import org.eclipse.lsp4j.SymbolKindCapabilities
import org.eclipse.lsp4j.SynchronizationCapabilities
import org.eclipse.lsp4j.TextDocumentClientCapabilities
import org.eclipse.lsp4j.TextDocumentContentChangeEvent
import org.eclipse.lsp4j.TextDocumentIdentifier
import org.eclipse.lsp4j.TextDocumentPositionParams
import org.eclipse.lsp4j.TextEdit
import org.eclipse.lsp4j.TypeDefinitionCapabilities
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier
import org.eclipse.lsp4j.WorkDoneProgressCancelParams
import org.eclipse.lsp4j.WorkDoneProgressCreateParams
import org.eclipse.lsp4j.WorkDoneProgressEnd
import org.eclipse.lsp4j.WorkDoneProgressNotification
import org.eclipse.lsp4j.WorkspaceClientCapabilities
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
import org.eclipse.lsp4j.services.LanguageServer
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

import static extension org.eclipse.lsp4j.test.services.LineEndings.*

class JsonSerializeTest {
	
	MessageJsonHandler jsonHandler
	
	@Before
	def void setup() {
		val methods = ServiceEndpoints.getSupportedMethods(LanguageServer)
		jsonHandler = new MessageJsonHandler(methods) [
			setPrettyPrinting()
		]
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
			method = MessageMethods.INITIALIZE
			params = new InitializeParams => [
				rootUri = "file:///tmp/foo"
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "1",
			  "method": "initialize",
			  "params": {
			    "processId": null,
			    "rootUri": "file:///tmp/foo"
			  }
			}
		''')
	}
	
	@Test
	def void testInitClientCapabilities() {
		val message = new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.INITIALIZE
			params = new InitializeParams => [
				rootUri = "file:///tmp/foo"
				capabilities = new ClientCapabilities => [
					textDocument = new TextDocumentClientCapabilities => [
						synchronization = new SynchronizationCapabilities => [
							dynamicRegistration = false
							willSave= true
							willSaveWaitUntil= false
							didSave = true
						]
						completion = new CompletionCapabilities => [
							dynamicRegistration = false
							completionItem = new CompletionItemCapabilities => [
								snippetSupport = true
								commitCharactersSupport = true
								documentationFormat = #[MarkupKind.PLAINTEXT, MarkupKind.MARKDOWN]
							]
							completionItemKind = new CompletionItemKindCapabilities => [
								valueSet = #[CompletionItemKind.Method, CompletionItemKind.Function]
							]
							contextSupport = false
						]
						hover = new HoverCapabilities => [
							dynamicRegistration = false
							contentFormat = #[MarkupKind.PLAINTEXT, MarkupKind.MARKDOWN]
						]
						signatureHelp = new SignatureHelpCapabilities => [
							dynamicRegistration = false
							signatureInformation = new SignatureInformationCapabilities => [
								documentationFormat = #[MarkupKind.PLAINTEXT, MarkupKind.MARKDOWN]
							]
						]
						references = new ReferencesCapabilities => [
							dynamicRegistration = false
						]
						documentHighlight = new DocumentHighlightCapabilities => [
							dynamicRegistration = false
						]
						documentSymbol = new DocumentSymbolCapabilities => [
							dynamicRegistration = false
							symbolKind = new SymbolKindCapabilities => [
								valueSet = #[SymbolKind.Module, SymbolKind.Namespace, SymbolKind.Package, SymbolKind.Class]
							]
						]
						formatting = new FormattingCapabilities => [
							dynamicRegistration = false
						]
						rangeFormatting = new RangeFormattingCapabilities => [
							dynamicRegistration = false
						]
						onTypeFormatting = new OnTypeFormattingCapabilities => [
							dynamicRegistration = false
						]
						definition= new DefinitionCapabilities => [
							dynamicRegistration = false
						]
						typeDefinition= new TypeDefinitionCapabilities => [
							dynamicRegistration = false
						]
						implementation= new ImplementationCapabilities => [
							dynamicRegistration = false
						]
						codeAction = new CodeActionCapabilities => [
							dynamicRegistration = false
						]
						codeLens= new CodeLensCapabilities => [
							dynamicRegistration = false
						]
						documentLink= new DocumentLinkCapabilities => [
							dynamicRegistration = false
						]
						colorProvider = new ColorProviderCapabilities => [
							dynamicRegistration = false
						]
						rename = new RenameCapabilities => [
							dynamicRegistration = false
						]
					]
					workspace = new WorkspaceClientCapabilities
				]
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "1",
			  "method": "initialize",
			  "params": {
			    "processId": null,
			    "rootUri": "file:///tmp/foo",
			    "capabilities": {
			      "workspace": {},
			      "textDocument": {
			        "synchronization": {
			          "willSave": true,
			          "willSaveWaitUntil": false,
			          "didSave": true,
			          "dynamicRegistration": false
			        },
			        "completion": {
			          "completionItem": {
			            "snippetSupport": true,
			            "commitCharactersSupport": true,
			            "documentationFormat": [
			              "plaintext",
			              "markdown"
			            ]
			          },
			          "completionItemKind": {
			            "valueSet": [
			              2,
			              3
			            ]
			          },
			          "contextSupport": false,
			          "dynamicRegistration": false
			        },
			        "hover": {
			          "contentFormat": [
			            "plaintext",
			            "markdown"
			          ],
			          "dynamicRegistration": false
			        },
			        "signatureHelp": {
			          "signatureInformation": {
			            "documentationFormat": [
			              "plaintext",
			              "markdown"
			            ]
			          },
			          "dynamicRegistration": false
			        },
			        "references": {
			          "dynamicRegistration": false
			        },
			        "documentHighlight": {
			          "dynamicRegistration": false
			        },
			        "documentSymbol": {
			          "symbolKind": {
			            "valueSet": [
			              2,
			              3,
			              4,
			              5
			            ]
			          },
			          "dynamicRegistration": false
			        },
			        "formatting": {
			          "dynamicRegistration": false
			        },
			        "rangeFormatting": {
			          "dynamicRegistration": false
			        },
			        "onTypeFormatting": {
			          "dynamicRegistration": false
			        },
			        "definition": {
			          "dynamicRegistration": false
			        },
			        "typeDefinition": {
			          "dynamicRegistration": false
			        },
			        "implementation": {
			          "dynamicRegistration": false
			        },
			        "codeAction": {
			          "dynamicRegistration": false
			        },
			        "codeLens": {
			          "dynamicRegistration": false
			        },
			        "documentLink": {
			          "dynamicRegistration": false
			        },
			        "colorProvider": {
			          "dynamicRegistration": false
			        },
			        "rename": {
			          "dynamicRegistration": false
			        }
			      }
			    }
			  }
			}
		''')
	}
	
	@Test
	def void testInitResponse() {
		val message = new ResponseMessage => [
			jsonrpc = "2.0"
			id = 12
			result = new InitializeResult => [
				capabilities = new ServerCapabilities
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": 12,
			  "result": {
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
			      "version": null,
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
				version = 1
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
			    ],
			    "version": 1
			  }
			}
		''')
	}
	
    @Test
	def void testRenameResponse() {
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
	def void testHoverResponse1() {
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
	def void testHoverResponse2() {
		val message = new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = new Hover => [
				contents = new MarkupContent => [
					kind = "plaintext"
					value = "foo"
				]
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "12",
			  "result": {
			    "contents": {
			      "kind": "plaintext",
			      "value": "foo"
			    }
			  }
			}
		''')
	}
    
	@Test
	def void testCodeLensResponse() {
		val message = new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = new CodeLens => [
				range = new Range => [
					start = new Position(3, 32)
					end = new Position(3, 35)
				]
				command = new Command => [
					title = 'save'
					command = 'saveCommand'
					arguments = <Object>newArrayList(
						new JsonObject => [
							addProperty('uri', 'file:/foo')
							addProperty('version', 5)
						]
					)
				]
				data = <Object>newArrayList(
					42,
					'qwert',
					new JsonObject => [
						addProperty('key', 'value')
					]
				)
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "12",
			  "result": {
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
			    "command": {
			      "title": "save",
			      "command": "saveCommand",
			      "arguments": [
			        {
			          "uri": "file:/foo",
			          "version": 5
			        }
			      ]
			    },
			    "data": [
			      42,
			      "qwert",
			      {
			        "key": "value"
			      }
			    ]
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
	def void testCompletionResponse() {
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
	def void testDocumentFormatting() {
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
				options.putNumber('customProperty', -7)
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
			      "insertSpaces": false,
			      "customProperty": -7
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
        
	@Test
	def void testNullResponse() {
		val message = new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "12",
			  "result": null
			}
		''')
	}
	
	@Test
	def void testProgressCreate() {
		val message = new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.PROGRESS_CREATE
			params = new WorkDoneProgressCreateParams => [
				token = Either.forLeft("progress-token")
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "1",
			  "method": "window/workDoneProgress/create",
			  "params": {
			    "token": "progress-token"
			  }
			}
		''')
		
		val message2 = new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.PROGRESS_CREATE
			params = new WorkDoneProgressCreateParams => [
				token = Either.forRight(1234)
			]
		]
		message2.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "1",
			  "method": "window/workDoneProgress/create",
			  "params": {
			    "token": 1234
			  }
			}
		''')
	}
	
	@Test
	def void testProgressCancel() {
		val message = new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.PROGRESS_CANCEL
			params = new WorkDoneProgressCancelParams => [
				token = Either.forLeft("progress-token")
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "1",
			  "method": "window/workDoneProgress/cancel",
			  "params": {
			    "token": "progress-token"
			  }
			}
		''')
		
		val message2 = new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.PROGRESS_CANCEL
			params = new WorkDoneProgressCancelParams => [
				token = Either.forRight(1234)
			]
		]
		message2.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "1",
			  "method": "window/workDoneProgress/cancel",
			  "params": {
			    "token": 1234
			  }
			}
		''')
	}
	
	@Test
	def void testProgressNotify() {
		val message = new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.PROGRESS_NOTIFY
			params = new ProgressParams => [
				token = Either.forLeft("progress-token")
				value = Either.<WorkDoneProgressNotification, Object>forLeft(new WorkDoneProgressEnd => [
					message = "message"
				])
			]
		]
		message.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "1",
			  "method": "$/progress",
			  "params": {
			    "token": "progress-token",
			    "value": {
			      "kind": "end",
			      "message": "message"
			    }
			  }
			}
		''')
		
		val message2 = new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.PROGRESS_NOTIFY
			params = new ProgressParams => [
				token = Either.forRight(1234)
				value = Either.<WorkDoneProgressNotification, Object>forRight(new JsonObject => [
					addProperty("foo", "bar")
				])
			]
		]
		message2.assertSerialize('''
			{
			  "jsonrpc": "2.0",
			  "id": "1",
			  "method": "$/progress",
			  "params": {
			    "token": 1234,
			    "value": {
			      "foo": "bar"
			    }
			  }
			}
		''')
	}
	
}
