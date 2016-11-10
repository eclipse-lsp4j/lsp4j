package org.eclipse.lsp4j;

import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * An identifier to denote a specific version of a text document.
 */
@SuppressWarnings("all")
public class VersionedTextDocumentIdentifier extends TextDocumentIdentifier {
  /**
   * The version number of this document.
   */
  private int version;
  
  /**
   * The version number of this document.
   */
  @Pure
  public int getVersion() {
    return this.version;
  }
  
  /**
   * The version number of this document.
   */
  public void setVersion(final int version) {
    this.version = version;
  }
  
  public VersionedTextDocumentIdentifier() {
    
  }
  
  public VersionedTextDocumentIdentifier(final int version) {
    this.version = version;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("version", this.version);
    b.add("uri", getUri());
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
    VersionedTextDocumentIdentifier other = (VersionedTextDocumentIdentifier) obj;
    if (other.version != this.version)
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + this.version;
    return result;
  }
}
