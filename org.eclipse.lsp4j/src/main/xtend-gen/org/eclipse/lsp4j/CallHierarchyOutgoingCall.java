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
import java.util.List;
import org.eclipse.lsp4j.CallHierarchyItem;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents an outgoing call, e.g. calling a getter from a method or a method from a constructor etc.
 */
@Beta
@SuppressWarnings("all")
public class CallHierarchyOutgoingCall {
  /**
   * The item that is called.
   */
  @NonNull
  private CallHierarchyItem to;
  
  /**
   * The range at which this item is called. This is the range relative to the caller, e.g the item
   * passed to [`provideCallHierarchyOutgoingCalls`](#CallHierarchyItemProvider.provideCallHierarchyOutgoingCalls)
   * and not [`this.to`](#CallHierarchyOutgoingCall.to).
   */
  @NonNull
  private List<Range> fromRanges;
  
  public CallHierarchyOutgoingCall() {
  }
  
  public CallHierarchyOutgoingCall(@NonNull final CallHierarchyItem to, @NonNull final List<Range> fromRanges) {
    this.to = to;
    this.fromRanges = fromRanges;
  }
  
  /**
   * The item that is called.
   */
  @Pure
  @NonNull
  public CallHierarchyItem getTo() {
    return this.to;
  }
  
  /**
   * The item that is called.
   */
  public void setTo(@NonNull final CallHierarchyItem to) {
    this.to = Preconditions.checkNotNull(to, "to");
  }
  
  /**
   * The range at which this item is called. This is the range relative to the caller, e.g the item
   * passed to [`provideCallHierarchyOutgoingCalls`](#CallHierarchyItemProvider.provideCallHierarchyOutgoingCalls)
   * and not [`this.to`](#CallHierarchyOutgoingCall.to).
   */
  @Pure
  @NonNull
  public List<Range> getFromRanges() {
    return this.fromRanges;
  }
  
  /**
   * The range at which this item is called. This is the range relative to the caller, e.g the item
   * passed to [`provideCallHierarchyOutgoingCalls`](#CallHierarchyItemProvider.provideCallHierarchyOutgoingCalls)
   * and not [`this.to`](#CallHierarchyOutgoingCall.to).
   */
  public void setFromRanges(@NonNull final List<Range> fromRanges) {
    this.fromRanges = Preconditions.checkNotNull(fromRanges, "fromRanges");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("to", this.to);
    b.add("fromRanges", this.fromRanges);
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
    CallHierarchyOutgoingCall other = (CallHierarchyOutgoingCall) obj;
    if (this.to == null) {
      if (other.to != null)
        return false;
    } else if (!this.to.equals(other.to))
      return false;
    if (this.fromRanges == null) {
      if (other.fromRanges != null)
        return false;
    } else if (!this.fromRanges.equals(other.fromRanges))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.to== null) ? 0 : this.to.hashCode());
    return prime * result + ((this.fromRanges== null) ? 0 : this.fromRanges.hashCode());
  }
}
