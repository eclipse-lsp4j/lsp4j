/**
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.debug;

import java.util.Arrays;
import org.eclipse.lsp4j.debug.ChecksumAlgorithm;
import org.eclipse.lsp4j.debug.ColumnDescriptor;
import org.eclipse.lsp4j.debug.ExceptionBreakpointsFilter;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Information about the capabilities of a debug adapter.
 */
@SuppressWarnings("all")
public class Capabilities {
  /**
   * The debug adapter supports the 'configurationDone' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsConfigurationDoneRequest;
  
  /**
   * The debug adapter supports function breakpoints.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsFunctionBreakpoints;
  
  /**
   * The debug adapter supports conditional breakpoints.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsConditionalBreakpoints;
  
  /**
   * The debug adapter supports breakpoints that break execution after a specified number of hits.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsHitConditionalBreakpoints;
  
  /**
   * The debug adapter supports a (side effect free) evaluate request for data hovers.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsEvaluateForHovers;
  
  /**
   * Available filters or options for the setExceptionBreakpoints request.
   * <p>
   * This is an optional property.
   */
  private ExceptionBreakpointsFilter[] exceptionBreakpointFilters;
  
  /**
   * The debug adapter supports stepping back via the 'stepBack' and 'reverseContinue' requests.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsStepBack;
  
  /**
   * The debug adapter supports setting a variable to a value.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsSetVariable;
  
  /**
   * The debug adapter supports restarting a frame.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsRestartFrame;
  
  /**
   * The debug adapter supports the 'gotoTargets' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsGotoTargetsRequest;
  
  /**
   * The debug adapter supports the 'stepInTargets' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsStepInTargetsRequest;
  
  /**
   * The debug adapter supports the 'completions' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsCompletionsRequest;
  
  /**
   * The set of characters that should trigger completion in a REPL. If not specified, the UI should assume the '.'
   * character.
   * <p>
   * This is an optional property.
   */
  private String[] completionTriggerCharacters;
  
  /**
   * The debug adapter supports the 'modules' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsModulesRequest;
  
  /**
   * The set of additional module information exposed by the debug adapter.
   * <p>
   * This is an optional property.
   */
  private ColumnDescriptor[] additionalModuleColumns;
  
  /**
   * Checksum algorithms supported by the debug adapter.
   * <p>
   * This is an optional property.
   */
  private ChecksumAlgorithm[] supportedChecksumAlgorithms;
  
  /**
   * The debug adapter supports the 'restart' request. In this case a client should not implement 'restart' by
   * terminating and relaunching the adapter but by calling the RestartRequest.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsRestartRequest;
  
  /**
   * The debug adapter supports 'exceptionOptions' on the setExceptionBreakpoints request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsExceptionOptions;
  
  /**
   * The debug adapter supports a 'format' attribute on the stackTraceRequest, variablesRequest, and
   * evaluateRequest.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsValueFormattingOptions;
  
  /**
   * The debug adapter supports the 'exceptionInfo' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsExceptionInfoRequest;
  
  /**
   * The debug adapter supports the 'terminateDebuggee' attribute on the 'disconnect' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportTerminateDebuggee;
  
  /**
   * The debug adapter supports the delayed loading of parts of the stack, which requires that both the 'startFrame'
   * and 'levels' arguments and the 'totalFrames' result of the 'StackTrace' request are supported.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsDelayedStackTraceLoading;
  
  /**
   * The debug adapter supports the 'loadedSources' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsLoadedSourcesRequest;
  
  /**
   * The debug adapter supports logpoints by interpreting the 'logMessage' attribute of the SourceBreakpoint.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsLogPoints;
  
  /**
   * The debug adapter supports the 'terminateThreads' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsTerminateThreadsRequest;
  
  /**
   * The debug adapter supports the 'setExpression' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsSetExpression;
  
  /**
   * The debug adapter supports the 'terminate' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsTerminateRequest;
  
  /**
   * The debug adapter supports data breakpoints.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsDataBreakpoints;
  
  /**
   * The debug adapter supports the 'readMemory' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsReadMemoryRequest;
  
  /**
   * The debug adapter supports the 'disassemble' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsDisassembleRequest;
  
  /**
   * The debug adapter supports the 'cancel' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsCancelRequest;
  
  /**
   * The debug adapter supports the 'breakpointLocations' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsBreakpointLocationsRequest;
  
  /**
   * The debug adapter supports the 'clipboard' context value in the 'evaluate' request.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsClipboardContext;
  
  /**
   * The debug adapter supports stepping granularities (argument 'granularity') for the stepping requests.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsSteppingGranularity;
  
  /**
   * The debug adapter supports adding breakpoints based on instruction references.
   * <p>
   * This is an optional property.
   */
  private Boolean supportsInstructionBreakpoints;
  
