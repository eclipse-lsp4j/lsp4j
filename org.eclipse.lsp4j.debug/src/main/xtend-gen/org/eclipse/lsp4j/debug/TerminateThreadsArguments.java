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

import java.util.Arrays;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'terminateThreads' request.
 */
@SuppressWarnings("all")
public class TerminateThreadsArguments {
  /**
   * Ids of threads to be terminated.
   * <p>
   * This is an optional property.
   */
  private int[] threadIds;
  
  /**
   * Ids of threads to be terminated.
   * <p>
   * This is an optional property.
   */
  @Pure
  public int[] getThreadIds() {
    return this.threadIds;
  }
  
  /**
   * Ids of threads to be terminated.
   * <p>
   * This is an optional property.
   */
  public void setThreadIds(final int[] threadIds) {
    this.threadIds = threadIds;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("threadIds", this.threadIds);
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
    TerminateThreadsArguments other = (TerminateThreadsArguments) obj;
    if (this.threadIds == null) {
      if (other.threadIds != null)
        return false;
    } else if (!Arrays.equals(this.threadIds, other.threadIds))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.threadIds== null) ? 0 : Arrays.hashCode(this.threadIds));
  }
}
