/**
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.debug;

import java.util.Arrays;
import org.eclipse.lsp4j.debug.ExceptionBreakMode;
import org.eclipse.lsp4j.debug.ExceptionPathSegment;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * An ExceptionOptions assigns configuration options to a set of exceptions.
 */
@SuppressWarnings("all")
public class ExceptionOptions {
  /**
   * A path that selects a single or multiple exceptions in a tree. If 'path' is missing, the whole tree is
   * selected.
   * <p>
   * By convention the first segment of the path is a category that is used to group exceptions in the UI.
   * <p>
   * This is an optional property.
   */
  private ExceptionPathSegment[] path;
  
  /**
   * Condition when a thrown exception should result in a break.
   */
  @NonNull
  private ExceptionBreakMode breakMode;
  
  /**
   * A path that selects a single or multiple exceptions in a tree. If 'path' is missing, the whole tree is
   * selected.
   * <p>
   * By convention the first segment of the path is a category that is used to group exceptions in the UI.
   * <p>
   * This is an optional property.
   */
  @Pure
  public ExceptionPathSegment[] getPath() {
    return this.path;
  }
  
  /**
   * A path that selects a single or multiple exceptions in a tree. If 'path' is missing, the whole tree is
   * selected.
   * <p>
   * By convention the first segment of the path is a category that is used to group exceptions in the UI.
   * <p>
   * This is an optional property.
   */
  public void setPath(final ExceptionPathSegment[] path) {
    this.path = path;
  }
  
  /**
   * Condition when a thrown exception should result in a break.
   */
  @Pure
  @NonNull
  public ExceptionBreakMode getBreakMode() {
    return this.breakMode;
  }
  
  /**
   * Condition when a thrown exception should result in a break.
   */
  public void setBreakMode(@NonNull final ExceptionBreakMode breakMode) {
    this.breakMode = Preconditions.checkNotNull(breakMode, "breakMode");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("path", this.path);
    b.add("breakMode", this.breakMode);
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
    ExceptionOptions other = (ExceptionOptions) obj;
    if (this.path == null) {
      if (other.path != null)
        return false;
    } else if (!Arrays.deepEquals(this.path, other.path))
      return false;
    if (this.breakMode == null) {
      if (other.breakMode != null)
        return false;
    } else if (!this.breakMode.equals(other.breakMode))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.path== null) ? 0 : Arrays.deepHashCode(this.path));
    return prime * result + ((this.breakMode== null) ? 0 : this.breakMode.hashCode());
  }
}
