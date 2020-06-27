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
import org.eclipse.lsp4j.Range;

/**
 * Utility class for {@link Range}.
 */
public final class Ranges {

	/**
	 * {@code true} if the {@code smaller} {@link Range range} is inside or equal to
	 * the {@code bigger} range. Otherwise, {@code false}.
	 */
	public static boolean containsRange(Range bigger, Range smaller) {
		Preconditions.checkNotNull(bigger, "bigger");
		Preconditions.checkNotNull(smaller, "smaller");
		return containsPosition(bigger, smaller.getStart()) && containsPosition(bigger, smaller.getEnd());
	}

	/**
	 * {@code true} if the {@link Position position} is either inside or on the
	 * border of the {@link Range range}. Otherwise, {@code false}.
	 */
	public static boolean containsPosition(Range range, Position position) {
		Preconditions.checkNotNull(range, "range");
		Preconditions.checkNotNull(position, "position");
		return (range.getStart().equals(position) || Positions.isBefore(range.getStart(), position)
				&& (range.getEnd().equals(position) || Positions.isBefore(position, range.getEnd())));
	}

	private Ranges() {

	}

}
