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

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.debug.BreakpointEventArguments;
import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.CapabilitiesEventArguments;
import org.eclipse.lsp4j.debug.ContinuedEventArguments;
import org.eclipse.lsp4j.debug.ExitedEventArguments;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.InvalidatedEventArguments;
import org.eclipse.lsp4j.debug.LoadedSourceEventArguments;
import org.eclipse.lsp4j.debug.MemoryEventArguments;
import org.eclipse.lsp4j.debug.ModuleEventArguments;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.ProcessEventArguments;
import org.eclipse.lsp4j.debug.ProgressEndEventArguments;
import org.eclipse.lsp4j.debug.ProgressStartEventArguments;
import org.eclipse.lsp4j.debug.ProgressUpdateEventArguments;
import org.eclipse.lsp4j.debug.RunInTerminalRequestArguments;
import org.eclipse.lsp4j.debug.RunInTerminalResponse;
import org.eclipse.lsp4j.debug.StartDebuggingRequestArguments;
import org.eclipse.lsp4j.debug.StoppedEventArguments;
import org.eclipse.lsp4j.debug.TerminatedEventArguments;
import org.eclipse.lsp4j.debug.ThreadEventArguments;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

/**
 * Declaration of client notifications for the
 * <a href="https://microsoft.github.io/debug-adapter-protocol/">Debug Adapter
 * Protocol</a>
 */
public interface IDebugProtocolClient {
	/**
	 * Version of Debug Protocol
	 */
	String SCHEMA_VERSION = "1.60.0";

	/**
	 * This event indicates that the debug adapter is ready to accept configuration
	 * requests (e.g. `setBreakpoints`, `setExceptionBreakpoints`).
	 * <p>
	 * A debug adapter is expected to send this event when it is ready to accept
	 * configuration requests (but not before the 'initialize' request has
	 * finished).
	 * <p>
	 * The sequence of events/requests is as follows:
	 * <ul>
	 * <li>adapters sends 'initialized' event (after the 'initialize' request has
	 * returned)</li>
	 * <li>client sends zero or more 'setBreakpoints' requests</li>
	 * <li>client sends one 'setFunctionBreakpoints' request (if corresponding capability
	 * {@link Capabilities#getSupportsFunctionBreakpoints} is true)</li>
	 * <li>client sends a 'setExceptionBreakpoints' request if one or more
	 * 'exceptionBreakpointFilters' have been defined (or if
	 * {@link Capabilities#getSupportsConfigurationDoneRequest} is not true)</li>
	 * <li>client sends other future configuration requests</li>
	 * <li>client sends one 'configurationDone' request to indicate the end of the
	 * configuration.</li>
	 * </ul>
	 */
	@JsonNotification
	default void initialized() {
	}

	/**
	 * The event indicates that the execution of the debuggee has stopped due to
	 * some condition.
	 * <p>
	 * This can be caused by a breakpoint previously set, a stepping request has
	 * completed, by executing a debugger statement etc.
	 */
	@JsonNotification
	default void stopped(StoppedEventArguments args) {
	}

	/**
	 * The event indicates that the execution of the debuggee has continued.
	 * <p>
	 * Please note: a debug adapter is not expected to send this event in response
	 * to a request that implies that execution continues, e.g. 'launch' or
	 * 'continue'.
	 * <p>
	 * It is only necessary to send a 'continued' event if there was no previous
	 * request that implied this.
	 */
	@JsonNotification
	default void continued(ContinuedEventArguments args) {
	}

	/**
	 * The event indicates that the debuggee has exited and returns its exit code.
	 */
	@JsonNotification
	default void exited(ExitedEventArguments args) {
	}

	/**
	 * The event indicates that debugging of the debuggee has terminated. This does
	 * **not** mean that the debuggee itself has exited.
	 */
	@JsonNotification
	default void terminated(TerminatedEventArguments args) {
	}

	/**
	 * The event indicates that a thread has started or exited.
	 */
	@JsonNotification
	default void thread(ThreadEventArguments args) {
	}

	/**
	 * The event indicates that the target has produced some output.
	 */
	@JsonNotification
	default void output(OutputEventArguments args) {
	}

	/**
	 * The event indicates that some information about a breakpoint has changed.
	 */
	@JsonNotification
	default void breakpoint(BreakpointEventArguments args) {
	}

	/**
	 * The event indicates that some information about a module has changed.
	 */
	@JsonNotification
	default void module(ModuleEventArguments args) {
	}

	/**
	 * The event indicates that some source has been added, changed, or removed from
	 * the set of all loaded sources.
	 */
	@JsonNotification
	default void loadedSource(LoadedSourceEventArguments args) {
	}

	/**
	 * The event indicates that the debugger has begun debugging a new process.
	 * Either one that it has launched, or one that it has attached to.
	 */
	@JsonNotification
	default void process(ProcessEventArguments args) {
	}

