package org.eclipse.lsp4j;

import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The document range formatting request is sent from the client to the server to format a given range in a document.
 */
@SuppressWarnings("all")
public class DocumentRangeFormattingParams extends DocumentFormattingParams {
  /**
   * The range to format
   */
  @NonNull
  private Range range;
  
  /**
   * The range to format
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range to format
   */
  public void setRange(@NonNull final Range range) {
    this.range = range;
  }
  
  public DocumentRangeFormattingParams() {
    
  }
  
  public DocumentRangeFormattingParams(final Range range) {
    this.range = range;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("textDocument", getTextDocument());
    b.add("options", getOptions());
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
    DocumentRangeFormattingParams other = (DocumentRangeFormattingParams) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    return result;
  }
}
