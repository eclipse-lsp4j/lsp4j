/******************************************************************************
 * Copyright (c) 2019, 2024 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.debug.util;

/**
 * Utilities for checking method and constructor arguments.
 */
public final class Preconditions {
	
	private Preconditions() {}

	/**
	 * @deprecated Use {@link org.eclipse.lsp4j.jsonrpc.util.Preconditions#enableNullChecks(boolean)} directly.
	 */
	public static void enableNullChecks(boolean enable) {
		org.eclipse.lsp4j.jsonrpc.util.Preconditions.enableNullChecks(enable);
	}
}
