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
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Client capabilities for the show document request.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class ShowDocumentCapabilities {
  /**
   * The client has support for the show document
   * request.
   */
  private boolean support;
  
  public ShowDocumentCapabilities() {
  }
  
  public ShowDocumentCapabilities(final boolean support) {
    this.support = support;
  }
  
  /**
   * The client has support for the show document
   * request.
   */
  @Pure
  public boolean isSupport() {
    return this.support;
  }
  
  /**
   * The client has support for the show document
   * request.
   */
  public void setSupport(final boolean support) {
    this.support = support;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("support", this.support);
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
    ShowDocumentCapabilities other = (ShowDocumentCapabilities) obj;
    if (other.support != this.support)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + (this.support ? 1231 : 1237);
  }
}
