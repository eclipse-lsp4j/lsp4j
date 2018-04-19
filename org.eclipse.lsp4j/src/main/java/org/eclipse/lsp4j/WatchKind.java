/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
