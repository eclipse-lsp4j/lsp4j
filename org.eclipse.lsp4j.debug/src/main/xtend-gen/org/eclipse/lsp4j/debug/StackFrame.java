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
import org.eclipse.lsp4j.debug.StackFramePresentationHint;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A Stackframe contains the source location.
 */
@SuppressWarnings("all")
public class StackFrame {
  /**
   * An identifier for the stack frame. It must be unique across all threads.
   * <p>
   * This id can be used to retrieve the scopes of the frame with the 'scopesRequest' or to restart the execution of
   * a stackframe.
   */
  private int id;
  
  /**
   * The name of the stack frame, typically a method name.
   */
  @NonNull
  private String name;
  
  /**
   * The optional source of the frame.
   * <p>
   * This is an optional property.
   */
  private Source source;
  
  /**
   * The line within the file of the frame. If source is null or doesn't exist, line is 0 and must be ignored.
   */
  private int line;
  
  /**
   * The column within the line. If source is null or doesn't exist, column is 0 and must be ignored.
   */
  private int column;
  
  /**
   * An optional end line of the range covered by the stack frame.
   * <p>
   * This is an optional property.
   */
  private Integer endLine;
  
  /**
   * An optional end column of the range covered by the stack frame.
   * <p>
   * This is an optional property.
   */
  private Integer endColumn;
  
  /**
   * Optional memory reference for the current instruction pointer in this frame.
   * <p>
   * This is an optional property.
   */
  private String instructionPointerReference;
  
  /**
   * The module associated with this frame, if any.
   * <p>
   * This is an optional property.
   */
  private Either<Integer, String> moduleId;
  
  /**
   * An optional hint for how to present this frame in the UI.
   * <p>
   * A value of 'label' can be used to indicate that the frame is an artificial frame that is used as a visual label
   * or separator. A value of 'subtle' can be used to change the appearance of a frame in a 'subtle' way.
   * <p>
   * This is an optional property.
   */
  private StackFramePresentationHint presentationHint;
  
  /**
   * An identifier for the stack frame. It must be unique across all threads.
   * <p>
   * This id can be used to retrieve the scopes of the frame with the 'scopesRequest' or to restart the execution of
   * a stackframe.
   */
  @Pure
  public int getId() {
    return this.id;
  }
  
  /**
   * An identifier for the stack frame. It must be unique across all threads.
   * <p>
   * This id can be used to retrieve the scopes of the frame with the 'scopesRequest' or to restart the execution of
   * a stackframe.
   */
  public void setId(final int id) {
    this.id = id;
  }
  
  /**
   * The name of the stack frame, typically a method name.
   */
  @Pure
  @NonNull
  public String getName() {
    return this.name;
  }
  
  /**
   * The name of the stack frame, typically a method name.
   */
  public void setName(@NonNull final String name) {
    this.name = Preconditions.checkNotNull(name, "name");
  }
  
  /**
   * The optional source of the frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Source getSource() {
    return this.source;
  }
  
  /**
   * The optional source of the frame.
   * <p>
   * This is an optional property.
   */
  public void setSource(final Source source) {
    this.source = source;
  }
  
  /**
   * The line within the file of the frame. If source is null or doesn't exist, line is 0 and must be ignored.
   */
  @Pure
  public int getLine() {
    return this.line;
  }
  
  /**
   * The line within the file of the frame. If source is null or doesn't exist, line is 0 and must be ignored.
   */
  public void setLine(final int line) {
    this.line = line;
  }
  
  /**
   * The column within the line. If source is null or doesn't exist, column is 0 and must be ignored.
   */
  @Pure
  public int getColumn() {
    return this.column;
  }
  
  /**
   * The column within the line. If source is null or doesn't exist, column is 0 and must be ignored.
   */
  public void setColumn(final int column) {
    this.column = column;
  }
  
  /**
   * An optional end line of the range covered by the stack frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndLine() {
    return this.endLine;
  }
  
  /**
   * An optional end line of the range covered by the stack frame.
   * <p>
   * This is an optional property.
   */
  public void setEndLine(final Integer endLine) {
    this.endLine = endLine;
  }
  