  /**
   * The debug adapter supports the 'configurationDone' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsConfigurationDoneRequest() {
    return this.supportsConfigurationDoneRequest;
  }
  
  /**
   * The debug adapter supports the 'configurationDone' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsConfigurationDoneRequest(final Boolean supportsConfigurationDoneRequest) {
    this.supportsConfigurationDoneRequest = supportsConfigurationDoneRequest;
  }
  
  /**
   * The debug adapter supports function breakpoints.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsFunctionBreakpoints() {
    return this.supportsFunctionBreakpoints;
  }
  
  /**
   * The debug adapter supports function breakpoints.
   * <p>
   * This is an optional property.
   */
  public void setSupportsFunctionBreakpoints(final Boolean supportsFunctionBreakpoints) {
    this.supportsFunctionBreakpoints = supportsFunctionBreakpoints;
  }
  
  /**
   * The debug adapter supports conditional breakpoints.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsConditionalBreakpoints() {
    return this.supportsConditionalBreakpoints;
  }
  
  /**
   * The debug adapter supports conditional breakpoints.
   * <p>
   * This is an optional property.
   */
  public void setSupportsConditionalBreakpoints(final Boolean supportsConditionalBreakpoints) {
    this.supportsConditionalBreakpoints = supportsConditionalBreakpoints;
  }
  
  /**
   * The debug adapter supports breakpoints that break execution after a specified number of hits.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsHitConditionalBreakpoints() {
    return this.supportsHitConditionalBreakpoints;
  }
  
  /**
   * The debug adapter supports breakpoints that break execution after a specified number of hits.
   * <p>
   * This is an optional property.
   */
  public void setSupportsHitConditionalBreakpoints(final Boolean supportsHitConditionalBreakpoints) {
    this.supportsHitConditionalBreakpoints = supportsHitConditionalBreakpoints;
  }
  
  /**
   * The debug adapter supports a (side effect free) evaluate request for data hovers.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsEvaluateForHovers() {
    return this.supportsEvaluateForHovers;
  }
  
  /**
   * The debug adapter supports a (side effect free) evaluate request for data hovers.
   * <p>
   * This is an optional property.
   */
  public void setSupportsEvaluateForHovers(final Boolean supportsEvaluateForHovers) {
    this.supportsEvaluateForHovers = supportsEvaluateForHovers;
  }
  
  /**
   * Available filters or options for the setExceptionBreakpoints request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ExceptionBreakpointsFilter[] getExceptionBreakpointFilters() {
    return this.exceptionBreakpointFilters;
  }
  
  /**
   * Available filters or options for the setExceptionBreakpoints request.
   * <p>
   * This is an optional property.
   */
  public void setExceptionBreakpointFilters(final ExceptionBreakpointsFilter[] exceptionBreakpointFilters) {
    this.exceptionBreakpointFilters = exceptionBreakpointFilters;
  }
  
  /**
   * The debug adapter supports stepping back via the 'stepBack' and 'reverseContinue' requests.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsStepBack() {
    return this.supportsStepBack;
  }
  
  /**
   * The debug adapter supports stepping back via the 'stepBack' and 'reverseContinue' requests.
   * <p>
   * This is an optional property.
   */
  public void setSupportsStepBack(final Boolean supportsStepBack) {
    this.supportsStepBack = supportsStepBack;
  }
  
  /**
   * The debug adapter supports setting a variable to a value.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsSetVariable() {
    return this.supportsSetVariable;
  }
  
  /**
   * The debug adapter supports setting a variable to a value.
   * <p>
   * This is an optional property.
   */
  public void setSupportsSetVariable(final Boolean supportsSetVariable) {
    this.supportsSetVariable = supportsSetVariable;
  }
  
  /**
   * The debug adapter supports restarting a frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsRestartFrame() {
    return this.supportsRestartFrame;
  }
  
  /**
   * The debug adapter supports restarting a frame.
   * <p>
   * This is an optional property.
   */
  public void setSupportsRestartFrame(final Boolean supportsRestartFrame) {
    this.supportsRestartFrame = supportsRestartFrame;
  }
  
