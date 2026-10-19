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
package org.eclipse.lsp4j.test.adapters;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.lsp4j.DocumentDiagnosticReport;
import org.eclipse.lsp4j.DocumentDiagnosticReportPartialResult;
import org.eclipse.lsp4j.FullDocumentDiagnosticReport;
import org.eclipse.lsp4j.RelatedFullDocumentDiagnosticReport;
import org.eclipse.lsp4j.RelatedUnchangedDocumentDiagnosticReport;
import org.eclipse.lsp4j.UnchangedDocumentDiagnosticReport;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.LanguageServer;
import org.junit.Test;

import com.google.gson.Gson;

public class RelatedDocumentsMapAdapterTest {

	private static final String RELATED_DOCUMENTS = "'relatedDocuments': {"
			+ "'file:///tmp/full.hpp': {'kind': 'full', 'items': []},"
			+ "'file:///tmp/unchanged.hpp': {'kind': 'unchanged', 'resultId': '456'}}";

	private final Gson gson = new MessageJsonHandler(Collections.emptyMap()).getGson();

	private static Map<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>> relatedDocuments() {
		var relatedDocuments = new LinkedHashMap<String, Either<FullDocumentDiagnosticReport, UnchangedDocumentDiagnosticReport>>();
		relatedDocuments.put("file:///tmp/full.hpp", Either.forLeft(new FullDocumentDiagnosticReport(Collections.emptyList())));
		relatedDocuments.put("file:///tmp/unchanged.hpp", Either.forRight(new UnchangedDocumentDiagnosticReport("456")));
		return relatedDocuments;
	}

	@Test
	public void testParseRelatedFullDocumentDiagnosticReport() {
		var expected = new RelatedFullDocumentDiagnosticReport(Collections.emptyList());
		expected.setRelatedDocuments(relatedDocuments());

		assertEquals(expected, gson.fromJson("{'kind': 'full', 'items': [], " + RELATED_DOCUMENTS + "}",
				RelatedFullDocumentDiagnosticReport.class));
	}

	@Test
	public void testParseRelatedUnchangedDocumentDiagnosticReport() {
		var expected = new RelatedUnchangedDocumentDiagnosticReport("123");
		expected.setRelatedDocuments(relatedDocuments());

		assertEquals(expected, gson.fromJson("{'kind': 'unchanged', 'resultId': '123', " + RELATED_DOCUMENTS + "}",
				RelatedUnchangedDocumentDiagnosticReport.class));
	}

	@Test
	public void testParseDocumentDiagnosticReportPartialResult() {
		assertEquals(new DocumentDiagnosticReportPartialResult(relatedDocuments()),
				gson.fromJson("{" + RELATED_DOCUMENTS + "}", DocumentDiagnosticReportPartialResult.class));
	}

	@Test
	public void testDiagnosticResponseRoundTrip() {
		var handler = new MessageJsonHandler(ServiceEndpoints.getSupportedMethods(LanguageServer.class));
		handler.setMethodProvider(id -> "textDocument/diagnostic");
		var full = new RelatedFullDocumentDiagnosticReport(Collections.emptyList());
		full.setRelatedDocuments(relatedDocuments());
		var response = new ResponseMessage();
		response.setId("1");
		response.setResult(new DocumentDiagnosticReport(full));

		var parsed = (ResponseMessage) handler.parseMessage(handler.serialize(response));

		assertEquals(response.getResult(), parsed.getResult());
	}
}
