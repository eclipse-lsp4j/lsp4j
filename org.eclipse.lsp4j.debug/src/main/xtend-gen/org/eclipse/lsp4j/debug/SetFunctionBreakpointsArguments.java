/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import java.util.Arrays;
import org.eclipse.lsp4j.debug.FunctionBreakpoint;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'setFunctionBreakpoints' request.
 */
@SuppressWarnings("all")
public class SetFunctionBreakpointsArguments {
  /**
   * The function names of the breakpoints.
   */
  private FunctionBreakpoint[] breakpoints;
  
  /**
   * The function names of the breakpoints.
   */
  @Pure
  public FunctionBreakpoint[] getBreakpoints() {
    return this.breakpoints;
  }
  
  /**
   * The function names of the breakpoints.
   */
  public void setBreakpoints(final FunctionBreakpoint[] breakpoints) {
    this.breakpoints = breakpoints;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("breakpoints", this.breakpoints);
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
    SetFunctionBreakpointsArguments other = (SetFunctionBreakpointsArguments) obj;
    if (this.breakpoints == null) {
      if (other.breakpoints != null)
        return false;
    } else if (!Arrays.deepEquals(this.breakpoints, other.breakpoints))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.breakpoints== null) ? 0 : Arrays.deepHashCode(this.breakpoints));
    return result;
  }
}
