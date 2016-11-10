package org.eclipse.lsp4j;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A parameter literal used in requests to pass a text document and a position inside that document.
 */
@SuppressWarnings("all")
public class TextDocumentPositionParams {
  /**
   * The text document.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Deprecated
  private String uri;
  
  /**
   * The position inside the text document.
   */
  @NonNull
  private Position position;
  
  /**
   * The text document.
   */
  @Pure
  public TextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The text document.
   */
  public void setTextDocument(final TextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Pure
  @Deprecated
  public String getUri() {
    return this.uri;
  }
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Deprecated
  public void setUri(final String uri) {
    this.uri = uri;
  }
  
  /**
   * The position inside the text document.
   */
  @Pure
  public Position getPosition() {
    return this.position;
  }
  
  /**
   * The position inside the text document.
   */
  public void setPosition(final Position position) {
    this.position = position;
  }
  
  public TextDocumentPositionParams() {
    
  }
  
  public TextDocumentPositionParams(final TextDocumentIdentifier textDocument, final String uri, final Position position) {
    this.textDocument = textDocument;
    this.uri = uri;
    this.position = position;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("uri", this.uri);
    b.add("position", this.position);
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
    TextDocumentPositionParams other = (TextDocumentPositionParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.position == null) {
      if (other.position != null)
        return false;
    } else if (!this.position.equals(other.position))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    result = prime * result + ((this.position== null) ? 0 : this.position.hashCode());
    return result;
  }
}