  /**
   * An optional end column of the range covered by the stack frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getEndColumn() {
    return this.endColumn;
  }
  
  /**
   * An optional end column of the range covered by the stack frame.
   * <p>
   * This is an optional property.
   */
  public void setEndColumn(final Integer endColumn) {
    this.endColumn = endColumn;
  }
  
  /**
   * Optional memory reference for the current instruction pointer in this frame.
   * <p>
   * This is an optional property.
   */
  @Pure
  public String getInstructionPointerReference() {
    return this.instructionPointerReference;
  }
  
  /**
   * Optional memory reference for the current instruction pointer in this frame.
   * <p>
   * This is an optional property.
   */
  public void setInstructionPointerReference(final String instructionPointerReference) {
    this.instructionPointerReference = instructionPointerReference;
  }
  
  /**
   * The module associated with this frame, if any.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Either<Integer, String> getModuleId() {
    return this.moduleId;
  }
  
  /**
   * The module associated with this frame, if any.
   * <p>
   * This is an optional property.
   */
  public void setModuleId(final Either<Integer, String> moduleId) {
    this.moduleId = moduleId;
  }
  
  public void setModuleId(final Integer moduleId) {
    if (moduleId == null) {
      this.moduleId = null;
      return;
    }
    this.moduleId = Either.forLeft(moduleId);
  }
  
  public void setModuleId(final String moduleId) {
    if (moduleId == null) {
      this.moduleId = null;
      return;
    }
    this.moduleId = Either.forRight(moduleId);
  }
  
  /**
   * An optional hint for how to present this frame in the UI.
   * <p>
   * A value of 'label' can be used to indicate that the frame is an artificial frame that is used as a visual label
   * or separator. A value of 'subtle' can be used to change the appearance of a frame in a 'subtle' way.
   * <p>
   * This is an optional property.
   */
  @Pure
  public StackFramePresentationHint getPresentationHint() {
    return this.presentationHint;
  }
  
  /**
   * An optional hint for how to present this frame in the UI.
   * <p>
   * A value of 'label' can be used to indicate that the frame is an artificial frame that is used as a visual label
   * or separator. A value of 'subtle' can be used to change the appearance of a frame in a 'subtle' way.
   * <p>
   * This is an optional property.
   */
  public void setPresentationHint(final StackFramePresentationHint presentationHint) {
    this.presentationHint = presentationHint;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("id", this.id);
    b.add("name", this.name);
    b.add("source", this.source);
    b.add("line", this.line);
    b.add("column", this.column);
    b.add("endLine", this.endLine);
    b.add("endColumn", this.endColumn);
    b.add("instructionPointerReference", this.instructionPointerReference);
    b.add("moduleId", this.moduleId);
    b.add("presentationHint", this.presentationHint);
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
    StackFrame other = (StackFrame) obj;
    if (other.id != this.id)
      return false;
    if (this.name == null) {
      if (other.name != null)
        return false;
    } else if (!this.name.equals(other.name))
      return false;
    if (this.source == null) {
      if (other.source != null)
        return false;
    } else if (!this.source.equals(other.source))
      return false;
    if (other.line != this.line)
      return false;
    if (other.column != this.column)
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
    if (this.moduleId == null) {
      if (other.moduleId != null)
        return false;
    } else if (!this.moduleId.equals(other.moduleId))
      return false;
    if (this.presentationHint == null) {
      if (other.presentationHint != null)
        return false;
    } else if (!this.presentationHint.equals(other.presentationHint))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.id;
    result = prime * result + ((this.name== null) ? 0 : this.name.hashCode());
    result = prime * result + ((this.source== null) ? 0 : this.source.hashCode());
    result = prime * result + this.line;
    result = prime * result + this.column;
    result = prime * result + ((this.endLine== null) ? 0 : this.endLine.hashCode());
    result = prime * result + ((this.endColumn== null) ? 0 : this.endColumn.hashCode());
    result = prime * result + ((this.instructionPointerReference== null) ? 0 : this.instructionPointerReference.hashCode());
    result = prime * result + ((this.moduleId== null) ? 0 : this.moduleId.hashCode());
    return prime * result + ((this.presentationHint== null) ? 0 : this.presentationHint.hashCode());
  }
}
