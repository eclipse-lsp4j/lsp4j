package org.eclipse.lsp4j;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.SignatureInformation;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Signature help represents the signature of something callable. There can be multiple signature but only one
 * active and only one active parameter.
 */
@SuppressWarnings("all")
public class SignatureHelp {
  /**
   * One or more signatures.
   */
  @NonNull
  private List<SignatureInformation> signatures = new ArrayList<SignatureInformation>();
  
  /**
   * The active signature.
   */
  private Integer activeSignature;
  
  /**
   * The active parameter of the active signature.
   */
  private Integer activeParameter;
  
  /**
   * One or more signatures.
   */
  @Pure
  @NonNull
  public List<SignatureInformation> getSignatures() {
    return this.signatures;
  }
  
  /**
   * One or more signatures.
   */
  public void setSignatures(@NonNull final List<SignatureInformation> signatures) {
    this.signatures = signatures;
  }
  
  /**
   * The active signature.
   */
  @Pure
  public Integer getActiveSignature() {
    return this.activeSignature;
  }
  
  /**
   * The active signature.
   */
  public void setActiveSignature(final Integer activeSignature) {
    this.activeSignature = activeSignature;
  }
  
  /**
   * The active parameter of the active signature.
   */
  @Pure
  public Integer getActiveParameter() {
    return this.activeParameter;
  }
  
  /**
   * The active parameter of the active signature.
   */
  public void setActiveParameter(final Integer activeParameter) {
    this.activeParameter = activeParameter;
  }
  
  public SignatureHelp() {
    
  }
  
  public SignatureHelp(final List<SignatureInformation> signatures, final Integer activeSignature, final Integer activeParameter) {
    this.signatures = signatures;
    this.activeSignature = activeSignature;
    this.activeParameter = activeParameter;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("signatures", this.signatures);
    b.add("activeSignature", this.activeSignature);
    b.add("activeParameter", this.activeParameter);
    return b.toString();
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    if (!super.equals(obj))
      return false;
    SignatureHelp other = (SignatureHelp) obj;
    if (this.signatures == null) {
      if (other.signatures != null)
        return false;
    } else if (!this.signatures.equals(other.signatures))
      return false;
    if (this.activeSignature == null) {
      if (other.activeSignature != null)
        return false;
    } else if (!this.activeSignature.equals(other.activeSignature))
      return false;
    if (this.activeParameter == null) {
      if (other.activeParameter != null)
        return false;
    } else if (!this.activeParameter.equals(other.activeParameter))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.signatures== null) ? 0 : this.signatures.hashCode());
    result = prime * result + ((this.activeSignature== null) ? 0 : this.activeSignature.hashCode());
    result = prime * result + ((this.activeParameter== null) ? 0 : this.activeParameter.hashCode());
    return result;
  }
}
