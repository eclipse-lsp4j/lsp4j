package org.eclipse.lsp4j.jsonrpc;

import java.util.Map;

/**
 * Provides {@link RpcMethod}. Can be implemented by {@link Endpoint}s to
 * provide information about the supported methods.
 */
public interface RpcMethodProvider {

	public Map<String, RpcMethod> supportedMethods();
}
