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

import org.eclipse.lsp4j.FileOperationPattern;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A filter to describe in which file operation requests or notifications
 * the server is interested in.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class FileOperationFilter {
  /**
   * The actual file operation pattern.
   */
  @NonNull
  private FileOperationPattern pattern;
  
  /**
   * A Uri like {@code file} or {@code untitled}.
   */
  private String scheme;
  
  public FileOperationFilter() {
  }
  
  public FileOperationFilter(@NonNull final FileOperationPattern pattern) {
    this.pattern = Preconditions.<FileOperationPattern>checkNotNull(pattern, "pattern");
  }
  
  public FileOperationFilter(@NonNull final FileOperationPattern pattern, final String scheme) {
    this(pattern);
    this.scheme = scheme;
  }
  
  /**
   * The actual file operation pattern.
   */
  @Pure
  @NonNull
  public FileOperationPattern getPattern() {
    return this.pattern;
  }
  
  /**
   * The actual file operation pattern.
   */
  public void setPattern(@NonNull final FileOperationPattern pattern) {
    this.pattern = Preconditions.checkNotNull(pattern, "pattern");
  }
  
  /**
   * A Uri like {@code file} or {@code untitled}.
   */
  @Pure
  public String getScheme() {
    return this.scheme;
  }
  
  /**
   * A Uri like {@code file} or {@code untitled}.
   */
  public void setScheme(final String scheme) {
    this.scheme = scheme;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("pattern", this.pattern);
    b.add("scheme", this.scheme);
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
    FileOperationFilter other = (FileOperationFilter) obj;
    if (this.pattern == null) {
      if (other.pattern != null)
        return false;
    } else if (!this.pattern.equals(other.pattern))
      return false;
    if (this.scheme == null) {
      if (other.scheme != null)
        return false;
    } else if (!this.scheme.equals(other.scheme))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.pattern== null) ? 0 : this.pattern.hashCode());
    return prime * result + ((this.scheme== null) ? 0 : this.scheme.hashCode());
  }
}
