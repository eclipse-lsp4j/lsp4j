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

import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Whether the client in general supports change annotations on text edits,
 * create file, rename file and delete file changes.
 * 
 * Since 3.16.0
 */
@SuppressWarnings("all")
public class WorkspaceEditChangeAnnotationSupportCapabilities {
  /**
   * Whether the client groups edits with equal labels into tree nodes,
   * for instance all edits labelled with "Changes in Strings" would
   * be a tree node.
   */
  private Boolean groupsOnLabel;
  
  public WorkspaceEditChangeAnnotationSupportCapabilities() {
  }
  
  public WorkspaceEditChangeAnnotationSupportCapabilities(final Boolean groupsOnLabel) {
    this.groupsOnLabel = groupsOnLabel;
  }
  
  /**
   * Whether the client groups edits with equal labels into tree nodes,
   * for instance all edits labelled with "Changes in Strings" would
   * be a tree node.
   */
  @Pure
  public Boolean getGroupsOnLabel() {
    return this.groupsOnLabel;
  }
  
  /**
   * Whether the client groups edits with equal labels into tree nodes,
   * for instance all edits labelled with "Changes in Strings" would
   * be a tree node.
   */
  public void setGroupsOnLabel(final Boolean groupsOnLabel) {
    this.groupsOnLabel = groupsOnLabel;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("groupsOnLabel", this.groupsOnLabel);
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
    WorkspaceEditChangeAnnotationSupportCapabilities other = (WorkspaceEditChangeAnnotationSupportCapabilities) obj;
    if (this.groupsOnLabel == null) {
      if (other.groupsOnLabel != null)
        return false;
    } else if (!this.groupsOnLabel.equals(other.groupsOnLabel))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.groupsOnLabel== null) ? 0 : this.groupsOnLabel.hashCode());
  }
}
