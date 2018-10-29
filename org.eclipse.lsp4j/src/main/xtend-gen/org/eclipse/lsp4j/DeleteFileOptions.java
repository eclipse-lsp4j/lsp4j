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

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Delete file options
 */
@SuppressWarnings("all")
public class DeleteFileOptions {
  /**
   * Delete the content recursively if a folder is denoted.
   */
  private Boolean recursive;
  
  /**
   * Ignore the operation if the file doesn't exist.
   */
  private Boolean ignoreIfNotExists;
  
  public DeleteFileOptions() {
  }
  
  public DeleteFileOptions(final Boolean recursive, final Boolean ignoreIfNotExists) {
    this.recursive = recursive;
    this.ignoreIfNotExists = ignoreIfNotExists;
  }
  
  /**
   * Delete the content recursively if a folder is denoted.
   */
  @Pure
  public Boolean getRecursive() {
    return this.recursive;
  }
  
  /**
   * Delete the content recursively if a folder is denoted.
   */
  public void setRecursive(final Boolean recursive) {
    this.recursive = recursive;
  }
  
  /**
   * Ignore the operation if the file doesn't exist.
   */
  @Pure
  public Boolean getIgnoreIfNotExists() {
    return this.ignoreIfNotExists;
  }
  
  /**
   * Ignore the operation if the file doesn't exist.
   */
  public void setIgnoreIfNotExists(final Boolean ignoreIfNotExists) {
    this.ignoreIfNotExists = ignoreIfNotExists;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("recursive", this.recursive);
    b.add("ignoreIfNotExists", this.ignoreIfNotExists);
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
    DeleteFileOptions other = (DeleteFileOptions) obj;
    if (this.recursive == null) {
      if (other.recursive != null)
        return false;
    } else if (!this.recursive.equals(other.recursive))
      return false;
    if (this.ignoreIfNotExists == null) {
      if (other.ignoreIfNotExists != null)
        return false;
    } else if (!this.ignoreIfNotExists.equals(other.ignoreIfNotExists))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.recursive== null) ? 0 : this.recursive.hashCode());
    return prime * result + ((this.ignoreIfNotExists== null) ? 0 : this.ignoreIfNotExists.hashCode());
  }
}
