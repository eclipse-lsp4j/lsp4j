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

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'cancel' request.
 */
@SuppressWarnings("all")
public class CancelArguments {
  /**
   * The ID (attribute 'seq') of the request to cancel. If missing no request is cancelled.
   * <p>
   * Both a 'requestId' and a 'progressId' can be specified in one request.
   * <p>
   * This is an optional property.
   */
  private Integer requestId;
  
  /**
   * The ID (attribute 'progressId') of the progress to cancel. If missing no progress is cancelled.
   * <p>
   * Both a 'requestId' and a 'progressId' can be specified in one request.
   * <p>
   * This is an optional property.
   */
  private String progressId;
  
  /**
   * The ID (attribute 'seq') of the request to cancel. If missing no request is cancelled.
   * <p>
   * Both a 'requestId' and a 'progressId' can be specified in one request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getRequestId() {
    return this.requestId;
  }
  
  /**
   * The ID (attribute 'seq') of the request to cancel. If missing no request is cancelled.
   * <p>
   * Both a 'requestId' and a 'progressId' can be specified in one request.
   * <p>
   * This is an optional property.
   */
  public void setRequestId(final Integer requestId) {
    this.requestId = requestId;
  }
  
  /**
   * The ID (attribute 'progressId') of the progress to cancel. If missing no progress is cancelled.
   * <p>
   * Both a 'requestId' and a 'progressId' can be specified in one request.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getProgressId() {
    return this.progressId;
  }
  
  /**
   * The ID (attribute 'progressId') of the progress to cancel. If missing no progress is cancelled.
   * <p>
   * Both a 'requestId' and a 'progressId' can be specified in one request.
   * <p>
   * This is an optional property.
   */
  public void setProgressId(final String progressId) {
    this.progressId = progressId;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("requestId", this.requestId);
    b.add("progressId", this.progressId);
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
    CancelArguments other = (CancelArguments) obj;
    if (this.requestId == null) {
      if (other.requestId != null)
        return false;
    } else if (!this.requestId.equals(other.requestId))
      return false;
    if (this.progressId == null) {
      if (other.progressId != null)
        return false;
    } else if (!this.progressId.equals(other.progressId))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.requestId== null) ? 0 : this.requestId.hashCode());
    return prime * result + ((this.progressId== null) ? 0 : this.progressId.hashCode());
  }
}
