/******************************************************************************
 * Copyright (c) 2018 Kichwa Coders and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.debug.test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Future;

import org.eclipse.lsp4j.debug.launch.DSPLauncher;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.junit.Test;

public class DSPDebugServer {

	// This is a test for https://github.com/eclipse/lsp4j/issues/224
	@Test
	public void testDebugServerCanBeLaunched() throws IOException {
		TestDebugServer testDebugServer = new TestDebugServer();
		Launcher<IDebugProtocolClient> launcher = DSPLauncher.createServerLauncher(testDebugServer,
				new PipedInputStream(), new PipedOutputStream());
		Future<Void> listening = launcher.startListening();
		listening.cancel(true);
	}
}
