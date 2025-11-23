/******************************************************************************
 * Copyright (c) 2016-2018 TypeFox and others.
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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.launch.DSPLauncher;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;
import org.eclipse.lsp4j.jsonrpc.Endpoint;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.json.StreamMessageProducer;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DSPLauncherTest {

	private static final long TIMEOUT = 2000;

	@Test
	public void testNotification() {
		OutputEventArguments p = new OutputEventArguments();
		p.setOutput("Hello World");

		client.expectedNotifications.put("output", p);
		serverLauncher.getRemoteProxy().output(p);
		client.joinOnEmpty();
	}

	@Test
	public void testRequest() throws Exception {
		InitializeRequestArguments p = new InitializeRequestArguments();
		p.setClientID("test");

		Capabilities result = new Capabilities();
		result.setSupportTerminateDebuggee(true);
		result.setSupportsCompletionsRequest(false);

		server.expectedRequests.put("initialize", List.of(p, result));
		CompletableFuture<Capabilities> future = clientLauncher.getRemoteProxy().initialize(p);
		Assert.assertEquals(result.toString(), future.get(TIMEOUT, TimeUnit.MILLISECONDS).toString());
		client.joinOnEmpty();
	}

	static class AssertingEndpoint implements Endpoint {
		public Map<String, List<Object>> expectedRequests = new LinkedHashMap<>();

		@Override
		public CompletableFuture<?> request(String method, Object parameter) {
			Assert.assertTrue(expectedRequests.containsKey(method));
			List<Object> result = expectedRequests.remove(method);
			Assert.assertEquals(2, result.size());
			Assert.assertEquals(result.get(0).toString(), parameter.toString());
			return CompletableFuture.completedFuture(result.get(1));
		}

		public Map<String, Object> expectedNotifications = new LinkedHashMap<>();

		@Override
		public void notify(String method, Object parameter) {
			Assert.assertTrue(expectedNotifications.containsKey(method));
			Object object = expectedNotifications.remove(method);
			Assert.assertEquals(object.toString(), parameter.toString());
		}

		/**
		 * wait max 1 sec for all expectations to be removed
		 */
		public void joinOnEmpty() {
			long before = System.currentTimeMillis();
			do {
				if (expectedNotifications.isEmpty() && expectedNotifications.isEmpty()) {
					return;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (System.currentTimeMillis() < before + 1000);
			Assert.fail("expectations weren't empty " + toString());
		}

		@Override
		public String toString() {
			return Arrays.toString(new Object[] {expectedRequests, expectedNotifications});
		}

	}

	private AssertingEndpoint server;
	private Launcher<IDebugProtocolClient> serverLauncher;
	private Future<?> serverListening;

	private AssertingEndpoint client;
	private Launcher<IDebugProtocolServer> clientLauncher;
	private Future<?> clientListening;

	private Level logLevel;

	@Before
	public void setup() throws IOException {
		PipedInputStream inClient = new PipedInputStream();
		PipedOutputStream outClient = new PipedOutputStream();
		PipedInputStream inServer = new PipedInputStream();
		PipedOutputStream outServer = new PipedOutputStream();

		inClient.connect(outServer);
		outClient.connect(inServer);
		server = new AssertingEndpoint();
		serverLauncher = DSPLauncher.createServerLauncher(
				ServiceEndpoints.toServiceObject(server, IDebugProtocolServer.class), inServer, outServer);
		serverListening = serverLauncher.startListening();

		client = new AssertingEndpoint();
		clientLauncher = DSPLauncher.createClientLauncher(
				ServiceEndpoints.toServiceObject(client, IDebugProtocolClient.class), inClient, outClient);
		clientListening = clientLauncher.startListening();

		Logger logger = Logger.getLogger(StreamMessageProducer.class.getName());
		logLevel = logger.getLevel();
		logger.setLevel(Level.SEVERE);
	}

	@After
	public void teardown() throws InterruptedException {
		clientListening.cancel(true);
		serverListening.cancel(true);
		Thread.sleep(10);
		Logger logger = Logger.getLogger(StreamMessageProducer.class.getName());
		logger.setLevel(logLevel);
	}

}
