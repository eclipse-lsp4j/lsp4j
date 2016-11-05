/*******************************************************************************
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.json

interface MessageConstants {
    static val JSONRPC_VERSION = '2.0'
    static val CONTENT_LENGTH_HEADER = 'Content-Length'
    static val CONTENT_TYPE_HEADER = 'Content-Type'
    static val JSON_MIME_TYPE = 'application/json'
    static val CRLF = '\r\n'
}
