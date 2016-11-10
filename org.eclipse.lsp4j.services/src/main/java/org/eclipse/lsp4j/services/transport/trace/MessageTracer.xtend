/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.transport.trace

import org.eclipse.lsp4j.Message
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Delegate

interface MessageTracer {

    def void onError(String message, Throwable throwable)

    def void onRead(Message message, String json)

    def void onWrite(Message message, String json)

}

class NullMessageTracer implements MessageTracer {

    override onError(String message, Throwable throwable) {
    }

    override onRead(Message message, String json) {
    }

    override onWrite(Message message, String json) {
    }

}

class DelegatingMessageTracer implements MessageTracer {

    @Delegate
    @Accessors
    val MessageTracer delegate

    new(MessageTracer messageTracer) {
        this.delegate = if (messageTracer === null)
            new NullMessageTracer
        else
            messageTracer
    }

}
