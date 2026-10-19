/******************************************************************************
 * Copyright (c) 2026 Marcus Lagergren and others.
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
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.lsp4j.FullDocumentDiagnosticReport;
import org.eclipse.lsp4j.UnchangedDocumentDiagnosticReport;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A type adapter for the {@code relatedDocuments} of a document diagnostic report. Both kinds of
 * report are JSON objects, so each value is identified by its {@code kind} property.
 */
public class RelatedDocumentsMapAdapter implements TypeAdapterFactory {

	private static final TypeToken<Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>> ELEMENT_TYPE
			= new TypeToken<Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>>() {};

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		final var leftChecker = new PropertyChecker("kind", "full");
		final var rightChecker = new PropertyChecker("kind", "unchanged");
		final var elementTypeAdapter = new EitherTypeAdapter<>(gson, ELEMENT_TYPE, leftChecker, rightChecker);
		return (TypeAdapter<T>) new TypeAdapter<Map<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>>>() {

			@Override
			public void write(JsonWriter out, Map<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>> value)
					throws IOException {
				if (value == null) {
					out.nullValue();
					return;
				}
				out.beginObject();
				for (var entry : value.entrySet()) {
					out.name(entry.getKey());
					elementTypeAdapter.write(out, entry.getValue());
				}
				out.endObject();
			}

			@Override
			public Map<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>> read(JsonReader in)
					throws IOException {
				if (in.peek() == JsonToken.NULL) {
					in.nextNull();
					return null;
				}
				var result = new LinkedHashMap<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>>();
				in.beginObject();
				while (in.hasNext()) {
					result.put(in.nextName(), elementTypeAdapter.read(in));
				}
				in.endObject();
				return result;
			}
		};
	}
}
