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

import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'dataBreakpointInfo' request.
 */
@SuppressWarnings("all")
public class DataBreakpointInfoArguments {
  /**
   * Reference to the Variable container if the data breakpoint is requested for a child of the container.
   * <p>
   * This is an optional property.
   */
  private Integer variablesReference;
  
  /**
   * The name of the Variable's child to obtain data breakpoint information for.
   * <p>
   * If variableReference isn’t provided, this can be an expression.
   */
  @NonNull
  private String name;
  
  /**
   * Reference to the Variable container if the data breakpoint is requested for a child of the container.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getVariablesReference() {
    return this.variablesReference;
  }
  
  /**
   * Reference to the Variable container if the data breakpoint is requested for a child of the container.
   * <p>
   * This is an optional property.
   */
  public void setVariablesReference(final Integer variablesReference) {
    this.variablesReference = variablesReference;
  }
  
  /**
   * The name of the Variable's child to obtain data breakpoint information for.
   * <p>
   * If variableReference isn’t provided, this can be an expression.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * The name of the Variable's child to obtain data breakpoint information for.
   * <p>
   * If variableReference isn’t provided, this can be an expression.
   */
  public void setName(@NonNull final String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("variablesReference", this.variablesReference);
    b.add("name", this.name);
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
    DataBreakpointInfoArguments other = (DataBreakpointInfoArguments) obj;
    if (this.variablesReference == null) {
      if (other.variablesReference != null)
        return false;
    } else if (!this.variablesReference.equals(other.variablesReference))
      return false;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.variablesReference== null) ? 0 : this.variablesReference.hashCode());
    return prime * result + ((this.name== null) ? 0 : this.name.hashCode());
  }
}
