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
 * Signature help represents the signature of something callable. There can be multiple signature but only one
 * active and only one active parameter.
 */
@LanguageServerAPI
class SignatureHelp {
	
	/**
	 * One or more signatures.
	 */
	@NonNull
	List<SignatureInformation> signatures = new ArrayList
	
	/**
	 * The active signature.
	 */
	Integer activeSignature
	
	/**
	 * The active parameter of the active signature.
	 */
	Integer activeParameter
	
}