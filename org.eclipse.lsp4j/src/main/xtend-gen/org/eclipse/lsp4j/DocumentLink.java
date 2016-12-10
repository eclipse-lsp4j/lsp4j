package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * A document link is a range in a text document that links to an internal or external resource, like another
 * text document or a web site.
 */
@SuppressWarnings("all")
public class DocumentLink extends WrappedJsonObject {
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * The range this link applies to.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * The range this link applies to.
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
  
  private static WrappedJsonProperty<String> targetProperty = new WrappedJsonProperty<>("target", WrappedJsonConverter.stringConverter);
  
  /**
   * The uri this link points to.
   */
  @Pure
  @NonNull
  public String getTarget() {
    return targetProperty.get(jsonObject);
  }
  
  /**
   * The uri this link points to.
   */
  public void setTarget(@NonNull final String target) {
    targetProperty.set(jsonObject, target);
  }
  
  /**
   * Removes the property target from the underlying JSON object.
   */
  public String removeTarget() {
    return targetProperty.remove(jsonObject);
  }
  
  public DocumentLink() {
    super();
  }
  
  public DocumentLink(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DocumentLink(final Range range, final String target) {
    this.setRange(range);
    this.setTarget(target);
  }
}
