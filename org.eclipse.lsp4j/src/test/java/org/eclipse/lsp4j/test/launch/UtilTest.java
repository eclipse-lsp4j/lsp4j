/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.test.launch;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.launch.LSPUtil;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.junit.Assert;
import org.junit.Test;

public class UtilTest {
	
	private static final long TIMEOUT = 2000;
	
	private static class TestLanguageServer implements LanguageServer {

		@Override
		public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
			throw new UnsupportedOperationException();
		}

		@Override
		public CompletableFuture<Object> shutdown() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void exit() {
			System.out.println("EXIT 2");
		}

		@Override
		public TextDocumentService getTextDocumentService() {
			throw new UnsupportedOperationException();
		}

		@Override
		public WorkspaceService getWorkspaceService() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	public static class Process1 {
		public static void main(String[] args) throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line = reader.readLine();
			if (line != null) {
				System.out.println("ECHO: " + line);
			}
		}
	}
	
	public static class Process2 {
		public static void main(String[] args) {
			long processId = Long.parseLong(args[0]);
			LanguageServer langServer = new TestLanguageServer();
			LSPUtil.checkAlive(processId, langServer, 50);
			while (true) {}
		}
	}
	
	private Process startProcess(Class<?> mainClass, String... args) throws IOException {
		List<String> command = new ArrayList<>(args.length + 4);
		command.add(getJavaExec());
		command.add("-cp");
		command.add(System.getProperty("java.class.path"));
		command.add(mainClass.getName());
		for (String arg : args) {
			command.add(arg);
		}
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectError(Redirect.INHERIT);
		return builder.start();
	}
	
	private String getJavaExec() {
		// TODO support other operating systems
		return System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
	}
	
	private long getProcessId(Process process) throws NoSuchFieldException, IllegalAccessException {
		Class<? extends Process> clazz = process.getClass();
		// TODO support other operating systems
		if (clazz.getName().equals("java.lang.UNIXProcess")) {
			Field f = clazz.getDeclaredField("pid");
			f.setAccessible(true);
			return f.getInt(process);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	private void writeTo(Process process, String text) throws IOException {
		Writer writer1 = new OutputStreamWriter(process.getOutputStream());
		writer1.write(text);
		writer1.flush();
	}
	
	private String readOutput(Process process, ExecutorService executorService) throws InterruptedException, ExecutionException, TimeoutException {
		Callable<String> callable = () -> {
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			return reader.readLine();
		};
		Future<String> future = executorService.submit(callable);
		return future.get(TIMEOUT, TimeUnit.MILLISECONDS);
	}
	
	@Test
	public void testCheckAlive() throws Exception {
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().contains("win")) {
			// Disable test on Windows
			System.err.println(getClass().getName() + ": Tests are currently not supported on Windows.");
			return;
		}
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Process process1 = startProcess(Process1.class);
		Process process2 = startProcess(Process2.class, Long.toString(getProcessId(process1)));
		writeTo(process1, "EXIT 1\n");
		Assert.assertEquals("ECHO: EXIT 1", readOutput(process1, executorService));
		Assert.assertEquals("EXIT 2", readOutput(process2, executorService));
	}

}
