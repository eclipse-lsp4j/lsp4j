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
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {

	val Gson context
	val TypeAdapter<T> delegate
	val Type type

	override read(JsonReader in) throws IOException {
		delegate.read(in)
	}

	override write(JsonWriter out, T value) throws IOException {
		if (value !== null && type != value.class && (type instanceof TypeVariable<?> || type instanceof Class<?>)) {
			val runtimeType = value.class
			val runtimeTypeAdapter = context.getAdapter(TypeToken.get(runtimeType)) as TypeAdapter<T>
			if (!(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)
					|| delegate instanceof ReflectiveTypeAdapterFactory.Adapter) {
				runtimeTypeAdapter.write(out, value)
				return
			}
		}
		delegate.write(out, value)
	}

}
