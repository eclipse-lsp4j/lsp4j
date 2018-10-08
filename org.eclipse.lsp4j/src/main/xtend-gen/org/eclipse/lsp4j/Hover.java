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

import com.google.gson.annotations.JsonAdapter;
import java.util.List;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.adapters.HoverTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The result of a hover request.
 */
@JsonAdapter(HoverTypeAdapter.Factory.class)
@SuppressWarnings("all")
public class Hover {
  /**
   * The hover's content as markdown
   */
  @NonNull
  private Either<List<Either<String, MarkedString>>, MarkupContent> contents;
  
  /**
   * An optional range
   */
  private Range range;
  
  public Hover() {
  }
  
  public Hover(@NonNull final List<Either<String, MarkedString>> contents) {
    this.setContents(contents);
  }
  
  public Hover(@NonNull final List<Either<String, MarkedString>> contents, final Range range) {
    this.setContents(contents);
    this.range = range;
  }
  
  public Hover(@NonNull final MarkupContent contents) {
    this.setContents(contents);
  }
  
  public Hover(@NonNull final MarkupContent contents, final Range range) {
    this.setContents(contents);
    this.range = range;
  }
  
  /**
   * The hover's content as markdown
   */
  @Pure
  @NonNull
  public Either<List<Either<String, MarkedString>>, MarkupContent> getContents() {
    return this.contents;
  }
  
  /**
   * The hover's content as markdown
   */
  public void setContents(@NonNull final Either<List<Either<String, MarkedString>>, MarkupContent> contents) {
    this.contents = contents;
  }
  
  public void setContents(final List<Either<String, MarkedString>> contents) {
    if (contents == null) {
      this.contents = null;
      return;
    }
    this.contents = Either.forLeft(contents);
  }
  
  public void setContents(final MarkupContent contents) {
    if (contents == null) {
      this.contents = null;
      return;
    }
    this.contents = Either.forRight(contents);
  }
  
  /**
   * An optional range
   */
  @Pure
  public Range getRange() {
    return this.range;
  }
  
  /**
   * An optional range
   */
  public void setRange(final Range range) {
    this.range = range;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("contents", this.contents);
    b.add("range", this.range);
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
    Hover other = (Hover) obj;
    if (this.contents == null) {
      if (other.contents != null)
        return false;
    } else if (!this.contents.equals(other.contents))
      return false;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.contents== null) ? 0 : this.contents.hashCode());
    return prime * result + ((this.range== null) ? 0 : this.range.hashCode());
  }
}
