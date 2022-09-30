/******************************************************************************
 * Copyright (c) 2022 1C-Soft LLC and others.
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
import java.util.function.Predicate;

import org.eclipse.lsp4j.DocumentDiagnosticReport;
import org.eclipse.lsp4j.RelatedFullDocumentDiagnosticReport;
import org.eclipse.lsp4j.RelatedUnchangedDocumentDiagnosticReport;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class DocumentDiagnosticReportTypeAdapter implements TypeAdapterFactory {

	private static final TypeToken<DocumentDiagnosticReport> ELEMENT_TYPE = TypeToken.get(DocumentDiagnosticReport.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Predicate<JsonElement> leftChecker = new PropertyChecker("kind", "full");
		Predicate<JsonElement> rightChecker = new PropertyChecker("kind", "unchanged");
		return (TypeAdapter<T>) new EitherTypeAdapter<RelatedFullDocumentDiagnosticReport, RelatedUnchangedDocumentDiagnosticReport>(
				gson, ELEMENT_TYPE, leftChecker, rightChecker) {

			@Override
			protected DocumentDiagnosticReport createLeft(RelatedFullDocumentDiagnosticReport obj) throws IOException {
				return new DocumentDiagnosticReport(obj);
			}

			@Override
			protected DocumentDiagnosticReport createRight(RelatedUnchangedDocumentDiagnosticReport obj)
					throws IOException {
				return new DocumentDiagnosticReport(obj);
			}
		};
	}
}
