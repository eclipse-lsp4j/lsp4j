package org.eclipse.lsp4j;

import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The document save notification is sent from the client to the server when the document for saved in the clinet.
 */
@SuppressWarnings("all")
public class DidSaveTextDocumentParams {
  /**
   * The document that was closed.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * Optional the content when saved. Depends on the includeText value
   * when the save notification was requested.
   */
  private String text;
  
  public DidSaveTextDocumentParams() {
  }
  
  public DidSaveTextDocumentParams(@NonNull final TextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  public DidSaveTextDocumentParams(@NonNull final TextDocumentIdentifier textDocument, final String text) {
    this(textDocument);
    this.text = text;
  }
  
  /**
   * The document that was closed.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The document that was closed.
   */
  public void setTextDocument(@NonNull final TextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  /**
   * Optional the content when saved. Depends on the includeText value
   * when the save notification was requested.
   */
  @Pure
  public String getText() {
    return this.text;
  }
  
  /**
   * Optional the content when saved. Depends on the includeText value
   * when the save notification was requested.
   */
  public void setText(final String text) {
    this.text = text;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
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
    DidSaveTextDocumentParams other = (DidSaveTextDocumentParams) obj;
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
    int result = 1;
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.text== null) ? 0 : this.text.hashCode());
    return result;
  }
}
