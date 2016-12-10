package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.Map;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Value-object describing what options formatting should use.
 */
@SuppressWarnings("all")
public class FormattingOptions extends WrappedJsonObject {
  private static WrappedJsonProperty<Integer> tabSizeProperty = new WrappedJsonProperty<>("tabSize", WrappedJsonConverter.integerConverter);
  
  /**
   * Size of a tab in spaces.
   */
  @Pure
  public int getTabSize() {
    return tabSizeProperty.get(jsonObject);
  }
  
  /**
   * Size of a tab in spaces.
   */
  public void setTabSize(final int tabSize) {
    tabSizeProperty.set(jsonObject, tabSize);
  }
  
  /**
   * Removes the property tabSize from the underlying JSON object.
   */
  public int removeTabSize() {
    return tabSizeProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Boolean> insertSpacesProperty = new WrappedJsonProperty<>("insertSpaces", WrappedJsonConverter.booleanConverter);
  
  /**
   * Prefer spaces over tabs.
   */
  @Pure
  public boolean isInsertSpaces() {
    return insertSpacesProperty.get(jsonObject);
  }
  
  /**
   * Prefer spaces over tabs.
   */
  public void setInsertSpaces(final boolean insertSpaces) {
    insertSpacesProperty.set(jsonObject, insertSpaces);
  }
  
  /**
   * Removes the property insertSpaces from the underlying JSON object.
   */
  public boolean removeInsertSpaces() {
    return insertSpacesProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Map<String, String>> propertiesProperty = new WrappedJsonProperty<>("properties", WrappedJsonConverter.mapConverter(WrappedJsonConverter.stringConverter));
  
  /**
   * Signature for further properties.
   */
  @Pure
  public Map<String, String> getProperties() {
    return propertiesProperty.get(jsonObject);
  }
  
  /**
   * Signature for further properties.
   */
  public void setProperties(final Map<String, String> properties) {
    propertiesProperty.set(jsonObject, properties);
  }
  
  /**
   * Removes the property properties from the underlying JSON object.
   */
  public Map<String, String> removeProperties() {
    return propertiesProperty.remove(jsonObject);
  }
  
  public FormattingOptions() {
    super();
  }
  
  public FormattingOptions(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public FormattingOptions(final int tabSize, final boolean insertSpaces, final Map<String, String> properties) {
    this.setTabSize(tabSize);
    this.setInsertSpaces(insertSpaces);
    this.setProperties(properties);
  }
}
