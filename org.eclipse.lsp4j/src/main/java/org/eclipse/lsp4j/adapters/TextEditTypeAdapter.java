/******************************************************************************
 * Copyright (c) 2026 1C-Soft LLC and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.adapters;

import java.io.IOException;

import org.eclipse.lsp4j.AnnotatedTextEdit;
import org.eclipse.lsp4j.TextEdit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Type adapter for {@link TextEdit} and {@link AnnotatedTextEdit}.
 */
public class TextEditTypeAdapter implements TypeAdapterFactory {

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

		if (!TextEdit.class.isAssignableFrom(type.getRawType())) {
			return null;
		}

		var textEditAdapter = gson.getDelegateAdapter(this, TypeToken.get(TextEdit.class));
		var annotatedTextEditAdapter = gson.getDelegateAdapter(this, TypeToken.get(AnnotatedTextEdit.class));

		return (TypeAdapter<T>) new TypeAdapter<TextEdit>() {

			@Override
			public void write(JsonWriter out, TextEdit value) throws IOException {
				if (value instanceof AnnotatedTextEdit) {
					annotatedTextEditAdapter.write(out, (AnnotatedTextEdit) value);
				} else {
					textEditAdapter.write(out, value);
				}
			}

			@Override
			public TextEdit read(JsonReader in) throws IOException {
				JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();
				if (jsonObject.has("annotationId")) {
					return annotatedTextEditAdapter.fromJsonTree(jsonObject);
				}
				return textEditAdapter.fromJsonTree(jsonObject);
			}
		};
	}
}
