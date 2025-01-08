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

package org.eclipse.lsp4j.debug;

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.Map
import org.eclipse.lsp4j.debug.adapters.RestartArgumentsArgumentsTypeAdapter
import org.eclipse.lsp4j.generator.JsonRpcData
import org.eclipse.lsp4j.jsonrpc.messages.Either
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

/**
 * Declaration of parameters, response bodies, and event bodies for
 * the <a href="https://microsoft.github.io/debug-adapter-protocol/">Debug Adapter Protocol</a>
 */
class DebugProtocol {
	/**
	 * Version of Debug Protocol
	 */
	public static final String SCHEMA_VERSION = "1.65.0";

	/**
	 * Refer to the Debug Adapter Protocol's
	 * <a href="https://microsoft.github.io/debug-adapter-protocol/overview">Overview</a> on the
	 * specification's website.
	 */
	public static final String Overview = "Overview";
}

/**
 * Arguments for 'cancel' request.
 */
@JsonRpcData
class CancelArguments {
	/**
	 * The ID (attribute 'seq') of the request to cancel. If missing no request is cancelled.
	 * <p>
	 * Both a 'requestId' and a 'progressId' can be specified in one request.
	 * <p>
	 * This is an optional property.
	 */
	Integer requestId;
	/**
	 * The ID (attribute 'progressId') of the progress to cancel. If missing no progress is cancelled.
	 * <p>
	 * Both a 'requestId' and a 'progressId' can be specified in one request.
	 * <p>
	 * This is an optional property.
	 */
	String progressId;
}

/**
 * The event indicates that the execution of the debuggee has stopped due to some condition.
 * <p>
 * This can be caused by a breakpoint previously set, a stepping request has completed, by executing a debugger
 * statement etc.
 * <p>
 * Represents the {@code body} of {@code StoppedEvent} defined in spec.
 */
@JsonRpcData
class StoppedEventArguments {
	/**
	 * The reason for the event.
	 * <p>
	 * For backward compatibility this string is shown in the UI if the 'description' attribute is missing (but it
	 * must not be translated).
	 * <p>
	 * Possible values include - but not limited to those defined in {@link StoppedEventArgumentsReason}
	 */
	@NonNull
	String reason;
	/**
	 * The full reason for the event, e.g. 'Paused on exception'. This string is shown in the UI as is and can be
	 * translated.
	 * <p>
	 * This is an optional property.
	 */
	String description;
	/**
	 * The thread which was stopped.
	 * <p>
	 * This is an optional property.
	 */
	Integer threadId;
	/**
	 * A value of true hints to the client that this event should not change the focus.
	 * <p>
	 * This is an optional property.
	 */
	Boolean preserveFocusHint;
	/**
	 * Additional information. E.g. if reason is 'exception', text contains the exception name. This string is shown
	 * in the UI.
	 * <p>
	 * This is an optional property.
	 */
	String text;
	/**
	 * If 'allThreadsStopped' is true, a debug adapter can announce that all threads have stopped.
	 * <ul>
	 * <li>The client should use this information to enable that all threads can be expanded to access their
	 * stacktraces.</li>
	 * <li>If the attribute is missing or false, only the thread with the given threadId can be expanded.</li>
	 * </ul>
	 * <p>
	 * This is an optional property.
	 */
	Boolean allThreadsStopped;
	/**
	 * Ids of the breakpoints that triggered the event. In most cases there is
	 * only a single breakpoint but here are some examples for multiple
	 * breakpoints:
	 * <ul>
	 * <li>Different types of breakpoints map to the same location.</li>
	 * <li>Multiple source breakpoints get collapsed to the same instruction by
	 * the compiler/runtime.</li>
	 * <li>Multiple function breakpoints with different function names map to the
	 * same location.</li>
	 * </ul>
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.46
	 */
	Integer[] hitBreakpointIds;
}

/**
 * The reason for the event.
 * <p>
 * For backward compatibility this string is shown in the UI if the 'description' attribute is missing (but it
 * must not be translated).
 * <p>
 * Possible values include - but not limited to those defined in {@link StoppedEventArgumentsReason}
 */
interface StoppedEventArgumentsReason {
	public static final String STEP = "step";
	public static final String BREAKPOINT = "breakpoint";
	public static final String EXCEPTION = "exception";
	public static final String PAUSE = "pause";
	public static final String ENTRY = "entry";
	public static final String GOTO = "goto";
	public static final String FUNCTION_BREAKPOINT = "function breakpoint";
	public static final String DATA_BREAKPOINT = "data breakpoint";
	public static final String INSTRUCTION_BREAKPOINT = "instruction breakpoint";
}

/**
 * The event indicates that the execution of the debuggee has continued.
 * <p>
 * Please note: a debug adapter is not expected to send this event in response to a request that implies that
 * execution continues, e.g. 'launch' or 'continue'.
 * <p>
 * It is only necessary to send a 'continued' event if there was no previous request that implied this.
 * <p>
 * Represents the {@code body} of {@code ContinuedEvent} defined in spec.
 */
@JsonRpcData
class ContinuedEventArguments {
	/**
	 * The thread which was continued.
	 */
	int threadId;
	/**
	 * If 'allThreadsContinued' is true, a debug adapter can announce that all threads have continued.
	 * <p>
	 * This is an optional property.
	 */
	Boolean allThreadsContinued;
}

/**
 * The event indicates that the debuggee has exited and returns its exit code.
 * <p>
 * Represents the {@code body} of {@code ExitedEvent} defined in spec.
 */
@JsonRpcData
class ExitedEventArguments {
	/**
	 * The exit code returned from the debuggee.
	 */
	int exitCode;
}

/**
 * The event indicates that debugging of the debuggee has terminated. This does **not** mean that the debuggee
 * itself has exited.
 * <p>
 * Represents the {@code body} of {@code TerminatedEvent} defined in spec.
 */
@JsonRpcData
class TerminatedEventArguments {
	/**
	 * A debug adapter may set 'restart' to true (or to an arbitrary object) to request that the client restarts
	 * the session.
	 * <p>
	 * The value is not interpreted by the client and passed unmodified as an attribute '__restart' to the 'launch'
	 * and 'attach' requests.
	 * <p>
	 * This is an optional property.
	 */
	Object restart;
}

/**
 * The event indicates that a thread has started or exited.
 * <p>
 * Represents the {@code body} of {@code ThreadEvent} defined in spec.
 */
@JsonRpcData
class ThreadEventArguments {
	/**
	 * The reason for the event.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link ThreadEventArgumentsReason}
	 */
	@NonNull
	String reason;
	/**
	 * The identifier of the thread.
	 */
	int threadId;
}

/**
 * The reason for the event.
 * <p>
 * Possible values include - but not limited to those defined in {@link ThreadEventArgumentsReason}
 */
interface ThreadEventArgumentsReason {
	public static final String STARTED = "started";
	public static final String EXITED = "exited";
}

/**
 * The event indicates that the target has produced some output.
 * <p>
 * Represents the {@code body} of {@code OutputEvent} defined in spec.
 */
@JsonRpcData
class OutputEventArguments {
	/**
	 * The output category. If not specified, 'console' is assumed.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link OutputEventArgumentsCategory}
	 */
	String category;
	/**
	 * The output to report.
	 */
	@NonNull
	String output;
	/**
	 * Support for keeping an output log organized by grouping related messages.
	 * <p>
	 * This is an optional property.
	 */
	OutputEventArgumentsGroup group;
	/**
	 * If an attribute `variablesReference` exists and its value is &gt; 0, the output
	 * contains objects which can be retrieved by passing `variablesReference` to the
	 * `variables` request as long as execution remains suspended. See 'Lifetime of
	 * Object References' in the {@link DebugProtocol#Overview} section for details.
	 * <p>
	 * This is an optional property.
	 */
	Integer variablesReference;
	/**
	 * The source location where the output was produced.
	 * <p>
	 * This is an optional property.
	 */
	Source source;
	/**
	 * The source location's line where the output was produced.
	 * <p>
	 * This is an optional property.
	 */
	Integer line;
	/**
	 * The position in `line` where the output was produced. It is measured in UTF-16 code units and the
	 * client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
	/**
	 * Additional data to report. For the 'telemetry' category the data is sent to telemetry, for the other
	 * categories the data is shown in JSON format.
	 * <p>
	 * This is an optional property.
	 */
	Object data;
	/**
	 * A reference that allows the client to request the location where the new value is declared. For example,
	 * if the logged value is function pointer, the adapter may be able to look up the function's location. This should
	 * be present only if the adapter is likely to be able to resolve the location.
	 * <p>
	 * This reference shares the same lifetime as the `variablesReference`.
	 * See 'Lifetime of Object References' in the Overview section for details.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.68
	 */
	Integer locationReference;
}

/**
 * The output category. If not specified or if the category is not
 * understood by the client, 'console' is assumed.
 * <p>
 * Possible values include - but not limited to those defined in {@link OutputEventArgumentsCategory}
 */
interface OutputEventArgumentsCategory {
	/**
	 * Show the output in the client's default message UI, e.g. a
	 * 'debug console'. This category should only be used for informational
	 * output from the debugger (as opposed to the debuggee).
	 */
	public static final String CONSOLE = "console";
	/**
	 * A hint for the client to show the output in the client's UI
	 * for important and highly visible information, e.g. as a popup
	 * notification. This category should only be used for important messages
	 * from the debugger (as opposed to the debuggee). Since this category value
	 * is a hint, clients might ignore the hint and assume the 'console'
	 * category.
	 * <p>
	 * Since 1.51
	 */
	public static final String IMPORTANT = "important";
	/**
	 * Show the output as normal program output from the debuggee.
	 */
	public static final String STDOUT = "stdout";
	/**
	 * Show the output as error program output from the debuggee.
	 */
	public static final String STDERR = "stderr";
	/**
	 * Send the output to telemetry instead of showing it to the
	 * user.
	 */
	public static final String TELEMETRY = "telemetry";
}

/**
 * Support for keeping an output log organized by grouping related messages.
 */
enum OutputEventArgumentsGroup {
	/**
	 * Start a new group in expanded mode. Subsequent output events are members of the group and should be shown
	 * indented.
	 * The 'output' attribute becomes the name of the group and is not indented.
	 */
	START,
	/**
	 * Start a new group in collapsed mode. Subsequent output events are members of the group and should be shown
	 * indented (as soon as the group is expanded).
	 * <p>
	 * The 'output' attribute becomes the name of the group and is not
	 * indented.
	 */
	START_COLLAPSED,
	/**
	 * End the current group and decrease the indentation of subsequent output events.
	 * <p>
	 * A non-empty 'output' attribute is shown as the unindented end of the group.
	 */
	END
}

/**
 * The event indicates that some information about a breakpoint has changed.
 * <p>
 * Represents the {@code body} of {@code BreakpointEvent} defined in spec.
 */
@JsonRpcData
class BreakpointEventArguments {
	/**
	 * The reason for the event.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link BreakpointEventArgumentsReason}
	 */
	@NonNull
	String reason;
	/**
	 * The 'id' attribute is used to find the target breakpoint and the other attributes are used as the new values.
	 */
	@NonNull
	Breakpoint breakpoint;
}

/**
 * The reason for the event.
 * <p>
 * Possible values include - but not limited to those defined in {@link BreakpointEventArgumentsReason}
 */
interface BreakpointEventArgumentsReason {
	public static final String CHANGED = "changed";
	public static final String NEW = "new";
	public static final String REMOVED = "removed";
}

/**
 * The event indicates that some information about a module has changed.
 * <p>
 * Represents the {@code body} of {@code ModuleEvent} defined in spec.
 */
@JsonRpcData
class ModuleEventArguments {
	/**
	 * The reason for the event.
	 */
	@NonNull
	ModuleEventArgumentsReason reason;
	/**
	 * The new, changed, or removed module. In case of 'removed' only the module id is used.
	 */
	@NonNull
	Module module;
}

/**
 * The reason for the event.
 */
enum ModuleEventArgumentsReason {
	NEW,
	CHANGED,
	REMOVED
}

/**
 * The event indicates that some source has been added, changed, or removed from the set of all loaded sources.
 * <p>
 * Represents the {@code body} of {@code LoadedSourceEvent} defined in spec.
 */
@JsonRpcData
class LoadedSourceEventArguments {
	/**
	 * The reason for the event.
	 */
	@NonNull
	LoadedSourceEventArgumentsReason reason;
	/**
	 * The new, changed, or removed source.
	 */
	@NonNull
	Source source;
}

/**
 * The reason for the event.
 */
enum LoadedSourceEventArgumentsReason {
	NEW,
	CHANGED,
	REMOVED
}

/**
 * The event indicates that the debugger has begun debugging a new process. Either one that it has launched, or
 * one that it has attached to.
 * <p>
 * Represents the {@code body} of {@code ProcessEvent} defined in spec.
 */
@JsonRpcData
class ProcessEventArguments {
	/**
	 * The logical name of the process. This is usually the full path to process's executable file. Example:
	 * /home/example/myproj/program.js.
	 */
	@NonNull
	String name;
	/**
	 * The system process id of the debugged process. This property is missing for non-system processes.
	 * <p>
	 * This is an optional property.
	 */
	Integer systemProcessId;
	/**
	 * If true, the process is running on the same computer as the debug adapter.
	 * <p>
	 * This is an optional property.
	 */
	Boolean isLocalProcess;
	/**
	 * Describes how the debug engine started debugging this process.
	 * <p>
	 * This is an optional property.
	 */
	ProcessEventArgumentsStartMethod startMethod;
	/**
	 * The size of a pointer or address for this process, in bits. This value may be used by clients when formatting
	 * addresses for display.
	 * <p>
	 * This is an optional property.
	 */
	Integer pointerSize;
}

/**
 * Describes how the debug engine started debugging this process.
 */
enum ProcessEventArgumentsStartMethod {
	/**
	 * Process was launched under the debugger.
	 */
	LAUNCH,
	/**
	 * Debugger attached to an existing process.
	 */
	ATTACH,
	/**
	 * A project launcher component has launched a new process in a suspended state and then asked the debugger to
	 * attach.
	 */
	ATTACH_FOR_SUSPENDED_LAUNCH
}

/**
 * The event indicates that one or more capabilities have changed.
 * <p>
 * Since the capabilities are dependent on the client and its UI, it might not be possible to change that at
 * random times (or too late).
 * <p>
 * Consequently this event has a hint characteristic: a client can only be expected to make a 'best effort' in
 * honoring individual capabilities but there are no guarantees.
 * <p>
 * Only changed capabilities need to be included, all other capabilities keep their values.
 * <p>
 * Represents the {@code body} of {@code CapabilitiesEvent} defined in spec.
 */
