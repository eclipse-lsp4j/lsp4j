/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.transport.io

import org.eclipse.lsp4j.Message
import java.nio.channels.ClosedChannelException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class ConcurrentMessageReader extends AbstractMessageReader implements Runnable {

    @Accessors(PUBLIC_GETTER)
    boolean isRunning

    Thread thread

    val MessageReader messageReader
    
    val ExecutorService executorService
    
    Consumer<Message> callback
    
    Future<?> future
    
    override setOnRead((Message, String)=>void onRead) {
        super.setOnRead(onRead)
        messageReader.onRead = onRead
    }

    override setOnError(Consumer<Throwable> onError) {
        super.setOnError(onError)
        messageReader.onError = onError
    }

    override listen(Consumer<Message> callback) {
        this.callback = callback
        future = executorService.submit(this)
    }

    def void join() {
        future.get
    }

    override void close() {
        messageReader.close
        thread?.interrupt()
    }
    
    override run() {
        if (isRunning)
            throw new IllegalStateException("The reader is already running.")
        thread = Thread.currentThread
        isRunning = true
        try {
            messageReader.listen(callback)
        } catch (ClosedChannelException e) {
            // The channel whose stream has been listened was closed
        } catch (Exception e) {
            fireError(e)
        } finally {
            isRunning = false
            thread = null
        }
    }

}