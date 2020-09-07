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
import org.eclipse.lsp4j.AbstractWorkDoneProgressOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Code Action options.
 */
@SuppressWarnings("all")
public class CodeActionOptions extends AbstractWorkDoneProgressOptions {
  /**
   * CodeActionKinds that this server may return.
   * 
   * The list of kinds may be generic, such as `CodeActionKind.Refactor`, or the server
   * may list out every specific kind they provide.
   */
  private List<String> codeActionKinds;
  
  public CodeActionOptions() {
  }
  
  public CodeActionOptions(final List<String> codeActionKinds) {
    this.codeActionKinds = codeActionKinds;
  }
  
  /**
   * CodeActionKinds that this server may return.
   * 
   * The list of kinds may be generic, such as `CodeActionKind.Refactor`, or the server
   * may list out every specific kind they provide.
   */
  @Pure
  public List<String> getCodeActionKinds() {
    return this.codeActionKinds;
  }
  
  /**
   * CodeActionKinds that this server may return.
   * 
   * The list of kinds may be generic, such as `CodeActionKind.Refactor`, or the server
   * may list out every specific kind they provide.
   */
  public void setCodeActionKinds(final List<String> codeActionKinds) {
    this.codeActionKinds = codeActionKinds;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("codeActionKinds", this.codeActionKinds);
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
    CodeActionOptions other = (CodeActionOptions) obj;
    if (this.codeActionKinds == null) {
      if (other.codeActionKinds != null)
        return false;
    } else if (!this.codeActionKinds.equals(other.codeActionKinds))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.codeActionKinds== null) ? 0 : this.codeActionKinds.hashCode());
  }
}
