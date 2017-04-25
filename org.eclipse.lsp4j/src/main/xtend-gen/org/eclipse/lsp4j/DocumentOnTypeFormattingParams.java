package org.eclipse.lsp4j;

import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The document on type formatting request is sent from the client to the server to format parts of the document during typing.
 */
@SuppressWarnings("all")
public class DocumentOnTypeFormattingParams extends DocumentFormattingParams {
  /**
   * The position at which this request was send.
   */
  @NonNull
  private Position position;
  
  /**
   * The character that has been typed.
   */
  @NonNull
  private String ch;
  
  public DocumentOnTypeFormattingParams() {
  }
  
  public DocumentOnTypeFormattingParams(final Position position, final String ch) {
    this.position = position;
    this.ch = ch;
  }
  
  /**
   * The position at which this request was send.
   */
  @Pure
  @NonNull
  public Position getPosition() {
    return this.position;
  }
  
  /**
   * The position at which this request was send.
   */
  public void setPosition(@NonNull final Position position) {
    this.position = position;
  }
  
  /**
   * The character that has been typed.
   */
  @Pure
  @NonNull
  public String getCh() {
    return this.ch;
  }
  
  /**
   * The character that has been typed.
   */
  public void setCh(@NonNull final String ch) {
    this.ch = ch;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("position", this.position);
    b.add("ch", this.ch);
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
    DocumentOnTypeFormattingParams other = (DocumentOnTypeFormattingParams) obj;
    if (this.position == null) {
      if (other.position != null)
        return false;
    } else if (!this.position.equals(other.position))
      return false;
    if (this.ch == null) {
      if (other.ch != null)
        return false;
    } else if (!this.ch.equals(other.ch))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.position== null) ? 0 : this.position.hashCode());
    result = prime * result + ((this.ch== null) ? 0 : this.ch.hashCode());
    return result;
  }
}
