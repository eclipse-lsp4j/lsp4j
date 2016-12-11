package org.eclipse.lsp4j;

import java.util.List;

import org.eclipse.lsp4j.jsonrpc.json.Either;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJson;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonList;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class WrappingFoo extends WrappedJsonObject {
	JsonObject wrapped;
	
	public String getFoo() {
		JsonElement element = wrapped.get("foo");
		return WrappedJsonConverter.stringConverter.fromJson(element);
	}
	
	public void setFoo(String s) {
		JsonElement json = WrappedJsonConverter.stringConverter.toJson(s);
		wrapped.add("foo", json);
	}
	
	public String removeFoo() {
		return WrappedJsonConverter.stringConverter.fromJson(wrapped.remove("foo"));
	}
	
	WrappedJsonConverter<Either<String, List<String>>> foosConverter = WrappedJsonConverter.eitherConverter(WrappedJsonConverter.stringConverter, WrappedJsonConverter.listConverter(WrappedJsonConverter.stringConverter));
	
	public Either<String, List<String>> getFoos() {
		JsonElement element = wrapped.get("foos");
		return foosConverter.fromJson(element);
	}
	
	public void setFoos(Either<String,List<String>> s) {
		JsonElement json = foosConverter.toJson(s);
		wrapped.add("foos", json);
	}
	
	public Either<String,List<String>> removeFoos() {
		JsonElement removed = wrapped.remove("foos");
		return foosConverter.fromJson(removed);
	}
	
	WrappedJsonConverter<List<WrappingFoo>> wrappingfoosConverter = WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(WrappingFoo.class));
	
	public List<WrappingFoo> getWrappingFoos() {
		JsonElement element = wrapped.get("foos");
		return wrappingfoosConverter.fromJson(element);
	}
	
	public void setWrappingFoos(List<WrappingFoo> s) {
		JsonElement json = wrappingfoosConverter.toJson(s);
		wrapped.add("foos", json);
	}
	
	public List<WrappingFoo> removeWrappingFoos() {
		JsonElement removed = wrapped.remove("foos");
		return wrappingfoosConverter.fromJson(removed);
	}

	@Override
	public JsonElement jsonElement() {
		return wrapped;
	}
}


