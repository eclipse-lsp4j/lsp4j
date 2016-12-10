package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * An identifier to denote a specific version of a text document.
 */
@SuppressWarnings("all")
public class VersionedTextDocumentIdentifier extends TextDocumentIdentifier {
  private static WrappedJsonProperty<Integer> versionProperty = new WrappedJsonProperty<>("version", WrappedJsonConverter.integerConverter);
  
  /**
   * The version number of this document.
   */
  @Pure
  public int getVersion() {
    return versionProperty.get(jsonObject);
  }
  
  /**
   * The version number of this document.
   */
  public void setVersion(final int version) {
    versionProperty.set(jsonObject, version);
  }
  
  /**
   * Removes the property version from the underlying JSON object.
   */
  public int removeVersion() {
    return versionProperty.remove(jsonObject);
  }
  
  public VersionedTextDocumentIdentifier() {
    super();
  }
  
  public VersionedTextDocumentIdentifier(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public VersionedTextDocumentIdentifier(final int version) {
    this.setVersion(version);
  }
}
