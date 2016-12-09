package org.eclipse.lsp4j;

import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The code lens request is sent from the client to the server to compute code lenses for a given text document.
 */
@SuppressWarnings("all")
public class CodeLensParams {
  /**
   * The document to request code lens for.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * The document to request code lens for.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The document to request code lens for.
   */
  public void setTextDocument(@NonNull final TextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  public CodeLensParams() {
    
  }
  
  public CodeLensParams(final TextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
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
    CodeLensParams other = (CodeLensParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    return result;
  }
}
