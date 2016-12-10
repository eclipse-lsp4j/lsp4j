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
 * The rename request is sent from the client to the server to do a workspace wide rename of a symbol.
 */
@SuppressWarnings("all")
public class RenameParams extends WrappedJsonObject {
  private static WrappedJsonProperty<TextDocumentIdentifier> textDocumentProperty = new WrappedJsonProperty<>("textDocument", WrappedJsonConverter.objectConverter(TextDocumentIdentifier.class));
  
  /**
   * The document in which to find the symbol.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return textDocumentProperty.get(jsonObject);
  }
  
  /**
   * The document in which to find the symbol.
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
  
  private static WrappedJsonProperty<String> newNameProperty = new WrappedJsonProperty<>("newName", WrappedJsonConverter.stringConverter);
  
  /**
   * The new name of the symbol. If the given name is not valid the request must return a
   * ResponseError with an appropriate message set.
   */
  @Pure
  @NonNull
  public String getNewName() {
    return newNameProperty.get(jsonObject);
  }
  
  /**
   * The new name of the symbol. If the given name is not valid the request must return a
   * ResponseError with an appropriate message set.
   */
  public void setNewName(@NonNull final String newName) {
    newNameProperty.set(jsonObject, newName);
  }
  
  /**
   * Removes the property newName from the underlying JSON object.
   */
  public String removeNewName() {
    return newNameProperty.remove(jsonObject);
  }
  
  public RenameParams() {
    super();
  }
  
  public RenameParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public RenameParams(final TextDocumentIdentifier textDocument, final Position position, final String newName) {
    this.setTextDocument(textDocument);
    this.setPosition(position);
    this.setNewName(newName);
  }
}
