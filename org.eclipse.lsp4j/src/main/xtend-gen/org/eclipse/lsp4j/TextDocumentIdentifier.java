package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Text documents are identified using an URI. On the protocol level URI's are passed as strings.
 */
@SuppressWarnings("all")
public class TextDocumentIdentifier extends WrappedJsonObject {
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
  
  public TextDocumentIdentifier() {
    super();
  }
  
  public TextDocumentIdentifier(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public TextDocumentIdentifier(final String uri) {
    this.setUri(uri);
  }
}
