/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

/**
 * Represents a diagnostic, such as a compiler error or warning. Diagnostic objects are only valid in the scope of a resource.
 */
@LanguageServerAPI
class Diagnostic {
	
	/**
	 * The range at which the message applies
	 */
	@NonNull
	Range range
	
	/**
	 * The diagnostic's severity. Can be omitted. If omitted it is up to the client to interpret diagnostics as error,
	 * warning, info or hint.
	 */
	DiagnosticSeverity severity
	
	/**
	 * The diagnostic's code. Can be omitted.
	 */
	String code
	
	/**
	 * A human-readable string describing the source of this diagnostic, e.g. 'typescript' or 'super lint'.
	 */
	String source
	
	/**
	 * The diagnostic's message.
	 */
	@NonNull
	String message
	
}