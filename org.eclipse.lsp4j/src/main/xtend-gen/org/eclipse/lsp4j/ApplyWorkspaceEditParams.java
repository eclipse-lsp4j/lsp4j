/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class ApplyWorkspaceEditParams {
  /**
   * The edits to apply.
   */
  private WorkspaceEdit edit;
  
  /**
   * An optional label of the workspace edit. This label is
   * presented in the user interface for example on an undo
   * stack to undo the workspace edit.
   */
  private String label;
  
  public ApplyWorkspaceEditParams() {
  }
  
  public ApplyWorkspaceEditParams(final WorkspaceEdit edit) {
    this.edit = edit;
  }
  
  public ApplyWorkspaceEditParams(final WorkspaceEdit edit, final String label) {
    this.edit = edit;
    this.label = label;
  }
  
  /**
   * The edits to apply.
   */
  @Pure
  public WorkspaceEdit getEdit() {
    return this.edit;
  }
  
  /**
   * The edits to apply.
   */
  public void setEdit(final WorkspaceEdit edit) {
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
    result = prime * result + ((this.label== null) ? 0 : this.label.hashCode());
    return result;
  }
}
