/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The document change notification is sent from the client to the server to signal changes to a text document.
 */
@SuppressWarnings("all")
public class DidChangeTextDocumentParams {
  /**
   * The document that did change. The version number points to the version after all provided content changes have
   * been applied.
   */
  @NonNull
  private VersionedTextDocumentIdentifier textDocument;
  
  /**
   * Legacy property to support protocol version 1.0 requests.
   */
  @Deprecated
  private String uri;
  
  /**
   * The actual content changes.
   */
  @NonNull
  private List<TextDocumentContentChangeEvent> contentChanges = new ArrayList<TextDocumentContentChangeEvent>();
  
  public DidChangeTextDocumentParams() {
  }
  
  public DidChangeTextDocumentParams(@NonNull final VersionedTextDocumentIdentifier textDocument, @NonNull final List<TextDocumentContentChangeEvent> contentChanges) {
    this.textDocument = textDocument;
    this.contentChanges = contentChanges;
  }
  
  @Deprecated
  public DidChangeTextDocumentParams(@NonNull final VersionedTextDocumentIdentifier textDocument, final String uri, @NonNull final List<TextDocumentContentChangeEvent> contentChanges) {
    this(textDocument, contentChanges);
    this.uri = uri;
  }
  
  /**
   * The document that did change. The version number points to the version after all provided content changes have
   * been applied.
   */
  @Pure
  @NonNull
  public VersionedTextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The document that did change. The version number points to the version after all provided content changes have
   * been applied.
   */
  public void setTextDocument(@NonNull final VersionedTextDocumentIdentifier textDocument) {
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
   * The actual content changes.
   */
  @Pure
  @NonNull
  public List<TextDocumentContentChangeEvent> getContentChanges() {
    return this.contentChanges;
  }
  
  /**
   * The actual content changes.
   */
  public void setContentChanges(@NonNull final List<TextDocumentContentChangeEvent> contentChanges) {
    this.contentChanges = contentChanges;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("uri", this.uri);
    b.add("contentChanges", this.contentChanges);
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
    DidChangeTextDocumentParams other = (DidChangeTextDocumentParams) obj;
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
    if (this.contentChanges == null) {
      if (other.contentChanges != null)
        return false;
    } else if (!this.contentChanges.equals(other.contentChanges))
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
    result = prime * result + ((this.contentChanges== null) ? 0 : this.contentChanges.hashCode());
    return result;
  }
}
