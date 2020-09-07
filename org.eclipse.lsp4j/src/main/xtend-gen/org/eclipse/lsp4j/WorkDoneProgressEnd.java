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

import org.eclipse.lsp4j.WorkDoneProgressKind;
import org.eclipse.lsp4j.WorkDoneProgressNotification;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The notification payload about progress reporting.
 * Signaling the end of a progress reporting is done using the following payload:
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public class WorkDoneProgressEnd implements WorkDoneProgressNotification {
  /**
   * Always return end
   */
  @Override
  public WorkDoneProgressKind getKind() {
    return WorkDoneProgressKind.end;
  }
  
  /**
   * Optional, a final message indicating to for example indicate the outcome
   * of the operation.
   */
  private String message;
  
  /**
   * Optional, a final message indicating to for example indicate the outcome
   * of the operation.
   */
  @Pure
  public String getMessage() {
    return this.message;
  }
  
  /**
   * Optional, a final message indicating to for example indicate the outcome
   * of the operation.
   */
  public void setMessage(final String message) {
    this.message = message;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("message", this.message);
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
    WorkDoneProgressEnd other = (WorkDoneProgressEnd) obj;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.message== null) ? 0 : this.message.hashCode());
  }
}
