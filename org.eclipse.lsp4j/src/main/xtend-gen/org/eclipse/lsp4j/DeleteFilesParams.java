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

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.FileDelete;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The parameters sent in notifications/requests for user-initiated deletes
 * of files.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class DeleteFilesParams {
  /**
   * An array of all files/folders deleted in this operation.
   */
  @NonNull
  private List<FileDelete> files = new ArrayList<FileDelete>();
  
  public DeleteFilesParams() {
  }
  
  public DeleteFilesParams(@NonNull final List<FileDelete> files) {
    this.files = Preconditions.<List<FileDelete>>checkNotNull(files, "files");
  }
  
  /**
   * An array of all files/folders deleted in this operation.
   */
  @Pure
  @NonNull
  public List<FileDelete> getFiles() {
    return this.files;
  }
  
  /**
   * An array of all files/folders deleted in this operation.
   */
  public void setFiles(@NonNull final List<FileDelete> files) {
    this.files = Preconditions.checkNotNull(files, "files");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("files", this.files);
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
    DeleteFilesParams other = (DeleteFilesParams) obj;
    if (this.files == null) {
      if (other.files != null)
        return false;
    } else if (!this.files.equals(other.files))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.files== null) ? 0 : this.files.hashCode());
  }
}
