/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import java.util.Arrays;
import org.eclipse.lsp4j.debug.Scope;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'scopes' request.
 */
@SuppressWarnings("all")
public class ScopesResponse {
  /**
   * The scopes of the stackframe. If the array has length zero, there are no scopes available.
   */
  @NonNull
  private Scope[] scopes;
  
  /**
   * The scopes of the stackframe. If the array has length zero, there are no scopes available.
   */
  @Pure
  @NonNull
  public Scope[] getScopes() {
    return this.scopes;
  }
  
  /**
   * The scopes of the stackframe. If the array has length zero, there are no scopes available.
   */
  public void setScopes(@NonNull final Scope[] scopes) {
    this.scopes = scopes;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("scopes", this.scopes);
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
    ScopesResponse other = (ScopesResponse) obj;
    if (this.scopes == null) {
      if (other.scopes != null)
        return false;
    } else if (!Arrays.deepEquals(this.scopes, other.scopes))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.scopes== null) ? 0 : Arrays.deepHashCode(this.scopes));
  }
}
