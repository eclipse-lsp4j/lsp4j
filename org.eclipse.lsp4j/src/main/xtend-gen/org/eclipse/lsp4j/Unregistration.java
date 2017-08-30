/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * General parameters to unregister a capability.
 */
@SuppressWarnings("all")
public class Unregistration {
  /**
   * The id used to unregister the request or notification. Usually an id
   * provided during the register request.
   */
  @NonNull
  private String id;
  
  /**
   * The method / capability to unregister for.
   */
  @NonNull
  private String method;
  
  public Unregistration() {
  }
  
  public Unregistration(@NonNull final String id, @NonNull final String method) {
    this.id = id;
    this.method = method;
  }
  
  /**
   * The id used to unregister the request or notification. Usually an id
   * provided during the register request.
   */
  @Pure
  @NonNull
  public String getId() {
    return this.id;
  }
  
  /**
   * The id used to unregister the request or notification. Usually an id
   * provided during the register request.
   */
  public void setId(@NonNull final String id) {
    this.id = id;
  }
  
  /**
   * The method / capability to unregister for.
   */
  @Pure
  @NonNull
  public String getMethod() {
    return this.method;
  }
  
  /**
   * The method / capability to unregister for.
   */
  public void setMethod(@NonNull final String method) {
    this.method = method;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("method", this.method);
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
    Unregistration other = (Unregistration) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.method == null) {
      if (other.method != null)
        return false;
    } else if (!this.method.equals(other.method))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id== null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.method== null) ? 0 : this.method.hashCode());
    return result;
  }
}
