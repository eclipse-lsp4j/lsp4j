/**
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Position in a text document expressed as zero-based line and character offset.
 */
@SuppressWarnings("all")
public class Position {
  /**
   * Line position in a document (zero-based).
   */
  private int line;
  
  /**
   * Character offset on a line in a document (zero-based).
   */
  private int character;
  
  public Position() {
  }
  
  public Position(final int line, final int character) {
    this.line = line;
    this.character = character;
  }
  
  /**
   * Line position in a document (zero-based).
   */
  @Pure
  public int getLine() {
    return this.line;
  }
  
  /**
   * Line position in a document (zero-based).
   */
  public void setLine(final int line) {
    this.line = line;
  }
  
  /**
   * Character offset on a line in a document (zero-based).
   */
  @Pure
  public int getCharacter() {
    return this.character;
  }
  
  /**
   * Character offset on a line in a document (zero-based).
   */
  public void setCharacter(final int character) {
    this.character = character;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("line", this.line);
    b.add("character", this.character);
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
    Position other = (Position) obj;
    if (other.line != this.line)
      return false;
    if (other.character != this.character)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.line;
    return prime * result + this.character;
  }
}
