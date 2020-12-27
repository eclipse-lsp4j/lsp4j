/**
 * Copyright (c) 2020 TypeFox and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

package org.eclipse.lsp4j;

/**
 * The moniker kind.
 * 
 * Since 3.16.0
 */
public final class MonikerKind {
	private MonikerKind() {
	}

	/**
	 * The moniker represents a symbol that is imported into a project
	 */
	public static final String Import = "import";

	/**
	 * The moniker represents a symbol that is exported from a project
	 */
	public static final String Export = "export";

	/**
	 * The moniker represents a symbol that is local to a project (e.g. a local
	 * variable of a function, a class not visible outside the project, ...)
	 */
	public static final String Local = "local";
}
