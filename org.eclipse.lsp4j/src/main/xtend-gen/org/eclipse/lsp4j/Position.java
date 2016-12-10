package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Position in a text document expressed as zero-based line and character offset.
 */
@SuppressWarnings("all")
public class Position extends WrappedJsonObject {
  private static WrappedJsonProperty<Integer> lineProperty = new WrappedJsonProperty<>("line", WrappedJsonConverter.integerConverter);
  
  /**
   * Line position in a document (zero-based).
   */
  @Pure
  public int getLine() {
    return lineProperty.get(jsonObject);
  }
  
  /**
   * Line position in a document (zero-based).
   */
  public void setLine(final int line) {
    lineProperty.set(jsonObject, line);
  }
  
  /**
   * Removes the property line from the underlying JSON object.
   */
  public int removeLine() {
    return lineProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Integer> characterProperty = new WrappedJsonProperty<>("character", WrappedJsonConverter.integerConverter);
  
  /**
   * Character offset on a line in a document (zero-based).
   */
  @Pure
  public int getCharacter() {
    return characterProperty.get(jsonObject);
  }
  
  /**
   * Character offset on a line in a document (zero-based).
   */
  public void setCharacter(final int character) {
    characterProperty.set(jsonObject, character);
  }
  
  /**
   * Removes the property character from the underlying JSON object.
   */
  public int removeCharacter() {
    return characterProperty.remove(jsonObject);
  }
  
  public Position() {
    super();
  }
  
  public Position(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public Position(final int line, final int character) {
    this.setLine(line);
    this.setCharacter(character);
  }
}
