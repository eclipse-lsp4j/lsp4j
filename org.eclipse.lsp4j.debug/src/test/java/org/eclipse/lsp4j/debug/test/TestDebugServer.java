package org.eclipse.lsp4j.debug.test;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
import org.eclipse.lsp4j.debug.RunInTerminalRequestArguments;
import org.eclipse.lsp4j.debug.RunInTerminalResponse;
import org.eclipse.lsp4j.debug.ScopesArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetDataBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetDataBreakpointsResponse;
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
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;

public class TestDebugServer implements IDebugProtocolServer {
	@Override
	public CompletableFuture<RunInTerminalResponse> runInTerminal(RunInTerminalRequestArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Capabilities> initialize(InitializeRequestArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> configurationDone(ConfigurationDoneArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> launch(Map<String, Object> args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> attach(Map<String, Object> args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> restart(RestartArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> disconnect(DisconnectArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<SetBreakpointsResponse> setBreakpoints(SetBreakpointsArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<SetFunctionBreakpointsResponse> setFunctionBreakpoints(
			SetFunctionBreakpointsArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> setExceptionBreakpoints(SetExceptionBreakpointsArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<DataBreakpointInfoResponse> dataBreakpointInfo(DataBreakpointInfoArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<SetDataBreakpointsResponse> setDataBreakpoints(SetDataBreakpointsArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<ContinueResponse> continue_(ContinueArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> next(NextArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> stepIn(StepInArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> stepOut(StepOutArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> stepBack(StepBackArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> reverseContinue(ReverseContinueArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> restartFrame(RestartFrameArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> goto_(GotoArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> pause(PauseArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<StackTraceResponse> stackTrace(StackTraceArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<ScopesResponse> scopes(ScopesArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<VariablesResponse> variables(VariablesArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<SetVariableResponse> setVariable(SetVariableArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<SourceResponse> source(SourceArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<ThreadsResponse> threads() {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<ModulesResponse> modules(ModulesArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<LoadedSourcesResponse> loadedSources(LoadedSourcesArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<EvaluateResponse> evaluate(EvaluateArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<StepInTargetsResponse> stepInTargets(StepInTargetsArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<GotoTargetsResponse> gotoTargets(GotoTargetsArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<CompletionsResponse> completions(CompletionsArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<ExceptionInfoResponse> exceptionInfo(ExceptionInfoArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> terminate(TerminateArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<Void> terminateThreads(TerminateThreadsArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<SetExpressionResponse> setExpression(SetExpressionArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<ReadMemoryResponse> readMemory(ReadMemoryArguments args) {
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public CompletableFuture<DisassembleResponse> disassemble(DisassembleArguments args) {
		return CompletableFuture.completedFuture(null);
	}
}
