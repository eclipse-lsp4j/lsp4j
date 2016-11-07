package org.eclipse.lsp4j.services;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

public interface LanguageClient {
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
	CompletableFuture<Void> showMessageRequest(ShowMessageRequestParams requestParams);

	/**
	 * The log message notification is send from the server to the client to ask
	 * the client to log a particular message.
	 */
	@JsonNotification("window/logMessage")
	void logMessage(MessageParams message);
}
