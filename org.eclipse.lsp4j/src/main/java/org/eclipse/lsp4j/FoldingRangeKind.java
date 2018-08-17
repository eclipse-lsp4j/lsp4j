/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

/**
 * Enum of known range kinds
 */
public final class FoldingRangeKind {
	private FoldingRangeKind() {}
	
	/**
	 * Folding range for a comment
	 */
	public static final String Comment = "comment";
	
	/**
	 * Folding range for a imports or includes
	 */
	public static final String Imports = "imports";
	
	/**
	 * Folding range for a region (e.g. `#region`)
	 */
	public static final String Region = "region";

}
