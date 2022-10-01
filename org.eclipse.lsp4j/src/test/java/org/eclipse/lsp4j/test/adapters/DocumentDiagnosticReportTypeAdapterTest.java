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

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.lsp4j.DocumentDiagnosticReport;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.junit.Test;

import com.google.gson.Gson;

public class DocumentDiagnosticReportTypeAdapterTest {

	@Test
	public void test() {
		Gson gson = new MessageJsonHandler(Map.of()).getGson();
		assertTrue(gson.fromJson("{'kind': 'full', 'items': []}", DocumentDiagnosticReport.class).isLeft());
		assertTrue(gson.fromJson("{'kind': 'unchanged'}", DocumentDiagnosticReport.class).isRight());
	}
}
