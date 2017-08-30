/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.test.services;

import java.util.Map;

import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.junit.Assert;
import org.junit.Test;

public class RpcMethodTest {

	@Test public void testDocumentSymbol() {
		Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(TextDocumentService.class);
		JsonRpcMethod jsonRpcMethod = methods.get("textDocument/documentSymbol");
		Assert.assertNotNull(jsonRpcMethod);
	}
	
	@Test public void testCodelensResolve() {
		Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(TextDocumentService.class);
		Assert.assertNotNull(methods.get("codeLens/resolve"));
		Assert.assertNotNull(methods.get("completionItem/resolve"));
	}
}
