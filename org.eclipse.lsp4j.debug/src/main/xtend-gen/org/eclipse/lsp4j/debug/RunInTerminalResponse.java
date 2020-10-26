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
 * Response to 'runInTerminal' request.
 */
@SuppressWarnings("all")
public class RunInTerminalResponse {
  /**
   * The process ID. The value should be less than or equal to 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  private Integer processId;
  
  /**
   * The process ID of the terminal shell. The value should be less than or equal to 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  private Integer shellProcessId;
  
  /**
   * The process ID. The value should be less than or equal to 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getProcessId() {
    return this.processId;
  }
  
  /**
   * The process ID. The value should be less than or equal to 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  public void setProcessId(final Integer processId) {
    this.processId = processId;
  }
  
  /**
   * The process ID of the terminal shell. The value should be less than or equal to 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getShellProcessId() {
    return this.shellProcessId;
  }
  
  /**
   * The process ID of the terminal shell. The value should be less than or equal to 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  public void setShellProcessId(final Integer shellProcessId) {
    this.shellProcessId = shellProcessId;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("processId", this.processId);
    b.add("shellProcessId", this.shellProcessId);
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
    RunInTerminalResponse other = (RunInTerminalResponse) obj;
    if (this.processId == null) {
      if (other.processId != null)
        return false;
    } else if (!this.processId.equals(other.processId))
      return false;
    if (this.shellProcessId == null) {
      if (other.shellProcessId != null)
        return false;
    } else if (!this.shellProcessId.equals(other.shellProcessId))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.processId== null) ? 0 : this.processId.hashCode());
    return prime * result + ((this.shellProcessId== null) ? 0 : this.shellProcessId.hashCode());
  }
}
