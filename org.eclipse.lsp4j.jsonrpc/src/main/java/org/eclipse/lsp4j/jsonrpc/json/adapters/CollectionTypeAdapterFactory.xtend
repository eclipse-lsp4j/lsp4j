/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.internal.ConstructorConstructor
import com.google.gson.internal.ObjectConstructor
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.util.Collection
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

/**
 * A specialized type adapter factory for collections that can handle single values.
 */
@FinalFieldsConstructor
class CollectionTypeAdapterFactory implements TypeAdapterFactory {

	val ConstructorConstructor constructorConstructor
	
	new() {
		this.constructorConstructor = new ConstructorConstructor(emptyMap)
	}

	override <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
		if (!Collection.isAssignableFrom(typeToken.rawType))
			return null

		val elementType = TypeUtil.getCollectionElementType(typeToken.type, typeToken.rawType)
		val elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType))
		val constructor = constructorConstructor.get(typeToken) as ObjectConstructor<? extends Collection<?>>
		return new Adapter(new TypeAdapterRuntimeTypeWrapper(gson, elementTypeAdapter, elementType), constructor)
	}

	@FinalFieldsConstructor
	private static class Adapter<E> extends TypeAdapter<Collection<E>> {

		val TypeAdapter<E> elementTypeAdapter
		val ObjectConstructor<? extends Collection<E>> constructor

		override read(JsonReader in) throws IOException {
			val peek = in.peek
			if (peek == JsonToken.NULL) {
				in.nextNull
				return null
			} else if (peek == JsonToken.BEGIN_ARRAY) {
				val collection = constructor.construct()
				in.beginArray
				while (in.hasNext) {
					val instance = elementTypeAdapter.read(in)
					collection.add(instance)
				}
				in.endArray
				return collection
			} else {
				val collection = constructor.construct()
				val instance = elementTypeAdapter.read(in)
				collection.add(instance)
				return collection
			}
		}

		override write(JsonWriter out, Collection<E> collection) throws IOException {
			if (collection === null) {
				out.nullValue
				return
			}
			out.beginArray
			for (element : collection) {
				elementTypeAdapter.write(out, element)
			}
			out.endArray
		}
	}

}
