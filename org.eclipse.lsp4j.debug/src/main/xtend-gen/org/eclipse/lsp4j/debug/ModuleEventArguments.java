/**
 * Copyright (c) 2017 Kichwa Coders Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j.debug;

import org.eclipse.lsp4j.debug.Module;
import org.eclipse.lsp4j.debug.ModuleEventArgumentsReason;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event message for 'module' event type.
 * <p>
 * The event indicates that some information about a module has changed.
 */
@SuppressWarnings("all")
public class ModuleEventArguments {
  /**
   * The reason for the event.
   */
  private ModuleEventArgumentsReason reason;
  
  /**
   * The new, changed, or removed module. In case of 'removed' only the module id is used.
   */
  private Module module;
  
  /**
   * The reason for the event.
   */
  @Pure
  public ModuleEventArgumentsReason getReason() {
    return this.reason;
  }
  
  /**
   * The reason for the event.
   */
  public void setReason(final ModuleEventArgumentsReason reason) {
    this.reason = reason;
  }
  
  /**
   * The new, changed, or removed module. In case of 'removed' only the module id is used.
   */
  @Pure
  public Module getModule() {
    return this.module;
  }
  
  /**
   * The new, changed, or removed module. In case of 'removed' only the module id is used.
   */
  public void setModule(final Module module) {
    this.module = module;
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
    result = prime * result + ((this.module== null) ? 0 : this.module.hashCode());
    return result;
  }
}
