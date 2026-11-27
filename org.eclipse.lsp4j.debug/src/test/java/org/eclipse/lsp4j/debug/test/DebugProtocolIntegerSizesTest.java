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
package org.eclipse.lsp4j.debug.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.lsp4j.debug.Breakpoint;
import org.eclipse.lsp4j.debug.BreakpointLocation;
import org.eclipse.lsp4j.debug.BreakpointLocationsArguments;
import org.eclipse.lsp4j.debug.ColumnDescriptor;
import org.eclipse.lsp4j.debug.CompletionItem;
import org.eclipse.lsp4j.debug.CompletionsArguments;
import org.eclipse.lsp4j.debug.DataBreakpointInfoArguments;
import org.eclipse.lsp4j.debug.DisassembleArguments;
import org.eclipse.lsp4j.debug.DisassembledInstruction;
import org.eclipse.lsp4j.debug.EvaluateArguments;
import org.eclipse.lsp4j.debug.GotoTarget;
import org.eclipse.lsp4j.debug.GotoTargetsArguments;
import org.eclipse.lsp4j.debug.InstructionBreakpoint;
import org.eclipse.lsp4j.debug.LocationsResponse;
import org.eclipse.lsp4j.debug.MemoryEventArguments;
import org.eclipse.lsp4j.debug.ModulesArguments;
import org.eclipse.lsp4j.debug.ModulesResponse;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.ProcessEventArguments;
import org.eclipse.lsp4j.debug.ReadMemoryArguments;
import org.eclipse.lsp4j.debug.ReadMemoryResponse;
import org.eclipse.lsp4j.debug.Scope;
import org.eclipse.lsp4j.debug.SetBreakpointsArguments;
import org.eclipse.lsp4j.debug.SourceBreakpoint;
import org.eclipse.lsp4j.debug.StackFrame;
import org.eclipse.lsp4j.debug.StackTraceArguments;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StepInTarget;
import org.eclipse.lsp4j.debug.VariablesArguments;
import org.eclipse.lsp4j.debug.WriteMemoryArguments;
import org.eclipse.lsp4j.debug.WriteMemoryResponse;
import org.eclipse.lsp4j.jsonrpc.debug.json.DebugMessageJsonHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Tests that each field DAP 1.71 sizes as {@code int64}, {@code uint64} or {@code uint32} keeps the limits of its size
 * through the debug protocol's JSON handling. A field typed as {@code int} or {@code Integer} can't hold them: read
 * from text, they fail to parse, and read from a JSON tree, they are truncated.
 */
@RunWith(Parameterized.class)
public class DebugProtocolIntegerSizesTest {

	// DAP gives int64 and uint64 the range of the integers that a JSON number holds exactly
	private static final long[] INT64 = { -9007199254740991L, 9007199254740991L };
	private static final long[] UINT64 = { 9007199254740991L };
	private static final long[] UINT32 = { 4294967295L };

	private static final Gson GSON = new DebugMessageJsonHandler(Map.of()).getGson();

