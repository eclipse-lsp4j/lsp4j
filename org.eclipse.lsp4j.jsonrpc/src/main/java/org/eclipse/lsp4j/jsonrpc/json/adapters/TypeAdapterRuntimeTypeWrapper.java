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
import java.lang.reflect.TypeVariable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

class TypeAdapterRuntimeTypeWrapper<T> extends TypeAdapter<T> {

	private final Gson context;
	private final TypeAdapter<T> delegate;
	private final Type type;
	
	public TypeAdapterRuntimeTypeWrapper(Gson context, TypeAdapter<T> delegate, Type type) {
		this.context = context;
		this.delegate = delegate;
		this.type = type;
	}
	
	@Override
	public T read(JsonReader in) throws IOException {
		return delegate.read(in);
	}
	
	@Override
	public void write(JsonWriter out, T value) throws IOException {
		if (value != null && type != value.getClass() && (type instanceof TypeVariable<?> || type instanceof Class<?>)) {
			Class<?> runtimeType = value.getClass();
			@SuppressWarnings("unchecked")
			TypeAdapter<T> runtimeTypeAdapter = (TypeAdapter<T>) context.getAdapter(TypeToken.get(runtimeType));
			if (!(runtimeTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)
					|| delegate instanceof ReflectiveTypeAdapterFactory.Adapter) {
				runtimeTypeAdapter.write(out, value);
				return;
			}
		}
		delegate.write(out, value);
	}

}
