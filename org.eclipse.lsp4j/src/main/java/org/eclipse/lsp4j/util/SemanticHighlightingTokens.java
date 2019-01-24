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

import static java.util.Collections.emptyList;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.List;

import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.Iterables;

/**
 * Utility class for encoding and decoding semantic highlighting tokens into a
 * compact, {@code base64} representation.
 */
public final class SemanticHighlightingTokens {

	private static int LENGTH_SHIFT = 0x0000010;
	private static int SCOPES_MASK = 0x0000FFFF;

	/**
	 * Encodes the iterable of tokens into a compact, {@code base64} string. Returns
	 * with an empty string if the {@code tokens} argument is {@code null} or empty.
	 */
	public static String encode(Iterable<? extends Token> tokens) {
		if (IterableExtensions.isNullOrEmpty(tokens)) {
			return "";
		}
		// 2 stands for: number of elements for the output; the "character" and the
		// "length and scope".
		// 4 stands for: 4 byte for a primitive integer.
		ByteBuffer buffer = ByteBuffer.allocate(Iterables.size(tokens) * 2 * 4);
		for (Token token : tokens) {
			int character = token.character;
			int length = token.length;
			int scope = token.scope;

			int lengthAndScope = length;
			lengthAndScope = lengthAndScope << LENGTH_SHIFT;
			lengthAndScope |= scope;

			buffer.putInt(character);
			buffer.putInt(lengthAndScope);
		}
		return Base64.getEncoder().encodeToString(buffer.array());
	}

	/**
	 * Decodes the tokens string and returns with a list of semantic highlighting
	 * tokens. Returns with an empty list if the argument is {@code null} or empty.
	 */
	public static List<Token> decode(String tokens) {
		if (tokens == null || tokens.length() == 0) {
			return emptyList();
		}
		ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(tokens));
		Builder<Token> builder = ImmutableList.builder();
		while (buffer.hasRemaining()) {
			int character = buffer.getInt();

			int lengthAndScope = buffer.getInt();
			int length = lengthAndScope >>> LENGTH_SHIFT;
			int scope = lengthAndScope & SCOPES_MASK;

			builder.add(new Token(character, length, scope));
		}
		return builder.build();
	}

	/**
	 * The bare minimum representation of a semantic highlighting token.
	 * 
	 * <p>
	 * Semantic highlighting tokens are <b>not</b> part of the protocol, as they're
	 * sent through the wire as an encoded string. The purpose of this data type is
	 * to help working with them.
	 */
	public static class Token implements Comparable<Token> {

		private static int[] EMPTY_ARRAY = new int[0];
		private static int MAX_UINT_16 = 65_535; // 2^16 - 1

		/**
		 * Converts an iterable of tokens into an array of primitive integers. Returns
		 * with an empty array if the argument is {@code null} or empty.
		 * 
		 * <p>
		 * <ul>
		 * <li>{@code array[3*i]} is the {@link Token#character character} of the
		 * token.</li>
		 * <li>{@code array[3*i+1]} is the {@link Token#length length} of the
		 * token.</li>
		 * <li>{@code array[3*i+2]} is the token {@link Token#scope scope}.</li>
		 * </ul>
		 */
		public static int[] toIntArray(Iterable<? extends Token> tokens) {
			if (IterableExtensions.isNullOrEmpty(tokens)) {
				return EMPTY_ARRAY;
			}
			int[] array = new int[Iterables.size(tokens) * 3];
			int i = 0;
			for (Token token : tokens) {
				array[i++] = token.character;
				array[i++] = token.length;
				array[i++] = token.scope;
			}
			return array;
		}

		/**
		 * Counter-part of the {@link Token#toIntArray}. Converts an array of primitive
		 * integers into a list of tokens. If the input is {@code null}, returns with an
		 * empty list. Throws an exception if the length of the arguments is not a
		 * modulo of {@code 3}.
		 * 
		 * The elements of the input will be used to create new {@link Token} instances,
		 * hence they must comply the requirements described
		 * {@link #SemanticHighlightingTokens.Token(int, int, int) here}.
		 */
		public static List<Token> fromIntArray(int... input) {
			if (input == null) {
				return emptyList();
			}
			int inputLength = input.length;
			Preconditions.checkArgument(inputLength % 3 == 0,
					"Invalid input. 'input.length % 3 != 0' Input length was: " + inputLength + ".");
			Builder<Token> builder = ImmutableList.builder();
			for (int i = 0; i < inputLength; i = i + 3) {
				builder.add(new Token(
						input[i], // character
						input[i + 1], // length
						input[i + 2] // scope
				));
			}
			return builder.build();
		}

		/**
		 * The zero-based character offset of the token in the line.
		 */
		public final int character;

		/**
		 * The length of the token.
		 */
		public final int length;

		/**
		 * The internal index of the
		 * <a href="https://manual.macromates.com/en/language_grammars">TextMate
		 * scope</a>.
		 */
		public final int scope;

		/**
		 * Creates a new highlighting token.
		 *
		 * @param character
		 *            the character offset of the token. Must not be a negative integer.
		 * @param length
		 *            the length of the token. Must be an integer between {@code 0} and
		 *            {@code 2}<sup>16</sup>{@code -1} (inclusive).
		 * @param scope
		 *            the scope. Must be an integer between {@code 0} and
		 *            {@code 2}<sup>16</sup>{@code -1} (inclusive).
		 */
		public Token(int character, int length, int scope) {
			Preconditions.checkArgument(character >= 0, "Expected 'character' >= 0. Was: " + character + ".");
			this.character = character;
			this.length = assertUint16(length, "length");
			this.scope = assertUint16(scope, "scope");
		}

		/**
		 * Asserts the argument. It must be in a range of {@code 0} and
		 * {@code 2}<sup>16</sup>{@code -1} (inclusive). Returns with the argument if
		 * valid. Otherwise throws an {@link IllegalArgumentException}.
		 */
		private static int assertUint16(int i, String prefix) {
			Preconditions.checkArgument(i >= 0, "Expected: '" + prefix + "' >= 0. '" + prefix + "' was: " + i + ".");
			Preconditions.checkArgument(i <= MAX_UINT_16,
					"Expected: '" + prefix + "' <= " + MAX_UINT_16 + ". '" + prefix + "' was: " + i + ".");
			return i;
		}

		@Override
		public int compareTo(Token o) {
			return ComparisonChain.start().compare(character, o.character).compare(length, o.length)
					.compare(scope, o.scope).result();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + character;
			result = prime * result + length;
			result = prime * result + scope;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Token other = (Token) obj;
			if (character != other.character)
				return false;
			if (length != other.length)
				return false;
			if (scope != other.scope)
				return false;
			return true;
		}

	}

	private SemanticHighlightingTokens() {
	}

}
