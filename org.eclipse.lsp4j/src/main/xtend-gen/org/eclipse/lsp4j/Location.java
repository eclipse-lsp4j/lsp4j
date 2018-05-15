/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Represents a location inside a resource, such as a line inside a text file.
 */
@SuppressWarnings("all")
public class Location {
  @NonNull
  private String uri;
  
  @NonNull
  private Range range;
  
  public Location() {
  }
  
  public Location(@NonNull final String uri, @NonNull final Range range) {
    this.uri = uri;
    this.range = range;
  }
  
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  public void setUri(@NonNull final String uri) {
    this.uri = uri;
  }
  
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  public void setRange(@NonNull final Range range) {
    this.range = range;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
    b.add("range", this.range);
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
    Location other = (Location) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    return prime * result + ((this.range== null) ? 0 : this.range.hashCode());
  }
}
