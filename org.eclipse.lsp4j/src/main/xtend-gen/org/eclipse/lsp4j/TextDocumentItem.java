package org.eclipse.lsp4j;

import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * An item to transfer a text document from the client to the server.
 */
@SuppressWarnings("all")
public class TextDocumentItem {
  /**
   * The text document's uri.
   */
  @NonNull
  private String uri;
  
  /**
   * The text document's language identifier
   */
  @NonNull
  private String languageId;
  
  /**
   * The version number of this document (it will strictly increase after each change, including undo/redo).
   */
  private int version;
  
  /**
   * The content of the opened  text document.
   */
  @NonNull
  private String text;
  
  public TextDocumentItem() {
  }
  
  public TextDocumentItem(@NonNull final String uri, @NonNull final String languageId, final int version, @NonNull final String text) {
    this.uri = uri;
    this.languageId = languageId;
    this.version = version;
    this.text = text;
  }
  
  /**
   * The text document's uri.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * The text document's uri.
   */
  public void setUri(@NonNull final String uri) {
    this.uri = uri;
  }
  
  /**
   * The text document's language identifier
   */
  @Pure
  @NonNull
  public String getLanguageId() {
    return this.languageId;
  }
  
  /**
   * The text document's language identifier
   */
  public void setLanguageId(@NonNull final String languageId) {
    this.languageId = languageId;
  }
  
  /**
   * The version number of this document (it will strictly increase after each change, including undo/redo).
   */
  @Pure
  public int getVersion() {
    return this.version;
  }
  
  /**
   * The version number of this document (it will strictly increase after each change, including undo/redo).
   */
  public void setVersion(final int version) {
    this.version = version;
  }
  
  /**
   * The content of the opened  text document.
   */
  @Pure
  @NonNull
  public String getText() {
    return this.text;
  }
  
  /**
   * The content of the opened  text document.
   */
  public void setText(@NonNull final String text) {
    this.text = text;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
    b.add("languageId", this.languageId);
    b.add("version", this.version);
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
    TextDocumentItem other = (TextDocumentItem) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.languageId == null) {
      if (other.languageId != null)
        return false;
    } else if (!this.languageId.equals(other.languageId))
      return false;
    if (other.version != this.version)
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
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    result = prime * result + ((this.languageId== null) ? 0 : this.languageId.hashCode());
    result = prime * result + this.version;
    result = prime * result + ((this.text== null) ? 0 : this.text.hashCode());
    return result;
  }
}