	/**
	 * The event indicates that one or more capabilities have changed.
	 * <p>
	 * Since the capabilities are dependent on the client and its UI, it might not
	 * be possible to change that at random times (or too late).
	 * <p>
	 * Consequently this event has a hint characteristic: a client can only be
	 * expected to make a 'best effort' in honoring individual capabilities but
	 * there are no guarantees.
	 * <p>
	 * Only changed capabilities need to be included, all other capabilities keep
	 * their values.
	 */
	@JsonNotification
	default void capabilities(CapabilitiesEventArguments args) {
	}

	/**
	 * The event signals that a long running operation is about to start and
	 * provides additional information for the client to set up a corresponding
	 * progress and cancellation UI.
	 * <p>
	 * The client is free to delay the showing of the UI in order to reduce flicker.
	 * <p>
	 * This event should only be sent if the corresponding capability
	 * {@link InitializeRequestArguments#getSupportsProgressReporting} is true.
	 */
	@JsonNotification
	default void progressStart(ProgressStartEventArguments args) {
	}

	/**
	 * The event signals that the progress reporting needs to be updated with a new
	 * message and/or percentage.
	 * <p>
	 * The client does not have to update the UI immediately, but the clients needs
	 * to keep track of the message and/or percentage values.
	 * <p>
	 * This event should only be sent if the corresponding capability
	 * {@link InitializeRequestArguments#getSupportsProgressReporting} is true.
	 */
	@JsonNotification
	default void progressUpdate(ProgressUpdateEventArguments args) {
	}

	/**
	 * The event signals the end of the progress reporting with an optional final
	 * message.
	 * <p>
	 * This event should only be sent if the corresponding capability
	 * {@link InitializeRequestArguments#getSupportsProgressReporting} is true.
	 */
	@JsonNotification
	default void progressEnd(ProgressEndEventArguments args) {
	}

	/**
	 * This event signals that some state in the debug adapter has changed and
	 * requires that the client needs to re-render the data snapshot previously
	 * requested.
	 * <p>
	 * Debug adapters do not have to emit this event for runtime changes like
	 * stopped or thread events because in that case the client refetches the new
	 * state anyway. But the event can be used for example to refresh the UI after
	 * rendering formatting has changed in the debug adapter.
	 * <p>
	 * This event should only be sent if the corresponding capability
	 * {@link InitializeRequestArguments#getSupportsInvalidatedEvent} is true.
	 */
	@JsonNotification
	default void invalidated(InvalidatedEventArguments args) {
	}

	/**
	 * This event indicates that some memory range has been updated. It should only
	 * be sent if the corresponding capability {@link InitializeRequestArguments#getSupportsMemoryEvent}
	 * is true.
	 * <p>
	 * Clients typically react to the event by re-issuing a `readMemory` request if they
	 * show the memory identified by the `memoryReference` and if the updated memory range
	 * overlaps the displayed range. Clients should not make assumptions how individual memory
	 * references relate to each other, so they should not assume that they are part of a
	 * single continuous address range and might overlap.
	 * <p>
	 * Debug adapters can use this event to indicate that the contents of a memory range
	 * has changed due to some other request like `setVariable` or `setExpression`.
	 * Debug adapters are not expected to emit this event for each and every memory change of
	 * a running program, because that information is typically not available from debuggers
	 * and it would flood clients with too many events.
	 * <p>
	 * Since 1.49
	 */
	@JsonNotification
	default void memory(MemoryEventArguments args) {
	}

	/**
	 * This request is sent from the debug adapter to the client to run a
	 * command in a terminal.
	 * <p>
	 * This is typically used to launch the debuggee in a terminal provided by the
	 * client.
	 * <p>
	 * This request should only be called if the corresponding client capability
	 * {@link InitializeRequestArguments#getSupportsRunInTerminalRequest} is true.
	 * <p>
	 * Client implementations of runInTerminal are free to run the command however they
	 * choose including issuing the command to a command line interpreter (aka 'shell').
	 * Argument strings passed to the runInTerminal request must arrive verbatim in the
	 * command to be run. As a consequence, clients which use a shell are responsible for
	 * escaping any special shell characters in the argument strings to prevent them from
	 * being interpreted (and modified) by the shell\nSome users may wish to take advantage
	 * of shell processing in the argument strings. For clients which implement runInTerminal
	 * using an intermediary shell, the 'argsCanBeInterpretedByShell' property can be set to
	 * true. In this case the client is requested not to escape any special shell characters
	 * in the argument strings.
	 */
	@JsonRequest
	default CompletableFuture<RunInTerminalResponse> runInTerminal(RunInTerminalRequestArguments args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This request is sent from the debug adapter to the client to start a new debug session
	 * of the same type as the caller.
	 * <p>
	 * This request should only be sent if the corresponding client capability
	 * {@link InitializeRequestArguments#getSupportsStartDebuggingRequest()} is true.
	 * <p>
	 * A client implementation of `startDebugging` should start a new debug session (of the same
	 * type as the caller) in the same way that the caller's session was started. If the client
	 * supports hierarchical debug sessions, the newly created session can be treated as a child
	 * of the caller session.
	 * </p>
	 * Since 1.59
	 */
	@JsonRequest
	default CompletableFuture<Void> startDebugging(StartDebuggingRequestArguments args) {
		throw new UnsupportedOperationException();
	}
}
