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
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A special text edit to provide an insert and a replace operation.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class InsertReplaceEdit {
  /**
   * The string to be inserted.
   */
  @NonNull
  private String newText;
  
  /**
   * The range if the insert that is requested
   */
  @NonNull
  private Range insert;
  
  /**
   * The range if the replace that is requested.
   */
  @NonNull
  private Range replace;
  
  public InsertReplaceEdit() {
  }
  
  public InsertReplaceEdit(@NonNull final String newText, @NonNull final Range insert, @NonNull final Range replace) {
    this.newText = Preconditions.<String>checkNotNull(newText, "newText");
    this.insert = Preconditions.<Range>checkNotNull(insert, "insert");
    this.replace = Preconditions.<Range>checkNotNull(replace, "replace");
  }
  
  /**
   * The string to be inserted.
   */
  @Pure
  @NonNull
  public String getNewText() {
    return this.newText;
  }
  
  /**
   * The string to be inserted.
   */
  public void setNewText(@NonNull final String newText) {
    this.newText = Preconditions.checkNotNull(newText, "newText");
  }
  
  /**
   * The range if the insert that is requested
   */
  @Pure
  @NonNull
  public Range getInsert() {
    return this.insert;
  }
  
  /**
   * The range if the insert that is requested
   */
  public void setInsert(@NonNull final Range insert) {
    this.insert = Preconditions.checkNotNull(insert, "insert");
  }
  
  /**
   * The range if the replace that is requested.
   */
  @Pure
  @NonNull
  public Range getReplace() {
    return this.replace;
  }
  
  /**
   * The range if the replace that is requested.
   */
  public void setReplace(@NonNull final Range replace) {
    this.replace = Preconditions.checkNotNull(replace, "replace");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("newText", this.newText);
    b.add("insert", this.insert);
    b.add("replace", this.replace);
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
    InsertReplaceEdit other = (InsertReplaceEdit) obj;
    if (this.newText == null) {
      if (other.newText != null)
        return false;
    } else if (!this.newText.equals(other.newText))
      return false;
    if (this.insert == null) {
      if (other.insert != null)
        return false;
    } else if (!this.insert.equals(other.insert))
      return false;
    if (this.replace == null) {
      if (other.replace != null)
        return false;
    } else if (!this.replace.equals(other.replace))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.newText== null) ? 0 : this.newText.hashCode());
    result = prime * result + ((this.insert== null) ? 0 : this.insert.hashCode());
    return prime * result + ((this.replace== null) ? 0 : this.replace.hashCode());
  }
}
