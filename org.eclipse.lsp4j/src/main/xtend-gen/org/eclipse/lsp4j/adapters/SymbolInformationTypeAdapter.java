package org.eclipse.lsp4j.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.generator.TypeAdapterImpl;

/**
 * A type adapter for the SymbolInformation protocol type.
 */
@TypeAdapterImpl(SymbolInformation.class)
@SuppressWarnings("all")
public class SymbolInformationTypeAdapter extends TypeAdapter<SymbolInformation> {
  public static class Factory implements TypeAdapterFactory {
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
      if (!SymbolInformation.class.isAssignableFrom(typeToken.getRawType())) {
      	return null;
      }
      return (TypeAdapter<T>) new SymbolInformationTypeAdapter(gson);
    }
  }
  
  protected void writeLocation(final JsonWriter out, final Location value) throws IOException {
    if ((value == null)) {
      out.nullValue();
      return;
    }
    out.beginObject();
    out.name("uri");
    out.value(value.getUri());
    out.name("range");
    this.writeRange(out, value.getRange());
    out.endObject();
  }
  
  protected void writeRange(final JsonWriter out, final Range value) throws IOException {
    if ((value == null)) {
      out.nullValue();
      return;
    }
    out.beginObject();
    out.name("start");
    this.writePosition(out, value.getStart());
    out.name("end");
    this.writePosition(out, value.getEnd());
    out.endObject();
  }
  
  protected void writePosition(final JsonWriter out, final Position value) throws IOException {
    if ((value == null)) {
      out.nullValue();
      return;
    }
    out.beginObject();
    out.name("line");
    out.value(value.getLine());
    out.name("character");
    out.value(value.getCharacter());
    out.endObject();
  }
  
  private final Gson gson;
  
  public SymbolInformationTypeAdapter(final Gson gson) {
    this.gson = gson;
  }
  
  public SymbolInformation read(final JsonReader in) throws IOException {
    JsonToken nextToken = in.peek();
    if (nextToken == JsonToken.NULL) {
    	return null;
    }
    
    SymbolInformation result = new SymbolInformation();
    in.beginObject();
    while (in.hasNext()) {
    	String name = in.nextName();
    	switch (name) {
    	case "name":
    		result.setName(readName(in));
    		break;
    	case "kind":
    		result.setKind(readKind(in));
    		break;
    	case "deprecated":
    		result.setDeprecated(readDeprecated(in));
    		break;
    	case "location":
    		result.setLocation(readLocation(in));
    		break;
    	case "containerName":
    		result.setContainerName(readContainerName(in));
    		break;
    	default:
    		in.skipValue();
    	}
    }
    in.endObject();
    return result;
  }
  
  protected String readName(final JsonReader in) throws IOException {
    return gson.fromJson(in, String.class);
  }
  
  protected SymbolKind readKind(final JsonReader in) throws IOException {
    return gson.fromJson(in, SymbolKind.class);
  }
  
  protected Boolean readDeprecated(final JsonReader in) throws IOException {
    return gson.fromJson(in, Boolean.class);
  }
  
  protected Location readLocation(final JsonReader in) throws IOException {
    return gson.fromJson(in, Location.class);
  }
  
  protected String readContainerName(final JsonReader in) throws IOException {
    return gson.fromJson(in, String.class);
  }
  
  public void write(final JsonWriter out, final SymbolInformation value) throws IOException {
    if (value == null) {
    	out.nullValue();
    	return;
    }
    
    out.beginObject();
    out.name("name");
    writeName(out, value.getName());
    out.name("kind");
    writeKind(out, value.getKind());
    out.name("deprecated");
    writeDeprecated(out, value.getDeprecated());
    out.name("location");
    writeLocation(out, value.getLocation());
    out.name("containerName");
    writeContainerName(out, value.getContainerName());
    out.endObject();
  }
  
  protected void writeName(final JsonWriter out, final String value) throws IOException {
    out.value(value);
  }
  
  protected void writeKind(final JsonWriter out, final SymbolKind value) throws IOException {
    gson.toJson(value, SymbolKind.class, out);
  }
  
  protected void writeDeprecated(final JsonWriter out, final Boolean value) throws IOException {
    out.value(value);
  }
  
  protected void writeContainerName(final JsonWriter out, final String value) throws IOException {
    out.value(value);
  }
}
