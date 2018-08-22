/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
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

import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;

import com.google.common.annotations.Beta;

/**
 * An extension interface for new methods that are not (yet) defined in the standard LSP protocol.
 */
public interface LanguageClientExtensions extends LanguageClient {

	/**
	 * Pushes the {@link org.eclipse.lsp4j.ColoringParams coloring parameter} to the client.
	 * 
	 * @param params
	 *            the information that should be pushed to the client side for
	 *            coloring purposes. Must not be {@code null}.
	 *
	 * @deprecated Use {@link LanguageClient#semanticHighlighting} instead.
	 */
	@Beta
	@Deprecated
	@JsonNotification("textDocument/updateColoring")
	void updateColoring(org.eclipse.lsp4j.ColoringParams params);
}
