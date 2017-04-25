package org.eclipse.lsp4j;

import org.eclipse.lsp4j.DocumentSelector;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Since most of the registration options require to specify a document selector there is a base interface that can be used.
 */
@SuppressWarnings("all")
public class TextDocumentRegistrationOptions {
  /**
   * A document selector to identify the scope of the registration. If set to null
   * the document selector provided on the client side will be used.
   */
  private DocumentSelector documentSelector;
  
  public TextDocumentRegistrationOptions() {
  }
  
  public TextDocumentRegistrationOptions(final DocumentSelector documentSelector) {
    this.documentSelector = documentSelector;
  }
  
  /**
   * A document selector to identify the scope of the registration. If set to null
   * the document selector provided on the client side will be used.
   */
  @Pure
  public DocumentSelector getDocumentSelector() {
    return this.documentSelector;
  }
  
  /**
   * A document selector to identify the scope of the registration. If set to null
   * the document selector provided on the client side will be used.
   */
  public void setDocumentSelector(final DocumentSelector documentSelector) {
    this.documentSelector = documentSelector;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("documentSelector", this.documentSelector);
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
    TextDocumentRegistrationOptions other = (TextDocumentRegistrationOptions) obj;
    if (this.documentSelector == null) {
      if (other.documentSelector != null)
        return false;
    } else if (!this.documentSelector.equals(other.documentSelector))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.documentSelector== null) ? 0 : this.documentSelector.hashCode());
    return result;
  }
}
