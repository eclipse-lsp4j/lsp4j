/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.jsonrpc.MessageProducer;
import org.eclipse.lsp4j.jsonrpc.messages.Message;

public class StreamMessageProducer implements MessageProducer, Closeable, MessageConstants {

    private static class Headers {
        int contentLength = -1;
        String charset = StandardCharsets.UTF_8.name();
    }
    
    private final MessageJsonHandler jsonHandler;
    
    private InputStream input;
    
    private MessageConsumer callback;
    private boolean keepRunning;
    
    public StreamMessageProducer(InputStream input, MessageJsonHandler jsonHandler) {
    	this.input = input;
    	this.jsonHandler = jsonHandler;
    }
    
    public InputStream getInput() {
    	return input;
    }
    
    public void setInput(InputStream input) {
    	this.input = input;
    }
    
    @Override
    public void listen(MessageConsumer callback) {
        this.callback = callback;
        keepRunning = true;
        StringBuilder headerBuilder = null;
        StringBuilder debugBuilder = null;
        boolean newLine = false;
        Headers headers = new Headers();
        while (keepRunning) {
            try {
                int c = input.read();
                if (c == -1)
                    // End of input stream has been reached
                    keepRunning = false;
                else {
                    if (debugBuilder == null)
                        debugBuilder = new StringBuilder();
                    debugBuilder.append((char) c);
                    if (c == '\n') {
                        if (newLine) {
                            // Two consecutive newlines have been read, which signals the start of the message content
                            if (headers.contentLength < 0) {
                                fireError(new IllegalStateException(
                                    "Missing header " + CONTENT_LENGTH_HEADER + " in input \"" + debugBuilder + "\""
                                ));
                            } else {
                            	boolean result = handleMessage(input, headers);
                                if (!result)
                                    keepRunning = false;
                                newLine = false;
                            }
                            headers = new Headers();
                            debugBuilder = null;
                        } else if (headerBuilder != null) {
                            // A single newline ends a header line
                            parseHeader(headerBuilder.toString(), headers);
                            headerBuilder = null;
                        }
                        newLine = true;
                    } else if (c != '\r') {
                        // Add the input to the current header line
                        if (headerBuilder == null)
                            headerBuilder = new StringBuilder();
                        headerBuilder.append((char) c);
                        newLine = false;
                    }
                }
            } catch (InterruptedIOException e) {
                // The read operation has been interrupted
            } catch (ClosedChannelException e) {
                // The channel whose stream has been listened was closed
            } catch (IOException e) {
            	throw new RuntimeException(e);
            }
        }
    }
	
	protected void fireError(Throwable exception) {
		Logger.getLogger(getClass().getName()).log(Level.SEVERE, exception.getMessage(), exception);
	}
	
    protected void parseHeader(String line, Headers headers) {
        int sepIndex = line.indexOf(':');
        if (sepIndex >= 0) {
            String key = line.substring(0, sepIndex).trim();
            switch (key) {
                case CONTENT_LENGTH_HEADER:
                    try {
                        headers.contentLength = Integer.parseInt(line.substring(sepIndex + 1).trim());
                    } catch (NumberFormatException e) {
                        fireError(e);
                    }
                    break;
                case CONTENT_TYPE_HEADER: {
                    int charsetIndex = line.indexOf("charset=");
                    if (charsetIndex >= 0)
                        headers.charset = line.substring(charsetIndex + 8).trim();
                    break;
                }
            }
        }
    }

    protected boolean handleMessage(InputStream input, Headers headers) throws IOException {
        try {
            int contentLength = headers.contentLength;
            byte[] buffer = new byte[contentLength];
            int bytesRead = 0;
            
            while (bytesRead < contentLength) {
                int readResult = input.read(buffer, bytesRead, contentLength - bytesRead);
                if (readResult == -1)
                    return false;
                bytesRead += readResult;
            }
            
            String content = new String(buffer, headers.charset);
            Message message = jsonHandler.parseMessage(content);
            callback.consume(message);
        } catch (UnsupportedEncodingException | InvalidMessageException e) {
            fireError(e);
        }
        return true;
    }
    
    @Override
    public void close() {
        keepRunning = false;
    }

}
