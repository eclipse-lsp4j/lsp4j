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

import org.eclipse.lsp4j.FileOperationsServerCapabilities;
import org.eclipse.lsp4j.WorkspaceFoldersOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Workspace specific server capabilities
 */
@SuppressWarnings("all")
public class WorkspaceServerCapabilities {
  /**
   * The server supports workspace folder.
   * 
   * Since 3.6.0
   */
  private WorkspaceFoldersOptions workspaceFolders;
  
  /**
   * The server is interested in file notifications/requests.
   * 
   * Since 3.16.0
   */
  private FileOperationsServerCapabilities fileOperations;
  
  public WorkspaceServerCapabilities() {
  }
  
  public WorkspaceServerCapabilities(final WorkspaceFoldersOptions workspaceFolders) {
    this.workspaceFolders = workspaceFolders;
  }
  
  /**
   * The server supports workspace folder.
   * 
   * Since 3.6.0
   */
  @Pure
  public WorkspaceFoldersOptions getWorkspaceFolders() {
    return this.workspaceFolders;
  }
  
  /**
   * The server supports workspace folder.
   * 
   * Since 3.6.0
   */
  public void setWorkspaceFolders(final WorkspaceFoldersOptions workspaceFolders) {
    this.workspaceFolders = workspaceFolders;
  }
  
  /**
   * The server is interested in file notifications/requests.
   * 
   * Since 3.16.0
   */
  @Pure
  public FileOperationsServerCapabilities getFileOperations() {
    return this.fileOperations;
  }
  
  /**
   * The server is interested in file notifications/requests.
   * 
   * Since 3.16.0
   */
  public void setFileOperations(final FileOperationsServerCapabilities fileOperations) {
    this.fileOperations = fileOperations;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workspaceFolders", this.workspaceFolders);
    b.add("fileOperations", this.fileOperations);
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
    if (this.fileOperations == null) {
      if (other.fileOperations != null)
        return false;
    } else if (!this.fileOperations.equals(other.fileOperations))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.workspaceFolders== null) ? 0 : this.workspaceFolders.hashCode());
    return prime * result + ((this.fileOperations== null) ? 0 : this.fileOperations.hashCode());
  }
}
