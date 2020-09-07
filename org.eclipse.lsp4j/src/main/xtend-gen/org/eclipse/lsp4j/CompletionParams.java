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

import org.eclipse.lsp4j.CompletionContext;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionAndWorkDoneProgressAndPartialResultParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class CompletionParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
  /**
   * The completion context. This is only available it the client specifies
   * to send this using `ClientCapabilities.textDocument.completion.contextSupport === true`
   */
  private CompletionContext context;
  
  public CompletionParams() {
  }
  
  public CompletionParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position) {
    super(textDocument, position);
  }
  
  public CompletionParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position, final CompletionContext context) {
    super(textDocument, position);
    this.context = context;
  }
  
  /**
   * The completion context. This is only available it the client specifies
   * to send this using `ClientCapabilities.textDocument.completion.contextSupport === true`
   */
  @Pure
  public CompletionContext getContext() {
    return this.context;
  }
  
  /**
   * The completion context. This is only available it the client specifies
   * to send this using `ClientCapabilities.textDocument.completion.contextSupport === true`
   */
  public void setContext(final CompletionContext context) {
    this.context = context;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("context", this.context);
    b.add("partialResultToken", getPartialResultToken());
    b.add("workDoneToken", getWorkDoneToken());
    b.add("textDocument", getTextDocument());
    b.add("uri", getUri());
    b.add("position", getPosition());
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
    CompletionParams other = (CompletionParams) obj;
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
    return 31 * super.hashCode() + ((this.context== null) ? 0 : this.context.hashCode());
  }
}
