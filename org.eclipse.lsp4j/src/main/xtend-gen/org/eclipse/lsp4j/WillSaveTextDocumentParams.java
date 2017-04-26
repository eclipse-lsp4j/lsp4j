package org.eclipse.lsp4j;

import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentSaveReason;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class WillSaveTextDocumentParams {
  /**
   * The document that will be saved.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * A reason why a text document is saved.
   */
  @NonNull
  private TextDocumentSaveReason reason;
  
  public WillSaveTextDocumentParams() {
  }
  
  public WillSaveTextDocumentParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final TextDocumentSaveReason reason) {
    this.textDocument = textDocument;
    this.reason = reason;
  }
  
  /**
   * The document that will be saved.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The document that will be saved.
   */
  public void setTextDocument(@NonNull final TextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  /**
   * A reason why a text document is saved.
   */
  @Pure
  @NonNull
  public TextDocumentSaveReason getReason() {
    return this.reason;
  }
  
  /**
   * A reason why a text document is saved.
   */
  public void setReason(@NonNull final TextDocumentSaveReason reason) {
    this.reason = reason;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("reason", this.reason);
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
    WillSaveTextDocumentParams other = (WillSaveTextDocumentParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.reason == null) {
      if (other.reason != null)
        return false;
    } else if (!this.reason.equals(other.reason))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.reason== null) ? 0 : this.reason.hashCode());
    return result;
  }
}
