package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Represents a parameter of a callable-signature. A parameter can have a label and a doc-comment.
 */
@SuppressWarnings("all")
public class ParameterInformation extends WrappedJsonObject {
  private static WrappedJsonProperty<String> labelProperty = new WrappedJsonProperty<>("label", WrappedJsonConverter.stringConverter);
  
  /**
   * The label of this signature. Will be shown in the UI.
   */
  @Pure
  @NonNull
  public String getLabel() {
    return labelProperty.get(jsonObject);
  }
  
  /**
   * The label of this signature. Will be shown in the UI.
   */
  public void setLabel(@NonNull final String label) {
    labelProperty.set(jsonObject, label);
  }
  
  /**
   * Removes the property label from the underlying JSON object.
   */
  public String removeLabel() {
    return labelProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<String> documentationProperty = new WrappedJsonProperty<>("documentation", WrappedJsonConverter.stringConverter);
  
  /**
   * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
   */
  @Pure
  public String getDocumentation() {
    return documentationProperty.get(jsonObject);
  }
  
  /**
   * The human-readable doc-comment of this signature. Will be shown in the UI but can be omitted.
   */
  public void setDocumentation(final String documentation) {
    documentationProperty.set(jsonObject, documentation);
  }
  
  /**
   * Removes the property documentation from the underlying JSON object.
   */
  public String removeDocumentation() {
    return documentationProperty.remove(jsonObject);
  }
  
  public ParameterInformation() {
    super();
  }
  
  public ParameterInformation(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public ParameterInformation(final String label, final String documentation) {
    this.setLabel(label);
    this.setDocumentation(documentation);
  }
}