@JsonRpcData
class CapabilitiesEventArguments {
	/**
	 * The set of updated capabilities.
	 */
	@NonNull
	Capabilities capabilities;
}

/**
 * The event signals that a long running operation is about to start and provides additional information
 * for the client to set up a corresponding progress and cancellation UI.
 * <p>
 * The client is free to delay the showing of the UI in order to reduce flicker.
 * <p>
 * This event should only be sent if the corresponding capability 'supportsProgressReporting' is true.
 * <p>
 * Represents the {@code body} of {@code ProgressStartEvent} defined in spec.
 */
@JsonRpcData
class ProgressStartEventArguments {
	/**
	 * An ID that can be used in subsequent 'progressUpdate' and 'progressEnd' events to make them refer to the same
	 * progress reporting.
	 * <p>
	 * IDs must be unique within a debug session.
	 */
	@NonNull
	String progressId;
	/**
	 * Short title of the progress reporting. Shown in the UI to describe the long running operation.
	 */
	@NonNull
	String title;
	/**
	 * The request ID that this progress report is related to. If specified a debug adapter is expected to emit
	 * progress events for the long running request until the request has been either completed or cancelled.
	 * <p>
	 * If the request ID is omitted, the progress report is assumed to be related to some general activity of the
	 * debug adapter.
	 * <p>
	 * This is an optional property.
	 */
	Integer requestId;
	/**
	 * If true, the request that reports progress may be cancelled with a 'cancel' request.
	 * <p>
	 * So this property basically controls whether the client should use UX that supports cancellation.
	 * <p>
	 * Clients that don't support cancellation are allowed to ignore the setting.
	 * <p>
	 * This is an optional property.
	 */
	Boolean cancellable;
	/**
	 * More detailed progress message.
	 * <p>
	 * This is an optional property.
	 */
	String message;
	/**
	 * Progress percentage to display (value range: 0 to 100). If omitted no percentage is shown.
	 * <p>
	 * This is an optional property.
	 */
	Double percentage;
}

/**
 * The event signals that the progress reporting needs to be updated with a new message and/or percentage.
 * <p>
 * The client does not have to update the UI immediately, but the clients needs to keep track of the message
 * and/or percentage values.
 * <p>
 * This event should only be sent if the corresponding capability 'supportsProgressReporting' is true.
 * <p>
 * Represents the {@code body} of {@code ProgressUpdateEvent} defined in spec.
 */
@JsonRpcData
class ProgressUpdateEventArguments {
	/**
	 * The ID that was introduced in the initial 'progressStart' event.
	 */
	@NonNull
	String progressId;
	/**
	 * More detailed progress message. If omitted, the previous message (if any) is used.
	 * <p>
	 * This is an optional property.
	 */
	String message;
	/**
	 * Progress percentage to display (value range: 0 to 100). If omitted no percentage is shown.
	 * <p>
	 * This is an optional property.
	 */
	Double percentage;
}

/**
 * The event signals the end of the progress reporting with a final message.
 * <p>
 * This event should only be sent if the corresponding capability 'supportsProgressReporting' is true.
 * <p>
 * Represents the {@code body} of {@code ProgressEndEvent} defined in spec.
 */
@JsonRpcData
class ProgressEndEventArguments {
	/**
	 * The ID that was introduced in the initial 'ProgressStartEvent'.
	 */
	@NonNull
	String progressId;
	/**
	 * More detailed progress message. If omitted, the previous message (if any) is used.
	 * <p>
	 * This is an optional property.
	 */
	String message;
}

/**
 * This event signals that some state in the debug adapter has changed and requires that the client needs to
 * re-render the data snapshot previously requested.
 * <p>
 * Debug adapters do not have to emit this event for runtime changes like stopped or thread events because in that
 * case the client refetches the new state anyway. But the event can be used for example to refresh the UI after
 * rendering formatting has changed in the debug adapter.
 * <p>
 * This event should only be sent if the corresponding capability 'supportsInvalidatedEvent' is true.
 * <p>
 * Represents the {@code body} of {@code InvalidatedEvent} defined in spec.
 */
@JsonRpcData
class InvalidatedEventArguments {
	/**
	 * Set of logical areas that got invalidated. This property has a hint characteristic: a client can only
	 * be expected to make a 'best effort' in honoring the areas but there are no guarantees. If this property is
	 * missing, empty, or if values are not understood, the client should assume a single value 'all'.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link InvalidatedAreas}
	 */
	String[] areas;
	/**
	 * If specified, the client only needs to refetch data related to this thread.
	 * <p>
	 * This is an optional property.
	 */
	Integer threadId;
	/**
	 * If specified, the client only needs to refetch data related to this stack frame (and the 'threadId' is
	 * ignored).
	 * <p>
	 * This is an optional property.
	 */
	Integer stackFrameId;
}

/**
 * Body for 'memory' event.
 * <p>
 * Represents the {@code body} of {@code MemoryEvent} defined in spec.
 * <p>
 * Since 1.49
 */
@JsonRpcData
class MemoryEventArguments {
	/**
	 * Memory reference of a memory range that has been updated.
	 */
	@NonNull
	String memoryReference;
	/**
	 * Starting offset in bytes where memory has been updated. Can be negative.
	 */
	int offset;
	/**
	 * Number of bytes updated.
	 */
	int count;
}

/**
 * Response to 'runInTerminal' request.
 */
@JsonRpcData
class RunInTerminalResponse {
	/**
	 * The process ID. The value should be less than or equal to 2147483647 (2^31-1).
	 * <p>
	 * This is an optional property.
	 */
	Integer processId;
	/**
	 * The process ID of the terminal shell. The value should be less than or equal to 2147483647 (2^31-1).
	 * <p>
	 * This is an optional property.
	 */
	Integer shellProcessId;
}

/**
 * Arguments for 'runInTerminal' request.
 */
@JsonRpcData
class RunInTerminalRequestArguments {
	/**
	 * What kind of terminal to launch. Defaults to `integrated` if not specified.
	 * <p>
	 * This is an optional property.
	 */
	RunInTerminalRequestArgumentsKind kind;
	/**
	 * Title of the terminal.
	 * <p>
	 * This is an optional property.
	 */
	String title;
	/**
	 * Working directory for the command. For non-empty, valid paths this typically results in execution of a change
	 * directory command.
	 */
	@NonNull
	String cwd;
	/**
	 * List of arguments. The first argument is the command to run.
	 */
	@NonNull
	String[] args;
	/**
	 * Environment key-value pairs that are added to or removed from the default environment.
	 * <p>
	 * A string is a proper value for an environment variable. The value `null` removes the variable from the environment.
	 * <p>
	 * This is an optional property.
	 */
	Map<String, String> env;
	/**
	 * This property should only be set if the corresponding capability
	 * {@link InitializeRequestArguments#getSupportsArgsCanBeInterpretedByShell} is true. If the client
	 * uses an intermediary shell to launch the application, then the client must not attempt to escape
	 * characters with special meanings for the shell. The user is fully responsible for escaping as
	 * needed and that arguments using special characters may not be portable across shells.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.57
	 */
	Boolean argsCanBeInterpretedByShell;
}

/**
 * What kind of terminal to launch. Defaults to `integrated` if not specified.
 */
enum RunInTerminalRequestArgumentsKind {
	INTEGRATED,
	EXTERNAL
}

/**
 * Arguments for 'initialize' request.
 */
@JsonRpcData
class InitializeRequestArguments {
	/**
	 * The ID of the client using this adapter.
	 * <p>
	 * This is an optional property.
	 */
	String clientID;
	/**
	 * The human-readable name of the client using this adapter.
	 * <p>
	 * This is an optional property.
	 */
	String clientName;
	/**
	 * The ID of the debug adapter.
	 */
	@NonNull
	String adapterID;
	/**
	 * The ISO-639 locale of the client using this adapter, e.g. en-US or de-CH.
	 * <p>
	 * This is an optional property.
	 */
	String locale;
	/**
	 * If true all line numbers are 1-based (default).
	 * <p>
	 * This is an optional property.
	 */
	Boolean linesStartAt1;
	/**
	 * If true all column numbers are 1-based (default).
	 * <p>
	 * This is an optional property.
	 */
	Boolean columnsStartAt1;
	/**
	 * Determines in what format paths are specified. The default is 'path', which is the native format.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link InitializeRequestArgumentsPathFormat}
	 */
	String pathFormat;
	/**
	 * Client supports the `type` attribute for variables.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsVariableType;
	/**
	 * Client supports the paging of variables.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsVariablePaging;
	/**
	 * Client supports the runInTerminal request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsRunInTerminalRequest;
	/**
	 * Client supports memory references.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsMemoryReferences;
	/**
	 * Client supports progress reporting.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsProgressReporting;
	/**
	 * Client supports the invalidated event.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsInvalidatedEvent;
	/**
	 * Client supports the memory event.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.49
	 */
	Boolean supportsMemoryEvent;
	/**
	 * Client supports the 'argsCanBeInterpretedByShell' attribute on the 'runInTerminal' request.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.57
	 */
	Boolean supportsArgsCanBeInterpretedByShell;
	/**
	 * Client supports the `startDebugging` request.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.59
	 */
	Boolean supportsStartDebuggingRequest;
}

/**
 * Determines in what format paths are specified. The default is 'path', which is the native format.
 * <p>
 * Possible values include - but not limited to those defined in {@link InitializeRequestArgumentsPathFormat}
 */
interface InitializeRequestArgumentsPathFormat {
	public static final String PATH = "path";
	public static final String URI = "uri";
}

/**
 * Arguments for 'configurationDone' request.
 */
@JsonRpcData
class ConfigurationDoneArguments {
}

/**
 * Arguments for 'launch' request. Additional attributes are implementation specific.
 */
@JsonRpcData
class LaunchRequestArguments {
	/**
	 * If true, the launch request should launch the program without enabling debugging.
	 * <p>
	 * This is an optional property.
	 */
	Boolean noDebug;
	/**
	 * Arbitrary data from the previous, restarted session.
	 * <p>
	 * The data is sent as the 'restart' attribute of the 'terminated' event.
	 * <p>
	 * The client should leave the data intact.
	 * <p>
	 * This is an optional property.
	 */
	Object __restart;
}

/**
 * Arguments for 'attach' request. Additional attributes are implementation specific.
 */
@JsonRpcData
class AttachRequestArguments {
	/**
	 * Arbitrary data from the previous, restarted session.
	 * <p>
	 * The data is sent as the 'restart' attribute of the 'terminated' event.
	 * <p>
	 * The client should leave the data intact.
	 * <p>
	 * This is an optional property.
	 */
	Object __restart;
}

/**
 * Arguments for 'restart' request.
 */
@JsonRpcData
class RestartArguments {
	/**
	 * The latest version of the 'launch' or 'attach' configuration.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.47
	 */
	@JsonAdapter(RestartArgumentsArgumentsTypeAdapter)
	Either<LaunchRequestArguments, AttachRequestArguments> arguments;
}

/**
 * Arguments for 'disconnect' request.
 */
@JsonRpcData
class DisconnectArguments {
	/**
	 * A value of true indicates that this 'disconnect' request is part of a restart sequence.
	 * <p>
	 * This is an optional property.
	 */
	Boolean restart;
	/**
	 * Indicates whether the debuggee should be terminated when the debugger is disconnected.
	 * <p>
	 * If unspecified, the debug adapter is free to do whatever it thinks is best.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability {@link Capabilities#getSupportTerminateDebuggee} is true.
	 * <p>
	 * This is an optional property.
	 */
	Boolean terminateDebuggee;
	/**
	 * Indicates whether the debuggee should stay suspended when the debugger is disconnected.
	 * <p>
	 * If unspecified, the debuggee should resume execution.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability {@link Capabilities#getSupportSuspendDebuggee} is true.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.47
	 */
	Boolean suspendDebuggee;
}

/**
 * Arguments for 'terminate' request.
 */
@JsonRpcData
class TerminateArguments {
	/**
	 * A value of true indicates that this 'terminate' request is part of a restart sequence.
	 * <p>
	 * This is an optional property.
	 */
	Boolean restart;
}

/**
 * Response to 'breakpointLocations' request.
 * <p>
 * Contains possible locations for source breakpoints.
 */
@JsonRpcData
class BreakpointLocationsResponse {
	/**
	 * Sorted set of possible breakpoint locations.
	 */
	@NonNull
	BreakpointLocation[] breakpoints;
}

/**
 * Arguments for 'breakpointLocations' request.
 */
@JsonRpcData
class BreakpointLocationsArguments {
	/**
	 * The source location of the breakpoints; either 'source.path' or 'source.sourceReference' must be specified.
	 */
	@NonNull
	Source source;
	/**
	 * Start line of range to search possible breakpoint locations in. If only the line is specified, the request
	 * returns all possible locations in that line.
	 */
	int line;
	/**
	 * Start position within `line` to search possible breakpoint locations in. It is measured in UTF-16
	 * code units and the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based. If no column is given,
	 * the first position in the start line is assumed.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
	/**
	 * End line of range to search possible breakpoint locations in. If no end line is given, then the end
	 * line is assumed to be the start line.
	 * <p>
	 * This is an optional property.
	 */
	Integer endLine;
	/**
	 * End position within `endLine` to search possible breakpoint locations in. It is measured in UTF-16
	 * code units and the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based. If no end column is given,
	 * the last position in the end line is assumed.
	 * <p>
	 * This is an optional property.
	 */
	Integer endColumn;
}

/**
 * Response to 'setBreakpoints' request.
 * <p>
 * Returned is information about each breakpoint created by this request.
 * <p>
 * This includes the actual code location and whether the breakpoint could be verified.
 * <p>
 * The breakpoints returned are in the same order as the elements of the 'breakpoints'
 * <p>
 * (or the deprecated 'lines') array in the arguments.
 */
@JsonRpcData
class SetBreakpointsResponse {
	/**
	 * Information about the breakpoints.
	 * <p>
	 * The array elements are in the same order as the elements of the 'breakpoints' (or the deprecated 'lines') array
	 * in the arguments.
	 */
	@NonNull
	Breakpoint[] breakpoints;
}

/**
 * Arguments for 'setBreakpoints' request.
 */
