/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.services.transport.io

import org.eclipse.lsp4j.Message
import java.util.function.Consumer
import org.eclipse.xtend.lib.annotations.Accessors

interface MessageReader {

    def void setOnRead((Message, String)=>void onRead)

    def void setOnError(Consumer<Throwable> onError)

    def void listen(Consumer<Message> callback)

    def void close()

}

@Accessors(PUBLIC_SETTER)
abstract class AbstractMessageReader implements MessageReader {

    var (Message, String)=>void onRead = []

    var Consumer<Throwable> onError = []

    protected def void fireRead(Message message, String content) {
        onRead.apply(message, content)
    }

    protected def void fireError(Throwable throwable) {
        onError.accept(throwable)
    }

}
