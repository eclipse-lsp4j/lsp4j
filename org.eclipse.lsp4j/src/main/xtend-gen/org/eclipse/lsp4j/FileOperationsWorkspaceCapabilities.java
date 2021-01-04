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
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The client has support for file requests/notifications.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class FileOperationsWorkspaceCapabilities extends DynamicRegistrationCapabilities {
  /**
   * The client has support for sending didCreateFiles notifications.
   */
  private Boolean didCreate;
  
  /**
   * The client has support for sending willCreateFiles requests.
   */
  private Boolean willCreate;
  
  /**
   * The client has support for sending didRenameFiles notifications.
   */
  private Boolean didRename;
  
  /**
   * The client has support for sending willRenameFiles requests.
   */
  private Boolean willRename;
  
  /**
   * The client has support for sending didDeleteFiles notifications.
   */
  private Boolean didDelete;
  
  /**
   * The client has support for sending willDeleteFiles requests.
   */
  private Boolean willDelete;
  
  public FileOperationsWorkspaceCapabilities() {
  }
  
  /**
   * The client has support for sending didCreateFiles notifications.
   */
  @Pure
  public Boolean getDidCreate() {
    return this.didCreate;
  }
  
  /**
   * The client has support for sending didCreateFiles notifications.
   */
  public void setDidCreate(final Boolean didCreate) {
    this.didCreate = didCreate;
  }
  
  /**
   * The client has support for sending willCreateFiles requests.
   */
  @Pure
  public Boolean getWillCreate() {
    return this.willCreate;
  }
  
  /**
   * The client has support for sending willCreateFiles requests.
   */
  public void setWillCreate(final Boolean willCreate) {
    this.willCreate = willCreate;
  }
  
  /**
   * The client has support for sending didRenameFiles notifications.
   */
  @Pure
  public Boolean getDidRename() {
    return this.didRename;
  }
  
  /**
   * The client has support for sending didRenameFiles notifications.
   */
  public void setDidRename(final Boolean didRename) {
    this.didRename = didRename;
  }
  
  /**
   * The client has support for sending willRenameFiles requests.
   */
  @Pure
  public Boolean getWillRename() {
    return this.willRename;
  }
  
  /**
   * The client has support for sending willRenameFiles requests.
   */
  public void setWillRename(final Boolean willRename) {
    this.willRename = willRename;
  }
  
  /**
   * The client has support for sending didDeleteFiles notifications.
   */
  @Pure
  public Boolean getDidDelete() {
    return this.didDelete;
  }
  
  /**
   * The client has support for sending didDeleteFiles notifications.
   */
  public void setDidDelete(final Boolean didDelete) {
    this.didDelete = didDelete;
  }
  
  /**
   * The client has support for sending willDeleteFiles requests.
   */
  @Pure
  public Boolean getWillDelete() {
    return this.willDelete;
  }
  
  /**
   * The client has support for sending willDeleteFiles requests.
   */
  public void setWillDelete(final Boolean willDelete) {
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
    FileOperationsWorkspaceCapabilities other = (FileOperationsWorkspaceCapabilities) obj;
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
    int result = super.hashCode();
    result = prime * result + ((this.didCreate== null) ? 0 : this.didCreate.hashCode());
    result = prime * result + ((this.willCreate== null) ? 0 : this.willCreate.hashCode());
    result = prime * result + ((this.didRename== null) ? 0 : this.didRename.hashCode());
    result = prime * result + ((this.willRename== null) ? 0 : this.willRename.hashCode());
    result = prime * result + ((this.didDelete== null) ? 0 : this.didDelete.hashCode());
    return prime * result + ((this.willDelete== null) ? 0 : this.willDelete.hashCode());
  }
}
