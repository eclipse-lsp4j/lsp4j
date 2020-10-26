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
import org.eclipse.lsp4j.debug.DataBreakpointAccessType;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'dataBreakpointInfo' request.
 */
@SuppressWarnings("all")
public class DataBreakpointInfoResponse {
  /**
   * An identifier for the data on which a data breakpoint can be registered with the setDataBreakpoints request or
   * null if no data breakpoint is available.
   */
  private String dataId;
  
  /**
   * UI string that describes on what data the breakpoint is set on or why a data breakpoint is not available.
   */
  @NonNull
  private String description;
  
  /**
   * Optional attribute listing the available access types for a potential data breakpoint. A UI frontend could
   * surface this information.
   * <p>
   * This is an optional property.
   */
  private DataBreakpointAccessType[] accessTypes;
  
  /**
   * Optional attribute indicating that a potential data breakpoint could be persisted across sessions.
   * <p>
   * This is an optional property.
   */
  private Boolean canPersist;
  
  /**
   * An identifier for the data on which a data breakpoint can be registered with the setDataBreakpoints request or
   * null if no data breakpoint is available.
   */
  @Pure
  public String getDataId() {
    return this.dataId;
  }
  
  /**
   * An identifier for the data on which a data breakpoint can be registered with the setDataBreakpoints request or
   * null if no data breakpoint is available.
   */
  public void setDataId(final String dataId) {
    this.dataId = dataId;
  }
  
  /**
   * UI string that describes on what data the breakpoint is set on or why a data breakpoint is not available.
   */
  @Pure
  @NonNull
  public String getDescription() {
    return this.description;
  }
  
  /**
   * UI string that describes on what data the breakpoint is set on or why a data breakpoint is not available.
   */
  public void setDescription(@NonNull final String description) {
    this.description = Preconditions.checkNotNull(description, "description");
  }
  
  /**
   * Optional attribute listing the available access types for a potential data breakpoint. A UI frontend could
   * surface this information.
   * <p>
   * This is an optional property.
   */
  @Pure
  public DataBreakpointAccessType[] getAccessTypes() {
    return this.accessTypes;
  }
  
  /**
   * Optional attribute listing the available access types for a potential data breakpoint. A UI frontend could
   * surface this information.
   * <p>
   * This is an optional property.
   */
  public void setAccessTypes(final DataBreakpointAccessType[] accessTypes) {
    this.accessTypes = accessTypes;
  }
  
  /**
   * Optional attribute indicating that a potential data breakpoint could be persisted across sessions.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getCanPersist() {
    return this.canPersist;
  }
  
  /**
   * Optional attribute indicating that a potential data breakpoint could be persisted across sessions.
   * <p>
   * This is an optional property.
   */
  public void setCanPersist(final Boolean canPersist) {
    this.canPersist = canPersist;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("dataId", this.dataId);
    b.add("description", this.description);
    b.add("accessTypes", this.accessTypes);
    b.add("canPersist", this.canPersist);
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
    DataBreakpointInfoResponse other = (DataBreakpointInfoResponse) obj;
    if (this.dataId == null) {
      if (other.dataId != null)
        return false;
    } else if (!this.dataId.equals(other.dataId))
      return false;
    if (this.description == null) {
      if (other.description != null)
        return false;
    } else if (!this.description.equals(other.description))
      return false;
    if (this.accessTypes == null) {
      if (other.accessTypes != null)
        return false;
    } else if (!Arrays.deepEquals(this.accessTypes, other.accessTypes))
      return false;
    if (this.canPersist == null) {
      if (other.canPersist != null)
        return false;
    } else if (!this.canPersist.equals(other.canPersist))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.dataId== null) ? 0 : this.dataId.hashCode());
    result = prime * result + ((this.description== null) ? 0 : this.description.hashCode());
    result = prime * result + ((this.accessTypes== null) ? 0 : Arrays.deepHashCode(this.accessTypes));
    return prime * result + ((this.canPersist== null) ? 0 : this.canPersist.hashCode());
  }
}
