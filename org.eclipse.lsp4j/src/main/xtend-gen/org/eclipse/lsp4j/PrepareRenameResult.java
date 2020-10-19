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
 * One of the result types of the `textDocument/prepareRename` request.
 * Provides the range of the string to rename and a placeholder text of the string content to be renamed.
 */
@SuppressWarnings("all")
public class PrepareRenameResult {
  /**
   * The range of the string to rename
   */
  @NonNull
  private Range range;
  
  /**
   * A placeholder text of the string content to be renamed.
   */
  @NonNull
  private String placeholder;
  
  public PrepareRenameResult() {
  }
  
  public PrepareRenameResult(@NonNull final Range range, @NonNull final String placeholder) {
    this.range = Preconditions.<Range>checkNotNull(range, "range");
    this.placeholder = Preconditions.<String>checkNotNull(placeholder, "placeholder");
  }
  
  /**
   * The range of the string to rename
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range of the string to rename
   */
  public void setRange(@NonNull final Range range) {
    this.range = Preconditions.checkNotNull(range, "range");
  }
  
  /**
   * A placeholder text of the string content to be renamed.
   */
  @Pure
  @NonNull
  public String getPlaceholder() {
    return this.placeholder;
  }
  
  /**
   * A placeholder text of the string content to be renamed.
   */
  public void setPlaceholder(@NonNull final String placeholder) {
    this.placeholder = Preconditions.checkNotNull(placeholder, "placeholder");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("placeholder", this.placeholder);
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
    PrepareRenameResult other = (PrepareRenameResult) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.placeholder == null) {
      if (other.placeholder != null)
        return false;
    } else if (!this.placeholder.equals(other.placeholder))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    return prime * result + ((this.placeholder== null) ? 0 : this.placeholder.hashCode());
  }
}
