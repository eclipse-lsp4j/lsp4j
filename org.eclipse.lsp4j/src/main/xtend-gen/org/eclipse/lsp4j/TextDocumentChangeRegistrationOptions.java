/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.TextDocumentRegistrationOptions;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Describe options to be used when registered for text document change events.
 */
@SuppressWarnings("all")
public class TextDocumentChangeRegistrationOptions extends TextDocumentRegistrationOptions {
  /**
   * How documents are synced to the server. See TextDocumentSyncKind.Full
   * and TextDocumentSyncKind.Incremental.
   */
  @NonNull
  private TextDocumentSyncKind syncKind;
  
  public TextDocumentChangeRegistrationOptions() {
  }
  
  public TextDocumentChangeRegistrationOptions(@NonNull final TextDocumentSyncKind syncKind) {
    this.syncKind = syncKind;
  }
  
  /**
   * How documents are synced to the server. See TextDocumentSyncKind.Full
   * and TextDocumentSyncKind.Incremental.
   */
  @Pure
  @NonNull
  public TextDocumentSyncKind getSyncKind() {
    return this.syncKind;
  }
  
  /**
   * How documents are synced to the server. See TextDocumentSyncKind.Full
   * and TextDocumentSyncKind.Incremental.
   */
  public void setSyncKind(@NonNull final TextDocumentSyncKind syncKind) {
    this.syncKind = syncKind;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("syncKind", this.syncKind);
    b.add("documentSelector", getDocumentSelector());
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
    TextDocumentChangeRegistrationOptions other = (TextDocumentChangeRegistrationOptions) obj;
    if (this.syncKind == null) {
      if (other.syncKind != null)
        return false;
    } else if (!this.syncKind.equals(other.syncKind))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.syncKind== null) ? 0 : this.syncKind.hashCode());
    return result;
  }
}
