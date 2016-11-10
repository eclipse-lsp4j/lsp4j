/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.json

import org.eclipse.lsp4j.services.LanguageServer
import java.io.IOException
import java.io.PrintWriter
import java.net.SocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.Channels
import java.nio.channels.CompletionHandler

@Deprecated
class LanguageServerLauncher {

	def static LanguageServerLauncher newLauncher(LanguageServer languageServer, SocketAddress socketAddress) {
		return new LanguageServerLauncher(new LanguageServerToJsonAdapter(languageServer), socketAddress)
	}

	def static LanguageServerLauncher newLoggingLauncher(LanguageServer languageServer, SocketAddress socketAddress) {
		val server = new LoggingJsonAdapter(languageServer)
		server.errorLog = new PrintWriter(System.err)
		server.messageLog = new PrintWriter(System.out)
		return new LanguageServerLauncher(server, socketAddress)
	}

	val SocketAddress socketAddress

	val LanguageServerToJsonAdapter languageServer

	new(LanguageServerToJsonAdapter languageServer, SocketAddress socketAddress) {
		this.socketAddress = socketAddress
		this.languageServer = languageServer
	}

	def void launch() {
		var AsynchronousServerSocketChannel serverSocket
		try {
			serverSocket = AsynchronousServerSocketChannel.open

			serverSocket.bind(socketAddress)
			println('Listening to ' + socketAddress)
			serverSocket.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

				override completed(AsynchronousSocketChannel channel, Object attachment) {
					val in = Channels.newInputStream(channel)
					val out = Channels.newOutputStream(channel)
					println('Connection accepted')

					languageServer.connect(in, out)
					languageServer.join()

					channel.close()
					println('Connection closed')
				}

				override failed(Throwable exc, Object attachment) {
					exc.printStackTrace
				}

			}) 
			while (true) {
				Thread.sleep(2000)
			}
		} catch (Throwable t) {
			t.printStackTrace()
		} finally {
			if (serverSocket !== null) {
				try {
					serverSocket.close()
				} catch (IOException e) {
				}
			}
		}
	}

}
