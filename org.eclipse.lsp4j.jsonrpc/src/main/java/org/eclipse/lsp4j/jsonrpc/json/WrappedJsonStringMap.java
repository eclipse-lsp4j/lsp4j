package org.eclipse.lsp4j.jsonrpc.json;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class WrappedJsonStringMap<T> implements Map<String, T>, WrappedJson {
	
	private JsonObject wrapped;
	private WrappedJsonConverter<T> converter;
	
	public WrappedJsonStringMap(Class<T> type) {
		this(new JsonObject(), WrappedJsonConverter.getConverter(type));
	}
	
	public WrappedJsonStringMap(TypeToken<T> type) {
		this(new JsonObject(), WrappedJsonConverter.getConverter(type.getType()));
	}
	
	public WrappedJsonStringMap(WrappedJsonConverter<T> converter) {
		this(new JsonObject(), converter);
	}
	
	public WrappedJsonStringMap(JsonObject wrapped, WrappedJsonConverter<T> converter) {
		this.wrapped = wrapped;
		this.converter = converter;
	}

	@Override
	public int size() {
		return wrapped.entrySet().size();
	}

	@Override
	public boolean isEmpty() {
		return wrapped.entrySet().isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return wrapped.has((String)key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean containsValue(Object value) {
		return wrapped.entrySet().stream().anyMatch( entry -> entry.getValue().equals(converter.toJson((T)value)));
	}

	@Override
	public T get(Object key) {
		return converter.fromJson(wrapped.get((String)key));
	}

	@Override
	public T put(String key, T value) {
		JsonElement oldElement = wrapped.get(key);
		wrapped.add(key, converter.toJson(value));
		if (oldElement == null) {
			return null;
		}
		return converter.fromJson(oldElement);
	}

	@Override
	public T remove(Object key) {
		JsonElement oldElement = wrapped.remove((String)key);
		if (oldElement == null) {
			return null;
		}
		return converter.fromJson(oldElement);
	}

	@Override
	public void putAll(Map<? extends String, ? extends T> m) {
		for (java.util.Map.Entry<? extends String, ? extends T> entry : m.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		for (java.util.Map.Entry<String, JsonElement> entry : wrapped.entrySet()) {
			wrapped.remove(entry.getKey());
		}
	}

	@Override
	public Set<String> keySet() {
		return wrapped.entrySet().stream().map(entry -> entry.getKey()).collect(Collectors.toSet());
	}

	@Override
	public Collection<T> values() {
		return wrapped.entrySet().stream().map(entry -> converter.fromJson(entry.getValue())).collect(Collectors.toList());
	}

	@Override
	public Set<java.util.Map.Entry<String, T>> entrySet() {
		return wrapped.entrySet().stream().map(entry -> {
			return new AbstractMap.SimpleEntry<String, T>(entry.getKey(), converter.fromJson(entry.getValue()));
		}).collect(Collectors.toSet());
	}

	@Override
	public JsonElement jsonElement() {
		return wrapped;
	}

	@Override
	public String toString() {
		return wrapped.toString();
	}
}
