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
 * Representation of a range and highlighting style identifiers that should be
 * highlighted based on the underlying model.
 */
@SuppressWarnings("all")
public class ColoringInformation extends WrappedJsonObject {
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * The range that should be highlighted on the client-side.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * The range that should be highlighted on the client-side.
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
  
  private static WrappedJsonProperty<List<Integer>> stylesProperty = new WrappedJsonProperty<>("styles", WrappedJsonConverter.listConverter(WrappedJsonConverter.integerConverter));
  
  /**
   * A list of highlighting styles, that should be applied on
   * the range. Several styles could be merged on the client-side by
   * applying all styles on the range.
   */
  @Pure
  @NonNull
  public List<Integer> getStyles() {
    return stylesProperty.get(jsonObject);
  }
  
  /**
   * A list of highlighting styles, that should be applied on
   * the range. Several styles could be merged on the client-side by
   * applying all styles on the range.
   */
  public void setStyles(@NonNull final List<Integer> styles) {
    stylesProperty.set(jsonObject, styles);
  }
  
  /**
   * Removes the property styles from the underlying JSON object.
   */
  public List<Integer> removeStyles() {
    return stylesProperty.remove(jsonObject);
  }
  
  public ColoringInformation() {
    super();
  }
  
  public ColoringInformation(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public ColoringInformation(final Range range, final List<Integer> styles) {
    this.setRange(range);
    this.setStyles(styles);
  }
}
