package org.eclipse.lsp4j.jsonrpc.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.junit.Assert;
import org.junit.Test;

public class LauncherTest {
	
	private static final long TIMEOUT = 2000;
	
	static class Param {
		public String message;
	}
	
	static interface A {
		@JsonNotification public void say(Param p);
	}

	@Test public void testDone() throws Exception {
		A a = new A() {
			@Override
			public void say(Param p) {
			}
		};
		Launcher<A> launcher = Launcher.createLauncher(a, A.class, new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());
		Future<?> startListening = launcher.startListening();
		startListening.get(TIMEOUT, TimeUnit.MILLISECONDS);
		Assert.assertTrue(startListening.isDone());
		Assert.assertFalse(startListening.isCancelled());
	}
	
	@Test public void testCanceled() throws Exception {
		A a = new A() {
			@Override
			public void say(Param p) {
			}
		};
		Launcher<A> launcher = Launcher.createLauncher(a, A.class, new InputStream() {
			@Override
			public int read() throws IOException {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				return '\n';
			}
		}, new ByteArrayOutputStream());
		Future<?> startListening = launcher.startListening();
		startListening.cancel(true);
		Assert.assertTrue(startListening.isDone());
		Assert.assertTrue(startListening.isCancelled());
	}
}
