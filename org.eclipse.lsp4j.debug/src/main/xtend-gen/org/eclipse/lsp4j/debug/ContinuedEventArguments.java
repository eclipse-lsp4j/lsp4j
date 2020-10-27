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
 * The event indicates that the execution of the debuggee has continued.
 * <p>
 * Please note: a debug adapter is not expected to send this event in response to a request that implies that
 * execution continues, e.g. 'launch' or 'continue'.
 * <p>
 * It is only necessary to send a 'continued' event if there was no previous request that implied this.
 */
@SuppressWarnings("all")
public class ContinuedEventArguments {
  /**
   * The thread which was continued.
   */
  private int threadId;
  
  /**
   * If 'allThreadsContinued' is true, a debug adapter can announce that all threads have continued.
   * <p>
   * This is an optional property.
   */
  private Boolean allThreadsContinued;
  
  /**
   * The thread which was continued.
   */
  @Pure
  public int getThreadId() {
    return this.threadId;
  }
  
  /**
   * The thread which was continued.
   */
  public void setThreadId(final int threadId) {
    this.threadId = threadId;
  }
  
  /**
   * If 'allThreadsContinued' is true, a debug adapter can announce that all threads have continued.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getAllThreadsContinued() {
    return this.allThreadsContinued;
  }
  
  /**
   * If 'allThreadsContinued' is true, a debug adapter can announce that all threads have continued.
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
    b.add("threadId", this.threadId);
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
    ContinuedEventArguments other = (ContinuedEventArguments) obj;
    if (other.threadId != this.threadId)
      return false;
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
    final int prime = 31;
    int result = 1;
    result = prime * result + this.threadId;
    return prime * result + ((this.allThreadsContinued== null) ? 0 : this.allThreadsContinued.hashCode());
  }
}
