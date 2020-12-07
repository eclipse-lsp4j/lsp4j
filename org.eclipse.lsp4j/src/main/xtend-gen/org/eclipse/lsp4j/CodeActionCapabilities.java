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

import com.google.common.annotations.Beta;
import org.eclipse.lsp4j.CodeActionLiteralSupportCapabilities;
import org.eclipse.lsp4j.CodeActionResolveSupportCapabilities;
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
  
  /**
   * Whether code action supports the `disabled` property.
   * 
   * Since 3.16.0
   */
  @Beta
  private Boolean disabledSupport;
  
  /**
   * Whether code action supports the `data` property which is
   * preserved between a `textDocument/codeAction` and a
   * `codeAction/resolve` request.
   * 
   * Since 3.16.0
   */
  @Beta
  private Boolean dataSupport;
  
  /**
   * Whether the client supports resolving additional code action
   * properties via a separate `codeAction/resolve` request.
   * 
   * Since 3.16.0
   */
  @Beta
  private CodeActionResolveSupportCapabilities resolveSupport;
  
  /**
   * Whether the client honors the change annotations in
   * text edits and resource operations returned via the
   * `CodeAction#edit` property by for example presenting
   * the workspace edit in the user interface and asking
   * for confirmation.
   * 
   * Since 3.16.0
   */
  @Beta
  private Boolean honorsChangeAnnotations;
  
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
  
  /**
   * Whether code action supports the `disabled` property.
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getDisabledSupport() {
    return this.disabledSupport;
  }
  
  /**
   * Whether code action supports the `disabled` property.
   * 
   * Since 3.16.0
   */
  public void setDisabledSupport(final Boolean disabledSupport) {
    this.disabledSupport = disabledSupport;
  }
  
  /**
   * Whether code action supports the `data` property which is
   * preserved between a `textDocument/codeAction` and a
   * `codeAction/resolve` request.
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getDataSupport() {
    return this.dataSupport;
  }
  
  /**
   * Whether code action supports the `data` property which is
   * preserved between a `textDocument/codeAction` and a
   * `codeAction/resolve` request.
   * 
   * Since 3.16.0
   */
  public void setDataSupport(final Boolean dataSupport) {
    this.dataSupport = dataSupport;
  }
  
  /**
   * Whether the client supports resolving additional code action
   * properties via a separate `codeAction/resolve` request.
   * 
   * Since 3.16.0
   */
  @Pure
  public CodeActionResolveSupportCapabilities getResolveSupport() {
    return this.resolveSupport;
  }
  
  /**
   * Whether the client supports resolving additional code action
   * properties via a separate `codeAction/resolve` request.
   * 
   * Since 3.16.0
   */
  public void setResolveSupport(final CodeActionResolveSupportCapabilities resolveSupport) {
    this.resolveSupport = resolveSupport;
  }
  
  /**
   * Whether the client honors the change annotations in
   * text edits and resource operations returned via the
   * `CodeAction#edit` property by for example presenting
   * the workspace edit in the user interface and asking
   * for confirmation.
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getHonorsChangeAnnotations() {
    return this.honorsChangeAnnotations;
  }
  
  /**
   * Whether the client honors the change annotations in
   * text edits and resource operations returned via the
   * `CodeAction#edit` property by for example presenting
   * the workspace edit in the user interface and asking
   * for confirmation.
   * 
   * Since 3.16.0
   */
  public void setHonorsChangeAnnotations(final Boolean honorsChangeAnnotations) {
    this.honorsChangeAnnotations = honorsChangeAnnotations;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("codeActionLiteralSupport", this.codeActionLiteralSupport);
    b.add("isPreferredSupport", this.isPreferredSupport);
    b.add("disabledSupport", this.disabledSupport);
    b.add("dataSupport", this.dataSupport);
    b.add("resolveSupport", this.resolveSupport);
    b.add("honorsChangeAnnotations", this.honorsChangeAnnotations);
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
    if (this.disabledSupport == null) {
      if (other.disabledSupport != null)
        return false;
    } else if (!this.disabledSupport.equals(other.disabledSupport))
      return false;
    if (this.dataSupport == null) {
      if (other.dataSupport != null)
        return false;
    } else if (!this.dataSupport.equals(other.dataSupport))
      return false;
    if (this.resolveSupport == null) {
      if (other.resolveSupport != null)
        return false;
    } else if (!this.resolveSupport.equals(other.resolveSupport))
      return false;
    if (this.honorsChangeAnnotations == null) {
      if (other.honorsChangeAnnotations != null)
        return false;
    } else if (!this.honorsChangeAnnotations.equals(other.honorsChangeAnnotations))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.codeActionLiteralSupport== null) ? 0 : this.codeActionLiteralSupport.hashCode());
    result = prime * result + ((this.isPreferredSupport== null) ? 0 : this.isPreferredSupport.hashCode());
    result = prime * result + ((this.disabledSupport== null) ? 0 : this.disabledSupport.hashCode());
    result = prime * result + ((this.dataSupport== null) ? 0 : this.dataSupport.hashCode());
    result = prime * result + ((this.resolveSupport== null) ? 0 : this.resolveSupport.hashCode());
    return prime * result + ((this.honorsChangeAnnotations== null) ? 0 : this.honorsChangeAnnotations.hashCode());
  }
}
