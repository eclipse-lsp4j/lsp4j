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

import org.eclipse.lsp4j.FileOperationOptions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The server is interested in file notifications/requests.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class FileOperationsServerCapabilities {
  /**
   * The server is interested in receiving didCreateFiles notifications.
   */
  private FileOperationOptions didCreate;
  
  /**
   * The server is interested in receiving willCreateFiles requests.
   */
  private FileOperationOptions willCreate;
  
  /**
   * The server is interested in receiving didRenameFiles notifications.
   */
  private FileOperationOptions didRename;
  
  /**
   * The server is interested in receiving willRenameFiles requests.
   */
  private FileOperationOptions willRename;
  
  /**
   * The server is interested in receiving didDeleteFiles file notifications.
   */
  private FileOperationOptions didDelete;
  
  /**
   * The server is interested in receiving willDeleteFiles file requests.
   */
  private FileOperationOptions willDelete;
  
  public FileOperationsServerCapabilities() {
  }
  
  /**
   * The server is interested in receiving didCreateFiles notifications.
   */
  @Pure
  public FileOperationOptions getDidCreate() {
    return this.didCreate;
  }
  
  /**
   * The server is interested in receiving didCreateFiles notifications.
   */
  public void setDidCreate(final FileOperationOptions didCreate) {
    this.didCreate = didCreate;
  }
  
  /**
   * The server is interested in receiving willCreateFiles requests.
   */
  @Pure
  public FileOperationOptions getWillCreate() {
    return this.willCreate;
  }
  
  /**
   * The server is interested in receiving willCreateFiles requests.
   */
  public void setWillCreate(final FileOperationOptions willCreate) {
    this.willCreate = willCreate;
  }
  
  /**
   * The server is interested in receiving didRenameFiles notifications.
   */
  @Pure
  public FileOperationOptions getDidRename() {
    return this.didRename;
  }
  
  /**
   * The server is interested in receiving didRenameFiles notifications.
   */
  public void setDidRename(final FileOperationOptions didRename) {
    this.didRename = didRename;
  }
  
  /**
   * The server is interested in receiving willRenameFiles requests.
   */
  @Pure
  public FileOperationOptions getWillRename() {
    return this.willRename;
  }
  
  /**
   * The server is interested in receiving willRenameFiles requests.
   */
  public void setWillRename(final FileOperationOptions willRename) {
    this.willRename = willRename;
  }
  
  /**
   * The server is interested in receiving didDeleteFiles file notifications.
   */
  @Pure
  public FileOperationOptions getDidDelete() {
    return this.didDelete;
  }
  
  /**
   * The server is interested in receiving didDeleteFiles file notifications.
   */
  public void setDidDelete(final FileOperationOptions didDelete) {
    this.didDelete = didDelete;
  }
  
  /**
   * The server is interested in receiving willDeleteFiles file requests.
   */
  @Pure
  public FileOperationOptions getWillDelete() {
    return this.willDelete;
  }
  
  /**
   * The server is interested in receiving willDeleteFiles file requests.
   */
  public void setWillDelete(final FileOperationOptions willDelete) {
    this.willDelete = willDelete;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("didCreate", this.didCreate);
    b.add("willCreate", this.willCreate);
    b.add("didRename", this.didRename);
    b.add("willRename", this.willRename);
    b.add("didDelete", this.didDelete);
    b.add("willDelete", this.willDelete);
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
    FileOperationsServerCapabilities other = (FileOperationsServerCapabilities) obj;
    if (this.didCreate == null) {
      if (other.didCreate != null)
        return false;
    } else if (!this.didCreate.equals(other.didCreate))
      return false;
    if (this.willCreate == null) {
      if (other.willCreate != null)
        return false;
    } else if (!this.willCreate.equals(other.willCreate))
      return false;
    if (this.didRename == null) {
      if (other.didRename != null)
        return false;
    } else if (!this.didRename.equals(other.didRename))
      return false;
    if (this.willRename == null) {
      if (other.willRename != null)
        return false;
    } else if (!this.willRename.equals(other.willRename))
      return false;
    if (this.didDelete == null) {
      if (other.didDelete != null)
        return false;
    } else if (!this.didDelete.equals(other.didDelete))
      return false;
    if (this.willDelete == null) {
      if (other.willDelete != null)
        return false;
    } else if (!this.willDelete.equals(other.willDelete))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.didCreate== null) ? 0 : this.didCreate.hashCode());
    result = prime * result + ((this.willCreate== null) ? 0 : this.willCreate.hashCode());
    result = prime * result + ((this.didRename== null) ? 0 : this.didRename.hashCode());
    result = prime * result + ((this.willRename== null) ? 0 : this.willRename.hashCode());
    result = prime * result + ((this.didDelete== null) ? 0 : this.didDelete.hashCode());
    return prime * result + ((this.willDelete== null) ? 0 : this.willDelete.hashCode());
  }
}
