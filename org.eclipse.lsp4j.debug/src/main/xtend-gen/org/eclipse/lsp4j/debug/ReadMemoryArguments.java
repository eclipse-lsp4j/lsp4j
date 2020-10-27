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
 * Arguments for 'readMemory' request.
 */
@SuppressWarnings("all")
public class ReadMemoryArguments {
  /**
   * Memory reference to the base location from which data should be read.
   */
  @NonNull
  private String memoryReference;
  
  /**
   * Optional offset (in bytes) to be applied to the reference location before reading data. Can be negative.
   * <p>
   * This is an optional property.
   */
  private Integer offset;
  
  /**
   * Number of bytes to read at the specified location and offset.
   */
  private int count;
  
  /**
   * Memory reference to the base location from which data should be read.
   */
  @Pure
  @NonNull
  public String getMemoryReference() {
    return this.memoryReference;
  }
  
  /**
   * Memory reference to the base location from which data should be read.
   */
  public void setMemoryReference(@NonNull final String memoryReference) {
    this.memoryReference = Preconditions.checkNotNull(memoryReference, "memoryReference");
  }
  
  /**
   * Optional offset (in bytes) to be applied to the reference location before reading data. Can be negative.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getOffset() {
    return this.offset;
  }
  
  /**
   * Optional offset (in bytes) to be applied to the reference location before reading data. Can be negative.
   * <p>
   * This is an optional property.
   */
  public void setOffset(final Integer offset) {
    this.offset = offset;
  }
  
  /**
   * Number of bytes to read at the specified location and offset.
   */
  @Pure
  public int getCount() {
    return this.count;
  }
  
  /**
   * Number of bytes to read at the specified location and offset.
   */
  public void setCount(final int count) {
    this.count = count;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("memoryReference", this.memoryReference);
    b.add("offset", this.offset);
    b.add("count", this.count);
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
    ReadMemoryArguments other = (ReadMemoryArguments) obj;
    if (this.memoryReference == null) {
      if (other.memoryReference != null)
        return false;
    } else if (!this.memoryReference.equals(other.memoryReference))
      return false;
    if (this.offset == null) {
      if (other.offset != null)
        return false;
    } else if (!this.offset.equals(other.offset))
      return false;
    if (other.count != this.count)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.memoryReference== null) ? 0 : this.memoryReference.hashCode());
    result = prime * result + ((this.offset== null) ? 0 : this.offset.hashCode());
    return prime * result + this.count;
  }
}
