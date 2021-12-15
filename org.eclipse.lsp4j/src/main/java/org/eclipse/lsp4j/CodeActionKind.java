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
 * The kind of a code action.
 * <p>
 * Kinds are a hierarchical list of identifiers separated by {@code .},
 * e.g. {@code "refactor.extract.function"}.
 * <p>
 * The set of kinds is open and client needs to announce the kinds it supports
 * to the server during initialization.
 */
public final class CodeActionKind {

	private CodeActionKind() {
	}

	/**
	 * Empty kind.
	 */
	public static final String Empty = "";

	/**
	 * Base kind for quickfix actions: 'quickfix'
	 */
	public static final String QuickFix = "quickfix";

	/**
	 * Base kind for refactoring actions: 'refactor'
	 */
	public static final String Refactor = "refactor";

	/**
	 * Base kind for refactoring extraction actions: 'refactor.extract'
	 * <p>
	 * Example extract actions:
	 * <p><ul>
	 * <li>Extract method
	 * <li>Extract function
	 * <li>Extract variable
	 * <li>Extract interface from class
	 * <li>...
	 * </ul>
	 */
	public static final String RefactorExtract = "refactor.extract";

	/**
	 * Base kind for refactoring inline actions: 'refactor.inline'
	 * <p>
	 * Example inline actions:
	 * <p><ul>
	 * <li>Inline function
	 * <li>Inline variable
	 * <li>Inline constant
	 * <li>...
	 * </ul>
	 */
	public static final String RefactorInline = "refactor.inline";

	/**
	 * Base kind for refactoring rewrite actions: 'refactor.rewrite'
	 * <p>
	 * Example rewrite actions:
	 * <p><ul>
	 * <li>Convert function to class 
	 * <li>Add or remove parameter 
	 * <li>Encapsulate field 
	 * <li>Make method static 
	 * <li>Move method to base class 
	 * <li>...
	 * </ul>
	 */
	public static final String RefactorRewrite = "refactor.rewrite";

	/**
	 * Base kind for source actions: 'source'
	 * <p>
	 * Source code actions apply to the entire file.
	 */
	public static final String Source = "source";

	/**
	 * Base kind for an organize imports source action: 'source.organizeImports'
	 */
	public static final String SourceOrganizeImports = "source.organizeImports";

	/**
	 * Base kind for a 'fix all' source action: 'source.fixAll'.
	 * <p>
	 * 'Fix all' actions automatically fix errors that have a clear fix that
	 * do not require user input. They should not suppress errors or perform
	 * unsafe fixes such as generating new types or classes.
	 */
	public static final String SourceFixAll = "source.fixAll";
}
