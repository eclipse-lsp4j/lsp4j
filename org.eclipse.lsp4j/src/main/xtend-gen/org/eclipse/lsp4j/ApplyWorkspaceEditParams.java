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
  
  public ApplyWorkspaceEditParams() {
  }
  
  public ApplyWorkspaceEditParams(final WorkspaceEdit edit) {
    this.edit = edit;
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
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("edit", this.edit);
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.edit== null) ? 0 : this.edit.hashCode());
    return result;
  }
}