@JsonRpcData
class SetBreakpointsArguments {
	/**
	 * The source location of the breakpoints; either 'source.path' or 'source.sourceReference' must be specified.
	 */
	@NonNull
	Source source;
	/**
	 * The code locations of the breakpoints.
	 * <p>
	 * This is an optional property.
	 */
	SourceBreakpoint[] breakpoints;
	/**
	 * The code locations of the breakpoints.
	 * <p>
	 * This is an optional property.
	 *
	 * @deprecated Use {@link SourceBreakpoint#line} of {@link #breakpoints} instead.
	 */
	@Deprecated
	int[] lines;
	/**
	 * A value of true indicates that the underlying source has been modified which results in new breakpoint
	 * locations.
	 * <p>
	 * This is an optional property.
	 */
	Boolean sourceModified;
}

/**
 * Response to 'setFunctionBreakpoints' request.
 * <p>
 * Returned is information about each breakpoint created by this request.
 */
@JsonRpcData
class SetFunctionBreakpointsResponse {
	/**
	 * Information about the breakpoints. The array elements correspond to the elements of the 'breakpoints' array.
	 */
	@NonNull
	Breakpoint[] breakpoints;
}

/**
 * Arguments for 'setFunctionBreakpoints' request.
 */
@JsonRpcData
class SetFunctionBreakpointsArguments {
	/**
	 * The function names of the breakpoints.
	 */
	@NonNull
	FunctionBreakpoint[] breakpoints;
}

/**
 * Arguments for 'setExceptionBreakpoints' request.
 */
@JsonRpcData
class SetExceptionBreakpointsArguments {
	/**
	 * Set of exception filters specified by their ID. The set of all possible exception filters is defined by the
	 * {@link Capabilities#getExceptionBreakpointFilters} capability. The 'filter' and 'filterOptions' sets are additive.
	 */
	@NonNull
	String[] filters;
	/**
	 * Set of exception filters and their options. The set of all possible exception filters is defined by the
	 * {@link Capabilities#getExceptionBreakpointFilters} capability. This attribute is only honored by a debug adapter if
	 * the corresponding capability {@link Capabilities#getSupportsExceptionFilterOptions} is true. The 'filter' and
	 * 'filterOptions' sets are additive.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.43
	 */
	ExceptionFilterOptions[] filterOptions;
	/**
	 * Configuration options for selected exceptions.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsExceptionOptions} is true.
	 * <p>
	 * This is an optional property.
	 */
	ExceptionOptions[] exceptionOptions;
}

/**
 * Response to 'setExceptionBreakpoints' request.
 * <p>
 * The response contains an array of Breakpoint objects with information about each exception breakpoint or filter.
 * The Breakpoint objects are in the same order as the elements of the 'filters', 'filterOptions', 'exceptionOptions'
 * arrays given as arguments. If both 'filters' and 'filterOptions' are given, the returned array must start with
 * 'filters' information first, followed by 'filterOptions' information.
 * <p>
 * The 'verified' property of a Breakpoint object signals whether the exception breakpoint or filter could
 * be successfully created and whether the condition is valid. In case of an error
 * the 'message' property explains the problem. The 'id' property can be used to introduce a unique ID for the
 * exception breakpoint or filter so that it can be updated subsequently by sending breakpoint events.
 * <p>
 * For backward compatibility both the 'breakpoints' array and the enclosing 'body' are optional. If these elements
 * are missing a client is not able to show problems for individual exception breakpoints or filters.
 */
@JsonRpcData
class SetExceptionBreakpointsResponse {
	/**
	 * Information about the exception breakpoints or filters.
	 * <p>
	 * The breakpoints returned are in the same order as the elements of the
	 * 'filters', 'filterOptions', 'exceptionOptions' arrays in the arguments.
	 * If both 'filters' and 'filterOptions' are given, the returned array must
	 * start with 'filters' information first, followed by 'filterOptions'
	 * information.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.47
	 */
	Breakpoint[] breakpoints;
}

/**
 * Response to 'dataBreakpointInfo' request.
 */
@JsonRpcData
class DataBreakpointInfoResponse {
	/**
	 * An identifier for the data on which a data breakpoint can be registered with the setDataBreakpoints request or
	 * null if no data breakpoint is available. If a `variablesReference` or `frameId` is passed, the `dataId`
	 * is valid in the current suspended state, otherwise it's valid indefinitely.
	 * See 'Lifetime of Object References' in the Overview section for details. Breakpoints set using the `dataId` in the
	 * `setDataBreakpoints` request may outlive the lifetime of the associated `dataId`.
	 */
	String dataId;
	/**
	 * UI string that describes on what data the breakpoint is set on or why a data breakpoint is not available.
	 */
	@NonNull
	String description;
	/**
	 * Attribute lists the available access types for a potential data breakpoint. A UI client could
	 * surface this information.
	 * <p>
	 * This is an optional property.
	 */
	DataBreakpointAccessType[] accessTypes;
	/**
	 * Attribute indicates that a potential data breakpoint could be persisted across sessions.
	 * <p>
	 * This is an optional property.
	 */
	Boolean canPersist;
}

/**
 * Arguments for 'dataBreakpointInfo' request.
 */
@JsonRpcData
class DataBreakpointInfoArguments {
	/**
	 * Reference to the variable container if the data breakpoint is requested for a child of the container.
	 * The `variablesReference` must have been obtained in the current suspended state.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 * <p>
	 * This is an optional property.
	 */
	Integer variablesReference;
	/**
	 * The name of the Variable's child to obtain data breakpoint information for.
	 * <p>
	 * If variablesReference isn't specified, this can be an expression, or an address
	 * if `asAddress` is also true.
	 */
	@NonNull
	String name;
	/**
	 * When `name` is an expression, evaluate it in the scope of this stack frame.
	 * If not specified, the expression is evaluated in the global scope. When
	 * `variablesReference` is specified, this property has no effect.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.59
	 */
	Integer frameId;
	/**
	 * If specified, a debug adapter should return information for the range of memory extending `bytes` number of
	 * bytes from the address or variable specified by `name`. Breakpoints set using the resulting data ID should
	 * pause on data access anywhere within that range.
	 * <p>
	 * Clients may set this property only if the `supportsDataBreakpointBytes` capability is true.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.66
	 */
	Integer bytes;
	/**
	 * If `true`, the `name` is a memory address and the debugger should interpret it as a decimal value,
	 * or hex value if it is prefixed with `0x`.
	 * <p>
	 * Clients may set this property only if the `supportsDataBreakpointBytes` capability is true.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.66
	 */
	Boolean asAddress;
	/**
	 * The mode of the desired breakpoint. If defined, this must be one of the `breakpointModes`
	 * the debug adapter advertised in its `Capabilities`.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.65
	 */
	String mode;
}

/**
 * Response to 'setDataBreakpoints' request.
 * <p>
 * Returned is information about each breakpoint created by this request.
 */
@JsonRpcData
class SetDataBreakpointsResponse {
	/**
	 * Information about the data breakpoints. The array elements correspond to the elements of the input argument
	 * 'breakpoints' array.
	 */
	@NonNull
	Breakpoint[] breakpoints;
}

/**
 * Arguments for 'setDataBreakpoints' request.
 */
@JsonRpcData
class SetDataBreakpointsArguments {
	/**
	 * The contents of this array replaces all existing data breakpoints. An empty array clears all data breakpoints.
	 */
	@NonNull
	DataBreakpoint[] breakpoints;
}

/**
 * Response to 'setInstructionBreakpoints' request
 */
@JsonRpcData
class SetInstructionBreakpointsResponse {
	/**
	 * Information about the breakpoints. The array elements correspond to the elements of the 'breakpoints' array.
	 */
	@NonNull
	Breakpoint[] breakpoints;
}

/**
 * Arguments for 'setInstructionBreakpoints' request
 */
@JsonRpcData
class SetInstructionBreakpointsArguments {
	/**
	 * The instruction references of the breakpoints
	 */
	@NonNull
	InstructionBreakpoint[] breakpoints;
}

/**
 * Response to 'continue' request.
 */
@JsonRpcData
class ContinueResponse {
	/**
	 * The value {@code true} (or a missing property) signals to the client that all
	 * threads have been resumed. The value {@code false} indicates that not all
	 * threads were resumed.
	 * <p>
	 * This is an optional property.
	 */
	Boolean allThreadsContinued;
}

/**
 * Arguments for 'continue' request.
 */
@JsonRpcData
class ContinueArguments {
	/**
	 * Specifies the active thread. If the debug adapter supports single thread
	 * execution (see {@link Capabilities#getSupportsSingleThreadExecutionRequests}) and the
	 * argument {@link #singleThread} is true, only the thread with this ID is resumed.
	 */
	int threadId;
	/**
	 * If this flag is true, execution is resumed only for the thread
	 * with given {@link #threadId}.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.51
	 */
	Boolean singleThread;
}

/**
 * Arguments for 'next' request.
 */
@JsonRpcData
class NextArguments {
	/**
	 * Specifies the thread for which to resume execution for one step (of the given granularity).
	 */
	int threadId;
	/**
	 * If this flag is true, all other suspended threads are not resumed.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.51
	 */
	Boolean singleThread;
	/**
	 * Stepping granularity. If no granularity is specified, a granularity of 'statement' is assumed.
	 * <p>
	 * This is an optional property.
	 */
	SteppingGranularity granularity;
}

/**
 * Arguments for 'stepIn' request.
 */
@JsonRpcData
class StepInArguments {
	/**
	 * Specifies the thread for which to resume execution for one step-into (of
	 * the given granularity).
	 */
	int threadId;
	/**
	 * If this flag is true, all other suspended threads are not resumed.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.51
	 */
	Boolean singleThread;
	/**
	 * Id of the target to step into.
	 * <p>
	 * This is an optional property.
	 */
	Integer targetId;
	/**
	 * Stepping granularity. If no granularity is specified, a granularity of 'statement' is assumed.
	 * <p>
	 * This is an optional property.
	 */
	SteppingGranularity granularity;
}

/**
 * Arguments for 'stepOut' request.
 */
@JsonRpcData
class StepOutArguments {
	/**
	 * Specifies the thread for which to resume execution for one step-out (of the
	 * given granularity).
	 */
	int threadId;
	/**
	 * If this flag is true, all other suspended threads are not resumed.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.51
	 */
	Boolean singleThread;
	/**
	 * Stepping granularity. If no granularity is specified, a granularity of 'statement' is assumed.
	 * <p>
	 * This is an optional property.
	 */
	SteppingGranularity granularity;
}

/**
 * Arguments for 'stepBack' request.
 */
@JsonRpcData
class StepBackArguments {
	/**
	 * Specifies the thread for which to resume execution for one step backwards
	 * (of the given granularity).
	 */
	int threadId;
	/**
	 * If this flag is true, all other suspended threads are not resumed.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.51
	 */
	Boolean singleThread;
	/**
	 * Stepping granularity. If no granularity is specified, a granularity of 'statement' is assumed.
	 * <p>
	 * This is an optional property.
	 */
	SteppingGranularity granularity;
}

/**
 * Arguments for 'reverseContinue' request.
 */
@JsonRpcData
class ReverseContinueArguments {
	/**
	 * Specifies the active thread. If the debug adapter supports single thread
	 * execution (see {@link Capabilities#getSupportsSingleThreadExecutionRequests})
	 * and the 'singleThread' argument is true, only the thread with this ID is resumed.
	 */
	int threadId;
	/**
	 * If this flag is true, backward execution is resumed only for the
	 * thread with given 'threadId'.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.51
	 */
	Boolean singleThread;
}

/**
 * Arguments for 'restartFrame' request.
 */
@JsonRpcData
class RestartFrameArguments {
	/**
	 * Restart the stack frame identified by `frameId`. The `frameId` must
	 * have been obtained in the current suspended state.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 */
	int frameId;
}

/**
 * Arguments for 'goto' request.
 */
@JsonRpcData
class GotoArguments {
	/**
	 * Set the goto target for this thread.
	 */
	int threadId;
	/**
	 * The location where the debuggee will continue to run.
	 */
	int targetId;
}

/**
 * Arguments for 'pause' request.
 */
@JsonRpcData
class PauseArguments {
	/**
	 * Pause execution for this thread.
	 */
	int threadId;
}

/**
 * Response to 'stackTrace' request.
 */
@JsonRpcData
class StackTraceResponse {
	/**
	 * The frames of the stack frame. If the array has length zero, there are no stackframes available.
	 * <p>
	 * This means that there is no location information available.
	 */
	@NonNull
	StackFrame[] stackFrames;
	/**
	 * The total number of frames available in the stack. If omitted or if totalFrames is larger than the available
	 * frames, a client is expected to request frames until a request returns less frames than requested (which
	 * indicates the end of the stack). Returning monotonically increasing totalFrames values for subsequent requests
	 * can be used to enforce paging in the client.
	 * <p>
	 * This is an optional property.
	 */
	Integer totalFrames;
}

/**
 * Arguments for 'stackTrace' request.
 */
@JsonRpcData
class StackTraceArguments {
	/**
	 * Retrieve the stacktrace for this thread.
	 */
	int threadId;
	/**
	 * The index of the first frame to return; if omitted frames start at 0.
	 * <p>
	 * This is an optional property.
	 */
	Integer startFrame;
	/**
	 * The maximum number of frames to return. If levels is not specified or 0, all frames are returned.
	 * <p>
	 * This is an optional property.
	 */
	Integer levels;
	/**
	 * Specifies details on how to format the stack frames.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsValueFormattingOptions} is true.
	 * <p>
	 * This is an optional property.
	 */
	StackFrameFormat format;
}

/**
 * Response to 'scopes' request.
 */
@JsonRpcData
class ScopesResponse {
	/**
	 * The scopes of the stack frame. If the array has length zero, there are no scopes available.
	 */
	@NonNull
	Scope[] scopes;
}

/**
 * Arguments for 'scopes' request.
 */
@JsonRpcData
class ScopesArguments {
	/**
	 * Retrieve the scopes for the stack frame identified by `frameId`.
	 * The `frameId` must have been obtained in the current suspended state.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 */
	int frameId;
}

/**
 * Response to 'variables' request.
 */
@JsonRpcData
class VariablesResponse {
	/**
	 * All (or a range) of variables for the given variable reference.
	 */
	@NonNull
	Variable[] variables;
}

/**
 * Arguments for 'variables' request.
 */
