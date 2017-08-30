/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.test.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.RemoteEndpoint;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.junit.Assert;
import org.junit.Test;

public class NullResponseTest implements LanguageServer {
	
	private Object shutdownReturn;

	@Test public void testNullResponse() throws InterruptedException, ExecutionException {
		Endpoint endpoint = ServiceEndpoints.toEndpoint(this);
		Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(LanguageServer.class);
		MessageJsonHandler handler = new MessageJsonHandler(methods);
		List<Message> msgs = new ArrayList<>();
		MessageConsumer consumer = (message) -> {
			msgs.add(message);
		};
		RemoteEndpoint re = new RemoteEndpoint(consumer, endpoint);
		
		RequestMessage request = new RequestMessage();
		request.setId("1");
		request.setMethod("shutdown");
		re.consume(request);
		Assert.assertEquals("{\"jsonrpc\":\"2.0\",\"id\":\"1\"}", handler.serialize(msgs.get(0)));
		msgs.clear();
		shutdownReturn = new Object();
		re.consume(request);
		Assert.assertEquals("{\"jsonrpc\":\"2.0\",\"id\":\"1\",\"result\":{}}", handler.serialize(msgs.get(0)));
	}

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		return CompletableFuture.completedFuture(shutdownReturn);
	}

	@Override
	public void exit() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TextDocumentService getTextDocumentService() {
		return null;
	}

	@Override
	public WorkspaceService getWorkspaceService() {
		return null;
	}

}
