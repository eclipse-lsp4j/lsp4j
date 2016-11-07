package org.eclipse.lsp4j.lsp4j.jsonrpc;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.junit.Assert;
import org.junit.Test;

public class IntegrationTest {

	public static class MyParam {
		public MyParam(String string) {
			this.value = string;
		}

		String value;
	}

	public static interface MyServer {
		@JsonRequest
		CompletableFuture<MyParam> askServer(MyParam param);
	}

	public static interface MyClient {
		@JsonRequest
		CompletableFuture<MyParam> askClient(MyParam param);
	}

	@Test
	public void testBothDirectionRequests() throws Exception {
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();

		in.connect(out2);
		out.connect(in2);

		MyClient client = new MyClient() {

			@Override
			public CompletableFuture<MyParam> askClient(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}

		};
		Launcher<MyServer> clientSideLauncher = Launcher.createLauncher(client, MyServer.class, in, out);

		// create server side

		MyServer server = new MyServer() {

			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}

		};
		Launcher<MyClient> serverSideLauncher = Launcher.createLauncher(server, MyClient.class, in2, out2);

		clientSideLauncher.startListening();
		serverSideLauncher.startListening();
		
		CompletableFuture<MyParam> fooFuture = clientSideLauncher.getRemoteProxy().askServer(new MyParam("FOO"));
		CompletableFuture<MyParam> barFuture = serverSideLauncher.getRemoteProxy().askClient(new MyParam("BAR"));

		Assert.assertEquals("FOO", fooFuture.get().value);
		Assert.assertEquals("BAR", barFuture.get().value);
	}

	@Test
	public void testCancellation() throws Exception{
		// create client side
		PipedInputStream in = new PipedInputStream();
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in2 = new PipedInputStream();
		PipedOutputStream out2 = new PipedOutputStream();

		in.connect(out2);
		out.connect(in2);
		
		ArrayBlockingQueue<Boolean> cancellationHappend = new ArrayBlockingQueue<Boolean>(1);

		MyClient client = new MyClient() {

			@Override
			public CompletableFuture<MyParam> askClient(MyParam param) {
				return CompletableFutures.computeAsync(cancelToken -> {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}
					try {
						cancelToken.checkCanceled();
					} catch (CancellationException e) {
						cancellationHappend.add(true);
					}
					return param;
				});
			}

		};
		Launcher<MyServer> clientSideLauncher = Launcher.createLauncher(client, MyServer.class, in, out);

		// create server side

		MyServer server = new MyServer() {

			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				return CompletableFuture.completedFuture(param);
			}

		};
		Launcher<MyClient> serverSideLauncher = Launcher.createLauncher(server, MyClient.class, in2, out2);

		clientSideLauncher.startListening();
		serverSideLauncher.startListening();
		
		CompletableFuture<MyParam> future = serverSideLauncher.getRemoteProxy().askClient(new MyParam("FOO"));
		future.cancel(true);
		cancellationHappend.take();
		try {
			future.get();
			Assert.fail("expected cancellation");
		} catch (CancellationException e) {
		}
	}
}
