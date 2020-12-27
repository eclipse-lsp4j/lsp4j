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

import org.eclipse.lsp4j.DynamicRegistrationCapabilities;
import org.eclipse.lsp4j.SymbolKindCapabilities;
import org.eclipse.lsp4j.SymbolTagSupportCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to the `textDocument/documentSymbol`
 */
@SuppressWarnings("all")
public class DocumentSymbolCapabilities extends DynamicRegistrationCapabilities {
  /**
   * Specific capabilities for the `SymbolKind`.
   */
  private SymbolKindCapabilities symbolKind;
  
  /**
   * The client support hierarchical document symbols.
   */
  private Boolean hierarchicalDocumentSymbolSupport;
  
  /**
   * The client supports tags on `SymbolInformation`. Tags are supported on
   * `DocumentSymbol` if `hierarchicalDocumentSymbolSupport` is set to true.
   * Clients supporting tags have to handle unknown tags gracefully.
   * 
   * Since 3.16.0
   */
  private SymbolTagSupportCapabilities tagSupport;
  
  /**
   * The client supports an additional label presented in the UI when
   * registering a document symbol provider.
   * 
   * Since 3.16.0
   */
  private Boolean labelSupport;
  
  public DocumentSymbolCapabilities() {
  }
  
  public DocumentSymbolCapabilities(final Boolean dynamicRegistration) {
    super(dynamicRegistration);
  }
  
  public DocumentSymbolCapabilities(final SymbolKindCapabilities symbolKind) {
    this.symbolKind = symbolKind;
  }
  
  public DocumentSymbolCapabilities(final SymbolKindCapabilities symbolKind, final Boolean dynamicRegistration) {
    super(dynamicRegistration);
    this.symbolKind = symbolKind;
  }
  
  public DocumentSymbolCapabilities(final SymbolKindCapabilities symbolKind, final Boolean dynamicRegistration, final Boolean hierarchicalDocumentSymbolSupport) {
    super(dynamicRegistration);
    this.symbolKind = symbolKind;
    this.hierarchicalDocumentSymbolSupport = hierarchicalDocumentSymbolSupport;
  }
  
  /**
   * Specific capabilities for the `SymbolKind`.
   */
  @Pure
  public SymbolKindCapabilities getSymbolKind() {
    return this.symbolKind;
  }
  
  /**
   * Specific capabilities for the `SymbolKind`.
   */
  public void setSymbolKind(final SymbolKindCapabilities symbolKind) {
    this.symbolKind = symbolKind;
  }
  
  /**
   * The client support hierarchical document symbols.
   */
  @Pure
  public Boolean getHierarchicalDocumentSymbolSupport() {
    return this.hierarchicalDocumentSymbolSupport;
  }
  
  /**
   * The client support hierarchical document symbols.
   */
  public void setHierarchicalDocumentSymbolSupport(final Boolean hierarchicalDocumentSymbolSupport) {
    this.hierarchicalDocumentSymbolSupport = hierarchicalDocumentSymbolSupport;
  }
  
  /**
   * The client supports tags on `SymbolInformation`. Tags are supported on
   * `DocumentSymbol` if `hierarchicalDocumentSymbolSupport` is set to true.
   * Clients supporting tags have to handle unknown tags gracefully.
   * 
   * Since 3.16.0
   */
  @Pure
  public SymbolTagSupportCapabilities getTagSupport() {
    return this.tagSupport;
  }
  
  /**
   * The client supports tags on `SymbolInformation`. Tags are supported on
   * `DocumentSymbol` if `hierarchicalDocumentSymbolSupport` is set to true.
   * Clients supporting tags have to handle unknown tags gracefully.
   * 
   * Since 3.16.0
   */
  public void setTagSupport(final SymbolTagSupportCapabilities tagSupport) {
    this.tagSupport = tagSupport;
  }
  
  /**
   * The client supports an additional label presented in the UI when
   * registering a document symbol provider.
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getLabelSupport() {
    return this.labelSupport;
  }
  
  /**
   * The client supports an additional label presented in the UI when
   * registering a document symbol provider.
   * 
   * Since 3.16.0
   */
  public void setLabelSupport(final Boolean labelSupport) {
    this.labelSupport = labelSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("symbolKind", this.symbolKind);
    b.add("hierarchicalDocumentSymbolSupport", this.hierarchicalDocumentSymbolSupport);
    b.add("tagSupport", this.tagSupport);
    b.add("labelSupport", this.labelSupport);
    b.add("dynamicRegistration", getDynamicRegistration());
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
    DocumentSymbolCapabilities other = (DocumentSymbolCapabilities) obj;
    if (this.symbolKind == null) {
      if (other.symbolKind != null)
        return false;
    } else if (!this.symbolKind.equals(other.symbolKind))
      return false;
    if (this.hierarchicalDocumentSymbolSupport == null) {
      if (other.hierarchicalDocumentSymbolSupport != null)
        return false;
    } else if (!this.hierarchicalDocumentSymbolSupport.equals(other.hierarchicalDocumentSymbolSupport))
      return false;
    if (this.tagSupport == null) {
      if (other.tagSupport != null)
        return false;
    } else if (!this.tagSupport.equals(other.tagSupport))
      return false;
    if (this.labelSupport == null) {
      if (other.labelSupport != null)
        return false;
    } else if (!this.labelSupport.equals(other.labelSupport))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.symbolKind== null) ? 0 : this.symbolKind.hashCode());
    result = prime * result + ((this.hierarchicalDocumentSymbolSupport== null) ? 0 : this.hierarchicalDocumentSymbolSupport.hashCode());
    result = prime * result + ((this.tagSupport== null) ? 0 : this.tagSupport.hashCode());
    return prime * result + ((this.labelSupport== null) ? 0 : this.labelSupport.hashCode());
  }
}
