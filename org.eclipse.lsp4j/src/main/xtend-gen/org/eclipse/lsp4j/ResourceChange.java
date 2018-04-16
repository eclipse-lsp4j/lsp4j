/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import com.google.common.annotations.Beta;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A resource change.
 * 
 * If both current and newUri has valid values this is considered to be a move operation.
 * If current has a valid value while newUri is null it is treated as a delete operation.
 * If current is null and newUri has a valid value a create operation is executed.
 */
@Beta
@SuppressWarnings("all")
public class ResourceChange {
  /**
   * The Uri for current resource. Required for delete and move operations
   * otherwise it is null.
   */
  private String current;
  
  /**
   * The new uri for the resource. Required for create and move operations.
   * otherwise null.
   * 
   * Must be compatible with the current uri ie. must be a file
   * uri if current is not null and is a file uri.
   */
  private String newUri;
  
  /**
   * The Uri for current resource. Required for delete and move operations
   * otherwise it is null.
   */
  @Pure
  public String getCurrent() {
    return this.current;
  }
  
  /**
   * The Uri for current resource. Required for delete and move operations
   * otherwise it is null.
   */
  public void setCurrent(final String current) {
    this.current = current;
  }
  
  /**
   * The new uri for the resource. Required for create and move operations.
   * otherwise null.
   * 
   * Must be compatible with the current uri ie. must be a file
   * uri if current is not null and is a file uri.
   */
  @Pure
  public String getNewUri() {
    return this.newUri;
  }
  
  /**
   * The new uri for the resource. Required for create and move operations.
   * otherwise null.
   * 
   * Must be compatible with the current uri ie. must be a file
   * uri if current is not null and is a file uri.
   */
  public void setNewUri(final String newUri) {
    this.newUri = newUri;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("current", this.current);
    b.add("newUri", this.newUri);
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
    ResourceChange other = (ResourceChange) obj;
    if (this.current == null) {
      if (other.current != null)
        return false;
    } else if (!this.current.equals(other.current))
      return false;
    if (this.newUri == null) {
      if (other.newUri != null)
        return false;
    } else if (!this.newUri.equals(other.newUri))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.current== null) ? 0 : this.current.hashCode());
    result = prime * result + ((this.newUri== null) ? 0 : this.newUri.hashCode());
    return result;
  }
}