  /**
   * The debug adapter supports the 'gotoTargets' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsGotoTargetsRequest() {
    return this.supportsGotoTargetsRequest;
  }
  
  /**
   * The debug adapter supports the 'gotoTargets' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsGotoTargetsRequest(final Boolean supportsGotoTargetsRequest) {
    this.supportsGotoTargetsRequest = supportsGotoTargetsRequest;
  }
  
  /**
   * The debug adapter supports the 'stepInTargets' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsStepInTargetsRequest() {
    return this.supportsStepInTargetsRequest;
  }
  
  /**
   * The debug adapter supports the 'stepInTargets' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsStepInTargetsRequest(final Boolean supportsStepInTargetsRequest) {
    this.supportsStepInTargetsRequest = supportsStepInTargetsRequest;
  }
  
  /**
   * The debug adapter supports the 'completions' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsCompletionsRequest() {
    return this.supportsCompletionsRequest;
  }
  
  /**
   * The debug adapter supports the 'completions' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsCompletionsRequest(final Boolean supportsCompletionsRequest) {
    this.supportsCompletionsRequest = supportsCompletionsRequest;
  }
  
  /**
   * The set of characters that should trigger completion in a REPL. If not specified, the UI should assume the '.'
   * character.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String[] getCompletionTriggerCharacters() {
    return this.completionTriggerCharacters;
  }
  
  /**
   * The set of characters that should trigger completion in a REPL. If not specified, the UI should assume the '.'
   * character.
   * <p>
   * This is an optional property.
   */
  public void setCompletionTriggerCharacters(final String[] completionTriggerCharacters) {
    this.completionTriggerCharacters = completionTriggerCharacters;
  }
  
  /**
   * The debug adapter supports the 'modules' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsModulesRequest() {
    return this.supportsModulesRequest;
  }
  
  /**
   * The debug adapter supports the 'modules' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsModulesRequest(final Boolean supportsModulesRequest) {
    this.supportsModulesRequest = supportsModulesRequest;
  }
  
  /**
   * The set of additional module information exposed by the debug adapter.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ColumnDescriptor[] getAdditionalModuleColumns() {
    return this.additionalModuleColumns;
  }
  
  /**
   * The set of additional module information exposed by the debug adapter.
   * <p>
   * This is an optional property.
   */
  public void setAdditionalModuleColumns(final ColumnDescriptor[] additionalModuleColumns) {
    this.additionalModuleColumns = additionalModuleColumns;
  }
  
  /**
   * Checksum algorithms supported by the debug adapter.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ChecksumAlgorithm[] getSupportedChecksumAlgorithms() {
    return this.supportedChecksumAlgorithms;
  }
  
  /**
   * Checksum algorithms supported by the debug adapter.
   * <p>
   * This is an optional property.
   */
  public void setSupportedChecksumAlgorithms(final ChecksumAlgorithm[] supportedChecksumAlgorithms) {
    this.supportedChecksumAlgorithms = supportedChecksumAlgorithms;
  }
  
  /**
   * The debug adapter supports the 'restart' request. In this case a client should not implement 'restart' by
   * terminating and relaunching the adapter but by calling the RestartRequest.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsRestartRequest() {
    return this.supportsRestartRequest;
  }
  
  /**
   * The debug adapter supports the 'restart' request. In this case a client should not implement 'restart' by
   * terminating and relaunching the adapter but by calling the RestartRequest.
   * <p>
   * This is an optional property.
   */
  public void setSupportsRestartRequest(final Boolean supportsRestartRequest) {
    this.supportsRestartRequest = supportsRestartRequest;
  }
  
  /**
   * The debug adapter supports 'exceptionOptions' on the setExceptionBreakpoints request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsExceptionOptions() {
    return this.supportsExceptionOptions;
  }
  
  /**
   * The debug adapter supports 'exceptionOptions' on the setExceptionBreakpoints request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsExceptionOptions(final Boolean supportsExceptionOptions) {
    this.supportsExceptionOptions = supportsExceptionOptions;
  }
  
  /**
   * The debug adapter supports a 'format' attribute on the stackTraceRequest, variablesRequest, and
   * evaluateRequest.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsValueFormattingOptions() {
    return this.supportsValueFormattingOptions;
  }
  
  /**
   * The debug adapter supports a 'format' attribute on the stackTraceRequest, variablesRequest, and
   * evaluateRequest.
   * <p>
   * This is an optional property.
   */
  public void setSupportsValueFormattingOptions(final Boolean supportsValueFormattingOptions) {
    this.supportsValueFormattingOptions = supportsValueFormattingOptions;
  }
  
