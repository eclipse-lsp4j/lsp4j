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
import org.eclipse.lsp4j.SignatureHelpContext;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionAndWorkDoneProgressParams;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The signature help request is sent from the client to the server to request signature information at a given cursor position.
 */
@SuppressWarnings("all")
public class SignatureHelpParams extends TextDocumentPositionAndWorkDoneProgressParams {
  /**
   * The signature help context. This is only available if the client specifies
   * to send this using the client capability  `textDocument.signatureHelp.contextSupport === true`
   * 
   * Since 3.15.0
   */
  private SignatureHelpContext context;
  
  public SignatureHelpParams() {
  }
  
  public SignatureHelpParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position) {
    super(textDocument, position);
  }
  
  public SignatureHelpParams(@NonNull final TextDocumentIdentifier textDocument, @NonNull final Position position, final SignatureHelpContext context) {
    super(textDocument, position);
    this.context = context;
  }
  
  /**
   * The signature help context. This is only available if the client specifies
   * to send this using the client capability  `textDocument.signatureHelp.contextSupport === true`
   * 
   * Since 3.15.0
   */
  @Pure
  public SignatureHelpContext getContext() {
    return this.context;
  }
  
  /**
   * The signature help context. This is only available if the client specifies
   * to send this using the client capability  `textDocument.signatureHelp.contextSupport === true`
   * 
   * Since 3.15.0
   */
  public void setContext(final SignatureHelpContext context) {
    this.context = context;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("context", this.context);
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
    SignatureHelpParams other = (SignatureHelpParams) obj;
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
