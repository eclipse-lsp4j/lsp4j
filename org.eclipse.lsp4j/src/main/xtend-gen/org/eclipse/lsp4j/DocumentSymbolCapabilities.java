/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.DynamicRegistrationCapabilities;
import org.eclipse.lsp4j.SymbolKindCapabilities;
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
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("symbolKind", this.symbolKind);
    b.add("hierarchicalDocumentSymbolSupport", this.hierarchicalDocumentSymbolSupport);
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.symbolKind== null) ? 0 : this.symbolKind.hashCode());
    return prime * result + ((this.hierarchicalDocumentSymbolSupport== null) ? 0 : this.hierarchicalDocumentSymbolSupport.hashCode());
  }
}
