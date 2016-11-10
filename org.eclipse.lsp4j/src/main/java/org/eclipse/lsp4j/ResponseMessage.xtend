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

/**
 * Response Message send as a result of a request.
 */
@LanguageServerAPI
interface ResponseMessage extends Message {
	
	/**
	 * The request id.
	 */
	def String getId()
	
	/**
	 * The result of a request. This can be omitted in the case of an error.
	 */
	@Nullable
	def Object getResult()
	
	/**
	 * The error object in case a request fails.
	 */
	@Nullable
	def ResponseError getError()
	
}