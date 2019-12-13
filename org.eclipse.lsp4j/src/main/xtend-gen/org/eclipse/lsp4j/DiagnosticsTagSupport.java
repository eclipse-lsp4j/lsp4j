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
import org.eclipse.lsp4j.DiagnosticTag;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class DiagnosticsTagSupport {
  /**
   * The tags supported by the client.
   */
  private List<DiagnosticTag> valueSet;
  
  public DiagnosticsTagSupport() {
  }
  
  public DiagnosticsTagSupport(final List<DiagnosticTag> valueSet) {
    this.valueSet = valueSet;
  }
  
  /**
   * The tags supported by the client.
   */
  @Pure
  public List<DiagnosticTag> getValueSet() {
    return this.valueSet;
  }
  
  /**
   * The tags supported by the client.
   */
  public void setValueSet(final List<DiagnosticTag> valueSet) {
    this.valueSet = valueSet;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("valueSet", this.valueSet);
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
    DiagnosticsTagSupport other = (DiagnosticsTagSupport) obj;
    if (this.valueSet == null) {
      if (other.valueSet != null)
        return false;
    } else if (!this.valueSet.equals(other.valueSet))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.valueSet== null) ? 0 : this.valueSet.hashCode());
  }
}
