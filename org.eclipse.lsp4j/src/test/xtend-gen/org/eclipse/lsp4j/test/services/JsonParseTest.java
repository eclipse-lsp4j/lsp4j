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

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LazilyParsedNumber;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionCapabilities;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensCapabilities;
import org.eclipse.lsp4j.ColorProviderCapabilities;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionCapabilities;
import org.eclipse.lsp4j.CompletionItemCapabilities;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionItemKindCapabilities;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.CreateFile;
import org.eclipse.lsp4j.CreateFileOptions;
import org.eclipse.lsp4j.DefinitionCapabilities;
import org.eclipse.lsp4j.DeleteFile;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlightCapabilities;
import org.eclipse.lsp4j.DocumentLinkCapabilities;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.FormattingCapabilities;
import org.eclipse.lsp4j.FormattingOptions;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverCapabilities;
import org.eclipse.lsp4j.ImplementationCapabilities;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import org.eclipse.lsp4j.OnTypeFormattingCapabilities;
import org.eclipse.lsp4j.ParameterInformation;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.RangeFormattingCapabilities;
import org.eclipse.lsp4j.ReferencesCapabilities;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.RenameFile;
import org.eclipse.lsp4j.ResourceOperation;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureHelpCapabilities;
import org.eclipse.lsp4j.SignatureInformation;
import org.eclipse.lsp4j.SignatureInformationCapabilities;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.SymbolKindCapabilities;
import org.eclipse.lsp4j.SynchronizationCapabilities;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentEdit;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.TypeDefinitionCapabilities;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.MethodProvider;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.MessageIssue;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.messages.Tuple;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.test.services.MessageMethods;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("all")
public class JsonParseTest {
  /**
   * Gson parses numbers with {@link LazilyParsedNumber}, which is not
   * equals-compatible with {@link Integer}.
   */
  @FinalFieldsConstructor
  private static class MyInteger extends Number {
    private final int value;
    
    public double doubleValue() {
      return this.value;
    }
    
    public float floatValue() {
      return this.value;
    }
    
    public int intValue() {
      return this.value;
    }
    
    public long longValue() {
      return this.value;
    }
    
    public boolean equals(final Object obj) {
      if ((obj instanceof Number)) {
        double _doubleValue = ((Number)obj).doubleValue();
        return (((double) this.value) == _doubleValue);
      }
      return false;
    }
    
    public int hashCode() {
      return Integer.hashCode(this.value);
    }
    
    public String toString() {
      return Integer.toString(this.value);
    }
    
    public MyInteger(final int value) {
      super();
      this.value = value;
    }
  }
  
  private MessageJsonHandler jsonHandler;
  
  @Before
  public void setup() {
    final Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(LanguageServer.class);
    final Map<String, JsonRpcMethod> clientMethods = ServiceEndpoints.getSupportedMethods(LanguageClient.class);
    final HashMap<String, JsonRpcMethod> all = new HashMap<String, JsonRpcMethod>();
    all.putAll(methods);
    all.putAll(clientMethods);
    MessageJsonHandler _messageJsonHandler = new MessageJsonHandler(all);
    this.jsonHandler = _messageJsonHandler;
  }
  
  private void assertParse(final CharSequence json, final Message expected) {
    final Message actual = this.jsonHandler.parseMessage(json);
    Assert.assertEquals(expected.toString(), actual.toString());
    Assert.assertEquals(expected, actual);
  }
  
