package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The result of a hover request.
 */
@SuppressWarnings("all")
public class Hover extends WrappedJsonObject {
  private static WrappedJsonProperty<List<String>> contentsProperty = new WrappedJsonProperty<>("contents", WrappedJsonConverter.listConverter(WrappedJsonConverter.stringConverter));
  
  /**
   * The hover's content as markdown
   */
  @Pure
  @NonNull
  public List<String> getContents() {
    return contentsProperty.get(jsonObject);
  }
  
  /**
   * The hover's content as markdown
   */
  public void setContents(@NonNull final List<String> contents) {
    contentsProperty.set(jsonObject, contents);
  }
  
  /**
   * Removes the property contents from the underlying JSON object.
   */
  public List<String> removeContents() {
    return contentsProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * An optional range
   */
  @Pure
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * An optional range
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
  
  public Hover() {
    super();
  }
  
  public Hover(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public Hover(final List<String> contents, final Range range) {
    this.setContents(contents);
    this.setRange(range);
  }
}
