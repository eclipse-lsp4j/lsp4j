/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.json

import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.lsp4j.services.transport.io.ConcurrentMessageReader
import org.eclipse.lsp4j.services.transport.server.LanguageServerEndpoint
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * Wraps a language server implementation and adapts it to the JSON-based protocol.
 */
@Deprecated
class LanguageServerToJsonAdapter extends LanguageServerEndpoint {
    
    ConcurrentMessageReader reader
    
    val MessageJsonHandler jsonHandler
    
    @Accessors(PUBLIC_GETTER)
    val LanguageServerProtocol protocol
	
	new(LanguageServer delegate) {
		this(delegate, new MessageJsonHandler)
	}
	
	new(LanguageServer delegate, MessageJsonHandler jsonHandler) {
		this(delegate, jsonHandler, Executors.newCachedThreadPool)
	}
	
	new(LanguageServer delegate, MessageJsonHandler jsonHandler, ExecutorService executorService) {
		super(delegate, executorService)
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