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

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import org.eclipse.lsp4j.DocumentSymbol;

/**
 * Utilities for {@link DocumentSymbol document symbols}.
 */
public final class DocumentSymbols {

	/**
	 * Returns an unmodifiable iterator over the {@link DocumentSymbol document
	 * symbols} using the breath-first traversal. That is, all the symbols of depth
	 * 0 are returned, then depth 1, then 2, and so on.
	 * 
	 * <p>
	 * It does not guarantee a well defined behavior of the resulting iterator if
	 * you modify the structure while the iteration is in progress.
	 */
	public static Iterator<DocumentSymbol> asIterator(DocumentSymbol documentSymbol) {
		return new DocumentSymbolIterator(documentSymbol);
	}

	protected static class DocumentSymbolIterator extends BreadthFirstIterator<DocumentSymbol> {

		protected DocumentSymbolIterator(DocumentSymbol documentSymbol) {
			super(Preconditions.checkNotNull(documentSymbol, "documentSymbol"));
		}

		@Override
		protected Iterable<DocumentSymbol> getChildren(DocumentSymbol node) {
			return node.getChildren();
		}

	}

	protected abstract static class BreadthFirstIterator<T> implements Iterator<T> {

		private Queue<T> queue;

		protected BreadthFirstIterator(T root) {
			Preconditions.checkNotNull(root, "root");
			this.queue = new ArrayDeque<>();
			this.queue.add(root);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
			return !this.queue.isEmpty();
		}

		@Override
		public T next() {
			T result = queue.remove();
			Iterable<T> children = getChildren(result);
			if (children != null) {
				for (T child : children) {
					queue.add(child);
				}
			}
			return result;
		}

		/**
		 * Returns with the children (direct descendants) of the {@code node} argument.
		 * If the argument does not have any children, clients are allowed to return
		 * {@code null}.
		 */
		protected abstract Iterable<T> getChildren(T node);

	}

	private DocumentSymbols() {

	}

}
