/*******************************************************************************
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.lsp4j.debug.services;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.debug.AttachRequestArguments;
import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.CompletionsArguments;
import org.eclipse.lsp4j.debug.CompletionsResponse;
import org.eclipse.lsp4j.debug.ConfigurationDoneArguments;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ContinueResponse;
import org.eclipse.lsp4j.debug.DisconnectArguments;
import org.eclipse.lsp4j.debug.EvaluateArguments;
import org.eclipse.lsp4j.debug.EvaluateResponse;
import org.eclipse.lsp4j.debug.ExceptionInfoArguments;
import org.eclipse.lsp4j.debug.ExceptionInfoResponse;
import org.eclipse.lsp4j.debug.GotoArguments;
import org.eclipse.lsp4j.debug.GotoTargetsArguments;
import org.eclipse.lsp4j.debug.GotoTargetsResponse;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.LoadedSourcesArguments;
import org.eclipse.lsp4j.debug.LoadedSourcesResponse;
import org.eclipse.lsp4j.debug.ModulesArguments;
import org.eclipse.lsp4j.debug.ModulesResponse;
import org.eclipse.lsp4j.debug.NextArguments;
import org.eclipse.lsp4j.debug.PauseArguments;
import org.eclipse.lsp4j.debug.RestartArguments;
import org.eclipse.lsp4j.debug.RestartFrameArguments;
import org.eclipse.lsp4j.debug.ReverseContinueArguments;
import org.eclipse.lsp4j.debug.RunInTerminalRequestArguments;
import org.eclipse.lsp4j.debug.RunInTerminalResponse;
import org.eclipse.lsp4j.debug.ScopesArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetExceptionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetFunctionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetFunctionBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetVariableArguments;
import org.eclipse.lsp4j.debug.SetVariableResponse;
import org.eclipse.lsp4j.debug.SourceArguments;
import org.eclipse.lsp4j.debug.SourceResponse;
import org.eclipse.lsp4j.debug.StackTraceArguments;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StepBackArguments;
import org.eclipse.lsp4j.debug.StepInArguments;
import org.eclipse.lsp4j.debug.StepInTargetsArguments;
import org.eclipse.lsp4j.debug.StepInTargetsResponse;
import org.eclipse.lsp4j.debug.StepOutArguments;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import org.eclipse.lsp4j.debug.VariablesArguments;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

/**
 * Declaration of server requests.
 * <p>
 * Auto-generated from debugProtocol.json schema version 1.25.0. Do not edit
 * manually.
 */
public interface IDebugProtocolServer {
	/**
	 * Version of debugProtocol.json this class was derived from.
	 */
	public static final String SCHEMA_VERSION = "1.25.0";

	/**
	 * runInTerminal request; value of command field is 'runInTerminal'.
	 * <p>
	 * With this request a debug adapter can run a command in a terminal.
	 */
	@JsonRequest
	CompletableFuture<RunInTerminalResponse> runInTerminal(RunInTerminalRequestArguments args);

	/**
	 * Initialize request; value of command field is 'initialize'.
	 */
	@JsonRequest
	CompletableFuture<Capabilities> initialize(InitializeRequestArguments args);

	/**
	 * ConfigurationDone request; value of command field is 'configurationDone'.
	 * <p>
	 * The client of the debug protocol must send this request at the end of the
	 * sequence of configuration requests (which was started by the
	 * InitializedEvent).
	 */
	@JsonRequest
	CompletableFuture<Void> configurationDone(ConfigurationDoneArguments args);

	/**
	 * Launch request; value of command field is 'launch'.
	 */
	@JsonRequest
	CompletableFuture<Void> launch(Map<String, Object> args);

	/**
	 * Attach request; value of command field is 'attach'.
	 */
	@JsonRequest
	CompletableFuture<Void> attach(AttachRequestArguments args);

	/**
	 * Restart request; value of command field is 'restart'.
	 * <p>
	 * Restarts a debug session. If the capability 'supportsRestartRequest' is
	 * missing or has the value false,
	 * <p>
	 * the client will implement 'restart' by terminating the debug adapter first
	 * and then launching it anew.
	 * <p>
	 * A debug adapter can override this default behaviour by implementing a restart
	 * request
	 * <p>
	 * and setting the capability 'supportsRestartRequest' to true.
	 */
	@JsonRequest
	CompletableFuture<Void> restart(RestartArguments args);

	/**
	 * Disconnect request; value of command field is 'disconnect'.
	 */
	@JsonRequest
	CompletableFuture<Void> disconnect(DisconnectArguments args);

