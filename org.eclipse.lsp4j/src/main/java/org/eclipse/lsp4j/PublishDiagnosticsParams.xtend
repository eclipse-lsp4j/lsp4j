/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import java.util.ArrayList
import java.util.List
import org.eclipse.lsp4j.annotations.LanguageServerAPI
import org.eclipse.lsp4j.jsonrpc.validation.NonNull

/**
 * Diagnostics notification are sent from the server to the client to signal results of validation runs.
 */
@LanguageServerAPI
class PublishDiagnosticsParams {
	
	/**
	 * The URI for which diagnostic information is reported.
	 */
	@NonNull
	String uri
	
	/**
	 * An array of diagnostic information items.
	 */
	@NonNull
	List<Diagnostic> diagnostics = new ArrayList
	
}