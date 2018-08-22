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
 * The references request is sent from the client to the server to resolve project-wide references for the symbol
 * denoted by the given text document position.
 */
@SuppressWarnings("all")
public class ReferenceContext {
  /**
   * Include the declaration of the current symbol.
   */
  private boolean includeDeclaration;
  
  public ReferenceContext() {
  }
  
  public ReferenceContext(final boolean includeDeclaration) {
    this.includeDeclaration = includeDeclaration;
  }
  
  /**
   * Include the declaration of the current symbol.
   */
  @Pure
  public boolean isIncludeDeclaration() {
    return this.includeDeclaration;
  }
  
  /**
   * Include the declaration of the current symbol.
   */
  public void setIncludeDeclaration(final boolean includeDeclaration) {
    this.includeDeclaration = includeDeclaration;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("includeDeclaration", this.includeDeclaration);
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
    ReferenceContext other = (ReferenceContext) obj;
    if (other.includeDeclaration != this.includeDeclaration)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + (this.includeDeclaration ? 1231 : 1237);
  }
}
