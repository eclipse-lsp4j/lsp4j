/******************************************************************************
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.ApplyWorkspaceEditResponse;
import org.eclipse.lsp4j.ConfigurationParams;
import org.eclipse.lsp4j.LogTraceParams;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.ProgressParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.RegistrationParams;
import org.eclipse.lsp4j.ShowDocumentParams;
import org.eclipse.lsp4j.ShowDocumentResult;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.UnregistrationParams;
import org.eclipse.lsp4j.WorkDoneProgressCreateParams;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

public interface LanguageClient {
	/**
	 * The workspace/applyEdit request is sent from the server to the client to modify resource on the client side.
	 */
	@JsonRequest("workspace/applyEdit")
	default CompletableFuture<ApplyWorkspaceEditResponse> applyEdit(ApplyWorkspaceEditParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The client/registerCapability request is sent from the server to the client
	 * to register for a new capability on the client side.
	 * Not all clients need to support dynamic capability registration.
	 * A client opts in via the ClientCapabilities.dynamicRegistration property
	 */
	@JsonRequest("client/registerCapability")
	default CompletableFuture<Void> registerCapability(RegistrationParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The client/unregisterCapability request is sent from the server to the client
	 * to unregister a previously register capability.
	 */
	@JsonRequest("client/unregisterCapability")
	default CompletableFuture<Void> unregisterCapability(UnregistrationParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The telemetry notification is sent from the server to the client to ask
	 * the client to log a telemetry event.
	 */
	@JsonNotification("telemetry/event")
	void telemetryEvent(Object object);

	/**
	 * Diagnostics notifications are sent from the server to the client to
	 * signal results of validation runs.
	 */
	@JsonNotification("textDocument/publishDiagnostics")
	void publishDiagnostics(PublishDiagnosticsParams diagnostics);

	/**
	 * The show message notification is sent from a server to a client to ask
	 * the client to display a particular message in the user interface.
	 */
	@JsonNotification("window/showMessage")
	void showMessage(MessageParams messageParams);

	/**
	 * The show message request is sent from a server to a client to ask the
	 * client to display a particular message in the user interface. In addition
	 * to the show message notification the request allows to pass actions and
	 * to wait for an answer from the client.
	 */
	@JsonRequest("window/showMessageRequest")
	CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams);

	/**
	 * The show document request is sent from a server to a client to ask the
	 * client to display a particular document in the user interface.
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest("window/showDocument")
	default CompletableFuture<ShowDocumentResult> showDocument(ShowDocumentParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The log message notification is send from the server to the client to ask
	 * the client to log a particular message.
	 */
	@JsonNotification("window/logMessage")
	void logMessage(MessageParams message);

	/**
	 * The workspace/workspaceFolders request is sent from the server to the client
	 * to fetch the current open list of workspace folders.
	 *
	 * @return null in the response if only a single file is open in the tool,
	 *         an empty array if a workspace is open but no folders are configured,
	 *         the workspace folders otherwise.
	 * 
	 * Since 3.6.0
	 */
	@JsonRequest("workspace/workspaceFolders")
	default CompletableFuture<List<WorkspaceFolder>> workspaceFolders() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The workspace/configuration request is sent from the server to the client to fetch
	 * configuration settings from the client. The request can fetch several configuration settings
	 * in one roundtrip. The order of the returned configuration settings correspond to the
	 * order of the passed ConfigurationItems (e.g. the first item in the response is the
	 * result for the first configuration item in the params).
	 * 
	 * Since 3.6.0
	 */
	@JsonRequest("workspace/configuration")
	default CompletableFuture<List<Object>> configuration(ConfigurationParams configurationParams) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This request is sent from the server to the client to ask the client to create a work done progress.
	 * 
	 * Since 3.15.0
	 */
	@JsonRequest("window/workDoneProgress/create")
	default CompletableFuture<Void> createProgress(WorkDoneProgressCreateParams params) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The base protocol offers also support to report progress in a generic fashion.
	 * This mechanism can be used to report any kind of progress including work done progress
	 * (usually used to report progress in the user interface using a progress bar)
	 * and partial result progress to support streaming of results.
	 * 
	 * Since 3.15.0
	 */
	@JsonNotification("$/progress")
	default void notifyProgress(ProgressParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * A notification to log the trace of the server's execution. The amount and content of these
	 * notifications depends on the current trace configuration. If trace is 'off', the server
	 * should not send any logTrace notification. If trace is 'message', the server should not
	 * add the 'verbose' field in the LogTraceParams.
	 *
	 * $/logTrace should be used for systematic trace reporting. For single debugging messages,
	 * the server should send window/logMessage notifications.
	 * 
	 * Since 3.16.0
	 */
	@JsonNotification("$/logTrace")
	default void logTrace(LogTraceParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * The `workspace/semanticTokens/refresh` request is sent from the server to the client.
	 * Servers can use it to ask clients to refresh the editors for which this server
	 * provides semantic tokens. As a result the client should ask the server to recompute
	 * the semantic tokens for these editors. This is useful if a server detects a project wide
	 * configuration change which requires a re-calculation of all semantic tokens.
	 * Note that the client still has the freedom to delay the re-calculation of the semantic tokens
	 * if for example an editor is currently not visible.
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest("workspace/semanticTokens/refresh")
	default CompletableFuture<Void> refreshSemanticTokens() {
		throw new UnsupportedOperationException();
	}

	/**
	 * The {@code workspace/codeLens/refresh} request is sent from the server to the client.
	 * Servers can use it to ask clients to refresh the code lenses currently shown in editors.
	 * As a result the client should ask the server to recompute the code lenses for these editors.
	 * This is useful if a server detects a configuration change which requires a re-calculation of
	 * all code lenses. Note that the client still has the freedom to delay the re-calculation of
	 * the code lenses if for example an editor is currently not visible.
	 * 
	 * Since 3.16.0
	 */
	@JsonRequest("workspace/codeLens/refresh")
	default CompletableFuture<Void> refreshCodeLenses() {
		throw new UnsupportedOperationException();
	}
}
