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
 * Matching options for the file operation pattern.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class FileOperationPatternOptions {
  /**
   * The pattern should be matched ignoring casing.
   */
  private Boolean ignoreCase;
  
  public FileOperationPatternOptions() {
  }
  
  public FileOperationPatternOptions(final Boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
  }
  
  /**
   * The pattern should be matched ignoring casing.
   */
  @Pure
  public Boolean getIgnoreCase() {
    return this.ignoreCase;
  }
  
  /**
   * The pattern should be matched ignoring casing.
   */
  public void setIgnoreCase(final Boolean ignoreCase) {
    this.ignoreCase = ignoreCase;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("ignoreCase", this.ignoreCase);
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
    FileOperationPatternOptions other = (FileOperationPatternOptions) obj;
    if (this.ignoreCase == null) {
      if (other.ignoreCase != null)
        return false;
    } else if (!this.ignoreCase.equals(other.ignoreCase))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.ignoreCase== null) ? 0 : this.ignoreCase.hashCode());
  }
}
