package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class InitializeResult extends WrappedJsonObject {
  private static WrappedJsonProperty<ServerCapabilities> capabilitiesProperty = new WrappedJsonProperty<>("capabilities", WrappedJsonConverter.objectConverter(ServerCapabilities.class));
  
  /**
   * The capabilities the language server provides.
   */
  @Pure
  @NonNull
  public ServerCapabilities getCapabilities() {
    return capabilitiesProperty.get(jsonObject);
  }
  
  /**
   * The capabilities the language server provides.
   */
  public void setCapabilities(@NonNull final ServerCapabilities capabilities) {
    capabilitiesProperty.set(jsonObject, capabilities);
  }
  
  /**
   * Removes the property capabilities from the underlying JSON object.
   */
  public ServerCapabilities removeCapabilities() {
    return capabilitiesProperty.remove(jsonObject);
  }
  
  public InitializeResult() {
    super();
  }
  
  public InitializeResult(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public InitializeResult(final ServerCapabilities capabilities) {
    this.setCapabilities(capabilities);
  }
}
