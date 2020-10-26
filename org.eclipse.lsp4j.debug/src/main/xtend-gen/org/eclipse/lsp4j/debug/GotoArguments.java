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
 * Arguments for 'goto' request.
 */
@SuppressWarnings("all")
public class GotoArguments {
  /**
   * Set the goto target for this thread.
   */
  private int threadId;
  
  /**
   * The location where the debuggee will continue to run.
   */
  private int targetId;
  
  /**
   * Set the goto target for this thread.
   */
  @Pure
  public int getThreadId() {
    return this.threadId;
  }
  
  /**
   * Set the goto target for this thread.
   */
  public void setThreadId(final int threadId) {
    this.threadId = threadId;
  }
  
  /**
   * The location where the debuggee will continue to run.
   */
  @Pure
  public int getTargetId() {
    return this.targetId;
  }
  
  /**
   * The location where the debuggee will continue to run.
   */
  public void setTargetId(final int targetId) {
    this.targetId = targetId;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("threadId", this.threadId);
    b.add("targetId", this.targetId);
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
    GotoArguments other = (GotoArguments) obj;
    if (other.threadId != this.threadId)
      return false;
    if (other.targetId != this.targetId)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.threadId;
    return prime * result + this.targetId;
  }
}
