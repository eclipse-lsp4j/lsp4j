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
 * Arguments for 'continue' request.
 */
@SuppressWarnings("all")
public class ContinueArguments {
  /**
   * Continue execution for the specified thread (if possible). If the backend cannot continue on a single thread
   * but will continue on all threads, it should set the allThreadsContinued attribute in the response to true.
   */
  private Integer threadId;
  
  /**
   * Continue execution for the specified thread (if possible). If the backend cannot continue on a single thread
   * but will continue on all threads, it should set the allThreadsContinued attribute in the response to true.
   */
  @Pure
  public Integer getThreadId() {
    return this.threadId;
  }
  
  /**
   * Continue execution for the specified thread (if possible). If the backend cannot continue on a single thread
   * but will continue on all threads, it should set the allThreadsContinued attribute in the response to true.
   */
  public void setThreadId(final Integer threadId) {
    this.threadId = threadId;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("threadId", this.threadId);
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
    ContinueArguments other = (ContinueArguments) obj;
    if (this.threadId == null) {
      if (other.threadId != null)
        return false;
    } else if (!this.threadId.equals(other.threadId))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.threadId== null) ? 0 : this.threadId.hashCode());
    return result;
  }
}
