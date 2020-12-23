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

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.eclipse.lsp4j.util.SemanticHighlightingTokens.decode;
import static org.eclipse.lsp4j.util.SemanticHighlightingTokens.encode;
import static org.eclipse.lsp4j.util.SemanticHighlightingTokens.Token.fromIntArray;
import static org.eclipse.lsp4j.util.SemanticHighlightingTokens.Token.toIntArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.lsp4j.util.SemanticHighlightingTokens.Token;
import org.junit.Test;

public class SemanticHighlightingTokensTest {

	private static int[] EMPTY_ARRAY = new int[0];

	@Test(expected = IllegalArgumentException.class)
	public void newToken_invalidCharacter() {
		new Token(-1, 0, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void newToken_invalidLengthSmaller() {
		new Token(0, -1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void newToken_invalidLengthGreater() {
		new Token(0, (int) Math.pow(2, 16), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void newToken_invalidScopeSmaller() {
		new Token(0, 0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void newToken_invalidScopeGreater() {
		new Token(0, 0, (int) Math.pow(2, 16));
	}

	@Test
	public void toIntArray_null() {
		assertArrayEquals(EMPTY_ARRAY, toIntArray(null));
	}

	@Test
	public void toIntArray_empty() {
		assertArrayEquals(EMPTY_ARRAY, toIntArray(emptyList()));
	}

	@Test(expected = NullPointerException.class)
	public void toIntArray_invalid() {
		toIntArray(newArrayList((Token) null));
	}

	@Test
	public void toIntArray_() {
		assertArrayEquals(new int[] { 2, 5, 0, 12, 15, 1, 7, 1000, 1 },
				toIntArray(newArrayList(new Token(2, 5, 0), new Token(12, 15, 1), new Token(7, 1000, 1))));
	}

	@Test
	public void fromIntArray_null() {
		assertEquals(emptyList(), fromIntArray(null));
	}

	@Test
	public void fromIntArray_empty() {
		assertEquals(emptyList(), fromIntArray(EMPTY_ARRAY));
	}

	@Test(expected = IllegalArgumentException.class)
	public void fromIntArray_invalid() {
		assertEquals(emptyList(), fromIntArray(new int[] { 1, 2 }));
	}

	@Test
	public void fromIntArray_() {
		assertEquals(newArrayList(new Token(2, 5, 0), new Token(12, 15, 1), new Token(7, 1000, 1)),
				fromIntArray(new int[] { 2, 5, 0, 12, 15, 1, 7, 1000, 1 }));
	}

	@Test
	public void encode_null() {
		assertEquals("", encode(null));
	}

	@Test
	public void encode_empty() {
		assertEquals("", encode(emptyList()));
	}

	@Test(expected = NullPointerException.class)
	public void encode_invalid() {
		encode(newArrayList((Token) null));
	}

	@Test
	public void encode_() {
		assertEquals("AAAAAgAFAAAAAAAMAA8AAQAAAAcD6AAB",
				encode(newArrayList(new Token(2, 5, 0), new Token(12, 15, 1), new Token(7, 1000, 1))));
	}

	@Test
	public void decode_null() {
		assertEquals(emptyList(), decode(null));
	}

	@Test
	public void decode_empty() {
		assertEquals(emptyList(), decode(""));
	}

	@Test
	public void decode_() {
		assertEquals(newArrayList(new Token(2, 5, 0), new Token(12, 15, 1), new Token(7, 1000, 1)),
				decode("AAAAAgAFAAAAAAAMAA8AAQAAAAcD6AAB"));
	}

	@Test
	public void symmetric() {
		List<Token> expected = newArrayList();
		for (int i = 0; i < 65536; i++) {
			expected.add(new Token(i, i, i));
		}
		assertEquals(expected, decode(encode(expected)));
	}

}
