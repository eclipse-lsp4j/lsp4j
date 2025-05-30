/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc;

import java.util.concurrent.CompletableFuture;

/**
 * An endpoint is a generic interface that accepts jsonrpc requests and notifications.
 */
public interface Endpoint {

	/**
	 * Accepts the given request for further processing.
	 *
	 * @param method a request method, may not be <code>null</code>
	 * @param parameter a request parameter
	 * @return an instance of CompletableFuture representing the result computed in response to the request.
	 *  May not be <code>null</code>.
	 */
	CompletableFuture<?> request(String method, Object parameter);
	
	/**
	 * Accepts the given notification for further processing.
	 *
	 * @param method a notification method, may not be <code>null</code>
	 * @param parameter a notification parameter
	 */
	void notify(String method, Object parameter);
	
}
