/**
 * Copyright (c) 2025 Vegard IT GmbH and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.jsonrpc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A {@link CompletableFuture} representing an outbound JSON-RPC request.
 * <p>
 * Design highlights:
 * <ul>
 * <li>{@link #cancelRequest()} sends the LSP <code>$/cancelRequest</code> exactly once for the
 * entire request chain and does not cancel any future.</li>
 * <li>{@link #getRoot()} returns the root request future; calling {@link #cancel(boolean)} on the
 * root ensures {@link #cancelRequest()} is invoked exactly once.</li>
 * <li>{@link #cancel(boolean)} on dependent stages cancels only that stage and does not send.</li>
 * <li>Protocol cancel emission is deduplicated across the chain under concurrency and skipped if the
 * request is already completed.</li>
 * </ul>
 */
@SuppressWarnings("unchecked")
public final class JsonRpcRequestFuture<T> extends CompletableFuture<T> {

	private static final Logger LOG = Logger.getLogger(JsonRpcRequestFuture.class.getName());

	private final Runnable cancelAction;
	private final AtomicBoolean cancelSent;
	private final JsonRpcRequestFuture<?> root;

	public JsonRpcRequestFuture(final Runnable cancelAction) {
		root = this;
		this.cancelAction = cancelAction;
		cancelSent = new AtomicBoolean(false);
	}

	private JsonRpcRequestFuture(final JsonRpcRequestFuture<?> root) {
		this.root = root;
		cancelAction = null;
		cancelSent = null;
	}

	@Override
	public JsonRpcRequestFuture<Void> acceptEither(final CompletionStage<? extends T> other, final Consumer<? super T> action) {
		return (JsonRpcRequestFuture<Void>) super.acceptEither(other, action);
	}

	@Override
	public JsonRpcRequestFuture<Void> acceptEitherAsync(final CompletionStage<? extends T> other, final Consumer<? super T> action) {
		return (JsonRpcRequestFuture<Void>) super.acceptEitherAsync(other, action);
	}

