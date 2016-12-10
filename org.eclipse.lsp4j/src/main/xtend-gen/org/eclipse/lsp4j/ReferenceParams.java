package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.ReferenceContext;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The references request is sent from the client to the server to resolve project-wide references for the symbol
 * denoted by the given text document position.
 */
@SuppressWarnings("all")
public class ReferenceParams extends TextDocumentPositionParams {
  private static WrappedJsonProperty<ReferenceContext> contextProperty = new WrappedJsonProperty<>("context", WrappedJsonConverter.objectConverter(ReferenceContext.class));
  
  @Pure
  @NonNull
  public ReferenceContext getContext() {
    return contextProperty.get(jsonObject);
  }
  
  public void setContext(@NonNull final ReferenceContext context) {
    contextProperty.set(jsonObject, context);
  }
  
  /**
   * Removes the property context from the underlying JSON object.
   */
  public ReferenceContext removeContext() {
    return contextProperty.remove(jsonObject);
  }
  
  public ReferenceParams() {
    super();
  }
  
  public ReferenceParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public ReferenceParams(final ReferenceContext context) {
    this.setContext(context);
  }
}
