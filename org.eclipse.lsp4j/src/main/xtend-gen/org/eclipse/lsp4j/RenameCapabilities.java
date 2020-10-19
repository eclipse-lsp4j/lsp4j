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
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to the `textDocument/rename`
 */
@SuppressWarnings("all")
public class RenameCapabilities extends DynamicRegistrationCapabilities {
  /**
   * Client supports testing for validity of rename operations
   * before execution.
   * 
   * Since 3.12.0
   */
  private Boolean prepareSupport;
  
  /**
   * Client supports the default behavior result (`{ defaultBehavior: boolean }`).
   * 
   * Since 3.16.0
   */
  private Boolean prepareSupportDefaultBehavior;
  
  public RenameCapabilities() {
  }
  
  public RenameCapabilities(final Boolean dynamicRegistration) {
    super(dynamicRegistration);
  }
  
  public RenameCapabilities(final Boolean prepareSupport, final Boolean dynamicRegistration) {
    super(dynamicRegistration);
    this.prepareSupport = prepareSupport;
  }
  
  /**
   * Client supports testing for validity of rename operations
   * before execution.
   * 
   * Since 3.12.0
   */
  @Pure
  public Boolean getPrepareSupport() {
    return this.prepareSupport;
  }
  
  /**
   * Client supports testing for validity of rename operations
   * before execution.
   * 
   * Since 3.12.0
   */
  public void setPrepareSupport(final Boolean prepareSupport) {
    this.prepareSupport = prepareSupport;
  }
  
  /**
   * Client supports the default behavior result (`{ defaultBehavior: boolean }`).
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getPrepareSupportDefaultBehavior() {
    return this.prepareSupportDefaultBehavior;
  }
  
  /**
   * Client supports the default behavior result (`{ defaultBehavior: boolean }`).
   * 
   * Since 3.16.0
   */
  public void setPrepareSupportDefaultBehavior(final Boolean prepareSupportDefaultBehavior) {
    this.prepareSupportDefaultBehavior = prepareSupportDefaultBehavior;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("prepareSupport", this.prepareSupport);
    b.add("prepareSupportDefaultBehavior", this.prepareSupportDefaultBehavior);
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
    RenameCapabilities other = (RenameCapabilities) obj;
    if (this.prepareSupport == null) {
      if (other.prepareSupport != null)
        return false;
    } else if (!this.prepareSupport.equals(other.prepareSupport))
      return false;
    if (this.prepareSupportDefaultBehavior == null) {
      if (other.prepareSupportDefaultBehavior != null)
        return false;
    } else if (!this.prepareSupportDefaultBehavior.equals(other.prepareSupportDefaultBehavior))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.prepareSupport== null) ? 0 : this.prepareSupport.hashCode());
    return prime * result + ((this.prepareSupportDefaultBehavior== null) ? 0 : this.prepareSupportDefaultBehavior.hashCode());
  }
}
