/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json

import java.nio.channels.ClosedChannelException
import java.util.concurrent.ExecutorService
import org.eclipse.lsp4j.jsonrpc.MessageConsumer
import org.eclipse.lsp4j.jsonrpc.MessageProducer
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.concurrent.Future
import java.util.logging.Logger
import java.util.logging.Level

@FinalFieldsConstructor
class ConcurrentMessageProcessor implements Runnable {
	
	public static final Logger LOG = Logger.getLogger(ConcurrentMessageProcessor.name);

    @Accessors(PUBLIC_GETTER)
    boolean isRunning

    val MessageProducer messageProducer
    val MessageConsumer messageConsumer
    
    def static Future<?> startProcessing(MessageProducer messageProducer, MessageConsumer messageConsumer, ExecutorService executorService) {
        val reader = new ConcurrentMessageProcessor(messageProducer, messageConsumer)
        return executorService.submit(reader)
    }
    
    override run() {
        if (isRunning)
            throw new IllegalStateException("The reader is already running.")
        isRunning = true
        try {
            messageProducer.listen(messageConsumer)
        } catch (ClosedChannelException e) {
            // The channel whose stream has been listened was closed
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.message, e)
        } finally {
            isRunning = false
        }
    }
				
}
