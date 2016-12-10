package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The document on type formatting request is sent from the client to the server to format parts of the document during typing.
 */
@SuppressWarnings("all")
public class DocumentOnTypeFormattingParams extends DocumentFormattingParams {
  private static WrappedJsonProperty<Position> positionProperty = new WrappedJsonProperty<>("position", WrappedJsonConverter.objectConverter(Position.class));
  
  /**
   * The position at which this request was send.
   */
  @Pure
  @NonNull
  public Position getPosition() {
    return positionProperty.get(jsonObject);
  }
  
  /**
   * The position at which this request was send.
   */
  public void setPosition(@NonNull final Position position) {
    positionProperty.set(jsonObject, position);
  }
  
  /**
   * Removes the property position from the underlying JSON object.
   */
  public Position removePosition() {
    return positionProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> chProperty = new WrappedJsonProperty<>("ch", WrappedJsonConverter.stringConverter);
  
  /**
   * The character that has been typed.
   */
  @Pure
  @NonNull
  public String getCh() {
    return chProperty.get(jsonObject);
  }
  
  /**
   * The character that has been typed.
   */
  public void setCh(@NonNull final String ch) {
    chProperty.set(jsonObject, ch);
  }
  
  /**
   * Removes the property ch from the underlying JSON object.
   */
  public String removeCh() {
    return chProperty.remove(jsonObject);
  }
  
  public DocumentOnTypeFormattingParams() {
    super();
  }
  
  public DocumentOnTypeFormattingParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DocumentOnTypeFormattingParams(final Position position, final String ch) {
    this.setPosition(position);
    this.setCh(ch);
  }
}
