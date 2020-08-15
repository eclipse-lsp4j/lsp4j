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

import java.util.List;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.WorkDoneProgressAndPartialResultParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * A parameter literal used in selection range requests.
 */
@SuppressWarnings("all")
public class SelectionRangeParams extends WorkDoneProgressAndPartialResultParams {
  /**
   * The text document.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * The positions inside the text document.
   */
  @NonNull
  private List<Position> positions;
  
  public SelectionRangeParams() {
  }
  
  public SelectionRangeParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final List<Position> positions) {
    this.textDocument = Preconditions.<TextDocumentIdentifier>checkNotNull(textDocument, "textDocument");
    this.positions = Preconditions.<List<Position>>checkNotNull(positions, "positions");
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
   * The positions inside the text document.
   */
  @Pure
  @NonNull
  public List<Position> getPositions() {
    return this.positions;
  }
  
  /**
   * The positions inside the text document.
   */
  public void setPositions(@NonNull final List<Position> positions) {
    this.positions = Preconditions.checkNotNull(positions, "positions");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("positions", this.positions);
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
    SelectionRangeParams other = (SelectionRangeParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.positions == null) {
      if (other.positions != null)
        return false;
    } else if (!this.positions.equals(other.positions))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    return prime * result + ((this.positions== null) ? 0 : this.positions.hashCode());
  }
}
