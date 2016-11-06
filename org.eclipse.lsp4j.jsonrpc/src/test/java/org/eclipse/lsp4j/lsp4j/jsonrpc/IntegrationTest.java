package org.eclipse.lsp4j.lsp4j.jsonrpc;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;

public class IntegrationTest {
	
	public static class MyParam {
		public MyParam(String string) {
			this.value = string;
		}

		String value;
	}
	
	public static interface MyServer {
		@JsonRequest CompletableFuture<MyParam> askServer(MyParam param);
	}
	
	public static interface MyClient {
		@JsonRequest CompletableFuture<MyParam> askClient(MyParam param);
	}
	
	public static void main(String[] args) throws Exception {
		
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
				System.out.println("asked client : "+param.value);
				return CompletableFuture.completedFuture(param);
			}
			
		};
		Launcher<MyServer> clientSideLauncher = Launcher.createLauncher(client, MyServer.class, in, out);
		
		// create server side
		
		MyServer server = new MyServer() {

			@Override
			public CompletableFuture<MyParam> askServer(MyParam param) {
				System.out.println("asked server : "+param.value);
				return CompletableFuture.completedFuture(param);
			}
			
		};
		Launcher<MyClient> serverSideLauncher = Launcher.createLauncher(server, MyClient.class, in2, out2);
		
		clientSideLauncher.startListening();
		serverSideLauncher.startListening();
		
		clientSideLauncher.getRemoteProxy().askServer(new MyParam("FOO"));
		serverSideLauncher.getRemoteProxy().askClient(new MyParam("BAR"));
	}
}
