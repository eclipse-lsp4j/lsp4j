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

import com.google.common.annotations.Beta;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to the code lens requests scoped to the
 * workspace.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class CodeLensWorkspaceCapabilities {
  /**
   * Whether the client implementation supports a refresh request sent from the
   * server to the client.
   * 
   * Note that this event is global and will force the client to refresh all
   * code lenses currently shown. It should be used with absolute care and is
   * useful for situations where a server for example detects a project-wide
   * change that requires such a calculation.
   */
  private Boolean refreshSupport;
  
  public CodeLensWorkspaceCapabilities() {
  }
  
  public CodeLensWorkspaceCapabilities(final Boolean refreshSupport) {
    this.refreshSupport = refreshSupport;
  }
  
  /**
   * Whether the client implementation supports a refresh request sent from the
   * server to the client.
   * 
   * Note that this event is global and will force the client to refresh all
   * code lenses currently shown. It should be used with absolute care and is
   * useful for situations where a server for example detects a project-wide
   * change that requires such a calculation.
   */
  @Pure
  public Boolean getRefreshSupport() {
    return this.refreshSupport;
  }
  
  /**
   * Whether the client implementation supports a refresh request sent from the
   * server to the client.
   * 
   * Note that this event is global and will force the client to refresh all
   * code lenses currently shown. It should be used with absolute care and is
   * useful for situations where a server for example detects a project-wide
   * change that requires such a calculation.
   */
  public void setRefreshSupport(final Boolean refreshSupport) {
    this.refreshSupport = refreshSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("refreshSupport", this.refreshSupport);
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
    CodeLensWorkspaceCapabilities other = (CodeLensWorkspaceCapabilities) obj;
    if (this.refreshSupport == null) {
      if (other.refreshSupport != null)
        return false;
    } else if (!this.refreshSupport.equals(other.refreshSupport))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.refreshSupport== null) ? 0 : this.refreshSupport.hashCode());
  }
}
