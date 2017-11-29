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
 * Arguments for 'evaluate' request.
 */
@SuppressWarnings("all")
public class EvaluateArguments {
  /**
   * The expression to evaluate.
   */
  private String expression;
  
  /**
   * Evaluate the expression in the scope of this stack frame. If not specified, the expression is evaluated in the
   * global scope.
   * <p>
   * This is an optional property.
   */
  private Integer frameId;
  
  /**
   * The context in which the evaluate request is run.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link EvaluateArgumentsContext}
   */
  private String context;
  
  /**
   * Specifies details on how to format the Evaluate result.
   * <p>
   * This is an optional property.
   */
  private ValueFormat format;
  
  /**
   * The expression to evaluate.
   */
  @Pure
  public String getExpression() {
    return this.expression;
  }
  
  /**
   * The expression to evaluate.
   */
  public void setExpression(final String expression) {
    this.expression = expression;
  }
  
  /**
   * Evaluate the expression in the scope of this stack frame. If not specified, the expression is evaluated in the
   * global scope.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getFrameId() {
    return this.frameId;
  }
  
  /**
   * Evaluate the expression in the scope of this stack frame. If not specified, the expression is evaluated in the
   * global scope.
   * <p>
   * This is an optional property.
   */
  public void setFrameId(final Integer frameId) {
    this.frameId = frameId;
  }
  
  /**
   * The context in which the evaluate request is run.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link EvaluateArgumentsContext}
   */
  @Pure
  public String getContext() {
    return this.context;
  }
  
  /**
   * The context in which the evaluate request is run.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link EvaluateArgumentsContext}
   */
  public void setContext(final String context) {
    this.context = context;
  }
  
  /**
   * Specifies details on how to format the Evaluate result.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ValueFormat getFormat() {
    return this.format;
  }
  
  /**
   * Specifies details on how to format the Evaluate result.
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
    b.add("frameId", this.frameId);
    b.add("context", this.context);
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
    EvaluateArguments other = (EvaluateArguments) obj;
    if (this.expression == null) {
      if (other.expression != null)
        return false;
    } else if (!this.expression.equals(other.expression))
      return false;
    if (this.frameId == null) {
      if (other.frameId != null)
        return false;
    } else if (!this.frameId.equals(other.frameId))
      return false;
    if (this.context == null) {
      if (other.context != null)
        return false;
    } else if (!this.context.equals(other.context))
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
    result = prime * result + ((this.frameId== null) ? 0 : this.frameId.hashCode());
    result = prime * result + ((this.context== null) ? 0 : this.context.hashCode());
    result = prime * result + ((this.format== null) ? 0 : this.format.hashCode());
    return result;
  }
}
