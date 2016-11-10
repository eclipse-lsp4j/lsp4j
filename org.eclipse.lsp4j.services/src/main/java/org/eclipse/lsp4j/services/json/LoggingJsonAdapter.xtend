/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.json

import org.eclipse.lsp4j.services.LanguageServer
import org.eclipse.lsp4j.services.transport.trace.PrintMessageTracer
import java.io.PrintWriter

@Deprecated
class LoggingJsonAdapter extends LanguageServerToJsonAdapter {

    new(LanguageServer server) {
        super(server)
        messageTracer = new PrintMessageTracer(null, null)
    }
    
    def void setErrorLog(PrintWriter errorLog) {
        val messageTracer = messageTracer
        if (messageTracer instanceof PrintMessageTracer)
            messageTracer.errorLog = errorLog
    }
    
    def void setMessageLog(PrintWriter messageLog) {
        val messageTracer = messageTracer
        if (messageTracer instanceof PrintMessageTracer)
            messageTracer.messageLog = messageLog
    }

}
