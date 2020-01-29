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

import org.eclipse.lsp4j.CodeActionLiteralSupportCapabilities;
import org.eclipse.lsp4j.DynamicRegistrationCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to the `textDocument/codeAction`
 */
@SuppressWarnings("all")
public class CodeActionCapabilities extends DynamicRegistrationCapabilities {
  /**
   * The client support code action literals as a valid
   * response of the `textDocument/codeAction` request.
   */
  private CodeActionLiteralSupportCapabilities codeActionLiteralSupport;
  
  /**
   * Whether code action supports the `isPreferred` property.
   * 
   * Since 3.15.0
   */
  private Boolean isPreferredSupport;
  
  public CodeActionCapabilities() {
  }
  
  public CodeActionCapabilities(final Boolean dynamicRegistration) {
    super(dynamicRegistration);
  }
  
  public CodeActionCapabilities(final CodeActionLiteralSupportCapabilities codeActionLiteralSupport, final Boolean dynamicRegistration) {
    super(dynamicRegistration);
    this.codeActionLiteralSupport = codeActionLiteralSupport;
  }
  
  public CodeActionCapabilities(final CodeActionLiteralSupportCapabilities codeActionLiteralSupport, final Boolean dynamicRegistration, final Boolean isPreferredSupport) {
    this(codeActionLiteralSupport, dynamicRegistration);
    this.isPreferredSupport = isPreferredSupport;
  }
  
  /**
   * The client support code action literals as a valid
   * response of the `textDocument/codeAction` request.
   */
  @Pure
  public CodeActionLiteralSupportCapabilities getCodeActionLiteralSupport() {
    return this.codeActionLiteralSupport;
  }
  
  /**
   * The client support code action literals as a valid
   * response of the `textDocument/codeAction` request.
   */
  public void setCodeActionLiteralSupport(final CodeActionLiteralSupportCapabilities codeActionLiteralSupport) {
    this.codeActionLiteralSupport = codeActionLiteralSupport;
  }
  
  /**
   * Whether code action supports the `isPreferred` property.
   * 
   * Since 3.15.0
   */
  @Pure
  public Boolean getIsPreferredSupport() {
    return this.isPreferredSupport;
  }
  
  /**
   * Whether code action supports the `isPreferred` property.
   * 
   * Since 3.15.0
   */
  public void setIsPreferredSupport(final Boolean isPreferredSupport) {
    this.isPreferredSupport = isPreferredSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("codeActionLiteralSupport", this.codeActionLiteralSupport);
    b.add("isPreferredSupport", this.isPreferredSupport);
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
    CodeActionCapabilities other = (CodeActionCapabilities) obj;
    if (this.codeActionLiteralSupport == null) {
      if (other.codeActionLiteralSupport != null)
        return false;
    } else if (!this.codeActionLiteralSupport.equals(other.codeActionLiteralSupport))
      return false;
    if (this.isPreferredSupport == null) {
      if (other.isPreferredSupport != null)
        return false;
    } else if (!this.isPreferredSupport.equals(other.isPreferredSupport))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.codeActionLiteralSupport== null) ? 0 : this.codeActionLiteralSupport.hashCode());
    return prime * result + ((this.isPreferredSupport== null) ? 0 : this.isPreferredSupport.hashCode());
  }
}
