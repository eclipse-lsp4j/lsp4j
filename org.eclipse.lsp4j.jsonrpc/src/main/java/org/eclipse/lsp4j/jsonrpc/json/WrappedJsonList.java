package org.eclipse.lsp4j.jsonrpc.json;

import java.util.AbstractList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class WrappedJsonList<T> extends AbstractList<T> implements WrappedJson {
	
	private JsonArray delegate;
	private WrappedJsonConverter<T> converter;
	
	public WrappedJsonList(Class<T> type) {
		this(WrappedJsonConverter.getConverter(type));
	}
	
	public WrappedJsonList(TypeToken<T> type) {
		this(WrappedJsonConverter.getConverter(type.getType()));
	}
	
	public WrappedJsonList(WrappedJsonConverter<T> converter) {
		this(new JsonArray(), converter);
	}
	
	public WrappedJsonList(JsonArray delegate, WrappedJsonConverter<T> converter) {
		this.delegate = delegate;
		this.converter = converter;
	}

	@Override
	public int size() {
		return delegate.size();
	}
	
	@Override
	public T get(int index) {
		JsonElement jsonElement = delegate.get(index);
		return converter.fromJson(jsonElement);
	}

	@Override
	public boolean add(T e) {
		if (e instanceof String) {
			delegate.add((String)e);
		} else if (e instanceof Number) {
			delegate.add((Number)e);
		} else if (e instanceof Boolean) {
			delegate.add((Boolean)e);
		} else if (e instanceof WrappedJson) {
			delegate.add(((WrappedJson) e).jsonElement());
		} else {
			throw new IllegalArgumentException("Cannot add "+e+" to the list.");
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		JsonElement json = converter.toJson((T)o);
		return this.delegate.remove(json);
	}

	@Override
	public T set(int index, T element) {
		JsonElement previous = delegate.set(index, converter.toJson(element));
		return converter.fromJson(previous);
	}
	
	@Override
	public JsonElement jsonElement() {
		return delegate;
	}
	
}
