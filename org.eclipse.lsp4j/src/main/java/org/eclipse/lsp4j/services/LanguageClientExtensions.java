package org.eclipse.lsp4j.services;

import org.eclipse.lsp4j.ColoringParams;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;

/**
 * An extension interface for new methods that are not (yet) defined in the standard LSP protocol.
 */
public interface LanguageClientExtensions extends LanguageClient {

	/**
	 * Pushes the {@link ColoringParams coloring parameter} to the client.
	 * 
	 * @param params
	 *            the information that should be pushed to the client side for
	 *            coloring purposes. Must not be {@code null}.
	 */
	@JsonNotification("textDocument/updateColoring")
	void updateColoring(ColoringParams params);
}
