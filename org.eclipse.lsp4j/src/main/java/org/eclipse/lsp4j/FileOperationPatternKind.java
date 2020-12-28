/******************************************************************************
 * Copyright (c) 2020 TypeFox and others.
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
 * A pattern kind describing if a glob pattern matches a file a folder or
 * both.
 *
 * Since 3.16.0
 */
public final class FileOperationPatternKind {

	private FileOperationPatternKind() {
	}

	/**
	 * The pattern matches a file only.
	 */
	public static final String File = "file";

	/**
	 * The pattern matches a folder only.
	 */
	public static final String Folder = "folder";
}
