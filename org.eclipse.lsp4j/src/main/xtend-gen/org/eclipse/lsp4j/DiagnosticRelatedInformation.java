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

import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
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
    this.location = Preconditions.<Location>checkNotNull(location, "location");
    this.message = Preconditions.<String>checkNotNull(message, "message");
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
    if (location == null) {
      throw new IllegalArgumentException("Property must not be null: location");
    }
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
    if (message == null) {
      throw new IllegalArgumentException("Property must not be null: message");
    }
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
    return prime * result + ((this.message== null) ? 0 : this.message.hashCode());
  }
}
