/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.json

import org.eclipse.lsp4j.services.transport.client.LanguageClientEndpoint
import org.eclipse.lsp4j.services.transport.io.ConcurrentMessageReader
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * A language server that delegates to an input and an output stream through the JSON-based protocol.
 */
@Deprecated
class JsonBasedLanguageServer extends LanguageClientEndpoint {
    
    ConcurrentMessageReader reader
    
    val MessageJsonHandler jsonHandler
    
    @Accessors(PUBLIC_GETTER)
    LanguageServerProtocol protocol
	
	new() {
		this(new MessageJsonHandler)
	}
	
	new(MessageJsonHandler jsonHandler) {
		this(jsonHandler, Executors.newCachedThreadPool)
	}
	
	new(MessageJsonHandler jsonHandler, ExecutorService executorService) {
		super(executorService)
		jsonHandler.methodResolver = this
		this.jsonHandler = jsonHandler
		protocol = new LanguageServerProtocol(null, null)
		messageTracer = protocol
	}
    
    def void connect(InputStream input, OutputStream output) {
        val reader = new StreamMessageReader(input, jsonHandler)
        this.reader = new ConcurrentMessageReader(reader, executorService)
        val writer = new StreamMessageWriter(output, jsonHandler)
        connect(this.reader, writer)
    }
    
    def void join() {
        reader.join
    }
    
    def isActive() {
        reader.isRunning
    }
	
}