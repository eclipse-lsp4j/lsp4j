/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.debug.Breakpoint;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event message for 'breakpoint' event type.
 * <p>
 * The event indicates that some information about a breakpoint has changed.
 */
@SuppressWarnings("all")
public class BreakpointEventArguments {
  /**
   * The reason for the event.
   * <p>
   * Possible values include - but not limited to those defined in {@link BreakpointEventArgumentsReason}
   */
  private String reason;
  
  /**
   * The breakpoint.
   */
  private Breakpoint breakpoint;
  
  /**
   * The reason for the event.
   * <p>
   * Possible values include - but not limited to those defined in {@link BreakpointEventArgumentsReason}
   */
  @Pure
  public String getReason() {
    return this.reason;
  }
  
  /**
   * The reason for the event.
   * <p>
   * Possible values include - but not limited to those defined in {@link BreakpointEventArgumentsReason}
   */
  public void setReason(final String reason) {
    this.reason = reason;
  }
  
  /**
   * The breakpoint.
   */
  @Pure
  public Breakpoint getBreakpoint() {
    return this.breakpoint;
  }
  
  /**
   * The breakpoint.
   */
  public void setBreakpoint(final Breakpoint breakpoint) {
    this.breakpoint = breakpoint;
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
    result = prime * result + ((this.breakpoint== null) ? 0 : this.breakpoint.hashCode());
    return result;
  }
}
