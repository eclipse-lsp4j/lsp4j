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

import org.eclipse.lsp4j.DiagnosticsTagSupport;
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
  
  /**
   * Client supports the tag property to provide meta data about a diagnostic.
   * 
   * Since 3.15
   */
  private DiagnosticsTagSupport tagSupport;
  
  public PublishDiagnosticsCapabilities() {
  }
  
  public PublishDiagnosticsCapabilities(final Boolean relatedInformation) {
    this.relatedInformation = relatedInformation;
  }
  
  public PublishDiagnosticsCapabilities(final Boolean relatedInformation, final DiagnosticsTagSupport tagSupport) {
    this.relatedInformation = relatedInformation;
    this.tagSupport = tagSupport;
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
  
  /**
   * Client supports the tag property to provide meta data about a diagnostic.
   * 
   * Since 3.15
   */
  @Pure
  public DiagnosticsTagSupport getTagSupport() {
    return this.tagSupport;
  }
  
  /**
   * Client supports the tag property to provide meta data about a diagnostic.
   * 
   * Since 3.15
   */
  public void setTagSupport(final DiagnosticsTagSupport tagSupport) {
    this.tagSupport = tagSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("relatedInformation", this.relatedInformation);
    b.add("tagSupport", this.tagSupport);
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
    if (this.tagSupport == null) {
      if (other.tagSupport != null)
        return false;
    } else if (!this.tagSupport.equals(other.tagSupport))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.relatedInformation== null) ? 0 : this.relatedInformation.hashCode());
    return prime * result + ((this.tagSupport== null) ? 0 : this.tagSupport.hashCode());
  }
}
