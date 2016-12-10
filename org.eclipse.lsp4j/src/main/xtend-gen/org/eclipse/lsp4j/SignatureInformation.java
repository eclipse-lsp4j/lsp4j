package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.ParameterInformation;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Represents the signature of something callable. A signature can have a label, like a function-name, a doc-comment, and
 * a set of parameters.
 */
@SuppressWarnings("all")
public class SignatureInformation extends WrappedJsonObject {
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
  
  private static WrappedJsonProperty<List<ParameterInformation>> parametersProperty = new WrappedJsonProperty<>("parameters", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(ParameterInformation.class)));
  
  /**
   * The parameters of this signature.
   */
  @Pure
  public List<ParameterInformation> getParameters() {
    return parametersProperty.get(jsonObject);
  }
  
  /**
   * The parameters of this signature.
   */
  public void setParameters(final List<ParameterInformation> parameters) {
    parametersProperty.set(jsonObject, parameters);
  }
  
  /**
   * Removes the property parameters from the underlying JSON object.
   */
  public List<ParameterInformation> removeParameters() {
    return parametersProperty.remove(jsonObject);
  }
  
  public SignatureInformation() {
    super();
  }
  
  public SignatureInformation(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public SignatureInformation(final String label, final String documentation, final List<ParameterInformation> parameters) {
    this.setLabel(label);
    this.setDocumentation(documentation);
    this.setParameters(parameters);
  }
}
