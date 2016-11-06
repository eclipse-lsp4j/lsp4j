package org.eclipse.lsp4j.jsonrpc.json;

import java.util.Map;

import org.eclipse.lsp4j.jsonrpc.Endpoint;

/**
 * Provides {@link JsonRpcMethod}. Can be implemented by {@link Endpoint}s to
 * provide information about the supported methods.
 */
public interface JsonRpcMethodProvider {

	public Map<String, JsonRpcMethod> supportedMethods();
}
