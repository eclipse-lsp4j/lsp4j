package org.eclipse.lsp4j;

import com.google.gson.JsonObject;
import java.util.List;
import org.eclipse.lsp4j.SignatureInformation;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonConverter;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonObject;
import org.eclipse.lsp4j.jsonrpc.json.WrappedJsonProperty;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Signature help represents the signature of something callable. There can be multiple signature but only one
 * active and only one active parameter.
 */
@SuppressWarnings("all")
public class SignatureHelp extends WrappedJsonObject {
  private static WrappedJsonProperty<List<SignatureInformation>> signaturesProperty = new WrappedJsonProperty<>("signatures", WrappedJsonConverter.listConverter(WrappedJsonConverter.objectConverter(SignatureInformation.class)));
  
  /**
   * One or more signatures.
   */
  @Pure
  @NonNull
  public List<SignatureInformation> getSignatures() {
    return signaturesProperty.get(jsonObject);
  }
  
  /**
   * One or more signatures.
   */
  public void setSignatures(@NonNull final List<SignatureInformation> signatures) {
    signaturesProperty.set(jsonObject, signatures);
  }
  
  /**
   * Removes the property signatures from the underlying JSON object.
   */
  public List<SignatureInformation> removeSignatures() {
    return signaturesProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Integer> activeSignatureProperty = new WrappedJsonProperty<>("activeSignature", WrappedJsonConverter.integerConverter);
  
  /**
   * The active signature.
   */
  @Pure
  public Integer getActiveSignature() {
    return activeSignatureProperty.get(jsonObject);
  }
  
  /**
   * The active signature.
   */
  public void setActiveSignature(final Integer activeSignature) {
    activeSignatureProperty.set(jsonObject, activeSignature);
  }
  
  /**
   * Removes the property activeSignature from the underlying JSON object.
   */
  public Integer removeActiveSignature() {
    return activeSignatureProperty.remove(jsonObject);
  }
  
  private static WrappedJsonProperty<Integer> activeParameterProperty = new WrappedJsonProperty<>("activeParameter", WrappedJsonConverter.integerConverter);
  
  /**
   * The active parameter of the active signature.
   */
  @Pure
  public Integer getActiveParameter() {
    return activeParameterProperty.get(jsonObject);
  }
  
  /**
   * The active parameter of the active signature.
   */
  public void setActiveParameter(final Integer activeParameter) {
    activeParameterProperty.set(jsonObject, activeParameter);
  }
  
  /**
   * Removes the property activeParameter from the underlying JSON object.
   */
  public Integer removeActiveParameter() {
    return activeParameterProperty.remove(jsonObject);
  }
  
  public SignatureHelp() {
    super();
  }
  
  public SignatureHelp(final JsonObject jsonObject) {
    super(jsonObject);
  }
  
  public SignatureHelp(final List<SignatureInformation> signatures, final Integer activeSignature, final Integer activeParameter) {
    this.setSignatures(signatures);
    this.setActiveSignature(activeSignature);
    this.setActiveParameter(activeParameter);
  }
}
