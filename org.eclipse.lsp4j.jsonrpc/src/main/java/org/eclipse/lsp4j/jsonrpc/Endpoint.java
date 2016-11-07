/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc;

import java.util.concurrent.CompletableFuture;

/**
 * An endpoint is a generic interface to accepts jsonrpc requests and notifications
 */
public interface Endpoint {

	public CompletableFuture<?> request(String method, Object parameter);
	
	public void notify(String method, Object parameter);
	
}
