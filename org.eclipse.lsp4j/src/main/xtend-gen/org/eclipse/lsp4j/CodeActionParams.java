package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The code action request is sent from the client to the server to compute commands for a given text document and range.
 * The request is triggered when the user moves the cursor into an problem marker in the editor or presses the lightbulb
 * associated with a marker.
 */
@SuppressWarnings("all")
public class CodeActionParams extends WrappedJsonObject {
  private static WrappedJsonProperty<TextDocumentIdentifier> textDocumentProperty = new WrappedJsonProperty<>("textDocument", WrappedJsonConverter.objectConverter(TextDocumentIdentifier.class));
  
  /**
   * The document in which the command was invoked.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return textDocumentProperty.get(jsonObject);
  }
  
  /**
   * The document in which the command was invoked.
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
  
  private static WrappedJsonProperty<Range> rangeProperty = new WrappedJsonProperty<>("range", WrappedJsonConverter.objectConverter(Range.class));
  
  /**
   * The range for which the command was invoked.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return rangeProperty.get(jsonObject);
  }
  
  /**
   * The range for which the command was invoked.
   */
  public void setRange(@NonNull final Range range) {
    rangeProperty.set(jsonObject, range);
  }
  
  /**
   * Removes the property range from the underlying JSON object.
   */
  public Range removeRange() {
    return rangeProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<CodeActionContext> contextProperty = new WrappedJsonProperty<>("context", WrappedJsonConverter.objectConverter(CodeActionContext.class));
  
  /**
   * Context carrying additional information.
   */
  @Pure
  @NonNull
  public CodeActionContext getContext() {
    return contextProperty.get(jsonObject);
  }
  
  /**
   * Context carrying additional information.
   */
  public void setContext(@NonNull final CodeActionContext context) {
    contextProperty.set(jsonObject, context);
  }
  
  /**
   * Removes the property context from the underlying JSON object.
   */
  public CodeActionContext removeContext() {
    return contextProperty.remove(jsonObject);
  }
  
  public CodeActionParams() {
    super();
  }
  
  public CodeActionParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public CodeActionParams(final TextDocumentIdentifier textDocument, final Range range, final CodeActionContext context) {
    this.setTextDocument(textDocument);
    this.setRange(range);
    this.setContext(context);
  }
}
