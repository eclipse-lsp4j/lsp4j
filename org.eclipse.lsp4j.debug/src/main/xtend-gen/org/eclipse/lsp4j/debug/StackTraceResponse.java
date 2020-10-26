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
import org.eclipse.lsp4j.debug.StackFrame;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'stackTrace' request.
 */
@SuppressWarnings("all")
public class StackTraceResponse {
  /**
   * The frames of the stackframe. If the array has length zero, there are no stackframes available.
   * <p>
   * This means that there is no location information available.
   */
  @NonNull
  private StackFrame[] stackFrames;
  
  /**
   * The total number of frames available.
   * <p>
   * This is an optional property.
   */
  private Integer totalFrames;
  
  /**
   * The frames of the stackframe. If the array has length zero, there are no stackframes available.
   * <p>
   * This means that there is no location information available.
   */
  @Pure
  @NonNull
  public StackFrame[] getStackFrames() {
    return this.stackFrames;
  }
  
  /**
   * The frames of the stackframe. If the array has length zero, there are no stackframes available.
   * <p>
   * This means that there is no location information available.
   */
  public void setStackFrames(@NonNull final StackFrame[] stackFrames) {
    this.stackFrames = Preconditions.checkNotNull(stackFrames, "stackFrames");
  }
  
  /**
   * The total number of frames available.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getTotalFrames() {
    return this.totalFrames;
  }
  
  /**
   * The total number of frames available.
   * <p>
   * This is an optional property.
   */
  public void setTotalFrames(final Integer totalFrames) {
    this.totalFrames = totalFrames;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("stackFrames", this.stackFrames);
    b.add("totalFrames", this.totalFrames);
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
    StackTraceResponse other = (StackTraceResponse) obj;
    if (this.stackFrames == null) {
      if (other.stackFrames != null)
        return false;
    } else if (!Arrays.deepEquals(this.stackFrames, other.stackFrames))
      return false;
    if (this.totalFrames == null) {
      if (other.totalFrames != null)
        return false;
    } else if (!this.totalFrames.equals(other.totalFrames))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.stackFrames== null) ? 0 : Arrays.deepHashCode(this.stackFrames));
    return prime * result + ((this.totalFrames== null) ? 0 : this.totalFrames.hashCode());
  }
}
