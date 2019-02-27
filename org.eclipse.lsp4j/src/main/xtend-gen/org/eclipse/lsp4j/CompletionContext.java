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

import org.eclipse.lsp4j.CompletionTriggerKind;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class CompletionContext {
  /**
   * How the completion was triggered.
   */
  @NonNull
  private CompletionTriggerKind triggerKind;
  
  /**
   * The trigger character (a single character) that has trigger code complete.
   * Is undefined if `triggerKind !== CompletionTriggerKind.TriggerCharacter`
   */
  private String triggerCharacter;
  
  public CompletionContext() {
  }
  
  public CompletionContext(@NonNull final CompletionTriggerKind triggerKind) {
    this.triggerKind = Preconditions.<CompletionTriggerKind>checkNotNull(triggerKind, "triggerKind");
  }
  
  public CompletionContext(@NonNull final CompletionTriggerKind triggerKind, final String triggerCharacter) {
    this(triggerKind);
    this.triggerCharacter = triggerCharacter;
  }
  
  /**
   * How the completion was triggered.
   */
  @Pure
  @NonNull
  public CompletionTriggerKind getTriggerKind() {
    return this.triggerKind;
  }
  
  /**
   * How the completion was triggered.
   */
  public void setTriggerKind(@NonNull final CompletionTriggerKind triggerKind) {
    if (triggerKind == null) {
      throw new IllegalArgumentException("Property must not be null: triggerKind");
    }
    this.triggerKind = triggerKind;
  }
  
  /**
   * The trigger character (a single character) that has trigger code complete.
   * Is undefined if `triggerKind !== CompletionTriggerKind.TriggerCharacter`
   */
  @Pure
  public String getTriggerCharacter() {
    return this.triggerCharacter;
  }
  
  /**
   * The trigger character (a single character) that has trigger code complete.
   * Is undefined if `triggerKind !== CompletionTriggerKind.TriggerCharacter`
   */
  public void setTriggerCharacter(final String triggerCharacter) {
    this.triggerCharacter = triggerCharacter;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("triggerKind", this.triggerKind);
    b.add("triggerCharacter", this.triggerCharacter);
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
    CompletionContext other = (CompletionContext) obj;
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
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.triggerKind== null) ? 0 : this.triggerKind.hashCode());
    return prime * result + ((this.triggerCharacter== null) ? 0 : this.triggerCharacter.hashCode());
  }
}
