/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json;

public interface MethodProvider {
	
	/**
	 * returns the method name for a given request id, or null if such request id is known.
	 * 
	 * @param requestId
	 * @return method name or <code>null</code>
	 */
	String resolveMethod(String requestId);
}
