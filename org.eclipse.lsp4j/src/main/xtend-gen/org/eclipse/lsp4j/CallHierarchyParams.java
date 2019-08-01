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
import org.eclipse.lsp4j.CallHierarchyDirection;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Returns a collection of calls from one symbol to another.
 */
@Beta
@SuppressWarnings("all")
public class CallHierarchyParams extends TextDocumentPositionParams {
  /**
   * The direction of calls to provide.
   */
  @NonNull
  private CallHierarchyDirection direction;
  
  /**
   * The direction of calls to provide.
   */
  @Pure
  @NonNull
  public CallHierarchyDirection getDirection() {
    return this.direction;
  }
  
  /**
   * The direction of calls to provide.
   */
  public void setDirection(@NonNull final CallHierarchyDirection direction) {
    this.direction = Preconditions.checkNotNull(direction, "direction");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("direction", this.direction);
    b.add("textDocument", getTextDocument());
    b.add("uri", getUri());
    b.add("position", getPosition());
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
    if (!super.equals(obj))
      return false;
    CallHierarchyParams other = (CallHierarchyParams) obj;
    if (this.direction == null) {
      if (other.direction != null)
        return false;
    } else if (!this.direction.equals(other.direction))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.direction== null) ? 0 : this.direction.hashCode());
  }
}
