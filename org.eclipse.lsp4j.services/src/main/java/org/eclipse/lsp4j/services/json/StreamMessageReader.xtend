/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.json

import org.eclipse.lsp4j.Message
import org.eclipse.lsp4j.services.transport.io.AbstractMessageReader
import java.io.InputStream
import java.io.InterruptedIOException
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class StreamMessageReader extends AbstractMessageReader implements MessageConstants {

    private static class Headers {
        int contentLength = -1
        String charset = StandardCharsets.UTF_8.name
    }

    val InputStream input

    val MessageJsonHandler jsonHandler

    (Message)=>void callback

    boolean keepRunning

    override listen(Consumer<Message> callback) {
        this.callback = callback

        keepRunning = true
        var StringBuilder headerBuilder
        var StringBuilder debugBuilder
        var newLine = false
        var headers = new Headers
        while (keepRunning) {
            try {
                val c = input.read
                if (c == -1)
                    // End of input stream has been reached
                    keepRunning = false
                else {
                    if (debugBuilder === null)
                        debugBuilder = new StringBuilder
                    debugBuilder.append(c as char)
                    if (c.matches('\n')) {
                        if (newLine) {
                            // Two consecutive newlines have been read, which signals the start of the message content
                            if (headers.contentLength < 0) {
                                fireError(new IllegalStateException(
                                    'Missing header ' + CONTENT_LENGTH_HEADER + ' in input "' + debugBuilder + '"'
                                ))
                            } else {
                                val result = handleMessage(input, headers)
                                if (!result)
                                    keepRunning = false
                                newLine = false
                            }
                            headers = new Headers
                            debugBuilder = null
                        } else if (headerBuilder !== null) {
                            // A single newline ends a header line
                            parseHeader(headerBuilder.toString, headers)
                            headerBuilder = null
                        }
                        newLine = true
                    } else if (!c.matches('\r')) {
                        // Add the input to the current header line
                        if (headerBuilder === null)
                            headerBuilder = new StringBuilder
                        headerBuilder.append(c as char)
                        newLine = false
                    }
                }
            } catch (InterruptedIOException exception) {
                // The read operation has been interrupted
            }
        }
    }

    protected def matches(int c1, char c2) {
        c1 == c2
    }

    protected def void parseHeader(String line, Headers headers) {
        val sepIndex = line.indexOf(':')
        if (sepIndex >= 0) {
            val key = line.substring(0, sepIndex).trim
            switch key {
                case CONTENT_LENGTH_HEADER:
                    try {
                        headers.contentLength = Integer.parseInt(line.substring(sepIndex + 1).trim)
                    } catch (NumberFormatException e) {
                        fireError(e)
                    }
                case CONTENT_TYPE_HEADER: {
                    val charsetIndex = line.indexOf('charset=')
                    if (charsetIndex >= 0)
                        headers.charset = line.substring(charsetIndex + 8).trim
                }
            }
        }
    }

    protected def boolean handleMessage(InputStream input, Headers headers) {
        try {
            val contentLength = headers.contentLength
            val buffer = newByteArrayOfSize(contentLength)
            var bytesRead = 0

            while (bytesRead < contentLength) {
                val readResult = input.read(buffer, bytesRead, contentLength - bytesRead)
                if (readResult == -1)
                    return false
                bytesRead += readResult
            }

            val content = new String(buffer, headers.charset)
            val message = jsonHandler.parseMessage(content)
            fireRead(message, content)
            callback.apply(message)
        } catch (UnsupportedEncodingException e) {
            fireError(e)
        } catch (InvalidMessageException e) {
            fireError(e)
        }
        return true
    }

    override close() {
        keepRunning = false
    }

}