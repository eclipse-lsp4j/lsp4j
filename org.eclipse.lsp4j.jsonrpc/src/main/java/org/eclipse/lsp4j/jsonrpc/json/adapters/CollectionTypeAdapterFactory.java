/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A specialized type adapter factory for collections that can handle single values.
 */
public class CollectionTypeAdapterFactory implements TypeAdapterFactory {

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
		if (!Collection.class.isAssignableFrom(typeToken.getRawType()))
			return null;

		Type elementType = getCollectionElementType(typeToken.getType(), typeToken.getRawType());
		TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
		Supplier<Collection<Object>> constructor = getConstructor((Class<Collection<Object>>) typeToken.getRawType());
		return new Adapter(gson, elementType, elementTypeAdapter, constructor);
	}

	private static class Adapter<E> extends TypeAdapter<Collection<E>> {

		private final Gson gson;
		private final Type elementType;
		private final TypeAdapter<E> elementTypeAdapter;
		private final Supplier<Collection<E>> constructor;

		Adapter(Gson gson, Type elementType, TypeAdapter<E> elementTypeAdapter, Supplier<Collection<E>> constructor) {
			this.gson = gson;
			this.elementType = elementType;
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
				Collection<E> collection = constructor.get();
				in.beginArray();
				while (in.hasNext()) {
					E instance = elementTypeAdapter.read(in);
					collection.add(instance);
				}
				in.endArray();
				return collection;
			} else {
				Collection<E> collection = constructor.get();
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
				if (element != null && elementType != element.getClass()
						&& (elementType instanceof TypeVariable<?> || elementType instanceof Class<?>)) {
					@SuppressWarnings("unchecked")
					TypeAdapter<E> runtimeTypeAdapter = (TypeAdapter<E>) gson.getAdapter(TypeToken.get(element.getClass()));
					runtimeTypeAdapter.write(out, element);
				} else {
					elementTypeAdapter.write(out, element);
				}
			}
			out.endArray();
		}
	}

	private <E> Supplier<Collection<E>> getConstructor(Class<Collection<E>> rawType) {
		try {
			Constructor<Collection<E>> constructor = rawType.getDeclaredConstructor();
			if (!constructor.isAccessible())
				constructor.setAccessible(true);
			return () -> {
				try {
					return constructor.newInstance();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			};
		} catch (NoSuchMethodException e) {
			if (SortedSet.class.isAssignableFrom(rawType)) {
				return () -> {
					return new TreeSet<E>();
				};
			} else if (Set.class.isAssignableFrom(rawType)) {
				return () -> {
					return new LinkedHashSet<E>();
				};
			} else if (Queue.class.isAssignableFrom(rawType)) {
				return () -> {
					return new LinkedList<E>();
				};
			} else {
				return () -> {
					return new ArrayList<E>();
				};
			}
		}
	}

	private Type getCollectionElementType(Type type, Class<?> rawType) {
		Map<String, Type> varMapping = createVariableMapping(type, rawType, Collections.emptyMap());
		return getCollectionElementType(type, rawType, varMapping);
	}
	
	private Type getCollectionElementType(Type type, Class<?> rawType, Map<String, Type> varMapping) {
		if (rawType == Collection.class) {
			return getActualTypeParameter(type, 0, varMapping);
		}
		Class<?>[] interfaces = rawType.getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			if (Collection.class.isAssignableFrom(interfaces[i])) {
				Type genericInterface = rawType.getGenericInterfaces()[i];
				Map<String, Type> newVarMapping = createVariableMapping(genericInterface, interfaces[i], varMapping);
				return getCollectionElementType(genericInterface, interfaces[i], newVarMapping);
			}
		}
		if (!rawType.isInterface()) {
			Class<?> rawSupertype = rawType.getSuperclass();
			if (Collection.class.isAssignableFrom(rawSupertype)) {
				Type genericSuperclass = rawType.getGenericSuperclass();
				Map<String, Type> newVarMapping = createVariableMapping(genericSuperclass, rawSupertype, varMapping);
				return getCollectionElementType(genericSuperclass, rawSupertype, newVarMapping);
			}
		}
		return Object.class;
	}
	
	private <T> Map<String, Type> createVariableMapping(Type type, Class<T> rawType, Map<String, Type> oldVarMapping) {
		if (type instanceof ParameterizedType) {
			TypeVariable<Class<T>>[] vars = rawType.getTypeParameters();
			Map<String, Type> newVarMapping = Maps.newHashMapWithExpectedSize(vars.length);
			for (int i = 0; i < vars.length; i++) {
				newVarMapping.put(vars[i].getName(), getActualTypeParameter(type, i, oldVarMapping));
			}
			return newVarMapping;
		}
		return Collections.emptyMap();
	}

	private Type getActualTypeParameter(Type type, int index, Map<String, Type> varMapping) {
		if (type instanceof ParameterizedType) {
			Type[] args = ((ParameterizedType) type).getActualTypeArguments();
			if (index < args.length) {
				Type arg = args[index];
				if (arg instanceof WildcardType)
					arg = ((WildcardType) arg).getUpperBounds()[0];
				if (arg instanceof TypeVariable) {
					String name = ((TypeVariable<?>) arg).getName();
					if (varMapping.containsKey(name))
						return varMapping.get(name);
				} else {
					return arg;
				}
			}
		}
		return Object.class;
	}

}
