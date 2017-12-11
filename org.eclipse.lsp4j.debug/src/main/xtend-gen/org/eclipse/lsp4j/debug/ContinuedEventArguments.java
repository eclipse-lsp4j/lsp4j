/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event message for 'continued' event type.
 * <p>
 * The event indicates that the execution of the debuggee has continued.
 * <p>
 * Please note: a debug adapter is not expected to send this event in response to a request that implies that
 * execution continues, e.g. 'launch' or 'continue'.
 * <p>
 * It is only necessary to send a ContinuedEvent if there was no previous request that implied this.
 */
@SuppressWarnings("all")
public class ContinuedEventArguments {
  /**
   * The thread which was continued.
   */
  @NonNull
  private Integer threadId;
  
  /**
   * If allThreadsContinued is true, a debug adapter can announce that all threads have continued.
   * <p>
   * This is an optional property.
   */
  private Boolean allThreadsContinued;
  
  /**
   * The thread which was continued.
   */
  @Pure
  @NonNull
  public Integer getThreadId() {
    return this.threadId;
  }
  
  /**
   * The thread which was continued.
   */
  public void setThreadId(@NonNull final Integer threadId) {
    this.threadId = threadId;
  }
  
  /**
   * If allThreadsContinued is true, a debug adapter can announce that all threads have continued.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getAllThreadsContinued() {
    return this.allThreadsContinued;
  }
  
  /**
   * If allThreadsContinued is true, a debug adapter can announce that all threads have continued.
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
    if (this.threadId == null) {
      if (other.threadId != null)
        return false;
    } else if (!this.threadId.equals(other.threadId))
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
    result = prime * result + ((this.threadId== null) ? 0 : this.threadId.hashCode());
    result = prime * result + ((this.allThreadsContinued== null) ? 0 : this.allThreadsContinued.hashCode());
    return result;
  }
}
