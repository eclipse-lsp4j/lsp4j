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

import java.util.List;
import org.eclipse.lsp4j.CallHierarchyItem;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents an incoming call, e.g. a caller of a method or constructor.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class CallHierarchyIncomingCall {
  /**
   * The item that makes the call.
   */
  @NonNull
  private CallHierarchyItem from;
  
  /**
   * The range at which at which the calls appears. This is relative to the caller
   * denoted by [`this.from`](#CallHierarchyIncomingCall.from).
   */
  @NonNull
  private List<Range> fromRanges;
  
  public CallHierarchyIncomingCall() {
  }
  
  public CallHierarchyIncomingCall(@NonNull final CallHierarchyItem from, @NonNull final List<Range> fromRanges) {
    this.from = Preconditions.<CallHierarchyItem>checkNotNull(from, "from");
    this.fromRanges = Preconditions.<List<Range>>checkNotNull(fromRanges, "fromRanges");
  }
  
  /**
   * The item that makes the call.
   */
  @Pure
  @NonNull
  public CallHierarchyItem getFrom() {
    return this.from;
  }
  
  /**
   * The item that makes the call.
   */
  public void setFrom(@NonNull final CallHierarchyItem from) {
    this.from = Preconditions.checkNotNull(from, "from");
  }
  
  /**
   * The range at which at which the calls appears. This is relative to the caller
   * denoted by [`this.from`](#CallHierarchyIncomingCall.from).
   */
  @Pure
  @NonNull
  public List<Range> getFromRanges() {
    return this.fromRanges;
  }
  
  /**
   * The range at which at which the calls appears. This is relative to the caller
   * denoted by [`this.from`](#CallHierarchyIncomingCall.from).
   */
  public void setFromRanges(@NonNull final List<Range> fromRanges) {
    this.fromRanges = Preconditions.checkNotNull(fromRanges, "fromRanges");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("from", this.from);
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
    CallHierarchyIncomingCall other = (CallHierarchyIncomingCall) obj;
    if (this.from == null) {
      if (other.from != null)
        return false;
    } else if (!this.from.equals(other.from))
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
    result = prime * result + ((this.from== null) ? 0 : this.from.hashCode());
    return prime * result + ((this.fromRanges== null) ? 0 : this.fromRanges.hashCode());
  }
}