  @Test
  public void testCompletion() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": 1,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"method\": \"textDocument/completion\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"uri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"position\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"character\": 22");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = new Procedure1<RequestMessage>() {
      public void apply(final RequestMessage it) {
        it.setJsonrpc("2.0");
        it.setId(1);
        it.setMethod(MessageMethods.DOC_COMPLETION);
        CompletionParams _completionParams = new CompletionParams();
        final Procedure1<CompletionParams> _function = new Procedure1<CompletionParams>() {
          public void apply(final CompletionParams it) {
            TextDocumentIdentifier _textDocumentIdentifier = new TextDocumentIdentifier();
            final Procedure1<TextDocumentIdentifier> _function = new Procedure1<TextDocumentIdentifier>() {
              public void apply(final TextDocumentIdentifier it) {
                it.setUri("file:///tmp/foo");
              }
            };
            TextDocumentIdentifier _doubleArrow = ObjectExtensions.<TextDocumentIdentifier>operator_doubleArrow(_textDocumentIdentifier, _function);
            it.setTextDocument(_doubleArrow);
            Position _position = new Position();
            final Procedure1<Position> _function_1 = new Procedure1<Position>() {
              public void apply(final Position it) {
                it.setLine(4);
                it.setCharacter(22);
              }
            };
            Position _doubleArrow_1 = ObjectExtensions.<Position>operator_doubleArrow(_position, _function_1);
            it.setPosition(_doubleArrow_1);
          }
        };
        CompletionParams _doubleArrow = ObjectExtensions.<CompletionParams>operator_doubleArrow(_completionParams, _function);
        it.setParams(_doubleArrow);
      }
    };
    RequestMessage _doubleArrow = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testDidChange() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"method\": \"textDocument/didChange\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"uri\": \"file:///tmp/foo\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"version\": 1234");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"contentChanges\": [");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"line\": 7,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"character\": 12");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"line\": 8,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"character\": 16");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"rangeLength\": 20,");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"text\": \"bar\"");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
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
                it.setVersion(Integer.valueOf(1234));
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
    NotificationMessage _doubleArrow = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testPublishDiagnostics() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"method\": \"textDocument/publishDiagnostics\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"uri\": \"file:///tmp/foo\",");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"diagnostics\": [");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"message\": \"Couldn\\u0027t resolve reference to State \\u0027bar\\u0027.\",");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"character\": 22,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"character\": 25,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"severity\": 1");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
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
    NotificationMessage _doubleArrow = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testDocumentSymbolResponse1() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_SYMBOL;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": [");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"name\" : \"foobar\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"kind\" : 9,");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"location\" : {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"uri\": \"file:/baz.txt\",");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"range\" : {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"character\": 22,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"character\": 25,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        SymbolInformation _symbolInformation = new SymbolInformation();
        final Procedure1<SymbolInformation> _function = new Procedure1<SymbolInformation>() {
          public void apply(final SymbolInformation it) {
            it.setName("foobar");
            it.setKind(SymbolKind.Constructor);
            Location _location = new Location();
            final Procedure1<Location> _function = new Procedure1<Location>() {
              public void apply(final Location it) {
                it.setUri("file:/baz.txt");
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
              }
            };
            Location _doubleArrow = ObjectExtensions.<Location>operator_doubleArrow(_location, _function);
            it.setLocation(_doubleArrow);
          }
        };
        SymbolInformation _doubleArrow = ObjectExtensions.<SymbolInformation>operator_doubleArrow(_symbolInformation, _function);
        it.setResult(CollectionLiterals.<Either<SymbolInformation, Object>>newArrayList(
          Either.<SymbolInformation, Object>forLeft(_doubleArrow)));
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testDocumentSymbolResponse2() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_SYMBOL;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": [");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"name\" : \"foobar\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"kind\" : 9,");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"range\" : {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 22,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 25,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"selectionRange\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 22,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 25,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        DocumentSymbol _documentSymbol = new DocumentSymbol();
        final Procedure1<DocumentSymbol> _function = new Procedure1<DocumentSymbol>() {
          public void apply(final DocumentSymbol it) {
            it.setName("foobar");
            it.setKind(SymbolKind.Constructor);
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
            Range _range_1 = new Range();
            final Procedure1<Range> _function_1 = new Procedure1<Range>() {
              public void apply(final Range it) {
                Position _position = new Position(4, 22);
                it.setStart(_position);
                Position _position_1 = new Position(4, 25);
                it.setEnd(_position_1);
              }
            };
            Range _doubleArrow_1 = ObjectExtensions.<Range>operator_doubleArrow(_range_1, _function_1);
            it.setSelectionRange(_doubleArrow_1);
          }
        };
        DocumentSymbol _doubleArrow = ObjectExtensions.<DocumentSymbol>operator_doubleArrow(_documentSymbol, _function);
        it.setResult(CollectionLiterals.<Either<Object, DocumentSymbol>>newArrayList(
          Either.<Object, DocumentSymbol>forRight(_doubleArrow)));
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testCodeActionResponse1() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_CODE_ACTION;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": [");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"title\": \"fixme\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"command\": \"fix\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        Command _command = new Command();
        final Procedure1<Command> _function = new Procedure1<Command>() {
          public void apply(final Command it) {
            it.setTitle("fixme");
            it.setCommand("fix");
          }
        };
        Command _doubleArrow = ObjectExtensions.<Command>operator_doubleArrow(_command, _function);
        it.setResult(CollectionLiterals.<Either<Command, Object>>newArrayList(
          Either.<Command, Object>forLeft(_doubleArrow)));
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testCodeActionResponse2() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_CODE_ACTION;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": [");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"title\": \"fixme\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"kind\": \"fix\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"diagnostics\": [],");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"edit\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"changes\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"file:test1533196529126.lspt\": [");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t\t");
    _builder.append("\"line\": 0,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t\t");
    _builder.append("\"character\": 0");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t\t");
    _builder.append("\"line\": 0,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t\t");
    _builder.append("\"character\": 5");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"newText\": \"fixed\"");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        CodeAction _codeAction = new CodeAction();
        final Procedure1<CodeAction> _function = new Procedure1<CodeAction>() {
          public void apply(final CodeAction it) {
            it.setTitle("fixme");
            it.setKind("fix");
            it.setDiagnostics(CollectionLiterals.<Diagnostic>newArrayList());
            WorkspaceEdit _workspaceEdit = new WorkspaceEdit();
            final Procedure1<WorkspaceEdit> _function = new Procedure1<WorkspaceEdit>() {
              public void apply(final WorkspaceEdit it) {
                TextEdit _textEdit = new TextEdit();
                final Procedure1<TextEdit> _function = new Procedure1<TextEdit>() {
                  public void apply(final TextEdit it) {
                    Range _range = new Range();
                    final Procedure1<Range> _function = new Procedure1<Range>() {
                      public void apply(final Range it) {
                        Position _position = new Position(0, 0);
                        it.setStart(_position);
                        Position _position_1 = new Position(0, 5);
                        it.setEnd(_position_1);
                      }
                    };
                    Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function);
                    it.setRange(_doubleArrow);
                    it.setNewText("fixed");
                  }
                };
                TextEdit _doubleArrow = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit, _function);
                it.getChanges().put("file:test1533196529126.lspt", CollectionLiterals.<TextEdit>newArrayList(_doubleArrow));
              }
            };
            WorkspaceEdit _doubleArrow = ObjectExtensions.<WorkspaceEdit>operator_doubleArrow(_workspaceEdit, _function);
            it.setEdit(_doubleArrow);
          }
        };
        CodeAction _doubleArrow = ObjectExtensions.<CodeAction>operator_doubleArrow(_codeAction, _function);
        it.setResult(CollectionLiterals.<Either<Object, CodeAction>>newArrayList(
          Either.<Object, CodeAction>forRight(_doubleArrow)));
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testRenameResponse1() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_RENAME;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"changes\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"file:///tmp/foo\": [");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"character\": 32,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"character\": 35,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"newText\": \"foobar\"");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"character\": 22,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"character\": 25,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"line\": 4");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"newText\": \"foobar\"");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
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
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testRenameResponse3() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_RENAME;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"documentChanges\": [");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"kind\": \"create\",");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"uri\": \"file:/foo.txt\",");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"options\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"overwrite\": true,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"ignoreIfExists\": true");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"kind\": \"delete\",");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"uri\": \"file:/foo.txt\"");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"kind\": \"rename\",");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"oldUri\": \"file:/foo.txt\",");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"newUri\": \"file:/bar.txt\"");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"uri\": \"file:/baz.txt\",");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"version\": 17");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"edits\": [");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("\"character\": 32,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("\"character\": 35,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"newText\": \"asdfqweryxcv\"");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        WorkspaceEdit _workspaceEdit = new WorkspaceEdit();
        final Procedure1<WorkspaceEdit> _function = new Procedure1<WorkspaceEdit>() {
          public void apply(final WorkspaceEdit it) {
            CreateFile _createFile = new CreateFile();
            final Procedure1<CreateFile> _function = new Procedure1<CreateFile>() {
              public void apply(final CreateFile it) {
                it.setUri("file:/foo.txt");
                CreateFileOptions _createFileOptions = new CreateFileOptions();
                final Procedure1<CreateFileOptions> _function = new Procedure1<CreateFileOptions>() {
                  public void apply(final CreateFileOptions it) {
                    it.setOverwrite(Boolean.valueOf(true));
                    it.setIgnoreIfExists(Boolean.valueOf(true));
                  }
                };
                CreateFileOptions _doubleArrow = ObjectExtensions.<CreateFileOptions>operator_doubleArrow(_createFileOptions, _function);
                it.setOptions(_doubleArrow);
              }
            };
            CreateFile _doubleArrow = ObjectExtensions.<CreateFile>operator_doubleArrow(_createFile, _function);
            DeleteFile _deleteFile = new DeleteFile();
            final Procedure1<DeleteFile> _function_1 = new Procedure1<DeleteFile>() {
              public void apply(final DeleteFile it) {
                it.setUri("file:/foo.txt");
              }
            };
            DeleteFile _doubleArrow_1 = ObjectExtensions.<DeleteFile>operator_doubleArrow(_deleteFile, _function_1);
            RenameFile _renameFile = new RenameFile();
            final Procedure1<RenameFile> _function_2 = new Procedure1<RenameFile>() {
              public void apply(final RenameFile it) {
                it.setOldUri("file:/foo.txt");
                it.setNewUri("file:/bar.txt");
              }
            };
            RenameFile _doubleArrow_2 = ObjectExtensions.<RenameFile>operator_doubleArrow(_renameFile, _function_2);
            TextDocumentEdit _textDocumentEdit = new TextDocumentEdit();
            final Procedure1<TextDocumentEdit> _function_3 = new Procedure1<TextDocumentEdit>() {
              public void apply(final TextDocumentEdit it) {
                VersionedTextDocumentIdentifier _versionedTextDocumentIdentifier = new VersionedTextDocumentIdentifier("file:/baz.txt", Integer.valueOf(17));
                it.setTextDocument(_versionedTextDocumentIdentifier);
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
                    it.setNewText("asdfqweryxcv");
                  }
                };
                TextEdit _doubleArrow = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit, _function);
                it.setEdits(CollectionLiterals.<TextEdit>newArrayList(_doubleArrow));
              }
            };
            TextDocumentEdit _doubleArrow_3 = ObjectExtensions.<TextDocumentEdit>operator_doubleArrow(_textDocumentEdit, _function_3);
            it.setDocumentChanges(CollectionLiterals.<Either<TextDocumentEdit, ResourceOperation>>newArrayList(
              Either.<TextDocumentEdit, ResourceOperation>forRight(((ResourceOperation) _doubleArrow)), 
              Either.<TextDocumentEdit, ResourceOperation>forRight(((ResourceOperation) _doubleArrow_1)), 
              Either.<TextDocumentEdit, ResourceOperation>forRight(((ResourceOperation) _doubleArrow_2)), 
              Either.<TextDocumentEdit, ResourceOperation>forLeft(_doubleArrow_3)));
          }
        };
        WorkspaceEdit _doubleArrow = ObjectExtensions.<WorkspaceEdit>operator_doubleArrow(_workspaceEdit, _function);
        it.setResult(_doubleArrow);
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testResponseError() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = "textDocument/rename";
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"error\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"code\": -32600,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"message\": \"Could not parse request.\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
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
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testTelemetry() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"method\": \"telemetry/event\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"foo\": 12.3,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"bar\": \"qwertz\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    NotificationMessage _notificationMessage = new NotificationMessage();
    final Procedure1<NotificationMessage> _function = new Procedure1<NotificationMessage>() {
      public void apply(final NotificationMessage it) {
        it.setJsonrpc("2.0");
        it.setMethod(MessageMethods.TELEMETRY_EVENT);
        Pair<String, Double> _mappedTo = Pair.<String, Double>of("foo", Double.valueOf(12.3));
        Pair<String, String> _mappedTo_1 = Pair.<String, String>of("bar", "qwertz");
        it.setParams(CollectionLiterals.<String, Object>newLinkedHashMap(_mappedTo, _mappedTo_1));
      }
    };
    NotificationMessage _doubleArrow = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testHoverResponse1() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_HOVER;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"character\": 32,");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"character\": 35,");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"contents\": [");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"foo\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"boo shuby doo\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
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
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testHoverResponse2() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_HOVER;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"contents\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"kind\": \"plaintext\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"value\": \"foo\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
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
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testHoverResponse3() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_HOVER;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"contents\": \"foo\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        Hover _hover = new Hover();
        final Procedure1<Hover> _function = new Procedure1<Hover>() {
          public void apply(final Hover it) {
            it.setContents(CollectionLiterals.<Either<String, MarkedString>>newArrayList(Either.<String, MarkedString>forLeft("foo")));
          }
        };
        Hover _doubleArrow = ObjectExtensions.<Hover>operator_doubleArrow(_hover, _function);
        it.setResult(_doubleArrow);
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testHoverResponse4() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_HOVER;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"contents\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"language\": \"plaintext\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"value\": \"foo\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        Hover _hover = new Hover();
        final Procedure1<Hover> _function = new Procedure1<Hover>() {
          public void apply(final Hover it) {
            MarkedString _markedString = new MarkedString();
            final Procedure1<MarkedString> _function = new Procedure1<MarkedString>() {
              public void apply(final MarkedString it) {
                it.setLanguage("plaintext");
                it.setValue("foo");
              }
            };
            MarkedString _doubleArrow = ObjectExtensions.<MarkedString>operator_doubleArrow(_markedString, _function);
            it.setContents(CollectionLiterals.<Either<String, MarkedString>>newArrayList(Either.<String, MarkedString>forRight(_doubleArrow)));
          }
        };
        Hover _doubleArrow = ObjectExtensions.<Hover>operator_doubleArrow(_hover, _function);
        it.setResult(_doubleArrow);
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testCodeLensResponse() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"character\": 32,");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"character\": 35,");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"command\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"title\": \"save\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"command\": \"saveCommand\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"arguments\": [");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"uri\": \"file:/foo\",");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"version\": 5");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"data\": [");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("42,");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"qwert\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"key\": \"value\"");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    final String json = _builder.toString();
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_CODE_LENS;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    final Message message = this.jsonHandler.parseMessage(json);
    Assert.assertTrue("Expected a ResponseMessage", (message instanceof ResponseMessage));
    final ResponseMessage response = ((ResponseMessage) message);
    Object _result = response.getResult();
    Assert.assertTrue("Expected a Collection in result", (_result instanceof Collection<?>));
    Object _result_1 = response.getResult();
    final Collection<?> result = ((Collection<?>) _result_1);
    Object _head = IterableExtensions.head(result);
    Assert.assertTrue("Expected a CodeLens in result[0]", (_head instanceof CodeLens));
    Object _head_1 = IterableExtensions.head(result);
    final CodeLens codeLens = ((CodeLens) _head_1);
    Assert.assertNotNull(codeLens.getCommand());
    final Object argument = IterableExtensions.<Object>head(codeLens.getCommand().getArguments());
    Assert.assertTrue("Expected a JsonObject in command.arguments[0]", (argument instanceof JsonObject));
    Assert.assertEquals("file:/foo", ((JsonObject) argument).get("uri").getAsString());
    Assert.assertEquals(5, ((JsonObject) argument).get("version").getAsInt());
    Object _data = codeLens.getData();
    Assert.assertTrue("Expected a JsonArray in data", (_data instanceof JsonArray));
    Object _data_1 = codeLens.getData();
    final JsonArray data = ((JsonArray) _data_1);
    Assert.assertEquals(42, data.get(0).getAsInt());
    Assert.assertEquals("qwert", data.get(1).getAsString());
    JsonElement _get = data.get(2);
    Assert.assertTrue("Expected a JsonObject in data[2]", (_get instanceof JsonObject));
    JsonElement _get_1 = data.get(2);
    Assert.assertEquals("value", ((JsonObject) _get_1).get("key").getAsString());
  }
  
  @Test
  public void testDeclarationResponse() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_DECLARATION;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": [");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"uri\": \"foo\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 7,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 12");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 8,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 16");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        Position _position = new Position(7, 12);
        Position _position_1 = new Position(8, 16);
        Range _range = new Range(_position, _position_1);
        Location _location = new Location("foo", _range);
        it.setResult(Either.<List<? extends Location>, List<? extends LocationLink>>forLeft(
          Collections.<Location>unmodifiableList(CollectionLiterals.<Location>newArrayList(_location))));
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testDefinitionResponse1() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_DEFINITION;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": [");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"targetUri\": \"foo\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"targetRange\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 7,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 12");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 8,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 16");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        LocationLink _locationLink = new LocationLink();
        final Procedure1<LocationLink> _function = new Procedure1<LocationLink>() {
          public void apply(final LocationLink it) {
            it.setTargetUri("foo");
            Position _position = new Position(7, 12);
            Position _position_1 = new Position(8, 16);
            Range _range = new Range(_position, _position_1);
            it.setTargetRange(_range);
          }
        };
        LocationLink _doubleArrow = ObjectExtensions.<LocationLink>operator_doubleArrow(_locationLink, _function);
        it.setResult(Either.<List<? extends Location>, List<? extends LocationLink>>forRight(
          Collections.<LocationLink>unmodifiableList(CollectionLiterals.<LocationLink>newArrayList(_doubleArrow))));
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testDefinitionResponse2() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_DEFINITION;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": []");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        it.setResult(Either.<List<? extends Location>, List<? extends LocationLink>>forLeft(CollectionLiterals.<Location>emptyList()));
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testTypeDefinitionResponse() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_TYPE_DEFINITION;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": [");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"uri\": \"foo\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 7,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 12");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 8,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 16");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        Position _position = new Position(7, 12);
        Position _position_1 = new Position(8, 16);
        Range _range = new Range(_position, _position_1);
        Location _location = new Location("foo", _range);
        it.setResult(Either.<List<? extends Location>, List<? extends LocationLink>>forLeft(
          Collections.<Location>unmodifiableList(CollectionLiterals.<Location>newArrayList(_location))));
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testImplementationResponse() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_IMPLEMENTATION;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": [");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"targetUri\": \"foo\",");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"targetRange\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 7,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 12");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"line\": 8,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"character\": 16");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        LocationLink _locationLink = new LocationLink();
        final Procedure1<LocationLink> _function = new Procedure1<LocationLink>() {
          public void apply(final LocationLink it) {
            it.setTargetUri("foo");
            Position _position = new Position(7, 12);
            Position _position_1 = new Position(8, 16);
            Range _range = new Range(_position, _position_1);
            it.setTargetRange(_range);
          }
        };
        LocationLink _doubleArrow = ObjectExtensions.<LocationLink>operator_doubleArrow(_locationLink, _function);
        it.setResult(Either.<List<? extends Location>, List<? extends LocationLink>>forRight(
          Collections.<LocationLink>unmodifiableList(CollectionLiterals.<LocationLink>newArrayList(_doubleArrow))));
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testSignatureHelpResponse() {
    final MethodProvider _function = new MethodProvider() {
      public String resolveMethod(final String id) {
        String _switchResult = null;
        boolean _matched = false;
        if (Objects.equal(id, "12")) {
          _matched=true;
          _switchResult = MessageMethods.DOC_SIGNATURE_HELP;
        }
        return _switchResult;
      }
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"signatures\": [");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"label\": \"Foo\",");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"parameters\": [");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"label\": \"label1\"");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"label\": [12, 25]");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("]");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = new Procedure1<ResponseMessage>() {
      public void apply(final ResponseMessage it) {
        it.setJsonrpc("2.0");
        it.setId("12");
        SignatureHelp _signatureHelp = new SignatureHelp();
        final Procedure1<SignatureHelp> _function = new Procedure1<SignatureHelp>() {
          public void apply(final SignatureHelp it) {
            SignatureInformation _signatureInformation = new SignatureInformation();
            final Procedure1<SignatureInformation> _function = new Procedure1<SignatureInformation>() {
              public void apply(final SignatureInformation it) {
                it.setLabel("Foo");
                ParameterInformation _parameterInformation = new ParameterInformation();
                final Procedure1<ParameterInformation> _function = new Procedure1<ParameterInformation>() {
                  public void apply(final ParameterInformation it) {
                    it.setLabel(Either.<String, Tuple.Two<Integer, Integer>>forLeft("label1"));
                  }
                };
                ParameterInformation _doubleArrow = ObjectExtensions.<ParameterInformation>operator_doubleArrow(_parameterInformation, _function);
                ParameterInformation _parameterInformation_1 = new ParameterInformation();
                final Procedure1<ParameterInformation> _function_1 = new Procedure1<ParameterInformation>() {
                  public void apply(final ParameterInformation it) {
                    it.setLabel(Either.<String, Tuple.Two<Integer, Integer>>forRight(Tuple.<Integer, Integer>two(Integer.valueOf(12), Integer.valueOf(25))));
                  }
                };
                ParameterInformation _doubleArrow_1 = ObjectExtensions.<ParameterInformation>operator_doubleArrow(_parameterInformation_1, _function_1);
                it.setParameters(Collections.<ParameterInformation>unmodifiableList(CollectionLiterals.<ParameterInformation>newArrayList(_doubleArrow, _doubleArrow_1)));
              }
            };
            SignatureInformation _doubleArrow = ObjectExtensions.<SignatureInformation>operator_doubleArrow(_signatureInformation, _function);
            it.setSignatures(Collections.<SignatureInformation>unmodifiableList(CollectionLiterals.<SignatureInformation>newArrayList(_doubleArrow)));
          }
        };
        SignatureHelp _doubleArrow = ObjectExtensions.<SignatureHelp>operator_doubleArrow(_signatureHelp, _function);
        it.setResult(_doubleArrow);
      }
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testDocumentFormatting() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"method\": \"textDocument/formatting\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"uri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"options\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"insertSpaces\": false,");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"tabSize\": 4,");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"customProperty\": -7");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
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
                it.setInsertSpaces(false);
                JsonParseTest.MyInteger _myInteger = new JsonParseTest.MyInteger(4);
                it.putNumber("tabSize", _myInteger);
                JsonParseTest.MyInteger _myInteger_1 = new JsonParseTest.MyInteger((-7));
                it.putNumber("customProperty", _myInteger_1);
              }
            };
            FormattingOptions _doubleArrow = ObjectExtensions.<FormattingOptions>operator_doubleArrow(_formattingOptions, _function);
            it.setOptions(_doubleArrow);
          }
        };
        DocumentFormattingParams _doubleArrow = ObjectExtensions.<DocumentFormattingParams>operator_doubleArrow(_documentFormattingParams, _function);
        it.setParams(_doubleArrow);
      }
    };
    RequestMessage _doubleArrow = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testMessageIssue() {
    Gson _gson = this.jsonHandler.getGson();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"text\": \"Howdy!\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"code\": 1234,");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"cause\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"message\": \"Foo\",");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"cause\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"message\": \"Bar\"");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    final MessageIssue issue = _gson.<MessageIssue>fromJson(_builder.toString(), MessageIssue.class);
    Assert.assertEquals("Howdy!", issue.getText());
    Assert.assertEquals(1234, issue.getIssueCode());
    Assert.assertEquals("Foo", issue.getCause().getMessage());
    Assert.assertEquals("Bar", issue.getCause().getCause().getMessage());
  }
  
  @Test
  public void testInitialize() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"1\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"method\": \"initialize\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"rootUri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
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
    RequestMessage _doubleArrow = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testInitializeClientCapabilities() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"id\": \"1\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"method\": \"initialize\",");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"rootUri\": \"file:///tmp/foo\",");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("\"capabilities\": {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"synchronization\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"willSave\": true,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"willSaveWaitUntil\": false,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"didSave\": true");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"completion\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"completionItem\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"snippetSupport\": true,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"commitCharactersSupport\": true,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"documentationFormat\": [\"plaintext\", \"markdown\"]");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"completionItemKind\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"valueSet\": [2, 3]");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"contextSupport\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"hover\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"contentFormat\": [\"plaintext\", \"markdown\"]");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"signatureHelp\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"signatureInformation\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("\"documentationFormat\": [\"plaintext\", \"markdown\"]");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"references\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"documentHighlight\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"documentSymbol\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false,");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"symbolKind\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t");
    _builder.append("valueSet: [2, 3, 4, 5]");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"formatting\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"rangeFormatting\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"onTypeFormatting\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"definition\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"typeDefinition\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"implementation\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"codeAction\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"codeLens\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"documentLink\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"colorProvider\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("\"rename\": {");
    _builder.newLine();
    _builder.append("\t\t\t\t\t");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("},");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("\"workspace\": {}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
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
    RequestMessage _doubleArrow = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
}
