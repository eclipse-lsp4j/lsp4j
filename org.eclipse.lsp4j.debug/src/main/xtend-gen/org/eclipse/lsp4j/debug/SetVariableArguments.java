/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.debug.ValueFormat;
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
  private Integer variablesReference;
  
  /**
   * The name of the variable.
   */
  private String name;
  
  /**
   * The value of the variable.
   */
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
  public Integer getVariablesReference() {
    return this.variablesReference;
  }
  
  /**
   * The reference of the variable container.
   */
  public void setVariablesReference(final Integer variablesReference) {
    this.variablesReference = variablesReference;
  }
  
  /**
   * The name of the variable.
   */
  @Pure
  public String getName() {
    return this.name;
  }
  
  /**
   * The name of the variable.
   */
  public void setName(final String name) {
    this.name = name;
  }
  
  /**
   * The value of the variable.
   */
  @Pure
  public String getValue() {
    return this.value;
  }
  
  /**
   * The value of the variable.
   */
  public void setValue(final String value) {
    this.value = value;
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
    result = prime * result + ((this.variablesReference== null) ? 0 : this.variablesReference.hashCode());
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.value== null) ? 0 : this.value.hashCode());
    result = prime * result + ((this.format== null) ? 0 : this.format.hashCode());
    return result;
  }
}
