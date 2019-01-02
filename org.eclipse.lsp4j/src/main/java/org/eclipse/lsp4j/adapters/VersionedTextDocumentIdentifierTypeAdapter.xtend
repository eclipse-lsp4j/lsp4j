/******************************************************************************
 * Copyright (c) 2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.adapters

import com.google.gson.stream.JsonWriter
import java.io.IOException
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier
import org.eclipse.lsp4j.generator.TypeAdapterImpl

/**
 * A type adapter for the VersionedTextDocumentIdentifier protocol type.
 */
@TypeAdapterImpl(VersionedTextDocumentIdentifier)
class VersionedTextDocumentIdentifierTypeAdapter {

	protected def void writeVersion(JsonWriter out, Integer value) throws IOException {
		if (value === null) {
			val previousSerializeNulls = out.serializeNulls
			out.serializeNulls = true
			out.nullValue()
			out.serializeNulls = previousSerializeNulls
		} else {
			out.value(value)
		}
	}

}
