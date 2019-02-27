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
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The workspace folder change event.
 */
@SuppressWarnings("all")
public class WorkspaceFoldersChangeEvent {
  /**
   * The array of added workspace folders
   */
  @NonNull
  private List<WorkspaceFolder> added = new ArrayList<WorkspaceFolder>();
  
  /**
   * The array of the removed workspace folders
   */
  @NonNull
  private List<WorkspaceFolder> removed = new ArrayList<WorkspaceFolder>();
  
  public WorkspaceFoldersChangeEvent() {
  }
  
  public WorkspaceFoldersChangeEvent(@NonNull final List<WorkspaceFolder> added, @NonNull final List<WorkspaceFolder> removed) {
    this.added = Preconditions.<List<WorkspaceFolder>>checkNotNull(added, "added");
    this.removed = Preconditions.<List<WorkspaceFolder>>checkNotNull(removed, "removed");
  }
  
  /**
   * The array of added workspace folders
   */
  @Pure
  @NonNull
  public List<WorkspaceFolder> getAdded() {
    return this.added;
  }
  
  /**
   * The array of added workspace folders
   */
  public void setAdded(@NonNull final List<WorkspaceFolder> added) {
    if (added == null) {
      throw new IllegalArgumentException("Property must not be null: added");
    }
    this.added = added;
  }
  
  /**
   * The array of the removed workspace folders
   */
  @Pure
  @NonNull
  public List<WorkspaceFolder> getRemoved() {
    return this.removed;
  }
  
  /**
   * The array of the removed workspace folders
   */
  public void setRemoved(@NonNull final List<WorkspaceFolder> removed) {
    if (removed == null) {
      throw new IllegalArgumentException("Property must not be null: removed");
    }
    this.removed = removed;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("added", this.added);
    b.add("removed", this.removed);
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
    WorkspaceFoldersChangeEvent other = (WorkspaceFoldersChangeEvent) obj;
    if (this.added == null) {
      if (other.added != null)
        return false;
    } else if (!this.added.equals(other.added))
      return false;
    if (this.removed == null) {
      if (other.removed != null)
        return false;
    } else if (!this.removed.equals(other.removed))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.added== null) ? 0 : this.added.hashCode());
    return prime * result + ((this.removed== null) ? 0 : this.removed.hashCode());
  }
}
