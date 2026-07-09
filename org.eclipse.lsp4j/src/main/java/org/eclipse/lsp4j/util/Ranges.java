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
import org.eclipse.lsp4j.jsonrpc.util.Preconditions;

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

    Position smallerStart = smaller.getStart();
    Position smallerEnd = smaller.getEnd();

    return containsPosition(bigger, smallerStart) && containsPosition(bigger, smallerEnd);
}

	/**
	 * {@code true} if the {@link Position position} is either inside or on the
	 * border of the {@link Range range}. Otherwise, {@code false}.
	 */
	public static boolean containsPosition(Range range, Position position) {
    Preconditions.checkNotNull(range, "range");
    Preconditions.checkNotNull(position, "position");

    Position start = range.getStart();
    Position end = range.getEnd();

    boolean startsAtPosition = start.equals(position);
    boolean isAfterStart = Positions.isBefore(start, position);
    boolean endsAtOrAfterPosition = end.equals(position) || Positions.isBefore(position, end);

    return startsAtPosition || (isAfterStart && endsAtOrAfterPosition);
}

	private Ranges() {

	}

}