@JsonRpcData
class VariablesArguments {
	/**
	 * The variable for which to retrieve its children.
	 * The `variablesReference` must have been obtained in the current suspended state.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 */
	int variablesReference;
	/**
	 * Filter to limit the child variables to either named or indexed. If omitted, both types are fetched.
	 * <p>
	 * This is an optional property.
	 */
	VariablesArgumentsFilter filter;
	/**
	 * The index of the first variable to return; if omitted children start at 0.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding
	 * capability {@link InitializeRequestArguments#getSupportsVariablePaging} is true.
	 * <p>
	 * This is an optional property.
	 */
	Integer start;
	/**
	 * The number of variables to return. If count is missing or 0, all variables are returned.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding
	 * capability {@link InitializeRequestArguments#getSupportsVariablePaging} is true.
	 * <p>
	 * This is an optional property.
	 */
	Integer count;
	/**
	 * Specifies details on how to format the Variable values.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsValueFormattingOptions} is true.
	 * <p>
	 * This is an optional property.
	 */
	ValueFormat format;
}

/**
 * Optional filter to limit the child variables to either named or indexed. If omitted, both types are fetched.
 */
enum VariablesArgumentsFilter {
	INDEXED,
	NAMED
}

/**
 * Response to 'setVariable' request.
 */
@JsonRpcData
class SetVariableResponse {
	/**
	 * The new value of the variable.
	 */
	@NonNull
	String value;
	/**
	 * The type of the new value. Typically shown in the UI when hovering over the value.
	 * <p>
	 * This is an optional property.
	 */
	String type;
	/**
	 * If `variablesReference` is &gt; 0, the new value is structured and its children can be retrieved by passing
	 * `variablesReference` to the `variables` request as long as execution remains suspended.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 * <p>
	 * This is an optional property.
	 */
	Integer variablesReference;
	/**
	 * The number of named child variables.
	 * <p>
	 * The client can use this information to present the variables in a paged UI and fetch them in chunks.
	 * <p>
	 * The value should be less than or equal to 2147483647 (2^31-1).
	 * <p>
	 * This is an optional property.
	 */
	Integer namedVariables;
	/**
	 * The number of indexed child variables.
	 * <p>
	 * The client can use this information to present the variables in a paged UI and fetch them in chunks.
	 * <p>
	 * The value should be less than or equal to 2147483647 (2^31-1).
	 * <p>
	 * This is an optional property.
	 */
	Integer indexedVariables;
	/**
	 * A memory reference to a location appropriate for this result.
	 * <p>
	 * For pointer type eval results, this is generally a reference to the memory address contained in the pointer.
	 * <p>
	 * This attribute may be returned by a debug adapter if corresponding capability
	 * {@link InitializeRequestArguments#getSupportsMemoryReferences} is true.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.63
	 */
	String memoryReference;
	/**
	 * A reference that allows the client to request the location where the new value is declared. For example,
	 * if the new value is function pointer, the adapter may be able to look up the function's location. This should be
	 * present only if the adapter is likely to be able to resolve the location.
	 * <p>
	 * This reference shares the same lifetime as the `variablesReference`.
	 * See 'Lifetime of Object References' in the Overview section for details.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.68
	 */
	Integer valueLocationReference;
}

/**
 * Arguments for 'setVariable' request.
 */
@JsonRpcData
class SetVariableArguments {
	/**
	 * The reference of the variable container.
	 * The `variablesReference` must have been obtained in the current suspended state.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 */
	int variablesReference;
	/**
	 * The name of the variable in the container.
	 */
	@NonNull
	String name;
	/**
	 * The value of the variable.
	 */
	@NonNull
	String value;
	/**
	 * Specifies details on how to format the response value.
	 * <p>
	 * This is an optional property.
	 */
	ValueFormat format;
}

/**
 * Response to 'source' request.
 */
@JsonRpcData
class SourceResponse {
	/**
	 * Content of the source reference.
	 */
	@NonNull
	String content;
	/**
	 * Content type (MIME type) of the source.
	 * <p>
	 * This is an optional property.
	 */
	String mimeType;
}

/**
 * Arguments for 'source' request.
 */
@JsonRpcData
class SourceArguments {
	/**
	 * Specifies the source content to load. Either source.path or source.sourceReference must be specified.
	 * <p>
	 * This is an optional property.
	 */
	Source source;
	/**
	 * The reference to the source. This is the same as source.sourceReference.
	 * <p>
	 * This is provided for backward compatibility since old clients do not understand the 'source' attribute.
	 */
	int sourceReference;
}

/**
 * Response to 'threads' request.
 */
@JsonRpcData
class ThreadsResponse {
	/**
	 * All threads.
	 */
	@NonNull
	Thread[] threads;
}

/**
 * Arguments for 'terminateThreads' request.
 */
@JsonRpcData
class TerminateThreadsArguments {
	/**
	 * Ids of threads to be terminated.
	 * <p>
	 * This is an optional property.
	 */
	int[] threadIds;
}

/**
 * Response to 'modules' request.
 */
@JsonRpcData
class ModulesResponse {
	/**
	 * All modules or range of modules.
	 */
	@NonNull
	Module[] modules;
	/**
	 * The total number of modules available.
	 * <p>
	 * This is an optional property.
	 */
	Integer totalModules;
}

/**
 * Arguments for 'modules' request.
 */
@JsonRpcData
class ModulesArguments {
	/**
	 * The index of the first module to return; if omitted modules start at 0.
	 * <p>
	 * This is an optional property.
	 */
	Integer startModule;
	/**
	 * The number of modules to return. If moduleCount is not specified or 0, all modules are returned.
	 * <p>
	 * This is an optional property.
	 */
	Integer moduleCount;
}

/**
 * Response to 'loadedSources' request.
 */
@JsonRpcData
class LoadedSourcesResponse {
	/**
	 * Set of loaded sources.
	 */
	@NonNull
	Source[] sources;
}

/**
 * Arguments for 'loadedSources' request.
 */
@JsonRpcData
class LoadedSourcesArguments {
}

/**
 * Response to 'evaluate' request.
 */
@JsonRpcData
class EvaluateResponse {
	/**
	 * The result of the evaluate request.
	 */
	@NonNull
	String result;
	/**
	 * The type of the evaluate result.
	 * <p>
	 * This attribute should only be returned by a debug adapter if the corresponding capability
	 * {@link InitializeRequestArguments#getSupportsVariableType} is true.
	 * <p>
	 * This is an optional property.
	 */
	String type;
	/**
	 * Properties of an evaluate result that can be used to determine how to render the result in the UI.
	 * <p>
	 * This is an optional property.
	 */
	VariablePresentationHint presentationHint;
	/**
	 * If `variablesReference` is &gt; 0, the evaluate result is structured and its children can be retrieved by passing
	 * `variablesReference` to the `variables` request as long as execution remains suspended.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 * <p>
	 * The value should be less than or equal to 2147483647 (2^31-1).
	 */
	int variablesReference;
	/**
	 * The number of named child variables.
	 * <p>
	 * The client can use this information to present the variables in a paged UI and fetch them in chunks.
	 * <p>
	 * The value should be less than or equal to 2147483647 (2^31-1).
	 * <p>
	 * This is an optional property.
	 */
	Integer namedVariables;
	/**
	 * The number of indexed child variables.
	 * <p>
	 * The client can use this information to present the variables in a paged UI and fetch them in chunks.
	 * <p>
	 * The value should be less than or equal to 2147483647 (2^31-1).
	 * <p>
	 * This is an optional property.
	 */
	Integer indexedVariables;
	/**
	 * A memory reference to a location appropriate for this result.
	 * <p>
	 * For pointer type eval results, this is generally a reference to the memory address contained in the pointer.
	 * <p>
	 * This attribute may be returned by a debug adapter if corresponding capability
	 * {@link InitializeRequestArguments#getSupportsMemoryReferences} is true.
	 * <p>
	 * This is an optional property.
	 */
	String memoryReference;
	/**
	 * A reference that allows the client to request the location where the returned value is declared. For example,
	 * if a function pointer is returned, the adapter may be able to look up the function's location.
	 * This should be present only if the adapter is likely to be able to resolve the location.
	 * <p>
	 * This reference shares the same lifetime as the `variablesReference`.
	 * See 'Lifetime of Object References' in the Overview section for details.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.68
	 */
	Integer valueLocationReference;
}

/**
 * Arguments for 'evaluate' request.
 */
@JsonRpcData
class EvaluateArguments {
	/**
	 * The expression to evaluate.
	 */
	@NonNull
	String expression;
	/**
	 * Evaluate the expression in the scope of this stack frame. If not specified, the expression is evaluated in the
	 * global scope.
	 * <p>
	 * This is an optional property.
	 */
	Integer frameId;
	/**
	 * The contextual line where the expression should be evaluated. In the 'hover' context, this should be set to the
	 * start of the expression being hovered.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.67
	 */
	Integer line;
	/**
	 * The contextual column where the expression should be evaluated. This may be provided if `line` is also provided.
	 * <p>
	 * It is measured in UTF-16 code units and the client capability `columnsStartAt1` determines whether
	 * it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.67
	 */
	Integer column;
	/**
	 * The contextual source in which the `line` is found. This must be provided if `line` is provided.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.67
	 */
	Source source;
	/**
	 * The context in which the evaluate request is used.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link EvaluateArgumentsContext}
	 */
	String context;
	/**
	 * Specifies details on how to format the result.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsValueFormattingOptions} is true.
	 * <p>
	 * This is an optional property.
	 */
	ValueFormat format;
}

/**
 * The context in which the evaluate request is used.
 * <p>
 * Possible values include - but not limited to those defined in {@link EvaluateArgumentsContext}
 */
interface EvaluateArgumentsContext {
	/**
	 * evaluate is called from a watch view context.
	 */
	public static final String WATCH = "watch";
	/**
	 * evaluate is called from a REPL context.
	 */
	public static final String REPL = "repl";
	/**
	 * evaluate is called to generate the debug hover contents.
	 * This value should only be used if the corresponding capability
	 * {@link Capabilities#getSupportsEvaluateForHovers} is true.
	 */
	public static final String HOVER = "hover";
	/**
	 * evaluate is called to generate clipboard contents.
	 * This value should only be used if the corresponding capability
	 * {@link Capabilities#getSupportsClipboardContext} is true.
	 */
	public static final String CLIPBOARD = "clipboard";
	/**
	 * evaluate is called from a variables view context.
	 * <p>
	 * Since 1.55
	 */
	public static final String VARIABLES = "variables";
}

/**
 * Response to 'setExpression' request.
 */
@JsonRpcData
class SetExpressionResponse {
	/**
	 * The new value of the expression.
	 */
	@NonNull
	String value;
	/**
	 * The type of the value.
	 * <p>
	 * This attribute should only be returned by a debug adapter if the corresponding capability
	 * {@link InitializeRequestArguments#getSupportsVariableType} is true.
	 * <p>
	 * This is an optional property.
	 */
	String type;
	/**
	 * Properties of a value that can be used to determine how to render the result in the UI.
	 * <p>
	 * This is an optional property.
	 */
	VariablePresentationHint presentationHint;
	/**
	 * If `variablesReference` is &gt; 0, the evaluate result is structured and its children can be retrieved
	 * by passing `variablesReference` to the `variables` request as long as execution remains suspended.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 * <p>
	 * This is an optional property.
	 */
	Integer variablesReference;
	/**
	 * The number of named child variables.
	 * <p>
	 * The client can use this information to present the variables in a paged UI and fetch them in chunks.
	 * <p>
	 * The value should be less than or equal to 2147483647 (2^31-1).
	 * <p>
	 * This is an optional property.
	 */
	Integer namedVariables;
	/**
	 * The number of indexed child variables.
	 * <p>
	 * The client can use this information to present the variables in a paged UI and fetch them in chunks.
	 * <p>
	 * The value should be less than or equal to 2147483647 (2^31-1).
	 * <p>
	 * This is an optional property.
	 */
	Integer indexedVariables;
	/**
	 * A memory reference to a location appropriate for this result.
	 * <p>
	 * For pointer type eval results, this is generally a reference to the memory address contained in the pointer.
	 * <p>
	 * This attribute may be returned by a debug adapter if corresponding capability
	 * {@link InitializeRequestArguments#getSupportsMemoryReferences} is true.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.63
	 */
	String memoryReference;
	/**
	 * A reference that allows the client to request the location where the new value is declared. For example,
	 * if the new value is function pointer, the adapter may be able to look up the function's location. This should be
	 * present only if the adapter is likely to be able to resolve the location.
	 * <p>
	 * This reference shares the same lifetime as the `variablesReference`.
	 * See 'Lifetime of Object References' in the Overview section for details.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.68
	 */
	Integer valueLocationReference;
}

/**
 * Arguments for 'setExpression' request.
 */
@JsonRpcData
class SetExpressionArguments {
	/**
	 * The l-value expression to assign to.
	 */
	@NonNull
	String expression;
	/**
	 * The value expression to assign to the l-value expression.
	 */
	@NonNull
	String value;
	/**
	 * Evaluate the expressions in the scope of this stack frame. If not specified, the expressions are evaluated in
	 * the global scope.
	 * <p>
	 * This is an optional property.
	 */
	Integer frameId;
	/**
	 * Specifies how the resulting value should be formatted.
	 * <p>
	 * This is an optional property.
	 */
	ValueFormat format;
}

/**
 * Response to 'stepInTargets' request.
 */
@JsonRpcData
class StepInTargetsResponse {
	/**
	 * The possible step-in targets of the specified source location.
	 */
	@NonNull
	StepInTarget[] targets;
}

/**
 * Arguments for 'stepInTargets' request.
 */
@JsonRpcData
class StepInTargetsArguments {
	/**
	 * The stack frame for which to retrieve the possible step-in targets.
	 */
	int frameId;
}

/**
 * Response to 'gotoTargets' request.
 */
@JsonRpcData
class GotoTargetsResponse {
	/**
	 * The possible goto targets of the specified location.
	 */
	@NonNull
	GotoTarget[] targets;
}

/**
 * Arguments for 'gotoTargets' request.
 */
