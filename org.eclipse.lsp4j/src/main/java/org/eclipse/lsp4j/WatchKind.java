/******************************************************************************
 * Copyright (c) 2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j;

public final class WatchKind {
	private WatchKind() {}
	
	/**
	 * Interested in create events.
	 */
	public static final int Create = 1;
	
	/**
	 * Interested in change events
	 */
	public static final int Change = 2;
	
	/**
	 * Interested in delete events
	 */
	public static final int Delete = 4;
	
}
