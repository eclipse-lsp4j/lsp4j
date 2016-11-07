/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json;

import java.util.Map;

import org.eclipse.lsp4j.jsonrpc.Endpoint;

/**
 * Provides {@link JsonRpcMethod}. Can be implemented by {@link Endpoint}s to
 * provide information about the supported methods.
 */
public interface JsonRpcMethodProvider {

	Map<String, JsonRpcMethod> supportedMethods();
	
}
