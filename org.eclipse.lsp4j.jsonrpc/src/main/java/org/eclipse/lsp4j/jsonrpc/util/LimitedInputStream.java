/******************************************************************************
 * Copyright (c) 2024 Sebastian Thomschke and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LimitedInputStream extends FilterInputStream {
	private static final int EOF = -1;

	private int bytesRemaining;
	private final boolean closeWrapped;
	private boolean isClosed;
	private int mark = EOF;

	/**
	 * @param closeWrapped controls if the underlying {@link InputStream} should also be closed via {@link #close()}
	 */
	public LimitedInputStream(final InputStream wrapped, final int maxBytesToRead, final boolean closeWrapped) {
		super(wrapped);
		if (maxBytesToRead < 0)
			throw new IllegalArgumentException("[maxBytesToRead] must be >= 0");
		bytesRemaining = maxBytesToRead;
		this.closeWrapped = closeWrapped;
	}

	@Override
	public int available() throws IOException {
		if (isClosed)
			return 0;
		final int availableBytes = in.available();
		return Math.min(availableBytes, bytesRemaining);
	}

	@Override
	public void close() throws IOException {
		if (closeWrapped) {
			in.close();
		}
		isClosed = true;
	}

	@Override
	public int read() throws IOException {
		if (isClosed || bytesRemaining < 1)
			return EOF;

		final int data = in.read();
		if (data != EOF) {
			bytesRemaining--;
		}
		return data;
	}

	@Override
	public int read(final byte[] b, final int off, final int len) throws IOException {
		if (isClosed || bytesRemaining < 1)
			return EOF;

		final int bytesRead = in.read(b, off, Math.min(len, bytesRemaining));
		if (bytesRead != EOF) {
			bytesRemaining -= bytesRead;
		}
		return bytesRead;
	}

	@Override
	public synchronized void mark(final int readlimit) {
		in.mark(readlimit);
		mark = bytesRemaining;
	}

	@Override
	public synchronized void reset() throws IOException {
		if (!in.markSupported())
			throw new IOException("mark/reset not supported");

		if (mark == EOF)
			throw new IOException("mark not set");

		in.reset();
		bytesRemaining = mark;
	}

	@Override
	public long skip(final long n) throws IOException {
		final long skipped = in.skip(Math.min(n, bytesRemaining));
		bytesRemaining -= skipped;
		return skipped;
	}
}
