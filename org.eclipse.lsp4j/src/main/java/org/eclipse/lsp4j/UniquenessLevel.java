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
 * Moniker uniqueness level to define scope of the moniker.
 * 
 * Since 3.16.0
 */
public final class UniquenessLevel {
	private UniquenessLevel() {
	}

	/**
	 * The moniker is only unique inside a document
	 */
	public static final String Document = "document";

	/**
	 * The moniker is unique inside a project for which a dump got created
	 */
	public static final String Project = "project";

	/**
	 * The moniker is unique inside the group to which a project belongs
	 */
	public static final String Group = "group";

	/**
	 * The moniker is unique inside the moniker scheme.
	 */
	public static final String Scheme = "scheme";

	/**
	 * The moniker is globally unique
	 */
	public static final String Global = "global";
}
