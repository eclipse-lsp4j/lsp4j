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
 * A notification message. A processed notification message must not send a response back. They work like events.
 */
@LanguageServerAPI
interface NotificationMessage extends Message {
	
	/**
	 * The method to be invoked.
	 */
	def String getMethod()
	
	/**
	 * The notification's params.
	 */
	@Nullable
	def Object getParams()
	
}