/*******************************************************************************
 * Copyright (c) 2017 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json.adapters;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

/**
 * Utilities for handling types in the JSON parser / serializer.
 */
public class TypeUtils {
	
	private TypeUtils() {}
	
	/**
	 * Determine the actual type arguments of the given type token with regard to the given target type.
	 */
	public static Type[] getElementTypes(TypeToken<?> typeToken, Class<?> targetType) {
		return getElementTypes(typeToken.getType(), typeToken.getRawType(), targetType);
	}
	
	private static Type[] getElementTypes(Type type, Class<?> rawType, Class<?> targetType) {
		if (targetType.equals(rawType) && type instanceof ParameterizedType) {
			Type mappedType;
			if (type instanceof ParameterizedTypeImpl)
				mappedType = type;
			else
				// Transform wildcards in the actual type arguments
				mappedType = getMappedType(type, Collections.emptyMap());
			return ((ParameterizedType) mappedType).getActualTypeArguments();
		}
		// Map the parameters of the raw type to the actual type arguments
		Map<String, Type> varMapping = createVariableMapping(type, rawType);
		if (targetType.isInterface()) {
			// Look for superinterfaces that extend the target interface
			Class<?>[] interfaces = rawType.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				if (Collection.class.isAssignableFrom(interfaces[i])) {
					Type genericInterface = rawType.getGenericInterfaces()[i];
					Type mappedInterface = getMappedType(genericInterface, varMapping);
					return getElementTypes(mappedInterface, interfaces[i], targetType);
				}
			}
		}
		if (!rawType.isInterface()) {
			// Visit the superclass if it extends the target class / implements the target interface
			Class<?> rawSupertype = rawType.getSuperclass();
			if (targetType.isAssignableFrom(rawSupertype)) {
				Type genericSuperclass = rawType.getGenericSuperclass();
				Type mappedSuperclass = getMappedType(genericSuperclass, varMapping);
				return getElementTypes(mappedSuperclass, rawSupertype, targetType);
			}
		}
		// No luck, return an array of Object types
		Type[] result = new Type[targetType.getTypeParameters().length];
		Arrays.fill(result, Object.class);
		return result;
	}
	
	private static <T> Map<String, Type> createVariableMapping(Type type, Class<T> rawType) {
		if (type instanceof ParameterizedType) {
			TypeVariable<Class<T>>[] vars = rawType.getTypeParameters();
			Type[] args = ((ParameterizedType) type).getActualTypeArguments();
			Map<String, Type> newVarMapping = new HashMap<>(capacity(vars.length));
			for (int i = 0; i < vars.length; i++) {
				Type actualType = Object.class;
				if (i < args.length) {
					actualType = args[i];
					if (actualType instanceof WildcardType)
						actualType = ((WildcardType) actualType).getUpperBounds()[0];
				}
				newVarMapping.put(vars[i].getName(), actualType);
			}
			return newVarMapping;
		}
		return Collections.emptyMap();
	}
	
	private static int capacity(int expectedSize) {
		if (expectedSize < 3)
			return expectedSize + 1;
		else
			return expectedSize + expectedSize / 3;
	}
	
	private static Type getMappedType(Type type, Map<String, Type> varMapping) {
		if (type instanceof TypeVariable) {
			String name = ((TypeVariable<?>) type).getName();
			if (varMapping.containsKey(name))
				return varMapping.get(name);
		}
		if (type instanceof WildcardType) {
			return getMappedType(((WildcardType) type).getUpperBounds()[0], varMapping);
		}
		if (type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Type[] origArgs = pt.getActualTypeArguments();
			Type[] mappedArgs = new Type[origArgs.length];
			for (int i = 0; i < origArgs.length; i++) {
				mappedArgs[i] = getMappedType(origArgs[i], varMapping);
			}
			return new ParameterizedTypeImpl(pt, mappedArgs);
		}
		return type;
	}

	private static class ParameterizedTypeImpl implements ParameterizedType {
		
		private final Type ownerType;
		private final Type rawType;
		private final Type[] actualTypeArguments;
		
		ParameterizedTypeImpl(ParameterizedType original, Type[] typeArguments) {
			this(original.getOwnerType(), original.getRawType(), typeArguments);
		}
		
		ParameterizedTypeImpl(Type ownerType, Type rawType, Type[] typeArguments) {
			this.ownerType = ownerType;
			this.rawType = rawType;
			this.actualTypeArguments = typeArguments;
		}
		
		@Override
		public Type getOwnerType() {
			return ownerType;
		}
		
		@Override
		public Type getRawType() {
			return rawType;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return actualTypeArguments;
		}
		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder();
			if (ownerType != null) {
				result.append(toString(ownerType));
				result.append('$');
			}
			result.append(toString(rawType));
			result.append('<');
			for (int i = 0; i < actualTypeArguments.length; i++) {
				if (i > 0)
					result.append(", ");
				result.append(toString(actualTypeArguments[i]));
			}
			result.append('>');
			return result.toString();
		}
		
		private String toString(Type type) {
			if (type instanceof Class<?>)
				return ((Class<?>) type).getName();
			else
				return String.valueOf(type);
		}
		
	}

}
