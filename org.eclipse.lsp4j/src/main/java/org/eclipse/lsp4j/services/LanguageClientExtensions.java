/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
