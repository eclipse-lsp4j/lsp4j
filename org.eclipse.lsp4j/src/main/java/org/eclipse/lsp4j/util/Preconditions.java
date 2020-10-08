/******************************************************************************
 * Copyright (c) 2019 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.util;

/**
 * Utilities for checking method and constructor arguments.
 */
public final class Preconditions {
	
	private Preconditions() {}
	
	private static boolean nullChecks = true;
	
	public static void enableNullChecks(boolean enable) {
		Preconditions.nullChecks = enable;
	}
	
	public static <T> T checkNotNull(T object, String propertyName) {
		if (nullChecks && object == null) {
			throw new NullPointerException("Property must not be null: " + propertyName);
		}
		return object;
	}

}
