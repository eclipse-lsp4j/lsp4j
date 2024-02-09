/******************************************************************************
 * Copyright (c) 2023 SonarSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 ******************************************************************************/
package org.eclipse.lsp4j.jsonrpc.test;

import java.io.IOException;
import java.nio.file.Path;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class PathTypeAdapter extends TypeAdapter<Path> {

	@Override
	public void write(JsonWriter out, Path value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else {
			out.value(value.toString());
		}
	}

	@Override
	public Path read(JsonReader in) throws IOException {
		var peek = in.peek();
		if (peek == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		return Path.of(in.nextString());
	}
}
