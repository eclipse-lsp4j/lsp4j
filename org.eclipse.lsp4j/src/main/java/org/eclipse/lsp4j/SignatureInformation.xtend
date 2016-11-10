/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI
import java.util.List
import javax.annotation.Nullable

/**
 * Represents the signature of something callable. A signature can have a label, like a function-name, a doc-comment, and
 * a set of parameters.
 */
@LanguageServerAPI
interface SignatureInformation {
	
	/**
	 * The label of this signature. Will be shown in the UI.
	 */
	def String getLabel()
	
	/**
	 * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
	 */
	@Nullable
	def String getDocumentation()
	
	/**
	 * The parameters of this signature.
	 */
	@Nullable
	def List<? extends ParameterInformation> getParameters()
	
}