	@Override
	public JsonRpcRequestFuture<Void> acceptEitherAsync(final CompletionStage<? extends T> other, final Consumer<? super T> action,
			final Executor executor) {
		return (JsonRpcRequestFuture<Void>) super.acceptEitherAsync(other, action, executor);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> applyToEither(final CompletionStage<? extends T> other, final Function<? super T, U> fn) {
		return (JsonRpcRequestFuture<U>) super.applyToEither(other, fn);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> applyToEitherAsync(final CompletionStage<? extends T> other, final Function<? super T, U> fn) {
		return (JsonRpcRequestFuture<U>) super.applyToEitherAsync(other, fn);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> applyToEitherAsync(final CompletionStage<? extends T> other, final Function<? super T, U> fn,
			final Executor executor) {
		return (JsonRpcRequestFuture<U>) super.applyToEitherAsync(other, fn, executor);
	}

	@Override
	public boolean cancel(final boolean mayInterruptIfRunning) {
		// Root cancel ensures cancelRequest() behavior once before local cancellation
		if (root == this) {
			try {
				cancelRequest();
			} catch (final RuntimeException ex) {
				// catching potential exception to ensure local cancel still proceeds
				LOG.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		return super.cancel(mayInterruptIfRunning);
	}

	/**
	 * Returns the root future for this request chain. For the root, this returns {@code this}.
	 */
	public JsonRpcRequestFuture<?> getRoot() {
		return root;
	}

	/**
	 * Sends the protocol cancel (<code>$/cancelRequest</code>) exactly once for this request chain.
	 * Does not cancel any future locally. Returns {@code true} if the notification was sent by this call,
	 * or {@code false} if it was already sent before or the request is already completed.
	 */
	public boolean cancelRequest() {
		final JsonRpcRequestFuture<?> root = getRoot();
		if (root.isDone()) {
			return false;
		}
		if (root.cancelSent.compareAndSet(false, true)) {
			root.cancelAction.run();
			return true;
		}
		return false;
	}

	@Override
	public JsonRpcRequestFuture<T> completeAsync(final Supplier<? extends T> supplier) {
		return (JsonRpcRequestFuture<T>) super.completeAsync(supplier);
	}

	@Override
	public JsonRpcRequestFuture<T> completeAsync(final Supplier<? extends T> supplier, final Executor executor) {
		return (JsonRpcRequestFuture<T>) super.completeAsync(supplier, executor);
	}

	@Override
	public JsonRpcRequestFuture<T> completeOnTimeout(final T value, final long timeout, final TimeUnit unit) {
		return (JsonRpcRequestFuture<T>) super.completeOnTimeout(value, timeout, unit);
	}

	@Override
	public JsonRpcRequestFuture<T> copy() {
		return (JsonRpcRequestFuture<T>) super.copy();
	}

	@Override
	public JsonRpcRequestFuture<T> exceptionally(final Function<Throwable, ? extends T> fn) {
		return (JsonRpcRequestFuture<T>) super.exceptionally(fn);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> handle(final BiFunction<? super T, Throwable, ? extends U> fn) {
		return (JsonRpcRequestFuture<U>) super.handle(fn);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> handleAsync(final BiFunction<? super T, Throwable, ? extends U> fn) {
		return (JsonRpcRequestFuture<U>) super.handleAsync(fn);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> handleAsync(final BiFunction<? super T, Throwable, ? extends U> fn, final Executor executor) {
		return (JsonRpcRequestFuture<U>) super.handleAsync(fn, executor);
	}

	@Override
	public JsonRpcRequestFuture<T> newIncompleteFuture() {
		// Dependent stages share the same cancel state but are not roots
		return new JsonRpcRequestFuture<>(root);
	}

	@Override
	public JsonRpcRequestFuture<T> orTimeout(final long timeout, final TimeUnit unit) {
		return (JsonRpcRequestFuture<T>) super.orTimeout(timeout, unit);
	}

	@Override
	public JsonRpcRequestFuture<Void> runAfterBoth(final CompletionStage<?> other, final Runnable action) {
		return (JsonRpcRequestFuture<Void>) super.runAfterBoth(other, action);
	}

	@Override
	public JsonRpcRequestFuture<Void> runAfterBothAsync(final CompletionStage<?> other, final Runnable action) {
		return (JsonRpcRequestFuture<Void>) super.runAfterBothAsync(other, action);
	}

	@Override
	public JsonRpcRequestFuture<Void> runAfterBothAsync(final CompletionStage<?> other, final Runnable action, final Executor executor) {
		return (JsonRpcRequestFuture<Void>) super.runAfterBothAsync(other, action, executor);
	}

	@Override
	public JsonRpcRequestFuture<Void> runAfterEither(final CompletionStage<?> other, final Runnable action) {
		return (JsonRpcRequestFuture<Void>) super.runAfterEither(other, action);
	}

	@Override
	public JsonRpcRequestFuture<Void> runAfterEitherAsync(final CompletionStage<?> other, final Runnable action) {
		return (JsonRpcRequestFuture<Void>) super.runAfterEitherAsync(other, action);
	}

	@Override
	public JsonRpcRequestFuture<Void> runAfterEitherAsync(final CompletionStage<?> other, final Runnable action, final Executor executor) {
		return (JsonRpcRequestFuture<Void>) super.runAfterEitherAsync(other, action, executor);
	}

	@Override
	public JsonRpcRequestFuture<Void> thenAccept(final Consumer<? super T> action) {
		return (JsonRpcRequestFuture<Void>) super.thenAccept(action);
	}

	@Override
	public JsonRpcRequestFuture<Void> thenAcceptAsync(final Consumer<? super T> action) {
		return (JsonRpcRequestFuture<Void>) super.thenAcceptAsync(action);
	}

	@Override
	public JsonRpcRequestFuture<Void> thenAcceptAsync(final Consumer<? super T> action, final Executor executor) {
		return (JsonRpcRequestFuture<Void>) super.thenAcceptAsync(action, executor);
	}

	@Override
	public <U> JsonRpcRequestFuture<Void> thenAcceptBoth(final CompletionStage<? extends U> other,
			final BiConsumer<? super T, ? super U> action) {
		return (JsonRpcRequestFuture<Void>) super.thenAcceptBoth(other, action);
	}

	@Override
	public <U> JsonRpcRequestFuture<Void> thenAcceptBothAsync(final CompletionStage<? extends U> other,
			final BiConsumer<? super T, ? super U> action) {
		return (JsonRpcRequestFuture<Void>) super.thenAcceptBothAsync(other, action);
	}

	@Override
	public <U> JsonRpcRequestFuture<Void> thenAcceptBothAsync(final CompletionStage<? extends U> other,
			final BiConsumer<? super T, ? super U> action, final Executor executor) {
		return (JsonRpcRequestFuture<Void>) super.thenAcceptBothAsync(other, action, executor);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> thenApply(final Function<? super T, ? extends U> fn) {
		return (JsonRpcRequestFuture<U>) super.thenApply(fn);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> thenApplyAsync(final Function<? super T, ? extends U> fn) {
		return (JsonRpcRequestFuture<U>) super.thenApplyAsync(fn);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> thenApplyAsync(final Function<? super T, ? extends U> fn, final Executor executor) {
		return (JsonRpcRequestFuture<U>) super.thenApplyAsync(fn, executor);
	}

	@Override
	public <U, V> JsonRpcRequestFuture<V> thenCombine(final CompletionStage<? extends U> other,
			final BiFunction<? super T, ? super U, ? extends V> fn) {
		return (JsonRpcRequestFuture<V>) super.thenCombine(other, fn);
	}

	@Override
	public <U, V> JsonRpcRequestFuture<V> thenCombineAsync(final CompletionStage<? extends U> other,
			final BiFunction<? super T, ? super U, ? extends V> fn) {
		return (JsonRpcRequestFuture<V>) super.thenCombineAsync(other, fn);
	}

	@Override
	public <U, V> JsonRpcRequestFuture<V> thenCombineAsync(final CompletionStage<? extends U> other,
			final BiFunction<? super T, ? super U, ? extends V> fn, final Executor executor) {
		return (JsonRpcRequestFuture<V>) super.thenCombineAsync(other, fn, executor);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> thenCompose(final Function<? super T, ? extends CompletionStage<U>> fn) {
		return (JsonRpcRequestFuture<U>) super.thenCompose(fn);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> thenComposeAsync(final Function<? super T, ? extends CompletionStage<U>> fn) {
		return (JsonRpcRequestFuture<U>) super.thenComposeAsync(fn);
	}

	@Override
	public <U> JsonRpcRequestFuture<U> thenComposeAsync(final Function<? super T, ? extends CompletionStage<U>> fn,
			final Executor executor) {
		return (JsonRpcRequestFuture<U>) super.thenComposeAsync(fn, executor);
	}

	@Override
	public JsonRpcRequestFuture<Void> thenRun(final Runnable action) {
		return (JsonRpcRequestFuture<Void>) super.thenRun(action);
	}

	@Override
	public JsonRpcRequestFuture<Void> thenRunAsync(final Runnable action) {
		return (JsonRpcRequestFuture<Void>) super.thenRunAsync(action);
	}

	@Override
	public JsonRpcRequestFuture<Void> thenRunAsync(final Runnable action, final Executor executor) {
		return (JsonRpcRequestFuture<Void>) super.thenRunAsync(action, executor);
	}

	@Override
	public JsonRpcRequestFuture<T> toCompletableFuture() {
		return (JsonRpcRequestFuture<T>) super.toCompletableFuture();
	}

	@Override
	public JsonRpcRequestFuture<T> whenComplete(final BiConsumer<? super T, ? super Throwable> action) {
		return (JsonRpcRequestFuture<T>) super.whenComplete(action);
	}

	@Override
	public JsonRpcRequestFuture<T> whenCompleteAsync(final BiConsumer<? super T, ? super Throwable> action) {
		return (JsonRpcRequestFuture<T>) super.whenCompleteAsync(action);
	}

	@Override
	public JsonRpcRequestFuture<T> whenCompleteAsync(final BiConsumer<? super T, ? super Throwable> action, final Executor executor) {
		return (JsonRpcRequestFuture<T>) super.whenCompleteAsync(action, executor);
	}
}
