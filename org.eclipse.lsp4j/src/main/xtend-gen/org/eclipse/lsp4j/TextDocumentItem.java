package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * An item to transfer a text document from the client to the server.
 */
@SuppressWarnings("all")
public class TextDocumentItem extends WrappedJsonObject {
  private static WrappedJsonProperty<String> uriProperty = new WrappedJsonProperty<>("uri", WrappedJsonConverter.stringConverter);
  
  /**
   * The text document's uri.
   */
  @Pure
  @NonNull
  public String getUri() {
    return uriProperty.get(jsonObject);
  }
  
  /**
   * The text document's uri.
   */
  public void setUri(@NonNull final String uri) {
    uriProperty.set(jsonObject, uri);
  }
  
  /**
   * Removes the property uri from the underlying JSON object.
   */
  public String removeUri() {
    return uriProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> languageIdProperty = new WrappedJsonProperty<>("languageId", WrappedJsonConverter.stringConverter);
  
  /**
   * The text document's language identifier
   */
  @Pure
  @NonNull
  public String getLanguageId() {
    return languageIdProperty.get(jsonObject);
  }
  
  /**
   * The text document's language identifier
   */
  public void setLanguageId(@NonNull final String languageId) {
    languageIdProperty.set(jsonObject, languageId);
  }
  
  /**
   * Removes the property languageId from the underlying JSON object.
   */
  public String removeLanguageId() {
    return languageIdProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Integer> versionProperty = new WrappedJsonProperty<>("version", WrappedJsonConverter.integerConverter);
  
  /**
   * The version number of this document (it will strictly increase after each change, including undo/redo).
   */
  @Pure
  public int getVersion() {
    return versionProperty.get(jsonObject);
  }
  
  /**
   * The version number of this document (it will strictly increase after each change, including undo/redo).
   */
  public void setVersion(final int version) {
    versionProperty.set(jsonObject, version);
  }
  
  /**
   * Removes the property version from the underlying JSON object.
   */
  public int removeVersion() {
    return versionProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> textProperty = new WrappedJsonProperty<>("text", WrappedJsonConverter.stringConverter);
  
  /**
   * The content of the opened  text document.
   */
  @Pure
  @NonNull
  public String getText() {
    return textProperty.get(jsonObject);
  }
  
  /**
   * The content of the opened  text document.
   */
  public void setText(@NonNull final String text) {
    textProperty.set(jsonObject, text);
  }
  
  /**
   * Removes the property text from the underlying JSON object.
   */
  public String removeText() {
    return textProperty.remove(jsonObject);
  }
  
  public TextDocumentItem() {
    super();
  }
  
  public TextDocumentItem(final JsonObject jsonObject) {
    super(jsonObject);
  }
}
