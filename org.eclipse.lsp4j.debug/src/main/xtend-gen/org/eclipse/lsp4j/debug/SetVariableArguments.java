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

import org.eclipse.lsp4j.debug.ValueFormat;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'setVariable' request.
 */
@SuppressWarnings("all")
public class SetVariableArguments {
  /**
   * The reference of the variable container.
   */
  private int variablesReference;
  
  /**
   * The name of the variable in the container.
   */
  @NonNull
  private String name;
  
  /**
   * The value of the variable.
   */
  @NonNull
  private String value;
  
  /**
   * Specifies details on how to format the response value.
   * <p>
   * This is an optional property.
   */
  private ValueFormat format;
  
  /**
   * The reference of the variable container.
   */
  @Pure
  public int getVariablesReference() {
    return this.variablesReference;
  }
  
  /**
   * The reference of the variable container.
   */
  public void setVariablesReference(final int variablesReference) {
    this.variablesReference = variablesReference;
  }
  
  /**
   * The name of the variable in the container.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * The name of the variable in the container.
   */
  public void setName(@NonNull final String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }
  
  /**
   * The value of the variable.
   */
  @Pure
  @NonNull
  public String getValue() {
    return this.value;
  }
  
  /**
   * The value of the variable.
   */
  public void setValue(@NonNull final String value) {
    this.value = Preconditions.checkNotNull(value, "value");
  }
  
  /**
   * Specifies details on how to format the response value.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ValueFormat getFormat() {
    return this.format;
  }
  
  /**
   * Specifies details on how to format the response value.
   * <p>
   * This is an optional property.
   */
  public void setFormat(final ValueFormat format) {
    this.format = format;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("variablesReference", this.variablesReference);
    b.add("name", this.name);
    b.add("value", this.value);
    b.add("format", this.format);
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
    SetVariableArguments other = (SetVariableArguments) obj;
    if (other.variablesReference != this.variablesReference)
      return false;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.value == null) {
      if (other.value != null)
        return false;
    } else if (!this.value.equals(other.value))
      return false;
    if (this.format == null) {
      if (other.format != null)
        return false;
    } else if (!this.format.equals(other.format))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.variablesReference;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.value== null) ? 0 : this.value.hashCode());
    return prime * result + ((this.format== null) ? 0 : this.format.hashCode());
  }
}
