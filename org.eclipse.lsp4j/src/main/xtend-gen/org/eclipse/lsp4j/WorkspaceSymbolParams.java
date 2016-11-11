package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The parameters of a Workspace Symbol Request.
 */
@SuppressWarnings("all")
public class WorkspaceSymbolParams {
  /**
   * A non-empty query string
   */
  @NonNull
  private String query;
  
  /**
   * A non-empty query string
   */
  @Pure
  public String getQuery() {
    return this.query;
  }
  
  /**
   * A non-empty query string
   */
  public void setQuery(final String query) {
    this.query = query;
  }
  
  public WorkspaceSymbolParams() {
    
  }
  
  public WorkspaceSymbolParams(final String query) {
    this.query = query;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("query", this.query);
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
    WorkspaceSymbolParams other = (WorkspaceSymbolParams) obj;
    if (this.query == null) {
      if (other.query != null)
        return false;
    } else if (!this.query.equals(other.query))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.query== null) ? 0 : this.query.hashCode());
    return result;
  }
}
