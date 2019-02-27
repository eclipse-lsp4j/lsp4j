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

@SuppressWarnings("all")
public class FileSystemWatcher {
  /**
   * The  glob pattern to watch
   */
  @NonNull
  private String globPattern;
  
  /**
   * The kind of events of interest. If omitted it defaults
   * to WatchKind.Create | WatchKind.Change | WatchKind.Delete
   * which is 7.
   */
  private Integer kind;
  
  public FileSystemWatcher() {
  }
  
  public FileSystemWatcher(@NonNull final String globPattern) {
    this.globPattern = Preconditions.<String>checkNotNull(globPattern, "globPattern");
  }
  
  public FileSystemWatcher(@NonNull final String globPattern, final Integer kind) {
    this(globPattern);
    this.kind = kind;
  }
  
  /**
   * The  glob pattern to watch
   */
  @Pure
  @NonNull
  public String getGlobPattern() {
    return this.globPattern;
  }
  
  /**
   * The  glob pattern to watch
   */
  public void setGlobPattern(@NonNull final String globPattern) {
    if (globPattern == null) {
      throw new IllegalArgumentException("Property must not be null: globPattern");
    }
    this.globPattern = globPattern;
  }
  
  /**
   * The kind of events of interest. If omitted it defaults
   * to WatchKind.Create | WatchKind.Change | WatchKind.Delete
   * which is 7.
   */
  @Pure
  public Integer getKind() {
    return this.kind;
  }
  
  /**
   * The kind of events of interest. If omitted it defaults
   * to WatchKind.Create | WatchKind.Change | WatchKind.Delete
   * which is 7.
   */
  public void setKind(final Integer kind) {
    this.kind = kind;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("globPattern", this.globPattern);
    b.add("kind", this.kind);
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
    FileSystemWatcher other = (FileSystemWatcher) obj;
    if (this.globPattern == null) {
      if (other.globPattern != null)
        return false;
    } else if (!this.globPattern.equals(other.globPattern))
      return false;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.globPattern== null) ? 0 : this.globPattern.hashCode());
    return prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
  }
}
