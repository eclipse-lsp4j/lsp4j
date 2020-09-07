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

import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.WorkDoneProgressAndPartialResultParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The code action request is sent from the client to the server to compute commands for a given text document and range.
 * These commands are typically code fixes to either fix problems or to beautify/refactor code.
 */
@SuppressWarnings("all")
public class CodeActionParams extends WorkDoneProgressAndPartialResultParams {
  /**
   * The document in which the command was invoked.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * The range for which the command was invoked.
   */
  @NonNull
  private Range range;
  
  /**
   * Context carrying additional information.
   */
  @NonNull
  private CodeActionContext context;
  
  public CodeActionParams() {
  }
  
  public CodeActionParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Range range, @NonNull final CodeActionContext context) {
    this.textDocument = Preconditions.<TextDocumentIdentifier>checkNotNull(textDocument, "textDocument");
    this.range = Preconditions.<Range>checkNotNull(range, "range");
    this.context = Preconditions.<CodeActionContext>checkNotNull(context, "context");
  }
  
  /**
   * The document in which the command was invoked.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The document in which the command was invoked.
   */
  public void setTextDocument(@NonNull final TextDocumentIdentifier textDocument) {
    this.textDocument = Preconditions.checkNotNull(textDocument, "textDocument");
  }
  
  /**
   * The range for which the command was invoked.
   */
  @Pure
  @NonNull
  public Range getRange() {
    return this.range;
  }
  
  /**
   * The range for which the command was invoked.
   */
  public void setRange(@NonNull final Range range) {
    this.range = Preconditions.checkNotNull(range, "range");
  }
  
  /**
   * Context carrying additional information.
   */
  @Pure
  @NonNull
  public CodeActionContext getContext() {
    return this.context;
  }
  
  /**
   * Context carrying additional information.
   */
  public void setContext(@NonNull final CodeActionContext context) {
    this.context = Preconditions.checkNotNull(context, "context");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("range", this.range);
    b.add("context", this.context);
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
    CodeActionParams other = (CodeActionParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.context == null) {
      if (other.context != null)
        return false;
    } else if (!this.context.equals(other.context))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    return prime * result + ((this.context== null) ? 0 : this.context.hashCode());
  }
}
