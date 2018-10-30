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
import java.util.List;
import org.eclipse.lsp4j.FailureHandlingKind;
import org.eclipse.lsp4j.ResourceOperationKind;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to `WorkspaceEdit`s
 */
@SuppressWarnings("all")
public class WorkspaceEditCapabilities {
  /**
   * The client supports versioned document changes in `WorkspaceEdit`s
   */
  private Boolean documentChanges;
  
  /**
   * The client supports resource changes
   * in `WorkspaceEdit`s.
   * 
   * @deprecated Since LSP introduces resource operations, use {link #resourceOperations}
   */
  @Deprecated
  @Beta
  private Boolean resourceChanges;
  
  /**
   * The resource operations the client supports. Clients should at least
   * support 'create', 'rename' and 'delete' files and folders.
   */
  private List<ResourceOperationKind> resourceOperations;
  
  /**
   * The failure handling strategy of a client if applying the workspace edit
   * fails.
   */
  private FailureHandlingKind failureHandling;
  
  public WorkspaceEditCapabilities() {
  }
  
  @Deprecated
  public WorkspaceEditCapabilities(final Boolean documentChanges) {
    this.documentChanges = documentChanges;
  }
  
  /**
   * The client supports versioned document changes in `WorkspaceEdit`s
   */
  @Pure
  public Boolean getDocumentChanges() {
    return this.documentChanges;
  }
  
  /**
   * The client supports versioned document changes in `WorkspaceEdit`s
   */
  public void setDocumentChanges(final Boolean documentChanges) {
    this.documentChanges = documentChanges;
  }
  
  /**
   * The client supports resource changes
   * in `WorkspaceEdit`s.
   * 
   * @deprecated Since LSP introduces resource operations, use {link #resourceOperations}
   */
  @Pure
  @Deprecated
  public Boolean getResourceChanges() {
    return this.resourceChanges;
  }
  
  /**
   * The client supports resource changes
   * in `WorkspaceEdit`s.
   * 
   * @deprecated Since LSP introduces resource operations, use {link #resourceOperations}
   */
  @Deprecated
  public void setResourceChanges(final Boolean resourceChanges) {
    this.resourceChanges = resourceChanges;
  }
  
  /**
   * The resource operations the client supports. Clients should at least
   * support 'create', 'rename' and 'delete' files and folders.
   */
  @Pure
  public List<ResourceOperationKind> getResourceOperations() {
    return this.resourceOperations;
  }
  
  /**
   * The resource operations the client supports. Clients should at least
   * support 'create', 'rename' and 'delete' files and folders.
   */
  public void setResourceOperations(final List<ResourceOperationKind> resourceOperations) {
    this.resourceOperations = resourceOperations;
  }
  
  /**
   * The failure handling strategy of a client if applying the workspace edit
   * fails.
   */
  @Pure
  public FailureHandlingKind getFailureHandling() {
    return this.failureHandling;
  }
  
  /**
   * The failure handling strategy of a client if applying the workspace edit
   * fails.
   */
  public void setFailureHandling(final FailureHandlingKind failureHandling) {
    this.failureHandling = failureHandling;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("documentChanges", this.documentChanges);
    b.add("resourceChanges", this.resourceChanges);
    b.add("resourceOperations", this.resourceOperations);
    b.add("failureHandling", this.failureHandling);
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
    WorkspaceEditCapabilities other = (WorkspaceEditCapabilities) obj;
    if (this.documentChanges == null) {
      if (other.documentChanges != null)
        return false;
    } else if (!this.documentChanges.equals(other.documentChanges))
      return false;
    if (this.resourceChanges == null) {
      if (other.resourceChanges != null)
        return false;
    } else if (!this.resourceChanges.equals(other.resourceChanges))
      return false;
    if (this.resourceOperations == null) {
      if (other.resourceOperations != null)
        return false;
    } else if (!this.resourceOperations.equals(other.resourceOperations))
      return false;
    if (this.failureHandling == null) {
      if (other.failureHandling != null)
        return false;
    } else if (!this.failureHandling.equals(other.failureHandling))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.documentChanges== null) ? 0 : this.documentChanges.hashCode());
    result = prime * result + ((this.resourceChanges== null) ? 0 : this.resourceChanges.hashCode());
    result = prime * result + ((this.resourceOperations== null) ? 0 : this.resourceOperations.hashCode());
    return prime * result + ((this.failureHandling== null) ? 0 : this.failureHandling.hashCode());
  }
}