@JsonRpcData
class GotoTargetsArguments {
	/**
	 * The source location for which the goto targets are determined.
	 */
	@NonNull
	Source source;
	/**
	 * The line location for which the goto targets are determined.
	 */
	int line;
	/**
	 * The position within `line` for which the goto targets are determined. It is measured in UTF-16 code units and
	 * the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
}

/**
 * Response to 'completions' request.
 */
@JsonRpcData
class CompletionsResponse {
	/**
	 * The possible completions for .
	 */
	@NonNull
	CompletionItem[] targets;
}

/**
 * Arguments for 'completions' request.
 */
@JsonRpcData
class CompletionsArguments {
	/**
	 * Returns completions in the scope of this stack frame. If not specified, the completions are returned for the
	 * global scope.
	 * <p>
	 * This is an optional property.
	 */
	Integer frameId;
	/**
	 * One or more source lines. Typically this is the text users have typed into the debug console
	 * before they asked for completion.
	 */
	@NonNull
	String text;
	/**
	 * The position within `text` for which to determine the completion proposals. It is measured in UTF-16 code units and
	 * the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines whether it is 0- or 1-based.
	 */
	int column;
	/**
	 * A line for which to determine the completion proposals. If missing the first line of the text is
	 * assumed.
	 * <p>
	 * This is an optional property.
	 */
	Integer line;
}

/**
 * Response to 'exceptionInfo' request.
 */
@JsonRpcData
class ExceptionInfoResponse {
	/**
	 * ID of the exception that was thrown.
	 */
	@NonNull
	String exceptionId;
	/**
	 * Descriptive text for the exception.
	 * <p>
	 * This is an optional property.
	 */
	String description;
	/**
	 * Mode that caused the exception notification to be raised.
	 */
	@NonNull
	ExceptionBreakMode breakMode;
	/**
	 * Detailed information about the exception.
	 * <p>
	 * This is an optional property.
	 */
	ExceptionDetails details;
}

/**
 * Arguments for 'exceptionInfo' request.
 */
@JsonRpcData
class ExceptionInfoArguments {
	/**
	 * Thread for which exception information should be retrieved.
	 */
	int threadId;
}

/**
 * Response to 'readMemory' request.
 */
@JsonRpcData
class ReadMemoryResponse {
	/**
	 * The address of the first byte of data returned.
	 * <p>
	 * Treated as a hex value if prefixed with '0x', or as a decimal value otherwise.
	 */
	@NonNull
	String address;
	/**
	 * The number of unreadable bytes encountered after the last successfully read byte.
	 * <p>
	 * This can be used to determine the number of bytes that should be skipped before a subsequent 'readMemory' request
	 * succeeds.
	 * <p>
	 * This is an optional property.
	 */
	Integer unreadableBytes;
	/**
	 * The bytes read from memory, encoded using base64. If the decoded length of
	 * `data` is less than the requested `count` in the original `readMemory`
	 * request, and `unreadableBytes` is zero or omitted, then the client should
	 * assume it's reached the end of readable memory.
	 * <p>
	 * This is an optional property.
	 */
	String data;
}

/**
 * Arguments for 'readMemory' request.
 */
@JsonRpcData
class ReadMemoryArguments {
	/**
	 * Memory reference to the base location from which data should be read.
	 */
	@NonNull
	String memoryReference;
	/**
	 * Offset (in bytes) to be applied to the reference location before reading data. Can be negative.
	 * <p>
	 * This is an optional property.
	 */
	Integer offset;
	/**
	 * Number of bytes to read at the specified location and offset.
	 */
	int count;
}

/**
 * Response to 'writeMemory' request.
 * <p>
 * Since 1.48
 */
@JsonRpcData
class WriteMemoryResponse {
	/**
	 * Property that should be returned when 'allowPartial' is true to
	 * indicate the offset of the first byte of data successfully written. Can
	 * be negative.
	 * <p>
	 * This is an optional property.
	 */
	Integer offset;
	/**
	 * Property that should be returned when 'allowPartial' is true to
	 * indicate the number of bytes starting from address that were successfully
	 * written.
	 * <p>
	 * This is an optional property.
	 */
	Integer bytesWritten;
}

/**
 * Arguments for 'writeMemory' request.
 * <p>
 * Since 1.48
 */
@JsonRpcData
class WriteMemoryArguments {
	/**
	 * Memory reference to the base location to which data should be written.
	 */
	@NonNull
	String memoryReference;
	/**
	 * Offset (in bytes) to be applied to the reference location before
	 * writing data. Can be negative.
	 * <p>
	 * This is an optional property.
	 */
	Integer offset;
	/**
	 * Property to control partial writes. If true, the debug adapter
	 * should attempt to write memory even if the entire memory region is not
	 * writable. In such a case the debug adapter should stop after hitting the
	 * first byte of memory that cannot be written and return the number of bytes
	 * written in the response via the 'offset' and 'bytesWritten' properties.
	 * If false or missing, a debug adapter should attempt to verify the region is
	 * writable before writing, and fail the response if it is not.
	 * <p>
	 * This is an optional property.
	 */
	Boolean allowPartial;
	/**
	 * Bytes to write, encoded using base64.
	 */
	@NonNull
	String data;
}

/**
 * Response to 'disassemble' request.
 */
@JsonRpcData
class DisassembleResponse {
	/**
	 * The list of disassembled instructions.
	 */
	@NonNull
	DisassembledInstruction[] instructions;
}

/**
 * Arguments for 'disassemble' request.
 */
@JsonRpcData
class DisassembleArguments {
	/**
	 * Memory reference to the base location containing the instructions to disassemble.
	 */
	@NonNull
	String memoryReference;
	/**
	 * Offset (in bytes) to be applied to the reference location before disassembling. Can be negative.
	 * <p>
	 * This is an optional property.
	 */
	Integer offset;
	/**
	 * Offset (in instructions) to be applied after the byte offset (if any) before disassembling. Can be
	 * negative.
	 * <p>
	 * This is an optional property.
	 */
	Integer instructionOffset;
	/**
	 * Number of instructions to disassemble starting at the specified location and offset.
	 * <p>
	 * An adapter must return exactly this number of instructions - any unavailable instructions should be replaced
	 * with an implementation-defined 'invalid instruction' value.
	 */
	int instructionCount;
	/**
	 * If true, the adapter should attempt to resolve memory addresses and other values to symbolic names.
	 * <p>
	 * This is an optional property.
	 */
	Boolean resolveSymbols;
}


/**
 * Arguments for 'locations' request.
 * <p>
 * Since 1.68
 */
@JsonRpcData
class LocationsArguments {
	/**
	 * Location reference to resolve.
	 */
	@NonNull
	Integer locationReference;
}

/**
 * Response to 'locations' request.
 * <p>
 * Since 1.68
 */
@JsonRpcData
class LocationsResponse {
	/**
	 * The source containing the location; either `source.path` or
	 * `source.sourceReference` must be specified.
	 */
	@NonNull
	Source source;
	/**
	 * The line number of the location. The client capability `linesStartAt1`
	 * determines whether it is 0- or 1-based.
	 */
	@NonNull
	Integer line;
	/**
	 * Position of the location within the `line`. It is measured in UTF-16 code
	 * units and the client capability `columnsStartAt1` determines whether it
	 * is 0- or 1-based. If no column is given, the first position in the start
	 * line is assumed.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
	/**
	 * End line of the location, present if the location refers to a range.  The
	 * client capability `linesStartAt1` determines whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer endLine;
	/**
	 * End position of the location within `endLine`, present if the location
	 * refers to a range. It is measured in UTF-16 code units and the client
	 * capability `columnsStartAt1` determines whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer endColumn;
}

/**
 * Information about the capabilities of a debug adapter.
 */
@JsonRpcData
class Capabilities {
	/**
	 * The debug adapter supports the 'configurationDone' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsConfigurationDoneRequest;
	/**
	 * The debug adapter supports function breakpoints.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsFunctionBreakpoints;
	/**
	 * The debug adapter supports conditional breakpoints.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsConditionalBreakpoints;
	/**
	 * The debug adapter supports breakpoints that break execution after a specified number of hits.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsHitConditionalBreakpoints;
	/**
	 * The debug adapter supports a (side effect free) evaluate request for data hovers.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsEvaluateForHovers;
	/**
	 * Available exception filter options for the 'setExceptionBreakpoints' request.
	 * <p>
	 * This is an optional property.
	 */
	ExceptionBreakpointsFilter[] exceptionBreakpointFilters;
	/**
	 * The debug adapter supports stepping back via the 'stepBack' and 'reverseContinue' requests.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsStepBack;
	/**
	 * The debug adapter supports setting a variable to a value.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsSetVariable;
	/**
	 * The debug adapter supports restarting a frame.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsRestartFrame;
	/**
	 * The debug adapter supports the 'gotoTargets' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsGotoTargetsRequest;
	/**
	 * The debug adapter supports the 'stepInTargets' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsStepInTargetsRequest;
	/**
	 * The debug adapter supports the 'completions' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsCompletionsRequest;
	/**
	 * The set of characters that should trigger completion in a REPL. If not specified, the UI should assume the '.'
	 * character.
	 * <p>
	 * This is an optional property.
	 */
	String[] completionTriggerCharacters;
	/**
	 * The debug adapter supports the 'modules' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsModulesRequest;
	/**
	 * The set of additional module information exposed by the debug adapter.
	 * <p>
	 * This is an optional property.
	 */
	ColumnDescriptor[] additionalModuleColumns;
	/**
	 * Checksum algorithms supported by the debug adapter.
	 * <p>
	 * This is an optional property.
	 */
	ChecksumAlgorithm[] supportedChecksumAlgorithms;
	/**
	 * The debug adapter supports the 'restart' request. In this case a client should not implement 'restart' by
	 * terminating and relaunching the adapter but by calling the `restart` request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsRestartRequest;
	/**
	 * The debug adapter supports 'exceptionOptions' on the setExceptionBreakpoints request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsExceptionOptions;
	/**
	 * The debug adapter supports a 'format' attribute on the `stackTrace`, `variables`, and `evaluate` requests.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsValueFormattingOptions;
	/**
	 * The debug adapter supports the 'exceptionInfo' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsExceptionInfoRequest;
	/**
	 * The debug adapter supports the 'terminateDebuggee' attribute on the 'disconnect' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportTerminateDebuggee;
	/**
	 * The debug adapter supports the 'suspendDebuggee' attribute on the 'disconnect' request.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.47
	 */
	Boolean supportSuspendDebuggee;
	/**
	 * The debug adapter supports the delayed loading of parts of the stack, which requires that both the 'startFrame'
	 * and 'levels' arguments and the 'totalFrames' result of the 'StackTrace' request are supported.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsDelayedStackTraceLoading;
	/**
	 * The debug adapter supports the 'loadedSources' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsLoadedSourcesRequest;
	/**
	 * The debug adapter supports log points by interpreting the 'logMessage' attribute of the SourceBreakpoint.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsLogPoints;
	/**
	 * The debug adapter supports the 'terminateThreads' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsTerminateThreadsRequest;
	/**
	 * The debug adapter supports the 'setExpression' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsSetExpression;
	/**
	 * The debug adapter supports the 'terminate' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsTerminateRequest;
	/**
	 * The debug adapter supports data breakpoints.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsDataBreakpoints;
	/**
	 * The debug adapter supports the 'readMemory' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsReadMemoryRequest;
	/**
	 * The debug adapter supports the 'writeMemory' request.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.48
	 */
	Boolean supportsWriteMemoryRequest;
	/**
	 * The debug adapter supports the 'disassemble' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsDisassembleRequest;
	/**
	 * The debug adapter supports the 'cancel' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsCancelRequest;
	/**
	 * The debug adapter supports the 'breakpointLocations' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsBreakpointLocationsRequest;
	/**
	 * The debug adapter supports the 'clipboard' context value in the 'evaluate' request.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsClipboardContext;
	/**
	 * The debug adapter supports stepping granularities (argument 'granularity') for the stepping requests.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsSteppingGranularity;
	/**
	 * The debug adapter supports adding breakpoints based on instruction references.
	 * <p>
	 * This is an optional property.
	 */
	Boolean supportsInstructionBreakpoints;
	/**
	 * The debug adapter supports 'filterOptions' as an argument on the 'setExceptionBreakpoints' request.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.43
	 */
	Boolean supportsExceptionFilterOptions;
	/**
	 * The debug adapter supports the 'singleThread' property on the execution
	 * requests ('continue', 'next', 'stepIn', 'stepOut', 'reverseContinue', 'stepBack').
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.51
	 */
	Boolean supportsSingleThreadExecutionRequests;
	/**
	 * The debug adapter supports the 'asAddress' and 'bytes' fields in the 'dataBreakpointInfo'
	 * request.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.66
	 */
	Boolean supportsDataBreakpointBytes;
	/**
	 * Modes of breakpoints supported by the debug adapter, such as 'hardware' or 'software'.
	 * If present, the client may allow the user to select a mode and include it in its `setBreakpoints` request.
	 * <p>
	 * <p>
	 * Clients may present the first applicable mode in this array as the 'default' mode in gestures that set breakpoints.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link BreakpointMode}
	 * <p>
	 * Since 1.65
	 */
	BreakpointMode[] breakpointModes;
}

/**
 * A `BreakpointMode` is provided as a option when setting breakpoints on sources or instructions.
 * <p>
 * Since 1.65
 */
@JsonRpcData
class BreakpointMode {
	/**
	 * The internal ID of the mode. This value is passed to the `setBreakpoints` request.
	 */
	@NonNull
	String mode;
	/**
	 * The name of the breakpoint mode. This is shown in the UI.
	 */
	@NonNull
	String label;
	/**
	 * A help text providing additional information about the breakpoint mode.
	 * This string is typically shown as a hover and can be translated.
	 * <p>
	 * This is an optional property.
	 */
	String description;
	/**
	 * Describes one or more type of breakpoint this mode applies to.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link BreakpointModeApplicability}
	 */
	@NonNull
	String[] appliesTo;
}

/**
 * Describes one or more type of breakpoint a `BreakpointMode` applies to.
 * This is a non-exhaustive enumeration and may expand as future breakpoint types are added.
 * <p>
 * Since 1.65
 */
interface BreakpointModeApplicability {
	/**
	 * In `SourceBreakpoint`s
	 */
	public static final String SOURCE = "source";
	/**
	 * In exception breakpoints applied in the `ExceptionFilterOptions`
	 */
	public static final String EXCEPTION = "exception";
	/**
	 * In data breakpoints requested in the `DataBreakpointInfo` request
	 */
	public static final String DATA = "data";
	/**
	 * In `InstructionBreakpoint`s
	 */
	public static final String INSTRUCTION = "instruction";
}

/**
 * An ExceptionBreakpointsFilter is shown in the UI as an filter option for configuring how exceptions are dealt
 * with.
 */
@JsonRpcData
class ExceptionBreakpointsFilter {
	/**
	 * The internal ID of the filter option. This value is passed to the 'setExceptionBreakpoints' request.
	 */
	@NonNull
	String filter;
	/**
	 * The name of the filter option. This is shown in the UI.
	 */
	@NonNull
	String label;
	/**
	 * A help text providing additional information about the exception
	 * filter. This string is typically shown as a hover and can be translated.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.45
	 */
	String description;
	/**
	 * Initial value of the filter option. If not specified a value 'false' is assumed.
	 * <p>
	 * This is an optional property.
	 */
	@SerializedName(value="default")
	Boolean default_;
	/**
	 * Controls whether a condition can be specified for this filter option. If false or missing, a condition can not
	 * be set.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.43
	 */
	Boolean supportsCondition;
	/**
	 * A help text providing information about the condition. This
	 * string is shown as the placeholder text for a text box and can be
	 * translated.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.45
	 */
	String conditionDescription;
}

/**
 * A structured message object. Used to return errors from requests.
 */
@JsonRpcData
class Message {
	/**
	 * Unique (within a debug adapter implementation) identifier for the message.
	 * The purpose of these error IDs is to help extension authors that have the
	 * requirement that every user visible error message needs a corresponding error
	 * number, so that users or customer support can find information about the
	 * specific error more easily.
	 */
	int id;
	/**
	 * A format string for the message. Embedded variables have the form '{name}'.
	 * <p>
	 * If variable name starts with an underscore character, the variable does not contain user data (PII) and can be
	 * safely used for telemetry purposes.
	 */
	@NonNull
	String format;
	/**
	 * An object used as a dictionary for looking up the variables in the format string.
	 * <p>
	 * This is an optional property.
	 */
	Map<String, String> variables;
	/**
	 * If true send to telemetry.
	 * <p>
	 * This is an optional property.
	 */
	Boolean sendTelemetry;
	/**
	 * If true show user.
	 * <p>
	 * This is an optional property.
	 */
	Boolean showUser;
	/**
	 * A url where additional information about this message can be found.
	 * <p>
	 * This is an optional property.
	 */
	String url;
	/**
	 * A label that is presented to the user as the UI for opening the url.
	 * <p>
	 * This is an optional property.
	 */
	String urlLabel;
}

/**
 * A Module object represents a row in the modules view.
 * <p>
 * The `id` attribute identifies a module in the modules view and is used in a `module` event for
 * identifying a module for adding, updating or deleting.
 * <p>
 * The `name` attribute is used to minimally render the module in the UI.
 * <p>
 * <p>
 * Additional attributes can be added to the module. They show up in the module view if they have a
 * corresponding ColumnDescriptor.
 * <p>
 * <p>
 * To avoid an unnecessary proliferation of additional attributes with similar semantics but different names,
 * we recommend to re-use attributes from the 'recommended' list below first, and only introduce new attributes if
 * nothing appropriate could be found.
 */
@JsonRpcData
class Module {
	/**
	 * Unique identifier for the module.
	 */
	@NonNull
	Either<Integer, String> id;
	/**
	 * A name of the module.
	 */
	@NonNull
	String name;
	/**
	 * Logical full path to the module. The exact definition is implementation defined, but usually this would be a
	 * full path to the on-disk file for the module.
	 * <p>
	 * This is an optional property.
	 */
	String path;
	/**
	 * True if the module is optimized.
	 * <p>
	 * This is an optional property.
	 */
	Boolean isOptimized;
	/**
	 * True if the module is considered 'user code' by a debugger that supports 'Just My Code'.
	 * <p>
	 * This is an optional property.
	 */
	Boolean isUserCode;
	/**
	 * Version of Module.
	 * <p>
	 * This is an optional property.
	 */
	String version;
	/**
	 * User-understandable description of if symbols were found for the module (ex: 'Symbols Loaded', 'Symbols not
	 * found', etc.)
	 * <p>
	 * This is an optional property.
	 */
	String symbolStatus;
	/**
	 * Logical full path to the symbol file. The exact definition is implementation defined.
	 * <p>
	 * This is an optional property.
	 */
	String symbolFilePath;
	/**
	 * Module created or modified, encoded as a RFC 3339 timestamp.
	 * <p>
	 * This is an optional property.
	 */
	String dateTimeStamp;
	/**
	 * Address range covered by this module.
	 * <p>
	 * This is an optional property.
	 */
	String addressRange;
}

/**
 * A ColumnDescriptor specifies what module attribute to show in a column of the modules view, how to format it,
 * <p>
 * and what the column's label should be.
 * <p>
 * It is only used if the underlying UI actually supports this level of customization.
 */
@JsonRpcData
class ColumnDescriptor {
	/**
	 * Name of the attribute rendered in this column.
	 */
	@NonNull
	String attributeName;
	/**
	 * Header UI label of column.
	 */
	@NonNull
	String label;
	/**
	 * Format to use for the rendered values in this column. TBD how the format strings looks like.
	 * <p>
	 * This is an optional property.
	 */
	String format;
	/**
	 * Datatype of values in this column. Defaults to 'string' if not specified.
	 * <p>
	 * This is an optional property.
	 */
	ColumnDescriptorType type;
	/**
	 * Width of this column in characters (hint only).
	 * <p>
	 * This is an optional property.
	 */
	Integer width;
}

/**
 * Datatype of values in this column. Defaults to 'string' if not specified.
 */
enum ColumnDescriptorType {
	STRING,
	NUMBER,
	BOOLEAN,
	@SerializedName("unixTimestampUTC")
	UNIX_TIMESTAMP_UTC
}

/**
 * A Thread
 */
@JsonRpcData
class Thread {
	/**
	 * Unique identifier for the thread.
	 */
	int id;
	/**
	 * The name of the thread.
	 */
	@NonNull
	String name;
}

/**
 * A Source is a descriptor for source code.
 * <p>
 * It is returned from the debug adapter as part of a StackFrame and it is used by clients when specifying
 * breakpoints.
 */
@JsonRpcData
class Source {
	/**
	 * The short name of the source. Every source returned from the debug adapter has a name.
	 * <p>
	 * When sending a source to the debug adapter this name is optional.
	 * <p>
	 * This is an optional property.
	 */
	String name;
	/**
	 * The path of the source to be shown in the UI.
	 * <p>
	 * It is only used to locate and load the content of the source if no sourceReference is specified (or its value
	 * is 0).
	 * <p>
	 * This is an optional property.
	 */
	String path;
	/**
	 * If the value &gt; 0 the contents of the source must be retrieved through the SourceRequest (even if a
	 * path is specified).
	 * <p>
	 * Since a `source` request is only valid for a session, it can not be used to persist a source.
	 * <p>
	 * The value should be less than or equal to 2147483647 (2^31-1).
	 * <p>
	 * This is an optional property.
	 */
	Integer sourceReference;
	/**
	 * A hint for how to present the source in the UI.
	 * <p>
	 * A value of 'deemphasize' can be used to indicate that the source is not available or that it is skipped on
	 * stepping.
	 * <p>
	 * This is an optional property.
	 */
	SourcePresentationHint presentationHint;
	/**
	 * The origin of this source. For example, 'internal module', 'inlined content from source map', etc.
	 * <p>
	 * This is an optional property.
	 */
	String origin;
	/**
	 * A list of sources that are related to this source. These may be the source that generated this
	 * source.
	 * <p>
	 * This is an optional property.
	 */
	Source[] sources;
	/**
	 * Additional data that a debug adapter might want to loop through the client.
	 * <p>
	 * The client should leave the data intact and persist it across sessions. The client should not interpret the
	 * data.
	 * <p>
	 * This is an optional property.
	 */
	Object adapterData;
	/**
	 * The checksums associated with this file.
	 * <p>
	 * This is an optional property.
	 */
	Checksum[] checksums;
}

/**
 * An optional hint for how to present the source in the UI.
 * <p>
 * A value of 'deemphasize' can be used to indicate that the source is not available or that it is skipped on
 * stepping.
 */
enum SourcePresentationHint {
	NORMAL,
	EMPHASIZE,
	DEEMPHASIZE
}

/**
 * A Stackframe contains the source location.
 */
@JsonRpcData
class StackFrame {
	/**
	 * An identifier for the stack frame. It must be unique across all threads.
	 * <p>
	 * This id can be used to retrieve the scopes of the frame with the `scopes` request or to restart the execution of
	 * a stack frame.
	 */
	int id;
	/**
	 * The name of the stack frame, typically a method name.
	 */
	@NonNull
	String name;
	/**
	 * The source of the frame.
	 * <p>
	 * This is an optional property.
	 */
	Source source;
	/**
	 * The line within the source of the frame. If the source attribute is missing or doesn't exist,
	 * line is 0 and should be ignored by the client.
	 */
	int line;
	/**
	 * Start position of the range covered by the stack frame. It is measured in UTF-16 code units
	 * and the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based. If attribute `source` is missing or doesn't exist, `column`
	 * is 0 and should be ignored by the client.
	 */
	int column;
	/**
	 * The end line of the range covered by the stack frame.
	 * <p>
	 * This is an optional property.
	 */
	Integer endLine;
	/**
	 * End position of the range covered by the stack frame. It is measured in UTF-16 code units and
	 * the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer endColumn;
	/**
	 * Indicates whether this frame can be restarted with the `restartFrame` request.
	 * Clients should only use this if the debug adapter supports the `restart` request
	 * and the corresponding capability {@link Capabilities#getSupportsRestartRequest}
	 * is {@code true}. If a debug adapter has this capability, then `canRestart` defaults
	 * to `true` if the property is absent.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.45
	 */
	Boolean canRestart;
	/**
	 * A memory reference for the current instruction pointer in this frame.
	 * <p>
	 * This is an optional property.
	 */
	String instructionPointerReference;
	/**
	 * The module associated with this frame, if any.
	 * <p>
	 * This is an optional property.
	 */
	Either<Integer, String> moduleId;
	/**
	 * A hint for how to present this frame in the UI.
	 * <p>
	 * A value of 'label' can be used to indicate that the frame is an artificial frame that is used as a visual label
	 * or separator. A value of 'subtle' can be used to change the appearance of a frame in a 'subtle' way.
	 * <p>
	 * This is an optional property.
	 */
	StackFramePresentationHint presentationHint;
}

/**
 * An optional hint for how to present this frame in the UI.
 * <p>
 * A value of 'label' can be used to indicate that the frame is an artificial frame that is used as a visual label
 * or separator. A value of 'subtle' can be used to change the appearance of a frame in a 'subtle' way.
 */
enum StackFramePresentationHint {
	NORMAL,
	LABEL,
	SUBTLE
}

/**
 * A Scope is a named container for variables. Optionally a scope can map to a source or a range within a source.
 */
@JsonRpcData
class Scope {
	/**
	 * Name of the scope such as 'Arguments', 'Locals', or 'Registers'. This string is shown in the UI as is and can
	 * be translated.
	 */
	@NonNull
	String name;
	/**
	 * A hint for how to present this scope in the UI. If this attribute is missing, the scope is shown with
	 * a generic UI.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link ScopePresentationHint}
	 */
	String presentationHint;
	/**
	 * The variables of this scope can be retrieved by passing the value of `variablesReference` to the
	 * `variables` request as long as execution remains suspended.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 */
	int variablesReference;
	/**
	 * The number of named variables in this scope.
	 * <p>
	 * The client can use this information to present the variables in a paged UI and fetch them in chunks.
	 * <p>
	 * This is an optional property.
	 */
	Integer namedVariables;
	/**
	 * The number of indexed variables in this scope.
	 * <p>
	 * The client can use this information to present the variables in a paged UI and fetch them in chunks.
	 * <p>
	 * This is an optional property.
	 */
	Integer indexedVariables;
	/**
	 * If true, the number of variables in this scope is large or expensive to retrieve.
	 */
	boolean expensive;
	/**
	 * The source for this scope.
	 * <p>
	 * This is an optional property.
	 */
	Source source;
	/**
	 * The start line of the range covered by this scope.
	 * <p>
	 * This is an optional property.
	 */
	Integer line;
	/**
	 * Start position of the range covered by the scope. It is measured in UTF-16 code units and
	 * the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
	/**
	 * The end line of the range covered by this scope.
	 * <p>
	 * This is an optional property.
	 */
	Integer endLine;
	/**
	 * End position of the range covered by the scope. It is measured in UTF-16 code units and
	 * the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer endColumn;
}

/**
 * An optional hint for how to present this scope in the UI. If this attribute is missing, the scope is shown with
 * a generic UI.
 * <p>
 * Possible values include - but not limited to those defined in {@link ScopePresentationHint}
 */
interface ScopePresentationHint {
	/**
	 * Scope contains method arguments.
	 */
	public static final String ARGUMENTS = "arguments";
	/**
	 * Scope contains local variables.
	 */
	public static final String LOCALS = "locals";
	/**
	 * Scope contains registers. Only a single 'registers' scope should be returned from a 'scopes' request.
	 */
	public static final String REGISTERS = "registers";
	/**
	 * Scope contains one or more return values.
	 * <p>
	 * Since 1.67
	 */
	public static final String RETURN_VALUE = "returnValue";
}

/**
 * A Variable is a name/value pair.
 * <p>
 * The `type` attribute is shown if space permits or when hovering over the variable's
 * name.
 * <p>
 * The 'kind' attribute is used to render additional properties of the variable, e.g. different icons can be used to
 * indicate that a variable is public or private.
 * <p>
 * If the value is structured (has children), a handle is provided to retrieve the children with the
 * `variables` request.
 * <p>
 * If the number of named or indexed children is large, the numbers should be returned via the
 * 'namedVariables' and 'indexedVariables' attributes.
 * <p>
 * The client can use this information to present the children in a paged UI and fetch them in chunks.
 */
@JsonRpcData
class Variable {
	/**
	 * The variable's name.
	 */
	@NonNull
	String name;
	/**
	 * The variable's value. This can be a multi-line text, e.g. for a function the body of a function.
	 * <p>
	 * For structured variables (which do not have a simple value), it is recommended to provide a
	 * one-line representation of the structured object. This helps to identify the structured object
	 * in the collapsed state when its children are not yet visible.
	 * <p>
	 * An empty string can be used if no value should be shown in the UI.
	 */
	@NonNull
	String value;
	/**
	 * The type of the variable's value. Typically shown in the UI when hovering over the value.
	 * <p>
	 * This attribute should only be returned by a debug adapter if the corresponding capability
	 * {@link InitializeRequestArguments#getSupportsVariableType} is true.
	 * <p>
	 * This is an optional property.
	 */
	String type;
	/**
	 * Properties of a variable that can be used to determine how to render the variable in the UI.
	 * <p>
	 * This is an optional property.
	 */
	VariablePresentationHint presentationHint;
	/**
	 * The evaluatable name of this variable which can be passed to the 'EvaluateRequest' to fetch the variable's
	 * value.
	 * <p>
	 * This is an optional property.
	 */
	String evaluateName;
	/**
	 * If `variablesReference` is &gt; 0, the variable is structured and its children can be retrieved by passing
	 * `variablesReference` to the `variables` request as long as execution remains suspended.
	 * See 'Lifetime of Object References' in the {@link DebugProtocol#Overview} section for details.
	 */
	int variablesReference;
	/**
	 * The number of named child variables.
	 * <p>
	 * The client can use this information to present the children in a paged UI and fetch them in chunks.
	 * <p>
	 * This is an optional property.
	 */
	Integer namedVariables;
	/**
	 * The number of indexed child variables.
	 * <p>
	 * The client can use this information to present the children in a paged UI and fetch them in chunks.
	 * <p>
	 * This is an optional property.
	 */
	Integer indexedVariables;
	/**
	 * A memory reference associated with this variable.
	 * <p>
	 * For pointer type variables, this is generally a reference to the memory address contained in the pointer.
	 * <p>
	 * For executable data, this reference may later be used in a `disassemble` request.
	 * <p>
	 * This attribute may be returned by a debug adapter if corresponding capability
	 * {@link InitializeRequestArguments#getSupportsMemoryReferences} is true.
	 * <p>
	 * This is an optional property.
	 */
	String memoryReference;
	/**
	 * A reference that allows the client to request the location where the variable is declared. This should be
	 * present only if the adapter is likely to be able to resolve the location.
	 * <p>
	 * This reference shares the same lifetime as the `variablesReference`.
	 * See 'Lifetime of Object References' in the Overview section for details.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.68
	 */
	Integer declarationLocationReference;
	/**
	 * A reference that allows the client to request the location where the variable's value is declared. For example,
	 * if the variable contains a function pointer, the adapter may be able to look up the function's location.
	 * This should be present only if the adapter is likely to be able to resolve the location.
	 * <p>
	 * This reference shares the same lifetime as the `variablesReference`.
	 * See 'Lifetime of Object References' in the Overview section for details.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.68
	 */
	Integer valueLocationReference;
}

/**
 * Properties of a variable that can be used to determine how to render the variable in the UI.
 */
@JsonRpcData
class VariablePresentationHint {
	/**
	 * The kind of variable. Before introducing additional values, try to use the listed values.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link VariablePresentationHintKind}
	 */
	String kind;
	/**
	 * Set of attributes represented as an array of strings. Before introducing additional values, try to use the
	 * listed values.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link VariablePresentationHintAttributes}
	 */
	String[] attributes;
	/**
	 * Visibility of variable. Before introducing additional values, try to use the listed values.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Possible values include - but not limited to those defined in {@link VariablePresentationHintVisibility}
	 */
	String visibility;
	/**
	 * If true, clients can present the variable with a UI that supports a
	 * specific gesture to trigger its evaluation.
	 * <p>
	 * This mechanism can be used for properties that require executing code when
	 * retrieving their value and where the code execution can be expensive and/or
	 * produce side-effects. A typical example are properties based on a getter
	 * function.
	 * <b>
	 * Please note that in addition to the 'lazy' flag, the variable's
	 * 'variablesReference' is expected to refer to a variable that provides
	 * the value through another 'variable' request.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.54
	 */
	Boolean lazy;
}

/**
 * The kind of variable. Before introducing additional values, try to use the listed values.
 * <p>
 * Possible values include - but not limited to those defined in {@link VariablePresentationHintKind}
 */
interface VariablePresentationHintKind {
	/**
	 * Indicates that the object is a property.
	 */
	public static final String PROPERTY = "property";
	/**
	 * Indicates that the object is a method.
	 */
	public static final String METHOD = "method";
	/**
	 * Indicates that the object is a class.
	 */
	public static final String CLASS = "class";
	/**
	 * Indicates that the object is data.
	 */
	public static final String DATA = "data";
	/**
	 * Indicates that the object is an event.
	 */
	public static final String EVENT = "event";
	/**
	 * Indicates that the object is a base class.
	 */
	public static final String BASE_CLASS = "baseClass";
	/**
	 * Indicates that the object is an inner class.
	 */
	public static final String INNER_CLASS = "innerClass";
	/**
	 * Indicates that the object is an interface.
	 */
	public static final String INTERFACE = "interface";
	/**
	 * Indicates that the object is the most derived class.
	 */
	public static final String MOST_DERIVED_CLASS = "mostDerivedClass";
	/**
	 * Indicates that the object is virtual, that means it is a synthetic object introduced by the
	 * adapter for rendering purposes, e.g. an index range for large arrays.
	 */
	public static final String VIRTUAL = "virtual";
	/**
	 * Indicates that a data breakpoint is registered for the object.
	 *
	 * @deprecated The {@link VariablePresentationHintAttributes#HAS_DATA_BREAKPOINT} attribute should generally be used instead.
	 */
	@Deprecated
	public static final String DATA_BREAKPOINT = "dataBreakpoint";
}

/**

 * <p>
 * Possible values include - but not limited to those defined in {@link VariablePresentationHintAttributes}
 */
interface VariablePresentationHintAttributes {
	/**
	 * Indicates that the object is static.
	 */
	public static final String STATIC = "static";
	/**
	 * Indicates that the object is a constant.
	 */
	public static final String CONSTANT = "constant";
	/**
	 * Indicates that the object is read only.
	 */
	public static final String READ_ONLY = "readOnly";
	/**
	 * Indicates that the object is a raw string.
	 */
	public static final String RAW_STRING = "rawString";
	/**
	 * Indicates that the object can have an Object ID created for it.
	 * This is a vestigial attribute that is used by some clients; 'Object ID's are not specified in the protocol.
	 */
	public static final String HAS_OBJECT_ID = "hasObjectId";
	/**
	 * Indicates that the object has an Object ID associated with it.
	 * This is a vestigial attribute that is used by some clients; 'Object ID's are not specified in the protocol.
	 */
	public static final String CAN_HAVE_OBJECT_ID = "canHaveObjectId";
	/**
	 * Indicates that the evaluation had side effects.
	 */
	public static final String HAS_SIDE_EFFECTS = "hasSideEffects";
	/**
	 * Indicates that the object has its value tracked by a data breakpoint.
	 * <p>
	 * Since 1.44
	 */
	public static final String HAS_DATA_BREAKPOINT = "hasDataBreakpoint";
}

/**
 * Visibility of variable. Before introducing additional values, try to use the listed values.
 * <p>
 * Possible values include - but not limited to those defined in {@link VariablePresentationHintVisibility}
 */
interface VariablePresentationHintVisibility {
	public static final String PUBLIC = "public";
	public static final String PRIVATE = "private";
	public static final String PROTECTED = "protected";
	public static final String INTERNAL = "internal";
	public static final String FINAL = "final";
}

/**
 * Properties of a breakpoint location returned from the 'breakpointLocations' request.
 */
@JsonRpcData
class BreakpointLocation {
	/**
	 * Start line of breakpoint location.
	 */
	int line;
	/**
	 * The start position of a breakpoint location. Position is measured in UTF-16 code units and
	 * the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
	/**
	 * The end line of breakpoint location if the location covers a range.
	 * <p>
	 * This is an optional property.
	 */
	Integer endLine;
	/**
	 * The end position of a breakpoint location (if the location covers a range).
	 * Position is measured in UTF-16 code units and the client capability
	 * {@link InitializeRequestArguments#getColumnsStartAt1} determines whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer endColumn;
}

/**
 * Properties of a breakpoint or logpoint passed to the setBreakpoints request.
 */
@JsonRpcData
class SourceBreakpoint {
	/**
	 * The source line of the breakpoint or logpoint.
	 */
	int line;
	/**
	 * Start position within source line of the breakpoint or logpoint.
	 * It is measured in UTF-16 code units and the client capability
	 * {@link InitializeRequestArguments#getColumnsStartAt1} determines whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
	/**
	 * The expression for conditional breakpoints.
	 * <p>
	 * It is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsConditionalBreakpoints} is true.
	 * <p>
	 * This is an optional property.
	 */
	String condition;
	/**
	 * The expression that controls how many hits of the breakpoint are ignored.
	 * <p>
	 * The debug adapter is expected to interpret the expression as needed.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsHitConditionalBreakpoints} is true.
	 * <p>
	 * If both this property and `condition` are specified, `hitCondition` should be
	 * evaluated only if the `condition` is met, and the debug adapter should
	 * stop only if both conditions are met.
	 * <p>
	 * This is an optional property.
	 */
	String hitCondition;
	/**
	 * If this attribute exists and is non-empty, the debug adapter must not 'break' (stop)
	 * <p>
	 * but log the message instead. Expressions within {} are interpolated.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsLogPoints} is true.
	 * <p>
	 * If either `hitCondition` or `condition` is specified, then the message should only
	 * be logged if those conditions are met.
	 * <p>
	 * This is an optional property.
	 */
	String logMessage;
	/**
	 * The mode of this breakpoint. If defined, this must be one of the `breakpointModes`
	 * the debug adapter advertised in its `Capabilities`.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.65
	 */
	String mode;
}

/**
 * Properties of a breakpoint passed to the setFunctionBreakpoints request.
 */
@JsonRpcData
class FunctionBreakpoint {
	/**
	 * The name of the function.
	 */
	@NonNull
	String name;
	/**
	 * An expression for conditional breakpoints.
	 * <p>
	 * It is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsConditionalBreakpoints} is true.
	 * <p>
	 * This is an optional property.
	 */
	String condition;
	/**
	 * An expression that controls how many hits of the breakpoint are ignored.
	 * <p>
	 * The debug adapter is expected to interpret the expression as needed.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsHitConditionalBreakpoints} is true.
	 * <p>
	 * This is an optional property.
	 */
	String hitCondition;
}

/**
 * This enumeration defines all possible access types for data breakpoints.
 */
enum DataBreakpointAccessType {
	READ,
	WRITE,
	READ_WRITE
}

/**
 * Properties of a data breakpoint passed to the setDataBreakpoints request.
 */
@JsonRpcData
class DataBreakpoint {
	/**
	 * An id representing the data. This id is returned from the dataBreakpointInfo request.
	 */
	@NonNull
	String dataId;
	/**
	 * The access type of the data.
	 * <p>
	 * This is an optional property.
	 */
	DataBreakpointAccessType accessType;
	/**
	 * An expression for conditional breakpoints.
	 * <p>
	 * This is an optional property.
	 */
	String condition;
	/**
	 * An expression that controls how many hits of the breakpoint are ignored.
	 * <p>
	 * The debug adapter is expected to interpret the expression as needed.
	 * <p>
	 * This is an optional property.
	 */
	String hitCondition;
}

/**
 * Properties of a breakpoint passed to the setInstructionBreakpoints request
 */
@JsonRpcData
class InstructionBreakpoint {
	/**
	 * The instruction reference of the breakpoint.
	 * <p>
	 * This should be a memory or instruction pointer reference from an EvaluateResponse, Variable, StackFrame,
	 * GotoTarget, or Breakpoint.
	 */
	@NonNull
	String instructionReference;
	/**
	 * The offset from the instruction reference in bytes.
	 * <p>
	 * This can be negative.
	 * <p>
	 * This is an optional property.
	 */
	Integer offset;
	/**
	 * An expression for conditional breakpoints.
	 * <p>
	 * It is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsConditionalBreakpoints} is true.
	 * <p>
	 * This is an optional property.
	 */
	String condition;
	/**
	 * An expression that controls how many hits of the breakpoint are ignored.
	 * <p>
	 * The debug adapter is expected to interpret the expression as needed.
	 * <p>
	 * The attribute is only honored by a debug adapter if the corresponding capability
	 * {@link Capabilities#getSupportsHitConditionalBreakpoints} is true.
	 * <p>
	 * This is an optional property.
	 */
	String hitCondition;
	/**
	 * The mode of this breakpoint. If defined, this must be one of the `breakpointModes`
	 * the debug adapter advertised in its `Capabilities`.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.65
	 */
	String mode;
}

/**
 * Information about a breakpoint created in setBreakpoints, setFunctionBreakpoints, setInstructionBreakpoints, or
 * setDataBreakpoints requests.
 */
@JsonRpcData
class Breakpoint {
	/**
	 * The identifier for the breakpoint. It is needed if breakpoint events are used to update or remove
	 * breakpoints.
	 * <p>
	 * This is an optional property.
	 */
	Integer id;
	/**
	 * If true, breakpoint could be set (but not necessarily at the desired location).
	 */
	boolean verified;
	/**
	 * A message about the state of the breakpoint.
	 * <p>
	 * This is shown to the user and can be used to explain why a breakpoint could not be verified.
	 * <p>
	 * This is an optional property.
	 */
	String message;
	/**
	 * The source where the breakpoint is located.
	 * <p>
	 * This is an optional property.
	 */
	Source source;
	/**
	 * The start line of the actual range covered by the breakpoint.
	 * <p>
	 * This is an optional property.
	 */
	Integer line;
	/**
	 * Start position of the source range covered by the breakpoint. It is measured in UTF-16 code units
	 * and the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
	/**
	 * The end line of the actual range covered by the breakpoint.
	 * <p>
	 * This is an optional property.
	 */
	Integer endLine;
	/**
	 * End position of the source range covered by the breakpoint. It is measured in UTF-16 code units
	 * and the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based.
	 * <p>
	 * If no end line is given, then the end column is assumed to be in the start line.
	 * <p>
	 * This is an optional property.
	 */
	Integer endColumn;
	/**
	 * A memory reference to where the breakpoint is set.
	 * <p>
	 * This is an optional property.
	 */
	String instructionReference;
	/**
	 * The offset from the instruction reference.
	 * <p>
	 * This can be negative.
	 * <p>
	 * This is an optional property.
	 */
	Integer offset;
	/**
	 * A machine-readable explanation of why a breakpoint may not be verified. If
	 * a breakpoint is verified or a specific reason is not known, the adapter
	 * should omit this property. Possible values include:
	 * <ul>
	 * <li>`pending`: Indicates a breakpoint might be verified in the future, but
	 * the adapter cannot verify it in the current state.</li>
	 * <li>`failed`: Indicates a breakpoint was not able to be verified, and the
	 * adapter does not believe it can be verified without intervention.</li>
	 * </ul>
	 * <p>
	 * Values: 'pending', 'failed'
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.64
	 */
	BreakpointNotVerifiedReason reason;
}

/**
 * A machine-readable explanation of why a breakpoint may not be verified. If
 * a breakpoint is verified or a specific reason is not known, the adapter
 * should omit this property.
 * <p>
 * Since 1.64
 */
enum BreakpointNotVerifiedReason {
	/**
	 * Indicates a breakpoint might be verified in the future, but the adapter cannot verify it in the current state.
	 */
	PENDING,
	/**
	 * Indicates a breakpoint was not able to be verified,
	 * and the adapter does not believe it can be verified without intervention.
	 */
	FAILED
}

/**
 * The granularity of one 'step' in the stepping requests 'next', 'stepIn', 'stepOut', and 'stepBack'.
 */
enum SteppingGranularity {
	/**
	 * The step should allow the program to run until the current statement has finished executing.
	 * The meaning of a
	 * statement is determined by the adapter and it may be considered equivalent to a line.
	 * For example 'for(int i =
	 * 0; i &lt; 10; i++) could be considered to have 3 statements 'int i = 0', 'i &lt; 10', and 'i++'.
	 */
	STATEMENT,
	/**
	 * The step should allow the program to run until the current source line has executed.
	 */
	LINE,
	/**
	 * The step should allow one instruction to execute (e.g. one x86 instruction).
	 */
	INSTRUCTION
}

/**
 * A StepInTarget can be used in the `stepIn` request and determines into which single target the `stepIn` request
 * should step.
 */
@JsonRpcData
class StepInTarget {
	/**
	 * Unique identifier for a step-in target.
	 */
	int id;
	/**
	 * The name of the step-in target (shown in the UI).
	 */
	@NonNull
	String label;
	/**
	 * The line of the step-in target.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.56
	 */
	Integer line;
	/**
	 * Start position of the range covered by the step in target. It is measured in UTF-16 code units
	 * and the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.56
	 */
	Integer column;
	/**
	 * The end line of the range covered by the step-in target.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.56
	 */
	Integer endLine;
	/**
	 * End position of the range covered by the step in target. It is measured in UTF-16 code units
	 * and the client capability {@link InitializeRequestArguments#getColumnsStartAt1} determines
	 * whether it is 0- or 1-based.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.56
	 */
	Integer endColumn;
}

/**
 * A GotoTarget describes a code location that can be used as a target in the 'goto' request.
 * <p>
 * The possible goto targets can be determined via the 'gotoTargets' request.
 */
@JsonRpcData
class GotoTarget {
	/**
	 * Unique identifier for a goto target. This is used in the goto request.
	 */
	int id;
	/**
	 * The name of the goto target (shown in the UI).
	 */
	@NonNull
	String label;
	/**
	 * The line of the goto target.
	 */
	int line;
	/**
	 * The column of the goto target.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
	/**
	 * The end line of the range covered by the goto target.
	 * <p>
	 * This is an optional property.
	 */
	Integer endLine;
	/**
	 * The end column of the range covered by the goto target.
	 * <p>
	 * This is an optional property.
	 */
	Integer endColumn;
	/**
	 * A memory reference for the instruction pointer value represented by this target.
	 * <p>
	 * This is an optional property.
	 */
	String instructionPointerReference;
}

/**
 * CompletionItems are the suggestions returned from the `completions` request.
 */
@JsonRpcData
class CompletionItem {
	/**
	 * The label of this completion item. By default this is also the text that is inserted when selecting this
	 * completion.
	 */
	@NonNull
	String label;
	/**
	 * If text is returned and not an empty string, then it is inserted instead of the label.
	 * <p>
	 * This is an optional property.
	 */
	String text;
	/**
	 * A string that should be used when comparing this item with other items. If not returned or an empty string, the `label` is used instead.
	 * <p>
	 * This is an optional property.
	 */
	String sortText;
	/**
	 * A human-readable string with additional information about this item, like
	 * type or symbol information.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.53
	 */
	String detail;
	/**
	 * The item's type. Typically the client uses this information to render the item in the UI with an icon.
	 * <p>
	 * This is an optional property.
	 */
	CompletionItemType type;
	/**
	 * Start position (within the `text` attribute of the `completions` request) where the completion text is added.
	 * The position is measured in UTF-16 code units and the client capability {@link InitializeRequestArguments#getColumnsStartAt1}
	 * determines whether it is 0- or 1-based. If the start position is omitted the text is added at the location specified
	 * by the `column` attribute of the `completions` request.
	 * <p>
	 * This is an optional property.
	 */
	Integer start;
	/**
	 * Length determines how many characters are overwritten by the completion text and it is measured in UTF-16 code units.
	 * If missing the value 0 is assumed which results in the completion text being inserted.
	 * <p>
	 * This is an optional property.
	 */
	Integer length;
	/**
	 * Determines the start of the new selection after the text has been inserted (or replaced). `selectionStart` is measured
	 * in UTF-16 code units and must be in the range 0 and length of the completion text. If omitted the selection starts at
	 * the end of the completion text.
	 * <p>
	 * This is an optional property.
	 */
	Integer selectionStart;
	/**
	 * Determines the length of the new selection after the text has been inserted (or replaced) and it is measured in
	 * UTF-16 code units. The selection can not extend beyond the bounds of the completion text. If omitted the length is
	 * assumed to be 0.
	 * <p>
	 * This is an optional property.
	 */
	Integer selectionLength;
}

/**
 * Some predefined types for the CompletionItem. Please note that not all clients have specific icons for all of
 * them.
 */
enum CompletionItemType {
	METHOD,
	FUNCTION,
	CONSTRUCTOR,
	FIELD,
	VARIABLE,
	CLASS,
	INTERFACE,
	MODULE,
	PROPERTY,
	UNIT,
	VALUE,
	ENUM,
	KEYWORD,
	SNIPPET,
	TEXT,
	COLOR,
	FILE,
	REFERENCE,
	CUSTOMCOLOR
}

/**
 * Names of checksum algorithms that may be supported by a debug adapter.
 */
enum ChecksumAlgorithm {
	@SerializedName("MD5")
	MD5,
	@SerializedName("SHA1")
	SHA1,
	@SerializedName("SHA256")
	SHA256,
	TIMESTAMP
}

/**
 * The checksum of an item calculated by the specified algorithm.
 */
@JsonRpcData
class Checksum {
	/**
	 * The algorithm used to calculate this checksum.
	 */
	@NonNull
	ChecksumAlgorithm algorithm;
	/**
	 * Value of the checksum, encoded as a hexadecimal value.
	 */
	@NonNull
	String checksum;
}

/**
 * Provides formatting information for a value.
 */
@JsonRpcData
class ValueFormat {
	/**
	 * Display the value in hex.
	 * <p>
	 * This is an optional property.
	 */
	Boolean hex;
}

/**
 * Provides formatting information for a stack frame.
 */
@JsonRpcData
class StackFrameFormat extends ValueFormat {
	/**
	 * Displays parameters for the stack frame.
	 * <p>
	 * This is an optional property.
	 */
	Boolean parameters;
	/**
	 * Displays the types of parameters for the stack frame.
	 * <p>
	 * This is an optional property.
	 */
	Boolean parameterTypes;
	/**
	 * Displays the names of parameters for the stack frame.
	 * <p>
	 * This is an optional property.
	 */
	Boolean parameterNames;
	/**
	 * Displays the values of parameters for the stack frame.
	 * <p>
	 * This is an optional property.
	 */
	Boolean parameterValues;
	/**
	 * Displays the line number of the stack frame.
	 * <p>
	 * This is an optional property.
	 */
	Boolean line;
	/**
	 * Displays the module of the stack frame.
	 * <p>
	 * This is an optional property.
	 */
	Boolean module;
	/**
	 * Includes all stack frames, including those the debug adapter might otherwise hide.
	 * <p>
	 * This is an optional property.
	 */
	Boolean includeAll;
}

/**
 * An ExceptionFilterOptions is used to specify an exception filter together with a condition for the
 * 'setExceptionBreakpoints' request.
 * <p>
 * Since 1.43
 */
@JsonRpcData
class ExceptionFilterOptions {
	/**
	 * ID of an exception filter returned by the 'exceptionBreakpointFilters' capability.
	 */
	@NonNull
	String filterId;
	/**
	 * An expression for conditional exceptions.
	 * <p>
	 * The exception breaks into the debugger if the result of the condition is true.
	 * <p>
	 * This is an optional property.
	 */
	String condition;

