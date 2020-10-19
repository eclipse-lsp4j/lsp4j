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
  
  /**
   * The server provides support to resolve additional
   * information for a code action.
   * 
   * Since 3.16.0
   */
  @Beta
  private Boolean resolveProvider;
  
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
  
  /**
   * The server provides support to resolve additional
   * information for a code action.
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getResolveProvider() {
    return this.resolveProvider;
  }
  
  /**
   * The server provides support to resolve additional
   * information for a code action.
   * 
   * Since 3.16.0
   */
  public void setResolveProvider(final Boolean resolveProvider) {
    this.resolveProvider = resolveProvider;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("codeActionKinds", this.codeActionKinds);
    b.add("resolveProvider", this.resolveProvider);
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
    if (this.resolveProvider == null) {
      if (other.resolveProvider != null)
        return false;
    } else if (!this.resolveProvider.equals(other.resolveProvider))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.codeActionKinds== null) ? 0 : this.codeActionKinds.hashCode());
    return prime * result + ((this.resolveProvider== null) ? 0 : this.resolveProvider.hashCode());
  }
}
