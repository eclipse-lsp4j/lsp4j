/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.transport

import org.eclipse.lsp4j.Message
import org.eclipse.lsp4j.RequestMessage
import org.eclipse.lsp4j.ResponseErrorCode
import org.eclipse.lsp4j.builders.NotificationMessageBuilder
import org.eclipse.lsp4j.builders.RequestMessageBuilder
import org.eclipse.lsp4j.builders.ResponseErrorBuilder
import org.eclipse.lsp4j.builders.ResponseMessageBuilder
import org.eclipse.lsp4j.services.transport.io.MessageReader
import org.eclipse.lsp4j.services.transport.io.MessageWriter
import org.eclipse.lsp4j.services.transport.trace.MessageTracer
import java.io.IOException
import java.util.concurrent.CancellationException
import java.util.concurrent.ExecutorService
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

interface LanguageEndpoint {

    def void connect(MessageReader reader, MessageWriter writer)

    def void close()

}

@FinalFieldsConstructor
abstract class AbstractLanguageEndpoint implements LanguageEndpoint, Consumer<Message>, MessageMethods {

    @Accessors
    MessageReader reader

    @Accessors
    MessageWriter writer

    @Accessors(PUBLIC_SETTER, PROTECTED_GETTER)
    MessageTracer messageTracer

    @Accessors(PROTECTED_GETTER)
    val ExecutorService executorService

    override synchronized void connect(MessageReader reader, MessageWriter writer) {
        writer.onError = [logError(it)]
        writer.onWrite = [logOutgoingMessage($0, $1)]

        reader.onError = [logError(it)]
        reader.onRead = [logIncomingMessage($0, $1)]

        this.reader = reader
        this.writer = writer

        reader.listen [ message |
            handleMessage(message)
        ]
    }

    protected def void handleMessage(Message message) {
        try {
            accept(message)
        } catch (Exception e) {
            handleRequestError(message, e)
        }
    }

    protected def void handleRequestError(Message message, Throwable e) {
        val requestId = if(message instanceof RequestMessage) message.id
        switch e {
            CancellationException,
            InterruptedException: {
                return
            }
            InvalidMessageException: {
                logError(e)
                sendResponseError(e.requestId ?: requestId, e.message, e.errorCode)
            }
            default: {
                logError(e)
                sendResponseError(requestId, e.class.name + ":" + e.message, ResponseErrorCode.InternalError)
            }
        }
    }

    protected def void sendMessage(Message message) {
        try {
            writer?.write(message)
        } catch (IOException e) {
            logError(e)
        }
    }

    protected def sendRequest(String id, String method, Object params) {
        sendMessage(new RequestMessageBuilder [
            id(id)
            method(method)
            params(params)
        ].build)
    }

    protected def sendNotification(String method, Object params) {
        sendMessage(new NotificationMessageBuilder [
            method(method)
            params(params)
        ].build)
    }

    protected def sendResponse(String id, Object result) {
        sendMessage(new ResponseMessageBuilder [
            id(id)
            result(result)
        ].build)
    }

    protected def sendResponseError(String id, String message, ResponseErrorCode code, Object data) {
        sendMessage(new ResponseMessageBuilder [
            id(id)
            error(new ResponseErrorBuilder [
                message(message)
                code(code)
                data(data)
            ].build)
        ].build)
    }

    protected def sendResponseError(String requestId, String message, ResponseErrorCode code) {
        sendResponseError(requestId, message, code, null)
    }

    override synchronized void close() {
        try {
            try {
                reader?.close
            } finally {
                writer?.close
            }
        } finally {
            executorService?.shutdownNow
        }
    }

    protected def void logIncomingMessage(Message message, String json) {
        messageTracer?.onRead(message, json)
    }

    protected def void logOutgoingMessage(Message message, String json) {
        messageTracer?.onWrite(message, json)
    }

    protected def void logError(Throwable throwable) {
        logError(throwable.message, throwable)
    }

    protected def void logError(String message, Throwable throwable) {
        messageTracer?.onError(message, throwable)
    }

}
