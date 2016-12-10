package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Format document on type options
 */
@SuppressWarnings("all")
public class DocumentOnTypeFormattingOptions extends WrappedJsonObject {
  private static WrappedJsonProperty<String> firstTriggerCharacterProperty = new WrappedJsonProperty<>("firstTriggerCharacter", WrappedJsonConverter.stringConverter);
  
  /**
   * A character on which formatting should be triggered, like `}`.
   */
  @Pure
  @NonNull
  public String getFirstTriggerCharacter() {
    return firstTriggerCharacterProperty.get(jsonObject);
  }
  
  /**
   * A character on which formatting should be triggered, like `}`.
   */
  public void setFirstTriggerCharacter(@NonNull final String firstTriggerCharacter) {
    firstTriggerCharacterProperty.set(jsonObject, firstTriggerCharacter);
  }
  
  /**
   * Removes the property firstTriggerCharacter from the underlying JSON object.
   */
  public String removeFirstTriggerCharacter() {
    return firstTriggerCharacterProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<List<String>> moreTriggerCharacterProperty = new WrappedJsonProperty<>("moreTriggerCharacter", WrappedJsonConverter.listConverter(WrappedJsonConverter.stringConverter));
  
  /**
   * More trigger characters.
   */
  @Pure
  public List<String> getMoreTriggerCharacter() {
    return moreTriggerCharacterProperty.get(jsonObject);
  }
  
  /**
   * More trigger characters.
   */
  public void setMoreTriggerCharacter(final List<String> moreTriggerCharacter) {
    moreTriggerCharacterProperty.set(jsonObject, moreTriggerCharacter);
  }
  
  /**
   * Removes the property moreTriggerCharacter from the underlying JSON object.
   */
  public List<String> removeMoreTriggerCharacter() {
    return moreTriggerCharacterProperty.remove(jsonObject);
  }
  
  public DocumentOnTypeFormattingOptions() {
    super();
  }
  
  public DocumentOnTypeFormattingOptions(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DocumentOnTypeFormattingOptions(final String firstTriggerCharacter, final List<String> moreTriggerCharacter) {
    this.setFirstTriggerCharacter(firstTriggerCharacter);
    this.setMoreTriggerCharacter(moreTriggerCharacter);
  }
}
