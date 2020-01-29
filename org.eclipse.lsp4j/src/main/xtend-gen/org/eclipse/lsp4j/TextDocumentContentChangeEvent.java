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
 * An event describing a change to a text document. If range and rangeLength are omitted the new text is considered
 * to be the full content of the document.
 */
@SuppressWarnings("all")
public class TextDocumentContentChangeEvent {
  /**
   * The range of the document that changed.
   */
  private Range range;
  
  /**
   * The length of the range that got replaced.
   * 
   * @deprecated Use range instead.
   */
  @Deprecated
  private Integer rangeLength;
  
  /**
   * The new text of the range/document.
   */
  @NonNull
  private String text;
  
  public TextDocumentContentChangeEvent() {
  }
  
  public TextDocumentContentChangeEvent(@NonNull final String text) {
    this.text = Preconditions.<String>checkNotNull(text, "text");
  }
  
  public TextDocumentContentChangeEvent(final Range range, final Integer rangeLength, @NonNull final String text) {
    this(text);
    this.range = range;
    this.rangeLength = rangeLength;
  }
  
  /**
   * The range of the document that changed.
   */
  @Pure
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range of the document that changed.
   */
  public void setRange(final Range range) {
    this.range = range;
  }
  
  /**
   * The length of the range that got replaced.
   * 
   * @deprecated Use range instead.
   */
  @Pure
  @Deprecated
  public Integer getRangeLength() {
    return this.rangeLength;
  }
  
  /**
   * The length of the range that got replaced.
   * 
   * @deprecated Use range instead.
   */
  @Deprecated
  public void setRangeLength(final Integer rangeLength) {
    this.rangeLength = rangeLength;
  }
  
  /**
   * The new text of the range/document.
   */
  @Pure
  @NonNull
  public String getText() {
    return this.text;
  }
  
  /**
   * The new text of the range/document.
   */
  public void setText(@NonNull final String text) {
    this.text = Preconditions.checkNotNull(text, "text");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("rangeLength", this.rangeLength);
    b.add("text", this.text);
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
    TextDocumentContentChangeEvent other = (TextDocumentContentChangeEvent) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.rangeLength == null) {
      if (other.rangeLength != null)
        return false;
    } else if (!this.rangeLength.equals(other.rangeLength))
      return false;
    if (this.text == null) {
      if (other.text != null)
        return false;
    } else if (!this.text.equals(other.text))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.rangeLength== null) ? 0 : this.rangeLength.hashCode());
    return prime * result + ((this.text== null) ? 0 : this.text.hashCode());
  }
}
