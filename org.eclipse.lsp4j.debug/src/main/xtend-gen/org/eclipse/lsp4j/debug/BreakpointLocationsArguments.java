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
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Arguments for 'breakpointLocations' request.
 */
@SuppressWarnings("all")
public class BreakpointLocationsArguments {
  /**
   * The source location of the breakpoints; either 'source.path' or 'source.reference' must be specified.
   */
  @NonNull
  private Source source;
  
  /**
   * Start line of range to search possible breakpoint locations in. If only the line is specified, the request
   * returns all possible locations in that line.
   */
  private int line;
  
  /**
   * Optional start column of range to search possible breakpoint locations in. If no start column is given, the
   * first column in the start line is assumed.
   * <p>
   * This is an optional property.
   */
  private Integer column;
  
  /**
   * Optional end line of range to search possible breakpoint locations in. If no end line is given, then the end
   * line is assumed to be the start line.
   * <p>
   * This is an optional property.
   */
  private Integer endLine;
  
  /**
   * Optional end column of range to search possible breakpoint locations in. If no end column is given, then it is
   * assumed to be in the last column of the end line.
   * <p>
   * This is an optional property.
   */
  private Integer endColumn;
  
  /**
   * The source location of the breakpoints; either 'source.path' or 'source.reference' must be specified.
   */
  @Pure
  @NonNull
  public Source getSource() {
    return this.source;
  }
  
  /**
   * The source location of the breakpoints; either 'source.path' or 'source.reference' must be specified.
   */
  public void setSource(@NonNull final Source source) {
    this.source = Preconditions.checkNotNull(source, "source");
  }
  
  /**
   * Start line of range to search possible breakpoint locations in. If only the line is specified, the request
   * returns all possible locations in that line.
   */
  @Pure
  public int getLine() {
    return this.line;
  }
  
  /**
   * Start line of range to search possible breakpoint locations in. If only the line is specified, the request
   * returns all possible locations in that line.
   */
  public void setLine(final int line) {
    this.line = line;
  }
  
  /**
   * Optional start column of range to search possible breakpoint locations in. If no start column is given, the
   * first column in the start line is assumed.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getColumn() {
    return this.column;
  }
  
  /**
   * Optional start column of range to search possible breakpoint locations in. If no start column is given, the
   * first column in the start line is assumed.
   * <p>
   * This is an optional property.
   */
  public void setColumn(final Integer column) {
    this.column = column;
  }
  
  /**
   * Optional end line of range to search possible breakpoint locations in. If no end line is given, then the end
   * line is assumed to be the start line.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndLine() {
    return this.endLine;
  }
  
  /**
   * Optional end line of range to search possible breakpoint locations in. If no end line is given, then the end
   * line is assumed to be the start line.
   * <p>
   * This is an optional property.
   */
  public void setEndLine(final Integer endLine) {
    this.endLine = endLine;
  }
  
  /**
   * Optional end column of range to search possible breakpoint locations in. If no end column is given, then it is
   * assumed to be in the last column of the end line.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndColumn() {
    return this.endColumn;
  }
  
  /**
   * Optional end column of range to search possible breakpoint locations in. If no end column is given, then it is
   * assumed to be in the last column of the end line.
   * <p>
   * This is an optional property.
   */
  public void setEndColumn(final Integer endColumn) {
    this.endColumn = endColumn;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("source", this.source);
    b.add("line", this.line);
    b.add("column", this.column);
    b.add("endLine", this.endLine);
    b.add("endColumn", this.endColumn);
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
    BreakpointLocationsArguments other = (BreakpointLocationsArguments) obj;
    if (this.source == null) {
      if (other.source != null)
        return false;
    } else if (!this.source.equals(other.source))
      return false;
    if (other.line != this.line)
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.source== null) ? 0 : this.source.hashCode());
    result = prime * result + this.line;
    result = prime * result + ((this.column== null) ? 0 : this.column.hashCode());
    result = prime * result + ((this.endLine== null) ? 0 : this.endLine.hashCode());
    return prime * result + ((this.endColumn== null) ? 0 : this.endColumn.hashCode());
  }
}
