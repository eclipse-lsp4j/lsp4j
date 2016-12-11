package org.eclipse.lsp4j.jsonrpc.json;

import com.google.gson.JsonElement;

public class Either<L, R> implements WrappedJson {
	
	private WrappedJsonConverter<L> leftConverter;
	private WrappedJsonConverter<R> rightConverter;
	private JsonElement wrapped;
	
	public Either(WrappedJsonConverter<L> leftConverter2, WrappedJsonConverter<R> rightConverter2) {
		leftConverter = leftConverter2;
		rightConverter = rightConverter2;
	}

	public void setLeft(L left) {
		this.wrapped = leftConverter.toJson(left);
	}
	
	public void setRight(R right) {
		this.wrapped = rightConverter.toJson(right);
	}
	
	public boolean isLeft() {
		return wrapped != null && leftConverter.isCompatible(wrapped); 
	}

	public boolean isRight() { 
		return wrapped != null && rightConverter.isCompatible(wrapped); 
	}

	public L getLeft() {
		if (wrapped == null) {
			return null;
		}
		return leftConverter.fromJson(wrapped);
	}

	public R getRight() { 
		if (wrapped == null) {
			return null;
		}
		return rightConverter.fromJson(wrapped);
	}
	
	@Override
	public JsonElement jsonElement() {
		return wrapped;
	}
	
	public void setWrapped(JsonElement wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public String toString() {
		return wrapped.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof WrappedJson) {
			JsonElement otherWrapped = ((WrappedJson) obj).jsonElement();
			if (this.wrapped == otherWrapped) {
				return true;
			}
			if (this.wrapped == null) {
				return false;
			}
			return this.wrapped.equals(otherWrapped);
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (this.wrapped != null) {
			return wrapped.hashCode();
		}
		return -1;
	}
}
