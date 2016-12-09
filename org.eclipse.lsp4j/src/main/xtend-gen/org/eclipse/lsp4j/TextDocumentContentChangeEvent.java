package org.eclipse.lsp4j;

import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * An event describing a change to a text document. If range and rangeLength are omitted the new text is considered
 * to be the full content of the document.
 */
@SuppressWarnings("all")
public class TextDocumentContentChangeEvent {
  /**
   * The range of the document that changed.
   */
  private Range range;
  
  /**
   * The length of the range that got replaced.
   */
  private Integer rangeLength;
  
  /**
   * The new text of the document.
   */
  @NonNull
  private String text;
  
  /**
   * The range of the document that changed.
   */
  @Pure
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range of the document that changed.
   */
  public void setRange(final Range range) {
    this.range = range;
  }
  
  /**
   * The length of the range that got replaced.
   */
  @Pure
  public Integer getRangeLength() {
    return this.rangeLength;
  }
  
  /**
   * The length of the range that got replaced.
   */
  public void setRangeLength(final Integer rangeLength) {
    this.rangeLength = rangeLength;
  }
  
  /**
   * The new text of the document.
   */
  @Pure
  @NonNull
  public String getText() {
    return this.text;
  }
  
  /**
   * The new text of the document.
   */
  public void setText(@NonNull final String text) {
    this.text = text;
  }
  
  public TextDocumentContentChangeEvent() {
    
  }
  
  public TextDocumentContentChangeEvent(final Range range, final Integer rangeLength, final String text) {
    this.range = range;
    this.rangeLength = rangeLength;
    this.text = text;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("rangeLength", this.rangeLength);
    b.add("text", this.text);
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
    TextDocumentContentChangeEvent other = (TextDocumentContentChangeEvent) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.rangeLength == null) {
      if (other.rangeLength != null)
        return false;
    } else if (!this.rangeLength.equals(other.rangeLength))
      return false;
    if (this.text == null) {
      if (other.text != null)
        return false;
    } else if (!this.text.equals(other.text))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    result = prime * result + ((this.rangeLength== null) ? 0 : this.rangeLength.hashCode());
    result = prime * result + ((this.text== null) ? 0 : this.text.hashCode());
    return result;
  }
}
