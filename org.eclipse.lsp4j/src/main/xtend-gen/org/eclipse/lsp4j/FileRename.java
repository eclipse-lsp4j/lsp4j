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

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents information on a file/folder rename.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class FileRename {
  /**
   * A file:// URI for the original location of the file/folder being renamed.
   */
  @NonNull
  private String oldUri;
  
  /**
   * A file:// URI for the new location of the file/folder being renamed.
   */
  @NonNull
  private String newUri;
  
  public FileRename() {
  }
  
  public FileRename(@NonNull final String oldUri, @NonNull final String newUri) {
    this.oldUri = Preconditions.<String>checkNotNull(oldUri, "oldUri");
    this.newUri = Preconditions.<String>checkNotNull(newUri, "newUri");
  }
  
  /**
   * A file:// URI for the original location of the file/folder being renamed.
   */
  @Pure
  @NonNull
  public String getOldUri() {
    return this.oldUri;
  }
  
  /**
   * A file:// URI for the original location of the file/folder being renamed.
   */
  public void setOldUri(@NonNull final String oldUri) {
    this.oldUri = Preconditions.checkNotNull(oldUri, "oldUri");
  }
  
  /**
   * A file:// URI for the new location of the file/folder being renamed.
   */
  @Pure
  @NonNull
  public String getNewUri() {
    return this.newUri;
  }
  
  /**
   * A file:// URI for the new location of the file/folder being renamed.
   */
  public void setNewUri(@NonNull final String newUri) {
    this.newUri = Preconditions.checkNotNull(newUri, "newUri");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("oldUri", this.oldUri);
    b.add("newUri", this.newUri);
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
    FileRename other = (FileRename) obj;
    if (this.oldUri == null) {
      if (other.oldUri != null)
        return false;
    } else if (!this.oldUri.equals(other.oldUri))
      return false;
    if (this.newUri == null) {
      if (other.newUri != null)
        return false;
    } else if (!this.newUri.equals(other.newUri))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.oldUri== null) ? 0 : this.oldUri.hashCode());
    return prime * result + ((this.newUri== null) ? 0 : this.newUri.hashCode());
  }
}
