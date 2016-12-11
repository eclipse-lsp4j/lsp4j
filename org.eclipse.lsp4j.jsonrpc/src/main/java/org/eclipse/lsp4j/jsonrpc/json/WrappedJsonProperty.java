package org.eclipse.lsp4j.jsonrpc.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class WrappedJsonProperty<T> {
	
	private String property;
	private WrappedJsonConverter<T> converter;
	
	public WrappedJsonProperty(String property, TypeToken<T> type) {
		this(property, WrappedJsonConverter.getConverter(type.getType()));
	}
	
	public WrappedJsonProperty(String property, Class<T> type) {
		this(property, WrappedJsonConverter.getConverter(type));
	}
	
	public WrappedJsonProperty(String property, WrappedJsonConverter<T> converter) {
		super();
		this.property = property;
		this.converter = converter;
	}
	
	public T get(JsonObject object) {
		JsonElement element = object.get(property);
		return converter.fromJson(element);
	}
	
	public void set(JsonObject object, T t) {
		JsonElement jsonElement = converter.toJson(t);
		object.add(property, jsonElement);
	}
	
	public T remove(JsonObject object) {
		JsonElement removed = object.remove(property);
		return converter.fromJson(removed);
	}
	
}
