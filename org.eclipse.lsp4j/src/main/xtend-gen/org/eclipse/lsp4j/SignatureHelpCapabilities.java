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

import org.eclipse.lsp4j.DynamicRegistrationCapabilities;
import org.eclipse.lsp4j.SignatureInformationCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to the `textDocument/signatureHelp`
 */
@SuppressWarnings("all")
public class SignatureHelpCapabilities extends DynamicRegistrationCapabilities {
  /**
   * The client supports the following `SignatureInformation`
   * specific properties.
   */
  private SignatureInformationCapabilities signatureInformation;
  
  public SignatureHelpCapabilities() {
  }
  
  public SignatureHelpCapabilities(final Boolean dynamicRegistration) {
    super(dynamicRegistration);
  }
  
  public SignatureHelpCapabilities(final SignatureInformationCapabilities signatureInformation, final Boolean dynamicRegistration) {
    super(dynamicRegistration);
    this.signatureInformation = signatureInformation;
  }
  
  /**
   * The client supports the following `SignatureInformation`
   * specific properties.
   */
  @Pure
  public SignatureInformationCapabilities getSignatureInformation() {
    return this.signatureInformation;
  }
  
  /**
   * The client supports the following `SignatureInformation`
   * specific properties.
   */
  public void setSignatureInformation(final SignatureInformationCapabilities signatureInformation) {
    this.signatureInformation = signatureInformation;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("signatureInformation", this.signatureInformation);
    b.add("dynamicRegistration", getDynamicRegistration());
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
    SignatureHelpCapabilities other = (SignatureHelpCapabilities) obj;
    if (this.signatureInformation == null) {
      if (other.signatureInformation != null)
        return false;
    } else if (!this.signatureInformation.equals(other.signatureInformation))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.signatureInformation== null) ? 0 : this.signatureInformation.hashCode());
  }
}
