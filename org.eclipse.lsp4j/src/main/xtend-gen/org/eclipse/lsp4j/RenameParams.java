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

import com.google.common.base.Preconditions;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The rename request is sent from the client to the server to do a workspace wide rename of a symbol.
 */
@SuppressWarnings("all")
public class RenameParams {
  /**
   * The document in which to find the symbol.
   */
  @NonNull
  private TextDocumentIdentifier textDocument;
  
  /**
   * The position at which this request was send.
   */
  @NonNull
  private Position position;
  
  /**
   * The new name of the symbol. If the given name is not valid the request must return a
   * ResponseError with an appropriate message set.
   */
  @NonNull
  private String newName;
  
  public RenameParams() {
  }
  
  public RenameParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position, @NonNull final String newName) {
    this.textDocument = Preconditions.<TextDocumentIdentifier>checkNotNull(textDocument);
    this.position = Preconditions.<Position>checkNotNull(position);
    this.newName = Preconditions.<String>checkNotNull(newName);
  }
  
  /**
   * The document in which to find the symbol.
   */
  @Pure
  @NonNull
  public TextDocumentIdentifier getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * The document in which to find the symbol.
   */
  public void setTextDocument(@NonNull final TextDocumentIdentifier textDocument) {
    this.textDocument = textDocument;
  }
  
  /**
   * The position at which this request was send.
   */
  @Pure
  @NonNull
  public Position getPosition() {
    return this.position;
  }
  
  /**
   * The position at which this request was send.
   */
  public void setPosition(@NonNull final Position position) {
    this.position = position;
  }
  
  /**
   * The new name of the symbol. If the given name is not valid the request must return a
   * ResponseError with an appropriate message set.
   */
  @Pure
  @NonNull
  public String getNewName() {
    return this.newName;
  }
  
  /**
   * The new name of the symbol. If the given name is not valid the request must return a
   * ResponseError with an appropriate message set.
   */
  public void setNewName(@NonNull final String newName) {
    this.newName = newName;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("textDocument", this.textDocument);
    b.add("position", this.position);
    b.add("newName", this.newName);
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
    RenameParams other = (RenameParams) obj;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.position == null) {
      if (other.position != null)
        return false;
    } else if (!this.position.equals(other.position))
      return false;
    if (this.newName == null) {
      if (other.newName != null)
        return false;
    } else if (!this.newName.equals(other.newName))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.position== null) ? 0 : this.position.hashCode());
    return prime * result + ((this.newName== null) ? 0 : this.newName.hashCode());
  }
}
