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
 * A request message to decribe a request between the client and the server. Every processed request must send a response back
 * to the sender of the request.
 */
@LanguageServerAPI
interface RequestMessage extends Message {
	
	/**
	 * The request id.
	 */
	def String getId()
	
	/**
	 * The method to be invoked.
	 */
	def String getMethod()
	
	/**
	 * The method's params.
	 */
	@Nullable
	def Object getParams()
	
}