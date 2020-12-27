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

import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureHelpTriggerKind;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Additional information about the context in which a signature help request was triggered.
 * 
 * Since 3.15.0
 */
@SuppressWarnings("all")
public class SignatureHelpContext {
  /**
   * Action that caused signature help to be triggered.
   */
  @NonNull
  private SignatureHelpTriggerKind triggerKind;
  
  /**
   * Character that caused signature help to be triggered.
   * 
   * This is undefined when `triggerKind !== SignatureHelpTriggerKind.TriggerCharacter`
   */
  private String triggerCharacter;
  
  /**
   * {@code true} if signature help was already showing when it was triggered.
   * 
   * Retriggers occur when the signature help is already active and can be caused by actions such as
   * typing a trigger character, a cursor move, or document content changes.
   */
  private boolean isRetrigger;
  
  /**
   * The currently active `SignatureHelp`.
   * 
   * The `activeSignatureHelp` has its `SignatureHelp.activeSignature` field updated based on
   * the user navigating through available signatures.
   */
  private SignatureHelp activeSignatureHelp;
  
  public SignatureHelpContext() {
  }
  
  public SignatureHelpContext(@NonNull final SignatureHelpTriggerKind triggerKind, final boolean isRetrigger) {
    this.triggerKind = Preconditions.<SignatureHelpTriggerKind>checkNotNull(triggerKind, "triggerKind");
    this.isRetrigger = isRetrigger;
  }
  
  /**
   * Action that caused signature help to be triggered.
   */
  @Pure
  @NonNull
  public SignatureHelpTriggerKind getTriggerKind() {
    return this.triggerKind;
  }
  
  /**
   * Action that caused signature help to be triggered.
   */
  public void setTriggerKind(@NonNull final SignatureHelpTriggerKind triggerKind) {
    this.triggerKind = Preconditions.checkNotNull(triggerKind, "triggerKind");
  }
  
  /**
   * Character that caused signature help to be triggered.
   * 
   * This is undefined when `triggerKind !== SignatureHelpTriggerKind.TriggerCharacter`
   */
  @Pure
  public String getTriggerCharacter() {
    return this.triggerCharacter;
  }
  
  /**
   * Character that caused signature help to be triggered.
   * 
   * This is undefined when `triggerKind !== SignatureHelpTriggerKind.TriggerCharacter`
   */
  public void setTriggerCharacter(final String triggerCharacter) {
    this.triggerCharacter = triggerCharacter;
  }
  
  /**
   * {@code true} if signature help was already showing when it was triggered.
   * 
   * Retriggers occur when the signature help is already active and can be caused by actions such as
   * typing a trigger character, a cursor move, or document content changes.
   */
  @Pure
  public boolean isRetrigger() {
    return this.isRetrigger;
  }
  
  /**
   * {@code true} if signature help was already showing when it was triggered.
   * 
   * Retriggers occur when the signature help is already active and can be caused by actions such as
   * typing a trigger character, a cursor move, or document content changes.
   */
  public void setIsRetrigger(final boolean isRetrigger) {
    this.isRetrigger = isRetrigger;
  }
  
  /**
   * The currently active `SignatureHelp`.
   * 
   * The `activeSignatureHelp` has its `SignatureHelp.activeSignature` field updated based on
   * the user navigating through available signatures.
   */
  @Pure
  public SignatureHelp getActiveSignatureHelp() {
    return this.activeSignatureHelp;
  }
  
  /**
   * The currently active `SignatureHelp`.
   * 
   * The `activeSignatureHelp` has its `SignatureHelp.activeSignature` field updated based on
   * the user navigating through available signatures.
   */
  public void setActiveSignatureHelp(final SignatureHelp activeSignatureHelp) {
    this.activeSignatureHelp = activeSignatureHelp;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("triggerKind", this.triggerKind);
    b.add("triggerCharacter", this.triggerCharacter);
    b.add("isRetrigger", this.isRetrigger);
    b.add("activeSignatureHelp", this.activeSignatureHelp);
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
    SignatureHelpContext other = (SignatureHelpContext) obj;
    if (this.triggerKind == null) {
      if (other.triggerKind != null)
        return false;
    } else if (!this.triggerKind.equals(other.triggerKind))
      return false;
    if (this.triggerCharacter == null) {
      if (other.triggerCharacter != null)
        return false;
    } else if (!this.triggerCharacter.equals(other.triggerCharacter))
      return false;
    if (other.isRetrigger != this.isRetrigger)
      return false;
    if (this.activeSignatureHelp == null) {
      if (other.activeSignatureHelp != null)
        return false;
    } else if (!this.activeSignatureHelp.equals(other.activeSignatureHelp))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.triggerKind== null) ? 0 : this.triggerKind.hashCode());
    result = prime * result + ((this.triggerCharacter== null) ? 0 : this.triggerCharacter.hashCode());
    result = prime * result + (this.isRetrigger ? 1231 : 1237);
    return prime * result + ((this.activeSignatureHelp== null) ? 0 : this.activeSignatureHelp.hashCode());
  }
}
