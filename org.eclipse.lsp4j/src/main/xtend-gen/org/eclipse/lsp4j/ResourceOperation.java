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
import com.google.gson.annotations.JsonAdapter;
import org.eclipse.lsp4j.ChangeAnnotation;
import org.eclipse.lsp4j.adapters.ResourceOperationTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@JsonAdapter(ResourceOperationTypeAdapter.class)
@SuppressWarnings("all")
public abstract class ResourceOperation {
  /**
   * The kind of resource operation. For allowed values, see {@link ResourceOperationKind}
   */
  @NonNull
  private String kind;
  
  /**
   * An optional annotation describing the operation.
   * 
   * Since 3.16.0
   */
  @Beta
  private ChangeAnnotation annotation;
  
  public ResourceOperation() {
  }
  
  public ResourceOperation(@NonNull final String kind) {
    this.kind = Preconditions.<String>checkNotNull(kind, "kind");
  }
  
  /**
   * The kind of resource operation. For allowed values, see {@link ResourceOperationKind}
   */
  @Pure
  @NonNull
  public String getKind() {
    return this.kind;
  }
  
  /**
   * The kind of resource operation. For allowed values, see {@link ResourceOperationKind}
   */
  public void setKind(@NonNull final String kind) {
    this.kind = Preconditions.checkNotNull(kind, "kind");
  }
  
  /**
   * An optional annotation describing the operation.
   * 
   * Since 3.16.0
   */
  @Pure
  public ChangeAnnotation getAnnotation() {
    return this.annotation;
  }
  
  /**
   * An optional annotation describing the operation.
   * 
   * Since 3.16.0
   */
  public void setAnnotation(final ChangeAnnotation annotation) {
    this.annotation = annotation;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("kind", this.kind);
    b.add("annotation", this.annotation);
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
    ResourceOperation other = (ResourceOperation) obj;
    if (this.kind == null) {
      if (other.kind != null)
        return false;
    } else if (!this.kind.equals(other.kind))
      return false;
    if (this.annotation == null) {
      if (other.annotation != null)
        return false;
    } else if (!this.annotation.equals(other.annotation))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.kind== null) ? 0 : this.kind.hashCode());
    return prime * result + ((this.annotation== null) ? 0 : this.annotation.hashCode());
  }
}
