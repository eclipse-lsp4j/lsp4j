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

package org.eclipse.lsp4j.debug.services;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
import org.eclipse.lsp4j.debug.SetExpressionArguments;
import org.eclipse.lsp4j.debug.SetExpressionResponse;
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
import org.eclipse.lsp4j.debug.TerminateArguments;
import org.eclipse.lsp4j.debug.TerminateThreadsArguments;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import org.eclipse.lsp4j.debug.VariablesArguments;
import org.eclipse.lsp4j.debug.VariablesResponse;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

/**
 * Declaration of server requests.
 * <p>
 * Auto-generated from debugProtocol.json schema version 1.31.0. Do not edit
 * manually.
 */
public interface IDebugProtocolServer {
	/**
	 * Version of debugProtocol.json this class was derived from.
	 */
	public static final String SCHEMA_VERSION = "1.31.0";

	/**
	 * This request is sent from the debug adapter to the client to run a command in
	 * a terminal. This is typically used to launch the debuggee in a terminal
	 * provided by the client.
	 */
	@JsonRequest
	CompletableFuture<RunInTerminalResponse> runInTerminal(RunInTerminalRequestArguments args);

	/**
	 * The 'initialize' request is sent as the first request from the client to the
	 * debug adapter in order to configure it with client capabilities and to
	 * retrieve capabilities from the debug adapter.
	 * <p>
	 * Until the debug adapter has responded to with an 'initialize' response, the
	 * client must not send any additional requests or events to the debug adapter.
	 * In addition the debug adapter is not allowed to send any requests or events
	 * to the client until it has responded with an 'initialize' response.
	 * <p>
	 * The 'initialize' request may only be sent once.
	 */
	@JsonRequest
	CompletableFuture<Capabilities> initialize(InitializeRequestArguments args);

	/**
	 * The client of the debug protocol must send this request at the end of the
	 * sequence of configuration requests (which was started by the 'initialized'
	 * event).
	 */
	@JsonRequest
	CompletableFuture<Void> configurationDone(ConfigurationDoneArguments args);

	/**
	 * The launch request is sent from the client to the debug adapter to start the
	 * debuggee with or without debugging (if 'noDebug' is true). Since launching is
	 * debugger/runtime specific, the arguments for this request are not part of
	 * this specification.
	 */
	@JsonRequest
	CompletableFuture<Void> launch(Map<String, Object> args);

	/**
	 * The attach request is sent from the client to the debug adapter to attach to
	 * a debuggee that is already running. Since attaching is debugger/runtime
	 * specific, the arguments for this request are not part of this specification.
	 */
	@JsonRequest
	CompletableFuture<Void> attach(Map<String, Object> args);

	/**
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
	 * The 'disconnect' request is sent from the client to the debug adapter in
	 * order to stop debugging. It asks the debug adapter to disconnect from the
	 * debuggee and to terminate the debug adapter. If the debuggee has been started
	 * with the 'launch' request, the 'disconnect' request terminates the debuggee.
	 * If the 'attach' request was used to connect to the debuggee, 'disconnect'
	 * does not terminate the debuggee. This behavior can be controlled with the
	 * 'terminateDebuggee' (if supported by the debug adapter).
	 */
	@JsonRequest
	CompletableFuture<Void> disconnect(DisconnectArguments args);

	/**
	 * The 'terminate' request is sent from the client to the debug adapter in order
	 * to give the debuggee a chance for terminating itself.
	 */
	@JsonRequest
	CompletableFuture<Void> terminate(TerminateArguments args);

	/**
	 * Sets multiple breakpoints for a single source and clears all previous
	 * breakpoints in that source.
	 * <p>
	 * To clear all breakpoint for a source, specify an empty array.
	 * <p>
	 * When a breakpoint is hit, a 'stopped' event (with reason 'breakpoint') is
	 * generated.
	 */
	@JsonRequest
	CompletableFuture<SetBreakpointsResponse> setBreakpoints(SetBreakpointsArguments args);

	/**
	 * Sets multiple function breakpoints and clears all previous function
	 * breakpoints.
	 * <p>
	 * To clear all function breakpoint, specify an empty array.
	 * <p>
	 * When a function breakpoint is hit, a 'stopped' event (event type 'function
	 * breakpoint') is generated.
	 */
	@JsonRequest
	CompletableFuture<SetFunctionBreakpointsResponse> setFunctionBreakpoints(SetFunctionBreakpointsArguments args);

	/**
	 * The request configures the debuggers response to thrown exceptions. If an
	 * exception is configured to break, a 'stopped' event is fired (with reason
	 * 'exception').
	 */
	@JsonRequest
	CompletableFuture<Void> setExceptionBreakpoints(SetExceptionBreakpointsArguments args);

