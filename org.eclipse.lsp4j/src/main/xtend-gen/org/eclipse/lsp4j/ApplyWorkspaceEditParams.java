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

import com.google.common.base.Preconditions;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The workspace/applyEdit request is sent from the server to the client to modify resource on the client side.
 */
@SuppressWarnings("all")
public class ApplyWorkspaceEditParams {
  /**
   * The edits to apply.
   */
  @NonNull
  private WorkspaceEdit edit;
  
  /**
   * An optional label of the workspace edit. This label is
   * presented in the user interface for example on an undo
   * stack to undo the workspace edit.
   */
  private String label;
  
  public ApplyWorkspaceEditParams() {
  }
  
  public ApplyWorkspaceEditParams(@NonNull final WorkspaceEdit edit) {
    this.edit = Preconditions.<WorkspaceEdit>checkNotNull(edit);
  }
  
  public ApplyWorkspaceEditParams(@NonNull final WorkspaceEdit edit, final String label) {
    this(edit);
    this.label = label;
  }
  
  /**
   * The edits to apply.
   */
  @Pure
  @NonNull
  public WorkspaceEdit getEdit() {
    return this.edit;
  }
  
  /**
   * The edits to apply.
   */
  public void setEdit(@NonNull final WorkspaceEdit edit) {
    this.edit = edit;
  }
  
  /**
   * An optional label of the workspace edit. This label is
   * presented in the user interface for example on an undo
   * stack to undo the workspace edit.
   */
  @Pure
  public String getLabel() {
    return this.label;
  }
  
  /**
   * An optional label of the workspace edit. This label is
   * presented in the user interface for example on an undo
   * stack to undo the workspace edit.
   */
  public void setLabel(final String label) {
    this.label = label;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("edit", this.edit);
    b.add("label", this.label);
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
    ApplyWorkspaceEditParams other = (ApplyWorkspaceEditParams) obj;
    if (this.edit == null) {
      if (other.edit != null)
        return false;
    } else if (!this.edit.equals(other.edit))
      return false;
    if (this.label == null) {
      if (other.label != null)
        return false;
    } else if (!this.label.equals(other.label))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.edit== null) ? 0 : this.edit.hashCode());
    return prime * result + ((this.label== null) ? 0 : this.label.hashCode());
  }
}
