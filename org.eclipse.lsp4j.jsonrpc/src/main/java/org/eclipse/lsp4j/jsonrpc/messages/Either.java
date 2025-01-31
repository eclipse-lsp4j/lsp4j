/******************************************************************************
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.messages;

import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * An either type maps union types in protocol specifications.
 */
public class Either<L, R> {

	public static <L, R> Either<L, R> forLeft(@NonNull L left) {
		return new Either<>(left, null);
	}

	public static <L, R> Either<L, R> forRight(@NonNull R right) {
		return new Either<>(null, right);
	}

	private final L left;
	private final R right;

	protected Either(L left, R right) {
		super();
		this.left = left;
		this.right = right;
	}

	public L getLeft() {
		return left;
	}

	public R getRight() {
		return right;
	}
	
	public Object get() {
		if (left != null)
			return left;
		if (right != null)
			return right;
		return null;
	}

	public boolean isLeft() {
		return left != null;
	}

	public boolean isRight() {
		return right != null;
	}

	public <T> T map(
			@NonNull Function<? super L, ? extends T> mapLeft,
			@NonNull Function<? super R, ? extends T> mapRight) {
		if (isLeft()) {
			return mapLeft.apply(getLeft());
		}
		if (isRight()) {
			return mapRight.apply(getRight());
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Either<?, ?>) {
			final var other = (Either<?, ?>) obj;
			return this.left == other.left && this.right == other.right
				|| this.left != null && other.left != null && this.left.equals(other.left)
				|| this.right != null && other.right != null && this.right.equals(other.right);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		if (this.left != null)
			return this.left.hashCode();
		if (this.right != null)
			return this.right.hashCode();
		return 0;
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder("Either [").append(System.lineSeparator());
		builder.append("  left = ").append(left).append(System.lineSeparator());
		builder.append("  right = ").append(right).append(System.lineSeparator());
		return builder.append("]").toString();
	}
}
