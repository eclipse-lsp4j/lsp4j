package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The code lens request is sent from the client to the server to compute code lenses for a given text document.
 */
@SuppressWarnings("all")
public class CodeLensParams extends WrappedJsonObject {
  private static WrappedJsonProperty<TextDocumentIdentifier> textDocumentProperty = new WrappedJsonProperty<>("textDocument", WrappedJsonConverter.objectConverter(TextDocumentIdentifier.class));
  
  /**
   * The document to request code lens for.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return textDocumentProperty.get(jsonObject);
  }
  
  /**
   * The document to request code lens for.
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
  
  public CodeLensParams() {
    super();
  }
  
  public CodeLensParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public CodeLensParams(final TextDocumentIdentifier textDocument) {
    this.setTextDocument(textDocument);
  }
}
