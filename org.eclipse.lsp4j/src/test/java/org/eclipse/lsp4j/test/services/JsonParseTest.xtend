/******************************************************************************
 * Copyright (c) 2016-2019 TypeFox and others.
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

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.internal.LazilyParsedNumber
import java.util.ArrayList
import java.util.Collection
import java.util.HashMap
import java.util.List
import org.eclipse.lsp4j.ClientCapabilities
import org.eclipse.lsp4j.CodeAction
import org.eclipse.lsp4j.CodeActionCapabilities
import org.eclipse.lsp4j.CodeLens
import org.eclipse.lsp4j.CodeLensCapabilities
import org.eclipse.lsp4j.ColorProviderCapabilities
import org.eclipse.lsp4j.Command
import org.eclipse.lsp4j.CompletionCapabilities
import org.eclipse.lsp4j.CompletionItemCapabilities
import org.eclipse.lsp4j.CompletionItemKind
import org.eclipse.lsp4j.CompletionItemKindCapabilities
import org.eclipse.lsp4j.CompletionParams
import org.eclipse.lsp4j.CreateFile
import org.eclipse.lsp4j.CreateFileOptions
import org.eclipse.lsp4j.DefinitionCapabilities
import org.eclipse.lsp4j.DeleteFile
import org.eclipse.lsp4j.Diagnostic
import org.eclipse.lsp4j.DiagnosticSeverity
import org.eclipse.lsp4j.DidChangeTextDocumentParams
import org.eclipse.lsp4j.DocumentFormattingParams
import org.eclipse.lsp4j.DocumentHighlightCapabilities
import org.eclipse.lsp4j.DocumentLinkCapabilities
import org.eclipse.lsp4j.DocumentSymbol
import org.eclipse.lsp4j.DocumentSymbolCapabilities
import org.eclipse.lsp4j.FormattingCapabilities
import org.eclipse.lsp4j.FormattingOptions
import org.eclipse.lsp4j.Hover
import org.eclipse.lsp4j.HoverCapabilities
import org.eclipse.lsp4j.ImplementationCapabilities
import org.eclipse.lsp4j.InitializeParams
import org.eclipse.lsp4j.Location
import org.eclipse.lsp4j.LocationLink
import org.eclipse.lsp4j.MarkedString
import org.eclipse.lsp4j.MarkupContent
import org.eclipse.lsp4j.MarkupKind
import org.eclipse.lsp4j.OnTypeFormattingCapabilities
import org.eclipse.lsp4j.ParameterInformation
import org.eclipse.lsp4j.Position
import org.eclipse.lsp4j.PrepareRenameResult
import org.eclipse.lsp4j.ProgressParams
import org.eclipse.lsp4j.PublishDiagnosticsParams
import org.eclipse.lsp4j.Range
import org.eclipse.lsp4j.RangeFormattingCapabilities
import org.eclipse.lsp4j.ReferencesCapabilities
import org.eclipse.lsp4j.RenameCapabilities
import org.eclipse.lsp4j.RenameFile
import org.eclipse.lsp4j.ResourceOperation
import org.eclipse.lsp4j.SignatureHelp
import org.eclipse.lsp4j.SignatureHelpCapabilities
import org.eclipse.lsp4j.SignatureInformation
import org.eclipse.lsp4j.SignatureInformationCapabilities
import org.eclipse.lsp4j.SymbolInformation
import org.eclipse.lsp4j.SymbolKind
import org.eclipse.lsp4j.SymbolKindCapabilities
import org.eclipse.lsp4j.SynchronizationCapabilities
import org.eclipse.lsp4j.TextDocumentClientCapabilities
import org.eclipse.lsp4j.TextDocumentContentChangeEvent
import org.eclipse.lsp4j.TextDocumentEdit
import org.eclipse.lsp4j.TextDocumentIdentifier
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
import org.eclipse.lsp4j.jsonrpc.messages.MessageIssue
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage
import org.eclipse.lsp4j.jsonrpc.messages.Tuple
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints
import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

class JsonParseTest {

	/**
	 * Gson parses numbers with {@link LazilyParsedNumber}, which is not
	 * equals-compatible with {@link Integer}.
	 */
	@FinalFieldsConstructor
	private static class MyInteger extends Number {

		val int value

		override doubleValue() { value }
		override floatValue() { value }
		override intValue() { value }
		override longValue() { value }

		override equals(Object obj) {
			if (obj instanceof Number) {
				return value as double == obj.doubleValue
			}
			return false
		}

		override hashCode() {
			Integer.hashCode(value)
		}

		override toString() {
			Integer.toString(value)
		}

	}

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
		val actual = jsonHandler.parseMessage(json)
		assertEquals(expected.toString, actual.toString)
		assertEquals(expected, actual)
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
			id = 1
			method = MessageMethods.DOC_COMPLETION
			params = new CompletionParams => [
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
						"uri": "file:///tmp/foo",
						"version": 1234
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
					version = 1234
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
					],
					version: 1
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
				version = 1
			]
		])
	}

	@Test
	def void testDocumentSymbolResponse1() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_SYMBOL
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": [
					{
						"name" : "foobar",
						"kind" : 9,
						"location" : {
							"uri": "file:/baz.txt",
							"range" : {
								"start": {
									"character": 22,
									"line": 4
								},
								"end": {
									"character": 25,
									"line": 4
								}
							}
						}
					}
				]
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = newArrayList(Either.forLeft(
				new SymbolInformation => [
					name = "foobar"
					kind = SymbolKind.Constructor
					location = new Location => [
						uri = "file:/baz.txt"
						range = new Range => [
							start = new Position(4, 22)
							end = new Position(4, 25)
						]
					]
				]
			))
		])
	}

	@Test
	def void testDocumentSymbolResponse2() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_SYMBOL
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": [
					{
						"name" : "foobar",
						"kind" : 9,
						"range" : {
							"start": {
								"character": 22,
								"line": 4
							},
							"end": {
								"character": 25,
								"line": 4
							}
						},
						"selectionRange": {
							"start": {
								"character": 22,
								"line": 4
							},
							"end": {
								"character": 25,
								"line": 4
							}
						}
					}
				]
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = newArrayList(Either.forRight(
				new DocumentSymbol => [
					name = "foobar"
					kind = SymbolKind.Constructor
					range = new Range => [
						start = new Position(4, 22)
						end = new Position(4, 25)
					]
					selectionRange = new Range => [
						start = new Position(4, 22)
						end = new Position(4, 25)
					]
				]
			))
		])
	}

	@Test
	def void testCodeActionResponse1() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_CODE_ACTION
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": [
					{
						"title": "fixme",
						"command": "fix"
					}
				]
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = newArrayList(Either.forLeft(
				new Command => [
					title = "fixme"
					command = "fix"
				]
			))
		])
	}

	@Test
	def void testCodeActionResponse2() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_CODE_ACTION
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": [
					{
						"title": "fixme",
						"kind": "fix",
						"diagnostics": [],
						"edit": {
							"changes": {
								"file:test1533196529126.lspt": [
									{
										"range": {
											"start": {
												"line": 0,
												"character": 0
											},
											"end": {
												"line": 0,
												"character": 5
											}
										},
										"newText": "fixed"
									}
								]
							}
						}
					}
				]
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = newArrayList(Either.forRight(
				new CodeAction => [
					title = "fixme"
					kind = "fix"
					diagnostics = newArrayList
					edit = new WorkspaceEdit => [
						changes.put("file:test1533196529126.lspt", newArrayList(
							new TextEdit => [
								range = new Range => [
									start = new Position(0, 0)
									end = new Position(0, 5)
								]
								newText = "fixed"
							]
						))
					]
				]
			))
		])
	}

	@Test
	def void testCodeActionResponse3() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_CODE_ACTION
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": [
					{
						"title": "fixme"
					}
				]
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = newArrayList(Either.forRight(
				new CodeAction => [
					title = "fixme"
				]
			))
		])
	}

	@Test
	def void testPrepareRenameResponse1() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_PREPARE_RENAME
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
					"placeholder": "someVar"
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = Either.forRight(new PrepareRenameResult => [
				range = new Range => [
					start = new Position(3, 32)
					end = new Position(3, 35)
				]
				placeholder = "someVar"
			])
		])
	}

	@Test
	def void testPrepareRenameResponse2() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_PREPARE_RENAME
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": {
					"start": {
						"character": 32,
						"line": 3
					},
					"end": {
						"character": 35,
						"line": 3
					}
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result =  Either.forLeft(new Range => [
				start = new Position(3, 32)
				end = new Position(3, 35)
			])
		])
	}

	@Test
	def void testRenameResponse1() {
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
	def void testRenameResponse3() {
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
					"documentChanges": [
						{
							"kind": "create",
							"uri": "file:/foo.txt",
							"options": {
								"overwrite": true,
								"ignoreIfExists": true
							}
						},
						{
							"kind": "delete",
							"uri": "file:/foo.txt"
						},
						{
							"kind": "rename",
							"oldUri": "file:/foo.txt",
							"newUri": "file:/bar.txt"
						},
						{
							"textDocument": {
								"uri": "file:/baz.txt",
								"version": 17
							},
							"edits": [
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
									"newText": "asdfqweryxcv"
								}
							]
						}
					]
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = new WorkspaceEdit => [
				documentChanges = newArrayList(
					Either.forRight((new CreateFile => [
						uri = "file:/foo.txt"
						options = new CreateFileOptions => [
							overwrite = true
							ignoreIfExists = true
						]
					]) as ResourceOperation),
					Either.forRight((new DeleteFile => [
						uri = "file:/foo.txt"
					]) as ResourceOperation),
					Either.forRight((new RenameFile => [
						oldUri = "file:/foo.txt"
						newUri = "file:/bar.txt"
					]) as ResourceOperation),
					Either.forLeft(new TextDocumentEdit => [
						textDocument = new VersionedTextDocumentIdentifier("file:/baz.txt", 17)
						edits = newArrayList(
							new TextEdit => [
								range = new Range => [
									start = new Position(3, 32)
									end = new Position(3, 35)
								]
								newText = "asdfqweryxcv"
							]
						)
					])
				)
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
					"code": -32600,
					"message": "Could not parse request."
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
	def void testHoverResponse1() {
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
						"foo",
						"boo shuby doo"
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
	def void testHoverResponse2() {
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
					"contents": {
						"kind": "plaintext",
						"value": "foo"
					}
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = new Hover => [
				contents = new MarkupContent => [
					kind = "plaintext"
					value = "foo"
				]
			]
		])
	}

	@Test
	def void testHoverResponse3() {
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
					"contents": "foo"
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = new Hover => [
				contents = newArrayList(Either.forLeft("foo"))
			]
		])
	}

	@Test
	def void testHoverResponse4() {
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
					"contents": {
						"language": "plaintext",
						"value": "foo"
					}
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = new Hover => [
				contents = newArrayList(Either.forRight(new MarkedString => [
					language = "plaintext"
					value = "foo"
				]))
			]
		])
	}

	@Test
	def void testCodeLensResponse() {
		val json = '''
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
		'''
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_CODE_LENS
			}
		]
		val message = jsonHandler.parseMessage(json)
		assertTrue("Expected a ResponseMessage", message instanceof ResponseMessage)
		val response = message as ResponseMessage
		assertTrue("Expected a Collection in result", response.result instanceof Collection<?>)
		val result = response.result as Collection<?>
		assertTrue("Expected a CodeLens in result[0]", result.head instanceof CodeLens)
		val codeLens = result.head as CodeLens
		assertNotNull(codeLens.command)
		val argument = codeLens.command.arguments.head
		assertTrue("Expected a JsonObject in command.arguments[0]", argument instanceof JsonObject)
		assertEquals("file:/foo", (argument as JsonObject).get("uri").asString)
		assertEquals(5, (argument as JsonObject).get("version").asInt)
		assertTrue("Expected a JsonArray in data", codeLens.data instanceof JsonArray)
		val data = codeLens.data as JsonArray
		assertEquals(42, data.get(0).asInt)
		assertEquals("qwert", data.get(1).asString)
		assertTrue("Expected a JsonObject in data[2]", data.get(2) instanceof JsonObject)
		assertEquals("value", (data.get(2) as JsonObject).get("key").asString)
	}

	@Test
	def void testDeclarationResponse() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_DECLARATION
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": [
					{
						"uri": "foo",
						"range": {
							"start": {
								"line": 7,
								"character": 12
							},
							"end": {
								"line": 8,
								"character": 16
							}
						}
					}
				]
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = Either.<List<? extends Location>, List<? extends LocationLink>>forLeft(#[
				new Location('foo', new Range(new Position(7, 12), new Position(8, 16)))
			])
		])

	}

	@Test
	def void testItemInsteadOfListResponse() {
		//test parse direct item without the list
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_DECLARATION
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": {
					"uri": "foo",
					"range": {
						"start": {
							"line": 7,
							"character": 12
						},
						"end": {
							"line": 8,
							"character": 16
						}
					}
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = Either.<List<? extends Location>, List<? extends LocationLink>>forLeft(#[
				new Location('foo', new Range(new Position(7, 12), new Position(8, 16)))
			])
		])
	}

	@Test
	def void testDefinitionResponse1() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_DEFINITION
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": [
					{
						"targetUri": "foo",
						"targetRange": {
							"start": {
								"line": 7,
								"character": 12
							},
							"end": {
								"line": 8,
								"character": 16
							}
						}
					}
				]
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = Either.<List<? extends Location>, List<? extends LocationLink>>forRight(#[
				new LocationLink => [
					targetUri = 'foo'
					targetRange = new Range(new Position(7, 12), new Position(8, 16))
				]
			])
		])
	}

	@Test
	def void testDefinitionResponse2() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_DEFINITION
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": []
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = Either.<List<? extends Location>, List<? extends LocationLink>>forLeft(emptyList)
		])
	}

	@Test
	def void testTypeDefinitionResponse() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_TYPE_DEFINITION
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": [
					{
						"uri": "foo",
						"range": {
							"start": {
								"line": 7,
								"character": 12
							},
							"end": {
								"line": 8,
								"character": 16
							}
						}
					}
				]
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = Either.<List<? extends Location>, List<? extends LocationLink>>forLeft(#[
				new Location('foo', new Range(new Position(7, 12), new Position(8, 16)))
			])
		])
	}

	@Test
	def void testImplementationResponse() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_IMPLEMENTATION
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": [
					{
						"targetUri": "foo",
						"targetRange": {
							"start": {
								"line": 7,
								"character": 12
							},
							"end": {
								"line": 8,
								"character": 16
							}
						}
					}
				]
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = Either.<List<? extends Location>, List<? extends LocationLink>>forRight(#[
				new LocationLink => [
					targetUri = 'foo'
					targetRange = new Range(new Position(7, 12), new Position(8, 16))
				]
			])
		])
	}

	@Test
	def void testSignatureHelpResponse() {
		jsonHandler.methodProvider = [ id |
			switch id {
				case '12': MessageMethods.DOC_SIGNATURE_HELP
			}
		]
		'''
			{
				"jsonrpc": "2.0",
				"id": "12",
				"result": {
					"signatures": [
						{
							"label": "Foo",
							"parameters": [
								{
									"label": "label1"
								},
								{
									"label": [12, 25]
								}
							]
						}
					]
				}
			}
		'''.assertParse(new ResponseMessage => [
			jsonrpc = "2.0"
			id = "12"
			result = new SignatureHelp => [
				signatures = #[
					new SignatureInformation => [
						label = "Foo"
						parameters = #[
							new ParameterInformation => [
								label = Either.forLeft("label1")
							],
							new ParameterInformation => [
								label = Either.forRight(Tuple.two(12, 25))
							]
						]
					]
				]
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
						"insertSpaces": false,
						"tabSize": 4,
						"customProperty": -7
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
					insertSpaces = false
					putNumber('tabSize', new MyInteger(4))
					putNumber('customProperty', new MyInteger(-7))
				]
			]
		])
	}

	@Test
	def void testMessageIssue() {
		val issue = jsonHandler.gson.fromJson('''
			{
				"text": "Howdy!",
				"code": 1234,
				"cause": {
					"message": "Foo",
					"cause": {
						"message": "Bar"
					}
				}
			}
		''', MessageIssue)
		assertEquals('Howdy!', issue.text)
		assertEquals(1234, issue.issueCode)
		assertEquals('Foo', issue.cause.message)
		assertEquals('Bar', issue.cause.cause.message)
	}

	@Test
	def void testInitialize() {
		'''
			{
				"jsonrpc": "2.0",
				"id": "1",
				"method": "initialize",
				"params": {
					"rootUri": "file:///tmp/foo"
				}
			}
		'''.assertParse(new RequestMessage => [
			jsonrpc = "2.0"
			id = "1"
			method = MessageMethods.INITIALIZE
			params = new InitializeParams => [
				rootUri = "file:///tmp/foo"
			]
		])
	}

	@Test
	def void testInitializeClientCapabilities() {
		'''
			{
				"jsonrpc": "2.0",
				"id": "1",
				"method": "initialize",
				"params": {
					"rootUri": "file:///tmp/foo",
					"capabilities": {
						"textDocument": {
							"synchronization": {
								"dynamicRegistration": false,
								"willSave": true,
								"willSaveWaitUntil": false,
								"didSave": true
							},
							"completion": {
								"dynamicRegistration": false,
								"completionItem": {
									"snippetSupport": true,
									"commitCharactersSupport": true,
									"documentationFormat": ["plaintext", "markdown"]
								},
								"completionItemKind": {
									"valueSet": [2, 3]
								},
								"contextSupport": false
							},
							"hover": {
								"dynamicRegistration": false,
								"contentFormat": ["plaintext", "markdown"]
							},
							"signatureHelp": {
								"dynamicRegistration": false,
								"signatureInformation": {
									"documentationFormat": ["plaintext", "markdown"]
								}
							},
							"references": {
								"dynamicRegistration": false
							},
							"documentHighlight": {
								"dynamicRegistration": false
							},
							"documentSymbol": {
								"dynamicRegistration": false,
								"symbolKind": {
									valueSet: [2, 3, 4, 5]
								}
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
						},
						"workspace": {}
					}
				}
			}
		'''.assertParse(new RequestMessage => [
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
		])
	}



	@Test
	def void testProgressCreate() {
		'''
			{
				"jsonrpc": "2.0",
				"id": 1,
				"method": "window/workDoneProgress/create",
				"params": {
					"token": "progress-token"
				}
			}
		'''.assertParse(new RequestMessage => [
			jsonrpc = "2.0"
			id = 1
			method = MessageMethods.PROGRESS_CREATE
			params = new WorkDoneProgressCreateParams => [
				token = Either.forLeft("progress-token")
			]
		])

		'''
			{
				"jsonrpc": "2.0",
				"id": 1,
				"method": "window/workDoneProgress/create",
				"params": {
					"token": 1234
				}
			}
		'''.assertParse(new RequestMessage => [
			jsonrpc = "2.0"
			id = 1
			method = MessageMethods.PROGRESS_CREATE
			params = new WorkDoneProgressCreateParams => [
				token = Either.forRight(1234)
			]
		])
	}

	@Test
	def void testProgressCancel() {
		'''
			{
				"jsonrpc": "2.0",
				"id": 1,
				"method": "window/workDoneProgress/cancel",
				"params": {
					"token": "progress-token"
				}
			}
		'''.assertParse(new RequestMessage => [
			jsonrpc = "2.0"
			id = 1
			method = MessageMethods.PROGRESS_CANCEL
			params = new WorkDoneProgressCancelParams => [
				token = Either.forLeft("progress-token")
			]
		])

		'''
			{
				"jsonrpc": "2.0",
				"id": 1,
				"method": "window/workDoneProgress/cancel",
				"params": {
					"token": 1234
				}
			}
		'''.assertParse(new RequestMessage => [
			jsonrpc = "2.0"
			id = 1
			method = MessageMethods.PROGRESS_CANCEL
			params = new WorkDoneProgressCancelParams => [
				token = Either.forRight(1234)
			]
		])
	}

	@Test
	def void testProgressNotify() {
		'''
			{
				"jsonrpc": "2.0",
				"id": 1,
				"method": "$/progress",
				"params": {
					"token": "progress-token",
					"value": {
						"kind": "end",
						"message": "message"
					}
				}
			}
		'''.assertParse(new RequestMessage => [
			jsonrpc = "2.0"
			id = 1
			method = MessageMethods.PROGRESS_NOTIFY
			params = new ProgressParams => [
				token = Either.forLeft("progress-token")
				value = Either.<WorkDoneProgressNotification, Object>forLeft(new WorkDoneProgressEnd => [
					message = "message"
				])
			]
		])

		'''
			{
				"jsonrpc": "2.0",
				"id": 1,
				"method": "$/progress",
				"params": {
					"token": 1234,
					"value": {
						"foo": "bar"
					}
				}
			}
		'''.assertParse(new RequestMessage => [
			jsonrpc = "2.0"
			id = 1
			method = MessageMethods.PROGRESS_NOTIFY
			params = new ProgressParams => [
				token = Either.forRight(1234)
				value = Either.<WorkDoneProgressNotification, Object>forRight(new JsonObject => [
					addProperty("foo", "bar")
				])
			]
		])
	}
}
