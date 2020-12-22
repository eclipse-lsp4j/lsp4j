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

import org.eclipse.lsp4j.WindowShowMessageRequestActionItemCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Show message request client capabilities
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class WindowShowMessageRequestCapabilities {
  /**
   * Capabilities specific to the `MessageActionItem` type.
   */
  private WindowShowMessageRequestActionItemCapabilities messageActionItem;
  
  public WindowShowMessageRequestCapabilities() {
  }
  
  /**
   * Capabilities specific to the `MessageActionItem` type.
   */
  @Pure
  public WindowShowMessageRequestActionItemCapabilities getMessageActionItem() {
    return this.messageActionItem;
  }
  
  /**
   * Capabilities specific to the `MessageActionItem` type.
   */
  public void setMessageActionItem(final WindowShowMessageRequestActionItemCapabilities messageActionItem) {
    this.messageActionItem = messageActionItem;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("messageActionItem", this.messageActionItem);
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
    WindowShowMessageRequestCapabilities other = (WindowShowMessageRequestCapabilities) obj;
    if (this.messageActionItem == null) {
      if (other.messageActionItem != null)
        return false;
    } else if (!this.messageActionItem.equals(other.messageActionItem))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.messageActionItem== null) ? 0 : this.messageActionItem.hashCode());
  }
}