  /**
   * The debug adapter supports the 'exceptionInfo' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsExceptionInfoRequest() {
    return this.supportsExceptionInfoRequest;
  }
  
  /**
   * The debug adapter supports the 'exceptionInfo' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsExceptionInfoRequest(final Boolean supportsExceptionInfoRequest) {
    this.supportsExceptionInfoRequest = supportsExceptionInfoRequest;
  }
  
  /**
   * The debug adapter supports the 'terminateDebuggee' attribute on the 'disconnect' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportTerminateDebuggee() {
    return this.supportTerminateDebuggee;
  }
  
  /**
   * The debug adapter supports the 'terminateDebuggee' attribute on the 'disconnect' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportTerminateDebuggee(final Boolean supportTerminateDebuggee) {
    this.supportTerminateDebuggee = supportTerminateDebuggee;
  }
  
  /**
   * The debug adapter supports the delayed loading of parts of the stack, which requires that both the 'startFrame'
   * and 'levels' arguments and the 'totalFrames' result of the 'StackTrace' request are supported.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsDelayedStackTraceLoading() {
    return this.supportsDelayedStackTraceLoading;
  }
  
  /**
   * The debug adapter supports the delayed loading of parts of the stack, which requires that both the 'startFrame'
   * and 'levels' arguments and the 'totalFrames' result of the 'StackTrace' request are supported.
   * <p>
   * This is an optional property.
   */
  public void setSupportsDelayedStackTraceLoading(final Boolean supportsDelayedStackTraceLoading) {
    this.supportsDelayedStackTraceLoading = supportsDelayedStackTraceLoading;
  }
  
  /**
   * The debug adapter supports the 'loadedSources' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsLoadedSourcesRequest() {
    return this.supportsLoadedSourcesRequest;
  }
  
  /**
   * The debug adapter supports the 'loadedSources' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsLoadedSourcesRequest(final Boolean supportsLoadedSourcesRequest) {
    this.supportsLoadedSourcesRequest = supportsLoadedSourcesRequest;
  }
  
  /**
   * The debug adapter supports logpoints by interpreting the 'logMessage' attribute of the SourceBreakpoint.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsLogPoints() {
    return this.supportsLogPoints;
  }
  
  /**
   * The debug adapter supports logpoints by interpreting the 'logMessage' attribute of the SourceBreakpoint.
   * <p>
   * This is an optional property.
   */
  public void setSupportsLogPoints(final Boolean supportsLogPoints) {
    this.supportsLogPoints = supportsLogPoints;
  }
  
  /**
   * The debug adapter supports the 'terminateThreads' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsTerminateThreadsRequest() {
    return this.supportsTerminateThreadsRequest;
  }
  
  /**
   * The debug adapter supports the 'terminateThreads' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsTerminateThreadsRequest(final Boolean supportsTerminateThreadsRequest) {
    this.supportsTerminateThreadsRequest = supportsTerminateThreadsRequest;
  }
  
  /**
   * The debug adapter supports the 'setExpression' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsSetExpression() {
    return this.supportsSetExpression;
  }
  
  /**
   * The debug adapter supports the 'setExpression' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsSetExpression(final Boolean supportsSetExpression) {
    this.supportsSetExpression = supportsSetExpression;
  }
  
  /**
   * The debug adapter supports the 'terminate' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsTerminateRequest() {
    return this.supportsTerminateRequest;
  }
  
  /**
   * The debug adapter supports the 'terminate' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsTerminateRequest(final Boolean supportsTerminateRequest) {
    this.supportsTerminateRequest = supportsTerminateRequest;
  }
  
  /**
   * The debug adapter supports data breakpoints.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsDataBreakpoints() {
    return this.supportsDataBreakpoints;
  }
  
  /**
   * The debug adapter supports data breakpoints.
   * <p>
   * This is an optional property.
   */
  public void setSupportsDataBreakpoints(final Boolean supportsDataBreakpoints) {
    this.supportsDataBreakpoints = supportsDataBreakpoints;
  }
  
