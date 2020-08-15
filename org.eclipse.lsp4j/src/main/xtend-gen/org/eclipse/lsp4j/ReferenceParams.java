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
import org.eclipse.lsp4j.ReferenceContext;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionAndWorkDoneProgressAndPartialResultParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The references request is sent from the client to the server to resolve project-wide references for the symbol
 * denoted by the given text document position.
 */
@SuppressWarnings("all")
public class ReferenceParams extends TextDocumentPositionAndWorkDoneProgressAndPartialResultParams {
  @NonNull
  private ReferenceContext context;
  
  public ReferenceParams() {
  }
  
  public ReferenceParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position, @NonNull final ReferenceContext context) {
    super(textDocument, position);
    this.context = Preconditions.<ReferenceContext>checkNotNull(context, "context");
  }
  
  @Deprecated
  public ReferenceParams(@NonNull final ReferenceContext context) {
    this.context = Preconditions.<ReferenceContext>checkNotNull(context, "context");
  }
  
  @Pure
  @NonNull
  public ReferenceContext getContext() {
    return this.context;
  }
  
  public void setContext(@NonNull final ReferenceContext context) {
    this.context = Preconditions.checkNotNull(context, "context");
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
    ReferenceParams other = (ReferenceParams) obj;
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
