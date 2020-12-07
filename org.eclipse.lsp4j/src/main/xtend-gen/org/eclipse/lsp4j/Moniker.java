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

import com.google.common.annotations.Beta;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Moniker definition to match LSIF 0.5 moniker definition.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class Moniker {
  /**
   * The scheme of the moniker. For example tsc or .Net
   */
  @NonNull
  private String scheme;
  
  /**
   * The identifier of the moniker. The value is opaque in LSIF however
   * schema owners are allowed to define the structure if they want.
   */
  @NonNull
  private String identifier;
  
  /**
   * The scope in which the moniker is unique. Values are taken from {@link UniquenessLevel}.
   */
  @NonNull
  private String unique;
  
  /**
   * The moniker kind if known. Values are taken from {@link MonikerKind}.
   */
  private String kind;
  
  public Moniker() {
  }
  
  public Moniker(@NonNull final String scheme, @NonNull final String identifier, @NonNull final String unique) {
    this.scheme = Preconditions.<String>checkNotNull(scheme, "scheme");
    this.identifier = Preconditions.<String>checkNotNull(identifier, "identifier");
    this.unique = Preconditions.<String>checkNotNull(unique, "unique");
  }
  
  public Moniker(@NonNull final String scheme, @NonNull final String identifier, @NonNull final String unique, final String kind) {
    this(scheme, identifier, unique);
    this.kind = kind;
  }
  
  /**
   * The scheme of the moniker. For example tsc or .Net
   */
  @Pure
  @NonNull
  public String getScheme() {
    return this.scheme;
  }
  
  /**
   * The scheme of the moniker. For example tsc or .Net
   */
  public void setScheme(@NonNull final String scheme) {
    this.scheme = Preconditions.checkNotNull(scheme, "scheme");
  }
  
  /**
   * The identifier of the moniker. The value is opaque in LSIF however
   * schema owners are allowed to define the structure if they want.
   */
  @Pure
  @NonNull
  public String getIdentifier() {
    return this.identifier;
  }
  
  /**
   * The identifier of the moniker. The value is opaque in LSIF however
   * schema owners are allowed to define the structure if they want.
   */
  public void setIdentifier(@NonNull final String identifier) {
    this.identifier = Preconditions.checkNotNull(identifier, "identifier");
  }
  
  /**
   * The scope in which the moniker is unique. Values are taken from {@link UniquenessLevel}.
   */
  @Pure
  @NonNull
  public String getUnique() {
    return this.unique;
  }
  
  /**
   * The scope in which the moniker is unique. Values are taken from {@link UniquenessLevel}.
   */
  public void setUnique(@NonNull final String unique) {
    this.unique = Preconditions.checkNotNull(unique, "unique");
  }
  
  /**
   * The moniker kind if known. Values are taken from {@link MonikerKind}.
   */
  @Pure
  public String getKind() {
    return this.kind;
  }
  
  /**
   * The moniker kind if known. Values are taken from {@link MonikerKind}.
   */
  public void setKind(final String kind) {
    this.kind = kind;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("scheme", this.scheme);
    b.add("identifier", this.identifier);
    b.add("unique", this.unique);
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
    Moniker other = (Moniker) obj;
    if (this.scheme == null) {
      if (other.scheme != null)
        return false;
    } else if (!this.scheme.equals(other.scheme))
      return false;
    if (this.identifier == null) {
      if (other.identifier != null)
        return false;
    } else if (!this.identifier.equals(other.identifier))
      return false;
    if (this.unique == null) {
      if (other.unique != null)
        return false;
    } else if (!this.unique.equals(other.unique))
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
    result = prime * result + ((this.scheme== null) ? 0 : this.scheme.hashCode());
    result = prime * result + ((this.identifier== null) ? 0 : this.identifier.hashCode());
    result = prime * result + ((this.unique== null) ? 0 : this.unique.hashCode());
    return prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
  }
}
