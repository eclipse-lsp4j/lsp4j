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
import org.eclipse.lsp4j.FileOperationFilter;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The options for file operations.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class FileOperationOptions {
  /**
   * The actual filters.
   */
  @NonNull
  private List<FileOperationFilter> filters = new ArrayList<FileOperationFilter>();
  
  public FileOperationOptions() {
  }
  
  public FileOperationOptions(@NonNull final List<FileOperationFilter> filters) {
    this.filters = Preconditions.<List<FileOperationFilter>>checkNotNull(filters, "filters");
  }
  
  /**
   * The actual filters.
   */
  @Pure
  @NonNull
  public List<FileOperationFilter> getFilters() {
    return this.filters;
  }
  
  /**
   * The actual filters.
   */
  public void setFilters(@NonNull final List<FileOperationFilter> filters) {
    this.filters = Preconditions.checkNotNull(filters, "filters");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("filters", this.filters);
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
    FileOperationOptions other = (FileOperationOptions) obj;
    if (this.filters == null) {
      if (other.filters != null)
        return false;
    } else if (!this.filters.equals(other.filters))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.filters== null) ? 0 : this.filters.hashCode());
  }
}
