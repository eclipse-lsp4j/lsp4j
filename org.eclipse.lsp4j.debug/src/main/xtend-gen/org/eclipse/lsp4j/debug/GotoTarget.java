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
 * A GotoTarget describes a code location that can be used as a target in the 'goto' request.
 * <p>
 * The possible goto targets can be determined via the 'gotoTargets' request.
 */
@SuppressWarnings("all")
public class GotoTarget {
  /**
   * Unique identifier for a goto target. This is used in the goto request.
   */
  private int id;
  
  /**
   * The name of the goto target (shown in the UI).
   */
  @NonNull
  private String label;
  
  /**
   * The line of the goto target.
   */
  private int line;
  
  /**
   * An optional column of the goto target.
   * <p>
   * This is an optional property.
   */
  private Integer column;
  
  /**
   * An optional end line of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  private Integer endLine;
  
  /**
   * An optional end column of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  private Integer endColumn;
  
  /**
   * Optional memory reference for the instruction pointer value represented by this target.
   * <p>
   * This is an optional property.
   */
  private String instructionPointerReference;
  
  /**
   * Unique identifier for a goto target. This is used in the goto request.
   */
  @Pure
  public int getId() {
    return this.id;
  }
  
  /**
   * Unique identifier for a goto target. This is used in the goto request.
   */
  public void setId(final int id) {
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
    this.label = Preconditions.checkNotNull(label, "label");
  }
  
  /**
   * The line of the goto target.
   */
  @Pure
  public int getLine() {
    return this.line;
  }
  
  /**
   * The line of the goto target.
   */
  public void setLine(final int line) {
    this.line = line;
  }
  
  /**
   * An optional column of the goto target.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getColumn() {
    return this.column;
  }
  
  /**
   * An optional column of the goto target.
   * <p>
   * This is an optional property.
   */
  public void setColumn(final Integer column) {
    this.column = column;
  }
  
  /**
   * An optional end line of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndLine() {
    return this.endLine;
  }
  
  /**
   * An optional end line of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  public void setEndLine(final Integer endLine) {
    this.endLine = endLine;
  }
  
  /**
   * An optional end column of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndColumn() {
    return this.endColumn;
  }
  
  /**
   * An optional end column of the range covered by the goto target.
   * <p>
   * This is an optional property.
   */
  public void setEndColumn(final Integer endColumn) {
    this.endColumn = endColumn;
  }
  
  /**
   * Optional memory reference for the instruction pointer value represented by this target.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getInstructionPointerReference() {
    return this.instructionPointerReference;
  }
  
  /**
   * Optional memory reference for the instruction pointer value represented by this target.
   * <p>
   * This is an optional property.
   */
  public void setInstructionPointerReference(final String instructionPointerReference) {
    this.instructionPointerReference = instructionPointerReference;
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
    b.add("instructionPointerReference", this.instructionPointerReference);
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
    if (other.id != this.id)
      return false;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
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
    if (this.instructionPointerReference == null) {
      if (other.instructionPointerReference != null)
        return false;
    } else if (!this.instructionPointerReference.equals(other.instructionPointerReference))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.id;
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    result = prime * result + this.line;
    result = prime * result + ((this.column== null) ? 0 : this.column.hashCode());
    result = prime * result + ((this.endLine== null) ? 0 : this.endLine.hashCode());
    result = prime * result + ((this.endColumn== null) ? 0 : this.endColumn.hashCode());
    return prime * result + ((this.instructionPointerReference== null) ? 0 : this.instructionPointerReference.hashCode());
  }
}
