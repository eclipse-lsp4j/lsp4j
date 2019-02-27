/**
 * Copyright (c) 2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.generator.TypeAdapterImpl;

/**
 * A type adapter for the VersionedTextDocumentIdentifier protocol type.
 */
@TypeAdapterImpl(VersionedTextDocumentIdentifier.class)
@SuppressWarnings("all")
public class VersionedTextDocumentIdentifierTypeAdapter extends TypeAdapter<VersionedTextDocumentIdentifier> {
  public static class Factory implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
      if (!VersionedTextDocumentIdentifier.class.isAssignableFrom(typeToken.getRawType())) {
      	return null;
      }
      return (TypeAdapter<T>) new VersionedTextDocumentIdentifierTypeAdapter(gson);
    }
  }
  
  protected void writeVersion(final JsonWriter out, final Integer value) throws IOException {
    if ((value == null)) {
      final boolean previousSerializeNulls = out.getSerializeNulls();
      out.setSerializeNulls(true);
      out.nullValue();
      out.setSerializeNulls(previousSerializeNulls);
    } else {
      out.value(value);
    }
  }
  
  private final Gson gson;
  
  public VersionedTextDocumentIdentifierTypeAdapter(final Gson gson) {
    this.gson = gson;
  }
  
  public VersionedTextDocumentIdentifier read(final JsonReader in) throws IOException {
    JsonToken nextToken = in.peek();
    if (nextToken == JsonToken.NULL) {
    	return null;
    }
    
    VersionedTextDocumentIdentifier result = new VersionedTextDocumentIdentifier();
    in.beginObject();
    while (in.hasNext()) {
    	String name = in.nextName();
    	switch (name) {
    	case "version":
    		result.setVersion(readVersion(in));
    		break;
    	case "uri":
    		result.setUri(readUri(in));
    		break;
    	default:
    		in.skipValue();
    	}
    }
    in.endObject();
    return result;
  }
  
  protected Integer readVersion(final JsonReader in) throws IOException {
    return gson.fromJson(in, Integer.class);
  }
  
  protected String readUri(final JsonReader in) throws IOException {
    return gson.fromJson(in, String.class);
  }
  
  public void write(final JsonWriter out, final VersionedTextDocumentIdentifier value) throws IOException {
    if (value == null) {
    	out.nullValue();
    	return;
    }
    
    out.beginObject();
    out.name("version");
    writeVersion(out, value.getVersion());
    out.name("uri");
    writeUri(out, value.getUri());
    out.endObject();
  }
  
  protected void writeUri(final JsonWriter out, final String value) throws IOException {
    out.value(value);
  }
}
