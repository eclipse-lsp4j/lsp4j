/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.launch

import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.lsp4j.services.json.MessageJsonHandler
import org.eclipse.lsp4j.services.json.StreamMessageReader
import org.eclipse.lsp4j.services.json.StreamMessageWriter
import org.eclipse.lsp4j.services.transport.LanguageEndpoint
import org.eclipse.lsp4j.services.transport.io.ConcurrentMessageReader
import org.eclipse.lsp4j.services.transport.server.LanguageServerEndpoint
import org.eclipse.lsp4j.services.transport.trace.MessageTracer
import org.eclipse.lsp4j.services.transport.trace.PrintMessageTracer
import java.io.IOException
import java.io.PrintWriter
import java.net.SocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.AsynchronousSocketChannel
import java.nio.channels.Channels
import java.nio.channels.CompletionHandler
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class LanguageServerLauncher {

    def static LanguageServerLauncher newLauncher(LanguageServer languageServer, SocketAddress socketAddress) {
        return newLauncher(languageServer, Executors.newCachedThreadPool, null, socketAddress)
    }

    def static LanguageServerLauncher newLoggingLauncher(LanguageServer languageServer, SocketAddress socketAddress) {
        val errorLog = new PrintWriter(System.err)
        val messageLog = new PrintWriter(System.out)
        val messageTracer = new PrintMessageTracer(errorLog, messageLog)
        return newLauncher(languageServer, Executors.newCachedThreadPool, messageTracer, socketAddress)
    }

    def static LanguageServerLauncher newLauncher(
        LanguageServer languageServer,
        ExecutorService executorService,
        MessageTracer messageTracer,
        SocketAddress socketAddress
    ) {
        val server = new LanguageServerEndpoint(languageServer, executorService)
        server.messageTracer = messageTracer
        return new LanguageServerLauncher(socketAddress, executorService, server)
    }

    val SocketAddress socketAddress
    val ExecutorService executorService
    val LanguageEndpoint languageEndpoint
    
    val jsonHandler = new MessageJsonHandler

    def void launch() {

        var AsynchronousServerSocketChannel serverSocket
        try {
            serverSocket = AsynchronousServerSocketChannel.open

            serverSocket.bind(socketAddress)
            println('Listening to ' + socketAddress)
            serverSocket.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

                override completed(AsynchronousSocketChannel channel, Object attachment) {
                    val in = Channels.newInputStream(channel)
                    val reader = new StreamMessageReader(in, jsonHandler)
                    val concurrentReader = new ConcurrentMessageReader(reader, executorService)

                    val out = Channels.newOutputStream(channel)
                    val writer = new StreamMessageWriter(out, jsonHandler)
                    println('Connection accepted')

                    languageEndpoint.connect(concurrentReader, writer)
                    concurrentReader.join()

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
            languageEndpoint.close()
        }
    }

}
