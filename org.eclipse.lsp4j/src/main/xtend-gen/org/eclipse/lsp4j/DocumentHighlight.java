package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.DocumentHighlightKind;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * A document highlight is a range inside a text document which deserves special attention. Usually a document highlight
 * is visualized by changing the background color of its range.
 */
@SuppressWarnings("all")
public class DocumentHighlight extends WrappedJsonObject {
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * The range this highlight applies to.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * The range this highlight applies to.
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
  
  private static WrappedJsonProperty<DocumentHighlightKind> kindProperty = new WrappedJsonProperty<>("kind", WrappedJsonConverter.enumConverter(DocumentHighlightKind.class));
  
  /**
   * The highlight kind, default is {@link DocumentHighlightKind#Text}.
   */
  @Pure
  public DocumentHighlightKind getKind() {
    return kindProperty.get(jsonObject);
  }
  
  /**
   * The highlight kind, default is {@link DocumentHighlightKind#Text}.
   */
  public void setKind(final DocumentHighlightKind kind) {
    kindProperty.set(jsonObject, kind);
  }
  
  /**
   * Removes the property kind from the underlying JSON object.
   */
  public DocumentHighlightKind removeKind() {
    return kindProperty.remove(jsonObject);
  }
  
  public DocumentHighlight() {
    super();
  }
  
  public DocumentHighlight(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DocumentHighlight(final Range range, final DocumentHighlightKind kind) {
    this.setRange(range);
    this.setKind(kind);
  }
}
