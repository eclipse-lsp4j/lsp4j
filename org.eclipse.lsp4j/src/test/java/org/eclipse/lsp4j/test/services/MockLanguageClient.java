package org.eclipse.lsp4j.test.services;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.services.LanguageClient;

public class MockLanguageClient implements LanguageClient {

	@Override
	public void telemetryEvent(Object object) {
	}

	@Override
	public void publishDiagnostics(PublishDiagnosticsParams diagnostics) {
	}

	@Override
	public void showMessage(MessageParams messageParams) {
	}

	@Override
	public CompletableFuture<MessageActionItem> showMessageRequest(ShowMessageRequestParams requestParams) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void logMessage(MessageParams message) {
	}

}
