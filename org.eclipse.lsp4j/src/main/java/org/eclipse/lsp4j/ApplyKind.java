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

import org.eclipse.lsp4j.jsonrpc.ProtocolDraft;
import org.eclipse.lsp4j.jsonrpc.ProtocolSince;

/**
 * Defines how values from a set of defaults and an individual item will be merged.
 */
@ProtocolDraft
@ProtocolSince("3.18.0")
public enum ApplyKind {
	/**
	 * The value from the individual item (if provided and not {@code null}) will be
	 * used instead of the default.
	 */
	Replace(1),

	/**
	 * The value from the item will be merged with the default.
	 * <p>
	 * The specific rules for merging values are defined against each field
	 * that supports merging.
	 */
	Merge(2);

	private final int value;

	ApplyKind(final int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ApplyKind forValue(final int value) {
		ApplyKind[] allValues = ApplyKind.values();
		if (value < 1 || value > allValues.length) {
			throw new IllegalArgumentException("Illegal enum value: " + value);
		}
		return allValues[value - 1];
	}
}
