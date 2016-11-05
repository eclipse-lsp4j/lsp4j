package org.eclipse.lsp4j.jsonrpc.json;

public interface MethodProvider {
	
	/**
	 * returns the method name for a given request id, or null if such request id is known.
	 * 
	 * @param requestId
	 * @return method name or <code>null</code>
	 */
	String resolveMethod(String requestId);
}
