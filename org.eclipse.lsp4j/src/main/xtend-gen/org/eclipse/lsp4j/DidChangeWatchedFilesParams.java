package org.eclipse.lsp4j;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The watched files notification is sent from the client to the server when the client detects changes
 * to file watched by the language client.
 */
@SuppressWarnings("all")
public class DidChangeWatchedFilesParams {
  /**
   * The actual file events.
   */
  @NonNull
  private List<FileEvent> changes;
  
  public DidChangeWatchedFilesParams() {
    ArrayList<FileEvent> _arrayList = new ArrayList<FileEvent>();
    this.changes = _arrayList;
  }
  
  public DidChangeWatchedFilesParams(final List<FileEvent> changes) {
    this.changes = changes;
  }
  
  /**
   * The actual file events.
   */
  @Pure
  @NonNull
  public List<FileEvent> getChanges() {
    return this.changes;
  }
  
  /**
   * The actual file events.
   */
  public void setChanges(@NonNull final List<FileEvent> changes) {
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
    DidChangeWatchedFilesParams other = (DidChangeWatchedFilesParams) obj;
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
    int result = 1;
    result = prime * result + ((this.changes== null) ? 0 : this.changes.hashCode());
    return result;
  }
}
