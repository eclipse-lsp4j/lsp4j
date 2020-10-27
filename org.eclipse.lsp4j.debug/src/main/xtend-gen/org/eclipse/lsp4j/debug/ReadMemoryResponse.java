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
 * Response to 'readMemory' request.
 */
@SuppressWarnings("all")
public class ReadMemoryResponse {
  /**
   * The address of the first byte of data returned.
   * <p>
   * Treated as a hex value if prefixed with '0x', or as a decimal value otherwise.
   */
  @NonNull
  private String address;
  
  /**
   * The number of unreadable bytes encountered after the last successfully read byte.
   * <p>
   * This can be used to determine the number of bytes that must be skipped before a subsequent 'readMemory' request
   * will succeed.
   * <p>
   * This is an optional property.
   */
  private Integer unreadableBytes;
  
  /**
   * The bytes read from memory, encoded using base64.
   * <p>
   * This is an optional property.
   */
  private String data;
  
  /**
   * The address of the first byte of data returned.
   * <p>
   * Treated as a hex value if prefixed with '0x', or as a decimal value otherwise.
   */
  @Pure
  @NonNull
  public String getAddress() {
    return this.address;
  }
  
  /**
   * The address of the first byte of data returned.
   * <p>
   * Treated as a hex value if prefixed with '0x', or as a decimal value otherwise.
   */
  public void setAddress(@NonNull final String address) {
    this.address = Preconditions.checkNotNull(address, "address");
  }
  
  /**
   * The number of unreadable bytes encountered after the last successfully read byte.
   * <p>
   * This can be used to determine the number of bytes that must be skipped before a subsequent 'readMemory' request
   * will succeed.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getUnreadableBytes() {
    return this.unreadableBytes;
  }
  
  /**
   * The number of unreadable bytes encountered after the last successfully read byte.
   * <p>
   * This can be used to determine the number of bytes that must be skipped before a subsequent 'readMemory' request
   * will succeed.
   * <p>
   * This is an optional property.
   */
  public void setUnreadableBytes(final Integer unreadableBytes) {
    this.unreadableBytes = unreadableBytes;
  }
  
  /**
   * The bytes read from memory, encoded using base64.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getData() {
    return this.data;
  }
  
  /**
   * The bytes read from memory, encoded using base64.
   * <p>
   * This is an optional property.
   */
  public void setData(final String data) {
    this.data = data;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("address", this.address);
    b.add("unreadableBytes", this.unreadableBytes);
    b.add("data", this.data);
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
    ReadMemoryResponse other = (ReadMemoryResponse) obj;
    if (this.address == null) {
      if (other.address != null)
        return false;
    } else if (!this.address.equals(other.address))
      return false;
    if (this.unreadableBytes == null) {
      if (other.unreadableBytes != null)
        return false;
    } else if (!this.unreadableBytes.equals(other.unreadableBytes))
      return false;
    if (this.data == null) {
      if (other.data != null)
        return false;
    } else if (!this.data.equals(other.data))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.address== null) ? 0 : this.address.hashCode());
    result = prime * result + ((this.unreadableBytes== null) ? 0 : this.unreadableBytes.hashCode());
    return prime * result + ((this.data== null) ? 0 : this.data.hashCode());
  }
}
