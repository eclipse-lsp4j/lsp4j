/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.adapters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A type adapter for the Hover protocol type.
 */
public class HoverTypeAdapter extends TypeAdapter<Hover> {
	
	public static class Factory implements TypeAdapterFactory {

		@SuppressWarnings("unchecked")
		@Override
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			if (!Hover.class.isAssignableFrom(typeToken.getRawType())) {
				return null;
			}
			return (TypeAdapter<T>) new HoverTypeAdapter(gson);
		}

	}
	
	private final static TypeToken<List<Either<String, MarkedString>>> LIST_STRING_MARKEDSTRING
			= new TypeToken<List<Either<String, MarkedString>>>() {};
	private final static TypeToken<Either<String, MarkedString>> STRING_MARKEDSTRING
			= new TypeToken<Either<String, MarkedString>>() {};
	
	private final Gson gson;
	
	public HoverTypeAdapter(Gson gson) {
		this.gson = gson;
	}

	@Override
	public Hover read(JsonReader in) throws IOException {
		JsonToken nextToken = in.peek();
		if (nextToken == JsonToken.NULL) {
			return null;
		}
		
		Hover hover = new Hover();
		in.beginObject();
		while (in.hasNext()) {
			String name = in.nextName();
			switch (name) {
			
			case "contents":
				hover.setContents(readContents(in));
				break;
			
			case "range":
				hover.setRange(readRange(in));
				break;
			
			default:
				in.skipValue();
			}
		}
		in.endObject();
		return hover;
	}
	
	protected Either<List<Either<String, MarkedString>>, MarkupContent> readContents(JsonReader in) throws IOException {
		JsonToken nextToken = in.peek();
		if (nextToken == JsonToken.STRING) {
			List<Either<String, MarkedString>> value = new ArrayList<>(1);
			value.add(Either.forLeft(in.nextString()));
			return Either.forLeft(value);
		} else if (nextToken == JsonToken.BEGIN_ARRAY) {
			List<Either<String, MarkedString>> value = gson.fromJson(in, LIST_STRING_MARKEDSTRING.getType());
			return Either.forLeft(value);
		} else {
			JsonObject object = (JsonObject) new JsonParser().parse(in);
			if (object.has("language")) {
				List<Either<String, MarkedString>> value = new ArrayList<>(1);
				value.add(Either.forRight(gson.fromJson(object, MarkedString.class)));
				return Either.forLeft(value);
			} else {
				return Either.forRight(gson.fromJson(object, MarkupContent.class));
			}
		}
	}
	
	protected Range readRange(JsonReader in) throws IOException {
		return gson.fromJson(in, Range.class);
	}

	@Override
	public void write(JsonWriter out, Hover hover) throws IOException {
		if (hover == null) {
			out.nullValue();
			return;
		}
		
		out.beginObject();
		if (hover.getContents() != null) {
			writeContents(out, hover.getContents());
		}
		
		if (hover.getRange() != null) {
			writeRange(out, hover.getRange());
		}
		out.endObject();
	}

	protected void writeContents(JsonWriter out, Either<List<Either<String, MarkedString>>, MarkupContent> contents) throws IOException {
		out.name("contents");
		if (contents.isLeft()) {
			List<Either<String, MarkedString>> list = contents.getLeft();
			if (list.size() == 1) {
				gson.toJson(list.get(0), STRING_MARKEDSTRING.getType(), out);
			} else {
				gson.toJson(list, LIST_STRING_MARKEDSTRING.getType(), out);
			}
		} else {
			gson.toJson(contents.getRight(), MarkupContent.class, out);
		}
	}

	protected void writeRange(JsonWriter out, Range range) throws IOException {
		out.name("range");
		gson.toJson(range, Range.class, out);
	}
	

}
