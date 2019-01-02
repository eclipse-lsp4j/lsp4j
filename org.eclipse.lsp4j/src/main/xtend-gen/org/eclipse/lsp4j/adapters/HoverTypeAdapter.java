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

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.List;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.generator.TypeAdapterImpl;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * A type adapter for the Hover protocol type.
 */
@TypeAdapterImpl(Hover.class)
@SuppressWarnings("all")
public class HoverTypeAdapter extends TypeAdapter<Hover> {
  public static class Factory implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
      if (!Hover.class.isAssignableFrom(typeToken.getRawType())) {
      	return null;
      }
      return (TypeAdapter<T>) new HoverTypeAdapter(gson);
    }
  }
  
  private static final TypeToken<List<Either<String, MarkedString>>> LIST_STRING_MARKEDSTRING = new TypeToken<List<Either<String, MarkedString>>>() {
  };
  
  private static final TypeToken<Either<String, MarkedString>> STRING_MARKEDSTRING = new TypeToken<Either<String, MarkedString>>() {
  };
  
  protected Either<List<Either<String, MarkedString>>, MarkupContent> readContents(final JsonReader in) throws IOException {
    final JsonToken nextToken = in.peek();
    boolean _equals = Objects.equal(nextToken, JsonToken.STRING);
    if (_equals) {
      final List<Either<String, MarkedString>> value = CollectionLiterals.<Either<String, MarkedString>>newArrayList(Either.<String, MarkedString>forLeft(in.nextString()));
      return Either.<List<Either<String, MarkedString>>, MarkupContent>forLeft(value);
    } else {
      boolean _equals_1 = Objects.equal(nextToken, JsonToken.BEGIN_ARRAY);
      if (_equals_1) {
        final List<Either<String, MarkedString>> value_1 = this.gson.<List<Either<String, MarkedString>>>fromJson(in, HoverTypeAdapter.LIST_STRING_MARKEDSTRING.getType());
        return Either.<List<Either<String, MarkedString>>, MarkupContent>forLeft(value_1);
      } else {
        JsonElement _parse = new JsonParser().parse(in);
        final JsonObject object = ((JsonObject) _parse);
        boolean _has = object.has("language");
        if (_has) {
          final List<Either<String, MarkedString>> value_2 = CollectionLiterals.<Either<String, MarkedString>>newArrayList(Either.<String, MarkedString>forRight(this.gson.<MarkedString>fromJson(object, MarkedString.class)));
          return Either.<List<Either<String, MarkedString>>, MarkupContent>forLeft(value_2);
        } else {
          return Either.<List<Either<String, MarkedString>>, MarkupContent>forRight(this.gson.<MarkupContent>fromJson(object, MarkupContent.class));
        }
      }
    }
  }
  
  protected void writeContents(final JsonWriter out, final Either<List<Either<String, MarkedString>>, MarkupContent> contents) throws IOException {
    boolean _isLeft = contents.isLeft();
    if (_isLeft) {
      final List<Either<String, MarkedString>> list = contents.getLeft();
      int _size = list.size();
      boolean _equals = (_size == 1);
      if (_equals) {
        this.gson.toJson(list.get(0), HoverTypeAdapter.STRING_MARKEDSTRING.getType(), out);
      } else {
        this.gson.toJson(list, HoverTypeAdapter.LIST_STRING_MARKEDSTRING.getType(), out);
      }
    } else {
      this.gson.toJson(contents.getRight(), MarkupContent.class, out);
    }
  }
  
  private static final TypeToken<Either<List<Either<String, MarkedString>>, MarkupContent>> CONTENTS_TYPE_TOKEN = new TypeToken<Either<List<Either<String, MarkedString>>, MarkupContent>>() {};
  
  private final Gson gson;
  
  public HoverTypeAdapter(final Gson gson) {
    this.gson = gson;
  }
  
  public Hover read(final JsonReader in) throws IOException {
    JsonToken nextToken = in.peek();
    if (nextToken == JsonToken.NULL) {
    	return null;
    }
    
    Hover result = new Hover();
    in.beginObject();
    while (in.hasNext()) {
    	String name = in.nextName();
    	switch (name) {
    	case "contents":
    		result.setContents(readContents(in));
    		break;
    	case "range":
    		result.setRange(readRange(in));
    		break;
    	default:
    		in.skipValue();
    	}
    }
    in.endObject();
    return result;
  }
  
  protected Range readRange(final JsonReader in) throws IOException {
    return gson.fromJson(in, Range.class);
  }
  
  public void write(final JsonWriter out, final Hover value) throws IOException {
    if (value == null) {
    	out.nullValue();
    	return;
    }
    
    out.beginObject();
    out.name("contents");
    writeContents(out, value.getContents());
    out.name("range");
    writeRange(out, value.getRange());
    out.endObject();
  }
  
  protected void writeRange(final JsonWriter out, final Range value) throws IOException {
    gson.toJson(value, Range.class, out);
  }
}
