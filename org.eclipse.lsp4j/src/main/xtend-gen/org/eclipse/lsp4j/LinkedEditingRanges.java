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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The linked editing range response is sent from the server to the client to return the range of the symbol
 * at the given position and all ranges that have the same content.
 * 
 * Optionally a word pattern can be returned to describe valid contents.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class LinkedEditingRanges {
  /**
   * A list of ranges that can be renamed together. The ranges must have
   * identical length and contain identical text content. The ranges cannot overlap.
   */
  @NonNull
  private List<Range> ranges;
  
  /**
   * An optional word pattern (regular expression) that describes valid contents for
   * the given ranges. If no pattern is provided, the client configuration's word
   * pattern will be used.
   */
  private String wordPattern;
  
  public LinkedEditingRanges() {
    ArrayList<Range> _arrayList = new ArrayList<Range>();
    this.ranges = _arrayList;
  }
  
  public LinkedEditingRanges(@NonNull final List<Range> ranges) {
    this.ranges = Preconditions.<List<Range>>checkNotNull(ranges, "ranges");
  }
  
  public LinkedEditingRanges(@NonNull final List<Range> ranges, final String wordPattern) {
    this(ranges);
    this.wordPattern = wordPattern;
  }
  
  /**
   * A list of ranges that can be renamed together. The ranges must have
   * identical length and contain identical text content. The ranges cannot overlap.
   */
  @Pure
  @NonNull
  public List<Range> getRanges() {
    return this.ranges;
  }
  
  /**
   * A list of ranges that can be renamed together. The ranges must have
   * identical length and contain identical text content. The ranges cannot overlap.
   */
  public void setRanges(@NonNull final List<Range> ranges) {
    this.ranges = Preconditions.checkNotNull(ranges, "ranges");
  }
  
  /**
   * An optional word pattern (regular expression) that describes valid contents for
   * the given ranges. If no pattern is provided, the client configuration's word
   * pattern will be used.
   */
  @Pure
  public String getWordPattern() {
    return this.wordPattern;
  }
  
  /**
   * An optional word pattern (regular expression) that describes valid contents for
   * the given ranges. If no pattern is provided, the client configuration's word
   * pattern will be used.
   */
  public void setWordPattern(final String wordPattern) {
    this.wordPattern = wordPattern;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("ranges", this.ranges);
    b.add("wordPattern", this.wordPattern);
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
    LinkedEditingRanges other = (LinkedEditingRanges) obj;
    if (this.ranges == null) {
      if (other.ranges != null)
        return false;
    } else if (!this.ranges.equals(other.ranges))
      return false;
    if (this.wordPattern == null) {
      if (other.wordPattern != null)
        return false;
    } else if (!this.wordPattern.equals(other.wordPattern))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.ranges== null) ? 0 : this.ranges.hashCode());
    return prime * result + ((this.wordPattern== null) ? 0 : this.wordPattern.hashCode());
  }
}
