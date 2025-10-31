/******************************************************************************
 * Copyright (c) 2025 1C-Soft LLC and others.
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

import org.eclipse.lsp4j.jsonrpc.Draft;

/**
 * Code action tags are extra annotations that tweak the behavior of a code action.
 * <p>
 * Since 3.18.0
 */
@Draft
public enum CodeActionTag {
	/**
	 * Marks the code action as LLM-generated.
	 */
	LLMGenerated(1);

	private final int value;

	CodeActionTag(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static CodeActionTag forValue(int value) {
		CodeActionTag[] allValues = CodeActionTag.values();
		if (value < 1 || value > allValues.length) {
			throw new IllegalArgumentException("Illegal enum value: " + value);
		}
		return allValues[value - 1];
	}
}
