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

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.InitializedParams;
import org.eclipse.lsp4j.SetTraceParams;
import org.eclipse.lsp4j.WorkDoneProgressCancelParams;
import org.eclipse.lsp4j.jsonrpc.services.JsonDelegate;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

/**
 * Interface for implementations of
 * https://github.com/Microsoft/vscode-languageserver-protocol
 */
public interface LanguageServer {
	/**
	 * The initialize request is sent as the first request from the client to
	 * the server.
	 * 
	 * If the server receives request or notification before the initialize request it should act as follows:
	 * 	- for a request the respond should be errored with code: -32001. The message can be picked by the server.
	 *  - notifications should be dropped, except for the exit notification. This will allow the exit a server without an initialize request.
	 *  
	 * Until the server has responded to the initialize request with an InitializeResult 
	 * the client must not sent any additional requests or notifications to the server.
	 * 
	 * During the initialize request the server is allowed to sent the notifications window/showMessage, 
	 * window/logMessage and telemetry/event as well as the window/showMessageRequest request to the client.
	 */
	@JsonRequest
	CompletableFuture<InitializeResult> initialize(InitializeParams params);

	/**
	 * The initialized notification is sent from the client to the server after
	 * the client received the result of the initialize request but before the
	 * client is sending any other request or notification to the server. The
	 * server can use the initialized notification for example to dynamically
	 * register capabilities.
	 */
	@JsonNotification
	default void initialized(InitializedParams params) {
		initialized();
	}
	/**
	 * @deprecated see initialized(InitializedParams)
	 */
	@Deprecated
	default void initialized() {
	}

	/**
	 * The shutdown request is sent from the client to the server. It asks the
	 * server to shutdown, but to not exit (otherwise the response might not be
	 * delivered correctly to the client). There is a separate exit notification
	 * that asks the server to exit.
	 */
	@JsonRequest
	CompletableFuture<Object> shutdown();

	/**
	 * A notification to ask the server to exit its process.
	 */
	@JsonNotification
	void exit();

	/**
	 * Provides access to the textDocument services.
	 */
	@JsonDelegate
	TextDocumentService getTextDocumentService();

	/**
	 * Provides access to the workspace services.
	 */
	@JsonDelegate
	WorkspaceService getWorkspaceService();
	
	/**
	 * This notification is sent from the client to the server to cancel a progress initiated on the server side.
	 */
	@JsonNotification("window/workDoneProgress/cancel")
	default void cancelProgress(WorkDoneProgressCancelParams params) {
		throw new UnsupportedOperationException();
	}

	/**
	 * A notification that should be used by the client to modify the trace setting of the server.
	 *
	 * Since 3.16.0
	 */
	@JsonNotification("$/setTrace")
	default void setTrace(SetTraceParams params) {
		throw new UnsupportedOperationException();
	}
}
