package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * The references request is sent from the client to the server to resolve project-wide references for the symbol
 * denoted by the given text document position.
 */
@SuppressWarnings("all")
public class ReferenceContext extends WrappedJsonObject {
  private static WrappedJsonProperty<Boolean> includeDeclarationProperty = new WrappedJsonProperty<>("includeDeclaration", WrappedJsonConverter.booleanConverter);
  
  /**
   * Include the declaration of the current symbol.
   */
  @Pure
  public boolean isIncludeDeclaration() {
    return includeDeclarationProperty.get(jsonObject);
  }
  
  /**
   * Include the declaration of the current symbol.
   */
  public void setIncludeDeclaration(final boolean includeDeclaration) {
    includeDeclarationProperty.set(jsonObject, includeDeclaration);
  }
  
  /**
   * Removes the property includeDeclaration from the underlying JSON object.
   */
  public boolean removeIncludeDeclaration() {
    return includeDeclarationProperty.remove(jsonObject);
  }
  
  public ReferenceContext() {
    super();
  }
  
  public ReferenceContext(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public ReferenceContext(final boolean includeDeclaration) {
    this.setIncludeDeclaration(includeDeclaration);
  }
}
