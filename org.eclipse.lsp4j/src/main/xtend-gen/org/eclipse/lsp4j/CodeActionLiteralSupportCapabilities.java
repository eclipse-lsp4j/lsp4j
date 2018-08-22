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

import org.eclipse.lsp4j.CodeActionKindCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class CodeActionLiteralSupportCapabilities {
  /**
   * The code action kind is support with the following value
   * set.
   */
  private CodeActionKindCapabilities codeActionKind;
  
  public CodeActionLiteralSupportCapabilities() {
  }
  
  public CodeActionLiteralSupportCapabilities(final CodeActionKindCapabilities codeActionKind) {
    this.codeActionKind = codeActionKind;
  }
  
  /**
   * The code action kind is support with the following value
   * set.
   */
  @Pure
  public CodeActionKindCapabilities getCodeActionKind() {
    return this.codeActionKind;
  }
  
  /**
   * The code action kind is support with the following value
   * set.
   */
  public void setCodeActionKind(final CodeActionKindCapabilities codeActionKind) {
    this.codeActionKind = codeActionKind;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("codeActionKind", this.codeActionKind);
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
    CodeActionLiteralSupportCapabilities other = (CodeActionLiteralSupportCapabilities) obj;
    if (this.codeActionKind == null) {
      if (other.codeActionKind != null)
        return false;
    } else if (!this.codeActionKind.equals(other.codeActionKind))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.codeActionKind== null) ? 0 : this.codeActionKind.hashCode());
  }
}
