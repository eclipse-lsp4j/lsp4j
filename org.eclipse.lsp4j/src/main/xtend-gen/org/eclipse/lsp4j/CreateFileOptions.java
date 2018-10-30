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
 * Options to create a file.
 */
@SuppressWarnings("all")
public class CreateFileOptions {
  /**
   * Overwrite existing file. Overwrite wins over `ignoreIfExists`
   */
  private Boolean overwrite;
  
  /**
   * Ignore if exists.
   */
  private Boolean ignoreIfExists;
  
  public CreateFileOptions() {
  }
  
  public CreateFileOptions(final Boolean overwrite, final Boolean ignoreIfExists) {
    this.overwrite = overwrite;
    this.ignoreIfExists = ignoreIfExists;
  }
  
  /**
   * Overwrite existing file. Overwrite wins over `ignoreIfExists`
   */
  @Pure
  public Boolean getOverwrite() {
    return this.overwrite;
  }
  
  /**
   * Overwrite existing file. Overwrite wins over `ignoreIfExists`
   */
  public void setOverwrite(final Boolean overwrite) {
    this.overwrite = overwrite;
  }
  
  /**
   * Ignore if exists.
   */
  @Pure
  public Boolean getIgnoreIfExists() {
    return this.ignoreIfExists;
  }
  
  /**
   * Ignore if exists.
   */
  public void setIgnoreIfExists(final Boolean ignoreIfExists) {
    this.ignoreIfExists = ignoreIfExists;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("overwrite", this.overwrite);
    b.add("ignoreIfExists", this.ignoreIfExists);
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
    CreateFileOptions other = (CreateFileOptions) obj;
    if (this.overwrite == null) {
      if (other.overwrite != null)
        return false;
    } else if (!this.overwrite.equals(other.overwrite))
      return false;
    if (this.ignoreIfExists == null) {
      if (other.ignoreIfExists != null)
        return false;
    } else if (!this.ignoreIfExists.equals(other.ignoreIfExists))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.overwrite== null) ? 0 : this.overwrite.hashCode());
    return prime * result + ((this.ignoreIfExists== null) ? 0 : this.ignoreIfExists.hashCode());
  }
}
