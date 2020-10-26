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

import org.eclipse.lsp4j.debug.DataBreakpointAccessType;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Properties of a data breakpoint passed to the setDataBreakpoints request.
 */
@SuppressWarnings("all")
public class DataBreakpoint {
  /**
   * An id representing the data. This id is returned from the dataBreakpointInfo request.
   */
  @NonNull
  private String dataId;
  
  /**
   * The access type of the data.
   * <p>
   * This is an optional property.
   */
  private DataBreakpointAccessType accessType;
  
  /**
   * An optional expression for conditional breakpoints.
   * <p>
   * This is an optional property.
   */
  private String condition;
  
  /**
   * An optional expression that controls how many hits of the breakpoint are ignored.
   * <p>
   * The backend is expected to interpret the expression as needed.
   * <p>
   * This is an optional property.
   */
  private String hitCondition;
  
  /**
   * An id representing the data. This id is returned from the dataBreakpointInfo request.
   */
  @Pure
  @NonNull
  public String getDataId() {
    return this.dataId;
  }
  
  /**
   * An id representing the data. This id is returned from the dataBreakpointInfo request.
   */
  public void setDataId(@NonNull final String dataId) {
    this.dataId = Preconditions.checkNotNull(dataId, "dataId");
  }
  
  /**
   * The access type of the data.
   * <p>
   * This is an optional property.
   */
  @Pure
  public DataBreakpointAccessType getAccessType() {
    return this.accessType;
  }
  
  /**
   * The access type of the data.
   * <p>
   * This is an optional property.
   */
  public void setAccessType(final DataBreakpointAccessType accessType) {
    this.accessType = accessType;
  }
  
  /**
   * An optional expression for conditional breakpoints.
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
   * This is an optional property.
   */
  public void setHitCondition(final String hitCondition) {
    this.hitCondition = hitCondition;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("dataId", this.dataId);
    b.add("accessType", this.accessType);
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
    DataBreakpoint other = (DataBreakpoint) obj;
    if (this.dataId == null) {
      if (other.dataId != null)
        return false;
    } else if (!this.dataId.equals(other.dataId))
      return false;
    if (this.accessType == null) {
      if (other.accessType != null)
        return false;
    } else if (!this.accessType.equals(other.accessType))
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
    result = prime * result + ((this.dataId== null) ? 0 : this.dataId.hashCode());
    result = prime * result + ((this.accessType== null) ? 0 : this.accessType.hashCode());
    result = prime * result + ((this.condition== null) ? 0 : this.condition.hashCode());
    return prime * result + ((this.hitCondition== null) ? 0 : this.hitCondition.hashCode());
  }
}
