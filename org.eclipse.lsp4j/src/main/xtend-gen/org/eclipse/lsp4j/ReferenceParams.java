package org.eclipse.lsp4j;

import org.eclipse.lsp4j.ReferenceContext;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The references request is sent from the client to the server to resolve project-wide references for the symbol
 * denoted by the given text document position.
 */
@SuppressWarnings("all")
public class ReferenceParams extends TextDocumentPositionParams {
  @NonNull
  private ReferenceContext context;
  
  @Pure
  @NonNull
  public ReferenceContext getContext() {
    return this.context;
  }
  
  public void setContext(@NonNull final ReferenceContext context) {
    this.context = context;
  }
  
  public ReferenceParams() {
    
  }
  
  public ReferenceParams(final ReferenceContext context) {
    this.context = context;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("context", this.context);
    b.add("textDocument", getTextDocument());
    b.add("uri", getUri());
    b.add("position", getPosition());
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
    ReferenceParams other = (ReferenceParams) obj;
    if (this.context == null) {
      if (other.context != null)
        return false;
    } else if (!this.context.equals(other.context))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.context== null) ? 0 : this.context.hashCode());
    return result;
  }
}
