/******************************************************************************
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
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

import org.eclipse.lsp4j.debug.BreakpointLocationsArguments;
import org.eclipse.lsp4j.debug.BreakpointLocationsResponse;
import org.eclipse.lsp4j.debug.CancelArguments;
import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.CompletionsArguments;
import org.eclipse.lsp4j.debug.CompletionsResponse;
import org.eclipse.lsp4j.debug.ConfigurationDoneArguments;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ContinueResponse;
import org.eclipse.lsp4j.debug.DataBreakpointInfoArguments;
import org.eclipse.lsp4j.debug.DataBreakpointInfoResponse;
import org.eclipse.lsp4j.debug.DisassembleArguments;
import org.eclipse.lsp4j.debug.DisassembleResponse;
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
import org.eclipse.lsp4j.debug.ReadMemoryArguments;
import org.eclipse.lsp4j.debug.ReadMemoryResponse;
import org.eclipse.lsp4j.debug.RestartArguments;
import org.eclipse.lsp4j.debug.RestartFrameArguments;
import org.eclipse.lsp4j.debug.ReverseContinueArguments;
import org.eclipse.lsp4j.debug.ScopesArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetDataBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetDataBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetExceptionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetExceptionBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetExpressionArguments;
import org.eclipse.lsp4j.debug.SetExpressionResponse;
import org.eclipse.lsp4j.debug.SetFunctionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetFunctionBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetInstructionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetInstructionBreakpointsResponse;
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
import org.eclipse.lsp4j.debug.WriteMemoryArguments;
import org.eclipse.lsp4j.debug.WriteMemoryResponse;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

/**
 * Declaration of server requests for the
 * <a href="https://microsoft.github.io/debug-adapter-protocol/">Debug Adapter
 * Protocol</a>
 */
public interface IDebugProtocolServer {
	/**
	 * Version of Debug Protocol
	 */
	String SCHEMA_VERSION = "1.60.0";