  /**
   * The debug adapter supports the 'readMemory' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsReadMemoryRequest() {
    return this.supportsReadMemoryRequest;
  }
  
  /**
   * The debug adapter supports the 'readMemory' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsReadMemoryRequest(final Boolean supportsReadMemoryRequest) {
    this.supportsReadMemoryRequest = supportsReadMemoryRequest;
  }
  
  /**
   * The debug adapter supports the 'disassemble' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsDisassembleRequest() {
    return this.supportsDisassembleRequest;
  }
  
  /**
   * The debug adapter supports the 'disassemble' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsDisassembleRequest(final Boolean supportsDisassembleRequest) {
    this.supportsDisassembleRequest = supportsDisassembleRequest;
  }
  
  /**
   * The debug adapter supports the 'cancel' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsCancelRequest() {
    return this.supportsCancelRequest;
  }
  
  /**
   * The debug adapter supports the 'cancel' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsCancelRequest(final Boolean supportsCancelRequest) {
    this.supportsCancelRequest = supportsCancelRequest;
  }
  
  /**
   * The debug adapter supports the 'breakpointLocations' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsBreakpointLocationsRequest() {
    return this.supportsBreakpointLocationsRequest;
  }
  
  /**
   * The debug adapter supports the 'breakpointLocations' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsBreakpointLocationsRequest(final Boolean supportsBreakpointLocationsRequest) {
    this.supportsBreakpointLocationsRequest = supportsBreakpointLocationsRequest;
  }
  
  /**
   * The debug adapter supports the 'clipboard' context value in the 'evaluate' request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsClipboardContext() {
    return this.supportsClipboardContext;
  }
  
  /**
   * The debug adapter supports the 'clipboard' context value in the 'evaluate' request.
   * <p>
   * This is an optional property.
   */
  public void setSupportsClipboardContext(final Boolean supportsClipboardContext) {
    this.supportsClipboardContext = supportsClipboardContext;
  }
  
  /**
   * The debug adapter supports stepping granularities (argument 'granularity') for the stepping requests.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsSteppingGranularity() {
    return this.supportsSteppingGranularity;
  }
  
  /**
   * The debug adapter supports stepping granularities (argument 'granularity') for the stepping requests.
   * <p>
   * This is an optional property.
   */
  public void setSupportsSteppingGranularity(final Boolean supportsSteppingGranularity) {
    this.supportsSteppingGranularity = supportsSteppingGranularity;
  }
  
