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

import org.eclipse.lsp4j.DocumentFormattingParams;
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
public class DocumentRangeFormattingParams extends DocumentFormattingParams implements WorkDoneProgressParams {
  private Either<String, Number> workDoneToken;
  
  /**
   * The range to format
   */
  @NonNull
  private Range range;
  
  public DocumentRangeFormattingParams() {
  }
  
  public DocumentRangeFormattingParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final FormattingOptions options, @NonNull final Range range) {
    super(textDocument, options);
    this.range = Preconditions.<Range>checkNotNull(range, "range");
  }
  
  @Deprecated
  public DocumentRangeFormattingParams(@NonNull final Range range) {
    this.range = Preconditions.<Range>checkNotNull(range, "range");
  }
  
  @Pure
  public Either<String, Number> getWorkDoneToken() {
    return this.workDoneToken;
  }
  
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
    b.add("range", this.range);
    b.add("textDocument", getTextDocument());
    b.add("options", getOptions());
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
    DocumentRangeFormattingParams other = (DocumentRangeFormattingParams) obj;
    if (this.workDoneToken == null) {
      if (other.workDoneToken != null)
        return false;
    } else if (!this.workDoneToken.equals(other.workDoneToken))
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
    int result = super.hashCode();
    result = prime * result + ((this.workDoneToken== null) ? 0 : this.workDoneToken.hashCode());
    return prime * result + ((this.range== null) ? 0 : this.range.hashCode());
  }
}
