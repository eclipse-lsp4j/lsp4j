package org.eclipse.lsp4j;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A workspace edit represents changes to many resources managed in the workspace.
 */
@SuppressWarnings("all")
public class WorkspaceEdit {
  /**
   * Holds changes to existing resources.
   */
  @NonNull
  private Map<String, List<TextEdit>> changes = new LinkedHashMap<String, List<TextEdit>>();
  
  /**
   * Holds changes to existing resources.
   */
  @Pure
  public Map<String, List<TextEdit>> getChanges() {
    return this.changes;
  }
  
  /**
   * Holds changes to existing resources.
   */
  public void setChanges(final Map<String, List<TextEdit>> changes) {
    this.changes = changes;
  }
  
  public WorkspaceEdit() {
    
  }
  
  public WorkspaceEdit(final Map<String, List<TextEdit>> changes) {
    this.changes = changes;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("changes", this.changes);
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
    if (!super.equals(obj))
      return false;
    WorkspaceEdit other = (WorkspaceEdit) obj;
    if (this.changes == null) {
      if (other.changes != null)
        return false;
    } else if (!this.changes.equals(other.changes))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.changes== null) ? 0 : this.changes.hashCode());
    return result;
  }
}
