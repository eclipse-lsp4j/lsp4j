/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI
import javax.annotation.Nullable

@LanguageServerAPI
interface ResponseError {
	
	/**
	 * A number indicating the error type that occured.
	 */
	def ResponseErrorCode getCode()
	
	/**
	 * A string providing a short decription of the error.
	 */
	def String getMessage()
	
	/**
	 * A Primitive or Structured value that contains additional information about the error. Can be omitted.
	 */
	@Nullable
	def Object getData()
	
}