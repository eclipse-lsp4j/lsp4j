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
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The parameters of a {@code textDocument/callHierarchy} request.
 */
@Beta
@SuppressWarnings("all")
public class CallHierarchyParams extends TextDocumentPositionParams {
  /**
   * The number of levels to resolve.
   */
  private int resolve;
  
  /**
   * The direction of calls to resolve.
   */
  private CallHierarchyDirection direction;
  
  /**
   * The number of levels to resolve.
   */
  @Pure
  public int getResolve() {
    return this.resolve;
  }
  
  /**
   * The number of levels to resolve.
   */
  public void setResolve(final int resolve) {
    this.resolve = resolve;
  }
  
  /**
   * The direction of calls to resolve.
   */
  @Pure
  public CallHierarchyDirection getDirection() {
    return this.direction;
  }
  
  /**
   * The direction of calls to resolve.
   */
  public void setDirection(final CallHierarchyDirection direction) {
    this.direction = direction;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("resolve", this.resolve);
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
    if (other.resolve != this.resolve)
      return false;
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
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + this.resolve;
    return prime * result + ((this.direction== null) ? 0 : this.direction.hashCode());
  }
}
