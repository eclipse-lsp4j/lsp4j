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
 * Capabilities specific to the `textDocument/typeDefinition`
 * 
 * Since 3.6.0
 */
@SuppressWarnings("all")
public class TypeDefinitionCapabilities extends DynamicRegistrationCapabilities {
  /**
   * The client supports additional metadata in the form of definition links.
   * 
   * Since 3.14.0
   */
  private Boolean linkSupport;
  
  public TypeDefinitionCapabilities() {
  }
  
  public TypeDefinitionCapabilities(final Boolean dynamicRegistration) {
    super(dynamicRegistration);
  }
  
  public TypeDefinitionCapabilities(final Boolean dynamicRegistration, final Boolean linkSupport) {
    super(dynamicRegistration);
    this.linkSupport = linkSupport;
  }
  
  /**
   * The client supports additional metadata in the form of definition links.
   * 
   * Since 3.14.0
   */
  @Pure
  public Boolean getLinkSupport() {
    return this.linkSupport;
  }
  
  /**
   * The client supports additional metadata in the form of definition links.
   * 
   * Since 3.14.0
   */
  public void setLinkSupport(final Boolean linkSupport) {
    this.linkSupport = linkSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("linkSupport", this.linkSupport);
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
    TypeDefinitionCapabilities other = (TypeDefinitionCapabilities) obj;
    if (this.linkSupport == null) {
      if (other.linkSupport != null)
        return false;
    } else if (!this.linkSupport.equals(other.linkSupport))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.linkSupport== null) ? 0 : this.linkSupport.hashCode());
  }
}
