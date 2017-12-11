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
 * Arguments for 'scopes' request.
 */
@SuppressWarnings("all")
public class ScopesArguments {
  /**
   * Retrieve the scopes for this stackframe.
   */
  @NonNull
  private Integer frameId;
  
  /**
   * Retrieve the scopes for this stackframe.
   */
  @Pure
  @NonNull
  public Integer getFrameId() {
    return this.frameId;
  }
  
  /**
   * Retrieve the scopes for this stackframe.
   */
  public void setFrameId(@NonNull final Integer frameId) {
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
    ScopesArguments other = (ScopesArguments) obj;
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
