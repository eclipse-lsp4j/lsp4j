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

import org.eclipse.lsp4j.SaveOptions;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class TextDocumentSyncOptions {
  /**
   * Open and close notifications are sent to the server.
   */
  private Boolean openClose;
  
  /**
   * Change notifications are sent to the server. See TextDocumentSyncKind.None, TextDocumentSyncKind.Full
   * and TextDocumentSyncKind.Incremental.
   */
  private TextDocumentSyncKind change;
  
  /**
   * Will save notifications are sent to the server.
   */
  private Boolean willSave;
  
  /**
   * Will save wait until requests are sent to the server.
   */
  private Boolean willSaveWaitUntil;
  
  /**
   * Save notifications are sent to the server.
   */
  private Either<Boolean, SaveOptions> save;
  
  /**
   * Open and close notifications are sent to the server.
   */
  @Pure
  public Boolean getOpenClose() {
    return this.openClose;
  }
  
  /**
   * Open and close notifications are sent to the server.
   */
  public void setOpenClose(final Boolean openClose) {
    this.openClose = openClose;
  }
  
  /**
   * Change notifications are sent to the server. See TextDocumentSyncKind.None, TextDocumentSyncKind.Full
   * and TextDocumentSyncKind.Incremental.
   */
  @Pure
  public TextDocumentSyncKind getChange() {
    return this.change;
  }
  
  /**
   * Change notifications are sent to the server. See TextDocumentSyncKind.None, TextDocumentSyncKind.Full
   * and TextDocumentSyncKind.Incremental.
   */
  public void setChange(final TextDocumentSyncKind change) {
    this.change = change;
  }
  
  /**
   * Will save notifications are sent to the server.
   */
  @Pure
  public Boolean getWillSave() {
    return this.willSave;
  }
  
  /**
   * Will save notifications are sent to the server.
   */
  public void setWillSave(final Boolean willSave) {
    this.willSave = willSave;
  }
  
  /**
   * Will save wait until requests are sent to the server.
   */
  @Pure
  public Boolean getWillSaveWaitUntil() {
    return this.willSaveWaitUntil;
  }
  
  /**
   * Will save wait until requests are sent to the server.
   */
  public void setWillSaveWaitUntil(final Boolean willSaveWaitUntil) {
    this.willSaveWaitUntil = willSaveWaitUntil;
  }
  
  /**
   * Save notifications are sent to the server.
   */
  @Pure
  public Either<Boolean, SaveOptions> getSave() {
    return this.save;
  }
  
  /**
   * Save notifications are sent to the server.
   */
  public void setSave(final Either<Boolean, SaveOptions> save) {
    this.save = save;
  }
  
  public void setSave(final Boolean save) {
    if (save == null) {
      this.save = null;
      return;
    }
    this.save = Either.forLeft(save);
  }
  
  public void setSave(final SaveOptions save) {
    if (save == null) {
      this.save = null;
      return;
    }
    this.save = Either.forRight(save);
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("openClose", this.openClose);
    b.add("change", this.change);
    b.add("willSave", this.willSave);
    b.add("willSaveWaitUntil", this.willSaveWaitUntil);
    b.add("save", this.save);
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
    TextDocumentSyncOptions other = (TextDocumentSyncOptions) obj;
    if (this.openClose == null) {
      if (other.openClose != null)
        return false;
    } else if (!this.openClose.equals(other.openClose))
      return false;
    if (this.change == null) {
      if (other.change != null)
        return false;
    } else if (!this.change.equals(other.change))
      return false;
    if (this.willSave == null) {
      if (other.willSave != null)
        return false;
    } else if (!this.willSave.equals(other.willSave))
      return false;
    if (this.willSaveWaitUntil == null) {
      if (other.willSaveWaitUntil != null)
        return false;
    } else if (!this.willSaveWaitUntil.equals(other.willSaveWaitUntil))
      return false;
    if (this.save == null) {
      if (other.save != null)
        return false;
    } else if (!this.save.equals(other.save))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.openClose== null) ? 0 : this.openClose.hashCode());
    result = prime * result + ((this.change== null) ? 0 : this.change.hashCode());
    result = prime * result + ((this.willSave== null) ? 0 : this.willSave.hashCode());
    result = prime * result + ((this.willSaveWaitUntil== null) ? 0 : this.willSaveWaitUntil.hashCode());
    return prime * result + ((this.save== null) ? 0 : this.save.hashCode());
  }
}
