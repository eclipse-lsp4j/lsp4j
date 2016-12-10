package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class DocumentLinkParams extends WrappedJsonObject {
  private static WrappedJsonProperty<TextDocumentIdentifier> textDocumentProperty = new WrappedJsonProperty<>("textDocument", WrappedJsonConverter.objectConverter(TextDocumentIdentifier.class));
  
  /**
   * The document to provide document links for.
   */
  @Pure
  public TextDocumentIdentifier getTextDocument() {
    return textDocumentProperty.get(jsonObject);
  }
  
  /**
   * The document to provide document links for.
   */
  public void setTextDocument(final TextDocumentIdentifier textDocument) {
    textDocumentProperty.set(jsonObject, textDocument);
  }
  
  /**
   * Removes the property textDocument from the underlying JSON object.
   */
  public TextDocumentIdentifier removeTextDocument() {
    return textDocumentProperty.remove(jsonObject);
  }
  
  public DocumentLinkParams() {
    super();
  }
  
  public DocumentLinkParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DocumentLinkParams(final TextDocumentIdentifier textDocument) {
    this.setTextDocument(textDocument);
  }
}
