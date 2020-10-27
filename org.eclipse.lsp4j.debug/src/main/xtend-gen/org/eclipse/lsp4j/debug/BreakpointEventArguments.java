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

import org.eclipse.lsp4j.debug.Breakpoint;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The event indicates that some information about a breakpoint has changed.
 */
@SuppressWarnings("all")
public class BreakpointEventArguments {
  /**
   * The reason for the event.
   * <p>
   * Possible values include - but not limited to those defined in {@link BreakpointEventArgumentsReason}
   */
  @NonNull
  private String reason;
  
  /**
   * The 'id' attribute is used to find the target breakpoint and the other attributes are used as the new values.
   */
  @NonNull
  private Breakpoint breakpoint;
  
  /**
   * The reason for the event.
   * <p>
   * Possible values include - but not limited to those defined in {@link BreakpointEventArgumentsReason}
   */
  @Pure
  @NonNull
  public String getReason() {
    return this.reason;
  }
  
  /**
   * The reason for the event.
   * <p>
   * Possible values include - but not limited to those defined in {@link BreakpointEventArgumentsReason}
   */
  public void setReason(@NonNull final String reason) {
    this.reason = Preconditions.checkNotNull(reason, "reason");
  }
  
  /**
   * The 'id' attribute is used to find the target breakpoint and the other attributes are used as the new values.
   */
  @Pure
  @NonNull
  public Breakpoint getBreakpoint() {
    return this.breakpoint;
  }
  
  /**
   * The 'id' attribute is used to find the target breakpoint and the other attributes are used as the new values.
   */
  public void setBreakpoint(@NonNull final Breakpoint breakpoint) {
    this.breakpoint = Preconditions.checkNotNull(breakpoint, "breakpoint");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("reason", this.reason);
    b.add("breakpoint", this.breakpoint);
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
    BreakpointEventArguments other = (BreakpointEventArguments) obj;
    if (this.reason == null) {
      if (other.reason != null)
        return false;
    } else if (!this.reason.equals(other.reason))
      return false;
    if (this.breakpoint == null) {
      if (other.breakpoint != null)
        return false;
    } else if (!this.breakpoint.equals(other.breakpoint))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.reason== null) ? 0 : this.reason.hashCode());
    return prime * result + ((this.breakpoint== null) ? 0 : this.breakpoint.hashCode());
  }
}
