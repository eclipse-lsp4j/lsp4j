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
 * Arguments for 'setExpression' request.
 */
@SuppressWarnings("all")
public class SetExpressionArguments {
  /**
   * The l-value expression to assign to.
   */
  @NonNull
  private String expression;
  
  /**
   * The value expression to assign to the l-value expression.
   */
  @NonNull
  private String value;
  
  /**
   * Evaluate the expressions in the scope of this stack frame. If not specified, the expressions are evaluated in
   * the global scope.
   * <p>
   * This is an optional property.
   */
  private Integer frameId;
  
  /**
   * Specifies how the resulting value should be formatted.
   * <p>
   * This is an optional property.
   */
  private ValueFormat format;
  
  /**
   * The l-value expression to assign to.
   */
  @Pure
  @NonNull
  public String getExpression() {
    return this.expression;
  }
  
  /**
   * The l-value expression to assign to.
   */
  public void setExpression(@NonNull final String expression) {
    this.expression = Preconditions.checkNotNull(expression, "expression");
  }
  
  /**
   * The value expression to assign to the l-value expression.
   */
  @Pure
  @NonNull
  public String getValue() {
    return this.value;
  }
  
  /**
   * The value expression to assign to the l-value expression.
   */
  public void setValue(@NonNull final String value) {
    this.value = Preconditions.checkNotNull(value, "value");
  }
  
  /**
   * Evaluate the expressions in the scope of this stack frame. If not specified, the expressions are evaluated in
   * the global scope.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getFrameId() {
    return this.frameId;
  }
  
  /**
   * Evaluate the expressions in the scope of this stack frame. If not specified, the expressions are evaluated in
   * the global scope.
   * <p>
   * This is an optional property.
   */
  public void setFrameId(final Integer frameId) {
    this.frameId = frameId;
  }
  
  /**
   * Specifies how the resulting value should be formatted.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ValueFormat getFormat() {
    return this.format;
  }
  
  /**
   * Specifies how the resulting value should be formatted.
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
    b.add("expression", this.expression);
    b.add("value", this.value);
    b.add("frameId", this.frameId);
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
    SetExpressionArguments other = (SetExpressionArguments) obj;
    if (this.expression == null) {
      if (other.expression != null)
        return false;
    } else if (!this.expression.equals(other.expression))
      return false;
    if (this.value == null) {
      if (other.value != null)
        return false;
    } else if (!this.value.equals(other.value))
      return false;
    if (this.frameId == null) {
      if (other.frameId != null)
        return false;
    } else if (!this.frameId.equals(other.frameId))
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
    result = prime * result + ((this.expression== null) ? 0 : this.expression.hashCode());
    result = prime * result + ((this.value== null) ? 0 : this.value.hashCode());
    result = prime * result + ((this.frameId== null) ? 0 : this.frameId.hashCode());
    return prime * result + ((this.format== null) ? 0 : this.format.hashCode());
  }
}
