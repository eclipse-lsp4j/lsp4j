/**
 * Copyright (c) 2017, 2020 Kichwa Coders Ltd. and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.debug.ModuleEventArgumentsReason;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The event indicates that some information about a module has changed.
 */
@SuppressWarnings("all")
public class ModuleEventArguments {
  /**
   * The reason for the event.
   */
  @NonNull
  private ModuleEventArgumentsReason reason;
  
  /**
   * The new, changed, or removed module. In case of 'removed' only the module id is used.
   */
  @NonNull
  private org.eclipse.lsp4j.debug.Module module;
  
  /**
   * The reason for the event.
   */
  @Pure
  @NonNull
  public ModuleEventArgumentsReason getReason() {
    return this.reason;
  }
  
  /**
   * The reason for the event.
   */
  public void setReason(@NonNull final ModuleEventArgumentsReason reason) {
    this.reason = Preconditions.checkNotNull(reason, "reason");
  }
  
  /**
   * The new, changed, or removed module. In case of 'removed' only the module id is used.
   */
  @Pure
  @NonNull
  public org.eclipse.lsp4j.debug.Module getModule() {
    return this.module;
  }
  
  /**
   * The new, changed, or removed module. In case of 'removed' only the module id is used.
   */
  public void setModule(@NonNull final org.eclipse.lsp4j.debug.Module module) {
    this.module = Preconditions.checkNotNull(module, "module");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("reason", this.reason);
    b.add("module", this.module);
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
    ModuleEventArguments other = (ModuleEventArguments) obj;
    if (this.reason == null) {
      if (other.reason != null)
        return false;
    } else if (!this.reason.equals(other.reason))
      return false;
    if (this.module == null) {
      if (other.module != null)
        return false;
    } else if (!this.module.equals(other.module))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.reason== null) ? 0 : this.reason.hashCode());
    return prime * result + ((this.module== null) ? 0 : this.module.hashCode());
  }
}
