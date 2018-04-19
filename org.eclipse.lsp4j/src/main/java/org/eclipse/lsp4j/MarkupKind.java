/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

/**
 * Describes the content type that a client supports in various
 * result literals like `Hover`, `ParameterInfo` or `CompletionItem`.
 *
 * Please note that `MarkupKind`s must not start with a `$`. These kinds
 * are reserved for internal usage.
 */
public final class MarkupKind {
	private MarkupKind() {}

	/**
	 * Plain text is supported as a content format.
	 */
	public static final String PLAINTEXT = "plaintext";
	
	/**
	 * Markdown is supported as a content format.
	 */
	public static final String MARKDOWN = "markdown";
}
