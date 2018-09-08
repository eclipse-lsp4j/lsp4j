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
 * Capabilities specific to `textDocument/publishDiagnostics`.
 */
@SuppressWarnings("all")
public class PublishDiagnosticsCapabilities {
  /**
   * Whether the client accepts diagnostics with related information.
   */
  private Boolean relatedInformation;
  
  public PublishDiagnosticsCapabilities() {
  }
  
  public PublishDiagnosticsCapabilities(final Boolean relatedInformation) {
    this.relatedInformation = relatedInformation;
  }
  
  /**
   * Whether the client accepts diagnostics with related information.
   */
  @Pure
  public Boolean getRelatedInformation() {
    return this.relatedInformation;
  }
  
  /**
   * Whether the client accepts diagnostics with related information.
   */
  public void setRelatedInformation(final Boolean relatedInformation) {
    this.relatedInformation = relatedInformation;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("relatedInformation", this.relatedInformation);
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
    PublishDiagnosticsCapabilities other = (PublishDiagnosticsCapabilities) obj;
    if (this.relatedInformation == null) {
      if (other.relatedInformation != null)
        return false;
    } else if (!this.relatedInformation.equals(other.relatedInformation))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.relatedInformation== null) ? 0 : this.relatedInformation.hashCode());
  }
}
