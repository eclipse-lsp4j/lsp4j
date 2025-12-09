/******************************************************************************
 * Copyright (c) 2025 Avaloq Group AG.
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

import org.eclipse.lsp4j.jsonrpc.ProtocolDraft;
import org.eclipse.lsp4j.jsonrpc.ProtocolSince;

/**
 * Describes how an inline completion request was triggered.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
public enum InlineCompletionTriggerKind {
	/**
	 * Completion was triggered explicitly by a user gesture. Return multiple
	 * completion items to enable cycling through them.
	 */
	Invoked(1),

	/**
	 * Completion was triggered automatically while editing. It is sufficient to
	 * return a single completion item in this case.
	 */
	Automatic(2);

	private final int value;

	InlineCompletionTriggerKind(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static InlineCompletionTriggerKind forValue(final int value) {
		InlineCompletionTriggerKind[] allValues = InlineCompletionTriggerKind.values();
		if (value < 1 || value > allValues.length) {
			throw new IllegalArgumentException("Illegal enum value: " + value);
		}
		return allValues[value - 1];
	}
}
