package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The document open notification is sent from the client to the server to signal newly opened text documents.
 * The document's truth is now managed by the client and the server must not try to read the document's truth using
 * the document's uri.
 */
@SuppressWarnings("all")
public class DidOpenTextDocumentParams extends TextDocumentIdentifier {
  private static WrappedJsonProperty<TextDocumentItem> textDocumentProperty = new WrappedJsonProperty<>("textDocument", WrappedJsonConverter.objectConverter(TextDocumentItem.class));
  
  /**
   * The document that was opened.
   */
  @Pure
  @NonNull
  public TextDocumentItem getTextDocument() {
    return textDocumentProperty.get(jsonObject);
  }
  
  /**
   * The document that was opened.
   */
  public void setTextDocument(@NonNull final TextDocumentItem textDocument) {
    textDocumentProperty.set(jsonObject, textDocument);
  }
  
  /**
   * Removes the property textDocument from the underlying JSON object.
   */
  public TextDocumentItem removeTextDocument() {
    return textDocumentProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> textProperty = new WrappedJsonProperty<>("text", WrappedJsonConverter.stringConverter);
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Pure
  @Deprecated
  public String getText() {
    return textProperty.get(jsonObject);
  }
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Deprecated
  public void setText(final String text) {
    textProperty.set(jsonObject, text);
  }
  
  /**
   * Removes the property text from the underlying JSON object.
   */
  @Deprecated
  public String removeText() {
    return textProperty.remove(jsonObject);
  }
  
  public DidOpenTextDocumentParams() {
    super();
  }
  
  public DidOpenTextDocumentParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DidOpenTextDocumentParams(final TextDocumentItem textDocument, final String text) {
    this.setTextDocument(textDocument);
    this.setText(text);
  }
}
