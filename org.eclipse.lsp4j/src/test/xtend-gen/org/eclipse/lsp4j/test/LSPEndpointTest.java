/**
 * Copyright (c) 2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Future;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class LSPEndpointTest {
  private static class DummyClient implements LanguageClient {
    public void logMessage(final MessageParams message) {
    }
    
    public void publishDiagnostics(final PublishDiagnosticsParams diagnostics) {
    }
    
    public void showMessage(final MessageParams messageParams) {
    }
    
    public CompletableFuture<MessageActionItem> showMessageRequest(final ShowMessageRequestParams requestParams) {
      return null;
    }
    
    public void telemetryEvent(final Object object) {
    }
  }
  
  @Test
  public void testIssue152() throws Exception {
    final LSPEndpointTest.DummyClient client = new LSPEndpointTest.DummyClient();
    final PipedInputStream in = new PipedInputStream();
    final PipedOutputStream responseStream = new PipedOutputStream();
    in.connect(responseStream);
    final OutputStreamWriter responseWriter = new OutputStreamWriter(responseStream);
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final Launcher<LanguageServer> launcher = LSPLauncher.createClientLauncher(client, in, out, true, null);
    final Future<Void> future = launcher.startListening();
    TextDocumentService _textDocumentService = launcher.getRemoteProxy().getTextDocumentService();
    TextDocumentPositionParams _textDocumentPositionParams = new TextDocumentPositionParams();
    final Procedure1<TextDocumentPositionParams> _function = new Procedure1<TextDocumentPositionParams>() {
      public void apply(final TextDocumentPositionParams it) {
        TextDocumentIdentifier _textDocumentIdentifier = new TextDocumentIdentifier("foo");
        it.setTextDocument(_textDocumentIdentifier);
        Position _position = new Position(0, 0);
        it.setPosition(_position);
      }
    };
    TextDocumentPositionParams _doubleArrow = ObjectExtensions.<TextDocumentPositionParams>operator_doubleArrow(_textDocumentPositionParams, _function);
    final CompletableFuture<Hover> hoverResult = _textDocumentService.hover(_doubleArrow);
    responseWriter.write("Content-Length: 60\r\n\r\n");
    responseWriter.write("{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"result\":{\"contents\":[null,null]}}");
    responseWriter.flush();
    try {
      hoverResult.join();
      Assert.fail("Expected a CompletionException to be thrown.");
    } catch (final Throwable _t) {
      if (_t instanceof CompletionException) {
        final CompletionException exception = (CompletionException)_t;
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("Lists must not contain null references. Path: $.result.contents[0]");
        _builder.newLine();
        _builder.append("Lists must not contain null references. Path: $.result.contents[1]");
        _builder.newLine();
        Assert.assertEquals(_builder.toString().trim(), exception.getCause().getMessage());
        Assert.assertFalse(future.isDone());
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    } finally {
      in.close();
    }
  }
}
