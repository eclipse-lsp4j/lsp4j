package org.eclipse.lsp4j;

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class WorkspaceEditCapabilities {
  /**
   * The client supports versioned document changes in `WorkspaceEdit`s
   */
  private Boolean documentChanges;
  
  public WorkspaceEditCapabilities() {
  }
  
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
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("documentChanges", this.documentChanges);
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.documentChanges== null) ? 0 : this.documentChanges.hashCode());
    return result;
  }
}
