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

import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Properties of a breakpoint passed to the setFunctionBreakpoints request.
 */
@SuppressWarnings("all")
public class FunctionBreakpoint {
  /**
   * The name of the function.
   */
  @NonNull
  private String name;
  
  /**
   * An optional expression for conditional breakpoints.
   * <p>
   * It is only honored by a debug adapter if the capability 'supportsConditionalBreakpoints' is true.
   * <p>
   * This is an optional property.
   */
  private String condition;
  
  /**
   * An optional expression that controls how many hits of the breakpoint are ignored.
   * <p>
   * The backend is expected to interpret the expression as needed.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportsHitConditionalBreakpoints' is true.
   * <p>
   * This is an optional property.
   */
  private String hitCondition;
  
  /**
   * The name of the function.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * The name of the function.
   */
  public void setName(@NonNull final String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }
  
  /**
   * An optional expression for conditional breakpoints.
   * <p>
   * It is only honored by a debug adapter if the capability 'supportsConditionalBreakpoints' is true.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getCondition() {
    return this.condition;
  }
  
  /**
   * An optional expression for conditional breakpoints.
   * <p>
   * It is only honored by a debug adapter if the capability 'supportsConditionalBreakpoints' is true.
   * <p>
   * This is an optional property.
   */
  public void setCondition(final String condition) {
    this.condition = condition;
  }
  
  /**
   * An optional expression that controls how many hits of the breakpoint are ignored.
   * <p>
   * The backend is expected to interpret the expression as needed.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportsHitConditionalBreakpoints' is true.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getHitCondition() {
    return this.hitCondition;
  }
  
  /**
   * An optional expression that controls how many hits of the breakpoint are ignored.
   * <p>
   * The backend is expected to interpret the expression as needed.
   * <p>
   * The attribute is only honored by a debug adapter if the capability 'supportsHitConditionalBreakpoints' is true.
   * <p>
   * This is an optional property.
   */
  public void setHitCondition(final String hitCondition) {
    this.hitCondition = hitCondition;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("name", this.name);
    b.add("condition", this.condition);
    b.add("hitCondition", this.hitCondition);
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
    FunctionBreakpoint other = (FunctionBreakpoint) obj;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.condition == null) {
      if (other.condition != null)
        return false;
    } else if (!this.condition.equals(other.condition))
      return false;
    if (this.hitCondition == null) {
      if (other.hitCondition != null)
        return false;
    } else if (!this.hitCondition.equals(other.hitCondition))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.condition== null) ? 0 : this.condition.hashCode());
    return prime * result + ((this.hitCondition== null) ? 0 : this.hitCondition.hashCode());
  }
}
