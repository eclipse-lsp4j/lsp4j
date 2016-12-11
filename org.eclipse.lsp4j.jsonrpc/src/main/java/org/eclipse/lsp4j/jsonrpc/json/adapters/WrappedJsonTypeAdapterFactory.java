package org.eclipse.lsp4j.jsonrpc.json.adapters;

import java.io.IOException;

import org.eclipse.lsp4j.jsonrpc.json.WrappedJson;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class WrappedJsonTypeAdapterFactory implements TypeAdapterFactory {
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		if (!WrappedJson.class.isAssignableFrom(type.getRawType())) {
			return null;
		}
		return (TypeAdapter<T>) new WrappedJsonTypeAdapter<WrappedJson>(gson, (TypeToken<WrappedJson>) type);
	}

	static class WrappedJsonTypeAdapter<T extends WrappedJson> extends TypeAdapter<T> {

		private Gson gson;
		private TypeToken<T> type;

		public WrappedJsonTypeAdapter(Gson gson, TypeToken<T> type) {
			this.gson = gson;
			this.type = type;
		}

		@Override
		public void write(JsonWriter out, T wrappedJson) throws IOException {
			JsonElement jsonElement = wrappedJson.jsonElement();
			gson.toJson(jsonElement, out);
		}

		@SuppressWarnings("unchecked")
		@Override
		public T read(JsonReader in) throws IOException {
			JsonElement jsonElement = (JsonElement) gson.fromJson(in, JsonElement.class);
			return (T) WrappedJsonConverter.getConverter(type.getType()).fromJson(jsonElement);
		}
	
	}
}
