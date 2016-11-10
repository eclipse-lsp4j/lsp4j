/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.json

import org.eclipse.lsp4j.Message
import org.eclipse.lsp4j.impl.MessageImpl
import org.eclipse.lsp4j.services.transport.io.AbstractMessageWriter
import java.io.OutputStream
import java.nio.charset.StandardCharsets
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class StreamMessageWriter extends AbstractMessageWriter implements MessageConstants {

    val OutputStream output
    val String encoding
    val MessageJsonHandler jsonHandler

    val outputLock = new Object
    
    new(OutputStream output, MessageJsonHandler jsonHandler) {
        this(output, StandardCharsets.UTF_8.name, jsonHandler)
    }
    
    override write(Message message) {
        if (message.jsonrpc === null) {
            if (message instanceof MessageImpl) {
                message.jsonrpc = JSONRPC_VERSION
            }
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
        fireWrite(message, content)
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
    
    override close() {
        // do nothing
    }

}