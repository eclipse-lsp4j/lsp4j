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
package org.eclipse.lsp4j.debug.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.lsp4j.debug.StartDebuggingRequestArguments;
import org.eclipse.lsp4j.debug.StartDebuggingRequestArgumentsOutputPresentation;
import org.eclipse.lsp4j.debug.StartDebuggingRequestArgumentsType;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.jsonrpc.debug.json.DebugMessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.debug.messages.DebugRequestMessage;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.junit.Test;

public class DebugProtocolTest {

	private final DebugMessageJsonHandler handler = new DebugMessageJsonHandler(
			ServiceEndpoints.getSupportedMethods(IDebugProtocolClient.class));

	@Test
	public void startDebuggingReadsTheOutputPresentation() {
		assertEquals(StartDebuggingRequestArgumentsOutputPresentation.SEPARATE,
				parseStartDebugging(",\"outputPresentation\":\"separate\"").getOutputPresentation());
		assertEquals(StartDebuggingRequestArgumentsOutputPresentation.MERGE_WITH_PARENT,
				parseStartDebugging(",\"outputPresentation\":\"mergeWithParent\"").getOutputPresentation());
		assertNull(parseStartDebugging("").getOutputPresentation());
	}

	@Test
	public void startDebuggingWritesTheOutputPresentation() {
		assertTrue(serializeStartDebugging(StartDebuggingRequestArgumentsOutputPresentation.MERGE_WITH_PARENT)
				.contains("\"outputPresentation\":\"mergeWithParent\""));
		assertTrue(serializeStartDebugging(StartDebuggingRequestArgumentsOutputPresentation.SEPARATE)
				.contains("\"outputPresentation\":\"separate\""));
		assertFalse(serializeStartDebugging(null).contains("outputPresentation"));
	}

	private StartDebuggingRequestArguments parseStartDebugging(String outputPresentation) {
		var message = (DebugRequestMessage) handler.parseMessage("{\"type\":\"request\",\"seq\":3,"
				+ "\"command\":\"startDebugging\",\"arguments\":{\"configuration\":{},\"request\":\"launch\""
				+ outputPresentation + "}}");
		return (StartDebuggingRequestArguments) message.getParams();
	}

	private String serializeStartDebugging(StartDebuggingRequestArgumentsOutputPresentation outputPresentation) {
		var arguments = new StartDebuggingRequestArguments();
		arguments.setConfiguration(Map.of());
		arguments.setRequest(StartDebuggingRequestArgumentsType.LAUNCH);
		arguments.setOutputPresentation(outputPresentation);
		var message = new DebugRequestMessage();
		message.setId(3);
		message.setMethod("startDebugging");
		message.setParams(arguments);
		return handler.serialize(message);
	}
}
