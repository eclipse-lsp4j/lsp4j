package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Text documents are identified using an URI. On the protocol level URI's are passed as strings.
 */
@SuppressWarnings("all")
public class TextDocumentIdentifier {
  /**
   * The text document's uri.
   */
  @NonNull
  private String uri;
  
  public TextDocumentIdentifier() {
  }
  
  public TextDocumentIdentifier(@NonNull final String uri) {
    this.uri = uri;
  }
  
  /**
   * The text document's uri.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * The text document's uri.
   */
  public void setUri(@NonNull final String uri) {
    this.uri = uri;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
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
    TextDocumentIdentifier other = (TextDocumentIdentifier) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    return result;
  }
}
