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

import org.eclipse.lsp4j.PartialResultParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionAndWorkDoneProgressParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Abstract class which extends TextDocumentPosition and implements work done progress and partial result request parameter.
 * It is not present in protocol specification, so it's just "dry" class.
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public abstract class TextDocumentPositionAndWorkDoneProgressAndPartialResultParams extends TextDocumentPositionAndWorkDoneProgressParams implements PartialResultParams {
  private Either<String, Number> partialResultToken;
  
  public TextDocumentPositionAndWorkDoneProgressAndPartialResultParams() {
  }
  
  public TextDocumentPositionAndWorkDoneProgressAndPartialResultParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position) {
    super(textDocument, position);
  }
  
  @Deprecated
  public TextDocumentPositionAndWorkDoneProgressAndPartialResultParams(@NonNull final TextDocumentIdentifier textDocument, final String uri, @NonNull final Position position) {
    super(textDocument, uri, position);
  }
  
  @Pure
  public Either<String, Number> getPartialResultToken() {
    return this.partialResultToken;
  }
  
  public void setPartialResultToken(final Either<String, Number> partialResultToken) {
    this.partialResultToken = partialResultToken;
  }
  
  public void setPartialResultToken(final String partialResultToken) {
    if (partialResultToken == null) {
      this.partialResultToken = null;
      return;
    }
    this.partialResultToken = Either.forLeft(partialResultToken);
  }
  
  public void setPartialResultToken(final Number partialResultToken) {
    if (partialResultToken == null) {
      this.partialResultToken = null;
      return;
    }
    this.partialResultToken = Either.forRight(partialResultToken);
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("partialResultToken", this.partialResultToken);
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
    TextDocumentPositionAndWorkDoneProgressAndPartialResultParams other = (TextDocumentPositionAndWorkDoneProgressAndPartialResultParams) obj;
    if (this.partialResultToken == null) {
      if (other.partialResultToken != null)
        return false;
    } else if (!this.partialResultToken.equals(other.partialResultToken))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.partialResultToken== null) ? 0 : this.partialResultToken.hashCode());
  }
}
