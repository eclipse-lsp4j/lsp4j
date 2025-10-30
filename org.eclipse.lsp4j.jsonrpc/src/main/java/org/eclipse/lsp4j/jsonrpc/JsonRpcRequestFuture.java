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

/**
 * A {@link CompletableFuture} representing an outbound JSON-RPC request.
 * <p>
 * Semantics:
 * <ul>
 * <li>{@link #cancelRequest(boolean)} sends the LSP <code>$/cancelRequest</code> once for the
 * entire request chain and cancels the root locally by default (fast-cancel).</li>
 * <li>Use {@link #cancelRequest(boolean, boolean)} with {@code cancelRootLocally == false} to send
 * the cancel but keep the root pending to receive the server's cancellation response.</li>
 * <li>{@link #cancel(boolean)} on the root behaves like {@code cancelRequest(true)} (sends once).</li>
 * <li>{@link #cancel(boolean)} on dependent stages cancels only that stage and does not send.</li>
 * <li>Protocol cancel emission is deduplicated across the chain under concurrency.</li>
 * </ul>
 * <p>
 * Completion outcomes:
 * <ul>
 * <li>Fast-cancel: root completes with {@link java.util.concurrent.CancellationException}; any later
 * server response is ignored for the root.</li>
 * <li>Wait-for-remote: root remains pending and is completed by the server response, typically
 * exceptionally with {@link org.eclipse.lsp4j.jsonrpc.ResponseErrorException} using
 * {@link org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode#RequestCancelled}.</li>
 * </ul>
 * <p>
 * <b>IMPORTANT:</b> Cancelling a dependent stage via {@link #cancel(boolean)} only cancels that
 * stage locally; it does not send <code>$/cancelRequest</code> and it does not cancel the root future.
 * To cancel the remote request, call one of the {@code cancelRequest(...)} methods from any stage.
 */
@SuppressWarnings("unchecked")
public final class JsonRpcRequestFuture<T> extends CompletableFuture<T> {

	private final Runnable cancelAction;
	private final AtomicBoolean cancelSent;
	private final JsonRpcRequestFuture<?> root;

	public JsonRpcRequestFuture(final Runnable cancelAction) {
		root = null;
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
		if (root == null && cancelSent.compareAndSet(false, true)) {
			cancelAction.run();
		}
		return super.cancel(mayInterruptIfRunning);
	}

	/**
	 * Send the protocol cancel (<code>$/cancelRequest</code>) for this request chain exactly once and cancel this future locally.
	 * Also cancels the root future locally (fast-cancel) so callers waiting on it are unblocked.
	 * Use {@link #cancelRequest(boolean, boolean)} with {@code cancelRootLocally == false} to
	 * keep the root pending and wait for the remote cancel response instead.
	 *
	 * @param mayInterruptIfRunning passed through to {@link #cancel(boolean)} for this stage
	 *
	 * @return true if this call cancelled this future; false if already completed or cancelled
	 */
	public boolean cancelRequest(final boolean mayInterruptIfRunning) {
		return cancelRequest(mayInterruptIfRunning, true);
	}

	/**
	 * Send the protocol cancel (<code>$/cancelRequest</code>) for this request chain exactly once and cancel this future locally.
	 * Optionally cancel the root future locally, or keep it pending to wait for the remote cancel response.
	 *
	 * Semantics:
	 * <ul>
	 * <li>When called on the root with {@code cancelRootLocally == true}, the root is cancelled locally
	 * and the cancel notification is sent once.
	 * <li>When called on the root with {@code cancelRootLocally == false}, only the cancel notification is sent;
	 * the root remains pending to receive the server's cancellation response.
	 * <li>When called on a dependent stage, the cancel notification is sent via the root. If
	 * {@code cancelRootLocally == true}, the root is also cancelled locally; otherwise it remains pending.
	 * </ul>
	 *
	 * @param mayInterruptIfRunning passed through to {@link #cancel(boolean)} for this stage
	 * @param cancelRootLocally when true, cancel the root future locally as well; when false, do not cancel
	 *            the root so it can wait for the remote cancellation response
	 *
	 * @return true if this call cancelled this future; false if already completed or cancelled
	 */
	public boolean cancelRequest(final boolean mayInterruptIfRunning, final boolean cancelRootLocally) {
		if (root == null) {
			// We are the root: send protocol cancel exactly once
			if (cancelSent.compareAndSet(false, true)) {
				cancelAction.run();
			}
			if (cancelRootLocally) {
				return super.cancel(mayInterruptIfRunning);
			}
			// Do not cancel the root locally; only send protocol cancel
			return false;
		}

		// We are a dependent stage
		final var cancelled = super.cancel(mayInterruptIfRunning);
		if (cancelRootLocally) {
			// Cancel the root, which will send protocol cancel (deduped)
			root.cancel(mayInterruptIfRunning);
		} else {
			// Send protocol cancel without cancelling root locally
			if (root.cancelSent.compareAndSet(false, true)) {
				root.cancelAction.run();
			}
		}
		return cancelled;
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
		return new JsonRpcRequestFuture<>((root == null) ? this : root);
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
