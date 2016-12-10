package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Signature help options.
 */
@SuppressWarnings("all")
public class SignatureHelpOptions extends WrappedJsonObject {
  private static WrappedJsonProperty<List<String>> triggerCharactersProperty = new WrappedJsonProperty<>("triggerCharacters", WrappedJsonConverter.listConverter(WrappedJsonConverter.stringConverter));
  
  /**
   * The characters that trigger signature help automatically.
   */
  @Pure
  public List<String> getTriggerCharacters() {
    return triggerCharactersProperty.get(jsonObject);
  }
  
  /**
   * The characters that trigger signature help automatically.
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
  
  public SignatureHelpOptions() {
    super();
  }
  
  public SignatureHelpOptions(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public SignatureHelpOptions(final List<String> triggerCharacters) {
    this.setTriggerCharacters(triggerCharacters);
  }
}
