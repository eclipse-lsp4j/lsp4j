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

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class DynamicRegistrationCapabilities {
  /**
   * Supports dynamic registration.
   */
  private Boolean dynamicRegistration;
  
  public DynamicRegistrationCapabilities() {
  }
  
  public DynamicRegistrationCapabilities(final Boolean dynamicRegistration) {
    this.dynamicRegistration = dynamicRegistration;
  }
  
  /**
   * Supports dynamic registration.
   */
  @Pure
  public Boolean getDynamicRegistration() {
    return this.dynamicRegistration;
  }
  
  /**
   * Supports dynamic registration.
   */
  public void setDynamicRegistration(final Boolean dynamicRegistration) {
    this.dynamicRegistration = dynamicRegistration;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("dynamicRegistration", this.dynamicRegistration);
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
    DynamicRegistrationCapabilities other = (DynamicRegistrationCapabilities) obj;
    if (this.dynamicRegistration == null) {
      if (other.dynamicRegistration != null)
        return false;
    } else if (!this.dynamicRegistration.equals(other.dynamicRegistration))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.dynamicRegistration== null) ? 0 : this.dynamicRegistration.hashCode());
  }
}
