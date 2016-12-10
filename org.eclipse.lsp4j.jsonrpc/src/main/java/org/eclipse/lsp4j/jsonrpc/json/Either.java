package org.eclipse.lsp4j.jsonrpc.json;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

public interface Either<L, R> {
	public static <L, R> Either<L, R> forLeft(L value) {
		return new Either<L,R>() {

			@Override
			public boolean isLeft() {
				return true;
			}

			@Override
			public L getLeft() {
				return value;
			}

			@Override
			public void onLeft(Consumer<L> consumer) {
				consumer.accept(value);
			}
			
			@Override
			public String toString() {
				return value.toString();
			}
		};
	}

	public static <L, R> Either<L, R> forRight(R value) {
		return new Either<L,R>() {

			@Override
			public boolean isRight() {
				return true;
			}

			@Override
			public R getRight() {
				return value;
			}

			@Override
			public void onRight(Consumer<R> consumer) {
				consumer.accept(value);
			}
			
			@Override
			public String toString() {
				return value.toString();
			}
		};
	}

	default boolean isLeft() { return false; }

	default boolean isRight() { return false; }

	default L getLeft() { throw new NoSuchElementException(); }

	default R getRight() { throw new NoSuchElementException(); }

	default void onLeft(Consumer<L> consumer) {}

	default void onRight(Consumer<R> consumer) {}

}
