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
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class InitializeResult {
  /**
   * The capabilities the language server provides.
   */
  @NonNull
  private ServerCapabilities capabilities;
  
  public InitializeResult() {
  }
  
  public InitializeResult(@NonNull final ServerCapabilities capabilities) {
    this.capabilities = Preconditions.<ServerCapabilities>checkNotNull(capabilities);
  }
  
  /**
   * The capabilities the language server provides.
   */
  @Pure
  @NonNull
  public ServerCapabilities getCapabilities() {
    return this.capabilities;
  }
  
  /**
   * The capabilities the language server provides.
   */
  public void setCapabilities(@NonNull final ServerCapabilities capabilities) {
    this.capabilities = capabilities;
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
    InitializeResult other = (InitializeResult) obj;
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
