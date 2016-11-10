/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j;

/**
 * Defines how text documents are synced.
 */
public enum TextDocumentSyncKind {
	
	/**
	 * Documents should not be synced at all.
	 */
	None,
	
	/**
	 * Documents are synced by always sending the full content of the document.
	 */
	Full,
	
	/**
	 * Documents are synced by sending the full content on open. After that only incremental
     * updates to the document are send.
	 */
	Incremental;
	
	public static TextDocumentSyncKind forValue(int value) {
		TextDocumentSyncKind[] allValues = TextDocumentSyncKind.values();
		if (value < 0 || value >= allValues.length)
			throw new IllegalArgumentException("Illegal enum value: " + value);
		return allValues[value];
	}
	
}
