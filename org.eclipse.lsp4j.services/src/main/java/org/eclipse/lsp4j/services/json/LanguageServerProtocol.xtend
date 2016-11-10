/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.json

import org.eclipse.lsp4j.Message
import org.eclipse.lsp4j.RequestMessage
import org.eclipse.lsp4j.ResponseErrorCode
import org.eclipse.lsp4j.ResponseMessage
import org.eclipse.lsp4j.impl.MessageImpl
import org.eclipse.lsp4j.impl.ResponseErrorImpl
import org.eclipse.lsp4j.impl.ResponseMessageImpl
import java.io.IOException
import java.io.InputStream
import java.io.InterruptedIOException
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.nio.channels.ClosedChannelException
import java.util.List
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.lsp4j.services.transport.trace.MessageTracer

@Deprecated
@FinalFieldsConstructor
class LanguageServerProtocol implements Consumer<Message>, MessageTracer {
	
	public static val JSONRPC_VERSION = '2.0'
	
	public static val H_CONTENT_LENGTH = 'Content-Length'
	public static val H_CONTENT_TYPE = 'Content-Type'
	
	static val CT_JSON = 'application/json'
	
	private static class Headers {
		int contentLength = -1
		String charset = 'UTF-8'
	}
	
	val MessageJsonHandler jsonHandler
	
	val Consumer<Message> incomingMessageAcceptor
	
	@Accessors
	val IOHandler ioHandler = new IOHandler(this)
	
	@Accessors
	String outputEncoding = 'UTF-8'
	
	val List<(String, Throwable)=>void> errorListeners = newArrayList
	val List<(Message, String)=>void> incomingMessageListeners = newArrayList
	val List<(Message, String)=>void> outgoingMessageListeners = newArrayList
	
	def void addErrorListener((String, Throwable)=>void listener) {
		errorListeners.add(listener)
	}
	
	def void addIncomingMessageListener((Message, String)=>void listener) {
		incomingMessageListeners.add(listener)
	}
	
	def void addOutgoingMessageListener((Message, String)=>void listener) {
		outgoingMessageListeners.add(listener)
	}
	
	protected def void handleMessage(String content) throws IOException {
		var String requestId
		try {
			val message = jsonHandler.parseMessage(content)
			if (message instanceof RequestMessage)
				requestId = message.id
			logIncomingMessage(message, content)
			
			incomingMessageAcceptor.accept(message)
			
		} catch (InvalidMessageException e) {
			logError(e)
			accept(createErrorResponse(e.message, e.errorCode, e.requestId))
		} catch (Exception e) {
			logError(e)
			accept(createErrorResponse(e.message, ResponseErrorCode.InternalError, requestId))
		}
	}
	
	protected def void logIncomingMessage(Message message, String json) {
		for (l : incomingMessageListeners) {
			l.apply(message, json)
		}
	}

    override onError(String message, Throwable throwable) {
        logError(message, throwable)
    }
    
    override onRead(Message message, String json) {
        logIncomingMessage(message, json)
    }
    
    override onWrite(Message message, String json) {
        logOutgoingMessage(message, json)
    }
	
	override accept(Message message) {
		try {
			if (message.jsonrpc === null && message instanceof MessageImpl)
				(message as MessageImpl).jsonrpc = JSONRPC_VERSION
			val content = jsonHandler.serialize(message)
			ioHandler.send(content, outputEncoding)
			logOutgoingMessage(message, content)
		} catch (IOException e) {
			logError(e)
		}
	}
	
	protected def ResponseMessage createErrorResponse(String errorMessage, ResponseErrorCode errorCode, String requestId) {
		val response = new ResponseMessageImpl
		response.jsonrpc = JSONRPC_VERSION
		if (requestId !== null)
			response.id = requestId
		response.error = new ResponseErrorImpl => [
			message = errorMessage
			code = errorCode
		]
		return response
	}
	
	protected def void logOutgoingMessage(Message message, String json) {
		for (l : outgoingMessageListeners) {
			l.apply(message, json)
		}
	}
	
	protected def logError(Throwable throwable) {
		logError(throwable.message, throwable)
	}
	
	protected def logError(String message, Throwable throwable) {
		for (l : errorListeners) {
			l.apply(message, throwable)
		}
	}
	
	static class IOHandler implements Runnable {
		
		val LanguageServerProtocol protocol
		
		@Accessors(PUBLIC_SETTER)
		InputStream input
		
		@Accessors(PUBLIC_SETTER)
		OutputStream output
		
		val outputLock = new Object
		
		@Accessors(PUBLIC_GETTER)
		boolean isRunning
		
		boolean keepRunning
		
		Thread thread
		
		protected new(LanguageServerProtocol protocol) {
			this.protocol = protocol
		}
		
		override run() {
			if (isRunning)
				throw new IllegalStateException("The I/O handler is already running.")
			thread = Thread.currentThread
			isRunning = true
			try {
				run(input)
			} catch (ClosedChannelException e) {
				// The channel whose stream has been listened was closed
			} catch (Exception e) {
				protocol.logError(e)
			} finally {
				isRunning = false
				thread = null
			}
		}
		
		def void stop() {
			keepRunning = false
			thread?.interrupt()
		}
		
		protected def run(InputStream input) throws IOException {
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
									protocol.logError(new IllegalStateException(
										'Missing header ' + H_CONTENT_LENGTH + ' in input "' + debugBuilder + '"'
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
		
		private def matches(int c1, char c2) {
			c1 == c2
		}
		
		protected def void parseHeader(String line, Headers headers) {
			val sepIndex = line.indexOf(':')
			if (sepIndex >= 0) {
				val key = line.substring(0, sepIndex).trim
				switch key {
					case H_CONTENT_LENGTH:
						try {
							headers.contentLength = Integer.parseInt(line.substring(sepIndex + 1).trim)
						} catch (NumberFormatException e) {
							protocol.logError(e)
						}
					case H_CONTENT_TYPE: {
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
				
				protocol.handleMessage(new String(buffer, headers.charset))
			} catch (UnsupportedEncodingException e) {
				protocol.logError(e)
			}
			return true
		}
		
		def void send(String content, String charset) {
			send(output, content, charset)
		}
		
		protected def void send(OutputStream output, String content, String charset) {
			val responseBytes = content.getBytes(charset)
			val headerBuilder = new StringBuilder
			headerBuilder.append(H_CONTENT_LENGTH).append(': ').append(responseBytes.length).append('\r\n')
			if (charset !== 'UTF-8')
				headerBuilder.append(H_CONTENT_TYPE).append(': ').append(CT_JSON).append('; charset=').append(charset).append('\r\n')
			headerBuilder.append('\r\n')
			synchronized (outputLock) {
				output.write(headerBuilder.toString.bytes)
				output.write(responseBytes)
				output.flush()
			}
		}
		
	}
	
}
