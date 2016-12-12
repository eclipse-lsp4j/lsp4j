/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters;

import static com.google.common.collect.Sets.newHashSet;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class EitherTypeAdapterFactory implements TypeAdapterFactory {

	@Override
	@SuppressWarnings("unchecked")
	public <T> TypeAdapter<T> create(Gson gson, com.google.gson.reflect.TypeToken<T> typeToken) {
		if (!Either.class.isAssignableFrom(typeToken.getRawType()))
			return null;
		ParameterizedType parameterizedType = (ParameterizedType)typeToken.getType();
		Type left = parameterizedType.getActualTypeArguments()[0];
		Type right = parameterizedType.getActualTypeArguments()[1];
		return (TypeAdapter<T>) new Adapter(gson, left, right);
	}

	static class Adapter extends TypeAdapter<Either<?,?>> {
		
		private final TypeToken<?> left;
		private final TypeToken<?> right;
		private final Gson gson;
		
		private final Set<Class<?>> specialRawTypes = newHashSet(String.class, Integer.class, Boolean.class, List.class);
		
		private boolean isObject(TypeToken<?> t) {
			for (Class<?> class1 : specialRawTypes) {
				if (class1.isAssignableFrom(t.getRawType())) {
					return false;
				}
			}
			return true;
		}

		public Adapter(Gson gson, Type left, Type right) {
			this.gson = gson;
			this.left = TypeToken.of(left);
			this.right = TypeToken.of(right);
			if (isObject(this.left) && isObject(this.right)) {
				throw new IllegalArgumentException("Only one of the Either types can be a json object or StringMap.");
			}
			if (this.left.getRawType() == this.right.getRawType()) {
				throw new IllegalArgumentException("The raw types of an Either must be distinct.");
			}
		}

		@Override
		public void write(JsonWriter out, Either<?,?> value) throws IOException {
			if (value.isLeft()) {
				gson.toJson(value.getLeft(), left.getRawType(), out);
			} else {
				gson.toJson(value.getRight(), right.getRawType(), out);
			}
		}

		@Override
		public Either<?,?> read(JsonReader in) throws IOException {
			JsonToken peek = in.peek();
			TypeToken<?> next;
			switch (in.peek()) {
				case NULL : {
					return null;
				}
				case BEGIN_ARRAY : {
					next = TypeToken.of(List.class);
					break;
				}
				case BOOLEAN : {
					next = TypeToken.of(Boolean.class);
					break;
				}
				case NUMBER : {
					next = TypeToken.of(Number.class);
					break;
				}
				case STRING : {
					next = TypeToken.of(String.class);
					break;
				}
				case BEGIN_OBJECT : {
					next = null;
					break;
				}
				default : {
					throw new IllegalStateException("Illegal token "+in.peek());
				}
			}
			if (next != null) {
				if (next.isAssignableFrom(left)) {
					return Either.forLeft(gson.fromJson(in, left.getType()));
				} else if (next.isAssignableFrom(right)) {
					return Either.forRight(gson.fromJson(in, right.getType()));
				} else {
					throw new IOException("Unexpected token '"+peek+"', for type '"+left+" | "+right+"'.");
				}
			}
			// handle object type
			if (isObject(left)) {
				return Either.forLeft(gson.fromJson(in, left.getType()));
			} else if (isObject(right)) {
				return Either.forRight(gson.fromJson(in, right.getType()));
			}
			throw new IOException("Unexpected token '"+peek+"', for type '"+left+" | "+right+"'.");
		}
		
	}
}
