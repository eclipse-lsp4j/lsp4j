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
 * This event signals that some state in the debug adapter has changed and requires that the client needs to
 * re-render the data snapshot previously requested.
 * <p>
 * Debug adapters do not have to emit this event for runtime changes like stopped or thread events because in that
 * case the client refetches the new state anyway. But the event can be used for example to refresh the UI after
 * rendering formatting has changed in the debug adapter.
 * <p>
 * This event should only be sent if the debug adapter has received a value true for the
 * 'supportsInvalidatedEvent' capability of the 'initialize' request.
 */
@SuppressWarnings("all")
public class InvalidatedEventArguments {
  /**
   * Optional set of logical areas that got invalidated. This property has a hint characteristic: a client can only
   * be expected to make a 'best effort' in honouring the areas but there are no guarantees. If this property is
   * missing, empty, or if values are not understand the client should assume a single value 'all'.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link InvalidatedAreas}
   */
  private String[] areas;
  
  /**
   * If specified, the client only needs to refetch data related to this thread.
   * <p>
   * This is an optional property.
   */
  private Integer threadId;
  
  /**
   * If specified, the client only needs to refetch data related to this stack frame (and the 'threadId' is
   * ignored).
   * <p>
   * This is an optional property.
   */
  private Integer stackFrameId;
  
  /**
   * Optional set of logical areas that got invalidated. This property has a hint characteristic: a client can only
   * be expected to make a 'best effort' in honouring the areas but there are no guarantees. If this property is
   * missing, empty, or if values are not understand the client should assume a single value 'all'.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link InvalidatedAreas}
   */
  @Pure
  public String[] getAreas() {
    return this.areas;
  }
  
  /**
   * Optional set of logical areas that got invalidated. This property has a hint characteristic: a client can only
   * be expected to make a 'best effort' in honouring the areas but there are no guarantees. If this property is
   * missing, empty, or if values are not understand the client should assume a single value 'all'.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link InvalidatedAreas}
   */
  public void setAreas(final String[] areas) {
    this.areas = areas;
  }
  
  /**
   * If specified, the client only needs to refetch data related to this thread.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getThreadId() {
    return this.threadId;
  }
  
  /**
   * If specified, the client only needs to refetch data related to this thread.
   * <p>
   * This is an optional property.
   */
  public void setThreadId(final Integer threadId) {
    this.threadId = threadId;
  }
  
  /**
   * If specified, the client only needs to refetch data related to this stack frame (and the 'threadId' is
   * ignored).
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getStackFrameId() {
    return this.stackFrameId;
  }
  
  /**
   * If specified, the client only needs to refetch data related to this stack frame (and the 'threadId' is
   * ignored).
   * <p>
   * This is an optional property.
   */
  public void setStackFrameId(final Integer stackFrameId) {
    this.stackFrameId = stackFrameId;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("areas", this.areas);
    b.add("threadId", this.threadId);
    b.add("stackFrameId", this.stackFrameId);
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
    InvalidatedEventArguments other = (InvalidatedEventArguments) obj;
    if (this.areas == null) {
      if (other.areas != null)
        return false;
    } else if (!Arrays.deepEquals(this.areas, other.areas))
      return false;
    if (this.threadId == null) {
      if (other.threadId != null)
        return false;
    } else if (!this.threadId.equals(other.threadId))
      return false;
    if (this.stackFrameId == null) {
      if (other.stackFrameId != null)
        return false;
    } else if (!this.stackFrameId.equals(other.stackFrameId))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.areas== null) ? 0 : Arrays.deepHashCode(this.areas));
    result = prime * result + ((this.threadId== null) ? 0 : this.threadId.hashCode());
    return prime * result + ((this.stackFrameId== null) ? 0 : this.stackFrameId.hashCode());
  }
}
