/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.test.services;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.FormattingOptions;
import org.eclipse.lsp4j.Hover;
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
      @Override
      public GsonBuilder getDefaultGsonBuilder() {
        GsonBuilder _defaultGsonBuilder = super.getDefaultGsonBuilder();
        return _defaultGsonBuilder.setPrettyPrinting();
      }
    };
  }
  
  private void assertSerialize(final Message message, final CharSequence expected) {
    String _string = expected.toString();
    String _trim = _string.trim();
    String _serialize = this.jsonHandler.serialize(message);
    String _systemLineEndings = LineEndings.toSystemLineEndings(_serialize);
    Assert.assertEquals(_trim, _systemLineEndings);
  }
  
  @Test
  public void testCompletion() {
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = (RequestMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("1");
      it.setMethod(MessageMethods.DOC_COMPLETION);
      TextDocumentPositionParams _textDocumentPositionParams = new TextDocumentPositionParams();
      final Procedure1<TextDocumentPositionParams> _function_1 = (TextDocumentPositionParams it_1) -> {
        TextDocumentIdentifier _textDocumentIdentifier = new TextDocumentIdentifier("file:///tmp/foo");
        it_1.setTextDocument(_textDocumentIdentifier);
        Position _position = new Position(4, 22);
        it_1.setPosition(_position);
      };
      TextDocumentPositionParams _doubleArrow = ObjectExtensions.<TextDocumentPositionParams>operator_doubleArrow(_textDocumentPositionParams, _function_1);
      it.setParams(_doubleArrow);
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
  public void testDidChange() {
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
  public void testRename() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      WorkspaceEdit _workspaceEdit = new WorkspaceEdit();
      final Procedure1<WorkspaceEdit> _function_1 = (WorkspaceEdit it_1) -> {
        HashMap<String, List<TextEdit>> _hashMap = new HashMap<String, List<TextEdit>>();
        final Procedure1<HashMap<String, List<TextEdit>>> _function_2 = (HashMap<String, List<TextEdit>> it_2) -> {
          TextEdit _textEdit = new TextEdit();
          final Procedure1<TextEdit> _function_3 = (TextEdit it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_4 = (Range it_4) -> {
              Position _position = new Position(3, 32);
              it_4.setStart(_position);
              Position _position_1 = new Position(3, 35);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_4);
            it_3.setRange(_doubleArrow);
            it_3.setNewText("foobar");
          };
          TextEdit _doubleArrow = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit, _function_3);
          TextEdit _textEdit_1 = new TextEdit();
          final Procedure1<TextEdit> _function_4 = (TextEdit it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_5 = (Range it_4) -> {
              Position _position = new Position(4, 22);
              it_4.setStart(_position);
              Position _position_1 = new Position(4, 25);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow_1 = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_5);
            it_3.setRange(_doubleArrow_1);
            it_3.setNewText("foobar");
          };
          TextEdit _doubleArrow_1 = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit_1, _function_4);
          ArrayList<TextEdit> _newArrayList = CollectionLiterals.<TextEdit>newArrayList(_doubleArrow, _doubleArrow_1);
          it_2.put("file:///tmp/foo", _newArrayList);
        };
        HashMap<String, List<TextEdit>> _doubleArrow = ObjectExtensions.<HashMap<String, List<TextEdit>>>operator_doubleArrow(_hashMap, _function_2);
        it_1.setChanges(_doubleArrow);
      };
      WorkspaceEdit _doubleArrow = ObjectExtensions.<WorkspaceEdit>operator_doubleArrow(_workspaceEdit, _function_1);
      it.setResult(_doubleArrow);
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
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      Hover _hover = new Hover();
      final Procedure1<Hover> _function_1 = (Hover it_1) -> {
        Range _range = new Range();
        final Procedure1<Range> _function_2 = (Range it_2) -> {
          Position _position = new Position(3, 32);
          it_2.setStart(_position);
          Position _position_1 = new Position(3, 35);
          it_2.setEnd(_position_1);
        };
        Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_2);
        it_1.setRange(_doubleArrow);
        ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList(
          "foo", 
          "boo shuby doo");
        it_1.setContents(_newArrayList);
      };
      Hover _doubleArrow = ObjectExtensions.<Hover>operator_doubleArrow(_hover, _function_1);
      it.setResult(_doubleArrow);
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
  public void testResponseError() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      ResponseError _responseError = new ResponseError();
      final Procedure1<ResponseError> _function_1 = (ResponseError it_1) -> {
        it_1.setCode(ResponseErrorCode.InvalidRequest);
        it_1.setMessage("Could not parse request.");
      };
      ResponseError _doubleArrow = ObjectExtensions.<ResponseError>operator_doubleArrow(_responseError, _function_1);
      it.setError(_doubleArrow);
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
  public void testBuildCompletionList() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      CompletionList _completionList = new CompletionList();
      final Procedure1<CompletionList> _function_1 = (CompletionList it_1) -> {
        it_1.setIsIncomplete(true);
        CompletionItem _completionItem = new CompletionItem();
        final Procedure1<CompletionItem> _function_2 = (CompletionItem it_2) -> {
          it_2.setLabel("foo");
        };
        CompletionItem _doubleArrow = ObjectExtensions.<CompletionItem>operator_doubleArrow(_completionItem, _function_2);
        it_1.setItems(Collections.<CompletionItem>unmodifiableList(CollectionLiterals.<CompletionItem>newArrayList(_doubleArrow)));
      };
      CompletionList _doubleArrow = ObjectExtensions.<CompletionList>operator_doubleArrow(_completionList, _function_1);
      it.setResult(_doubleArrow);
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
  public void testBuildDocumentFormattingParams() {
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
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testTelemetry() {
    NotificationMessage _notificationMessage = new NotificationMessage();
    final Procedure1<NotificationMessage> _function = (NotificationMessage it) -> {
      it.setJsonrpc("2.0");
      it.setMethod(MessageMethods.TELEMETRY_EVENT);
      JsonSerializeTest.TestObject _testObject = new JsonSerializeTest.TestObject();
      it.setParams(_testObject);
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
}
