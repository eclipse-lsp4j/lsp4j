package org.eclipse.lsp4j;

import org.eclipse.lsp4j.FileChangeType;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * An event describing a file change.
 */
@SuppressWarnings("all")
public class FileEvent {
  /**
   * The file's uri.
   */
  @NonNull
  private String uri;
  
  /**
   * The change type.
   */
  @NonNull
  private FileChangeType type;
  
  /**
   * The file's uri.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * The file's uri.
   */
  public void setUri(@NonNull final String uri) {
    this.uri = uri;
  }
  
  /**
   * The change type.
   */
  @Pure
  @NonNull
  public FileChangeType getType() {
    return this.type;
  }
  
  /**
   * The change type.
   */
  public void setType(@NonNull final FileChangeType type) {
    this.type = type;
  }
  
  public FileEvent() {
    
  }
  
  public FileEvent(final String uri, final FileChangeType type) {
    this.uri = uri;
    this.type = type;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
    b.add("type", this.type);
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
    FileEvent other = (FileEvent) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.type == null) {
      if (other.type != null)
        return false;
    } else if (!this.type.equals(other.type))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    result = prime * result + ((this.type== null) ? 0 : this.type.hashCode());
    return result;
  }
}
