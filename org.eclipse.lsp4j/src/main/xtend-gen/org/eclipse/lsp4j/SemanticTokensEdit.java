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

import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class SemanticTokensEdit {
  /**
   * The start offset of the edit.
   */
  private int start;
  
  /**
   * The count of elements to remove.
   */
  private int deleteCount;
  
  /**
   * The elements to insert.
   */
  private List<Integer> data;
  
  public SemanticTokensEdit(final int start, final int deleteCount, final List<Integer> data) {
    this.start = start;
    this.deleteCount = deleteCount;
    this.data = data;
  }
  
  /**
   * The start offset of the edit.
   */
  @Pure
  public int getStart() {
    return this.start;
  }
  
  /**
   * The start offset of the edit.
   */
  public void setStart(final int start) {
    this.start = start;
  }
  
  /**
   * The count of elements to remove.
   */
  @Pure
  public int getDeleteCount() {
    return this.deleteCount;
  }
  
  /**
   * The count of elements to remove.
   */
  public void setDeleteCount(final int deleteCount) {
    this.deleteCount = deleteCount;
  }
  
  /**
   * The elements to insert.
   */
  @Pure
  public List<Integer> getData() {
    return this.data;
  }
  
  /**
   * The elements to insert.
   */
  public void setData(final List<Integer> data) {
    this.data = data;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("start", this.start);
    b.add("deleteCount", this.deleteCount);
    b.add("data", this.data);
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
    SemanticTokensEdit other = (SemanticTokensEdit) obj;
    if (other.start != this.start)
      return false;
    if (other.deleteCount != this.deleteCount)
      return false;
    if (this.data == null) {
      if (other.data != null)
        return false;
    } else if (!this.data.equals(other.data))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.start;
    result = prime * result + this.deleteCount;
    return prime * result + ((this.data== null) ? 0 : this.data.hashCode());
  }
}
