/**
 * Copyright (c) 2016-2018 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
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
  
  public TextDocumentPositionParams() {
  }
  
  public TextDocumentPositionParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position) {
    this.textDocument = Preconditions.<TextDocumentIdentifier>checkNotNull(textDocument, "textDocument");
    this.position = Preconditions.<Position>checkNotNull(position, "position");
  }
  
  @Deprecated
  public TextDocumentPositionParams(@NonNull final TextDocumentIdentifier textDocument, final String uri, @NonNull final Position position) {
    this.textDocument = Preconditions.<TextDocumentIdentifier>checkNotNull(textDocument, "textDocument");
    this.uri = uri;
    this.position = Preconditions.<Position>checkNotNull(position, "position");
  }
  
  /**
   * The text document.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The text document.
   */
  public void setTextDocument(@NonNull final TextDocumentIdentifier textDocument) {
    this.textDocument = Preconditions.checkNotNull(textDocument, "textDocument");
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
  @NonNull
  public Position getPosition() {
    return this.position;
  }
  
  /**
   * The position inside the text document.
   */
  public void setPosition(@NonNull final Position position) {
    this.position = Preconditions.checkNotNull(position, "position");
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
    int result = 1;
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    return prime * result + ((this.position== null) ? 0 : this.position.hashCode());
  }
}
