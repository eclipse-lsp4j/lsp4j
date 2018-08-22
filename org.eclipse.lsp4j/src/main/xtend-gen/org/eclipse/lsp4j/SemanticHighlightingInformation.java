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

import com.google.common.annotations.Beta;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a semantic highlighting information that has to be applied on a specific line of the text document.
 */
@Beta
@SuppressWarnings("all")
public class SemanticHighlightingInformation {
  /**
   * The zero-based line position in the text document.
   */
  private int line;
  
  /**
   * A base64 encoded string representing every single highlighted ranges in the line with its start position, length
   * and the "lookup table" index of of the semantic highlighting <a href="https://manual.macromates.com/en/language_grammars">
   * TextMate scopes</a>. If the {@code tokens} is empty or not defined, then no highlighted positions are available for the line.
   */
  private String tokens;
  
  public SemanticHighlightingInformation() {
  }
  
  public SemanticHighlightingInformation(final int line, final String tokens) {
    this.line = line;
    this.tokens = tokens;
  }
  
  /**
   * The zero-based line position in the text document.
   */
  @Pure
  public int getLine() {
    return this.line;
  }
  
  /**
   * The zero-based line position in the text document.
   */
  public void setLine(final int line) {
    this.line = line;
  }
  
  /**
   * A base64 encoded string representing every single highlighted ranges in the line with its start position, length
   * and the "lookup table" index of of the semantic highlighting <a href="https://manual.macromates.com/en/language_grammars">
   * TextMate scopes</a>. If the {@code tokens} is empty or not defined, then no highlighted positions are available for the line.
   */
  @Pure
  public String getTokens() {
    return this.tokens;
  }
  
  /**
   * A base64 encoded string representing every single highlighted ranges in the line with its start position, length
   * and the "lookup table" index of of the semantic highlighting <a href="https://manual.macromates.com/en/language_grammars">
   * TextMate scopes</a>. If the {@code tokens} is empty or not defined, then no highlighted positions are available for the line.
   */
  public void setTokens(final String tokens) {
    this.tokens = tokens;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("line", this.line);
    b.add("tokens", this.tokens);
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
    SemanticHighlightingInformation other = (SemanticHighlightingInformation) obj;
    if (other.line != this.line)
      return false;
    if (this.tokens == null) {
      if (other.tokens != null)
        return false;
    } else if (!this.tokens.equals(other.tokens))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.line;
    return prime * result + ((this.tokens== null) ? 0 : this.tokens.hashCode());
  }
}
