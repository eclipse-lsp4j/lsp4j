/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.transport.trace

import org.eclipse.lsp4j.Message
import org.eclipse.lsp4j.NotificationMessage
import org.eclipse.lsp4j.RequestMessage
import org.eclipse.lsp4j.ResponseMessage
import java.io.PrintWriter
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors(PUBLIC_SETTER)
class PrintMessageTracer implements MessageTracer {

    PrintWriter errorLog

    PrintWriter messageLog
    
    new() {
        this(null, null)
    }

    new(PrintWriter errorLog, PrintWriter messageLog) {
        this.errorLog = errorLog
        this.messageLog = messageLog
    }

    override void onError(String message, Throwable throwable) {
        if (errorLog !== null) {
            if (throwable !== null)
                throwable.printStackTrace(errorLog)
            else if (message !== null)
                errorLog.println(message)
            errorLog.flush()
        }
    }

    override onRead(Message message, String json) {
        if (messageLog !== null) {
            switch message {
                RequestMessage:
                    messageLog.println('Client Request:\n\t' + json)
                NotificationMessage:
                    messageLog.println('Client Notification:\n\t' + json)
            }
            messageLog.flush()
        }
    }

    override void onWrite(Message message, String json) {
        if (messageLog !== null) {
            switch message {
                ResponseMessage:
                    messageLog.println('Server Response:\n\t' + json)
                NotificationMessage:
                    messageLog.println('Server Notification:\n\t' + json)
            }
            messageLog.flush()
        }
    }

}
