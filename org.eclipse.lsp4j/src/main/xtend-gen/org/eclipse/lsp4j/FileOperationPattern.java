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

import org.eclipse.lsp4j.FileOperationPatternKind;
import org.eclipse.lsp4j.FileOperationPatternOptions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A pattern to describe in which file operation requests or notifications
 * the server is interested in.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class FileOperationPattern {
  /**
   * The glob pattern to match. Glob patterns can have the following syntax:
   * - `*` to match one or more characters in a path segment		 * - `*` to match one or more characters in a path segment
   * - `?` to match on one character in a path segment		 * - `?` to match on one character in a path segment
   * - `**` to match any number of path segments, including none		 * - `**` to match any number of path segments, including none
   * - `{}` to group conditions (e.g. `**​/*.{ts,js}` matches all TypeScript and JavaScript files)		 * - `{}` to group conditions (e.g. `**​/*.{ts,js}` matches all TypeScript
   * - `[]` to declare a range of characters to match in a path segment (e.g., `example.[0-9]` to match on `example.0`, `example.1`, …)		 *   and JavaScript files)
   * - `[!...]` to negate a range of characters to match in a path segment (e.g., `example.[!0-9]` to match on `example.a`, `example.b`, but not `example.0`)		 * - `[]` to declare a range of characters to match in a path segment
   *   (e.g., `example.[0-9]` to match on `example.0`, `example.1`, …)
   * - `[!...]` to negate a range of characters to match in a path segment
   *   (e.g., `example.[!0-9]` to match on `example.a`, `example.b`, but
   *   not `example.0`)
   */
  @NonNull
  private String glob;
  
  /**
   * Whether to match files or folders with this pattern.
   * 
   * Matches both if undefined.
   */
  private FileOperationPatternKind matches;
  
  /**
   * Additional options used during matching.
   */
  private FileOperationPatternOptions options;
  
  public FileOperationPattern() {
  }
  
  public FileOperationPattern(@NonNull final String glob) {
    this.glob = Preconditions.<String>checkNotNull(glob, "glob");
  }
  
  /**
   * The glob pattern to match. Glob patterns can have the following syntax:
   * - `*` to match one or more characters in a path segment		 * - `*` to match one or more characters in a path segment
   * - `?` to match on one character in a path segment		 * - `?` to match on one character in a path segment
   * - `**` to match any number of path segments, including none		 * - `**` to match any number of path segments, including none
   * - `{}` to group conditions (e.g. `**​/*.{ts,js}` matches all TypeScript and JavaScript files)		 * - `{}` to group conditions (e.g. `**​/*.{ts,js}` matches all TypeScript
   * - `[]` to declare a range of characters to match in a path segment (e.g., `example.[0-9]` to match on `example.0`, `example.1`, …)		 *   and JavaScript files)
   * - `[!...]` to negate a range of characters to match in a path segment (e.g., `example.[!0-9]` to match on `example.a`, `example.b`, but not `example.0`)		 * - `[]` to declare a range of characters to match in a path segment
   *   (e.g., `example.[0-9]` to match on `example.0`, `example.1`, …)
   * - `[!...]` to negate a range of characters to match in a path segment
   *   (e.g., `example.[!0-9]` to match on `example.a`, `example.b`, but
   *   not `example.0`)
   */
  @Pure
  @NonNull
  public String getGlob() {
    return this.glob;
  }
  
  /**
   * The glob pattern to match. Glob patterns can have the following syntax:
   * - `*` to match one or more characters in a path segment		 * - `*` to match one or more characters in a path segment
   * - `?` to match on one character in a path segment		 * - `?` to match on one character in a path segment
   * - `**` to match any number of path segments, including none		 * - `**` to match any number of path segments, including none
   * - `{}` to group conditions (e.g. `**​/*.{ts,js}` matches all TypeScript and JavaScript files)		 * - `{}` to group conditions (e.g. `**​/*.{ts,js}` matches all TypeScript
   * - `[]` to declare a range of characters to match in a path segment (e.g., `example.[0-9]` to match on `example.0`, `example.1`, …)		 *   and JavaScript files)
   * - `[!...]` to negate a range of characters to match in a path segment (e.g., `example.[!0-9]` to match on `example.a`, `example.b`, but not `example.0`)		 * - `[]` to declare a range of characters to match in a path segment
   *   (e.g., `example.[0-9]` to match on `example.0`, `example.1`, …)
   * - `[!...]` to negate a range of characters to match in a path segment
   *   (e.g., `example.[!0-9]` to match on `example.a`, `example.b`, but
   *   not `example.0`)
   */
  public void setGlob(@NonNull final String glob) {
    this.glob = Preconditions.checkNotNull(glob, "glob");
  }
  
  /**
   * Whether to match files or folders with this pattern.
   * 
   * Matches both if undefined.
   */
  @Pure
  public FileOperationPatternKind getMatches() {
    return this.matches;
  }
  
  /**
   * Whether to match files or folders with this pattern.
   * 
   * Matches both if undefined.
   */
  public void setMatches(final FileOperationPatternKind matches) {
    this.matches = matches;
  }
  
  /**
   * Additional options used during matching.
   */
  @Pure
  public FileOperationPatternOptions getOptions() {
    return this.options;
  }
  
  /**
   * Additional options used during matching.
   */
  public void setOptions(final FileOperationPatternOptions options) {
    this.options = options;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("glob", this.glob);
    b.add("matches", this.matches);
    b.add("options", this.options);
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
    FileOperationPattern other = (FileOperationPattern) obj;
    if (this.glob == null) {
      if (other.glob != null)
        return false;
    } else if (!this.glob.equals(other.glob))
      return false;
    if (this.matches == null) {
      if (other.matches != null)
        return false;
    } else if (!this.matches.equals(other.matches))
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
    int result = 1;
    result = prime * result + ((this.glob== null) ? 0 : this.glob.hashCode());
    result = prime * result + ((this.matches== null) ? 0 : this.matches.hashCode());
    return prime * result + ((this.options== null) ? 0 : this.options.hashCode());
  }
}
