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

interface MessageWriter {

    def void setOnWrite((Message, String)=>void onWrite)

    def void setOnError(Consumer<Throwable> onError)    

    def void write(Message message)
    
    def void close()

}

abstract class AbstractMessageWriter implements MessageWriter {
    
    @Accessors(PUBLIC_SETTER)
    var (Message, String)=>void onWrite = []
    
    @Accessors(PUBLIC_SETTER)
    var Consumer<Throwable> onError = []

    protected def void fireWrite(Message message, String content) {
        onWrite.apply(message, content)
    }

    protected def void fireError(Throwable throwable) {
        onError.accept(throwable)
    }
    
}