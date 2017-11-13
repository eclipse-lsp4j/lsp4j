/*******************************************************************************
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.lsp4j.debug.services;

import org.eclipse.lsp4j.debug.BreakpointEventArguments;
import org.eclipse.lsp4j.debug.ContinuedEventArguments;
import org.eclipse.lsp4j.debug.ExitedEventArguments;
import org.eclipse.lsp4j.debug.LoadedSourceEventArguments;
import org.eclipse.lsp4j.debug.ModuleEventArguments;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.ProcessEventArguments;
import org.eclipse.lsp4j.debug.StoppedEventArguments;
import org.eclipse.lsp4j.debug.TerminatedEventArguments;
import org.eclipse.lsp4j.debug.ThreadEventArguments;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;

/**
 * Declaration of client notifications.
 * <p>
 * Auto-generated from debugProtocol.json schema version 1.24.0. Do not edit
 * manually.
 */
public interface IDebugProtocolClient {
	/**
	 * Version of debugProtocol.json this class was derived from.
	 */
	public static final String SCHEMA_VERSION = "1.24.0";

	/**
	 * Event message for 'initialized' event type.
	 * <p>
	 * This event indicates that the debug adapter is ready to accept configuration
	 * requests (e.g. SetBreakpointsRequest, SetExceptionBreakpointsRequest).
	 * <p>
	 * A debug adapter is expected to send this event when it is ready to accept
	 * configuration requests (but not before the InitializeRequest has finished).
	 * <p>
	 * The sequence of events/requests is as follows:
	 * <ul>
	 * <li>adapters sends InitializedEvent (after the InitializeRequest has
	 * returned)</li>
	 * <li>frontend sends zero or more SetBreakpointsRequest</li>
	 * <li>frontend sends one SetFunctionBreakpointsRequest</li>
	 * <li>frontend sends a SetExceptionBreakpointsRequest if one or more
	 * exceptionBreakpointFilters have been defined (or if
	 * supportsConfigurationDoneRequest is not defined or false)</li>
	 * <li>frontend sends other future configuration requests</li>
	 * <li>frontend sends one ConfigurationDoneRequest to indicate the end of the
	 * configuration</li>
	 * </ul>
	 */
	@JsonNotification
	void initialized();

	/**
	 * Event message for 'stopped' event type.
	 * <p>
	 * The event indicates that the execution of the debuggee has stopped due to
	 * some condition.
	 * <p>
	 * This can be caused by a break point previously set, a stepping action has
	 * completed, by executing a debugger statement etc.
	 */
	@JsonNotification
	void stopped(StoppedEventArguments args);

	/**
	 * Event message for 'continued' event type.
	 * <p>
	 * The event indicates that the execution of the debuggee has continued.
	 * <p>
	 * Please note: a debug adapter is not expected to send this event in response
	 * to a request that implies that execution continues, e.g. 'launch' or
	 * 'continue'.
	 * <p>
	 * It is only necessary to send a ContinuedEvent if there was no previous
	 * request that implied this.
	 */
	@JsonNotification
	void continued(ContinuedEventArguments args);

	/**
	 * Event message for 'exited' event type.
	 * <p>
	 * The event indicates that the debuggee has exited.
	 */
	@JsonNotification
	void exited(ExitedEventArguments args);

	/**
	 * Event message for 'terminated' event types.
	 * <p>
	 * The event indicates that debugging of the debuggee has terminated.
	 */
	@JsonNotification
	void terminated(TerminatedEventArguments args);

	/**
	 * Event message for 'thread' event type.
	 * <p>
	 * The event indicates that a thread has started or exited.
	 */
	@JsonNotification
	void thread(ThreadEventArguments args);

	/**
	 * Event message for 'output' event type.
	 * <p>
	 * The event indicates that the target has produced some output.
	 */
	@JsonNotification
	void output(OutputEventArguments args);

	/**
	 * Event message for 'breakpoint' event type.
	 * <p>
	 * The event indicates that some information about a breakpoint has changed.
	 */
	@JsonNotification
	void breakpoint(BreakpointEventArguments args);

	/**
	 * Event message for 'module' event type.
	 * <p>
	 * The event indicates that some information about a module has changed.
	 */
	@JsonNotification
	void module(ModuleEventArguments args);

	/**
	 * Event message for 'loadedSource' event type.
	 * <p>
	 * The event indicates that some source has been added, changed, or removed from
	 * the set of all loaded sources.
	 */
	@JsonNotification
	void loadedSource(LoadedSourceEventArguments args);

	/**
	 * Event message for 'process' event type.
	 * <p>
	 * The event indicates that the debugger has begun debugging a new process.
	 * Either one that it has launched, or one that it has attached to.
	 */
	@JsonNotification
	void process(ProcessEventArguments args);
}
