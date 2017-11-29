/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.debug.ChecksumAlgorithm;
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
  private ChecksumAlgorithm algorithm;
  
  /**
   * Value of the checksum.
   */
  private String checksum;
  
  /**
   * The algorithm used to calculate this checksum.
   */
  @Pure
  public ChecksumAlgorithm getAlgorithm() {
    return this.algorithm;
  }
  
  /**
   * The algorithm used to calculate this checksum.
   */
  public void setAlgorithm(final ChecksumAlgorithm algorithm) {
    this.algorithm = algorithm;
  }
  
  /**
   * Value of the checksum.
   */
  @Pure
  public String getChecksum() {
    return this.checksum;
  }
  
  /**
   * Value of the checksum.
   */
  public void setChecksum(final String checksum) {
    this.checksum = checksum;
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
    result = prime * result + ((this.checksum== null) ? 0 : this.checksum.hashCode());
    return result;
  }
}
