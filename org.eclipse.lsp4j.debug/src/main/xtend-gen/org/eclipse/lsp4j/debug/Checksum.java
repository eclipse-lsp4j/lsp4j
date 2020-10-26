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

import org.eclipse.lsp4j.debug.ChecksumAlgorithm;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The checksum of an item calculated by the specified algorithm.
 */
@SuppressWarnings("all")
public class Checksum {
  /**
   * The algorithm used to calculate this checksum.
   */
  @NonNull
  private ChecksumAlgorithm algorithm;
  
  /**
   * Value of the checksum.
   */
  @NonNull
  private String checksum;
  
  /**
   * The algorithm used to calculate this checksum.
   */
  @Pure
  @NonNull
  public ChecksumAlgorithm getAlgorithm() {
    return this.algorithm;
  }
  
  /**
   * The algorithm used to calculate this checksum.
   */
  public void setAlgorithm(@NonNull final ChecksumAlgorithm algorithm) {
    this.algorithm = Preconditions.checkNotNull(algorithm, "algorithm");
  }
  
  /**
   * Value of the checksum.
   */
  @Pure
  @NonNull
  public String getChecksum() {
    return this.checksum;
  }
  
  /**
   * Value of the checksum.
   */
  public void setChecksum(@NonNull final String checksum) {
    this.checksum = Preconditions.checkNotNull(checksum, "checksum");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("algorithm", this.algorithm);
    b.add("checksum", this.checksum);
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
    Checksum other = (Checksum) obj;
    if (this.algorithm == null) {
      if (other.algorithm != null)
        return false;
    } else if (!this.algorithm.equals(other.algorithm))
      return false;
    if (this.checksum == null) {
      if (other.checksum != null)
        return false;
    } else if (!this.checksum.equals(other.checksum))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.algorithm== null) ? 0 : this.algorithm.hashCode());
    return prime * result + ((this.checksum== null) ? 0 : this.checksum.hashCode());
  }
}
