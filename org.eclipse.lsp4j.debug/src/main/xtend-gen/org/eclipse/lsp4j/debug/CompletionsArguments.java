/**
 * Copyright (c) 2017, 2018 Kichwa Coders Ltd. and others.
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
 * Arguments for 'completions' request.
 */
@SuppressWarnings("all")
public class CompletionsArguments {
  /**
   * Returns completions in the scope of this stack frame. If not specified, the completions are returned for the
   * global scope.
   * <p>
   * This is an optional property.
   */
  private Long frameId;
  
  /**
   * One or more source lines. Typically this is the text a user has typed into the debug console before he asked
   * for completion.
   */
  @NonNull
  private String text;
  
  /**
   * The character position for which to determine the completion proposals.
   */
  @NonNull
  private Long column;
  
  /**
   * An optional line for which to determine the completion proposals. If missing the first line of the text is
   * assumed.
   * <p>
   * This is an optional property.
   */
  private Long line;
  
  /**
   * Returns completions in the scope of this stack frame. If not specified, the completions are returned for the
   * global scope.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Long getFrameId() {
    return this.frameId;
  }
  
  /**
   * Returns completions in the scope of this stack frame. If not specified, the completions are returned for the
   * global scope.
   * <p>
   * This is an optional property.
   */
  public void setFrameId(final Long frameId) {
    this.frameId = frameId;
  }
  
  /**
   * One or more source lines. Typically this is the text a user has typed into the debug console before he asked
   * for completion.
   */
  @Pure
  @NonNull
  public String getText() {
    return this.text;
  }
  
  /**
   * One or more source lines. Typically this is the text a user has typed into the debug console before he asked
   * for completion.
   */
  public void setText(@NonNull final String text) {
    if (text == null) {
      throw new IllegalArgumentException("Property must not be null: text");
    }
    this.text = text;
  }
  
  /**
   * The character position for which to determine the completion proposals.
   */
  @Pure
  @NonNull
  public Long getColumn() {
    return this.column;
  }
  
  /**
   * The character position for which to determine the completion proposals.
   */
  public void setColumn(@NonNull final Long column) {
    if (column == null) {
      throw new IllegalArgumentException("Property must not be null: column");
    }
    this.column = column;
  }
  
  /**
   * An optional line for which to determine the completion proposals. If missing the first line of the text is
   * assumed.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Long getLine() {
    return this.line;
  }
  
  /**
   * An optional line for which to determine the completion proposals. If missing the first line of the text is
   * assumed.
   * <p>
   * This is an optional property.
   */
  public void setLine(final Long line) {
    this.line = line;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("frameId", this.frameId);
    b.add("text", this.text);
    b.add("column", this.column);
    b.add("line", this.line);
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
    CompletionsArguments other = (CompletionsArguments) obj;
    if (this.frameId == null) {
      if (other.frameId != null)
        return false;
    } else if (!this.frameId.equals(other.frameId))
      return false;
    if (this.text == null) {
      if (other.text != null)
        return false;
    } else if (!this.text.equals(other.text))
      return false;
    if (this.column == null) {
      if (other.column != null)
        return false;
    } else if (!this.column.equals(other.column))
      return false;
    if (this.line == null) {
      if (other.line != null)
        return false;
    } else if (!this.line.equals(other.line))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.frameId== null) ? 0 : this.frameId.hashCode());
    result = prime * result + ((this.text== null) ? 0 : this.text.hashCode());
    result = prime * result + ((this.column== null) ? 0 : this.column.hashCode());
    return prime * result + ((this.line== null) ? 0 : this.line.hashCode());
  }
}
