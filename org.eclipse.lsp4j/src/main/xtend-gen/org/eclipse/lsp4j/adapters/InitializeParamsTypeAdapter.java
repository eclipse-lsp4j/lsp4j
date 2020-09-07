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
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.List;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.ClientInfo;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.generator.TypeAdapterImpl;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

/**
 * A type adapter for the InitializeParams protocol type.
 */
@TypeAdapterImpl(InitializeParams.class)
@SuppressWarnings("all")
public class InitializeParamsTypeAdapter extends TypeAdapter<InitializeParams> {
  public static class Factory implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
      if (!InitializeParams.class.isAssignableFrom(typeToken.getRawType())) {
      	return null;
      }
      return (TypeAdapter<T>) new InitializeParamsTypeAdapter(gson);
    }
  }
  
  protected Object readInitializationOptions(final JsonReader in) throws IOException {
    return this.gson.<JsonElement>getAdapter(JsonElement.class).read(in);
  }
  
  protected void writeProcessId(final JsonWriter out, final Integer value) throws IOException {
    if ((value == null)) {
      final boolean previousSerializeNulls = out.getSerializeNulls();
      out.setSerializeNulls(true);
      out.nullValue();
      out.setSerializeNulls(previousSerializeNulls);
    } else {
      out.value(value);
    }
  }
  
  protected void writeRootUri(final JsonWriter out, final String value) throws IOException {
    if ((value == null)) {
      final boolean previousSerializeNulls = out.getSerializeNulls();
      out.setSerializeNulls(true);
      out.nullValue();
      out.setSerializeNulls(previousSerializeNulls);
    } else {
      out.value(value);
    }
  }
  
  private static final TypeToken<Either<String, Number>> WORKDONETOKEN_TYPE_TOKEN = new TypeToken<Either<String, Number>>() {};
  
  private static final TypeToken<List<WorkspaceFolder>> WORKSPACEFOLDERS_TYPE_TOKEN = new TypeToken<List<WorkspaceFolder>>() {};
  
  private final Gson gson;
  
  public InitializeParamsTypeAdapter(final Gson gson) {
    this.gson = gson;
  }
  
  public InitializeParams read(final JsonReader in) throws IOException {
    JsonToken nextToken = in.peek();
    if (nextToken == JsonToken.NULL) {
    	return null;
    }
    
    InitializeParams result = new InitializeParams();
    in.beginObject();
    while (in.hasNext()) {
    	String name = in.nextName();
    	switch (name) {
    	case "workDoneToken":
    		result.setWorkDoneToken(readWorkDoneToken(in));
    		break;
    	case "processId":
    		result.setProcessId(readProcessId(in));
    		break;
    	case "rootPath":
    		result.setRootPath(readRootPath(in));
    		break;
    	case "rootUri":
    		result.setRootUri(readRootUri(in));
    		break;
    	case "initializationOptions":
    		result.setInitializationOptions(readInitializationOptions(in));
    		break;
    	case "capabilities":
    		result.setCapabilities(readCapabilities(in));
    		break;
    	case "clientName":
    		result.setClientName(readClientName(in));
    		break;
    	case "clientInfo":
    		result.setClientInfo(readClientInfo(in));
    		break;
    	case "trace":
    		result.setTrace(readTrace(in));
    		break;
    	case "workspaceFolders":
    		result.setWorkspaceFolders(readWorkspaceFolders(in));
    		break;
    	default:
    		in.skipValue();
    	}
    }
    in.endObject();
    return result;
  }
  
  protected Either<String, Number> readWorkDoneToken(final JsonReader in) throws IOException {
    return gson.fromJson(in, WORKDONETOKEN_TYPE_TOKEN.getType());
  }
  
  protected Integer readProcessId(final JsonReader in) throws IOException {
    return gson.fromJson(in, Integer.class);
  }
  
  protected String readRootPath(final JsonReader in) throws IOException {
    return gson.fromJson(in, String.class);
  }
  
  protected String readRootUri(final JsonReader in) throws IOException {
    return gson.fromJson(in, String.class);
  }
  
  protected ClientCapabilities readCapabilities(final JsonReader in) throws IOException {
    return gson.fromJson(in, ClientCapabilities.class);
  }
  
  protected String readClientName(final JsonReader in) throws IOException {
    return gson.fromJson(in, String.class);
  }
  
  protected ClientInfo readClientInfo(final JsonReader in) throws IOException {
    return gson.fromJson(in, ClientInfo.class);
  }
  
  protected String readTrace(final JsonReader in) throws IOException {
    return gson.fromJson(in, String.class);
  }
  
  protected List<WorkspaceFolder> readWorkspaceFolders(final JsonReader in) throws IOException {
    return gson.fromJson(in, WORKSPACEFOLDERS_TYPE_TOKEN.getType());
  }
  
  public void write(final JsonWriter out, final InitializeParams value) throws IOException {
    if (value == null) {
    	out.nullValue();
    	return;
    }
    
    out.beginObject();
    out.name("workDoneToken");
    writeWorkDoneToken(out, value.getWorkDoneToken());
    out.name("processId");
    writeProcessId(out, value.getProcessId());
    out.name("rootPath");
    writeRootPath(out, value.getRootPath());
    out.name("rootUri");
    writeRootUri(out, value.getRootUri());
    out.name("initializationOptions");
    writeInitializationOptions(out, value.getInitializationOptions());
    out.name("capabilities");
    writeCapabilities(out, value.getCapabilities());
    out.name("clientName");
    writeClientName(out, value.getClientName());
    out.name("clientInfo");
    writeClientInfo(out, value.getClientInfo());
    out.name("trace");
    writeTrace(out, value.getTrace());
    out.name("workspaceFolders");
    writeWorkspaceFolders(out, value.getWorkspaceFolders());
    out.endObject();
  }
  
  protected void writeWorkDoneToken(final JsonWriter out, final Either<String, Number> value) throws IOException {
    gson.toJson(value, WORKDONETOKEN_TYPE_TOKEN.getType(), out);
  }
  
  protected void writeRootPath(final JsonWriter out, final String value) throws IOException {
    out.value(value);
  }
  
  protected void writeInitializationOptions(final JsonWriter out, final Object value) throws IOException {
    if (value == null)
    	out.nullValue();
    else
    	gson.toJson(value, value.getClass(), out);
  }
  
  protected void writeCapabilities(final JsonWriter out, final ClientCapabilities value) throws IOException {
    gson.toJson(value, ClientCapabilities.class, out);
  }
  
  protected void writeClientName(final JsonWriter out, final String value) throws IOException {
    out.value(value);
  }
  
  protected void writeClientInfo(final JsonWriter out, final ClientInfo value) throws IOException {
    gson.toJson(value, ClientInfo.class, out);
  }
  
  protected void writeTrace(final JsonWriter out, final String value) throws IOException {
    out.value(value);
  }
  
  protected void writeWorkspaceFolders(final JsonWriter out, final List<WorkspaceFolder> value) throws IOException {
    gson.toJson(value, WORKSPACEFOLDERS_TYPE_TOKEN.getType(), out);
  }
}
