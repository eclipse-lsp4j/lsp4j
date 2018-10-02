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
import org.eclipse.lsp4j.util.Positions;
import org.junit.Assert;
import org.junit.Test;

public class PositionsTest {

	@Test(expected = NullPointerException.class)
	public void isBefore_nullLeft() {
		Positions.isBefore(null, new Position(0, 0));
	}

	@Test(expected = NullPointerException.class)
	public void isBefore_nullRight() {
		Positions.isBefore(new Position(0, 0), null);
	}

	@Test
	public void isBefore_equals() {
		Position position = new Position(3, 3);
		Assert.assertFalse(Positions.isBefore(position, position));
	}

	@Test
	public void isBefore_same() {
		Assert.assertFalse(Positions.isBefore(new Position(3, 3), new Position(3, 3)));
	}

	@Test
	public void isBefore_beforeSameLine() {
		Assert.assertTrue(Positions.isBefore(new Position(3, 3), new Position(3, 4)));
	}

	@Test
	public void isBefore_before() {
		Assert.assertTrue(Positions.isBefore(new Position(3, 3), new Position(4, 3)));
	}

	@Test
	public void isBefore_afterSameLine() {
		Assert.assertFalse(Positions.isBefore(new Position(3, 3), new Position(3, 2)));
	}

	@Test
	public void isBefore_after() {
		Assert.assertFalse(Positions.isBefore(new Position(3, 3), new Position(2, 3)));
	}

}
