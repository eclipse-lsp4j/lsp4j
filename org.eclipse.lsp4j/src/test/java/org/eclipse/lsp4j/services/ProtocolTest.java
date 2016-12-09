package org.eclipse.lsp4j.services;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.DocumentLink;
import org.eclipse.lsp4j.DocumentLinkParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.junit.Assert;
import org.junit.Test;

public class ProtocolTest {

	@Test public void testDocumentLink() throws Exception, ExecutionException {
		LanguageServer languageServer = wrap(LanguageServer.class, new MockLanguageServer() {
			@Override
			public CompletableFuture<List<DocumentLink>> documentLink(DocumentLinkParams params) {
				return CompletableFutures.computeAsync(canceler -> {
					return new ArrayList<>();
				});
			}
		});
		
		List<DocumentLink> list = languageServer.getTextDocumentService().documentLink(new DocumentLinkParams(new TextDocumentIdentifier("test"))).get();
		
		Assert.assertTrue(list.isEmpty());
	}
	
	@Test public void testDocumentLink_01() throws Exception, ExecutionException {
		LanguageServer languageServer = wrap(LanguageServer.class, new MockLanguageServer() {
			@Override
			public CompletableFuture<List<DocumentLink>> documentLink(DocumentLinkParams params) {
				return CompletableFutures.computeAsync(canceler -> {
					return null;
				});
			}
		});
		
		List<DocumentLink> list = languageServer.getTextDocumentService().documentLink(new DocumentLinkParams(new TextDocumentIdentifier("test"))).get();
		
		Assert.assertNull(list);
	}
	
	@Test public void testDocumentResolve() throws Exception, ExecutionException {
		LanguageServer languageServer = wrap(LanguageServer.class, new MockLanguageServer() {
			@Override
			public CompletableFuture<DocumentLink> documentLinkResolve(DocumentLink params) {
				return CompletableFutures.computeAsync(canceler -> {
					params.setTarget("resolved");
					return params;
				});
			}
		});
		
		DocumentLink resolved = languageServer.getTextDocumentService().documentLinkResolve(
				new DocumentLink(new Range(new Position(0, 0), new Position(0, 0)), "unresolved")
		).get();
		
		Assert.assertEquals("resolved", resolved.getTarget());
	}
	
	/**
	 * creates a proxy, delegating to a remote endpoint, forwarding to another remote endpoint, that delegates to an actual implementation.
	 * @param intf
	 * @param impl
	 * @return
	 * @throws IOException 
	 */
	public <T> T wrap(Class<T> intf, T impl) {
		PipedInputStream in1 = new PipedInputStream();
		PipedOutputStream out1 = new PipedOutputStream();
		Launcher<T> launcher1 = Launcher.createLauncher(impl, intf, in1, out1);
		
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();
		Launcher<T> launcher2 = Launcher.createLauncher(new Object(), intf, in2, out2);
		try {
			in1.connect(out2);
			in2.connect(out1);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		launcher1.startListening();
		launcher2.startListening();
		return launcher2.getRemoteProxy();
	}
}
