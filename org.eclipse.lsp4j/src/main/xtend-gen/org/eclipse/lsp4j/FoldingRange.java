/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a folding range.
 */
@SuppressWarnings("all")
public class FoldingRange {
  /**
   * The zero-based line number from where the folded range starts.
   */
  private int startLine;
  
  /**
   * The zero-based line number where the folded range ends.
   */
  private int endLine;
  
  /**
   * The zero-based character offset from where the folded range starts. If not defined, defaults
   * to the length of the start line.
   */
  private Integer startCharacter;
  
  /**
   * The zero-based character offset before the folded range ends. If not defined, defaults to the
   * length of the end line.
   */
  private Integer endCharacter;
  
  /**
   * Describes the kind of the folding range such as `comment' or 'region'. The kind
   * is used to categorize folding ranges and used by commands like 'Fold all comments'. See
   * FoldingRangeKind for an enumeration of standardized kinds.
   */
  private String kind;
  
  public FoldingRange() {
  }
  
  public FoldingRange(final int startLine, final int endLine) {
    this.startLine = startLine;
    this.endLine = endLine;
  }
  
  /**
   * The zero-based line number from where the folded range starts.
   */
  @Pure
  public int getStartLine() {
    return this.startLine;
  }
  
  /**
   * The zero-based line number from where the folded range starts.
   */
  public void setStartLine(final int startLine) {
    this.startLine = startLine;
  }
  
  /**
   * The zero-based line number where the folded range ends.
   */
  @Pure
  public int getEndLine() {
    return this.endLine;
  }
  
  /**
   * The zero-based line number where the folded range ends.
   */
  public void setEndLine(final int endLine) {
    this.endLine = endLine;
  }
  
  /**
   * The zero-based character offset from where the folded range starts. If not defined, defaults
   * to the length of the start line.
   */
  @Pure
  public Integer getStartCharacter() {
    return this.startCharacter;
  }
  
  /**
   * The zero-based character offset from where the folded range starts. If not defined, defaults
   * to the length of the start line.
   */
  public void setStartCharacter(final Integer startCharacter) {
    this.startCharacter = startCharacter;
  }
  
  /**
   * The zero-based character offset before the folded range ends. If not defined, defaults to the
   * length of the end line.
   */
  @Pure
  public Integer getEndCharacter() {
    return this.endCharacter;
  }
  
  /**
   * The zero-based character offset before the folded range ends. If not defined, defaults to the
   * length of the end line.
   */
  public void setEndCharacter(final Integer endCharacter) {
    this.endCharacter = endCharacter;
  }
  
  /**
   * Describes the kind of the folding range such as `comment' or 'region'. The kind
   * is used to categorize folding ranges and used by commands like 'Fold all comments'. See
   * FoldingRangeKind for an enumeration of standardized kinds.
   */
  @Pure
  public String getKind() {
    return this.kind;
  }
  
  /**
   * Describes the kind of the folding range such as `comment' or 'region'. The kind
   * is used to categorize folding ranges and used by commands like 'Fold all comments'. See
   * FoldingRangeKind for an enumeration of standardized kinds.
   */
  public void setKind(final String kind) {
    this.kind = kind;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("startLine", this.startLine);
    b.add("endLine", this.endLine);
    b.add("startCharacter", this.startCharacter);
    b.add("endCharacter", this.endCharacter);
    b.add("kind", this.kind);
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
    FoldingRange other = (FoldingRange) obj;
    if (other.startLine != this.startLine)
      return false;
    if (other.endLine != this.endLine)
      return false;
    if (this.startCharacter == null) {
      if (other.startCharacter != null)
        return false;
    } else if (!this.startCharacter.equals(other.startCharacter))
      return false;
    if (this.endCharacter == null) {
      if (other.endCharacter != null)
        return false;
    } else if (!this.endCharacter.equals(other.endCharacter))
      return false;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.startLine;
    result = prime * result + this.endLine;
    result = prime * result + ((this.startCharacter== null) ? 0 : this.startCharacter.hashCode());
    result = prime * result + ((this.endCharacter== null) ? 0 : this.endCharacter.hashCode());
    return prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
  }
}
