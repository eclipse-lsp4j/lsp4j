package org.eclipse.lsp4j;

import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The document open notification is sent from the client to the server to signal newly opened text documents.
 * The document's truth is now managed by the client and the server must not try to read the document's truth using
 * the document's uri.
 */
@SuppressWarnings("all")
public class DidOpenTextDocumentParams extends TextDocumentIdentifier {
  /**
   * The document that was opened.
   */
  @NonNull
  private TextDocumentItem textDocument;
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Deprecated
  private String text;
  
  /**
   * The document that was opened.
   */
  @Pure
  public TextDocumentItem getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The document that was opened.
   */
  public void setTextDocument(final TextDocumentItem textDocument) {
    this.textDocument = textDocument;
  }
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Pure
  @Deprecated
  public String getText() {
    return this.text;
  }
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Deprecated
  public void setText(final String text) {
    this.text = text;
  }
  
  public DidOpenTextDocumentParams() {
    
  }
  
  public DidOpenTextDocumentParams(final TextDocumentItem textDocument, final String text) {
    this.textDocument = textDocument;
    this.text = text;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("text", this.text);
    b.add("uri", getUri());
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
    DidOpenTextDocumentParams other = (DidOpenTextDocumentParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
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
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.text== null) ? 0 : this.text.hashCode());
    return result;
  }
}
