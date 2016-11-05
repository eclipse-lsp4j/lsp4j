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
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * A custom type adapter factory for enums that uses integer values.
 */
class EnumTypeAdapterFactory implements TypeAdapterFactory {

	override <T> create(Gson gson, TypeToken<T> typeToken) {
		var rawType = typeToken.rawType
		if (!Enum.isAssignableFrom(rawType) || rawType == Enum)
			return null
		if (!rawType.isEnum)
			rawType = rawType.superclass
		return new Adapter(rawType)
	}

	private static class Adapter<T extends Enum<T>> extends TypeAdapter<T> {
		
		static val VALUE_FIELD_NAME = 'value'

		val nameToConstant = <String, T>newHashMap
		val valueToConstant = <Integer, T>newHashMap
		val constantToValue = <T, Integer>newHashMap

		new(Class<T> classOfT) {
			try {
				val valueField = classOfT.getDeclaredField(VALUE_FIELD_NAME)
				if (valueField.type != int && valueField.type != Integer)
					throw new IllegalArgumentException("The field 'value' must contain an integer value.")
				valueField.accessible = true
				for (T constant : classOfT.enumConstants) {
					nameToConstant.put(constant.name, constant)
					val constValue = valueField.get(constant) as Integer
					valueToConstant.put(constValue, constant)
					constantToValue.put(constant, constValue)
				}
			} catch (NoSuchFieldException e) {
				for (T constant : classOfT.enumConstants) {
					nameToConstant.put(constant.name, constant)
					val constValue = constant.ordinal
					valueToConstant.put(constValue, constant)
					constantToValue.put(constant, constValue)
				}
			}
		}

		override read(JsonReader in) throws IOException {
			val peek = in.peek
			if (peek == JsonToken.NULL) {
				in.nextNull
				return null
			} else if (peek == JsonToken.NUMBER) {
				return valueToConstant.get(in.nextInt)
			} else {
				val string = in.nextString
				try {
					return valueToConstant.get(Integer.parseInt(string))
				} catch (NumberFormatException e) {
					return nameToConstant.get(string)
				}
			}
		}

		override write(JsonWriter out, T value) throws IOException {
			if (value !== null)
				out.value(constantToValue.get(value))
			else
				out.value(null as String)
		}

	}

}
