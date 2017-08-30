/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.FormattingOptions;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The document formatting request is sent from the server to the client to format a whole document.
 */
@SuppressWarnings("all")
public class DocumentFormattingParams {
  /**
   * The document to format.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * The format options
   */
  @NonNull
  private FormattingOptions options;
  
  public DocumentFormattingParams() {
  }
  
  public DocumentFormattingParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final FormattingOptions options) {
    this.textDocument = textDocument;
    this.options = options;
  }
  
  /**
   * The document to format.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The document to format.
   */
  public void setTextDocument(@NonNull final TextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  /**
   * The format options
   */
  @Pure
  @NonNull
  public FormattingOptions getOptions() {
    return this.options;
  }
  
  /**
   * The format options
   */
  public void setOptions(@NonNull final FormattingOptions options) {
    this.options = options;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("options", this.options);
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
    DocumentFormattingParams other = (DocumentFormattingParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.options == null) {
      if (other.options != null)
        return false;
    } else if (!this.options.equals(other.options))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.options== null) ? 0 : this.options.hashCode());
    return result;
  }
}
