package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The parameters of a Workspace Symbol Request.
 */
@SuppressWarnings("all")
public class WorkspaceSymbolParams extends WrappedJsonObject {
  private static WrappedJsonProperty<String> queryProperty = new WrappedJsonProperty<>("query", WrappedJsonConverter.stringConverter);
  
  /**
   * A non-empty query string
   */
  @Pure
  @NonNull
  public String getQuery() {
    return queryProperty.get(jsonObject);
  }
  
  /**
   * A non-empty query string
   */
  public void setQuery(@NonNull final String query) {
    queryProperty.set(jsonObject, query);
  }
  
  /**
   * Removes the property query from the underlying JSON object.
   */
  public String removeQuery() {
    return queryProperty.remove(jsonObject);
  }
  
  public WorkspaceSymbolParams() {
    super();
  }
  
  public WorkspaceSymbolParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public WorkspaceSymbolParams(final String query) {
    this.setQuery(query);
  }
}
