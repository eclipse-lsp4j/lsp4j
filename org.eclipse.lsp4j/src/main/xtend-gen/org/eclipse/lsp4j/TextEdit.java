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

import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A textual edit applicable to a text document.
 */
@SuppressWarnings("all")
public class TextEdit {
  /**
   * The range of the text document to be manipulated. To insert text into a document create a range where start === end.
   */
  @NonNull
  private Range range;
  
  /**
   * The string to be inserted. For delete operations use an empty string.
   */
  @NonNull
  private String newText;
  
  public TextEdit() {
  }
  
  public TextEdit(@NonNull final Range range, @NonNull final String newText) {
    this.range = Preconditions.<Range>checkNotNull(range, "range");
    this.newText = Preconditions.<String>checkNotNull(newText, "newText");
  }
  
  /**
   * The range of the text document to be manipulated. To insert text into a document create a range where start === end.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range of the text document to be manipulated. To insert text into a document create a range where start === end.
   */
  public void setRange(@NonNull final Range range) {
    if (range == null) {
      throw new IllegalArgumentException("Property must not be null: range");
    }
    this.range = range;
  }
  
  /**
   * The string to be inserted. For delete operations use an empty string.
   */
  @Pure
  @NonNull
  public String getNewText() {
    return this.newText;
  }
  
  /**
   * The string to be inserted. For delete operations use an empty string.
   */
  public void setNewText(@NonNull final String newText) {
    if (newText == null) {
      throw new IllegalArgumentException("Property must not be null: newText");
    }
    this.newText = newText;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("newText", this.newText);
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
    TextEdit other = (TextEdit) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.newText == null) {
      if (other.newText != null)
        return false;
    } else if (!this.newText.equals(other.newText))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    return prime * result + ((this.newText== null) ? 0 : this.newText.hashCode());
  }
}
