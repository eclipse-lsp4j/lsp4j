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
 * The result of an show document request.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class ShowDocumentResult {
  /**
   * A boolean indicating if the show was successful.
   */
  private boolean success;
  
  public ShowDocumentResult() {
  }
  
  public ShowDocumentResult(final boolean success) {
    this.success = success;
  }
  
  /**
   * A boolean indicating if the show was successful.
   */
  @Pure
  public boolean isSuccess() {
    return this.success;
  }
  
  /**
   * A boolean indicating if the show was successful.
   */
  public void setSuccess(final boolean success) {
    this.success = success;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("success", this.success);
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
    ShowDocumentResult other = (ShowDocumentResult) obj;
    if (other.success != this.success)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + (this.success ? 1231 : 1237);
  }
}
