/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
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

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A GotoTarget describes a code location that can be used as a target in the 'goto' request.
 * <p>
 * The possible goto targets can be determined via the 'gotoTargets' request.
 */
@SuppressWarnings("all")
public class GotoTarget {
  /**
   * Unique identifier for a goto target. This is used in the goto request.
   */
  @NonNull
  private Long id;
  
  /**
   * The name of the goto target (shown in the UI).
   */
  @NonNull
  private String label;
  
  /**
   * The line of the goto target.
   */
  @NonNull
  private Long line;
  
  /**
   * An optional column of the goto target.
   * <p>
   * This is an optional property.
   */
  private Long column;
  
  /**
   * An optional end line of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  private Long endLine;
  
  /**
   * An optional end column of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  private Long endColumn;
  
  /**
   * Unique identifier for a goto target. This is used in the goto request.
   */
  @Pure
  @NonNull
  public Long getId() {
    return this.id;
  }
  
  /**
   * Unique identifier for a goto target. This is used in the goto request.
   */
  public void setId(@NonNull final Long id) {
    this.id = id;
  }
  
  /**
   * The name of the goto target (shown in the UI).
   */
  @Pure
  @NonNull
  public String getLabel() {
    return this.label;
  }
  
  /**
   * The name of the goto target (shown in the UI).
   */
  public void setLabel(@NonNull final String label) {
    this.label = label;
  }
  
  /**
   * The line of the goto target.
   */
  @Pure
  @NonNull
  public Long getLine() {
    return this.line;
  }
  
  /**
   * The line of the goto target.
   */
  public void setLine(@NonNull final Long line) {
    this.line = line;
  }
  
  /**
   * An optional column of the goto target.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Long getColumn() {
    return this.column;
  }
  
  /**
   * An optional column of the goto target.
   * <p>
   * This is an optional property.
   */
  public void setColumn(final Long column) {
    this.column = column;
  }
  
  /**
   * An optional end line of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Long getEndLine() {
    return this.endLine;
  }
  
  /**
   * An optional end line of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  public void setEndLine(final Long endLine) {
    this.endLine = endLine;
  }
  
  /**
   * An optional end column of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Long getEndColumn() {
    return this.endColumn;
  }
  
  /**
   * An optional end column of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  public void setEndColumn(final Long endColumn) {
    this.endColumn = endColumn;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("label", this.label);
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
    GotoTarget other = (GotoTarget) obj;
    if (this.id == null) {
      if (other.id != null)
        return false;
    } else if (!this.id.equals(other.id))
      return false;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id== null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + ((this.line== null) ? 0 : this.line.hashCode());
    result = prime * result + ((this.column== null) ? 0 : this.column.hashCode());
    result = prime * result + ((this.endLine== null) ? 0 : this.endLine.hashCode());
    return prime * result + ((this.endColumn== null) ? 0 : this.endColumn.hashCode());
  }
}
