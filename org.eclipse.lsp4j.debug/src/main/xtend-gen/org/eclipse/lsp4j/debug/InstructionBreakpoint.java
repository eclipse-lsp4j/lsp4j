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
 * Properties of a breakpoint passed to the setInstructionBreakpoints request
 */
@SuppressWarnings("all")
public class InstructionBreakpoint {
  /**
   * The instruction reference of the breakpoint.
   * <p>
   * This should be a memory or instruction pointer reference from an EvaluateResponse, Variable, StackFrame,
   * GotoTarget, or Breakpoint.
   */
  @NonNull
  private String instructionReference;
  
  /**
   * An optional offset from the instruction reference.
   * <p>
   * This can be negative.
   * <p>
   * This is an optional property.
   */
  private Integer offset;
  
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
   * The instruction reference of the breakpoint.
   * <p>
   * This should be a memory or instruction pointer reference from an EvaluateResponse, Variable, StackFrame,
   * GotoTarget, or Breakpoint.
   */
  @Pure
  @NonNull
  public String getInstructionReference() {
    return this.instructionReference;
  }
  
  /**
   * The instruction reference of the breakpoint.
   * <p>
   * This should be a memory or instruction pointer reference from an EvaluateResponse, Variable, StackFrame,
   * GotoTarget, or Breakpoint.
   */
  public void setInstructionReference(@NonNull final String instructionReference) {
    this.instructionReference = Preconditions.checkNotNull(instructionReference, "instructionReference");
  }
  
  /**
   * An optional offset from the instruction reference.
   * <p>
   * This can be negative.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getOffset() {
    return this.offset;
  }
  
  /**
   * An optional offset from the instruction reference.
   * <p>
   * This can be negative.
   * <p>
   * This is an optional property.
   */
  public void setOffset(final Integer offset) {
    this.offset = offset;
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
    b.add("instructionReference", this.instructionReference);
    b.add("offset", this.offset);
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
    InstructionBreakpoint other = (InstructionBreakpoint) obj;
    if (this.instructionReference == null) {
      if (other.instructionReference != null)
        return false;
    } else if (!this.instructionReference.equals(other.instructionReference))
      return false;
    if (this.offset == null) {
      if (other.offset != null)
        return false;
    } else if (!this.offset.equals(other.offset))
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
    result = prime * result + ((this.instructionReference== null) ? 0 : this.instructionReference.hashCode());
    result = prime * result + ((this.offset== null) ? 0 : this.offset.hashCode());
    result = prime * result + ((this.condition== null) ? 0 : this.condition.hashCode());
    return prime * result + ((this.hitCondition== null) ? 0 : this.hitCondition.hashCode());
  }
}
