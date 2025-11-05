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
package org.eclipse.lsp4j.jsonrpc;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public final class CompletableFutures {
	private CompletableFutures() {}

	/**
	 * A utility method to cancel the JSON-RPC request associated with the given future.
	 * <p>
	 * If the given future does not pertain to a JSON-RPC request or is <code>null</code>,
	 * this method has no effect.
	 *
	 * @param future a {@link CompletableFuture}, or <code>null</code>
	 * @param cancelRootFuture if <code>false</code>, this method will only ensure that a cancel
	 *  notification has been sent for the pending request to the remote endpoint, without attempting
	 *  to cancel any future pertaining to the request; if <code>true</code>, this method will
	 *  also attempt to cancel the root future for the request
	 * @return <code>false</code> if the given future does not pertain to a JSON-RPC request
	 *  or is <code>null</code>; <code>true</code> otherwise
	 */
	public static boolean cancelRequest(CompletableFuture<?> future, boolean cancelRootFuture) {
		if (future instanceof JsonRpcRequestFuture) {
			if (cancelRootFuture) {
				((JsonRpcRequestFuture<?>) future).getRoot().cancel(true);
			} else {
				((JsonRpcRequestFuture<?>) future).cancelRequest();
			}
			return true;
		}
		return false;
	}

	/**
	 * A utility method to create a {@link CompletableFuture} with cancellation support.
	 *
	 * @param code a function that accepts a {@link CancelChecker} and returns the to be computed value
	 * @return a future
	 */
	public static <R> CompletableFuture<R> computeAsync(Function<CancelChecker, R> code) {
		final var start = new CompletableFuture<CancelChecker>();
		CompletableFuture<R> result = start.thenApplyAsync(code);
		start.complete(new FutureCancelChecker(result));
		return result;
	}

	/**
	 * A utility method to create a {@link CompletableFuture} with cancellation support.
	 *
	 * @param code a function that accepts a {@link CancelChecker} and returns the to be computed value
	 * @return a future
	 */
	public static <R> CompletableFuture<R> computeAsync(Executor executor, Function<CancelChecker, R> code) {
		final var start = new CompletableFuture<CancelChecker>();
		CompletableFuture<R> result = start.thenApplyAsync(code, executor);
		start.complete(new FutureCancelChecker(result));
		return result;
	}

	public static class FutureCancelChecker implements CancelChecker {

		private final CompletableFuture<?> future;

		public FutureCancelChecker(CompletableFuture<?> future) {
			this.future = future;
		}

		@Override
		public void checkCanceled() {
			if (future.isCancelled())
				throw new CancellationException();
		}

		@Override
		public boolean isCanceled() {
			return future.isCancelled();
		}

	}

}
