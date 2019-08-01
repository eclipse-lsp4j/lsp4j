/**
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.SignatureInformation;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
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
  private List<SignatureInformation> signatures;
  
  /**
   * The active signature. If omitted or the value lies outside the
   * range of `signatures` the value defaults to zero or is ignored if
   * `signatures.length === 0`. Whenever possible implementors should
   * make an active decision about the active signature and shouldn't
   * rely on a default value.
   * In future version of the protocol this property might become
   * mandantory to better express this.
   */
  private Integer activeSignature;
  
  /**
   * The active parameter of the active signature. If omitted or the value
   * lies outside the range of `signatures[activeSignature].parameters`
   * defaults to 0 if the active signature has parameters. If
   * the active signature has no parameters it is ignored.
   * In future version of the protocol this property might become
   * mandantory to better express the active parameter if the
   * active signature does have any.
   */
  private Integer activeParameter;
  
  public SignatureHelp() {
    ArrayList<SignatureInformation> _arrayList = new ArrayList<SignatureInformation>();
    this.signatures = _arrayList;
  }
  
  public SignatureHelp(@NonNull final List<SignatureInformation> signatures, final Integer activeSignature, final Integer activeParameter) {
    this.signatures = Preconditions.<List<SignatureInformation>>checkNotNull(signatures, "signatures");
    this.activeSignature = activeSignature;
    this.activeParameter = activeParameter;
  }
  
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
    this.signatures = Preconditions.checkNotNull(signatures, "signatures");
  }
  
  /**
   * The active signature. If omitted or the value lies outside the
   * range of `signatures` the value defaults to zero or is ignored if
   * `signatures.length === 0`. Whenever possible implementors should
   * make an active decision about the active signature and shouldn't
   * rely on a default value.
   * In future version of the protocol this property might become
   * mandantory to better express this.
   */
  @Pure
  public Integer getActiveSignature() {
    return this.activeSignature;
  }
  
  /**
   * The active signature. If omitted or the value lies outside the
   * range of `signatures` the value defaults to zero or is ignored if
   * `signatures.length === 0`. Whenever possible implementors should
   * make an active decision about the active signature and shouldn't
   * rely on a default value.
   * In future version of the protocol this property might become
   * mandantory to better express this.
   */
  public void setActiveSignature(final Integer activeSignature) {
    this.activeSignature = activeSignature;
  }
  
  /**
   * The active parameter of the active signature. If omitted or the value
   * lies outside the range of `signatures[activeSignature].parameters`
   * defaults to 0 if the active signature has parameters. If
   * the active signature has no parameters it is ignored.
   * In future version of the protocol this property might become
   * mandantory to better express the active parameter if the
   * active signature does have any.
   */
  @Pure
  public Integer getActiveParameter() {
    return this.activeParameter;
  }
  
  /**
   * The active parameter of the active signature. If omitted or the value
   * lies outside the range of `signatures[activeSignature].parameters`
   * defaults to 0 if the active signature has parameters. If
   * the active signature has no parameters it is ignored.
   * In future version of the protocol this property might become
   * mandantory to better express the active parameter if the
   * active signature does have any.
   */
  public void setActiveParameter(final Integer activeParameter) {
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
    int result = 1;
    result = prime * result + ((this.signatures== null) ? 0 : this.signatures.hashCode());
    result = prime * result + ((this.activeSignature== null) ? 0 : this.activeSignature.hashCode());
    return prime * result + ((this.activeParameter== null) ? 0 : this.activeParameter.hashCode());
  }
}
