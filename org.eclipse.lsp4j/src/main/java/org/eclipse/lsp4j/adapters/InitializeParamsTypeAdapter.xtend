/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.adapters

import com.google.gson.internal.bind.TypeAdapters
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException
import org.eclipse.lsp4j.InitializeParams
import org.eclipse.lsp4j.generator.TypeAdapterImpl

/**
 * A type adapter for the InitializeParams protocol type.
 */
 @TypeAdapterImpl(InitializeParams)
class InitializeParamsTypeAdapter {
	
	protected def Object readInitializationOptions(JsonReader in) throws IOException {
		return TypeAdapters.JSON_ELEMENT.read(in);
	}
	
	protected def void writeProcessId(JsonWriter out, Integer value) throws IOException {
		if (value === null) {
			val previousSerializeNulls = out.serializeNulls
			out.serializeNulls = true
			out.nullValue()
			out.serializeNulls = previousSerializeNulls
		} else {
			out.value(value)
		}
	}
	
	protected def void writeRootUri(JsonWriter out, String value) throws IOException {
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