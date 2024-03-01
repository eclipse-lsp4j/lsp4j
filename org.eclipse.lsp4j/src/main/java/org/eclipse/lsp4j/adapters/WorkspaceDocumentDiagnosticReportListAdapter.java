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
import java.util.ArrayList;
import java.util.function.Predicate;

import org.eclipse.lsp4j.WorkspaceDocumentDiagnosticReport;
import org.eclipse.lsp4j.WorkspaceFullDocumentDiagnosticReport;
import org.eclipse.lsp4j.WorkspaceUnchangedDocumentDiagnosticReport;
import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class WorkspaceDocumentDiagnosticReportListAdapter implements TypeAdapterFactory {

	private static final TypeToken<WorkspaceDocumentDiagnosticReport> ELEMENT_TYPE = TypeToken.get(WorkspaceDocumentDiagnosticReport.class);

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		Predicate<JsonElement> leftChecker = new PropertyChecker("kind", "full");
		Predicate<JsonElement> rightChecker = new PropertyChecker("kind", "unchanged");
		EitherTypeAdapter<WorkspaceFullDocumentDiagnosticReport, WorkspaceUnchangedDocumentDiagnosticReport> elementTypeAdapter =
				new EitherTypeAdapter<>(
						gson, ELEMENT_TYPE, leftChecker, rightChecker) {
			@Override
			protected WorkspaceDocumentDiagnosticReport createLeft(WorkspaceFullDocumentDiagnosticReport obj) throws IOException {
				return new WorkspaceDocumentDiagnosticReport(obj);
			}

			@Override
			protected WorkspaceDocumentDiagnosticReport createRight(WorkspaceUnchangedDocumentDiagnosticReport obj) throws IOException {
				return new WorkspaceDocumentDiagnosticReport(obj);
			}
		};
		return (TypeAdapter<T>) new CollectionTypeAdapter<>(gson, ELEMENT_TYPE.getType(), elementTypeAdapter, ArrayList::new);
	}
}
