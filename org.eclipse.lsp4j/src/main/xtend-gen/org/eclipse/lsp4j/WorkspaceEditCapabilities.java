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
import org.eclipse.lsp4j.WorkspaceEditChangeAnnotationSupportCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to {@link WorkspaceEdit}s
 */
@SuppressWarnings("all")
public class WorkspaceEditCapabilities {
  /**
   * The client supports versioned document changes in {@link WorkspaceEdit}s
   */
  private Boolean documentChanges;
  
  /**
   * The client supports resource changes
   * in {@link WorkspaceEdit}s.
   * 
   * @deprecated Since LSP introduced resource operations, use {@link #resourceOperations}
   */
  @Deprecated
  @Beta
  private Boolean resourceChanges;
  
  /**
   * The resource operations the client supports. Clients should at least
   * support 'create', 'rename' and 'delete' files and folders.
   * 
   * See {@link ResourceOperationKind} for allowed values.
   * 
   * Since 3.13.0
   */
  private List<String> resourceOperations;
  
  /**
   * The failure handling strategy of a client if applying the workspace edit
   * fails.
   * 
   * See {@link FailureHandlingKind} for allowed values.
   * 
   * Since 3.13.0
   */
  private String failureHandling;
  
  /**
   * Whether the client normalizes line endings to the client specific
   * setting.
   * If set to {@code true} the client will normalize line ending characters
   * in a workspace edit to the client specific new line character(s).
   * 
   * Since 3.16.0
   */
  private Boolean normalizesLineEndings;
  
  /**
   * Whether the client in general supports change annotations on text edits,
   * create file, rename file and delete file changes.
   * 
   * Since 3.16.0
   */
  private WorkspaceEditChangeAnnotationSupportCapabilities changeAnnotationSupport;
  
  public WorkspaceEditCapabilities() {
  }
  
  @Deprecated
  public WorkspaceEditCapabilities(final Boolean documentChanges) {
    this.documentChanges = documentChanges;
  }
  
  /**
   * The client supports versioned document changes in {@link WorkspaceEdit}s
   */
  @Pure
  public Boolean getDocumentChanges() {
    return this.documentChanges;
  }
  
  /**
   * The client supports versioned document changes in {@link WorkspaceEdit}s
   */
  public void setDocumentChanges(final Boolean documentChanges) {
    this.documentChanges = documentChanges;
  }
  
  /**
   * The client supports resource changes
   * in {@link WorkspaceEdit}s.
   * 
   * @deprecated Since LSP introduced resource operations, use {@link #resourceOperations}
   */
  @Pure
  @Deprecated
  public Boolean getResourceChanges() {
    return this.resourceChanges;
  }
  
  /**
   * The client supports resource changes
   * in {@link WorkspaceEdit}s.
   * 
   * @deprecated Since LSP introduced resource operations, use {@link #resourceOperations}
   */
  @Deprecated
  public void setResourceChanges(final Boolean resourceChanges) {
    this.resourceChanges = resourceChanges;
  }
  
  /**
   * The resource operations the client supports. Clients should at least
   * support 'create', 'rename' and 'delete' files and folders.
   * 
   * See {@link ResourceOperationKind} for allowed values.
   * 
   * Since 3.13.0
   */
  @Pure
  public List<String> getResourceOperations() {
    return this.resourceOperations;
  }
  
  /**
   * The resource operations the client supports. Clients should at least
   * support 'create', 'rename' and 'delete' files and folders.
   * 
   * See {@link ResourceOperationKind} for allowed values.
   * 
   * Since 3.13.0
   */
  public void setResourceOperations(final List<String> resourceOperations) {
    this.resourceOperations = resourceOperations;
  }
  
  /**
   * The failure handling strategy of a client if applying the workspace edit
   * fails.
   * 
   * See {@link FailureHandlingKind} for allowed values.
   * 
   * Since 3.13.0
   */
  @Pure
  public String getFailureHandling() {
    return this.failureHandling;
  }
  
  /**
   * The failure handling strategy of a client if applying the workspace edit
   * fails.
   * 
   * See {@link FailureHandlingKind} for allowed values.
   * 
   * Since 3.13.0
   */
  public void setFailureHandling(final String failureHandling) {
    this.failureHandling = failureHandling;
  }
  
  /**
   * Whether the client normalizes line endings to the client specific
   * setting.
   * If set to {@code true} the client will normalize line ending characters
   * in a workspace edit to the client specific new line character(s).
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getNormalizesLineEndings() {
    return this.normalizesLineEndings;
  }
  
  /**
   * Whether the client normalizes line endings to the client specific
   * setting.
   * If set to {@code true} the client will normalize line ending characters
   * in a workspace edit to the client specific new line character(s).
   * 
   * Since 3.16.0
   */
  public void setNormalizesLineEndings(final Boolean normalizesLineEndings) {
    this.normalizesLineEndings = normalizesLineEndings;
  }
  
  /**
   * Whether the client in general supports change annotations on text edits,
   * create file, rename file and delete file changes.
   * 
   * Since 3.16.0
   */
  @Pure
  public WorkspaceEditChangeAnnotationSupportCapabilities getChangeAnnotationSupport() {
    return this.changeAnnotationSupport;
  }
  
  /**
   * Whether the client in general supports change annotations on text edits,
   * create file, rename file and delete file changes.
   * 
   * Since 3.16.0
   */
  public void setChangeAnnotationSupport(final WorkspaceEditChangeAnnotationSupportCapabilities changeAnnotationSupport) {
    this.changeAnnotationSupport = changeAnnotationSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("documentChanges", this.documentChanges);
    b.add("resourceChanges", this.resourceChanges);
    b.add("resourceOperations", this.resourceOperations);
    b.add("failureHandling", this.failureHandling);
    b.add("normalizesLineEndings", this.normalizesLineEndings);
    b.add("changeAnnotationSupport", this.changeAnnotationSupport);
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
    if (this.normalizesLineEndings == null) {
      if (other.normalizesLineEndings != null)
        return false;
    } else if (!this.normalizesLineEndings.equals(other.normalizesLineEndings))
      return false;
    if (this.changeAnnotationSupport == null) {
      if (other.changeAnnotationSupport != null)
        return false;
    } else if (!this.changeAnnotationSupport.equals(other.changeAnnotationSupport))
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
    result = prime * result + ((this.failureHandling== null) ? 0 : this.failureHandling.hashCode());
    result = prime * result + ((this.normalizesLineEndings== null) ? 0 : this.normalizesLineEndings.hashCode());
    return prime * result + ((this.changeAnnotationSupport== null) ? 0 : this.changeAnnotationSupport.hashCode());
  }
}
