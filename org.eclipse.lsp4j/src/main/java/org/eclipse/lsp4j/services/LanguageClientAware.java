package org.eclipse.lsp4j.services;

public interface LanguageClientAware {

	void connect(LanguageClient client);
}
