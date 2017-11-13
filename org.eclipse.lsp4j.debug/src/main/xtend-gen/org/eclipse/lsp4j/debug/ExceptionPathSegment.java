/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import java.util.Arrays;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * An ExceptionPathSegment represents a segment in a path that is used to match leafs or nodes in a tree of
 * exceptions. If a segment consists of more than one name, it matches the names provided if 'negate' is false or
 * missing or it matches anything except the names provided if 'negate' is true.
 */
@SuppressWarnings("all")
public class ExceptionPathSegment {
  /**
   * If false or missing this segment matches the names provided, otherwise it matches anything except the names
   * provided.
   * <p>
   * This is an optional property.
   */
  private Boolean negate;
  
  /**
   * Depending on the value of 'negate' the names that should match or not match.
   */
  private String[] names;
  
  /**
   * If false or missing this segment matches the names provided, otherwise it matches anything except the names
   * provided.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getNegate() {
    return this.negate;
  }
  
  /**
   * If false or missing this segment matches the names provided, otherwise it matches anything except the names
   * provided.
   * <p>
   * This is an optional property.
   */
  public void setNegate(final Boolean negate) {
    this.negate = negate;
  }
  
  /**
   * Depending on the value of 'negate' the names that should match or not match.
   */
  @Pure
  public String[] getNames() {
    return this.names;
  }
  
  /**
   * Depending on the value of 'negate' the names that should match or not match.
   */
  public void setNames(final String[] names) {
    this.names = names;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("negate", this.negate);
    b.add("names", this.names);
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
    ExceptionPathSegment other = (ExceptionPathSegment) obj;
    if (this.negate == null) {
      if (other.negate != null)
        return false;
    } else if (!this.negate.equals(other.negate))
      return false;
    if (this.names == null) {
      if (other.names != null)
        return false;
    } else if (!Arrays.deepEquals(this.names, other.names))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.negate== null) ? 0 : this.negate.hashCode());
    result = prime * result + ((this.names== null) ? 0 : Arrays.deepHashCode(this.names));
    return result;
  }
}
