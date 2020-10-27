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

import org.eclipse.lsp4j.debug.SteppingGranularity;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'stepOut' request.
 */
@SuppressWarnings("all")
public class StepOutArguments {
  /**
   * Execute 'stepOut' for this thread.
   */
  private int threadId;
  
  /**
   * Optional granularity to step. If no granularity is specified, a granularity of 'statement' is assumed.
   * <p>
   * This is an optional property.
   */
  private SteppingGranularity granularity;
  
  /**
   * Execute 'stepOut' for this thread.
   */
  @Pure
  public int getThreadId() {
    return this.threadId;
  }
  
  /**
   * Execute 'stepOut' for this thread.
   */
  public void setThreadId(final int threadId) {
    this.threadId = threadId;
  }
  
  /**
   * Optional granularity to step. If no granularity is specified, a granularity of 'statement' is assumed.
   * <p>
   * This is an optional property.
   */
  @Pure
  public SteppingGranularity getGranularity() {
    return this.granularity;
  }
  
  /**
   * Optional granularity to step. If no granularity is specified, a granularity of 'statement' is assumed.
   * <p>
   * This is an optional property.
   */
  public void setGranularity(final SteppingGranularity granularity) {
    this.granularity = granularity;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("threadId", this.threadId);
    b.add("granularity", this.granularity);
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
    StepOutArguments other = (StepOutArguments) obj;
    if (other.threadId != this.threadId)
      return false;
    if (this.granularity == null) {
      if (other.granularity != null)
        return false;
    } else if (!this.granularity.equals(other.granularity))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.threadId;
    return prime * result + ((this.granularity== null) ? 0 : this.granularity.hashCode());
  }
}