	/**
	 * SetBreakpoints request; value of command field is 'setBreakpoints'.
	 * <p>
	 * Sets multiple breakpoints for a single source and clears all previous
	 * breakpoints in that source.
	 * <p>
	 * To clear all breakpoint for a source, specify an empty array.
	 * <p>
	 * When a breakpoint is hit, a StoppedEvent (event type 'breakpoint') is
	 * generated.
	 */
	@JsonRequest
	CompletableFuture<SetBreakpointsResponse> setBreakpoints(SetBreakpointsArguments args);

	/**
	 * SetFunctionBreakpoints request; value of command field is
	 * 'setFunctionBreakpoints'.
	 * <p>
	 * Sets multiple function breakpoints and clears all previous function
	 * breakpoints.
	 * <p>
	 * To clear all function breakpoint, specify an empty array.
	 * <p>
	 * When a function breakpoint is hit, a StoppedEvent (event type 'function
	 * breakpoint') is generated.
	 */
	@JsonRequest
	CompletableFuture<SetFunctionBreakpointsResponse> setFunctionBreakpoints(SetFunctionBreakpointsArguments args);

	/**
	 * SetExceptionBreakpoints request; value of command field is
	 * 'setExceptionBreakpoints'.
	 * <p>
	 * The request configures the debuggers response to thrown exceptions. If an
	 * exception is configured to break, a StoppedEvent is fired (event type
	 * 'exception').
	 */
	@JsonRequest
	CompletableFuture<Void> setExceptionBreakpoints(SetExceptionBreakpointsArguments args);

	/**
	 * Continue request; value of command field is 'continue'.
	 * <p>
	 * The request starts the debuggee to run again.
	 */
	@JsonRequest(value = "continue")
	CompletableFuture<ContinueResponse> continue_(ContinueArguments args);

	/**
	 * Next request; value of command field is 'next'.
	 * <p>
	 * The request starts the debuggee to run again for one step.
	 * <p>
	 * The debug adapter first sends the NextResponse and then a StoppedEvent (event
	 * type 'step') after the step has completed.
	 */
	@JsonRequest
	CompletableFuture<Void> next(NextArguments args);

	/**
	 * StepIn request; value of command field is 'stepIn'.
	 * <p>
	 * The request starts the debuggee to step into a function/method if possible.
	 * <p>
	 * If it cannot step into a target, 'stepIn' behaves like 'next'.
	 * <p>
	 * The debug adapter first sends the StepInResponse and then a StoppedEvent
	 * (event type 'step') after the step has completed.
	 * <p>
	 * If there are multiple function/method calls (or other targets) on the source
	 * line,
	 * <p>
	 * the optional argument 'targetId' can be used to control into which target the
	 * 'stepIn' should occur.
	 * <p>
	 * The list of possible targets for a given source line can be retrieved via the
	 * 'stepInTargets' request.
	 */
	@JsonRequest
	CompletableFuture<Void> stepIn(StepInArguments args);

	/**
	 * StepOut request; value of command field is 'stepOut'.
	 * <p>
	 * The request starts the debuggee to run again for one step.
	 * <p>
	 * The debug adapter first sends the StepOutResponse and then a StoppedEvent
	 * (event type 'step') after the step has completed.
	 */
	@JsonRequest
	CompletableFuture<Void> stepOut(StepOutArguments args);

	/**
	 * StepBack request; value of command field is 'stepBack'.
	 * <p>
	 * The request starts the debuggee to run one step backwards.
	 * <p>
	 * The debug adapter first sends the StepBackResponse and then a StoppedEvent
	 * (event type 'step') after the step has completed. Clients should only call
	 * this request if the capability supportsStepBack is true.
	 */
	@JsonRequest
	CompletableFuture<Void> stepBack(StepBackArguments args);

	/**
	 * ReverseContinue request; value of command field is 'reverseContinue'.
	 * <p>
	 * The request starts the debuggee to run backward. Clients should only call
	 * this request if the capability supportsStepBack is true.
	 */
	@JsonRequest
	CompletableFuture<Void> reverseContinue(ReverseContinueArguments args);

	/**
	 * RestartFrame request; value of command field is 'restartFrame'.
	 * <p>
	 * The request restarts execution of the specified stackframe.
	 * <p>
	 * The debug adapter first sends the RestartFrameResponse and then a
	 * StoppedEvent (event type 'restart') after the restart has completed.
	 */
	@JsonRequest
	CompletableFuture<Void> restartFrame(RestartFrameArguments args);

	/**
	 * Goto request; value of command field is 'goto'.
	 * <p>
	 * The request sets the location where the debuggee will continue to run.
	 * <p>
	 * This makes it possible to skip the execution of code or to executed code
	 * again.
	 * <p>
	 * The code between the current location and the goto target is not executed but
	 * skipped.
	 * <p>
	 * The debug adapter first sends the GotoResponse and then a StoppedEvent (event
	 * type 'goto').
	 */
	@JsonRequest(value = "goto")
	CompletableFuture<Void> goto_(GotoArguments args);

