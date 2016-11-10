package org.eclipse.lsp4j;

import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A textual edit applicable to a text document.
 */
@SuppressWarnings("all")
public class TextEdit {
  /**
   * The range of the text document to be manipulated. To insert text into a document create a range where start === end.
   */
  @NonNull
  private Range range;
  
  /**
   * The string to be inserted. For delete operations use an empty string.
   */
  @NonNull
  private String newText;
  
  /**
   * The range of the text document to be manipulated. To insert text into a document create a range where start === end.
   */
  @Pure
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range of the text document to be manipulated. To insert text into a document create a range where start === end.
   */
  public void setRange(final Range range) {
    this.range = range;
  }
  
  /**
   * The string to be inserted. For delete operations use an empty string.
   */
  @Pure
  public String getNewText() {
    return this.newText;
  }
  
  /**
   * The string to be inserted. For delete operations use an empty string.
   */
  public void setNewText(final String newText) {
    this.newText = newText;
  }
  
  public TextEdit() {
    
  }
  
  public TextEdit(final Range range, final String newText) {
    this.range = range;
    this.newText = newText;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("newText", this.newText);
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
    TextEdit other = (TextEdit) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.newText == null) {
      if (other.newText != null)
        return false;
    } else if (!this.newText.equals(other.newText))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.newText== null) ? 0 : this.newText.hashCode());
    return result;
  }
}
