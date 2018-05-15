/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import java.util.Arrays;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Response to 'variables' request.
 */
@SuppressWarnings("all")
public class VariablesResponse {
  /**
   * All (or a range) of variables for the given variable reference.
   */
  @NonNull
  private Variable[] variables;
  
  /**
   * All (or a range) of variables for the given variable reference.
   */
  @Pure
  @NonNull
  public Variable[] getVariables() {
    return this.variables;
  }
  
  /**
   * All (or a range) of variables for the given variable reference.
   */
  public void setVariables(@NonNull final Variable[] variables) {
    this.variables = variables;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("variables", this.variables);
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
    VariablesResponse other = (VariablesResponse) obj;
    if (this.variables == null) {
      if (other.variables != null)
        return false;
    } else if (!Arrays.deepEquals(this.variables, other.variables))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.variables== null) ? 0 : Arrays.deepHashCode(this.variables));
  }
}
