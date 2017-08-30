/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class ApplyWorkspaceEditResponse {
  /**
   * Indicates whether the edit was applied or not.
   */
  private Boolean applied;
  
  public ApplyWorkspaceEditResponse() {
  }
  
  public ApplyWorkspaceEditResponse(final Boolean applied) {
    this.applied = applied;
  }
  
  /**
   * Indicates whether the edit was applied or not.
   */
  @Pure
  public Boolean getApplied() {
    return this.applied;
  }
  
  /**
   * Indicates whether the edit was applied or not.
   */
  public void setApplied(final Boolean applied) {
    this.applied = applied;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("applied", this.applied);
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
    ApplyWorkspaceEditResponse other = (ApplyWorkspaceEditResponse) obj;
    if (this.applied == null) {
      if (other.applied != null)
        return false;
    } else if (!this.applied.equals(other.applied))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.applied== null) ? 0 : this.applied.hashCode());
    return result;
  }
}
