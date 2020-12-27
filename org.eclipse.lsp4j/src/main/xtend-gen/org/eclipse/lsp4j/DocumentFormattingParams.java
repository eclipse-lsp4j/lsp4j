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

import org.eclipse.lsp4j.FormattingOptions;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.WorkDoneProgressParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The document formatting request is sent from the server to the client to format a whole document.
 */
@SuppressWarnings("all")
public class DocumentFormattingParams implements WorkDoneProgressParams {
  /**
   * An optional token that a server can use to report work done progress.
   */
  private Either<String, Integer> workDoneToken;
  
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
    this.textDocument = Preconditions.<TextDocumentIdentifier>checkNotNull(textDocument, "textDocument");
    this.options = Preconditions.<FormattingOptions>checkNotNull(options, "options");
  }
  
  /**
   * An optional token that a server can use to report work done progress.
   */
  @Pure
  public Either<String, Integer> getWorkDoneToken() {
    return this.workDoneToken;
  }
  
  /**
   * An optional token that a server can use to report work done progress.
   */
  public void setWorkDoneToken(final Either<String, Integer> workDoneToken) {
    this.workDoneToken = workDoneToken;
  }
  
  public void setWorkDoneToken(final String workDoneToken) {
    if (workDoneToken == null) {
      this.workDoneToken = null;
      return;
    }
    this.workDoneToken = Either.forLeft(workDoneToken);
  }
  
  public void setWorkDoneToken(final Integer workDoneToken) {
    if (workDoneToken == null) {
      this.workDoneToken = null;
      return;
    }
    this.workDoneToken = Either.forRight(workDoneToken);
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
    this.textDocument = Preconditions.checkNotNull(textDocument, "textDocument");
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
    this.options = Preconditions.checkNotNull(options, "options");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workDoneToken", this.workDoneToken);
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
    if (this.workDoneToken == null) {
      if (other.workDoneToken != null)
        return false;
    } else if (!this.workDoneToken.equals(other.workDoneToken))
      return false;
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
    result = prime * result + ((this.workDoneToken== null) ? 0 : this.workDoneToken.hashCode());
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    return prime * result + ((this.options== null) ? 0 : this.options.hashCode());
  }
}
