package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The document change notification is sent from the client to the server to signal changes to a text document.
 */
@SuppressWarnings("all")
public class DidChangeTextDocumentParams extends WrappedJsonObject {
  private static WrappedJsonProperty<VersionedTextDocumentIdentifier> textDocumentProperty = new WrappedJsonProperty<>("textDocument", WrappedJsonConverter.objectConverter(VersionedTextDocumentIdentifier.class));
  
  /**
   * The document that did change. The version number points to the version after all provided content changes have
   * been applied.
   */
  @Pure
  @NonNull
  public VersionedTextDocumentIdentifier getTextDocument() {
    return textDocumentProperty.get(jsonObject);
  }
  
  /**
   * The document that did change. The version number points to the version after all provided content changes have
   * been applied.
   */
  public void setTextDocument(@NonNull final VersionedTextDocumentIdentifier textDocument) {
    textDocumentProperty.set(jsonObject, textDocument);
  }
  
  /**
   * Removes the property textDocument from the underlying JSON object.
   */
  public VersionedTextDocumentIdentifier removeTextDocument() {
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
  
  private static WrappedJsonProperty<List<TextDocumentContentChangeEvent>> contentChangesProperty = new WrappedJsonProperty<>("contentChanges", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(TextDocumentContentChangeEvent.class)));
  
  /**
   * The actual content changes.
   */
  @Pure
  @NonNull
  public List<TextDocumentContentChangeEvent> getContentChanges() {
    return contentChangesProperty.get(jsonObject);
  }
  
  /**
   * The actual content changes.
   */
  public void setContentChanges(@NonNull final List<TextDocumentContentChangeEvent> contentChanges) {
    contentChangesProperty.set(jsonObject, contentChanges);
  }
  
  /**
   * Removes the property contentChanges from the underlying JSON object.
   */
  public List<TextDocumentContentChangeEvent> removeContentChanges() {
    return contentChangesProperty.remove(jsonObject);
  }
  
  public DidChangeTextDocumentParams() {
    super();
  }
  
  public DidChangeTextDocumentParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DidChangeTextDocumentParams(final VersionedTextDocumentIdentifier textDocument, final String uri, final List<TextDocumentContentChangeEvent> contentChanges) {
    this.setTextDocument(textDocument);
    this.setUri(uri);
    this.setContentChanges(contentChanges);
  }
}
