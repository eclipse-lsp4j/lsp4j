/******************************************************************************
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.debug.adapters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class DebugEnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
	public static class Factory implements TypeAdapterFactory {
		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			Class<?> rawType = typeToken.getRawType();
			if (!Enum.class.isAssignableFrom(rawType) || rawType == Enum.class)
				return null;
			if (!rawType.isEnum())
				rawType = rawType.getSuperclass();
			return new DebugEnumTypeAdapter(rawType);
		}

	}

	private final Map<String, T> serializedFormToEnum = new HashMap<String, T>();
	private final Map<T, String> enumToSerializedForm = new HashMap<T, String>();

	public DebugEnumTypeAdapter(Class<T> clazz) {
		try {
			for (T constant : clazz.getEnumConstants()) {
				String name = constant.name();
				String serializedForm = getSerializedForm(name);
				// Like default gson, allow overriding names with SerializedName
				SerializedName annotation = clazz.getField(name).getAnnotation(SerializedName.class);
				if (annotation != null) {
					serializedForm = annotation.value();
					for (String alternate : annotation.alternate()) {
						serializedFormToEnum.put(alternate, constant);
					}
				}
				serializedFormToEnum.put(serializedForm, constant);
				enumToSerializedForm.put(constant, serializedForm);
			}
		} catch (NoSuchFieldException e) {
			throw new AssertionError(e);
		}
	}

	private String getSerializedForm(String name) {
		name = name.toLowerCase();
		Matcher m = Pattern.compile("_[a-z]").matcher(name);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, m.group().toUpperCase());
		}
		m.appendTail(sb);
		return sb.toString().replace("_", "");
	}

	@Override
	public T read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		return serializedFormToEnum.get(in.nextString());
	}

	@Override
	public void write(JsonWriter out, T value) throws IOException {
		out.value(value == null ? null : enumToSerializedForm.get(value));
	}
}