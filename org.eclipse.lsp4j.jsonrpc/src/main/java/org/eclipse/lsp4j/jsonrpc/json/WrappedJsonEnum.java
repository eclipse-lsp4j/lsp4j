package org.eclipse.lsp4j.jsonrpc.json;

/**
 * implementers are expected to provide a static factory method of the form
 * <codeforValue(int value)</code>
 */
public interface WrappedJsonEnum {
	
	int getValue();
}
