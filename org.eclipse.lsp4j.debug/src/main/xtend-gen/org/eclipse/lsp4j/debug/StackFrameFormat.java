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
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Provides formatting information for a stack frame.
 */
@SuppressWarnings("all")
public class StackFrameFormat extends ValueFormat {
  /**
   * Displays parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  private Boolean parameters;
  
  /**
   * Displays the types of parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  private Boolean parameterTypes;
  
  /**
   * Displays the names of parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  private Boolean parameterNames;
  
  /**
   * Displays the values of parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  private Boolean parameterValues;
  
  /**
   * Displays the line number of the stack frame.
   * <p>
   * This is an optional property.
   */
  private Boolean line;
  
  /**
   * Displays the module of the stack frame.
   * <p>
   * This is an optional property.
   */
  private Boolean module;
  
  /**
   * Includes all stack frames, including those the debug adapter might otherwise hide.
   * <p>
   * This is an optional property.
   */
  private Boolean includeAll;
  
  /**
   * Displays parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getParameters() {
    return this.parameters;
  }
  
  /**
   * Displays parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  public void setParameters(final Boolean parameters) {
    this.parameters = parameters;
  }
  
  /**
   * Displays the types of parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getParameterTypes() {
    return this.parameterTypes;
  }
  
  /**
   * Displays the types of parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  public void setParameterTypes(final Boolean parameterTypes) {
    this.parameterTypes = parameterTypes;
  }
  
  /**
   * Displays the names of parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getParameterNames() {
    return this.parameterNames;
  }
  
  /**
   * Displays the names of parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  public void setParameterNames(final Boolean parameterNames) {
    this.parameterNames = parameterNames;
  }
  
  /**
   * Displays the values of parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getParameterValues() {
    return this.parameterValues;
  }
  
  /**
   * Displays the values of parameters for the stack frame.
   * <p>
   * This is an optional property.
   */
  public void setParameterValues(final Boolean parameterValues) {
    this.parameterValues = parameterValues;
  }
  
  /**
   * Displays the line number of the stack frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getLine() {
    return this.line;
  }
  
  /**
   * Displays the line number of the stack frame.
   * <p>
   * This is an optional property.
   */
  public void setLine(final Boolean line) {
    this.line = line;
  }
  
  /**
   * Displays the module of the stack frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getModule() {
    return this.module;
  }
  
  /**
   * Displays the module of the stack frame.
   * <p>
   * This is an optional property.
   */
  public void setModule(final Boolean module) {
    this.module = module;
  }
  
  /**
   * Includes all stack frames, including those the debug adapter might otherwise hide.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Boolean getIncludeAll() {
    return this.includeAll;
  }
  
  /**
   * Includes all stack frames, including those the debug adapter might otherwise hide.
   * <p>
   * This is an optional property.
   */
  public void setIncludeAll(final Boolean includeAll) {
    this.includeAll = includeAll;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("parameters", this.parameters);
    b.add("parameterTypes", this.parameterTypes);
    b.add("parameterNames", this.parameterNames);
    b.add("parameterValues", this.parameterValues);
    b.add("line", this.line);
    b.add("module", this.module);
    b.add("includeAll", this.includeAll);
    b.add("hex", getHex());
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
    StackFrameFormat other = (StackFrameFormat) obj;
    if (this.parameters == null) {
      if (other.parameters != null)
        return false;
    } else if (!this.parameters.equals(other.parameters))
      return false;
    if (this.parameterTypes == null) {
      if (other.parameterTypes != null)
        return false;
    } else if (!this.parameterTypes.equals(other.parameterTypes))
      return false;
    if (this.parameterNames == null) {
      if (other.parameterNames != null)
        return false;
    } else if (!this.parameterNames.equals(other.parameterNames))
      return false;
    if (this.parameterValues == null) {
      if (other.parameterValues != null)
        return false;
    } else if (!this.parameterValues.equals(other.parameterValues))
      return false;
    if (this.line == null) {
      if (other.line != null)
        return false;
    } else if (!this.line.equals(other.line))
      return false;
    if (this.module == null) {
      if (other.module != null)
        return false;
    } else if (!this.module.equals(other.module))
      return false;
    if (this.includeAll == null) {
      if (other.includeAll != null)
        return false;
    } else if (!this.includeAll.equals(other.includeAll))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.parameters== null) ? 0 : this.parameters.hashCode());
    result = prime * result + ((this.parameterTypes== null) ? 0 : this.parameterTypes.hashCode());
    result = prime * result + ((this.parameterNames== null) ? 0 : this.parameterNames.hashCode());
    result = prime * result + ((this.parameterValues== null) ? 0 : this.parameterValues.hashCode());
    result = prime * result + ((this.line== null) ? 0 : this.line.hashCode());
    result = prime * result + ((this.module== null) ? 0 : this.module.hashCode());
    return prime * result + ((this.includeAll== null) ? 0 : this.includeAll.hashCode());
  }
}
