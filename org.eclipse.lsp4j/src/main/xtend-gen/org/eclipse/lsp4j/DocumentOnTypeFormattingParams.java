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
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The document on type formatting request is sent from the client to the server to format parts of the document during typing.
 */
@SuppressWarnings("all")
public class DocumentOnTypeFormattingParams extends DocumentFormattingParams {
  /**
   * The position at which this request was send.
   */
  @NonNull
  private Position position;
  
  /**
   * The character that has been typed.
   */
  @NonNull
  private String ch;
  
  public DocumentOnTypeFormattingParams() {
  }
  
  public DocumentOnTypeFormattingParams(@NonNull final Position position, @NonNull final String ch) {
    this.position = Preconditions.<Position>checkNotNull(position);
    this.ch = ch;
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
   * The character that has been typed.
   */
  @Pure
  @NonNull
  public String getCh() {
    return this.ch;
  }
  
  /**
   * The character that has been typed.
   */
  public void setCh(@NonNull final String ch) {
    this.ch = ch;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("position", this.position);
    b.add("ch", this.ch);
    b.add("textDocument", getTextDocument());
    b.add("options", getOptions());
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
    DocumentOnTypeFormattingParams other = (DocumentOnTypeFormattingParams) obj;
    if (this.position == null) {
      if (other.position != null)
        return false;
    } else if (!this.position.equals(other.position))
      return false;
    if (this.ch == null) {
      if (other.ch != null)
        return false;
    } else if (!this.ch.equals(other.ch))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.position== null) ? 0 : this.position.hashCode());
    return prime * result + ((this.ch== null) ? 0 : this.ch.hashCode());
  }
}
