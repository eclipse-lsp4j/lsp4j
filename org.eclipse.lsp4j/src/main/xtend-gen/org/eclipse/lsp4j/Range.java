package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * A range in a text document expressed as (zero-based) start and end positions.
 */
@SuppressWarnings("all")
public class Range extends WrappedJsonObject {
  private static WrappedJsonProperty<Position> startProperty = new WrappedJsonProperty<>("start", WrappedJsonConverter.objectConverter(Position.class));
  
  /**
   * The range's start position
   */
  @Pure
  @NonNull
  public Position getStart() {
    return startProperty.get(jsonObject);
  }
  
  /**
   * The range's start position
   */
  public void setStart(@NonNull final Position start) {
    startProperty.set(jsonObject, start);
  }
  
  /**
   * Removes the property start from the underlying JSON object.
   */
  public Position removeStart() {
    return startProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Position> endProperty = new WrappedJsonProperty<>("end", WrappedJsonConverter.objectConverter(Position.class));
  
  /**
   * The range's end position
   */
  @Pure
  @NonNull
  public Position getEnd() {
    return endProperty.get(jsonObject);
  }
  
  /**
   * The range's end position
   */
  public void setEnd(@NonNull final Position end) {
    endProperty.set(jsonObject, end);
  }
  
  /**
   * Removes the property end from the underlying JSON object.
   */
  public Position removeEnd() {
    return endProperty.remove(jsonObject);
  }
  
  public Range() {
    super();
  }
  
  public Range(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public Range(final Position start, final Position end) {
    this.setStart(start);
    this.setEnd(end);
  }
}
