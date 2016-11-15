package org.eclipse.lsp4j.services.test;

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
