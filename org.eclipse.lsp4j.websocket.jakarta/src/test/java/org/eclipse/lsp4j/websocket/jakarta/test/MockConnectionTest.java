/******************************************************************************
 * Copyright (c) 2019, 2021 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.websocket.jakarta.test;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.websocket.jakarta.WebSocketEndpoint;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MockConnectionTest {
	
	private static final long TIMEOUT = 2000;
	
	private Client client;
	private Server server;
	
	@SuppressWarnings("resource")
	@Before
	public void setup() {
		client = new Client();
		server = new Server();
		MockSession clientSession = new MockSession();
		MockSession serverSession = new MockSession();
		clientSession.connect(serverSession);
		clientSession.open(new ClientSideEndpoint());
		serverSession.open(new ServerSideEndpoint());
	}
	
	@Test
	public void testClientRequest() throws Exception {
		CompletableFuture<String> future = client.server.request("foo");
		String result = future.get(TIMEOUT, TimeUnit.MILLISECONDS);
		Assert.assertEquals("foobar", result);
	}
	
	@Test
	public void testNotifications() throws Exception {
		server.client.notify("12");
		await(() -> client.result.length() == 2);
		client.server.notify("foo");
		await(() -> server.result.length() == 3);
		server.client.notify("34");
		await(() -> client.result.length() == 4);
		client.server.notify("bar");
		await(() -> server.result.length() == 6);
		server.client.notify("56");
		await(() -> client.result.length() == 6);
		
		Assert.assertEquals("foobar", server.result);
		Assert.assertEquals("123456", client.result);
	}
	
	@Test
	public void testChunkedNotification() throws Exception {
		StringBuilder messageBuilder = new StringBuilder();
		Random random = new Random(1);
		for (int i = 0; i < 3 * MockSession.MAX_CHUNK_SIZE; i++) {
			messageBuilder.append((char) ('a' + random.nextInt('z' - 'a' + 1)));
		}
		String message = messageBuilder.toString();
		
		server.client.notify(message);
		await(() -> client.result.length() == message.length());
		
		Assert.assertEquals(message, client.result);
	}

	private void await(Supplier<Boolean> condition) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		while (!condition.get()) {
			Thread.sleep(20);
			if (System.currentTimeMillis() - startTime > TIMEOUT) {
				Assert.fail("Timeout elapsed while waiting for condition.\n");
			}
		}
	}
	
	private static interface ClientInterface {
		
		@JsonNotification("client/notify")
		void notify(String arg);
		
	}
	
	private static class Client implements ClientInterface {
		ServerInterface server;
		String result = "";

		@Override
		public void notify(String arg) {
			this.result += arg;
		}
	}
	
	private static interface ServerInterface {
		
		@JsonRequest("server/request")
		CompletableFuture<String> request(String arg);
		
		@JsonNotification("server/notify")
		void notify(String arg);
		
	}
	
	private static class Server implements ServerInterface {
		ClientInterface client;
		String result = "";
		
		@Override
		public CompletableFuture<String> request(String arg) {
			return CompletableFuture.supplyAsync(() -> arg + "bar");
		}
		
		@Override
		public void notify(String arg) {
			this.result += arg;
		}
	}
	
	private class ClientSideEndpoint extends WebSocketEndpoint<ServerInterface> {

		@Override
		protected void configure(Launcher.Builder<ServerInterface> builder) {
			builder
				.setLocalService(client)
				.setRemoteInterface(ServerInterface.class);
		}
		
		@Override
		protected void connect(Collection<Object> localServices, ServerInterface remoteProxy) {
			localServices.forEach(s -> ((Client) s).server = remoteProxy);
		}
		
	}
	
	private class ServerSideEndpoint extends WebSocketEndpoint<ClientInterface> {
		
		@Override
		protected void configure(Launcher.Builder<ClientInterface> builder) {
			builder
				.setLocalService(server)
				.setRemoteInterface(ClientInterface.class);
		}
		
		@Override
		protected void connect(Collection<Object> localServices, ClientInterface remoteProxy) {
			localServices.forEach(s -> ((Server) s).client = remoteProxy);
		}
		
	}

}
