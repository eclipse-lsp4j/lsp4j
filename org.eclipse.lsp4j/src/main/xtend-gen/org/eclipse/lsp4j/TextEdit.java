package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * A textual edit applicable to a text document.
 */
@SuppressWarnings("all")
public class TextEdit extends WrappedJsonObject {
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * The range of the text document to be manipulated. To insert text into a document create a range where start === end.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * The range of the text document to be manipulated. To insert text into a document create a range where start === end.
   */
  public void setRange(@NonNull final Range range) {
    rangeProperty.set(jsonObject, range);
  }
  
  /**
   * Removes the property range from the underlying JSON object.
   */
  public Range removeRange() {
    return rangeProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> newTextProperty = new WrappedJsonProperty<>("newText", WrappedJsonConverter.stringConverter);
  
  /**
   * The string to be inserted. For delete operations use an empty string.
   */
  @Pure
  @NonNull
  public String getNewText() {
    return newTextProperty.get(jsonObject);
  }
  
  /**
   * The string to be inserted. For delete operations use an empty string.
   */
  public void setNewText(@NonNull final String newText) {
    newTextProperty.set(jsonObject, newText);
  }
  
  /**
   * Removes the property newText from the underlying JSON object.
   */
  public String removeNewText() {
    return newTextProperty.remove(jsonObject);
  }
  
  public TextEdit() {
    super();
  }
  
  public TextEdit(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public TextEdit(final Range range, final String newText) {
    this.setRange(range);
    this.setNewText(newText);
  }
}
