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
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.WorkDoneProgressParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Abstract class which extends TextDocumentPosition and implements work done progress request parameter.
 * It is not present in protocol specification, so it's just "dry" class.
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public abstract class TextDocumentPositionAndWorkDoneProgressParams extends TextDocumentPositionParams implements WorkDoneProgressParams {
  /**
   * An optional token that a server can use to report work done progress.
   */
  private Either<String, Integer> workDoneToken;
  
  public TextDocumentPositionAndWorkDoneProgressParams() {
  }
  
  public TextDocumentPositionAndWorkDoneProgressParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position) {
    super(textDocument, position);
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
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workDoneToken", this.workDoneToken);
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
    TextDocumentPositionAndWorkDoneProgressParams other = (TextDocumentPositionAndWorkDoneProgressParams) obj;
    if (this.workDoneToken == null) {
      if (other.workDoneToken != null)
        return false;
    } else if (!this.workDoneToken.equals(other.workDoneToken))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * super.hashCode() + ((this.workDoneToken== null) ? 0 : this.workDoneToken.hashCode());
  }
}
