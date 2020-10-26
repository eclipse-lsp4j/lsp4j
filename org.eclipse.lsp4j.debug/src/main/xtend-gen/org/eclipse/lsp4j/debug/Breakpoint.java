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

import org.eclipse.lsp4j.debug.Source;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Information about a Breakpoint created in setBreakpoints, setFunctionBreakpoints, setInstructionBreakpoints, or
 * setDataBreakpoints.
 */
@SuppressWarnings("all")
public class Breakpoint {
  /**
   * An optional identifier for the breakpoint. It is needed if breakpoint events are used to update or remove
   * breakpoints.
   * <p>
   * This is an optional property.
   */
  private Integer id;
  
  /**
   * If true breakpoint could be set (but not necessarily at the desired location).
   */
  private boolean verified;
  
  /**
   * An optional message about the state of the breakpoint.
   * <p>
   * This is shown to the user and can be used to explain why a breakpoint could not be verified.
   * <p>
   * This is an optional property.
   */
  private String message;
  
  /**
   * The source where the breakpoint is located.
   * <p>
   * This is an optional property.
   */
  private Source source;
  
  /**
   * The start line of the actual range covered by the breakpoint.
   * <p>
   * This is an optional property.
   */
  private Integer line;
  
  /**
   * An optional start column of the actual range covered by the breakpoint.
   * <p>
   * This is an optional property.
   */
  private Integer column;
  
  /**
   * An optional end line of the actual range covered by the breakpoint.
   * <p>
   * This is an optional property.
   */
  private Integer endLine;
  
  /**
   * An optional end column of the actual range covered by the breakpoint.
   * <p>
   * If no end line is given, then the end column is assumed to be in the start line.
   * <p>
   * This is an optional property.
   */
  private Integer endColumn;
  
  /**
   * An optional memory reference to where the breakpoint is set.
   * <p>
   * This is an optional property.
   */
  private String instructionReference;
  
  /**
   * An optional offset from the instruction reference.
   * <p>
   * This can be negative.
   * <p>
   * This is an optional property.
   */
  private Integer offset;
  
  /**
   * An optional identifier for the breakpoint. It is needed if breakpoint events are used to update or remove
   * breakpoints.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getId() {
    return this.id;
  }
  
  /**
   * An optional identifier for the breakpoint. It is needed if breakpoint events are used to update or remove
   * breakpoints.
   * <p>
   * This is an optional property.
   */
  public void setId(final Integer id) {
    this.id = id;
  }
  
  /**
   * If true breakpoint could be set (but not necessarily at the desired location).
   */
  @Pure
  public boolean isVerified() {
    return this.verified;
  }
  
  /**
   * If true breakpoint could be set (but not necessarily at the desired location).
   */
  public void setVerified(final boolean verified) {
    this.verified = verified;
  }
  
  /**
   * An optional message about the state of the breakpoint.
   * <p>
   * This is shown to the user and can be used to explain why a breakpoint could not be verified.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getMessage() {
    return this.message;
  }
  
  /**
   * An optional message about the state of the breakpoint.
   * <p>
   * This is shown to the user and can be used to explain why a breakpoint could not be verified.
   * <p>
   * This is an optional property.
   */
  public void setMessage(final String message) {
    this.message = message;
  }
  
  /**
   * The source where the breakpoint is located.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Source getSource() {
    return this.source;
  }
  
  /**
   * The source where the breakpoint is located.
   * <p>
   * This is an optional property.
   */
  public void setSource(final Source source) {
    this.source = source;
  }
  
  /**
   * The start line of the actual range covered by the breakpoint.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getLine() {
    return this.line;
  }
  
  /**
   * The start line of the actual range covered by the breakpoint.
   * <p>
   * This is an optional property.
   */
  public void setLine(final Integer line) {
    this.line = line;
  }
  
  /**
   * An optional start column of the actual range covered by the breakpoint.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getColumn() {
    return this.column;
  }
  
  /**
   * An optional start column of the actual range covered by the breakpoint.
   * <p>
   * This is an optional property.
   */
  public void setColumn(final Integer column) {
    this.column = column;
  }
  
  /**
   * An optional end line of the actual range covered by the breakpoint.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndLine() {
    return this.endLine;
  }
  
  /**
   * An optional end line of the actual range covered by the breakpoint.
   * <p>
   * This is an optional property.
   */
  public void setEndLine(final Integer endLine) {
    this.endLine = endLine;
  }
  
  /**
   * An optional end column of the actual range covered by the breakpoint.
   * <p>
   * If no end line is given, then the end column is assumed to be in the start line.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndColumn() {
    return this.endColumn;
  }
  
  /**
   * An optional end column of the actual range covered by the breakpoint.
   * <p>
   * If no end line is given, then the end column is assumed to be in the start line.
   * <p>
   * This is an optional property.
   */
  public void setEndColumn(final Integer endColumn) {
    this.endColumn = endColumn;
  }
  
  /**
   * An optional memory reference to where the breakpoint is set.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getInstructionReference() {
    return this.instructionReference;
  }
  
  /**
   * An optional memory reference to where the breakpoint is set.
   * <p>
   * This is an optional property.
   */
  public void setInstructionReference(final String instructionReference) {
    this.instructionReference = instructionReference;
  }
  
  /**
   * An optional offset from the instruction reference.
   * <p>
   * This can be negative.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getOffset() {
    return this.offset;
  }
  
  /**
   * An optional offset from the instruction reference.
   * <p>
   * This can be negative.
   * <p>
   * This is an optional property.
   */
  public void setOffset(final Integer offset) {
    this.offset = offset;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("verified", this.verified);
    b.add("message", this.message);
    b.add("source", this.source);
    b.add("line", this.line);
    b.add("column", this.column);
    b.add("endLine", this.endLine);
    b.add("endColumn", this.endColumn);
    b.add("instructionReference", this.instructionReference);
    b.add("offset", this.offset);
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
    Breakpoint other = (Breakpoint) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (other.verified != this.verified)
      return false;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
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
    if (this.endLine == null) {
      if (other.endLine != null)
        return false;
    } else if (!this.endLine.equals(other.endLine))
      return false;
    if (this.endColumn == null) {
      if (other.endColumn != null)
        return false;
    } else if (!this.endColumn.equals(other.endColumn))
      return false;
    if (this.instructionReference == null) {
      if (other.instructionReference != null)
        return false;
    } else if (!this.instructionReference.equals(other.instructionReference))
      return false;
    if (this.offset == null) {
      if (other.offset != null)
        return false;
    } else if (!this.offset.equals(other.offset))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id== null) ? 0 : this.id.hashCode());
    result = prime * result + (this.verified ? 1231 : 1237);
    result = prime * result + ((this.message== null) ? 0 : this.message.hashCode());
    result = prime * result + ((this.source== null) ? 0 : this.source.hashCode());
    result = prime * result + ((this.line== null) ? 0 : this.line.hashCode());
    result = prime * result + ((this.column== null) ? 0 : this.column.hashCode());
    result = prime * result + ((this.endLine== null) ? 0 : this.endLine.hashCode());
    result = prime * result + ((this.endColumn== null) ? 0 : this.endColumn.hashCode());
    result = prime * result + ((this.instructionReference== null) ? 0 : this.instructionReference.hashCode());
    return prime * result + ((this.offset== null) ? 0 : this.offset.hashCode());
  }
}
