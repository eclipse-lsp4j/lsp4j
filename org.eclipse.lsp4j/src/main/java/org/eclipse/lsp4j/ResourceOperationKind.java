/******************************************************************************
 * Copyright (c) 2018 Microsoft Corporation and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/

package org.eclipse.lsp4j;

/**
 * The kind of resource operations supported by the client.
 */
public final class ResourceOperationKind {

	private ResourceOperationKind() {
	}

	/**
	 * Supports creating new files and folders.
	 */
	public static final String Create = "create";

	/**
	 * Supports renaming existing files and folders.
	 */
	public static final String Rename = "rename";

	/**
	 * Supports deleting existing files and folders.
	 */
	public static final String Delete = "delete";
}
