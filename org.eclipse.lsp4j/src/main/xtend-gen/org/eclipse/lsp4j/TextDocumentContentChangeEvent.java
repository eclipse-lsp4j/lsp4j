package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * An event describing a change to a text document. If range and rangeLength are omitted the new text is considered
 * to be the full content of the document.
 */
@SuppressWarnings("all")
public class TextDocumentContentChangeEvent extends WrappedJsonObject {
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * The range of the document that changed.
   */
  @Pure
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * The range of the document that changed.
   */
  public void setRange(final Range range) {
    rangeProperty.set(jsonObject, range);
  }
  
  /**
   * Removes the property range from the underlying JSON object.
   */
  public Range removeRange() {
    return rangeProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Integer> rangeLengthProperty = new WrappedJsonProperty<>("rangeLength", WrappedJsonConverter.integerConverter);
  
  /**
   * The length of the range that got replaced.
   */
  @Pure
  public Integer getRangeLength() {
    return rangeLengthProperty.get(jsonObject);
  }
  
  /**
   * The length of the range that got replaced.
   */
  public void setRangeLength(final Integer rangeLength) {
    rangeLengthProperty.set(jsonObject, rangeLength);
  }
  
  /**
   * Removes the property rangeLength from the underlying JSON object.
   */
  public Integer removeRangeLength() {
    return rangeLengthProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> textProperty = new WrappedJsonProperty<>("text", WrappedJsonConverter.stringConverter);
  
  /**
   * The new text of the document.
   */
  @Pure
  @NonNull
  public String getText() {
    return textProperty.get(jsonObject);
  }
  
  /**
   * The new text of the document.
   */
  public void setText(@NonNull final String text) {
    textProperty.set(jsonObject, text);
  }
  
  /**
   * Removes the property text from the underlying JSON object.
   */
  public String removeText() {
    return textProperty.remove(jsonObject);
  }
  
  public TextDocumentContentChangeEvent() {
    super();
  }
  
  public TextDocumentContentChangeEvent(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public TextDocumentContentChangeEvent(final Range range, final Integer rangeLength, final String text) {
    this.setRange(range);
    this.setRangeLength(rangeLength);
    this.setText(text);
  }
}
