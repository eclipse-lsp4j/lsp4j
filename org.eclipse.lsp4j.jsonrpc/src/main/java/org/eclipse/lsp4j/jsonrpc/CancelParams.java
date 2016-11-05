package org.eclipse.lsp4j.jsonrpc;

public class CancelParams {
	
	public final static RpcMethod METHOD_TYPE = RpcMethod.notification("$/cancel", CancelParams.class);
	
	private String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
