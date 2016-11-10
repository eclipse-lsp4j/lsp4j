/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j

import org.eclipse.lsp4j.annotations.LanguageServerAPI

/**
 * To cancel a request a notification message with the following properties is sent:
 */
@LanguageServerAPI
interface CancelParams {
	
	/**
	 * The request id to cancel.
	 */
	def String getId()
	
}
