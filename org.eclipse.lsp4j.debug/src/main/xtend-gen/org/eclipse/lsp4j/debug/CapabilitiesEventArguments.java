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

import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.util.Preconditions;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The event indicates that one or more capabilities have changed.
 * <p>
 * Since the capabilities are dependent on the frontend and its UI, it might not be possible to change that at
 * random times (or too late).
 * <p>
 * Consequently this event has a hint characteristic: a frontend can only be expected to make a 'best effort' in
 * honouring individual capabilities but there are no guarantees.
 * <p>
 * Only changed capabilities need to be included, all other capabilities keep their values.
 */
@SuppressWarnings("all")
public class CapabilitiesEventArguments {
  /**
   * The set of updated capabilities.
   */
  @NonNull
  private Capabilities capabilities;
  
  /**
   * The set of updated capabilities.
   */
  @Pure
  @NonNull
  public Capabilities getCapabilities() {
    return this.capabilities;
  }
  
  /**
   * The set of updated capabilities.
   */
  public void setCapabilities(@NonNull final Capabilities capabilities) {
    this.capabilities = Preconditions.checkNotNull(capabilities, "capabilities");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("capabilities", this.capabilities);
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
    CapabilitiesEventArguments other = (CapabilitiesEventArguments) obj;
    if (this.capabilities == null) {
      if (other.capabilities != null)
        return false;
    } else if (!this.capabilities.equals(other.capabilities))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.capabilities== null) ? 0 : this.capabilities.hashCode());
  }
}
