package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The result of a hover request.
 */
@SuppressWarnings("all")
public class Hover {
  /**
   * The hover's content as markdown
   */
  @NonNull
  private Either<Either<String, MarkedString>, List<Either<String, MarkedString>>> contents;
  
  /**
   * An optional range
   */
  private Range range;
  
  /**
   * The hover's content as markdown
   */
  @Pure
  @NonNull
  public Either<Either<String, MarkedString>, List<Either<String, MarkedString>>> getContents() {
    return this.contents;
  }
  
  /**
   * The hover's content as markdown
   */
  public void setContents(@NonNull final Either<Either<String, MarkedString>, List<Either<String, MarkedString>>> contents) {
    this.contents = contents;
  }
  
  public void setContents(final String contents) {
    final Either<String, MarkedString> _contents = Either.forLeft(contents);
    this.contents = Either.forLeft(_contents);
  }
  
  public void setContents(final MarkedString contents) {
    final Either<String, MarkedString> _contents = Either.forRight(contents);
    this.contents = Either.forLeft(_contents);
  }
  
  public void setContents(final List<Either<String, MarkedString>> contents) {
    this.contents = Either.forRight(contents);
  }
  
  /**
   * An optional range
   */
  @Pure
  public Range getRange() {
    return this.range;
  }
  
  /**
   * An optional range
   */
  public void setRange(final Range range) {
    this.range = range;
  }
  
  public Hover() {
    
  }
  
  public Hover(final Either<Either<String, MarkedString>, List<Either<String, MarkedString>>> contents, final Range range) {
    this.contents = contents;
    this.range = range;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("contents", this.contents);
    b.add("range", this.range);
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
    Hover other = (Hover) obj;
    if (this.contents == null) {
      if (other.contents != null)
        return false;
    } else if (!this.contents.equals(other.contents))
      return false;
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
    int result = 1;
    result = prime * result + ((this.contents== null) ? 0 : this.contents.hashCode());
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    return result;
  }
}
