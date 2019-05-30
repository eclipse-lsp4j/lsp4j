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

import com.google.common.annotations.Beta;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A selection range represents a part of a selection hierarchy. A selection range
 * may have a parent selection range that contains it.
 */
@Beta
@SuppressWarnings("all")
public class SelectionRange {
  /**
   * The [range](#Range) of this selection range.
   */
  @NonNull
  private Range range;
  
  /**
   * The parent selection range containing this range. Therefore `parent.range` must contain `this.range`.
   */
  private SelectionRange parent;
  
  public SelectionRange() {
  }
  
  public SelectionRange(@NonNull final Range range, final SelectionRange parent) {
    this.range = Preconditions.<Range>checkNotNull(range, "range");
    this.parent = parent;
  }
  
  /**
   * The [range](#Range) of this selection range.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The [range](#Range) of this selection range.
   */
  public void setRange(@NonNull final Range range) {
    if (range == null) {
      throw new IllegalArgumentException("Property must not be null: range");
    }
    this.range = range;
  }
  
  /**
   * The parent selection range containing this range. Therefore `parent.range` must contain `this.range`.
   */
  @Pure
  public SelectionRange getParent() {
    return this.parent;
  }
  
  /**
   * The parent selection range containing this range. Therefore `parent.range` must contain `this.range`.
   */
  public void setParent(final SelectionRange parent) {
    this.parent = parent;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("parent", this.parent);
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
    SelectionRange other = (SelectionRange) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.parent == null) {
      if (other.parent != null)
        return false;
    } else if (!this.parent.equals(other.parent))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    return prime * result + ((this.parent== null) ? 0 : this.parent.hashCode());
  }
}
