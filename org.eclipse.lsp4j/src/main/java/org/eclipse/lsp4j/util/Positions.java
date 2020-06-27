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
package org.eclipse.lsp4j.util;

import org.eclipse.lsp4j.Position;

/**
 * Utilities for the {@link Position}.
 */
public final class Positions {

	/**
	 * {@code true} if {@code left} is strictly before than {@code right}. Otherwise,
	 * {@code false}.
	 * <p>
	 * If you want to allow equality, use {@link Position#equals}.
	 */
	public static boolean isBefore(Position left, Position right) {
		Preconditions.checkNotNull(left, "left");
		Preconditions.checkNotNull(right, "right");
		if (left.getLine() < right.getLine()) {
			return true;
		}
		if (left.getLine() > right.getLine()) {
			return false;
		}
		return left.getCharacter() < right.getCharacter();
	}

	private Positions() {

	}

}
