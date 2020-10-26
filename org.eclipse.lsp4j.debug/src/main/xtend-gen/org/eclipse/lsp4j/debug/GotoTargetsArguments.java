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
 * Arguments for 'gotoTargets' request.
 */
@SuppressWarnings("all")
public class GotoTargetsArguments {
  /**
   * The source location for which the goto targets are determined.
   */
  @NonNull
  private Source source;
  
  /**
   * The line location for which the goto targets are determined.
   */
  private int line;
  
  /**
   * An optional column location for which the goto targets are determined.
   * <p>
   * This is an optional property.
   */
  private Integer column;
  
  /**
   * The source location for which the goto targets are determined.
   */
  @Pure
  @NonNull
  public Source getSource() {
    return this.source;
  }
  
  /**
   * The source location for which the goto targets are determined.
   */
  public void setSource(@NonNull final Source source) {
    this.source = Preconditions.checkNotNull(source, "source");
  }
  
  /**
   * The line location for which the goto targets are determined.
   */
  @Pure
  public int getLine() {
    return this.line;
  }
  
  /**
   * The line location for which the goto targets are determined.
   */
  public void setLine(final int line) {
    this.line = line;
  }
  
  /**
   * An optional column location for which the goto targets are determined.
   * <p>
   * This is an optional property.
   */
  @Pure
  public Integer getColumn() {
    return this.column;
  }
  
  /**
   * An optional column location for which the goto targets are determined.
   * <p>
   * This is an optional property.
   */
  public void setColumn(final Integer column) {
    this.column = column;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("source", this.source);
    b.add("line", this.line);
    b.add("column", this.column);
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
    GotoTargetsArguments other = (GotoTargetsArguments) obj;
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.source== null) ? 0 : this.source.hashCode());
    result = prime * result + this.line;
    return prime * result + ((this.column== null) ? 0 : this.column.hashCode());
  }
}
