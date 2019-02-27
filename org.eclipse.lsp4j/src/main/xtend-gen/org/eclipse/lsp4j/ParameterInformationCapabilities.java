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

/**
 * Client capabilities specific to parameter information.
 */
@SuppressWarnings("all")
public class ParameterInformationCapabilities {
  /**
   * The client supports processing label offsets instead of a
   * simple label string.
   * 
   * Since 3.14.0
   */
  private Boolean labelOffsetSupport;
  
  public ParameterInformationCapabilities() {
  }
  
  public ParameterInformationCapabilities(final Boolean labelOffsetSupport) {
    this.labelOffsetSupport = labelOffsetSupport;
  }
  
  /**
   * The client supports processing label offsets instead of a
   * simple label string.
   * 
   * Since 3.14.0
   */
  @Pure
  public Boolean getLabelOffsetSupport() {
    return this.labelOffsetSupport;
  }
  
  /**
   * The client supports processing label offsets instead of a
   * simple label string.
   * 
   * Since 3.14.0
   */
  public void setLabelOffsetSupport(final Boolean labelOffsetSupport) {
    this.labelOffsetSupport = labelOffsetSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("labelOffsetSupport", this.labelOffsetSupport);
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
    ParameterInformationCapabilities other = (ParameterInformationCapabilities) obj;
    if (this.labelOffsetSupport == null) {
      if (other.labelOffsetSupport != null)
        return false;
    } else if (!this.labelOffsetSupport.equals(other.labelOffsetSupport))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.labelOffsetSupport== null) ? 0 : this.labelOffsetSupport.hashCode());
  }
}
