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

import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * An event describing a file change.
 */
@SuppressWarnings("all")
public class FileEvent {
  /**
   * The file's uri.
   */
  @NonNull
  private String uri;
  
  /**
   * The change type.
   */
  @NonNull
  private FileChangeType type;
  
  public FileEvent() {
  }
  
  public FileEvent(@NonNull final String uri, @NonNull final FileChangeType type) {
    this.uri = Preconditions.<String>checkNotNull(uri, "uri");
    this.type = Preconditions.<FileChangeType>checkNotNull(type, "type");
  }
  
  /**
   * The file's uri.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * The file's uri.
   */
  public void setUri(@NonNull final String uri) {
    if (uri == null) {
      throw new IllegalArgumentException("Property must not be null: uri");
    }
    this.uri = uri;
  }
  
  /**
   * The change type.
   */
  @Pure
  @NonNull
  public FileChangeType getType() {
    return this.type;
  }
  
  /**
   * The change type.
   */
  public void setType(@NonNull final FileChangeType type) {
    if (type == null) {
      throw new IllegalArgumentException("Property must not be null: type");
    }
    this.type = type;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
    b.add("type", this.type);
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
    FileEvent other = (FileEvent) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.type == null) {
      if (other.type != null)
        return false;
    } else if (!this.type.equals(other.type))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    return prime * result + ((this.type== null) ? 0 : this.type.hashCode());
  }
}
