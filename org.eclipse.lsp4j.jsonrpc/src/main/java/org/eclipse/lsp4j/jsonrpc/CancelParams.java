package org.eclipse.lsp4j.jsonrpc;

import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;

public class CancelParams {
	
	public final static JsonRpcMethod METHOD_TYPE = JsonRpcMethod.notification("$/cancel", CancelParams.class);
	
	private String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
