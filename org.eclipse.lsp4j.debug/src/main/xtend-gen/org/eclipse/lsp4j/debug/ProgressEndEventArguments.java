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
 * The event signals the end of the progress reporting with an optional final message.
 * <p>
 * This event should only be sent if the client has passed the value true for the 'supportsProgressReporting'
 * capability of the 'initialize' request.
 */
@SuppressWarnings("all")
public class ProgressEndEventArguments {
  /**
   * The ID that was introduced in the initial 'ProgressStartEvent'.
   */
  @NonNull
  private String progressId;
  
  /**
   * Optional, more detailed progress message. If omitted, the previous message (if any) is used.
   * <p>
   * This is an optional property.
   */
  private String message;
  
  /**
   * The ID that was introduced in the initial 'ProgressStartEvent'.
   */
  @Pure
  @NonNull
  public String getProgressId() {
    return this.progressId;
  }
  
  /**
   * The ID that was introduced in the initial 'ProgressStartEvent'.
   */
  public void setProgressId(@NonNull final String progressId) {
    this.progressId = Preconditions.checkNotNull(progressId, "progressId");
  }
  
  /**
   * Optional, more detailed progress message. If omitted, the previous message (if any) is used.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getMessage() {
    return this.message;
  }
  
  /**
   * Optional, more detailed progress message. If omitted, the previous message (if any) is used.
   * <p>
   * This is an optional property.
   */
  public void setMessage(final String message) {
    this.message = message;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("progressId", this.progressId);
    b.add("message", this.message);
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
    ProgressEndEventArguments other = (ProgressEndEventArguments) obj;
    if (this.progressId == null) {
      if (other.progressId != null)
        return false;
    } else if (!this.progressId.equals(other.progressId))
      return false;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.progressId== null) ? 0 : this.progressId.hashCode());
    return prime * result + ((this.message== null) ? 0 : this.message.hashCode());
  }
}