  /**
   * The debug adapter supports adding breakpoints based on instruction references.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getSupportsInstructionBreakpoints() {
    return this.supportsInstructionBreakpoints;
  }
  
  /**
   * The debug adapter supports adding breakpoints based on instruction references.
   * <p>
   * This is an optional property.
   */
  public void setSupportsInstructionBreakpoints(final Boolean supportsInstructionBreakpoints) {
    this.supportsInstructionBreakpoints = supportsInstructionBreakpoints;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("supportsConfigurationDoneRequest", this.supportsConfigurationDoneRequest);
    b.add("supportsFunctionBreakpoints", this.supportsFunctionBreakpoints);
    b.add("supportsConditionalBreakpoints", this.supportsConditionalBreakpoints);
    b.add("supportsHitConditionalBreakpoints", this.supportsHitConditionalBreakpoints);
    b.add("supportsEvaluateForHovers", this.supportsEvaluateForHovers);
    b.add("exceptionBreakpointFilters", this.exceptionBreakpointFilters);
    b.add("supportsStepBack", this.supportsStepBack);
    b.add("supportsSetVariable", this.supportsSetVariable);
    b.add("supportsRestartFrame", this.supportsRestartFrame);
    b.add("supportsGotoTargetsRequest", this.supportsGotoTargetsRequest);
    b.add("supportsStepInTargetsRequest", this.supportsStepInTargetsRequest);
    b.add("supportsCompletionsRequest", this.supportsCompletionsRequest);
    b.add("completionTriggerCharacters", this.completionTriggerCharacters);
    b.add("supportsModulesRequest", this.supportsModulesRequest);
    b.add("additionalModuleColumns", this.additionalModuleColumns);
    b.add("supportedChecksumAlgorithms", this.supportedChecksumAlgorithms);
    b.add("supportsRestartRequest", this.supportsRestartRequest);
    b.add("supportsExceptionOptions", this.supportsExceptionOptions);
    b.add("supportsValueFormattingOptions", this.supportsValueFormattingOptions);
    b.add("supportsExceptionInfoRequest", this.supportsExceptionInfoRequest);
    b.add("supportTerminateDebuggee", this.supportTerminateDebuggee);
    b.add("supportsDelayedStackTraceLoading", this.supportsDelayedStackTraceLoading);
    b.add("supportsLoadedSourcesRequest", this.supportsLoadedSourcesRequest);
    b.add("supportsLogPoints", this.supportsLogPoints);
    b.add("supportsTerminateThreadsRequest", this.supportsTerminateThreadsRequest);
    b.add("supportsSetExpression", this.supportsSetExpression);
    b.add("supportsTerminateRequest", this.supportsTerminateRequest);
    b.add("supportsDataBreakpoints", this.supportsDataBreakpoints);
    b.add("supportsReadMemoryRequest", this.supportsReadMemoryRequest);
    b.add("supportsDisassembleRequest", this.supportsDisassembleRequest);
    b.add("supportsCancelRequest", this.supportsCancelRequest);
    b.add("supportsBreakpointLocationsRequest", this.supportsBreakpointLocationsRequest);
    b.add("supportsClipboardContext", this.supportsClipboardContext);
    b.add("supportsSteppingGranularity", this.supportsSteppingGranularity);
    b.add("supportsInstructionBreakpoints", this.supportsInstructionBreakpoints);
    return b.toString();
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Capabilities other = (Capabilities) obj;
    if (this.supportsConfigurationDoneRequest == null) {
      if (other.supportsConfigurationDoneRequest != null)
        return false;
    } else if (!this.supportsConfigurationDoneRequest.equals(other.supportsConfigurationDoneRequest))
      return false;
    if (this.supportsFunctionBreakpoints == null) {
      if (other.supportsFunctionBreakpoints != null)
        return false;
    } else if (!this.supportsFunctionBreakpoints.equals(other.supportsFunctionBreakpoints))
      return false;
    if (this.supportsConditionalBreakpoints == null) {
      if (other.supportsConditionalBreakpoints != null)
        return false;
    } else if (!this.supportsConditionalBreakpoints.equals(other.supportsConditionalBreakpoints))
      return false;
    if (this.supportsHitConditionalBreakpoints == null) {
      if (other.supportsHitConditionalBreakpoints != null)
        return false;
    } else if (!this.supportsHitConditionalBreakpoints.equals(other.supportsHitConditionalBreakpoints))
      return false;
    if (this.supportsEvaluateForHovers == null) {
      if (other.supportsEvaluateForHovers != null)
        return false;
    } else if (!this.supportsEvaluateForHovers.equals(other.supportsEvaluateForHovers))
      return false;
    if (this.exceptionBreakpointFilters == null) {
      if (other.exceptionBreakpointFilters != null)
        return false;
    } else if (!Arrays.deepEquals(this.exceptionBreakpointFilters, other.exceptionBreakpointFilters))
      return false;
    if (this.supportsStepBack == null) {
      if (other.supportsStepBack != null)
        return false;
    } else if (!this.supportsStepBack.equals(other.supportsStepBack))
      return false;
    if (this.supportsSetVariable == null) {
      if (other.supportsSetVariable != null)
        return false;
    } else if (!this.supportsSetVariable.equals(other.supportsSetVariable))
      return false;
    if (this.supportsRestartFrame == null) {
      if (other.supportsRestartFrame != null)
        return false;
    } else if (!this.supportsRestartFrame.equals(other.supportsRestartFrame))
      return false;
    if (this.supportsGotoTargetsRequest == null) {
      if (other.supportsGotoTargetsRequest != null)
        return false;
    } else if (!this.supportsGotoTargetsRequest.equals(other.supportsGotoTargetsRequest))
      return false;
    if (this.supportsStepInTargetsRequest == null) {
      if (other.supportsStepInTargetsRequest != null)
        return false;
    } else if (!this.supportsStepInTargetsRequest.equals(other.supportsStepInTargetsRequest))
      return false;
    if (this.supportsCompletionsRequest == null) {
      if (other.supportsCompletionsRequest != null)
        return false;
    } else if (!this.supportsCompletionsRequest.equals(other.supportsCompletionsRequest))
      return false;
    if (this.completionTriggerCharacters == null) {
      if (other.completionTriggerCharacters != null)
        return false;
    } else if (!Arrays.deepEquals(this.completionTriggerCharacters, other.completionTriggerCharacters))
      return false;
    if (this.supportsModulesRequest == null) {
      if (other.supportsModulesRequest != null)
        return false;
    } else if (!this.supportsModulesRequest.equals(other.supportsModulesRequest))
      return false;
    if (this.additionalModuleColumns == null) {
      if (other.additionalModuleColumns != null)
        return false;
    } else if (!Arrays.deepEquals(this.additionalModuleColumns, other.additionalModuleColumns))
      return false;
    if (this.supportedChecksumAlgorithms == null) {
      if (other.supportedChecksumAlgorithms != null)
        return false;
    } else if (!Arrays.deepEquals(this.supportedChecksumAlgorithms, other.supportedChecksumAlgorithms))
      return false;
    if (this.supportsRestartRequest == null) {
      if (other.supportsRestartRequest != null)
        return false;
    } else if (!this.supportsRestartRequest.equals(other.supportsRestartRequest))
      return false;
    if (this.supportsExceptionOptions == null) {
      if (other.supportsExceptionOptions != null)
        return false;
    } else if (!this.supportsExceptionOptions.equals(other.supportsExceptionOptions))
      return false;
    if (this.supportsValueFormattingOptions == null) {
      if (other.supportsValueFormattingOptions != null)
        return false;
    } else if (!this.supportsValueFormattingOptions.equals(other.supportsValueFormattingOptions))
      return false;
    if (this.supportsExceptionInfoRequest == null) {
      if (other.supportsExceptionInfoRequest != null)
        return false;
    } else if (!this.supportsExceptionInfoRequest.equals(other.supportsExceptionInfoRequest))
      return false;
    if (this.supportTerminateDebuggee == null) {
      if (other.supportTerminateDebuggee != null)
        return false;
    } else if (!this.supportTerminateDebuggee.equals(other.supportTerminateDebuggee))
      return false;
    if (this.supportsDelayedStackTraceLoading == null) {
      if (other.supportsDelayedStackTraceLoading != null)
        return false;
    } else if (!this.supportsDelayedStackTraceLoading.equals(other.supportsDelayedStackTraceLoading))
      return false;
    if (this.supportsLoadedSourcesRequest == null) {
      if (other.supportsLoadedSourcesRequest != null)
        return false;
    } else if (!this.supportsLoadedSourcesRequest.equals(other.supportsLoadedSourcesRequest))
      return false;
    if (this.supportsLogPoints == null) {
      if (other.supportsLogPoints != null)
        return false;
    } else if (!this.supportsLogPoints.equals(other.supportsLogPoints))
      return false;
    if (this.supportsTerminateThreadsRequest == null) {
      if (other.supportsTerminateThreadsRequest != null)
        return false;
    } else if (!this.supportsTerminateThreadsRequest.equals(other.supportsTerminateThreadsRequest))
      return false;
    if (this.supportsSetExpression == null) {
      if (other.supportsSetExpression != null)
        return false;
    } else if (!this.supportsSetExpression.equals(other.supportsSetExpression))
      return false;
    if (this.supportsTerminateRequest == null) {
      if (other.supportsTerminateRequest != null)
        return false;
    } else if (!this.supportsTerminateRequest.equals(other.supportsTerminateRequest))
      return false;
    if (this.supportsDataBreakpoints == null) {
      if (other.supportsDataBreakpoints != null)
        return false;
    } else if (!this.supportsDataBreakpoints.equals(other.supportsDataBreakpoints))
      return false;
    if (this.supportsReadMemoryRequest == null) {
      if (other.supportsReadMemoryRequest != null)
        return false;
    } else if (!this.supportsReadMemoryRequest.equals(other.supportsReadMemoryRequest))
      return false;
    if (this.supportsDisassembleRequest == null) {
      if (other.supportsDisassembleRequest != null)
        return false;
    } else if (!this.supportsDisassembleRequest.equals(other.supportsDisassembleRequest))
      return false;
    if (this.supportsCancelRequest == null) {
      if (other.supportsCancelRequest != null)
        return false;
    } else if (!this.supportsCancelRequest.equals(other.supportsCancelRequest))
      return false;
    if (this.supportsBreakpointLocationsRequest == null) {
      if (other.supportsBreakpointLocationsRequest != null)
        return false;
    } else if (!this.supportsBreakpointLocationsRequest.equals(other.supportsBreakpointLocationsRequest))
      return false;
    if (this.supportsClipboardContext == null) {
      if (other.supportsClipboardContext != null)
        return false;
    } else if (!this.supportsClipboardContext.equals(other.supportsClipboardContext))
      return false;
    if (this.supportsSteppingGranularity == null) {
      if (other.supportsSteppingGranularity != null)
        return false;
    } else if (!this.supportsSteppingGranularity.equals(other.supportsSteppingGranularity))
      return false;
    if (this.supportsInstructionBreakpoints == null) {
      if (other.supportsInstructionBreakpoints != null)
        return false;
    } else if (!this.supportsInstructionBreakpoints.equals(other.supportsInstructionBreakpoints))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.supportsConfigurationDoneRequest== null) ? 0 : this.supportsConfigurationDoneRequest.hashCode());
    result = prime * result + ((this.supportsFunctionBreakpoints== null) ? 0 : this.supportsFunctionBreakpoints.hashCode());
    result = prime * result + ((this.supportsConditionalBreakpoints== null) ? 0 : this.supportsConditionalBreakpoints.hashCode());
    result = prime * result + ((this.supportsHitConditionalBreakpoints== null) ? 0 : this.supportsHitConditionalBreakpoints.hashCode());
    result = prime * result + ((this.supportsEvaluateForHovers== null) ? 0 : this.supportsEvaluateForHovers.hashCode());
    result = prime * result + ((this.exceptionBreakpointFilters== null) ? 0 : Arrays.deepHashCode(this.exceptionBreakpointFilters));
    result = prime * result + ((this.supportsStepBack== null) ? 0 : this.supportsStepBack.hashCode());
    result = prime * result + ((this.supportsSetVariable== null) ? 0 : this.supportsSetVariable.hashCode());
    result = prime * result + ((this.supportsRestartFrame== null) ? 0 : this.supportsRestartFrame.hashCode());
    result = prime * result + ((this.supportsGotoTargetsRequest== null) ? 0 : this.supportsGotoTargetsRequest.hashCode());
    result = prime * result + ((this.supportsStepInTargetsRequest== null) ? 0 : this.supportsStepInTargetsRequest.hashCode());
    result = prime * result + ((this.supportsCompletionsRequest== null) ? 0 : this.supportsCompletionsRequest.hashCode());
    result = prime * result + ((this.completionTriggerCharacters== null) ? 0 : Arrays.deepHashCode(this.completionTriggerCharacters));
    result = prime * result + ((this.supportsModulesRequest== null) ? 0 : this.supportsModulesRequest.hashCode());
    result = prime * result + ((this.additionalModuleColumns== null) ? 0 : Arrays.deepHashCode(this.additionalModuleColumns));
    result = prime * result + ((this.supportedChecksumAlgorithms== null) ? 0 : Arrays.deepHashCode(this.supportedChecksumAlgorithms));
    result = prime * result + ((this.supportsRestartRequest== null) ? 0 : this.supportsRestartRequest.hashCode());
    result = prime * result + ((this.supportsExceptionOptions== null) ? 0 : this.supportsExceptionOptions.hashCode());
    result = prime * result + ((this.supportsValueFormattingOptions== null) ? 0 : this.supportsValueFormattingOptions.hashCode());
    result = prime * result + ((this.supportsExceptionInfoRequest== null) ? 0 : this.supportsExceptionInfoRequest.hashCode());
    result = prime * result + ((this.supportTerminateDebuggee== null) ? 0 : this.supportTerminateDebuggee.hashCode());
    result = prime * result + ((this.supportsDelayedStackTraceLoading== null) ? 0 : this.supportsDelayedStackTraceLoading.hashCode());
    result = prime * result + ((this.supportsLoadedSourcesRequest== null) ? 0 : this.supportsLoadedSourcesRequest.hashCode());
    result = prime * result + ((this.supportsLogPoints== null) ? 0 : this.supportsLogPoints.hashCode());
    result = prime * result + ((this.supportsTerminateThreadsRequest== null) ? 0 : this.supportsTerminateThreadsRequest.hashCode());
    result = prime * result + ((this.supportsSetExpression== null) ? 0 : this.supportsSetExpression.hashCode());
    result = prime * result + ((this.supportsTerminateRequest== null) ? 0 : this.supportsTerminateRequest.hashCode());
    result = prime * result + ((this.supportsDataBreakpoints== null) ? 0 : this.supportsDataBreakpoints.hashCode());
    result = prime * result + ((this.supportsReadMemoryRequest== null) ? 0 : this.supportsReadMemoryRequest.hashCode());
    result = prime * result + ((this.supportsDisassembleRequest== null) ? 0 : this.supportsDisassembleRequest.hashCode());
    result = prime * result + ((this.supportsCancelRequest== null) ? 0 : this.supportsCancelRequest.hashCode());
    result = prime * result + ((this.supportsBreakpointLocationsRequest== null) ? 0 : this.supportsBreakpointLocationsRequest.hashCode());
    result = prime * result + ((this.supportsClipboardContext== null) ? 0 : this.supportsClipboardContext.hashCode());
    result = prime * result + ((this.supportsSteppingGranularity== null) ? 0 : this.supportsSteppingGranularity.hashCode());
    return prime * result + ((this.supportsInstructionBreakpoints== null) ? 0 : this.supportsInstructionBreakpoints.hashCode());
  }
}
