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
 * Save options.
 */
@SuppressWarnings("all")
public class SaveOptions {
  /**
   * The client is supposed to include the content on save.
   */
  private Boolean includeText;
  
  public SaveOptions() {
  }
  
  public SaveOptions(final Boolean includeText) {
    this.includeText = includeText;
  }
  
  /**
   * The client is supposed to include the content on save.
   */
  @Pure
  public Boolean getIncludeText() {
    return this.includeText;
  }
  
  /**
   * The client is supposed to include the content on save.
   */
  public void setIncludeText(final Boolean includeText) {
    this.includeText = includeText;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("includeText", this.includeText);
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
    SaveOptions other = (SaveOptions) obj;
    if (this.includeText == null) {
      if (other.includeText != null)
        return false;
    } else if (!this.includeText.equals(other.includeText))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.includeText== null) ? 0 : this.includeText.hashCode());
  }
}