	@Parameters(name = "{0}.{1}")
	public static Collection<Object[]> fields() {
		return Arrays.asList(new Object[][] {
			{ MemoryEventArguments.class, "offset", INT64 },
			{ ReadMemoryArguments.class, "offset", INT64 },
			{ WriteMemoryArguments.class, "offset", INT64 },
			{ WriteMemoryResponse.class, "offset", INT64 },
			{ DisassembleArguments.class, "offset", INT64 },
			{ DisassembleArguments.class, "instructionOffset", INT64 },
			{ InstructionBreakpoint.class, "offset", INT64 },
			{ Breakpoint.class, "offset", INT64 },
			{ OutputEventArguments.class, "line", UINT64 },
			{ OutputEventArguments.class, "column", UINT64 },
			{ MemoryEventArguments.class, "count", UINT64 },
			{ BreakpointLocationsArguments.class, "line", UINT64 },
			{ BreakpointLocationsArguments.class, "column", UINT64 },
			{ BreakpointLocationsArguments.class, "endLine", UINT64 },
			{ BreakpointLocationsArguments.class, "endColumn", UINT64 },
			{ SetBreakpointsArguments.class, "lines", UINT64 },
			{ ModulesResponse.class, "totalModules", UINT64 },
			{ EvaluateArguments.class, "line", UINT64 },
			{ EvaluateArguments.class, "column", UINT64 },
			{ GotoTargetsArguments.class, "line", UINT64 },
			{ GotoTargetsArguments.class, "column", UINT64 },
			{ CompletionsArguments.class, "column", UINT64 },
			{ CompletionsArguments.class, "line", UINT64 },
			{ ReadMemoryArguments.class, "count", UINT64 },
			{ ReadMemoryResponse.class, "unreadableBytes", UINT64 },
			{ LocationsResponse.class, "line", UINT64 },
			{ LocationsResponse.class, "column", UINT64 },
			{ LocationsResponse.class, "endLine", UINT64 },
			{ LocationsResponse.class, "endColumn", UINT64 },
			{ StackFrame.class, "line", UINT64 },
			{ StackFrame.class, "column", UINT64 },
			{ StackFrame.class, "endLine", UINT64 },
			{ StackFrame.class, "endColumn", UINT64 },
			{ Scope.class, "line", UINT64 },
			{ Scope.class, "column", UINT64 },
			{ Scope.class, "endLine", UINT64 },
			{ Scope.class, "endColumn", UINT64 },
			{ BreakpointLocation.class, "line", UINT64 },
			{ BreakpointLocation.class, "column", UINT64 },
			{ BreakpointLocation.class, "endLine", UINT64 },
			{ BreakpointLocation.class, "endColumn", UINT64 },
			{ SourceBreakpoint.class, "line", UINT64 },
			{ SourceBreakpoint.class, "column", UINT64 },
			{ Breakpoint.class, "line", UINT64 },
			{ Breakpoint.class, "column", UINT64 },
			{ Breakpoint.class, "endLine", UINT64 },
			{ Breakpoint.class, "endColumn", UINT64 },
			{ StepInTarget.class, "line", UINT64 },
			{ StepInTarget.class, "column", UINT64 },
			{ StepInTarget.class, "endLine", UINT64 },
			{ StepInTarget.class, "endColumn", UINT64 },
			{ GotoTarget.class, "line", UINT64 },
			{ GotoTarget.class, "column", UINT64 },
			{ GotoTarget.class, "endLine", UINT64 },
			{ GotoTarget.class, "endColumn", UINT64 },
			{ DisassembledInstruction.class, "line", UINT64 },
			{ DisassembledInstruction.class, "column", UINT64 },
			{ DisassembledInstruction.class, "endLine", UINT64 },
			{ DisassembledInstruction.class, "endColumn", UINT64 },
			{ ProcessEventArguments.class, "pointerSize", UINT32 },
			{ DataBreakpointInfoArguments.class, "bytes", UINT32 },
			{ StackTraceArguments.class, "startFrame", UINT32 },
			{ StackTraceArguments.class, "levels", UINT32 },
			{ StackTraceResponse.class, "totalFrames", UINT32 },
			{ VariablesArguments.class, "start", UINT32 },
			{ VariablesArguments.class, "count", UINT32 },
			{ ModulesArguments.class, "moduleCount", UINT32 },
			{ WriteMemoryResponse.class, "bytesWritten", UINT32 },
			{ DisassembleArguments.class, "instructionCount", UINT32 },
			{ ColumnDescriptor.class, "width", UINT32 },
			{ CompletionItem.class, "start", UINT32 },
			{ CompletionItem.class, "length", UINT32 },
			{ CompletionItem.class, "selectionStart", UINT32 },
			{ CompletionItem.class, "selectionLength", UINT32 },
		});
	}

	@Parameter(0)
	public Class<?> type;

	@Parameter(1)
	public String property;

	@Parameter(2)
	public long[] limits;

	@Test
	public void theLimitsOfTheSizeAreReadAndWritten() throws Exception {
		boolean array = type.getDeclaredField(property).getType().isArray();
		for (long limit : limits) {
			JsonElement value = array ? arrayOf(limit) : new JsonPrimitive(limit);
			JsonObject json = new JsonObject();
			json.add(property, value);
			// A message's arguments are read from its text, or from a JSON tree when they come before its command
			for (Object object : List.of(GSON.fromJson(json.toString(), type), GSON.fromJson(json, type))) {
				assertEquals(value, GSON.toJsonTree(object).getAsJsonObject().get(property));
			}
		}
	}

	private static JsonArray arrayOf(long element) {
		JsonArray array = new JsonArray();
		array.add(element);
		return array;
	}
}
