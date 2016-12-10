package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The document range formatting request is sent from the client to the server to format a given range in a document.
 */
@SuppressWarnings("all")
public class DocumentRangeFormattingParams extends DocumentFormattingParams {
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * The range to format
   */
  @Pure
  @NonNull
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * The range to format
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
  
  public DocumentRangeFormattingParams() {
    super();
  }
  
  public DocumentRangeFormattingParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DocumentRangeFormattingParams(final Range range) {
    this.setRange(range);
  }
}
