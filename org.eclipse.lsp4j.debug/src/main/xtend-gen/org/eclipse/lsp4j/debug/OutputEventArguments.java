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

import org.eclipse.lsp4j.debug.OutputEventArgumentsGroup;
import org.eclipse.lsp4j.debug.Source;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The event indicates that the target has produced some output.
 */
@SuppressWarnings("all")
public class OutputEventArguments {
  /**
   * The output category. If not specified, 'console' is assumed.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link OutputEventArgumentsCategory}
   */
  private String category;
  
  /**
   * The output to report.
   */
  @NonNull
  private String output;
  
  /**
   * Support for keeping an output log organized by grouping related messages.
   * <p>
   * This is an optional property.
   */
  private OutputEventArgumentsGroup group;
  
  /**
   * If an attribute 'variablesReference' exists and its value is > 0, the output contains objects which can be
   * retrieved by passing 'variablesReference' to the 'variables' request. The value should be less than or equal to
   * 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  private Integer variablesReference;
  
  /**
   * An optional source location where the output was produced.
   * <p>
   * This is an optional property.
   */
  private Source source;
  
  /**
   * An optional source location line where the output was produced.
   * <p>
   * This is an optional property.
   */
  private Integer line;
  
  /**
   * An optional source location column where the output was produced.
   * <p>
   * This is an optional property.
   */
  private Integer column;
  
  /**
   * Optional data to report. For the 'telemetry' category the data will be sent to telemetry, for the other
   * categories the data is shown in JSON format.
   * <p>
   * This is an optional property.
   */
  private Object data;
  
  /**
   * The output category. If not specified, 'console' is assumed.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link OutputEventArgumentsCategory}
   */
  @Pure
  public String getCategory() {
    return this.category;
  }
  
  /**
   * The output category. If not specified, 'console' is assumed.
   * <p>
   * This is an optional property.
   * <p>
   * Possible values include - but not limited to those defined in {@link OutputEventArgumentsCategory}
   */
  public void setCategory(final String category) {
    this.category = category;
  }
  
  /**
   * The output to report.
   */
  @Pure
  @NonNull
  public String getOutput() {
    return this.output;
  }
  
  /**
   * The output to report.
   */
  public void setOutput(@NonNull final String output) {
    this.output = Preconditions.checkNotNull(output, "output");
  }
  
  /**
   * Support for keeping an output log organized by grouping related messages.
   * <p>
   * This is an optional property.
   */
  @Pure
  public OutputEventArgumentsGroup getGroup() {
    return this.group;
  }
  
  /**
   * Support for keeping an output log organized by grouping related messages.
   * <p>
   * This is an optional property.
   */
  public void setGroup(final OutputEventArgumentsGroup group) {
    this.group = group;
  }
  
  /**
   * If an attribute 'variablesReference' exists and its value is > 0, the output contains objects which can be
   * retrieved by passing 'variablesReference' to the 'variables' request. The value should be less than or equal to
   * 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getVariablesReference() {
    return this.variablesReference;
  }
  
  /**
   * If an attribute 'variablesReference' exists and its value is > 0, the output contains objects which can be
   * retrieved by passing 'variablesReference' to the 'variables' request. The value should be less than or equal to
   * 2147483647 (2^31 - 1).
   * <p>
   * This is an optional property.
   */
  public void setVariablesReference(final Integer variablesReference) {
    this.variablesReference = variablesReference;
  }
  
  /**
   * An optional source location where the output was produced.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Source getSource() {
    return this.source;
  }
  
  /**
   * An optional source location where the output was produced.
   * <p>
   * This is an optional property.
   */
  public void setSource(final Source source) {
    this.source = source;
  }
  
  /**
   * An optional source location line where the output was produced.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getLine() {
    return this.line;
  }
  
  /**
   * An optional source location line where the output was produced.
   * <p>
   * This is an optional property.
   */
  public void setLine(final Integer line) {
    this.line = line;
  }
  
  /**
   * An optional source location column where the output was produced.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getColumn() {
    return this.column;
  }
  
  /**
   * An optional source location column where the output was produced.
   * <p>
   * This is an optional property.
   */
  public void setColumn(final Integer column) {
    this.column = column;
  }
  
  /**
   * Optional data to report. For the 'telemetry' category the data will be sent to telemetry, for the other
   * categories the data is shown in JSON format.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Object getData() {
    return this.data;
  }
  
  /**
   * Optional data to report. For the 'telemetry' category the data will be sent to telemetry, for the other
   * categories the data is shown in JSON format.
   * <p>
   * This is an optional property.
   */
  public void setData(final Object data) {
    this.data = data;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("category", this.category);
    b.add("output", this.output);
    b.add("group", this.group);
    b.add("variablesReference", this.variablesReference);
    b.add("source", this.source);
    b.add("line", this.line);
    b.add("column", this.column);
    b.add("data", this.data);
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
    OutputEventArguments other = (OutputEventArguments) obj;
    if (this.category == null) {
      if (other.category != null)
        return false;
    } else if (!this.category.equals(other.category))
      return false;
    if (this.output == null) {
      if (other.output != null)
        return false;
    } else if (!this.output.equals(other.output))
      return false;
    if (this.group == null) {
      if (other.group != null)
        return false;
    } else if (!this.group.equals(other.group))
      return false;
    if (this.variablesReference == null) {
      if (other.variablesReference != null)
        return false;
    } else if (!this.variablesReference.equals(other.variablesReference))
      return false;
    if (this.source == null) {
      if (other.source != null)
        return false;
    } else if (!this.source.equals(other.source))
      return false;
    if (this.line == null) {
      if (other.line != null)
        return false;
    } else if (!this.line.equals(other.line))
      return false;
    if (this.column == null) {
      if (other.column != null)
        return false;
    } else if (!this.column.equals(other.column))
      return false;
    if (this.data == null) {
      if (other.data != null)
        return false;
    } else if (!this.data.equals(other.data))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.category== null) ? 0 : this.category.hashCode());
    result = prime * result + ((this.output== null) ? 0 : this.output.hashCode());
    result = prime * result + ((this.group== null) ? 0 : this.group.hashCode());
    result = prime * result + ((this.variablesReference== null) ? 0 : this.variablesReference.hashCode());
    result = prime * result + ((this.source== null) ? 0 : this.source.hashCode());
    result = prime * result + ((this.line== null) ? 0 : this.line.hashCode());
    result = prime * result + ((this.column== null) ? 0 : this.column.hashCode());
    return prime * result + ((this.data== null) ? 0 : this.data.hashCode());
  }
}
