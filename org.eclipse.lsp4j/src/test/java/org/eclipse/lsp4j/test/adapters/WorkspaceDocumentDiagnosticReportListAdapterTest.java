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
package org.eclipse.lsp4j.test.adapters;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.lsp4j.WorkspaceDiagnosticReport;
import org.eclipse.lsp4j.WorkspaceDiagnosticReportPartialResult;
import org.eclipse.lsp4j.WorkspaceDocumentDiagnosticReport;
import org.eclipse.lsp4j.WorkspaceFullDocumentDiagnosticReport;
import org.eclipse.lsp4j.WorkspaceUnchangedDocumentDiagnosticReport;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.junit.Test;

import com.google.gson.Gson;

public class WorkspaceDocumentDiagnosticReportListAdapterTest {

	private final Gson gson = new MessageJsonHandler(Collections.emptyMap()).getGson();

	@Test
	public void testParseWorkspaceDiagnosticReport() {

		assertEquals(
				gson.fromJson("{'items': [{'kind': 'full', 'items': [], 'uri': 'file:///tmp/foo'}]}",
						WorkspaceDiagnosticReport.class),
				new WorkspaceDiagnosticReport(Arrays.asList(new WorkspaceDocumentDiagnosticReport(
						new WorkspaceFullDocumentDiagnosticReport(Collections.emptyList(), "file:///tmp/foo", null)))));

		assertEquals(
				gson.fromJson("{'items': [{'kind': 'unchanged', 'resultId': '123', 'uri': 'file:///tmp/foo'}]}",
						WorkspaceDiagnosticReport.class),
				new WorkspaceDiagnosticReport(Arrays.asList(new WorkspaceDocumentDiagnosticReport(
						new WorkspaceUnchangedDocumentDiagnosticReport("123", "file:///tmp/foo", null)))));
	}

	@Test
	public void testParseWorkspaceDiagnosticReportPartialResult() {

		assertEquals(
				gson.fromJson("{'items': [{'kind': 'full', 'items': [], 'uri': 'file:///tmp/foo'}]}",
						WorkspaceDiagnosticReportPartialResult.class),
				new WorkspaceDiagnosticReportPartialResult(Arrays.asList(new WorkspaceDocumentDiagnosticReport(
						new WorkspaceFullDocumentDiagnosticReport(Collections.emptyList(), "file:///tmp/foo", null)))));

		assertEquals(
				gson.fromJson("{'items': [{'kind': 'unchanged', 'resultId': '123', 'uri': 'file:///tmp/foo'}]}",
						WorkspaceDiagnosticReportPartialResult.class),
				new WorkspaceDiagnosticReportPartialResult(Arrays.asList(new WorkspaceDocumentDiagnosticReport(
						new WorkspaceUnchangedDocumentDiagnosticReport("123", "file:///tmp/foo", null)))));
	}
}
