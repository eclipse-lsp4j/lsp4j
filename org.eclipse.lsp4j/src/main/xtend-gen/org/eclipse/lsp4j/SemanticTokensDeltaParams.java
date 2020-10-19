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

import com.google.common.annotations.Beta;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.WorkDoneProgressAndPartialResultParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The request is sent from the client to the server to resolve semantic token deltas for a given whole file.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class SemanticTokensDeltaParams extends WorkDoneProgressAndPartialResultParams {
  /**
   * The text document.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * The result id of a previous response. The result Id can either point to a full response
   * or a delta response depending on what was received last.
   */
  @NonNull
  private String previousResultId;
  
  public SemanticTokensDeltaParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final String previousResultId) {
    this.textDocument = Preconditions.<TextDocumentIdentifier>checkNotNull(textDocument, "textDocument");
    this.previousResultId = Preconditions.<String>checkNotNull(previousResultId, "previousResultId");
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
   * The result id of a previous response. The result Id can either point to a full response
   * or a delta response depending on what was received last.
   */
  @Pure
  @NonNull
  public String getPreviousResultId() {
    return this.previousResultId;
  }
  
  /**
   * The result id of a previous response. The result Id can either point to a full response
   * or a delta response depending on what was received last.
   */
  public void setPreviousResultId(@NonNull final String previousResultId) {
    this.previousResultId = Preconditions.checkNotNull(previousResultId, "previousResultId");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("previousResultId", this.previousResultId);
    b.add("workDoneToken", getWorkDoneToken());
    b.add("partialResultToken", getPartialResultToken());
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
    SemanticTokensDeltaParams other = (SemanticTokensDeltaParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.previousResultId == null) {
      if (other.previousResultId != null)
        return false;
    } else if (!this.previousResultId.equals(other.previousResultId))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    return prime * result + ((this.previousResultId== null) ? 0 : this.previousResultId.hashCode());
  }
}
