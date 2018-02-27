/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.launch;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.lsp4j.services.LanguageServer;

public final class LSPUtil {
	
	private LSPUtil() {}

	/**
	 * Calls {@link #checkAlive(long, LanguageServer, long)} with a predefined period of 3000 milliseconds.
	 */
	public static ScheduledFuture<Void> checkAlive(long processId, LanguageServer langServer) {
		return checkAlive(processId, langServer, 3000);
	}
	
	/**
	 * Start a thread that checks periodically whether the process with the given identifier is alive.
	 * Once it has been determined that it is not alive, the {@link LanguageServer#exit()} method is
	 * called on the given language server.
	 * 
	 * @param period - the checking period in milliseconds
	 */
	@SuppressWarnings("unchecked")
	public static ScheduledFuture<Void> checkAlive(long processId, LanguageServer langServer, long period) {
		final ScheduledFuture<?>[] future = new ScheduledFuture<?>[1];
		Runnable runnable = () -> {
			try {
				if (!isAlive(processId)) {
					try {
						langServer.exit();
					} finally {
						System.exit(0);
					}
				}
			} catch (IOException | InterruptedException e) {
				future[0].cancel(true);
			}
		};
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		future[0] = executor.scheduleAtFixedRate(runnable, period, period, TimeUnit.MILLISECONDS);
		return (ScheduledFuture<Void>) future[0];
	}
	
	/**
	 * Check whether the process with the given identifier is currently alive.
	 */
	public static boolean isAlive(long processId) throws IOException, InterruptedException {
		// TODO support other operating systems
		Process process = Runtime.getRuntime().exec(new String[] {"kill", "-0", String.valueOf(processId)});
		int exitCode = process.waitFor();
		return exitCode == 0;
	}

}
