/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;

import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Type adapter factory for {@link Either} and {@link Either3}.
 */
public class EitherTypeAdapterFactory implements TypeAdapterFactory {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
		if (!TypeUtils.isEither(typeToken.getType())) {
			return null;
		}
		return new Adapter(gson, typeToken);
	}

	protected static class Adapter<L, R> extends TypeAdapter<Either<L, R>> {

		protected final TypeToken<Either<L, R>> typeToken;
		protected final EitherTypeArgument<L> left;
		protected final EitherTypeArgument<R> right;

		public Adapter(Gson gson, TypeToken<Either<L, R>> typeToken) {
			this.typeToken = typeToken;
			Type[] elementTypes = TypeUtils.getElementTypes(typeToken, Either.class);
			this.left = new EitherTypeArgument<L>(gson, elementTypes[0]);
			this.right = new EitherTypeArgument<R>(gson, elementTypes[1]);
		}

		@Override
		public void write(JsonWriter out, Either<L, R> value) throws IOException {
			if (value == null) {
				out.nullValue();
			} else if (value.isLeft()) {
				left.write(out, value.getLeft());
			} else {
				right.write(out, value.getRight());
			}
		}

		@Override
		public Either<L, R> read(JsonReader in) throws IOException {
			JsonToken next = in.peek();
			if (next == JsonToken.NULL) {
				return null;
			}
			Either<L, R> result = create(next, in);
			if (result == null)
				throw new IOException("Unexpected token " + next + ", expected " + left + " | " + right + " tokens.");
			return result;
		}
		
		@SuppressWarnings("unchecked")
		protected Either<L, R> create(JsonToken nextToken, JsonReader in) throws IOException {
			if (left.isAssignable(nextToken)) {
				if (Either3.class.isAssignableFrom(typeToken.getRawType()))
					return (Either<L, R>) Either3.forLeft3(left.read(in));
				else
					return Either.forLeft(left.read(in));
			}
			if (right.isAssignable(nextToken)) {
				if (Either3.class.isAssignableFrom(typeToken.getRawType()))
					return (Either<L, R>) Either3.forRight3((Either<?, ?>) right.read(in));
				else
					return Either.forRight(right.read(in));
			}
			return null;
		}
		
	}

	protected static class EitherTypeArgument<T> {

		protected final TypeToken<T> token;
		protected final TypeAdapter<T> adapter;
		protected final Collection<JsonToken> expectedTokens;

		@SuppressWarnings("unchecked")
		public EitherTypeArgument(Gson gson, Type type) {
			this.token = (TypeToken<T>) TypeToken.get(type);
			this.adapter = gson.getAdapter(this.token);
			this.expectedTokens = new HashSet<>();
			for (Type expectedType : TypeUtils.getExpectedTypes(type)) {
				Class<?> rawType = TypeToken.get(expectedType).getRawType();
				JsonToken expectedToken = getExpectedToken(rawType);
				expectedTokens.add(expectedToken);
			}
		}

		protected JsonToken getExpectedToken(Class<?> rawType) {
			if (rawType.isArray() || Collection.class.isAssignableFrom(rawType)) {
				return JsonToken.BEGIN_ARRAY;
			}
			if (Boolean.class.isAssignableFrom(rawType)) {
				return JsonToken.BOOLEAN;
			}
			if (Number.class.isAssignableFrom(rawType) || Enum.class.isAssignableFrom(rawType)) {
				return JsonToken.NUMBER;
			}
			if (Character.class.isAssignableFrom(rawType) || String.class.isAssignableFrom(rawType)) {
				return JsonToken.STRING;
			}
			return JsonToken.BEGIN_OBJECT;
		}

		public boolean isAssignable(JsonToken token) {
			return this.expectedTokens.contains(token);
		}

		public void write(JsonWriter out, T value) throws IOException {
			this.adapter.write(out, value);
		}

		public T read(JsonReader in) throws IOException {
			return this.adapter.read(in);
		}

		public String toString() {
			StringBuilder builder = new StringBuilder();
			for (JsonToken expectedToken : expectedTokens) {
				if (builder.length() != 0) {
					builder.append(" | ");
				}
				builder.append(expectedToken);
			}
			return builder.toString();
		}

	}

}
