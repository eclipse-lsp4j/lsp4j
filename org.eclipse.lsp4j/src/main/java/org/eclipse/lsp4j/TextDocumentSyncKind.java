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
