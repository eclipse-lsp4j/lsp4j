/*******************************************************************************
 * Copyright (c) 2018 Microsoft Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.lsp4j;

/**
 * The kind of a code action.
 *
 * Kinds are a hierarchical list of identifiers separated by `.`, e.g.
 * `"refactor.extract.function"`.
 *
 * The set of kinds is open and client needs to announce the kinds it supports
 * to the server during initialization.
 */

public final class CodeActionKind {

	private CodeActionKind() {
	}

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
	 *
	 * Example extract actions:
	 *
	 * - Extract method - Extract function - Extract variable - Extract interface
	 * from class - ...
	 */
	public static final String RefactorExtract = "refactor.extract";

	/**
	 * Base kind for refactoring inline actions: 'refactor.inline'
	 *
	 * Example inline actions:
	 *
	 * - Inline function - Inline variable - Inline constant - ...
	 */
	public static final String RefactorInline = "refactor.inline";

	/**
	 * Base kind for refactoring rewrite actions: 'refactor.rewrite'
	 *
	 * Example rewrite actions:
	 *
	 * - Convert JavaScript function to class - Add or remove parameter -
	 * Encapsulate field - Make method static - Move method to base class - ...
	 */
	public static final String RefactorRewrite = "refactor.rewrite";

	/**
	 * Base kind for source actions: `source`
	 *
	 * Source code actions apply to the entire file.
	 */
	public static final String Source = "source";

	/**
	 * Base kind for an organize imports source action: `source.organizeImports`
	 */
	public static final String SourceOrganizeImports = "source.organizeImports";
}

