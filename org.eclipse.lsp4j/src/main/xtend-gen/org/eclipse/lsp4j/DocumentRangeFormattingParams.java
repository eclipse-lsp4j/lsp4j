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
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.WorkDoneProgressParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The document range formatting request is sent from the client to the server to format a given range in a document.
 */
@SuppressWarnings("all")
public class DocumentRangeFormattingParams implements WorkDoneProgressParams {
  /**
   * An optional token that a server can use to report work done progress.
   */
  private Either<String, Number> workDoneToken;
  
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
  
  /**
   * The range to format
   */
  @NonNull
  private Range range;
  
  public DocumentRangeFormattingParams() {
  }
  
  public DocumentRangeFormattingParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final FormattingOptions options, @NonNull final Range range) {
    this.textDocument = Preconditions.<TextDocumentIdentifier>checkNotNull(textDocument, "textDocument");
    this.options = Preconditions.<FormattingOptions>checkNotNull(options, "options");
    this.range = Preconditions.<Range>checkNotNull(range, "range");
  }
  
  @Deprecated
  public DocumentRangeFormattingParams(@NonNull final Range range) {
    this.range = Preconditions.<Range>checkNotNull(range, "range");
  }
  
  /**
   * An optional token that a server can use to report work done progress.
   */
  @Pure
  public Either<String, Number> getWorkDoneToken() {
    return this.workDoneToken;
  }
  
  /**
   * An optional token that a server can use to report work done progress.
   */
  public void setWorkDoneToken(final Either<String, Number> workDoneToken) {
    this.workDoneToken = workDoneToken;
  }
  
  public void setWorkDoneToken(final String workDoneToken) {
    if (workDoneToken == null) {
      this.workDoneToken = null;
      return;
    }
    this.workDoneToken = Either.forLeft(workDoneToken);
  }
  
  public void setWorkDoneToken(final Number workDoneToken) {
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
  
  /**
   * The range to format
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range to format
   */
  public void setRange(@NonNull final Range range) {
    this.range = Preconditions.checkNotNull(range, "range");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workDoneToken", this.workDoneToken);
    b.add("textDocument", this.textDocument);
    b.add("options", this.options);
    b.add("range", this.range);
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
    DocumentRangeFormattingParams other = (DocumentRangeFormattingParams) obj;
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
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
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
    result = prime * result + ((this.options== null) ? 0 : this.options.hashCode());
    return prime * result + ((this.range== null) ? 0 : this.range.hashCode());
  }
}
