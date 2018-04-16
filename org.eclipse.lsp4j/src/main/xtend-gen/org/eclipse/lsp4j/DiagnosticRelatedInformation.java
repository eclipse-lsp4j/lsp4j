/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a related message and source code location for a diagnostic. This should be
 * used to point to code locations that cause or related to a diagnostics, e.g when duplicating
 * a symbol in a scope.
 * 
 * Since 3.7.0
 */
@SuppressWarnings("all")
public class DiagnosticRelatedInformation {
  /**
   * The location of this related diagnostic information.
   */
  @NonNull
  private Location location;
  
  /**
   * The message of this related diagnostic information.
   */
  @NonNull
  private String message;
  
  public DiagnosticRelatedInformation() {
  }
  
  public DiagnosticRelatedInformation(@NonNull final Location location, @NonNull final String message) {
    this.location = location;
    this.message = message;
  }
  
  /**
   * The location of this related diagnostic information.
   */
  @Pure
  @NonNull
  public Location getLocation() {
    return this.location;
  }
  
  /**
   * The location of this related diagnostic information.
   */
  public void setLocation(@NonNull final Location location) {
    this.location = location;
  }
  
  /**
   * The message of this related diagnostic information.
   */
  @Pure
  @NonNull
  public String getMessage() {
    return this.message;
  }
  
  /**
   * The message of this related diagnostic information.
   */
  public void setMessage(@NonNull final String message) {
    this.message = message;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("location", this.location);
    b.add("message", this.message);
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
    DiagnosticRelatedInformation other = (DiagnosticRelatedInformation) obj;
    if (this.location == null) {
      if (other.location != null)
        return false;
    } else if (!this.location.equals(other.location))
      return false;
    if (this.message == null) {
      if (other.message != null)
        return false;
    } else if (!this.message.equals(other.message))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.location== null) ? 0 : this.location.hashCode());
    result = prime * result + ((this.message== null) ? 0 : this.message.hashCode());
    return result;
  }
}
