/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
  
  public CodeActionCapabilities() {
  }
  
  public CodeActionCapabilities(final Boolean dynamicRegistration) {
    super(dynamicRegistration);
  }
  
  public CodeActionCapabilities(final CodeActionLiteralSupportCapabilities codeActionLiteralSupport, final Boolean dynamicRegistration) {
    super(dynamicRegistration);
    this.codeActionLiteralSupport = codeActionLiteralSupport;
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
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("codeActionLiteralSupport", this.codeActionLiteralSupport);
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.codeActionLiteralSupport== null) ? 0 : this.codeActionLiteralSupport.hashCode());
  }
}
