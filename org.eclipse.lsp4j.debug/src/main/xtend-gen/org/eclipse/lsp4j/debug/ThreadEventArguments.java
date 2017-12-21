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
 * Event message for 'thread' event type.
 * <p>
 * The event indicates that a thread has started or exited.
 */
@SuppressWarnings("all")
public class ThreadEventArguments {
  /**
   * The reason for the event.
   * <p>
   * Possible values include - but not limited to those defined in {@link ThreadEventArgumentsReason}
   */
  @NonNull
  private String reason;
  
  /**
   * The identifier of the thread.
   */
  @NonNull
  private Long threadId;
  
  /**
   * The reason for the event.
   * <p>
   * Possible values include - but not limited to those defined in {@link ThreadEventArgumentsReason}
   */
  @Pure
  @NonNull
  public String getReason() {
    return this.reason;
  }
  
  /**
   * The reason for the event.
   * <p>
   * Possible values include - but not limited to those defined in {@link ThreadEventArgumentsReason}
   */
  public void setReason(@NonNull final String reason) {
    this.reason = reason;
  }
  
  /**
   * The identifier of the thread.
   */
  @Pure
  @NonNull
  public Long getThreadId() {
    return this.threadId;
  }
  
  /**
   * The identifier of the thread.
   */
  public void setThreadId(@NonNull final Long threadId) {
    this.threadId = threadId;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("reason", this.reason);
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
    ThreadEventArguments other = (ThreadEventArguments) obj;
    if (this.reason == null) {
      if (other.reason != null)
        return false;
    } else if (!this.reason.equals(other.reason))
      return false;
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
    result = prime * result + ((this.reason== null) ? 0 : this.reason.hashCode());
    result = prime * result + ((this.threadId== null) ? 0 : this.threadId.hashCode());
    return result;
  }
}
