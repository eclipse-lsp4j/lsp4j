/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to Initialize request.
 */
@SuppressWarnings("all")
public class RunInTerminalResponse {
  /**
   * The process ID.
   * <p>
   * This is an optional property.
   */
  private Integer processId;
  
  /**
   * The process ID.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getProcessId() {
    return this.processId;
  }
  
  /**
   * The process ID.
   * <p>
   * This is an optional property.
   */
  public void setProcessId(final Integer processId) {
    this.processId = processId;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("processId", this.processId);
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.processId== null) ? 0 : this.processId.hashCode());
    return result;
  }
}
