package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Completion options.
 */
@SuppressWarnings("all")
public class CompletionOptions extends WrappedJsonObject {
  private static WrappedJsonProperty<Boolean> resolveProviderProperty = new WrappedJsonProperty<>("resolveProvider", WrappedJsonConverter.booleanConverter);
  
  /**
   * The server provides support to resolve additional information for a completion item.
   */
  @Pure
  public Boolean getResolveProvider() {
    return resolveProviderProperty.get(jsonObject);
  }
  
  /**
   * The server provides support to resolve additional information for a completion item.
   */
  public void setResolveProvider(final Boolean resolveProvider) {
    resolveProviderProperty.set(jsonObject, resolveProvider);
  }
  
  /**
   * Removes the property resolveProvider from the underlying JSON object.
   */
  public Boolean removeResolveProvider() {
    return resolveProviderProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<List<String>> triggerCharactersProperty = new WrappedJsonProperty<>("triggerCharacters", WrappedJsonConverter.listConverter(WrappedJsonConverter.stringConverter));
  
  /**
   * The characters that trigger completion automatically.
   */
  @Pure
  public List<String> getTriggerCharacters() {
    return triggerCharactersProperty.get(jsonObject);
  }
  
  /**
   * The characters that trigger completion automatically.
   */
  public void setTriggerCharacters(final List<String> triggerCharacters) {
    triggerCharactersProperty.set(jsonObject, triggerCharacters);
  }
  
  /**
   * Removes the property triggerCharacters from the underlying JSON object.
   */
  public List<String> removeTriggerCharacters() {
    return triggerCharactersProperty.remove(jsonObject);
  }
  
  public CompletionOptions() {
    super();
  }
  
  public CompletionOptions(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public CompletionOptions(final Boolean resolveProvider, final List<String> triggerCharacters) {
    this.setResolveProvider(resolveProvider);
    this.setTriggerCharacters(triggerCharacters);
  }
}
