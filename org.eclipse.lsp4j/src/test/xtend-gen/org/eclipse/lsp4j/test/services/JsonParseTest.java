/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.test.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.FormattingOptions;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.json.MethodProvider;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.test.services.MessageMethods;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("all")
public class JsonParseTest {
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
    Assert.assertEquals(expected.toString(), this.jsonHandler.parseMessage(json).toString());
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
    final Procedure1<RequestMessage> _function = (RequestMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("1");
      it.setMethod(MessageMethods.DOC_COMPLETION);
      TextDocumentPositionParams _textDocumentPositionParams = new TextDocumentPositionParams();
      final Procedure1<TextDocumentPositionParams> _function_1 = (TextDocumentPositionParams it_1) -> {
        TextDocumentIdentifier _textDocumentIdentifier = new TextDocumentIdentifier();
        final Procedure1<TextDocumentIdentifier> _function_2 = (TextDocumentIdentifier it_2) -> {
          it_2.setUri("file:///tmp/foo");
        };
        TextDocumentIdentifier _doubleArrow = ObjectExtensions.<TextDocumentIdentifier>operator_doubleArrow(_textDocumentIdentifier, _function_2);
        it_1.setTextDocument(_doubleArrow);
        Position _position = new Position();
        final Procedure1<Position> _function_3 = (Position it_2) -> {
          it_2.setLine(4);
          it_2.setCharacter(22);
        };
        Position _doubleArrow_1 = ObjectExtensions.<Position>operator_doubleArrow(_position, _function_3);
        it_1.setPosition(_doubleArrow_1);
      };
      TextDocumentPositionParams _doubleArrow = ObjectExtensions.<TextDocumentPositionParams>operator_doubleArrow(_textDocumentPositionParams, _function_1);
      it.setParams(_doubleArrow);
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
    _builder.append("\"uri\": \"file:///tmp/foo\"");
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
    final Procedure1<NotificationMessage> _function = (NotificationMessage it) -> {
      it.setJsonrpc("2.0");
      it.setMethod(MessageMethods.DID_CHANGE_DOC);
      DidChangeTextDocumentParams _didChangeTextDocumentParams = new DidChangeTextDocumentParams();
      final Procedure1<DidChangeTextDocumentParams> _function_1 = (DidChangeTextDocumentParams it_1) -> {
        VersionedTextDocumentIdentifier _versionedTextDocumentIdentifier = new VersionedTextDocumentIdentifier();
        final Procedure1<VersionedTextDocumentIdentifier> _function_2 = (VersionedTextDocumentIdentifier it_2) -> {
          it_2.setUri("file:///tmp/foo");
        };
        VersionedTextDocumentIdentifier _doubleArrow = ObjectExtensions.<VersionedTextDocumentIdentifier>operator_doubleArrow(_versionedTextDocumentIdentifier, _function_2);
        it_1.setTextDocument(_doubleArrow);
        ArrayList<TextDocumentContentChangeEvent> _arrayList = new ArrayList<TextDocumentContentChangeEvent>();
        final Procedure1<ArrayList<TextDocumentContentChangeEvent>> _function_3 = (ArrayList<TextDocumentContentChangeEvent> it_2) -> {
          TextDocumentContentChangeEvent _textDocumentContentChangeEvent = new TextDocumentContentChangeEvent();
          final Procedure1<TextDocumentContentChangeEvent> _function_4 = (TextDocumentContentChangeEvent it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_5 = (Range it_4) -> {
              Position _position = new Position(7, 12);
              it_4.setStart(_position);
              Position _position_1 = new Position(8, 16);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow_1 = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_5);
            it_3.setRange(_doubleArrow_1);
            it_3.setRangeLength(Integer.valueOf(20));
            it_3.setText("bar");
          };
          TextDocumentContentChangeEvent _doubleArrow_1 = ObjectExtensions.<TextDocumentContentChangeEvent>operator_doubleArrow(_textDocumentContentChangeEvent, _function_4);
          it_2.add(_doubleArrow_1);
        };
        ArrayList<TextDocumentContentChangeEvent> _doubleArrow_1 = ObjectExtensions.<ArrayList<TextDocumentContentChangeEvent>>operator_doubleArrow(_arrayList, _function_3);
        it_1.setContentChanges(_doubleArrow_1);
      };
      DidChangeTextDocumentParams _doubleArrow = ObjectExtensions.<DidChangeTextDocumentParams>operator_doubleArrow(_didChangeTextDocumentParams, _function_1);
      it.setParams(_doubleArrow);
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
    final Procedure1<NotificationMessage> _function = (NotificationMessage it) -> {
      it.setJsonrpc("2.0");
      it.setMethod(MessageMethods.SHOW_DIAGNOSTICS);
      PublishDiagnosticsParams _publishDiagnosticsParams = new PublishDiagnosticsParams();
      final Procedure1<PublishDiagnosticsParams> _function_1 = (PublishDiagnosticsParams it_1) -> {
        it_1.setUri("file:///tmp/foo");
        ArrayList<Diagnostic> _arrayList = new ArrayList<Diagnostic>();
        final Procedure1<ArrayList<Diagnostic>> _function_2 = (ArrayList<Diagnostic> it_2) -> {
          Diagnostic _diagnostic = new Diagnostic();
          final Procedure1<Diagnostic> _function_3 = (Diagnostic it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_4 = (Range it_4) -> {
              Position _position = new Position(4, 22);
              it_4.setStart(_position);
              Position _position_1 = new Position(4, 25);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_4);
            it_3.setRange(_doubleArrow);
            it_3.setSeverity(DiagnosticSeverity.Error);
            it_3.setMessage("Couldn\'t resolve reference to State \'bar\'.");
          };
          Diagnostic _doubleArrow = ObjectExtensions.<Diagnostic>operator_doubleArrow(_diagnostic, _function_3);
          it_2.add(_doubleArrow);
        };
        ArrayList<Diagnostic> _doubleArrow = ObjectExtensions.<ArrayList<Diagnostic>>operator_doubleArrow(_arrayList, _function_2);
        it_1.setDiagnostics(_doubleArrow);
      };
      PublishDiagnosticsParams _doubleArrow = ObjectExtensions.<PublishDiagnosticsParams>operator_doubleArrow(_publishDiagnosticsParams, _function_1);
      it.setParams(_doubleArrow);
    };
    NotificationMessage _doubleArrow = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testRenameResponse() {
    final MethodProvider _function = (String id) -> {
      String _switchResult = null;
      if (id != null) {
        switch (id) {
          case "12":
            _switchResult = MessageMethods.DOC_RENAME;
            break;
        }
      }
      return _switchResult;
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
    final Procedure1<ResponseMessage> _function_1 = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      WorkspaceEdit _workspaceEdit = new WorkspaceEdit();
      final Procedure1<WorkspaceEdit> _function_2 = (WorkspaceEdit it_1) -> {
        HashMap<String, List<TextEdit>> _hashMap = new HashMap<String, List<TextEdit>>();
        final Procedure1<HashMap<String, List<TextEdit>>> _function_3 = (HashMap<String, List<TextEdit>> it_2) -> {
          TextEdit _textEdit = new TextEdit();
          final Procedure1<TextEdit> _function_4 = (TextEdit it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_5 = (Range it_4) -> {
              Position _position = new Position(3, 32);
              it_4.setStart(_position);
              Position _position_1 = new Position(3, 35);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_5);
            it_3.setRange(_doubleArrow);
            it_3.setNewText("foobar");
          };
          TextEdit _doubleArrow = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit, _function_4);
          TextEdit _textEdit_1 = new TextEdit();
          final Procedure1<TextEdit> _function_5 = (TextEdit it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_6 = (Range it_4) -> {
              Position _position = new Position(4, 22);
              it_4.setStart(_position);
              Position _position_1 = new Position(4, 25);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow_1 = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_6);
            it_3.setRange(_doubleArrow_1);
            it_3.setNewText("foobar");
          };
          TextEdit _doubleArrow_1 = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit_1, _function_5);
          it_2.put("file:///tmp/foo", CollectionLiterals.<TextEdit>newArrayList(_doubleArrow, _doubleArrow_1));
        };
        HashMap<String, List<TextEdit>> _doubleArrow = ObjectExtensions.<HashMap<String, List<TextEdit>>>operator_doubleArrow(_hashMap, _function_3);
        it_1.setChanges(_doubleArrow);
      };
      WorkspaceEdit _doubleArrow = ObjectExtensions.<WorkspaceEdit>operator_doubleArrow(_workspaceEdit, _function_2);
      it.setResult(_doubleArrow);
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testResponseError() {
    final MethodProvider _function = (String id) -> {
      String _switchResult = null;
      if (id != null) {
        switch (id) {
          case "12":
            _switchResult = "textDocument/rename";
            break;
        }
      }
      return _switchResult;
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
    _builder.append("code = -32600,");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("message = \"Could not parse request.\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      ResponseError _responseError = new ResponseError();
      final Procedure1<ResponseError> _function_2 = (ResponseError it_1) -> {
        it_1.setCode(ResponseErrorCode.InvalidRequest);
        it_1.setMessage("Could not parse request.");
      };
      ResponseError _doubleArrow = ObjectExtensions.<ResponseError>operator_doubleArrow(_responseError, _function_2);
      it.setError(_doubleArrow);
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
    final Procedure1<NotificationMessage> _function = (NotificationMessage it) -> {
      it.setJsonrpc("2.0");
      it.setMethod(MessageMethods.TELEMETRY_EVENT);
      Pair<String, Double> _mappedTo = Pair.<String, Double>of("foo", Double.valueOf(12.3));
      Pair<String, String> _mappedTo_1 = Pair.<String, String>of("bar", "qwertz");
      it.setParams(CollectionLiterals.<String, Object>newLinkedHashMap(_mappedTo, _mappedTo_1));
    };
    NotificationMessage _doubleArrow = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testHoverResponse() {
    final MethodProvider _function = (String id) -> {
      String _switchResult = null;
      if (id != null) {
        switch (id) {
          case "12":
            _switchResult = MessageMethods.DOC_HOVER;
            break;
        }
      }
      return _switchResult;
    };
    this.jsonHandler.setMethodProvider(_function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("\"character\": 32,");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("\"character\": 35,");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("\"line\": 3");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"contents\": [");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\'foo\',");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\'boo shuby doo\'");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function_1 = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      Hover _hover = new Hover();
      final Procedure1<Hover> _function_2 = (Hover it_1) -> {
        Range _range = new Range();
        final Procedure1<Range> _function_3 = (Range it_2) -> {
          Position _position = new Position(3, 32);
          it_2.setStart(_position);
          Position _position_1 = new Position(3, 35);
          it_2.setEnd(_position_1);
        };
        Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_3);
        it_1.setRange(_doubleArrow);
        it_1.setContents(CollectionLiterals.<Either<String, MarkedString>>newArrayList(
          Either.<String, MarkedString>forLeft("foo"), 
          Either.<String, MarkedString>forLeft("boo shuby doo")));
      };
      Hover _doubleArrow = ObjectExtensions.<Hover>operator_doubleArrow(_hover, _function_2);
      it.setResult(_doubleArrow);
    };
    ResponseMessage _doubleArrow = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function_1);
    this.assertParse(_builder, _doubleArrow);
  }
  
  @Test
  public void testDocumentFormatting() {
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
    _builder.append("\"insertSpaces\": false");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = (RequestMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      it.setMethod(MessageMethods.DOC_FORMATTING);
      DocumentFormattingParams _documentFormattingParams = new DocumentFormattingParams();
      final Procedure1<DocumentFormattingParams> _function_1 = (DocumentFormattingParams it_1) -> {
        TextDocumentIdentifier _textDocumentIdentifier = new TextDocumentIdentifier("file:///tmp/foo");
        it_1.setTextDocument(_textDocumentIdentifier);
        FormattingOptions _formattingOptions = new FormattingOptions();
        final Procedure1<FormattingOptions> _function_2 = (FormattingOptions it_2) -> {
          it_2.setTabSize(4);
          it_2.setInsertSpaces(false);
        };
        FormattingOptions _doubleArrow = ObjectExtensions.<FormattingOptions>operator_doubleArrow(_formattingOptions, _function_2);
        it_1.setOptions(_doubleArrow);
      };
      DocumentFormattingParams _doubleArrow = ObjectExtensions.<DocumentFormattingParams>operator_doubleArrow(_documentFormattingParams, _function_1);
      it.setParams(_doubleArrow);
    };
    RequestMessage _doubleArrow = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    this.assertParse(_builder, _doubleArrow);
  }
}
