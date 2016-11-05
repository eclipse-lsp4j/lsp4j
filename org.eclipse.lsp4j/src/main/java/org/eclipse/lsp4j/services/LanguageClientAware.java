package org.eclipse.lsp4j.services;

public interface LanguageClientAware {

	public abstract void connect(LanguageClient client);
}
