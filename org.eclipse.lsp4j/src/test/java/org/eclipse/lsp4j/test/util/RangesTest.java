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
package org.eclipse.lsp4j.test.util;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.util.Ranges;
import org.junit.Assert;
import org.junit.Test;

public class RangesTest {

	@Test(expected = NullPointerException.class)
	public void containsRange_nullBigger() {
		Ranges.containsRange(null, newRange(0, 0, 1, 1));
	}

	@Test(expected = NullPointerException.class)
	public void containsRange_nullSmaller() {
		Ranges.containsRange(newRange(0, 0, 1, 1), null);
	}

	@Test
	public void containsRange_beforeAbove() {
		Assert.assertFalse(Ranges.containsRange(newRange(2, 2, 3, 3), newRange(0, 0, 1, 1)));
	}

	@Test
	public void containsRange_beforeSameLine() {
		Assert.assertFalse(Ranges.containsRange(newRange(1, 2, 3, 3), newRange(0, 0, 1, 1)));
	}

	@Test
	public void containsRange_beforeIntersects() {
		Assert.assertFalse(Ranges.containsRange(newRange(1, 2, 3, 3), newRange(0, 0, 1, 3)));
	}

	@Test
	public void containsRange_same() {
		Assert.assertTrue(Ranges.containsRange(newRange(0, 0, 1, 3), newRange(0, 0, 1, 3)));
	}

	@Test
	public void containsRange_equals() {
		Range range = newRange(0, 0, 1, 3);
		Assert.assertTrue(Ranges.containsRange(range, range));
	}

	@Test
	public void containsRange_inside() {
		Assert.assertTrue(Ranges.containsRange(newRange(0, 0, 3, 3), newRange(1, 1, 2, 2)));
	}

	@Test
	public void containsRange_afterBelow() {
		Assert.assertFalse(Ranges.containsRange(newRange(2, 2, 3, 3), newRange(4, 4, 5, 5)));
	}

	@Test
	public void containsRange_afterSameLine() {
		Assert.assertFalse(Ranges.containsRange(newRange(2, 2, 3, 3), newRange(3, 4, 5, 5)));
	}

	@Test
	public void containsRange_afterIntersects() {
		Assert.assertFalse(Ranges.containsRange(newRange(2, 2, 3, 3), newRange(3, 1, 5, 5)));
	}

	@Test
	public void containsRange_overlaps() {
		Assert.assertFalse(Ranges.containsRange(newRange(2, 2, 3, 3), newRange(1, 1, 5, 5)));
	}

	@Test(expected = NullPointerException.class)
	public void containsPosition_nullRange() {
		Ranges.containsPosition(null, new Position(0, 0));
	}

	@Test(expected = NullPointerException.class)
	public void containsPosition_nullPosition() {
		Ranges.containsPosition(newRange(0, 0, 1, 1), null);
	}

	@Test
	public void containsPosition_beforeAbove() {
		Assert.assertFalse(Ranges.containsPosition(newRange(1, 1, 2, 2), new Position(0, 1)));
	}

	@Test
	public void containsPosition_beforeSameLine() {
		Assert.assertFalse(Ranges.containsPosition(newRange(1, 3, 2, 2), new Position(1, 2)));
	}

	@Test
	public void containsPosition_leftBorder() {
		Assert.assertTrue(Ranges.containsPosition(newRange(1, 3, 2, 2), new Position(1, 3)));
	}

	@Test
	public void containsPosition_inside() {
		Assert.assertTrue(Ranges.containsPosition(newRange(1, 3, 2, 2), new Position(1, 4)));
	}

	@Test
	public void containsPosition_rightBorder() {
		Assert.assertTrue(Ranges.containsPosition(newRange(1, 3, 2, 2), new Position(2, 2)));
	}

	@Test
	public void containsPosition_afterSameLine() {
		Assert.assertFalse(Ranges.containsPosition(newRange(1, 1, 2, 2), new Position(2, 4)));
	}

	@Test
	public void containsPosition_afterBelow() {
		Assert.assertFalse(Ranges.containsPosition(newRange(1, 3, 2, 2), new Position(3, 3)));
	}

	private static Range newRange(int startLine, int startCharacter, int endLine, int endCharacter) {
		return new Range(new Position(startLine, startCharacter), new Position(endLine, endCharacter));
	}

}
