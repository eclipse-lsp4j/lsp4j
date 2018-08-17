/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.DynamicRegistrationCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to `textDocument/foldingRange` requests.
 * 
 * Since 3.10.0
 */
@SuppressWarnings("all")
public class FoldingRangeCapabilities extends DynamicRegistrationCapabilities {
  /**
   * The maximum number of folding ranges that the client prefers to receive per document. The value serves as a
   * hint, servers are free to follow the limit.
   */
  private Integer rangeLimit;
  
  /**
   * If set, the client signals that it only supports folding complete lines. If set, client will
   * ignore specified `startCharacter` and `endCharacter` properties in a FoldingRange.
   */
  private Boolean lineFoldingOnly;
  
  /**
   * The maximum number of folding ranges that the client prefers to receive per document. The value serves as a
   * hint, servers are free to follow the limit.
   */
  @Pure
  public Integer getRangeLimit() {
    return this.rangeLimit;
  }
  
  /**
   * The maximum number of folding ranges that the client prefers to receive per document. The value serves as a
   * hint, servers are free to follow the limit.
   */
  public void setRangeLimit(final Integer rangeLimit) {
    this.rangeLimit = rangeLimit;
  }
  
  /**
   * If set, the client signals that it only supports folding complete lines. If set, client will
   * ignore specified `startCharacter` and `endCharacter` properties in a FoldingRange.
   */
  @Pure
  public Boolean getLineFoldingOnly() {
    return this.lineFoldingOnly;
  }
  
  /**
   * If set, the client signals that it only supports folding complete lines. If set, client will
   * ignore specified `startCharacter` and `endCharacter` properties in a FoldingRange.
   */
  public void setLineFoldingOnly(final Boolean lineFoldingOnly) {
    this.lineFoldingOnly = lineFoldingOnly;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("rangeLimit", this.rangeLimit);
    b.add("lineFoldingOnly", this.lineFoldingOnly);
    b.add("dynamicRegistration", getDynamicRegistration());
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
    if (!super.equals(obj))
      return false;
    FoldingRangeCapabilities other = (FoldingRangeCapabilities) obj;
    if (this.rangeLimit == null) {
      if (other.rangeLimit != null)
        return false;
    } else if (!this.rangeLimit.equals(other.rangeLimit))
      return false;
    if (this.lineFoldingOnly == null) {
      if (other.lineFoldingOnly != null)
        return false;
    } else if (!this.lineFoldingOnly.equals(other.lineFoldingOnly))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.rangeLimit== null) ? 0 : this.rangeLimit.hashCode());
    return prime * result + ((this.lineFoldingOnly== null) ? 0 : this.lineFoldingOnly.hashCode());
  }
}
