package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;

@SuppressWarnings("all")
public class ClientCapabilities extends WrappedJsonObject {
  public ClientCapabilities() {
    super();
  }
  
  public ClientCapabilities(final JsonObject jsonObject) {
    super(jsonObject);
  }
}
