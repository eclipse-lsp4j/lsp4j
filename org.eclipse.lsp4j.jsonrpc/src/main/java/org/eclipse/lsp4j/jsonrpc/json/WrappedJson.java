package org.eclipse.lsp4j.jsonrpc.json;

import com.google.gson.JsonElement;

public interface WrappedJson {

	public JsonElement getWrapped();
}
