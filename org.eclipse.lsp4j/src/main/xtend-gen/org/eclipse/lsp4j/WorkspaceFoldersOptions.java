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

import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The server supports workspace folder.
 * 
 * Since 3.6.0
 */
@SuppressWarnings("all")
public class WorkspaceFoldersOptions {
  /**
   * The server has support for workspace folders
   */
  private Boolean supported;
  
  /**
   * Whether the server wants to receive workspace folder
   * change notifications.
   * 
   * If a string is provided, the string is treated as an ID
   * under which the notification is registered on the client
   * side. The ID can be used to unregister for these events
   * using the `client/unregisterCapability` request.
   */
  private Either<String, Boolean> changeNotifications;
  
  /**
   * The server has support for workspace folders
   */
  @Pure
  public Boolean getSupported() {
    return this.supported;
  }
  
  /**
   * The server has support for workspace folders
   */
  public void setSupported(final Boolean supported) {
    this.supported = supported;
  }
  
  /**
   * Whether the server wants to receive workspace folder
   * change notifications.
   * 
   * If a string is provided, the string is treated as an ID
   * under which the notification is registered on the client
   * side. The ID can be used to unregister for these events
   * using the `client/unregisterCapability` request.
   */
  @Pure
  public Either<String, Boolean> getChangeNotifications() {
    return this.changeNotifications;
  }
  
  /**
   * Whether the server wants to receive workspace folder
   * change notifications.
   * 
   * If a string is provided, the string is treated as an ID
   * under which the notification is registered on the client
   * side. The ID can be used to unregister for these events
   * using the `client/unregisterCapability` request.
   */
  public void setChangeNotifications(final Either<String, Boolean> changeNotifications) {
    this.changeNotifications = changeNotifications;
  }
  
  public void setChangeNotifications(final String changeNotifications) {
    if (changeNotifications == null) {
      this.changeNotifications = null;
      return;
    }
    this.changeNotifications = Either.forLeft(changeNotifications);
  }
  
  public void setChangeNotifications(final Boolean changeNotifications) {
    if (changeNotifications == null) {
      this.changeNotifications = null;
      return;
    }
    this.changeNotifications = Either.forRight(changeNotifications);
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("supported", this.supported);
    b.add("changeNotifications", this.changeNotifications);
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
    WorkspaceFoldersOptions other = (WorkspaceFoldersOptions) obj;
    if (this.supported == null) {
      if (other.supported != null)
        return false;
    } else if (!this.supported.equals(other.supported))
      return false;
    if (this.changeNotifications == null) {
      if (other.changeNotifications != null)
        return false;
    } else if (!this.changeNotifications.equals(other.changeNotifications))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.supported== null) ? 0 : this.supported.hashCode());
    return prime * result + ((this.changeNotifications== null) ? 0 : this.changeNotifications.hashCode());
  }
}
