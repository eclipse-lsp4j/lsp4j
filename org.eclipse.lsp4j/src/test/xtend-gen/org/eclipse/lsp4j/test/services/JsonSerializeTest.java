/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.test.services;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.FormattingOptions;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
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
    this.jsonHandler = new MessageJsonHandler(methods) {
      public GsonBuilder getDefaultGsonBuilder() {
        return super.getDefaultGsonBuilder().setPrettyPrinting();
      }
    };
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
        it.setMethod(MessageMethods.DOC_COMPLETION);
        InitializeResult _initializeResult = new InitializeResult();
        final Procedure1<InitializeResult> _function = new Procedure1<InitializeResult>() {
          public void apply(final InitializeResult it) {
            ServerCapabilities _serverCapabilities = new ServerCapabilities();
            it.setCapabilities(_serverCapabilities);
          }
        };
        InitializeResult _doubleArrow = ObjectExtensions.<InitializeResult>operator_doubleArrow(_initializeResult, _function);
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
    _builder.append("\"version\": 0,");
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
  public void testHoverResponse() {
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
