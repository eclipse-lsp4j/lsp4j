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

import com.google.common.annotations.Beta;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to the {@code textDocument/typeHierarchy} and the {@code typeHierarchy/resolve}
 * language server methods.
 * 
 * <p>
 * <b>Note:</b> the <a href=
 * "https://github.com/Microsoft/vscode-languageserver-node/pull/426">{@code textDocument/typeHierarchy}
 * language feature</a> is not yet part of the official LSP specification.
 */
@Beta
@SuppressWarnings("all")
public class TypeHierarchyCapabilities {
  /**
   * The language client supports super- and subtype hierarchies.
   */
  private Boolean typeHierarchy;
  
  public TypeHierarchyCapabilities(final Boolean typeHierarchy) {
    this.typeHierarchy = typeHierarchy;
  }
  
  /**
   * The language client supports super- and subtype hierarchies.
   */
  @Pure
  public Boolean getTypeHierarchy() {
    return this.typeHierarchy;
  }
  
  /**
   * The language client supports super- and subtype hierarchies.
   */
  public void setTypeHierarchy(final Boolean typeHierarchy) {
    this.typeHierarchy = typeHierarchy;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("typeHierarchy", this.typeHierarchy);
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
    TypeHierarchyCapabilities other = (TypeHierarchyCapabilities) obj;
    if (this.typeHierarchy == null) {
      if (other.typeHierarchy != null)
        return false;
    } else if (!this.typeHierarchy.equals(other.typeHierarchy))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.typeHierarchy== null) ? 0 : this.typeHierarchy.hashCode());
  }
}
