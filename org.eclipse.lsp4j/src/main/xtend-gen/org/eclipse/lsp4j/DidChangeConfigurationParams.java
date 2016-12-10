package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * A notification sent from the client to the server to signal the change of configuration settings.
 */
@SuppressWarnings("all")
public class DidChangeConfigurationParams extends WrappedJsonObject {
  private static WrappedJsonProperty<Object> settingsProperty = new WrappedJsonProperty<>("settings", WrappedJsonConverter.noConverter);
  
  @Pure
  @NonNull
  public Object getSettings() {
    return settingsProperty.get(jsonObject);
  }
  
  public void setSettings(@NonNull final Object settings) {
    settingsProperty.set(jsonObject, settings);
  }
  
  /**
   * Removes the property settings from the underlying JSON object.
   */
  public Object removeSettings() {
    return settingsProperty.remove(jsonObject);
  }
  
  public DidChangeConfigurationParams() {
    super();
  }
  
  public DidChangeConfigurationParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DidChangeConfigurationParams(final Object settings) {
    this.setSettings(settings);
  }
}
