package org.eclipse.lsp4j;

import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A document link is a range in a text document that links to an internal or external resource, like another
 * text document or a web site.
 */
@SuppressWarnings("all")
public class DocumentLink {
  /**
   * The range this link applies to.
   */
  @NonNull
  private Range range;
  
  /**
   * The uri this link points to.
   */
  private String target;
  
  /**
   * The range this link applies to.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range this link applies to.
   */
  public void setRange(@NonNull final Range range) {
    this.range = range;
  }
  
  /**
   * The uri this link points to.
   */
  @Pure
  public String getTarget() {
    return this.target;
  }
  
  /**
   * The uri this link points to.
   */
  public void setTarget(final String target) {
    this.target = target;
  }
  
  public DocumentLink() {
    
  }
  
  public DocumentLink(final Range range, final String target) {
    this.range = range;
    this.target = target;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("target", this.target);
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
    DocumentLink other = (DocumentLink) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.target == null) {
      if (other.target != null)
        return false;
    } else if (!this.target.equals(other.target))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.target== null) ? 0 : this.target.hashCode());
    return result;
  }
}