	/**
	 * The mode of this exception breakpoint. If defined, this must be one of the `breakpointModes`
	 * the debug adapter advertised in its `Capabilities`.
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.65
	 */
	String mode;
}

/**
 * An ExceptionOptions assigns configuration options to a set of exceptions.
 */
@JsonRpcData
class ExceptionOptions {
	/**
	 * A path that selects a single or multiple exceptions in a tree. If 'path' is missing, the whole tree is
	 * selected.
	 * <p>
	 * By convention the first segment of the path is a category that is used to group exceptions in the UI.
	 * <p>
	 * This is an optional property.
	 */
	ExceptionPathSegment[] path;
	/**
	 * Condition when a thrown exception should result in a break.
	 */
	@NonNull
	ExceptionBreakMode breakMode;
}

/**
 * This enumeration defines all possible conditions when a thrown exception should result in a break.
 * <p>
 * never: never breaks,
 * <p>
 * always: always breaks,
 * <p>
 * unhandled: breaks when exception unhandled,
 * <p>
 * userUnhandled: breaks if the exception is not handled by user code.
 */
enum ExceptionBreakMode {
	NEVER,
	ALWAYS,
	UNHANDLED,
	USER_UNHANDLED
}

/**
 * An ExceptionPathSegment represents a segment in a path that is used to match leafs or nodes in a tree of
 * exceptions.
 * <p>
 * If a segment consists of more than one name, it matches the names provided if 'negate' is false or missing, or
 * it matches anything except the names provided if 'negate' is true.
 */
@JsonRpcData
class ExceptionPathSegment {
	/**
	 * If false or missing this segment matches the names provided, otherwise it matches anything except the names
	 * provided.
	 * <p>
	 * This is an optional property.
	 */
	Boolean negate;
	/**
	 * Depending on the value of 'negate' the names that should match or not match.
	 */
	@NonNull
	String[] names;
}

/**
 * Detailed information about an exception that has occurred.
 */
@JsonRpcData
class ExceptionDetails {
	/**
	 * Message contained in the exception.
	 * <p>
	 * This is an optional property.
	 */
	String message;
	/**
	 * Short type name of the exception object.
	 * <p>
	 * This is an optional property.
	 */
	String typeName;
	/**
	 * Fully-qualified type name of the exception object.
	 * <p>
	 * This is an optional property.
	 */
	String fullTypeName;
	/**
	 * An expression that can be evaluated in the current scope to obtain the exception object.
	 * <p>
	 * This is an optional property.
	 */
	String evaluateName;
	/**
	 * Stack trace at the time the exception was thrown.
	 * <p>
	 * This is an optional property.
	 */
	String stackTrace;
	/**
	 * Details of the exception contained by this exception, if any.
	 * <p>
	 * This is an optional property.
	 */
	ExceptionDetails[] innerException;
}

/**
 * Represents a single disassembled instruction.
 */
@JsonRpcData
class DisassembledInstruction {
	/**
	 * The address of the instruction. Treated as a hex value if prefixed with '0x', or as a decimal value otherwise.
	 */
	@NonNull
	String address;
	/**
	 * Raw bytes representing the instruction and its operands, in an implementation-defined format.
	 * <p>
	 * This is an optional property.
	 */
	String instructionBytes;
	/**
	 * Text representing the instruction and its operands, in an implementation-defined format.
	 */
	@NonNull
	String instruction;
	/**
	 * Name of the symbol that corresponds with the location of this instruction, if any.
	 * <p>
	 * This is an optional property.
	 */
	String symbol;
	/**
	 * Source location that corresponds to this instruction, if any.
	 * <p>
	 * Should always be set (if available) on the first instruction returned,
	 * <p>
	 * but can be omitted afterwards if this instruction maps to the same source file as the previous instruction.
	 * <p>
	 * This is an optional property.
	 */
	Source location;
	/**
	 * The line within the source location that corresponds to this instruction, if any.
	 * <p>
	 * This is an optional property.
	 */
	Integer line;
	/**
	 * The column within the line that corresponds to this instruction, if any.
	 * <p>
	 * This is an optional property.
	 */
	Integer column;
	/**
	 * The end line of the range that corresponds to this instruction, if any.
	 * <p>
	 * This is an optional property.
	 */
	Integer endLine;
	/**
	 * The end column of the range that corresponds to this instruction, if any.
	 * <p>
	 * This is an optional property.
	 */
	Integer endColumn;
	/**
	 * A hint for how to present the instruction in the UI.
	 * <p>
	 * <p>
	 * A value of `invalid` may be used to indicate this instruction is 'filler' and cannot be reached by the program.
	 * For example, unreadable memory addresses may be presented is 'invalid.'
	 * <p>
	 * This is an optional property.
	 * <p>
	 * Since 1.64
	 */
	DisassembledInstructionPresentationHint presentationHint;
}

/**
 * A hint for how to present the instruction in the UI.
 * <p>
 * <p>
 * A value of `invalid` may be used to indicate this instruction is 'filler' and cannot be reached by the program.
 * For example, unreadable memory addresses may be presented is 'invalid.'
 * <p>
 * This is an optional property.
 * <p>
 * Since 1.64
 */
enum DisassembledInstructionPresentationHint {
	NORMAL,
	INVALID
}

/**
 * Logical areas that can be invalidated by the 'invalidated' event.
 * <p>
 * Possible values include - but not limited to those defined here
 */
interface InvalidatedAreas {
	/**
	 * All previously fetched data has become invalid and needs to be refetched.
	 */
	public static final String ALL = "all";
	/**
	 * Previously fetched stack related data has become invalid and needs to be refetched.
	 */
	public static final String STACKS = "stacks";
	/**
	 * Previously fetched thread related data has become invalid and needs to be refetched.
	 */
	public static final String THREADS = "threads";
	/**
	 * Previously fetched variable data has become invalid and needs to be refetched.
	 */
	public static final String VARIABLES = "variables";
}

/**
 * Indicates whether the new debug session should be started with a `launch`
 * or `attach` request.
 */
enum StartDebuggingRequestArgumentsType {
	LAUNCH,
	ATTACH
}

/**
 * Arguments for 'startDebugging' request.
 */
@JsonRpcData
class StartDebuggingRequestArguments {

	/**
	 * Arguments passed to the new debug session. The arguments must only contain
	 * properties understood by the `launch` or `attach` requests of the debug
	 * adapter and they must not contain any client-specific properties (e.g.
	 * `type`) or client-specific features (e.g. substitutable 'variables').
	 */
	@NonNull
	Map<String, ?> configuration;

	/**
	 * Indicates whether the new debug session should be started with a `launch`
	 * or `attach` request.
	 */
	@NonNull
	StartDebuggingRequestArgumentsType request;
}
