package org.eclipse.lsp4j.jsonrpc;

import java.util.concurrent.CompletableFuture;

/**
 * An endpoint is a generic interface to accepts jsonrpc requests and notifications
 */
public interface Endpoint {

	public CompletableFuture<?> request(String method, Object parameter);
	
	public void notify(String method, Object parameter);
	
}
