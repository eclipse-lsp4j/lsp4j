package org.eclipse.lsp4j.jsonrpc.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class WrappedJsonObject implements WrappedJson {
	
	protected final JsonObject jsonObject;
	
	protected WrappedJsonObject() {
		this.jsonObject = new JsonObject();
	}
	
	protected WrappedJsonObject(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	@Override
	public JsonElement getWrapped() {
		return jsonObject;
	}

	@Override
	public String toString() {
		return jsonObject.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WrappedJsonObject) {
			return jsonObject.equals(((WrappedJsonObject) obj).getWrapped());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return jsonObject.hashCode();
	}
}
