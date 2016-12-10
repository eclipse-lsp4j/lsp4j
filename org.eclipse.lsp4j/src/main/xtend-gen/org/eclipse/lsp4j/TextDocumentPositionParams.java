package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * A parameter literal used in requests to pass a text document and a position inside that document.
 */
@SuppressWarnings("all")
public class TextDocumentPositionParams extends WrappedJsonObject {
  private static WrappedJsonProperty<TextDocumentIdentifier> textDocumentProperty = new WrappedJsonProperty<>("textDocument", WrappedJsonConverter.objectConverter(TextDocumentIdentifier.class));
  
  /**
   * The text document.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return textDocumentProperty.get(jsonObject);
  }
  
  /**
   * The text document.
   */
  public void setTextDocument(@NonNull final TextDocumentIdentifier textDocument) {
    textDocumentProperty.set(jsonObject, textDocument);
  }
  
  /**
   * Removes the property textDocument from the underlying JSON object.
   */
  public TextDocumentIdentifier removeTextDocument() {
    return textDocumentProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> uriProperty = new WrappedJsonProperty<>("uri", WrappedJsonConverter.stringConverter);
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Pure
  @Deprecated
  public String getUri() {
    return uriProperty.get(jsonObject);
  }
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Deprecated
  public void setUri(final String uri) {
    uriProperty.set(jsonObject, uri);
  }
  
  /**
   * Removes the property uri from the underlying JSON object.
   */
  @Deprecated
  public String removeUri() {
    return uriProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Position> positionProperty = new WrappedJsonProperty<>("position", WrappedJsonConverter.objectConverter(Position.class));
  
  /**
   * The position inside the text document.
   */
  @Pure
  @NonNull
  public Position getPosition() {
    return positionProperty.get(jsonObject);
  }
  
  /**
   * The position inside the text document.
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
  
  public TextDocumentPositionParams() {
    super();
  }
  
  public TextDocumentPositionParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public TextDocumentPositionParams(final TextDocumentIdentifier textDocument, final String uri, final Position position) {
    this.setTextDocument(textDocument);
    this.setUri(uri);
    this.setPosition(position);
  }
}
