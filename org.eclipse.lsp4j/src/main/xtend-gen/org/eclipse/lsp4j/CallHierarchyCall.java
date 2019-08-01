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
import org.eclipse.lsp4j.CallHierarchySymbol;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Each {@code CallHierarchyCall} object defines a call from one {@code CallHierarchySymbol} to another.
 */
@Beta
@SuppressWarnings("all")
public class CallHierarchyCall {
  /**
   * The source range of the reference. The range is a sub range of the {@link CallHierarchyCall#getFrom from} symbol range.
   */
  @NonNull
  private Range range;
  
  /**
   * The symbol that contains the reference.
   */
  @NonNull
  private CallHierarchySymbol from;
  
  /**
   * The symbol that is referenced.
   */
  @NonNull
  private CallHierarchySymbol to;
  
  /**
   * The source range of the reference. The range is a sub range of the {@link CallHierarchyCall#getFrom from} symbol range.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The source range of the reference. The range is a sub range of the {@link CallHierarchyCall#getFrom from} symbol range.
   */
  public void setRange(@NonNull final Range range) {
    this.range = Preconditions.checkNotNull(range, "range");
  }
  
  /**
   * The symbol that contains the reference.
   */
  @Pure
  @NonNull
  public CallHierarchySymbol getFrom() {
    return this.from;
  }
  
  /**
   * The symbol that contains the reference.
   */
  public void setFrom(@NonNull final CallHierarchySymbol from) {
    this.from = Preconditions.checkNotNull(from, "from");
  }
  
  /**
   * The symbol that is referenced.
   */
  @Pure
  @NonNull
  public CallHierarchySymbol getTo() {
    return this.to;
  }
  
  /**
   * The symbol that is referenced.
   */
  public void setTo(@NonNull final CallHierarchySymbol to) {
    this.to = Preconditions.checkNotNull(to, "to");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("from", this.from);
    b.add("to", this.to);
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
    CallHierarchyCall other = (CallHierarchyCall) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.from == null) {
      if (other.from != null)
        return false;
    } else if (!this.from.equals(other.from))
      return false;
    if (this.to == null) {
      if (other.to != null)
        return false;
    } else if (!this.to.equals(other.to))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.from== null) ? 0 : this.from.hashCode());
    return prime * result + ((this.to== null) ? 0 : this.to.hashCode());
  }
}
