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
import java.util.Collections;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A specialized type adapter factory for collections that can handle single values.
 */
public class CollectionTypeAdapterFactory implements TypeAdapterFactory {

	private final ConstructorConstructor constructorConstructor;
	
	public CollectionTypeAdapterFactory() {
		this.constructorConstructor = new ConstructorConstructor(Collections.emptyMap());
	}
	
	public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
		this.constructorConstructor = constructorConstructor;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
		if (!Collection.class.isAssignableFrom(typeToken.getRawType()))
			return null;
		
		Type elementType = $Gson$Types.getCollectionElementType(typeToken.getType(), typeToken.getRawType());
		TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
		ObjectConstructor<? extends Collection<?>> constructor =
				(ObjectConstructor<? extends Collection<?>>) constructorConstructor.get(typeToken);
		return new Adapter(new TypeAdapterRuntimeTypeWrapper(gson, elementTypeAdapter, elementType), constructor);
	}
	
	private static class Adapter<E> extends TypeAdapter<Collection<E>> {

		private final TypeAdapter<E> elementTypeAdapter;
		private final ObjectConstructor<? extends Collection<E>> constructor;
		
		Adapter(TypeAdapter<E> elementTypeAdapter, ObjectConstructor<? extends Collection<E>> constructor) {
			this.elementTypeAdapter = elementTypeAdapter;
			this.constructor = constructor;
		}
		
		@Override
		public Collection<E> read(JsonReader in) throws IOException {
			JsonToken peek = in.peek();
			if (peek == JsonToken.NULL) {
				in.nextNull();
				return null;
			} else if (peek == JsonToken.BEGIN_ARRAY) {
				Collection<E> collection = constructor.construct();
				in.beginArray();
				while (in.hasNext()) {
					E instance = elementTypeAdapter.read(in);
					collection.add(instance);
				}
				in.endArray();
				return collection;
			} else {
				Collection<E> collection = constructor.construct();
				E instance = elementTypeAdapter.read(in);
				collection.add(instance);
				return collection;
			}
		}

		@Override
		public void write(JsonWriter out, Collection<E> collection) throws IOException {
			if (collection == null) {
				out.nullValue();
				return;
			}
			out.beginArray();
			for (E element : collection) {
				elementTypeAdapter.write(out, element);
			}
			out.endArray();
		}
	}

}
