package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Represents a collection of completion items to be presented in the editor.
 */
@SuppressWarnings("all")
public class CompletionList extends WrappedJsonObject {
  private static WrappedJsonProperty<Boolean> isIncompleteProperty = new WrappedJsonProperty<>("isIncomplete", WrappedJsonConverter.booleanConverter);
  
  /**
   * This list it not complete. Further typing should result in recomputing this list.
   */
  @Pure
  public boolean isIncomplete() {
    return isIncompleteProperty.get(jsonObject);
  }
  
  /**
   * This list it not complete. Further typing should result in recomputing this list.
   */
  public void setIsIncomplete(final boolean isIncomplete) {
    isIncompleteProperty.set(jsonObject, isIncomplete);
  }
  
  /**
   * Removes the property isIncomplete from the underlying JSON object.
   */
  public boolean removeIsIncomplete() {
    return isIncompleteProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<List<CompletionItem>> itemsProperty = new WrappedJsonProperty<>("items", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(CompletionItem.class)));
  
  /**
   * The completion items.
   */
  @Pure
  @NonNull
  public List<CompletionItem> getItems() {
    return itemsProperty.get(jsonObject);
  }
  
  /**
   * The completion items.
   */
  public void setItems(@NonNull final List<CompletionItem> items) {
    itemsProperty.set(jsonObject, items);
  }
  
  /**
   * Removes the property items from the underlying JSON object.
   */
  public List<CompletionItem> removeItems() {
    return itemsProperty.remove(jsonObject);
  }
  
  public CompletionList() {
    super();
  }
  
  public CompletionList(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public CompletionList(final boolean isIncomplete, final List<CompletionItem> items) {
    this.setIsIncomplete(isIncomplete);
    this.setItems(items);
  }
}
