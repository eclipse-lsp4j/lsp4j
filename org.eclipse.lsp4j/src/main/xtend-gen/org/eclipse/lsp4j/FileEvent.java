package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * An event describing a file change.
 */
@SuppressWarnings("all")
public class FileEvent extends WrappedJsonObject {
  private static WrappedJsonProperty<String> uriProperty = new WrappedJsonProperty<>("uri", WrappedJsonConverter.stringConverter);
  
  /**
   * The file's uri.
   */
  @Pure
  @NonNull
  public String getUri() {
    return uriProperty.get(jsonObject);
  }
  
  /**
   * The file's uri.
   */
  public void setUri(@NonNull final String uri) {
    uriProperty.set(jsonObject, uri);
  }
  
  /**
   * Removes the property uri from the underlying JSON object.
   */
  public String removeUri() {
    return uriProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<FileChangeType> typeProperty = new WrappedJsonProperty<>("type", WrappedJsonConverter.enumConverter(FileChangeType.class));
  
  /**
   * The change type.
   */
  @Pure
  @NonNull
  public FileChangeType getType() {
    return typeProperty.get(jsonObject);
  }
  
  /**
   * The change type.
   */
  public void setType(@NonNull final FileChangeType type) {
    typeProperty.set(jsonObject, type);
  }
  
  /**
   * Removes the property type from the underlying JSON object.
   */
  public FileChangeType removeType() {
    return typeProperty.remove(jsonObject);
  }
  
  public FileEvent() {
    super();
  }
  
  public FileEvent(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public FileEvent(final String uri, final FileChangeType type) {
    this.setUri(uri);
    this.setType(type);
  }
}
