/**
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class ApplyWorkspaceEditResponse {
  /**
   * Indicates whether the edit was applied or not.
   */
  private boolean applied;
  
  /**
   * An optional textual description for why the edit was not applied.
   * This may be used by the server for diagnostic logging or to provide
   * a suitable error for a request that triggered the edit.
   */
  private String failureReason;
  
  /**
   * Depending on the client's failure handling strategy `failedChange`
   * might contain the index of the change that failed. This property is
   * only available if the client signals a `failureHandlingStrategy`
   * in its client capabilities.
   */
  private Integer failedChange;
  
  public ApplyWorkspaceEditResponse() {
  }
  
  public ApplyWorkspaceEditResponse(final boolean applied) {
    this.applied = applied;
  }
  
  /**
   * Indicates whether the edit was applied or not.
   */
  @Pure
  public boolean isApplied() {
    return this.applied;
  }
  
  /**
   * Indicates whether the edit was applied or not.
   */
  public void setApplied(final boolean applied) {
    this.applied = applied;
  }
  
  /**
   * An optional textual description for why the edit was not applied.
   * This may be used by the server for diagnostic logging or to provide
   * a suitable error for a request that triggered the edit.
   */
  @Pure
  public String getFailureReason() {
    return this.failureReason;
  }
  
  /**
   * An optional textual description for why the edit was not applied.
   * This may be used by the server for diagnostic logging or to provide
   * a suitable error for a request that triggered the edit.
   */
  public void setFailureReason(final String failureReason) {
    this.failureReason = failureReason;
  }
  
  /**
   * Depending on the client's failure handling strategy `failedChange`
   * might contain the index of the change that failed. This property is
   * only available if the client signals a `failureHandlingStrategy`
   * in its client capabilities.
   */
  @Pure
  public Integer getFailedChange() {
    return this.failedChange;
  }
  
  /**
   * Depending on the client's failure handling strategy `failedChange`
   * might contain the index of the change that failed. This property is
   * only available if the client signals a `failureHandlingStrategy`
   * in its client capabilities.
   */
  public void setFailedChange(final Integer failedChange) {
    this.failedChange = failedChange;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("applied", this.applied);
    b.add("failureReason", this.failureReason);
    b.add("failedChange", this.failedChange);
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
    if (other.applied != this.applied)
      return false;
    if (this.failureReason == null) {
      if (other.failureReason != null)
        return false;
    } else if (!this.failureReason.equals(other.failureReason))
      return false;
    if (this.failedChange == null) {
      if (other.failedChange != null)
        return false;
    } else if (!this.failedChange.equals(other.failedChange))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.applied ? 1231 : 1237);
    result = prime * result + ((this.failureReason== null) ? 0 : this.failureReason.hashCode());
    return prime * result + ((this.failedChange== null) ? 0 : this.failedChange.hashCode());
  }
}
