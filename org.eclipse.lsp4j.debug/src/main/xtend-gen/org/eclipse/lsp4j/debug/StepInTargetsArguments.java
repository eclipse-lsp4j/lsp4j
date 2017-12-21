/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'stepInTargets' request.
 */
@SuppressWarnings("all")
public class StepInTargetsArguments {
  /**
   * The stack frame for which to retrieve the possible stepIn targets.
   */
  @NonNull
  private Long frameId;
  
  /**
   * The stack frame for which to retrieve the possible stepIn targets.
   */
  @Pure
  @NonNull
  public Long getFrameId() {
    return this.frameId;
  }
  
  /**
   * The stack frame for which to retrieve the possible stepIn targets.
   */
  public void setFrameId(@NonNull final Long frameId) {
    this.frameId = frameId;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("frameId", this.frameId);
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
    StepInTargetsArguments other = (StepInTargetsArguments) obj;
    if (this.frameId == null) {
      if (other.frameId != null)
        return false;
    } else if (!this.frameId.equals(other.frameId))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.frameId== null) ? 0 : this.frameId.hashCode());
    return result;
  }
}
