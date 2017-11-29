/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Provides formatting information for a value.
 */
@SuppressWarnings("all")
public class ValueFormat {
  /**
   * Display the value in hex.
   * <p>
   * This is an optional property.
   */
  private Boolean hex;
  
  /**
   * Display the value in hex.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getHex() {
    return this.hex;
  }
  
  /**
   * Display the value in hex.
   * <p>
   * This is an optional property.
   */
  public void setHex(final Boolean hex) {
    this.hex = hex;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("hex", this.hex);
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
    ValueFormat other = (ValueFormat) obj;
    if (this.hex == null) {
      if (other.hex != null)
        return false;
    } else if (!this.hex.equals(other.hex))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.hex== null) ? 0 : this.hex.hashCode());
    return result;
  }
}
