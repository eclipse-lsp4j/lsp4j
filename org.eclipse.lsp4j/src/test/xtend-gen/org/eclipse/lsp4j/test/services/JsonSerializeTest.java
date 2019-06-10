/**
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.test.services;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeActionCapabilities;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensCapabilities;
import org.eclipse.lsp4j.ColorProviderCapabilities;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionCapabilities;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemCapabilities;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionItemKindCapabilities;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.DefinitionCapabilities;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlightCapabilities;
import org.eclipse.lsp4j.DocumentLinkCapabilities;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.FormattingCapabilities;
import org.eclipse.lsp4j.FormattingOptions;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverCapabilities;
import org.eclipse.lsp4j.ImplementationCapabilities;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import org.eclipse.lsp4j.OnTypeFormattingCapabilities;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.RangeFormattingCapabilities;
import org.eclipse.lsp4j.ReferencesCapabilities;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.SignatureHelpCapabilities;
import org.eclipse.lsp4j.SignatureInformationCapabilities;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.SymbolKindCapabilities;
import org.eclipse.lsp4j.SynchronizationCapabilities;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.TypeDefinitionCapabilities;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.test.services.LineEndings;
import org.eclipse.lsp4j.test.services.MessageMethods;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("all")
public class JsonSerializeTest {
  private static class TestObject {
    double foo = 12.3;
    
    String bar = "qwertz";
  }
  
  private MessageJsonHandler jsonHandler;
  
  @Before
  public void setup() {
    final Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(LanguageServer.class);
    final Consumer<GsonBuilder> _function = new Consumer<GsonBuilder>() {
      public void accept(final GsonBuilder it) {
        it.setPrettyPrinting();
      }
    };
    MessageJsonHandler _messageJsonHandler = new MessageJsonHandler(methods, _function);
    this.jsonHandler = _messageJsonHandler;
  }
  
  private void assertSerialize(final Message message, final CharSequence expected) {
    Assert.assertEquals(expected.toString().trim(), LineEndings.toSystemLineEndings(this.jsonHandler.serialize(message)));
  }
  
  @Test
  public void testCompletion() {
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = new Procedure1<RequestMessage>() {
      public void apply(final RequestMessage it) {
        it.setJsonrpc("2.0");
        it.setId("1");
        it.setMethod(MessageMethods.DOC_COMPLETION);
        TextDocumentPositionParams _textDocumentPositionParams = new TextDocumentPositionParams();
        final Procedure1<TextDocumentPositionParams> _function = new Procedure1<TextDocumentPositionParams>() {
          public void apply(final TextDocumentPositionParams it) {
            TextDocumentIdentifier _textDocumentIdentifier = new TextDocumentIdentifier("file:///tmp/foo");
            it.setTextDocument(_textDocumentIdentifier);
            Position _position = new Position(4, 22);
            it.setPosition(_position);
          }
        };
        TextDocumentPositionParams _doubleArrow = ObjectExtensions.<TextDocumentPositionParams>operator_doubleArrow(_textDocumentPositionParams, _function);
        it.setParams(_doubleArrow);
      }
    };
    final RequestMessage message = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"1\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"textDocument/completion\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"uri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"position\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"character\": 22");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testInit() {
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = new Procedure1<RequestMessage>() {
      public void apply(final RequestMessage it) {
        it.setJsonrpc("2.0");
        it.setId("1");
        it.setMethod(MessageMethods.INITIALIZE);
        InitializeParams _initializeParams = new InitializeParams();
        final Procedure1<InitializeParams> _function = new Procedure1<InitializeParams>() {
          public void apply(final InitializeParams it) {
            it.setRootUri("file:///tmp/foo");
          }
        };
        InitializeParams _doubleArrow = ObjectExtensions.<InitializeParams>operator_doubleArrow(_initializeParams, _function);
        it.setParams(_doubleArrow);
      }
    };
    final RequestMessage message = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"1\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"initialize\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"processId\": null,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"rootUri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testInitClientCapabilities() {
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = new Procedure1<RequestMessage>() {
      public void apply(final RequestMessage it) {
        it.setJsonrpc("2.0");
        it.setId("1");
        it.setMethod(MessageMethods.INITIALIZE);
        InitializeParams _initializeParams = new InitializeParams();
        final Procedure1<InitializeParams> _function = new Procedure1<InitializeParams>() {
          public void apply(final InitializeParams it) {
            it.setRootUri("file:///tmp/foo");
            ClientCapabilities _clientCapabilities = new ClientCapabilities();
            final Procedure1<ClientCapabilities> _function = new Procedure1<ClientCapabilities>() {
              public void apply(final ClientCapabilities it) {
                TextDocumentClientCapabilities _textDocumentClientCapabilities = new TextDocumentClientCapabilities();
                final Procedure1<TextDocumentClientCapabilities> _function = new Procedure1<TextDocumentClientCapabilities>() {
                  public void apply(final TextDocumentClientCapabilities it) {
                    SynchronizationCapabilities _synchronizationCapabilities = new SynchronizationCapabilities();
                    final Procedure1<SynchronizationCapabilities> _function = new Procedure1<SynchronizationCapabilities>() {
                      public void apply(final SynchronizationCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                        it.setWillSave(Boolean.valueOf(true));
                        it.setWillSaveWaitUntil(Boolean.valueOf(false));
                        it.setDidSave(Boolean.valueOf(true));
                      }
                    };
                    SynchronizationCapabilities _doubleArrow = ObjectExtensions.<SynchronizationCapabilities>operator_doubleArrow(_synchronizationCapabilities, _function);
                    it.setSynchronization(_doubleArrow);
                    CompletionCapabilities _completionCapabilities = new CompletionCapabilities();
                    final Procedure1<CompletionCapabilities> _function_1 = new Procedure1<CompletionCapabilities>() {
                      public void apply(final CompletionCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                        CompletionItemCapabilities _completionItemCapabilities = new CompletionItemCapabilities();
                        final Procedure1<CompletionItemCapabilities> _function = new Procedure1<CompletionItemCapabilities>() {
                          public void apply(final CompletionItemCapabilities it) {
                            it.setSnippetSupport(Boolean.valueOf(true));
                            it.setCommitCharactersSupport(Boolean.valueOf(true));
                            it.setDocumentationFormat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(MarkupKind.PLAINTEXT, MarkupKind.MARKDOWN)));
                          }
                        };
                        CompletionItemCapabilities _doubleArrow = ObjectExtensions.<CompletionItemCapabilities>operator_doubleArrow(_completionItemCapabilities, _function);
                        it.setCompletionItem(_doubleArrow);
                        CompletionItemKindCapabilities _completionItemKindCapabilities = new CompletionItemKindCapabilities();
                        final Procedure1<CompletionItemKindCapabilities> _function_1 = new Procedure1<CompletionItemKindCapabilities>() {
                          public void apply(final CompletionItemKindCapabilities it) {
                            it.setValueSet(Collections.<CompletionItemKind>unmodifiableList(CollectionLiterals.<CompletionItemKind>newArrayList(CompletionItemKind.Method, CompletionItemKind.Function)));
                          }
                        };
                        CompletionItemKindCapabilities _doubleArrow_1 = ObjectExtensions.<CompletionItemKindCapabilities>operator_doubleArrow(_completionItemKindCapabilities, _function_1);
                        it.setCompletionItemKind(_doubleArrow_1);
                        it.setContextSupport(Boolean.valueOf(false));
                      }
                    };
                    CompletionCapabilities _doubleArrow_1 = ObjectExtensions.<CompletionCapabilities>operator_doubleArrow(_completionCapabilities, _function_1);
                    it.setCompletion(_doubleArrow_1);
                    HoverCapabilities _hoverCapabilities = new HoverCapabilities();
                    final Procedure1<HoverCapabilities> _function_2 = new Procedure1<HoverCapabilities>() {
                      public void apply(final HoverCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                        it.setContentFormat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(MarkupKind.PLAINTEXT, MarkupKind.MARKDOWN)));
                      }
                    };
                    HoverCapabilities _doubleArrow_2 = ObjectExtensions.<HoverCapabilities>operator_doubleArrow(_hoverCapabilities, _function_2);
                    it.setHover(_doubleArrow_2);
                    SignatureHelpCapabilities _signatureHelpCapabilities = new SignatureHelpCapabilities();
                    final Procedure1<SignatureHelpCapabilities> _function_3 = new Procedure1<SignatureHelpCapabilities>() {
                      public void apply(final SignatureHelpCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                        SignatureInformationCapabilities _signatureInformationCapabilities = new SignatureInformationCapabilities();
                        final Procedure1<SignatureInformationCapabilities> _function = new Procedure1<SignatureInformationCapabilities>() {
                          public void apply(final SignatureInformationCapabilities it) {
                            it.setDocumentationFormat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(MarkupKind.PLAINTEXT, MarkupKind.MARKDOWN)));
                          }
                        };
                        SignatureInformationCapabilities _doubleArrow = ObjectExtensions.<SignatureInformationCapabilities>operator_doubleArrow(_signatureInformationCapabilities, _function);
                        it.setSignatureInformation(_doubleArrow);
                      }
                    };
                    SignatureHelpCapabilities _doubleArrow_3 = ObjectExtensions.<SignatureHelpCapabilities>operator_doubleArrow(_signatureHelpCapabilities, _function_3);
                    it.setSignatureHelp(_doubleArrow_3);
                    ReferencesCapabilities _referencesCapabilities = new ReferencesCapabilities();
                    final Procedure1<ReferencesCapabilities> _function_4 = new Procedure1<ReferencesCapabilities>() {
                      public void apply(final ReferencesCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    ReferencesCapabilities _doubleArrow_4 = ObjectExtensions.<ReferencesCapabilities>operator_doubleArrow(_referencesCapabilities, _function_4);
                    it.setReferences(_doubleArrow_4);
                    DocumentHighlightCapabilities _documentHighlightCapabilities = new DocumentHighlightCapabilities();
                    final Procedure1<DocumentHighlightCapabilities> _function_5 = new Procedure1<DocumentHighlightCapabilities>() {
                      public void apply(final DocumentHighlightCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    DocumentHighlightCapabilities _doubleArrow_5 = ObjectExtensions.<DocumentHighlightCapabilities>operator_doubleArrow(_documentHighlightCapabilities, _function_5);
                    it.setDocumentHighlight(_doubleArrow_5);
                    DocumentSymbolCapabilities _documentSymbolCapabilities = new DocumentSymbolCapabilities();
                    final Procedure1<DocumentSymbolCapabilities> _function_6 = new Procedure1<DocumentSymbolCapabilities>() {
                      public void apply(final DocumentSymbolCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                        SymbolKindCapabilities _symbolKindCapabilities = new SymbolKindCapabilities();
                        final Procedure1<SymbolKindCapabilities> _function = new Procedure1<SymbolKindCapabilities>() {
                          public void apply(final SymbolKindCapabilities it) {
                            it.setValueSet(Collections.<SymbolKind>unmodifiableList(CollectionLiterals.<SymbolKind>newArrayList(SymbolKind.Module, SymbolKind.Namespace, SymbolKind.Package, SymbolKind.Class)));
                          }
                        };
                        SymbolKindCapabilities _doubleArrow = ObjectExtensions.<SymbolKindCapabilities>operator_doubleArrow(_symbolKindCapabilities, _function);
                        it.setSymbolKind(_doubleArrow);
                      }
                    };
                    DocumentSymbolCapabilities _doubleArrow_6 = ObjectExtensions.<DocumentSymbolCapabilities>operator_doubleArrow(_documentSymbolCapabilities, _function_6);
                    it.setDocumentSymbol(_doubleArrow_6);
                    FormattingCapabilities _formattingCapabilities = new FormattingCapabilities();
                    final Procedure1<FormattingCapabilities> _function_7 = new Procedure1<FormattingCapabilities>() {
                      public void apply(final FormattingCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    FormattingCapabilities _doubleArrow_7 = ObjectExtensions.<FormattingCapabilities>operator_doubleArrow(_formattingCapabilities, _function_7);
                    it.setFormatting(_doubleArrow_7);
                    RangeFormattingCapabilities _rangeFormattingCapabilities = new RangeFormattingCapabilities();
                    final Procedure1<RangeFormattingCapabilities> _function_8 = new Procedure1<RangeFormattingCapabilities>() {
                      public void apply(final RangeFormattingCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    RangeFormattingCapabilities _doubleArrow_8 = ObjectExtensions.<RangeFormattingCapabilities>operator_doubleArrow(_rangeFormattingCapabilities, _function_8);
                    it.setRangeFormatting(_doubleArrow_8);
                    OnTypeFormattingCapabilities _onTypeFormattingCapabilities = new OnTypeFormattingCapabilities();
                    final Procedure1<OnTypeFormattingCapabilities> _function_9 = new Procedure1<OnTypeFormattingCapabilities>() {
                      public void apply(final OnTypeFormattingCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    OnTypeFormattingCapabilities _doubleArrow_9 = ObjectExtensions.<OnTypeFormattingCapabilities>operator_doubleArrow(_onTypeFormattingCapabilities, _function_9);
                    it.setOnTypeFormatting(_doubleArrow_9);
                    DefinitionCapabilities _definitionCapabilities = new DefinitionCapabilities();
                    final Procedure1<DefinitionCapabilities> _function_10 = new Procedure1<DefinitionCapabilities>() {
                      public void apply(final DefinitionCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    DefinitionCapabilities _doubleArrow_10 = ObjectExtensions.<DefinitionCapabilities>operator_doubleArrow(_definitionCapabilities, _function_10);
                    it.setDefinition(_doubleArrow_10);
                    TypeDefinitionCapabilities _typeDefinitionCapabilities = new TypeDefinitionCapabilities();
                    final Procedure1<TypeDefinitionCapabilities> _function_11 = new Procedure1<TypeDefinitionCapabilities>() {
                      public void apply(final TypeDefinitionCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    TypeDefinitionCapabilities _doubleArrow_11 = ObjectExtensions.<TypeDefinitionCapabilities>operator_doubleArrow(_typeDefinitionCapabilities, _function_11);
                    it.setTypeDefinition(_doubleArrow_11);
                    ImplementationCapabilities _implementationCapabilities = new ImplementationCapabilities();
                    final Procedure1<ImplementationCapabilities> _function_12 = new Procedure1<ImplementationCapabilities>() {
                      public void apply(final ImplementationCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    ImplementationCapabilities _doubleArrow_12 = ObjectExtensions.<ImplementationCapabilities>operator_doubleArrow(_implementationCapabilities, _function_12);
                    it.setImplementation(_doubleArrow_12);
                    CodeActionCapabilities _codeActionCapabilities = new CodeActionCapabilities();
                    final Procedure1<CodeActionCapabilities> _function_13 = new Procedure1<CodeActionCapabilities>() {
                      public void apply(final CodeActionCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    CodeActionCapabilities _doubleArrow_13 = ObjectExtensions.<CodeActionCapabilities>operator_doubleArrow(_codeActionCapabilities, _function_13);
                    it.setCodeAction(_doubleArrow_13);
                    CodeLensCapabilities _codeLensCapabilities = new CodeLensCapabilities();
                    final Procedure1<CodeLensCapabilities> _function_14 = new Procedure1<CodeLensCapabilities>() {
                      public void apply(final CodeLensCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    CodeLensCapabilities _doubleArrow_14 = ObjectExtensions.<CodeLensCapabilities>operator_doubleArrow(_codeLensCapabilities, _function_14);
                    it.setCodeLens(_doubleArrow_14);
                    DocumentLinkCapabilities _documentLinkCapabilities = new DocumentLinkCapabilities();
                    final Procedure1<DocumentLinkCapabilities> _function_15 = new Procedure1<DocumentLinkCapabilities>() {
                      public void apply(final DocumentLinkCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    DocumentLinkCapabilities _doubleArrow_15 = ObjectExtensions.<DocumentLinkCapabilities>operator_doubleArrow(_documentLinkCapabilities, _function_15);
                    it.setDocumentLink(_doubleArrow_15);
                    ColorProviderCapabilities _colorProviderCapabilities = new ColorProviderCapabilities();
                    final Procedure1<ColorProviderCapabilities> _function_16 = new Procedure1<ColorProviderCapabilities>() {
                      public void apply(final ColorProviderCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    ColorProviderCapabilities _doubleArrow_16 = ObjectExtensions.<ColorProviderCapabilities>operator_doubleArrow(_colorProviderCapabilities, _function_16);
                    it.setColorProvider(_doubleArrow_16);
                    RenameCapabilities _renameCapabilities = new RenameCapabilities();
                    final Procedure1<RenameCapabilities> _function_17 = new Procedure1<RenameCapabilities>() {
                      public void apply(final RenameCapabilities it) {
                        it.setDynamicRegistration(Boolean.valueOf(false));
                      }
                    };
                    RenameCapabilities _doubleArrow_17 = ObjectExtensions.<RenameCapabilities>operator_doubleArrow(_renameCapabilities, _function_17);
                    it.setRename(_doubleArrow_17);
                  }
                };
                TextDocumentClientCapabilities _doubleArrow = ObjectExtensions.<TextDocumentClientCapabilities>operator_doubleArrow(_textDocumentClientCapabilities, _function);
                it.setTextDocument(_doubleArrow);
                WorkspaceClientCapabilities _workspaceClientCapabilities = new WorkspaceClientCapabilities();
                it.setWorkspace(_workspaceClientCapabilities);
              }
            };
            ClientCapabilities _doubleArrow = ObjectExtensions.<ClientCapabilities>operator_doubleArrow(_clientCapabilities, _function);
            it.setCapabilities(_doubleArrow);
          }
        };
        InitializeParams _doubleArrow = ObjectExtensions.<InitializeParams>operator_doubleArrow(_initializeParams, _function);
        it.setParams(_doubleArrow);
      }
    };
    final RequestMessage message = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"1\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"initialize\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"processId\": null,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"rootUri\": \"file:///tmp/foo\",");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"capabilities\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"workspace\": {},");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"synchronization\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"willSave\": true,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"willSaveWaitUntil\": false,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"didSave\": true,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"completion\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"completionItem\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"snippetSupport\": true,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"commitCharactersSupport\": true,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"documentationFormat\": [");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"plaintext\",");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"markdown\"");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"completionItemKind\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"valueSet\": [");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("2,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("3");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"contextSupport\": false,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"hover\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"contentFormat\": [");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"plaintext\",");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"markdown\"");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("],");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"signatureHelp\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"signatureInformation\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"documentationFormat\": [");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"plaintext\",");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"markdown\"");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"references\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"documentHighlight\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"documentSymbol\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"symbolKind\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"valueSet\": [");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("2,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("3,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("4,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("5");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"formatting\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"rangeFormatting\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"onTypeFormatting\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"definition\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"typeDefinition\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"implementation\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"codeAction\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"codeLens\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"documentLink\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"colorProvider\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"rename\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testInitResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId(12);
        InitializeResult _initializeResult = new InitializeResult();
        final Procedure1<InitializeResult> _function = new Procedure1<InitializeResult>() {
          public void apply(final InitializeResult it) {
            ServerCapabilities _serverCapabilities = new ServerCapabilities();
            it.setCapabilities(_serverCapabilities);
          }
        };
        InitializeResult _doubleArrow = ObjectExtensions.<InitializeResult>operator_doubleArrow(_initializeResult, _function);
        it.setResult(_doubleArrow);
      }
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": 12,");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"capabilities\": {}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testDidChange() {
    NotificationMessage _notificationMessage = new NotificationMessage();
    final Procedure1<NotificationMessage> _function = new Procedure1<NotificationMessage>() {
      public void apply(final NotificationMessage it) {
        it.setJsonrpc("2.0");
        it.setMethod(MessageMethods.DID_CHANGE_DOC);
        DidChangeTextDocumentParams _didChangeTextDocumentParams = new DidChangeTextDocumentParams();
        final Procedure1<DidChangeTextDocumentParams> _function = new Procedure1<DidChangeTextDocumentParams>() {
          public void apply(final DidChangeTextDocumentParams it) {
            VersionedTextDocumentIdentifier _versionedTextDocumentIdentifier = new VersionedTextDocumentIdentifier();
            final Procedure1<VersionedTextDocumentIdentifier> _function = new Procedure1<VersionedTextDocumentIdentifier>() {
              public void apply(final VersionedTextDocumentIdentifier it) {
                it.setUri("file:///tmp/foo");
              }
            };
            VersionedTextDocumentIdentifier _doubleArrow = ObjectExtensions.<VersionedTextDocumentIdentifier>operator_doubleArrow(_versionedTextDocumentIdentifier, _function);
            it.setTextDocument(_doubleArrow);
            ArrayList<TextDocumentContentChangeEvent> _arrayList = new ArrayList<TextDocumentContentChangeEvent>();
            final Procedure1<ArrayList<TextDocumentContentChangeEvent>> _function_1 = new Procedure1<ArrayList<TextDocumentContentChangeEvent>>() {
              public void apply(final ArrayList<TextDocumentContentChangeEvent> it) {
                TextDocumentContentChangeEvent _textDocumentContentChangeEvent = new TextDocumentContentChangeEvent();
                final Procedure1<TextDocumentContentChangeEvent> _function = new Procedure1<TextDocumentContentChangeEvent>() {
                  public void apply(final TextDocumentContentChangeEvent it) {
                    Range _range = new Range();
                    final Procedure1<Range> _function = new Procedure1<Range>() {
                      public void apply(final Range it) {
                        Position _position = new Position(7, 12);
                        it.setStart(_position);
                        Position _position_1 = new Position(8, 16);
                        it.setEnd(_position_1);
                      }
                    };
                    Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function);
                    it.setRange(_doubleArrow);
                    it.setRangeLength(Integer.valueOf(20));
                    it.setText("bar");
                  }
                };
                TextDocumentContentChangeEvent _doubleArrow = ObjectExtensions.<TextDocumentContentChangeEvent>operator_doubleArrow(_textDocumentContentChangeEvent, _function);
                it.add(_doubleArrow);
              }
            };
            ArrayList<TextDocumentContentChangeEvent> _doubleArrow_1 = ObjectExtensions.<ArrayList<TextDocumentContentChangeEvent>>operator_doubleArrow(_arrayList, _function_1);
            it.setContentChanges(_doubleArrow_1);
          }
        };
        DidChangeTextDocumentParams _doubleArrow = ObjectExtensions.<DidChangeTextDocumentParams>operator_doubleArrow(_didChangeTextDocumentParams, _function);
        it.setParams(_doubleArrow);
      }
    };
    final NotificationMessage message = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"textDocument/didChange\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"version\": null,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"uri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"contentChanges\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"line\": 7,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"character\": 12");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"line\": 8,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"character\": 16");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"rangeLength\": 20,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"text\": \"bar\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testPublishDiagnostics() {
    NotificationMessage _notificationMessage = new NotificationMessage();
    final Procedure1<NotificationMessage> _function = new Procedure1<NotificationMessage>() {
      public void apply(final NotificationMessage it) {
        it.setJsonrpc("2.0");
        it.setMethod(MessageMethods.SHOW_DIAGNOSTICS);
        PublishDiagnosticsParams _publishDiagnosticsParams = new PublishDiagnosticsParams();
        final Procedure1<PublishDiagnosticsParams> _function = new Procedure1<PublishDiagnosticsParams>() {
          public void apply(final PublishDiagnosticsParams it) {
            it.setUri("file:///tmp/foo");
            ArrayList<Diagnostic> _arrayList = new ArrayList<Diagnostic>();
            final Procedure1<ArrayList<Diagnostic>> _function = new Procedure1<ArrayList<Diagnostic>>() {
              public void apply(final ArrayList<Diagnostic> it) {
                Diagnostic _diagnostic = new Diagnostic();
                final Procedure1<Diagnostic> _function = new Procedure1<Diagnostic>() {
                  public void apply(final Diagnostic it) {
                    Range _range = new Range();
                    final Procedure1<Range> _function = new Procedure1<Range>() {
                      public void apply(final Range it) {
                        Position _position = new Position(4, 22);
                        it.setStart(_position);
                        Position _position_1 = new Position(4, 25);
                        it.setEnd(_position_1);
                      }
                    };
                    Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function);
                    it.setRange(_doubleArrow);
                    it.setSeverity(DiagnosticSeverity.Error);
                    it.setMessage("Couldn\'t resolve reference to State \'bar\'.");
                  }
                };
                Diagnostic _doubleArrow = ObjectExtensions.<Diagnostic>operator_doubleArrow(_diagnostic, _function);
                it.add(_doubleArrow);
              }
            };
            ArrayList<Diagnostic> _doubleArrow = ObjectExtensions.<ArrayList<Diagnostic>>operator_doubleArrow(_arrayList, _function);
            it.setDiagnostics(_doubleArrow);
          }
        };
        PublishDiagnosticsParams _doubleArrow = ObjectExtensions.<PublishDiagnosticsParams>operator_doubleArrow(_publishDiagnosticsParams, _function);
        it.setParams(_doubleArrow);
      }
    };
    final NotificationMessage message = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"textDocument/publishDiagnostics\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"uri\": \"file:///tmp/foo\",");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"diagnostics\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"character\": 22");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"character\": 25");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"severity\": 1,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"message\": \"Couldn\\u0027t resolve reference to State \\u0027bar\\u0027.\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testRenameResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        WorkspaceEdit _workspaceEdit = new WorkspaceEdit();
        final Procedure1<WorkspaceEdit> _function = new Procedure1<WorkspaceEdit>() {
          public void apply(final WorkspaceEdit it) {
            HashMap<String, List<TextEdit>> _hashMap = new HashMap<String, List<TextEdit>>();
            final Procedure1<HashMap<String, List<TextEdit>>> _function = new Procedure1<HashMap<String, List<TextEdit>>>() {
              public void apply(final HashMap<String, List<TextEdit>> it) {
                TextEdit _textEdit = new TextEdit();
                final Procedure1<TextEdit> _function = new Procedure1<TextEdit>() {
                  public void apply(final TextEdit it) {
                    Range _range = new Range();
                    final Procedure1<Range> _function = new Procedure1<Range>() {
                      public void apply(final Range it) {
                        Position _position = new Position(3, 32);
                        it.setStart(_position);
                        Position _position_1 = new Position(3, 35);
                        it.setEnd(_position_1);
                      }
                    };
                    Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function);
                    it.setRange(_doubleArrow);
                    it.setNewText("foobar");
                  }
                };
                TextEdit _doubleArrow = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit, _function);
                TextEdit _textEdit_1 = new TextEdit();
                final Procedure1<TextEdit> _function_1 = new Procedure1<TextEdit>() {
                  public void apply(final TextEdit it) {
                    Range _range = new Range();
                    final Procedure1<Range> _function = new Procedure1<Range>() {
                      public void apply(final Range it) {
                        Position _position = new Position(4, 22);
                        it.setStart(_position);
                        Position _position_1 = new Position(4, 25);
                        it.setEnd(_position_1);
                      }
                    };
                    Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function);
                    it.setRange(_doubleArrow);
                    it.setNewText("foobar");
                  }
                };
                TextEdit _doubleArrow_1 = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit_1, _function_1);
                it.put("file:///tmp/foo", CollectionLiterals.<TextEdit>newArrayList(_doubleArrow, _doubleArrow_1));
              }
            };
            HashMap<String, List<TextEdit>> _doubleArrow = ObjectExtensions.<HashMap<String, List<TextEdit>>>operator_doubleArrow(_hashMap, _function);
            it.setChanges(_doubleArrow);
          }
        };
        WorkspaceEdit _doubleArrow = ObjectExtensions.<WorkspaceEdit>operator_doubleArrow(_workspaceEdit, _function);
        it.setResult(_doubleArrow);
      }
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"changes\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"file:///tmp/foo\": [");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"character\": 32");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"character\": 35");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"newText\": \"foobar\"");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"character\": 22");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"character\": 25");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"newText\": \"foobar\"");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testHoverResponse1() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        Hover _hover = new Hover();
        final Procedure1<Hover> _function = new Procedure1<Hover>() {
          public void apply(final Hover it) {
            Range _range = new Range();
            final Procedure1<Range> _function = new Procedure1<Range>() {
              public void apply(final Range it) {
                Position _position = new Position(3, 32);
                it.setStart(_position);
                Position _position_1 = new Position(3, 35);
                it.setEnd(_position_1);
              }
            };
            Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function);
            it.setRange(_doubleArrow);
            it.setContents(CollectionLiterals.<Either<String, MarkedString>>newArrayList(
              Either.<String, MarkedString>forLeft("foo"), 
              Either.<String, MarkedString>forLeft("boo shuby doo")));
          }
        };
        Hover _doubleArrow = ObjectExtensions.<Hover>operator_doubleArrow(_hover, _function);
        it.setResult(_doubleArrow);
      }
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"contents\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"foo\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"boo shuby doo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("],");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"character\": 32");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"character\": 35");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testHoverResponse2() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        Hover _hover = new Hover();
        final Procedure1<Hover> _function = new Procedure1<Hover>() {
          public void apply(final Hover it) {
            MarkupContent _markupContent = new MarkupContent();
            final Procedure1<MarkupContent> _function = new Procedure1<MarkupContent>() {
              public void apply(final MarkupContent it) {
                it.setKind("plaintext");
                it.setValue("foo");
              }
            };
            MarkupContent _doubleArrow = ObjectExtensions.<MarkupContent>operator_doubleArrow(_markupContent, _function);
            it.setContents(_doubleArrow);
          }
        };
        Hover _doubleArrow = ObjectExtensions.<Hover>operator_doubleArrow(_hover, _function);
        it.setResult(_doubleArrow);
      }
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"contents\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"kind\": \"plaintext\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"value\": \"foo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testCodeLensResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        CodeLens _codeLens = new CodeLens();
        final Procedure1<CodeLens> _function = new Procedure1<CodeLens>() {
          public void apply(final CodeLens it) {
            Range _range = new Range();
            final Procedure1<Range> _function = new Procedure1<Range>() {
              public void apply(final Range it) {
                Position _position = new Position(3, 32);
                it.setStart(_position);
                Position _position_1 = new Position(3, 35);
                it.setEnd(_position_1);
              }
            };
            Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function);
            it.setRange(_doubleArrow);
            Command _command = new Command();
            final Procedure1<Command> _function_1 = new Procedure1<Command>() {
              public void apply(final Command it) {
                it.setTitle("save");
                it.setCommand("saveCommand");
                JsonObject _jsonObject = new JsonObject();
                final Procedure1<JsonObject> _function = new Procedure1<JsonObject>() {
                  public void apply(final JsonObject it) {
                    it.addProperty("uri", "file:/foo");
                    it.addProperty("version", Integer.valueOf(5));
                  }
                };
                JsonObject _doubleArrow = ObjectExtensions.<JsonObject>operator_doubleArrow(_jsonObject, _function);
                it.setArguments(CollectionLiterals.<Object>newArrayList(_doubleArrow));
              }
            };
            Command _doubleArrow_1 = ObjectExtensions.<Command>operator_doubleArrow(_command, _function_1);
            it.setCommand(_doubleArrow_1);
            JsonObject _jsonObject = new JsonObject();
            final Procedure1<JsonObject> _function_2 = new Procedure1<JsonObject>() {
              public void apply(final JsonObject it) {
                it.addProperty("key", "value");
              }
            };
            JsonObject _doubleArrow_2 = ObjectExtensions.<JsonObject>operator_doubleArrow(_jsonObject, _function_2);
            it.setData(CollectionLiterals.<Object>newArrayList(
              Integer.valueOf(42), 
              "qwert", _doubleArrow_2));
          }
        };
        CodeLens _doubleArrow = ObjectExtensions.<CodeLens>operator_doubleArrow(_codeLens, _function);
        it.setResult(_doubleArrow);
      }
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"character\": 32");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"character\": 35");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"command\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"title\": \"save\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"command\": \"saveCommand\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"arguments\": [");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"uri\": \"file:/foo\",");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"version\": 5");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"data\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("42,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"qwert\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"key\": \"value\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testResponseError() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        ResponseError _responseError = new ResponseError();
        final Procedure1<ResponseError> _function = new Procedure1<ResponseError>() {
          public void apply(final ResponseError it) {
            it.setCode(ResponseErrorCode.InvalidRequest);
            it.setMessage("Could not parse request.");
          }
        };
        ResponseError _doubleArrow = ObjectExtensions.<ResponseError>operator_doubleArrow(_responseError, _function);
        it.setError(_doubleArrow);
      }
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"error\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"code\": -32600,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"message\": \"Could not parse request.\"");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testCompletionResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        CompletionList _completionList = new CompletionList();
        final Procedure1<CompletionList> _function = new Procedure1<CompletionList>() {
          public void apply(final CompletionList it) {
            it.setIsIncomplete(true);
            CompletionItem _completionItem = new CompletionItem();
            final Procedure1<CompletionItem> _function = new Procedure1<CompletionItem>() {
              public void apply(final CompletionItem it) {
                it.setLabel("foo");
              }
            };
            CompletionItem _doubleArrow = ObjectExtensions.<CompletionItem>operator_doubleArrow(_completionItem, _function);
            it.setItems(Collections.<CompletionItem>unmodifiableList(CollectionLiterals.<CompletionItem>newArrayList(_doubleArrow)));
          }
        };
        CompletionList _doubleArrow = ObjectExtensions.<CompletionList>operator_doubleArrow(_completionList, _function);
        it.setResult(_doubleArrow);
      }
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"isIncomplete\": true,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"items\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"label\": \"foo\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testDocumentFormatting() {
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = new Procedure1<RequestMessage>() {
      public void apply(final RequestMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        it.setMethod(MessageMethods.DOC_FORMATTING);
        DocumentFormattingParams _documentFormattingParams = new DocumentFormattingParams();
        final Procedure1<DocumentFormattingParams> _function = new Procedure1<DocumentFormattingParams>() {
          public void apply(final DocumentFormattingParams it) {
            TextDocumentIdentifier _textDocumentIdentifier = new TextDocumentIdentifier("file:///tmp/foo");
            it.setTextDocument(_textDocumentIdentifier);
            FormattingOptions _formattingOptions = new FormattingOptions();
            final Procedure1<FormattingOptions> _function = new Procedure1<FormattingOptions>() {
              public void apply(final FormattingOptions it) {
                it.setTabSize(4);
                it.setInsertSpaces(false);
              }
            };
            FormattingOptions _doubleArrow = ObjectExtensions.<FormattingOptions>operator_doubleArrow(_formattingOptions, _function);
            it.setOptions(_doubleArrow);
            it.getOptions().putNumber("customProperty", Integer.valueOf((-7)));
          }
        };
        DocumentFormattingParams _doubleArrow = ObjectExtensions.<DocumentFormattingParams>operator_doubleArrow(_documentFormattingParams, _function);
        it.setParams(_doubleArrow);
      }
    };
    final RequestMessage message = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"textDocument/formatting\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"uri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"options\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"tabSize\": 4,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"insertSpaces\": false,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"customProperty\": -7");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testTelemetry() {
    NotificationMessage _notificationMessage = new NotificationMessage();
    final Procedure1<NotificationMessage> _function = new Procedure1<NotificationMessage>() {
      public void apply(final NotificationMessage it) {
        it.setJsonrpc("2.0");
        it.setMethod(MessageMethods.TELEMETRY_EVENT);
        JsonSerializeTest.TestObject _testObject = new JsonSerializeTest.TestObject();
        it.setParams(_testObject);
      }
    };
    final NotificationMessage message = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"telemetry/event\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"foo\": 12.3,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"bar\": \"qwertz\"");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testNullResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
      }
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": null");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
}
