/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import com.google.common.annotations.Beta;
import org.eclipse.lsp4j.WorkspaceFoldersOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities of the server regarding workspace.
 * 
 * This is an LSP <b>proposal</b>.
 */
@Beta
@SuppressWarnings("all")
public class WorkspaceServerCapabilities {
  /**
   * Capabilities specific to the `workspace/didChangeWorkspaceFolders` notification.
   * 
   * This is an LSP <b>proposal</b>.
   */
  @Beta
  private WorkspaceFoldersOptions workspaceFolders;
  
  /**
   * Capabilities specific to the `workspace/didChangeWorkspaceFolders` notification.
   * 
   * This is an LSP <b>proposal</b>.
   */
  @Pure
  public WorkspaceFoldersOptions getWorkspaceFolders() {
    return this.workspaceFolders;
  }
  
  /**
   * Capabilities specific to the `workspace/didChangeWorkspaceFolders` notification.
   * 
   * This is an LSP <b>proposal</b>.
   */
  public void setWorkspaceFolders(final WorkspaceFoldersOptions workspaceFolders) {
    this.workspaceFolders = workspaceFolders;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workspaceFolders", this.workspaceFolders);
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
    WorkspaceServerCapabilities other = (WorkspaceServerCapabilities) obj;
    if (this.workspaceFolders == null) {
      if (other.workspaceFolders != null)
        return false;
    } else if (!this.workspaceFolders.equals(other.workspaceFolders))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.workspaceFolders== null) ? 0 : this.workspaceFolders.hashCode());
    return result;
  }
}
