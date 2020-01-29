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

import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.ServerInfo;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class InitializeResult {
  /**
   * The capabilities the language server provides.
   */
  @NonNull
  private ServerCapabilities capabilities;
  
  /**
   * Information about the server.
   * 
   * Since 3.15.0
   */
  private ServerInfo serverInfo;
  
  public InitializeResult() {
  }
  
  public InitializeResult(@NonNull final ServerCapabilities capabilities) {
    this.capabilities = Preconditions.<ServerCapabilities>checkNotNull(capabilities, "capabilities");
  }
  
  public InitializeResult(@NonNull final ServerCapabilities capabilities, final ServerInfo serverInfo) {
    this(capabilities);
    this.serverInfo = serverInfo;
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
    this.capabilities = Preconditions.checkNotNull(capabilities, "capabilities");
  }
  
  /**
   * Information about the server.
   * 
   * Since 3.15.0
   */
  @Pure
  public ServerInfo getServerInfo() {
    return this.serverInfo;
  }
  
  /**
   * Information about the server.
   * 
   * Since 3.15.0
   */
  public void setServerInfo(final ServerInfo serverInfo) {
    this.serverInfo = serverInfo;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("capabilities", this.capabilities);
    b.add("serverInfo", this.serverInfo);
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
    if (this.serverInfo == null) {
      if (other.serverInfo != null)
        return false;
    } else if (!this.serverInfo.equals(other.serverInfo))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.capabilities== null) ? 0 : this.capabilities.hashCode());
    return prime * result + ((this.serverInfo== null) ? 0 : this.serverInfo.hashCode());
  }
}
