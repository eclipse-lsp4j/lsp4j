/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
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
