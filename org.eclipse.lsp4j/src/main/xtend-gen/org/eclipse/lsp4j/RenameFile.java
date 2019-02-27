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

import com.google.common.base.Preconditions;
import org.eclipse.lsp4j.RenameFileOptions;
import org.eclipse.lsp4j.ResourceOperation;
import org.eclipse.lsp4j.ResourceOperationKind;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Rename file operation
 */
@SuppressWarnings("all")
public class RenameFile extends ResourceOperation {
  /**
   * The old (existing) location.
   */
  @NonNull
  private String oldUri;
  
  /**
   * The new location.
   */
  @NonNull
  private String newUri;
  
  /**
   * Rename options.
   */
  private RenameFileOptions options;
  
  public RenameFile() {
    super(ResourceOperationKind.Rename);
  }
  
  public RenameFile(@NonNull final String oldUri, @NonNull final String newUri) {
    super(ResourceOperationKind.Rename);
    this.oldUri = Preconditions.<String>checkNotNull(oldUri);
    this.newUri = Preconditions.<String>checkNotNull(newUri);
  }
  
  public RenameFile(@NonNull final String oldUri, @NonNull final String newUri, final RenameFileOptions options) {
    super(ResourceOperationKind.Rename);
    this.oldUri = Preconditions.<String>checkNotNull(oldUri);
    this.newUri = Preconditions.<String>checkNotNull(newUri);
    this.options = options;
  }
  
  /**
   * The old (existing) location.
   */
  @Pure
  @NonNull
  public String getOldUri() {
    return this.oldUri;
  }
  
  /**
   * The old (existing) location.
   */
  public void setOldUri(@NonNull final String oldUri) {
    this.oldUri = oldUri;
  }
  
  /**
   * The new location.
   */
  @Pure
  @NonNull
  public String getNewUri() {
    return this.newUri;
  }
  
  /**
   * The new location.
   */
  public void setNewUri(@NonNull final String newUri) {
    this.newUri = newUri;
  }
  
  /**
   * Rename options.
   */
  @Pure
  public RenameFileOptions getOptions() {
    return this.options;
  }
  
  /**
   * Rename options.
   */
  public void setOptions(final RenameFileOptions options) {
    this.options = options;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("oldUri", this.oldUri);
    b.add("newUri", this.newUri);
    b.add("options", this.options);
    b.add("kind", getKind());
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
    RenameFile other = (RenameFile) obj;
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
    if (this.options == null) {
      if (other.options != null)
        return false;
    } else if (!this.options.equals(other.options))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.oldUri== null) ? 0 : this.oldUri.hashCode());
    result = prime * result + ((this.newUri== null) ? 0 : this.newUri.hashCode());
    return prime * result + ((this.options== null) ? 0 : this.options.hashCode());
  }
}