	/**
	 * The 'cancel' request is used by the client in two situations:
	 * <ul>
	 * <li>to indicate that it is no longer interested in the result produced by a
	 * specific request issued earlier</li>
	 * <li>to cancel a progress sequence.</li>
	 * </ul>
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsCancelRequest} is true.
	 * <p>
	 * This request has a hint characteristic: a debug adapter can only be expected
	 * to make a 'best effort' in honoring this request but there are no
	 * guarantees.
	 * <p>
	 * The 'cancel' request may return an error if it could not cancel an operation
	 * but a client should refrain from presenting this error to end users.
	 * <p>
	 * The request that got cancelled still needs to send a response back. This can
	 * either be a normal result ('success' attribute true) or an error response
	 * ('success' attribute false and the 'message' set to 'cancelled').
	 * <p>
	 * Returning partial results from a cancelled request is possible but please
	 * note that a client has no generic way for detecting that a response
	 * is partial or not.
	 * <p>
	 * The progress that got cancelled still needs to send a 'progressEnd' event
	 * back.
	 * <p>
	 * A client should not assume that progress just got cancelled after sending the
	 * 'cancel' request.
	 */
	@JsonRequest
	default CompletableFuture<Void> cancel(CancelArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The 'initialize' request is sent as the first request from the client to the
	 * debug adapter in order to configure it with client capabilities and to retrieve
	 * capabilities from the debug adapter.
	 * <p>
	 * Until the debug adapter has responded with an 'initialize' response, the
	 * client must not send any additional requests or events to the debug adapter.
	 * <p>
	 * In addition the debug adapter is not allowed to send any requests or events
	 * to the client until it has responded with an 'initialize' response.
	 * <p>
	 * The 'initialize' request may only be sent once.
	 */
	@JsonRequest
	default CompletableFuture<Capabilities> initialize(InitializeRequestArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This request indicates that the client has finished initialization
	 * of the debug adapter.
	 * <p>
	 * So it is the last request in the sequence of configuration requests (which
	 * was started by the 'initialized' event).
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsConfigurationDoneRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<Void> configurationDone(ConfigurationDoneArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This launch request is sent from the client to the debug adapter to start the
	 * debuggee with or without debugging (if 'noDebug' is true).
	 * <p>
	 * Since launching is debugger/runtime specific, the arguments for this request
	 * are not part of this specification.
	 */
	@JsonRequest
	default CompletableFuture<Void> launch(Map<String, Object> args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The attach request is sent from the client to the debug adapter to attach to
	 * a debuggee that is already running.
	 * <p>
	 * Since attaching is debugger/runtime specific, the arguments for this request
	 * are not part of this specification.
	 */
	@JsonRequest
	default CompletableFuture<Void> attach(Map<String, Object> args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Restarts a debug session. Clients should only call this request if the
	 * corresponding capability {@link Capabilities#getSupportsRestartRequest} is true.
	 * <p>
	 * If the capability is missing or has the value false, a typical client
	 * emulates 'restart' by terminating the debug adapter first and then launching
	 * it anew.
	 */
	@JsonRequest
	default CompletableFuture<Void> restart(RestartArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The 'disconnect' request asks the debug adapter to disconnect from the debuggee
	 * (thus ending the debug session) and then to shut down itself (the debug adapter).
	 * <p>
	 * In addition, the debug adapter must terminate the debuggee if it was started with
	 * the 'launch' request. If an 'attach' request was used to connect to the debuggee,
	 * then the debug adapter must not terminate the debuggee.
	 * <p>
	 * This implicit behavior of when to terminate the debuggee can be overridden with
	 * the 'terminateDebuggee' argument (which is only supported by a debug adapter
	 * if the corresponding capability {@link Capabilities#getSupportTerminateDebuggee} is true).
	 */
	@JsonRequest
	default CompletableFuture<Void> disconnect(DisconnectArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The 'terminate' request is sent from the client to the debug adapter in order to
	 * shut down the debuggee gracefully. Clients should only call this request if the
	 * corresponding capability {@link Capabilities#getSupportsTerminateRequest} is true.
	 * <p>
	 * Typically a debug adapter implements 'terminate' by sending a software signal
	 * which the debuggee intercepts in order to clean things up properly before terminating itself.
	 * <p>
	 * Please note that this request does not directly affect the state of the debug
	 * session: if the debuggee decides to veto the graceful shutdown for any reason
	 * by not terminating itself, then the debug session just continues.
	 * <p>
	 * Clients can surface the 'terminate' request as an explicit command or they can
	 * integrate it into a two stage Stop command that first sends 'terminate' to request
	 * a graceful shutdown, and if that fails uses 'disconnect' for a forceful shutdown.
	 */
	@JsonRequest
	default CompletableFuture<Void> terminate(TerminateArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The 'breakpointLocations' request returns all possible locations for source
	 * breakpoints in a given range.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsBreakpointLocationsRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<BreakpointLocationsResponse> breakpointLocations(BreakpointLocationsArguments args) {
		throw new UnsupportedOperationException();
	}

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
	default CompletableFuture<SetBreakpointsResponse> setBreakpoints(SetBreakpointsArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Replaces all existing function breakpoints with new function breakpoints.
	 * <p>
	 * To clear all function breakpoints, specify an empty array.
	 * <p>
	 * When a function breakpoint is hit, a 'stopped' event (with reason 'function
	 * breakpoint') is generated.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsFunctionBreakpoints} is true.
	 */
	@JsonRequest
	default CompletableFuture<SetFunctionBreakpointsResponse> setFunctionBreakpoints(
			SetFunctionBreakpointsArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request configures the debugger's response to thrown exceptions.
	 * Each of the `filters`, `filterOptions`, and `exceptionOptions` in the request are independent
	 * configurations to a debug adapter indicating a kind of exception to catch.
	 * An exception thrown in a program should result in a `stopped` event from the debug adapter
	 * (with reason `exception`) if any of the configured filters match.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getExceptionBreakpointFilters} returns one or more filters.
	 */
	@JsonRequest
	default CompletableFuture<SetExceptionBreakpointsResponse> setExceptionBreakpoints(SetExceptionBreakpointsArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Obtains information on a possible data breakpoint that could be set on an
	 * expression or variable.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsDataBreakpoints} is true.
	 */
	@JsonRequest
	default CompletableFuture<DataBreakpointInfoResponse> dataBreakpointInfo(DataBreakpointInfoArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Replaces all existing data breakpoints with new data breakpoints.
	 * <p>
	 * To clear all data breakpoints, specify an empty array.
	 * <p>
	 * When a data breakpoint is hit, a 'stopped' event (with reason 'data
	 * breakpoint') is generated.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsDataBreakpoints} is true.
	 */
	@JsonRequest
	default CompletableFuture<SetDataBreakpointsResponse> setDataBreakpoints(SetDataBreakpointsArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Replaces all existing instruction breakpoints. Typically, instruction
	 * breakpoints would be set from a disassembly window.
	 * <p>
	 * To clear all instruction breakpoints, specify an empty array.
	 * <p>
	 * When an instruction breakpoint is hit, a 'stopped' event (with reason
	 * 'instruction breakpoint') is generated.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsInstructionBreakpoints} is true.
	 */
	@JsonRequest
	default CompletableFuture<SetInstructionBreakpointsResponse> setInstructionBreakpoints(
			SetInstructionBreakpointsArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request resumes execution of all threads. If the debug adapter supports
	 * single thread execution (see capability {@link Capabilities#getSupportsSingleThreadExecutionRequests}),
	 * setting the 'singleThread' argument to true resumes only the specified thread.
	 * If not all threads were resumed, the 'allThreadsContinued' attribute of the response should be set to false.
	 */
	@JsonRequest(value = "continue")
	default CompletableFuture<ContinueResponse> continue_(ContinueArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request executes one step (in the given granularity) for the specified
	 * thread and allows all other threads to run freely by resuming them.
	 * <p>
	 * If the debug adapter supports single thread execution (see capability
	 * {@link Capabilities#getSupportsSingleThreadExecutionRequests}), setting the
	 * 'singleThread' argument to true prevents other suspended threads from resuming.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'step') after the step has completed.
	 */
	@JsonRequest
	default CompletableFuture<Void> next(NextArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request resumes the given thread to step into a function/method and allows all
	 * other threads to run freely by resuming them.
	 * <p>
	 * If the debug adapter supports single thread execution (see capability
	 * {@link Capabilities#getSupportsSingleThreadExecutionRequests}), setting the
	 * 'singleThread' argument to true prevents other suspended threads from resuming.
	 * <p>
	 * If the request cannot step into a target, 'stepIn' behaves like the 'next' request.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'step') after the step has completed.
	 * <p>
	 * If there are multiple function/method calls (or other targets) on the source line,
	 * the argument 'targetId' can be used to control into which target the
	 * 'stepIn' should occur.
	 * <p>
	 * The list of possible targets for a given source line can be retrieved via the
	 * 'stepInTargets' request.
	 */
	@JsonRequest
	default CompletableFuture<Void> stepIn(StepInArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request resumes the given thread to step out (return) from a function/method and
	 * allows all other threads to run freely by resuming them.
	 * <p>
	 * If the debug adapter supports single thread execution (see capability
	 * {@link Capabilities#getSupportsSingleThreadExecutionRequests}), setting the
	 * 'singleThread' argument to true prevents other suspended threads from resuming.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'step') after the step has completed.
	 */
	@JsonRequest
	default CompletableFuture<Void> stepOut(StepOutArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request executes one backward step (in the given granularity) for the specified
	 * thread and allows all other threads to run backward freely by resuming them.
	 * <p>
	 * If the debug adapter supports single thread execution (see capability
	 * {@link Capabilities#getSupportsSingleThreadExecutionRequests}), setting the
	 * 'singleThread' argument to true prevents other suspended threads from resuming.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'step') after the step has completed.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsStepBack} is true.
	 */
	@JsonRequest
	default CompletableFuture<Void> stepBack(StepBackArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request resumes backward execution of all threads. If the debug adapter supports
	 * single thread execution (see capability {@link Capabilities#getSupportsSingleThreadExecutionRequests}),
	 * setting the 'singleThread' argument to true resumes only the specified thread. If not
	 * all threads were resumed, the 'allThreadsContinued' attribute of the response should be set to false.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsStepBack} is true.
	 */
	@JsonRequest
	default CompletableFuture<Void> reverseContinue(ReverseContinueArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request restarts execution of the specified stack frame.
	 * <p>
	 * The debug adapter first sends the response and then a `stopped` event (with
	 * reason `restart`) after the restart has completed.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsRestartFrame} is true.
	 */
	@JsonRequest
	default CompletableFuture<Void> restartFrame(RestartFrameArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request sets the location where the debuggee will continue to run.
	 * <p>
	 * This makes it possible to skip the execution of code or to execute code
	 * again.
	 * <p>
	 * The code between the current location and the goto target is not executed but
	 * skipped.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event with
	 * reason 'goto'.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsGotoTargetsRequest} is true (because only then
	 * goto targets exist that can be passed as arguments).
	 */
	@JsonRequest(value = "goto")
	default CompletableFuture<Void> goto_(GotoArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request suspends the debuggee.
	 * <p>
	 * The debug adapter first sends the response and then a 'stopped' event (with
	 * reason 'pause') after the thread has been paused successfully.
	 */
	@JsonRequest
	default CompletableFuture<Void> pause(PauseArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request returns a stacktrace from the current execution state of a given
	 * thread.
	 * <p>
	 * A client can request all stack frames by omitting the startFrame and levels
	 * arguments. For performance-conscious clients and if the corresponding capability
	 * {@link Capabilities#getSupportsDelayedStackTraceLoading} is true, stack frames can
	 * be retrieved in a piecemeal way with the startFrame and levels arguments. The
	 * response of the stackTrace request may contain a totalFrames property that
	 * hints at the total number of frames in the stack. If a client needs this
	 * total number upfront, it can issue a request for a single (first) frame and
	 * depending on the value of totalFrames decide how to proceed. In any case a
	 * client should be prepared to receive fewer frames than requested, which is an
	 * indication that the end of the stack has been reached.
	 */
	@JsonRequest
	default CompletableFuture<StackTraceResponse> stackTrace(StackTraceArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request returns the variable scopes for a given stack frame ID.
	 */
	@JsonRequest
	default CompletableFuture<ScopesResponse> scopes(ScopesArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Retrieves all child variables for the given variable reference.
	 * <p>
	 * A filter can be used to limit the fetched children to either named
	 * or indexed children.
	 */
	@JsonRequest
	default CompletableFuture<VariablesResponse> variables(VariablesArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Set the variable with the given name in the variable container to a new
	 * value. Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsSetVariable} is true.
	 * <p>
	 * If a debug adapter implements both setVariable and setExpression, a client
	 * only uses setExpression if the variable has an evaluateName property.
	 */
	@JsonRequest
	default CompletableFuture<SetVariableResponse> setVariable(SetVariableArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request retrieves the source code for a given source reference.
	 */
	@JsonRequest
	default CompletableFuture<SourceResponse> source(SourceArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request retrieves a list of all threads.
	 */
	@JsonRequest
	default CompletableFuture<ThreadsResponse> threads() {
		throw new UnsupportedOperationException();
	}

	/**
	 * The request terminates the threads with the given ids.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsTerminateThreadsRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<Void> terminateThreads(TerminateThreadsArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Modules can be retrieved from the debug adapter with this request which can
	 * either return all modules or a range of modules to support paging.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsModulesRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<ModulesResponse> modules(ModulesArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Retrieves the set of all sources currently loaded by the debugged process.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsLoadedSourcesRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<LoadedSourcesResponse> loadedSources(LoadedSourcesArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Evaluates the given expression in the context of the topmost stack frame.
	 * <p>
	 * The expression has access to any variables and arguments that are in scope.
	 */
	@JsonRequest
	default CompletableFuture<EvaluateResponse> evaluate(EvaluateArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Evaluates the given 'value' expression and assigns it to the 'expression'
	 * which must be a modifiable l-value.
	 * <p>
	 * The expressions have access to any variables and arguments that are in scope
	 * of the specified frame.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsSetExpression} is true.
	 * <p>
	 * If a debug adapter implements both setExpression and setVariable, a client
	 * uses setExpression if the variable has an evaluateName property.
	 */
	@JsonRequest
	default CompletableFuture<SetExpressionResponse> setExpression(SetExpressionArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This request retrieves the possible step-in targets for the specified stack
	 * frame.
	 * <p>
	 * These targets can be used in the 'stepIn' request.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsStepInTargetsRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<StepInTargetsResponse> stepInTargets(StepInTargetsArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This request retrieves the possible goto targets for the specified source
	 * location.
	 * <p>
	 * These targets can be used in the 'goto' request.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsGotoTargetsRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<GotoTargetsResponse> gotoTargets(GotoTargetsArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a list of possible completions for a given caret position and text.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsCompletionsRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<CompletionsResponse> completions(CompletionsArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Retrieves the details of the exception that caused this event to be raised.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsExceptionInfoRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<ExceptionInfoResponse> exceptionInfo(ExceptionInfoArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Reads bytes from memory at the provided location.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsReadMemoryRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<ReadMemoryResponse> readMemory(ReadMemoryArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Writes bytes to memory at the provided location.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsWriteMemoryRequest} is true.
	 * <p>
	 * Since 1.48
	 */
	@JsonRequest
	default CompletableFuture<WriteMemoryResponse> writeMemory(WriteMemoryArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Disassembles code stored at the provided location.
	 * <p>
	 * Clients should only call this request if the corresponding capability
	 * {@link Capabilities#getSupportsDisassembleRequest} is true.
	 */
	@JsonRequest
	default CompletableFuture<DisassembleResponse> disassemble(DisassembleArguments args) {
		throw new UnsupportedOperationException();
	}
}
