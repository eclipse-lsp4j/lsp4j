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
import org.eclipse.lsp4j.TextDocumentPositionAndWorkDoneProgressParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The rename request is sent from the client to the server to do a workspace wide rename of a symbol.
 */
@SuppressWarnings("all")
public class RenameParams extends TextDocumentPositionAndWorkDoneProgressParams {
  /**
   * The new name of the symbol. If the given name is not valid the request must return a
   * ResponseError with an appropriate message set.
   */
  @NonNull
  private String newName;
  
  public RenameParams() {
  }
  
  public RenameParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position, @NonNull final String newName) {
    super(textDocument, position);
    this.newName = Preconditions.<String>checkNotNull(newName, "newName");
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
    this.newName = Preconditions.checkNotNull(newName, "newName");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("newName", this.newName);
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
    RenameParams other = (RenameParams) obj;
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
    return 31 * super.hashCode() + ((this.newName== null) ? 0 : this.newName.hashCode());
  }
}
