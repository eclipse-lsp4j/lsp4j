package org.eclipse.lsp4j.jsonrpc.json.adapters;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.jsonrpc.json.Either;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class EitherTypeAdapterFactory implements TypeAdapterFactory {
	
	private final static Logger LOG = Logger.getLogger(EitherTypeAdapterFactory.class.getName());

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		if (type.getRawType() != Either.class) {
			return null;
		}
		ParameterizedType type2 = (ParameterizedType) type.getType();
		TypeToken<?> leftToken = TypeToken.get(type2.getActualTypeArguments()[0]);
		TypeAdapter<Object> leftAdapter = (TypeAdapter<Object>) gson.getAdapter(leftToken);
		TypeToken<?> rightToken = TypeToken.get(type2.getActualTypeArguments()[1]);
		TypeAdapter<Object> rightAdapter = (TypeAdapter<Object>) gson.getAdapter(rightToken);
		return (TypeAdapter<T>) new EitherTypeAdapter(leftAdapter, rightAdapter);
	}

	static class EitherTypeAdapter extends TypeAdapter<Either<?, ?>> {

		private TypeAdapter<Object> leftAdapter;
		private TypeAdapter<Object> rightAdapter;

		public EitherTypeAdapter(TypeAdapter<Object> leftAdapter, TypeAdapter<Object> rightAdapter) {
			this.leftAdapter = leftAdapter;
			this.rightAdapter = rightAdapter;
		}

		@Override
		public void write(JsonWriter out, Either<?, ?> eitherValue) throws IOException {
			try {
				eitherValue.onLeft(value -> {
					try {
						leftAdapter.write(out, value);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				});
				eitherValue.onRight(value -> {
					try {
						rightAdapter.write(out, value);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				});
			} catch (RuntimeException e) {
				if (e.getCause() instanceof IOException) {
					throw (IOException) e.getCause();
				}
				throw e;
			}
		}

		@Override
		public Either<?, ?> read(JsonReader in) throws IOException {
			String backtrackString = getStringFromReader(in);
			if (backtrackString == null) {
				LOG.log(Level.WARNING, "Parsing an Either type needs access to the input as string, but that wasn't provided. Using left type now.");
				return Either.forLeft(leftAdapter.read(in));
			} else {
				try {
					// lets first try if it successful and backtrack in case it wasn't.
					leftAdapter.read(new JsonReader(new StringReader(backtrackString)));
					return Either.forLeft(leftAdapter.read(in));
				} catch (IOException e) {
					return Either.forRight(rightAdapter.read(in));
				}
			}
		}

		private String getStringFromReader(JsonReader in) {
			try {
				Field field = JsonReader.class.getDeclaredField("in");
				field.setAccessible(true);
				Object reader = field.get(in);
				Field posField = JsonReader.class.getDeclaredField("pos");
				posField.setAccessible(true);
				Integer offset = (Integer) posField.get(in);
				
				if (reader instanceof StringReader) {
					Field strField = StringReader.class.getDeclaredField("str");
					String object = (String) strField.get(reader);
					if (object.length() > offset) {
						return object.substring(offset);
					}
				}
				return null;
			} catch (Exception e) {
				return null;
			}
		}

	}
}