	/**
	 * Pause request; value of command field is 'pause'.
	 * <p>
	 * The request suspenses the debuggee.
	 * <p>
	 * The debug adapter first sends the PauseResponse and then a StoppedEvent
	 * (event type 'pause') after the thread has been paused successfully.
	 */
	@JsonRequest
	CompletableFuture<Void> pause(PauseArguments args);

	/**
	 * StackTrace request; value of command field is 'stackTrace'. The request
	 * returns a stacktrace from the current execution state.
	 */
	@JsonRequest
	CompletableFuture<StackTraceResponse> stackTrace(StackTraceArguments args);

	/**
	 * Scopes request; value of command field is 'scopes'.
	 * <p>
	 * The request returns the variable scopes for a given stackframe ID.
	 */
	@JsonRequest
	CompletableFuture<ScopesResponse> scopes(ScopesArguments args);

	/**
	 * Variables request; value of command field is 'variables'.
	 * <p>
	 * Retrieves all child variables for the given variable reference.
	 * <p>
	 * An optional filter can be used to limit the fetched children to either named
	 * or indexed children.
	 */
	@JsonRequest
	CompletableFuture<VariablesResponse> variables(VariablesArguments args);

	/**
	 * setVariable request; value of command field is 'setVariable'.
	 * <p>
	 * Set the variable with the given name in the variable container to a new
	 * value.
	 */
	@JsonRequest
	CompletableFuture<SetVariableResponse> setVariable(SetVariableArguments args);

	/**
	 * Source request; value of command field is 'source'.
	 * <p>
	 * The request retrieves the source code for a given source reference.
	 */
	@JsonRequest
	CompletableFuture<SourceResponse> source(SourceArguments args);

	/**
	 * Thread request; value of command field is 'threads'.
	 * <p>
	 * The request retrieves a list of all threads.
	 */
	@JsonRequest
	CompletableFuture<ThreadsResponse> threads();

	/**
	 * Modules can be retrieved from the debug adapter with the ModulesRequest which
	 * can either return all modules or a range of modules to support paging.
	 */
	@JsonRequest
	CompletableFuture<ModulesResponse> modules(ModulesArguments args);

	/**
	 * Retrieves the set of all sources currently loaded by the debugged process.
	 */
	@JsonRequest
	CompletableFuture<LoadedSourcesResponse> loadedSources(LoadedSourcesArguments args);

	/**
	 * Evaluate request; value of command field is 'evaluate'.
	 * <p>
	 * Evaluates the given expression in the context of the top most stack frame.
	 * <p>
	 * The expression has access to any variables and arguments that are in scope.
	 */
	@JsonRequest
	CompletableFuture<EvaluateResponse> evaluate(EvaluateArguments args);

	/**
	 * StepInTargets request; value of command field is 'stepInTargets'.
	 * <p>
	 * This request retrieves the possible stepIn targets for the specified stack
	 * frame.
	 * <p>
	 * These targets can be used in the 'stepIn' request.
	 * <p>
	 * The StepInTargets may only be called if the 'supportsStepInTargetsRequest'
	 * capability exists and is true.
	 */
	@JsonRequest
	CompletableFuture<StepInTargetsResponse> stepInTargets(StepInTargetsArguments args);

	/**
	 * GotoTargets request; value of command field is 'gotoTargets'.
	 * <p>
	 * This request retrieves the possible goto targets for the specified source
	 * location.
	 * <p>
	 * These targets can be used in the 'goto' request.
	 * <p>
	 * The GotoTargets request may only be called if the
	 * 'supportsGotoTargetsRequest' capability exists and is true.
	 */
	@JsonRequest
	CompletableFuture<GotoTargetsResponse> gotoTargets(GotoTargetsArguments args);

	/**
	 * CompletionsRequest request; value of command field is 'completions'.
	 * <p>
	 * Returns a list of possible completions for a given caret position and text.
	 * <p>
	 * The CompletionsRequest may only be called if the 'supportsCompletionsRequest'
	 * capability exists and is true.
	 */
	@JsonRequest
	CompletableFuture<CompletionsResponse> completions(CompletionsArguments args);

	/**
	 * ExceptionInfoRequest request; value of command field is 'exceptionInfo'.
	 * <p>
	 * Retrieves the details of the exception that caused the StoppedEvent to be
	 * raised.
	 */
	@JsonRequest
	CompletableFuture<ExceptionInfoResponse> exceptionInfo(ExceptionInfoArguments args);
}
