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

import org.eclipse.lsp4j.debug.LoadedSourceEventArgumentsReason;
import org.eclipse.lsp4j.debug.Source;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The event indicates that some source has been added, changed, or removed from the set of all loaded sources.
 */
@SuppressWarnings("all")
public class LoadedSourceEventArguments {
  /**
   * The reason for the event.
   */
  @NonNull
  private LoadedSourceEventArgumentsReason reason;
  
  /**
   * The new, changed, or removed source.
   */
  @NonNull
  private Source source;
  
  /**
   * The reason for the event.
   */
  @Pure
  @NonNull
  public LoadedSourceEventArgumentsReason getReason() {
    return this.reason;
  }
  
  /**
   * The reason for the event.
   */
  public void setReason(@NonNull final LoadedSourceEventArgumentsReason reason) {
    this.reason = Preconditions.checkNotNull(reason, "reason");
  }
  
  /**
   * The new, changed, or removed source.
   */
  @Pure
  @NonNull
  public Source getSource() {
    return this.source;
  }
  
  /**
   * The new, changed, or removed source.
   */
  public void setSource(@NonNull final Source source) {
    this.source = Preconditions.checkNotNull(source, "source");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("reason", this.reason);
    b.add("source", this.source);
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
    LoadedSourceEventArguments other = (LoadedSourceEventArguments) obj;
    if (this.reason == null) {
      if (other.reason != null)
        return false;
    } else if (!this.reason.equals(other.reason))
      return false;
    if (this.source == null) {
      if (other.source != null)
        return false;
    } else if (!this.source.equals(other.source))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.reason== null) ? 0 : this.reason.hashCode());
    return prime * result + ((this.source== null) ? 0 : this.source.hashCode());
  }
}
