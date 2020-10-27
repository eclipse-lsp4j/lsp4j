/**
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'continue' request.
 */
@SuppressWarnings("all")
public class ContinueResponse {
  /**
   * If true, the 'continue' request has ignored the specified thread and continued all threads instead.
   * <p>
   * If this attribute is missing a value of 'true' is assumed for backward compatibility.
   * <p>
   * This is an optional property.
   */
  private Boolean allThreadsContinued;
  
  /**
   * If true, the 'continue' request has ignored the specified thread and continued all threads instead.
   * <p>
   * If this attribute is missing a value of 'true' is assumed for backward compatibility.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getAllThreadsContinued() {
    return this.allThreadsContinued;
  }
  
  /**
   * If true, the 'continue' request has ignored the specified thread and continued all threads instead.
   * <p>
   * If this attribute is missing a value of 'true' is assumed for backward compatibility.
   * <p>
   * This is an optional property.
   */
  public void setAllThreadsContinued(final Boolean allThreadsContinued) {
    this.allThreadsContinued = allThreadsContinued;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("allThreadsContinued", this.allThreadsContinued);
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
    ContinueResponse other = (ContinueResponse) obj;
    if (this.allThreadsContinued == null) {
      if (other.allThreadsContinued != null)
        return false;
    } else if (!this.allThreadsContinued.equals(other.allThreadsContinued))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.allThreadsContinued== null) ? 0 : this.allThreadsContinued.hashCode());
  }
}
