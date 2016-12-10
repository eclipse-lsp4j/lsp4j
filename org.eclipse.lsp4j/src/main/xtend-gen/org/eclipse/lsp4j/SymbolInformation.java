package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Represents information about programming constructs like variables, classes, classs etc.
 */
@SuppressWarnings("all")
public class SymbolInformation extends WrappedJsonObject {
  private static WrappedJsonProperty<String> nameProperty = new WrappedJsonProperty<>("name", WrappedJsonConverter.stringConverter);
  
  /**
   * The name of this symbol.
   */
  @Pure
  @NonNull
  public String getName() {
    return nameProperty.get(jsonObject);
  }
  
  /**
   * The name of this symbol.
   */
  public void setName(@NonNull final String name) {
    nameProperty.set(jsonObject, name);
  }
  
  /**
   * Removes the property name from the underlying JSON object.
   */
  public String removeName() {
    return nameProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<SymbolKind> kindProperty = new WrappedJsonProperty<>("kind", WrappedJsonConverter.enumConverter(SymbolKind.class));
  
  /**
   * The kind of this symbol.
   */
  @Pure
  @NonNull
  public SymbolKind getKind() {
    return kindProperty.get(jsonObject);
  }
  
  /**
   * The kind of this symbol.
   */
  public void setKind(@NonNull final SymbolKind kind) {
    kindProperty.set(jsonObject, kind);
  }
  
  /**
   * Removes the property kind from the underlying JSON object.
   */
  public SymbolKind removeKind() {
    return kindProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Location> locationProperty = new WrappedJsonProperty<>("location", WrappedJsonConverter.objectConverter(Location.class));
  
  /**
   * The location of this symbol.
   */
  @Pure
  @NonNull
  public Location getLocation() {
    return locationProperty.get(jsonObject);
  }
  
  /**
   * The location of this symbol.
   */
  public void setLocation(@NonNull final Location location) {
    locationProperty.set(jsonObject, location);
  }
  
  /**
   * Removes the property location from the underlying JSON object.
   */
  public Location removeLocation() {
    return locationProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> containerNameProperty = new WrappedJsonProperty<>("containerName", WrappedJsonConverter.stringConverter);
  
  /**
   * The name of the symbol containing this symbol.
   */
  @Pure
  public String getContainerName() {
    return containerNameProperty.get(jsonObject);
  }
  
  /**
   * The name of the symbol containing this symbol.
   */
  public void setContainerName(final String containerName) {
    containerNameProperty.set(jsonObject, containerName);
  }
  
  /**
   * Removes the property containerName from the underlying JSON object.
   */
  public String removeContainerName() {
    return containerNameProperty.remove(jsonObject);
  }
  
  public SymbolInformation() {
    super();
  }
  
  public SymbolInformation(final JsonObject jsonObject) {
    super(jsonObject);
  }
}
