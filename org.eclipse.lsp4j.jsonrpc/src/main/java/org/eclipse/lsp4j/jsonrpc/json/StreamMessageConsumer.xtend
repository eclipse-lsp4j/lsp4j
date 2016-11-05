/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json

import java.io.OutputStream
import java.nio.charset.StandardCharsets
import org.eclipse.lsp4j.jsonrpc.MessageConsumer
import org.eclipse.lsp4j.jsonrpc.messages.Message
import org.eclipse.xtend.lib.annotations.Accessors

class StreamMessageConsumer implements MessageConsumer, MessageConstants {

	@Accessors
    OutputStream output
    val String encoding
    val MessageJsonHandler jsonHandler

    val outputLock = new Object
    
    new(MessageJsonHandler jsonHandler) {
    	this(null, StandardCharsets.UTF_8.name, jsonHandler)
    }
    
    new(OutputStream output, MessageJsonHandler jsonHandler) {
    	this(output, StandardCharsets.UTF_8.name, jsonHandler)
    }
    
    new(OutputStream output, String encoding, MessageJsonHandler jsonHandler) {
        this.output = output
        this.encoding = encoding
		this.jsonHandler = jsonHandler    	    
    }
    
    override consume(Message message) {
        if (message.jsonrpc === null) {
            message.jsonrpc = JSONRPC_VERSION
        }

        val content = jsonHandler.serialize(message)
        val contentBytes = content.getBytes(encoding)
        val contentLength = contentBytes.length

        val header = contentLength.header
        val headerBytes = header.getBytes(StandardCharsets.US_ASCII)

        synchronized (outputLock) {
            output.write(headerBytes)
            output.write(contentBytes)
            output.flush()
        }
    }
    
    protected def String getHeader(int contentLength) {
        val headerBuilder = new StringBuilder
        headerBuilder.appendHeader(CONTENT_LENGTH_HEADER, contentLength).append(CRLF)
        if (encoding != 'UTF-8') {
            headerBuilder.appendHeader(CONTENT_TYPE_HEADER, JSON_MIME_TYPE)
            headerBuilder.append('; charset=').append(encoding).append(CRLF)
        }
        headerBuilder.append(CRLF)
        return headerBuilder.toString
    }
    
    protected def StringBuilder appendHeader(StringBuilder builder, String name, Object value) {
        builder.append(name).append(': ').append(value)
    }

}
