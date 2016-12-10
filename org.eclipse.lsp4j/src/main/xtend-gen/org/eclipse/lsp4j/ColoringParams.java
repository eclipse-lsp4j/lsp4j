package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.ColoringInformation;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Representation of a computed mapping from ranges to the appropriate
 * highlighting style.
 */
@SuppressWarnings("all")
public class ColoringParams extends WrappedJsonObject {
  private static WrappedJsonProperty<String> uriProperty = new WrappedJsonProperty<>("uri", WrappedJsonConverter.stringConverter);
  
  /**
   * The URI for which coloring information is reported.
   */
  @Pure
  @NonNull
  public String getUri() {
    return uriProperty.get(jsonObject);
  }
  
  /**
   * The URI for which coloring information is reported.
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
  
  private static WrappedJsonProperty<List<ColoringInformation>> infosProperty = new WrappedJsonProperty<>("infos", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(ColoringInformation.class)));
  
  /**
   * A list of coloring information instances.
   */
  @Pure
  @NonNull
  public List<ColoringInformation> getInfos() {
    return infosProperty.get(jsonObject);
  }
  
  /**
   * A list of coloring information instances.
   */
  public void setInfos(@NonNull final List<ColoringInformation> infos) {
    infosProperty.set(jsonObject, infos);
  }
  
  /**
   * Removes the property infos from the underlying JSON object.
   */
  public List<ColoringInformation> removeInfos() {
    return infosProperty.remove(jsonObject);
  }
  
  public ColoringParams() {
    super();
  }
  
  public ColoringParams(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public ColoringParams(final String uri, final List<ColoringInformation> infos) {
    this.setUri(uri);
    this.setInfos(infos);
  }
}
