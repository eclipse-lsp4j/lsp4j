package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Code Lens options.
 */
@SuppressWarnings("all")
public class CodeLensOptions extends WrappedJsonObject {
  private static WrappedJsonProperty<Boolean> resolveProviderProperty = new WrappedJsonProperty<>("resolveProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * Code lens has a resolve provider as well.
   */
  @Pure
  public boolean isResolveProvider() {
    return resolveProviderProperty.get(jsonObject);
  }
  
  /**
   * Code lens has a resolve provider as well.
   */
  public void setResolveProvider(final boolean resolveProvider) {
    resolveProviderProperty.set(jsonObject, resolveProvider);
  }
  
  /**
   * Removes the property resolveProvider from the underlying JSON object.
   */
  public boolean removeResolveProvider() {
    return resolveProviderProperty.remove(jsonObject);
  }
  
  public CodeLensOptions() {
    super();
  }
  
  public CodeLensOptions(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public CodeLensOptions(final boolean resolveProvider) {
    this.setResolveProvider(resolveProvider);
  }
}
