package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.FormattingOptions;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The document formatting request is sent from the server to the client to format a whole document.
 */
@SuppressWarnings("all")
public class DocumentFormattingParams extends WrappedJsonObject {
  private static WrappedJsonProperty<TextDocumentIdentifier> textDocumentProperty = new WrappedJsonProperty<>("textDocument", WrappedJsonConverter.objectConverter(TextDocumentIdentifier.class));
  
  /**
   * The document to format.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return textDocumentProperty.get(jsonObject);
  }
  
  /**
   * The document to format.
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
  
  private static WrappedJsonProperty<FormattingOptions> optionsProperty = new WrappedJsonProperty<>("options", WrappedJsonConverter.objectConverter(FormattingOptions.class));
  
  /**
   * The format options
   */
  @Pure
  @NonNull
  public FormattingOptions getOptions() {
    return optionsProperty.get(jsonObject);
  }
  
  /**
   * The format options
   */
  public void setOptions(@NonNull final FormattingOptions options) {
    optionsProperty.set(jsonObject, options);
  }
  
  /**
   * Removes the property options from the underlying JSON object.
   */
  public FormattingOptions removeOptions() {
    return optionsProperty.remove(jsonObject);
  }
  
  public DocumentFormattingParams() {
    super();
  }
  
  public DocumentFormattingParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public DocumentFormattingParams(final TextDocumentIdentifier textDocument, final FormattingOptions options) {
    this.setTextDocument(textDocument);
    this.setOptions(options);
  }
}