	/**
	 * The request starts the debuggee to run again.
	 */
	@JsonRequest(value = "continue")
	CompletableFuture<ContinueResponse> continue_(ContinueArguments args);

	/**
	 * The request starts the debuggee to run again for one step.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'step') after the step has completed.
	 */
	@JsonRequest
	CompletableFuture<Void> next(NextArguments args);

	/**
	 * The request starts the debuggee to step into a function/method if possible.
	 * <p>
	 * If it cannot step into a target, 'stepIn' behaves like 'next'.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'step') after the step has completed.
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
	 * The request starts the debuggee to run again for one step.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'step') after the step has completed.
	 */
	@JsonRequest
	CompletableFuture<Void> stepOut(StepOutArguments args);

	/**
	 * The request starts the debuggee to run one step backwards.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'step') after the step has completed. Clients should only call this
	 * request if the capability 'supportsStepBack' is true.
	 */
	@JsonRequest
	CompletableFuture<Void> stepBack(StepBackArguments args);

	/**
	 * The request starts the debuggee to run backward. Clients should only call
	 * this request if the capability 'supportsStepBack' is true.
	 */
	@JsonRequest
	CompletableFuture<Void> reverseContinue(ReverseContinueArguments args);

	/**
	 * The request restarts execution of the specified stackframe.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'restart') after the restart has completed.
	 */
	@JsonRequest
	CompletableFuture<Void> restartFrame(RestartFrameArguments args);

	/**
	 * The request sets the location where the debuggee will continue to run.
	 * <p>
	 * This makes it possible to skip the execution of code or to executed code
	 * again.
	 * <p>
	 * The code between the current location and the goto target is not executed but
	 * skipped.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event with
	 * reason 'goto'.
	 */
	@JsonRequest(value = "goto")
	CompletableFuture<Void> goto_(GotoArguments args);

	/**
	 * The request suspenses the debuggee.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'pause') after the thread has been paused successfully.
	 */
	@JsonRequest
	CompletableFuture<Void> pause(PauseArguments args);

	/**
	 * The request returns a stacktrace from the current execution state.
	 */
	@JsonRequest
	CompletableFuture<StackTraceResponse> stackTrace(StackTraceArguments args);

	/**
	 * The request returns the variable scopes for a given stackframe ID.
	 */
	@JsonRequest
	CompletableFuture<ScopesResponse> scopes(ScopesArguments args);

	/**
	 * Retrieves all child variables for the given variable reference.
	 * <p>
	 * An optional filter can be used to limit the fetched children to either named
	 * or indexed children.
	 */
	@JsonRequest
	CompletableFuture<VariablesResponse> variables(VariablesArguments args);

	/**
	 * Set the variable with the given name in the variable container to a new
	 * value.
	 */
	@JsonRequest
	CompletableFuture<SetVariableResponse> setVariable(SetVariableArguments args);

	/**
	 * The request retrieves the source code for a given source reference.
	 */
	@JsonRequest
	CompletableFuture<SourceResponse> source(SourceArguments args);

	/**
	 * The request retrieves a list of all threads.
	 */
	@JsonRequest
	CompletableFuture<ThreadsResponse> threads();

	/**
	 * The request terminates the threads with the given ids.
	 */
	@JsonRequest
	CompletableFuture<Void> terminateThreads(TerminateThreadsArguments args);

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
	 * Evaluates the given expression in the context of the top most stack frame.
	 * <p>
	 * The expression has access to any variables and arguments that are in scope.
	 */
	@JsonRequest
	CompletableFuture<EvaluateResponse> evaluate(EvaluateArguments args);

	/**
	 * Evaluates the given 'value' expression and assigns it to the 'expression'
	 * which must be a modifiable l-value.
	 * <p>
	 * The expressions have access to any variables and arguments that are in scope
	 * of the specified frame.
	 */
	@JsonRequest
	CompletableFuture<SetExpressionResponse> setExpression(SetExpressionArguments args);

	/**
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
	 * Returns a list of possible completions for a given caret position and text.
	 * <p>
	 * The CompletionsRequest may only be called if the 'supportsCompletionsRequest'
	 * capability exists and is true.
	 */
	@JsonRequest
	CompletableFuture<CompletionsResponse> completions(CompletionsArguments args);

	/**
	 * Retrieves the details of the exception that caused this event to be raised.
	 */
	@JsonRequest
	CompletableFuture<ExceptionInfoResponse> exceptionInfo(ExceptionInfoArguments args);
}
