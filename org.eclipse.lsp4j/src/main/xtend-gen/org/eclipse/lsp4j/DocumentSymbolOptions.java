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

import org.eclipse.lsp4j.AbstractWorkDoneProgressOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class DocumentSymbolOptions extends AbstractWorkDoneProgressOptions {
  /**
   * A human-readable string that is shown when multiple outlines trees
   * are shown for the same document.
   * 
   * Since 3.16.0
   */
  private String label;
  
  public DocumentSymbolOptions() {
  }
  
  public DocumentSymbolOptions(final String label) {
    this.label = label;
  }
  
  /**
   * A human-readable string that is shown when multiple outlines trees
   * are shown for the same document.
   * 
   * Since 3.16.0
   */
  @Pure
  public String getLabel() {
    return this.label;
  }
  
  /**
   * A human-readable string that is shown when multiple outlines trees
   * are shown for the same document.
   * 
   * Since 3.16.0
   */
  public void setLabel(final String label) {
    this.label = label;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("label", this.label);
    b.add("workDoneProgress", getWorkDoneProgress());
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
    DocumentSymbolOptions other = (DocumentSymbolOptions) obj;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.label== null) ? 0 : this.label.hashCode());
  }
}
