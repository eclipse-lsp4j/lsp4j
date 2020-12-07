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
import org.eclipse.lsp4j.ShowDocumentCapabilities;
import org.eclipse.lsp4j.WindowShowMessageRequestCapabilities;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Window specific client capabilities.
 */
@SuppressWarnings("all")
public class WindowClientCapabilities {
  /**
   * Whether client supports handling progress notifications. If set servers are allowed to
   * report in `workDoneProgress` property in the request specific server capabilities.
   * 
   * Since 3.15.0
   */
  private Boolean workDoneProgress;
  
  /**
   * Capabilities specific to the showMessage request
   * 
   * Since 3.16.0
   */
  @Beta
  private WindowShowMessageRequestCapabilities showMessage;
  
  /**
   * Client capabilities for the show document request.
   * 
   * Since 3.16.0
   */
  @Beta
  private ShowDocumentCapabilities showDocument;
  
  /**
   * Whether client supports handling progress notifications. If set servers are allowed to
   * report in `workDoneProgress` property in the request specific server capabilities.
   * 
   * Since 3.15.0
   */
  @Pure
  public Boolean getWorkDoneProgress() {
    return this.workDoneProgress;
  }
  
  /**
   * Whether client supports handling progress notifications. If set servers are allowed to
   * report in `workDoneProgress` property in the request specific server capabilities.
   * 
   * Since 3.15.0
   */
  public void setWorkDoneProgress(final Boolean workDoneProgress) {
    this.workDoneProgress = workDoneProgress;
  }
  
  /**
   * Capabilities specific to the showMessage request
   * 
   * Since 3.16.0
   */
  @Pure
  public WindowShowMessageRequestCapabilities getShowMessage() {
    return this.showMessage;
  }
  
  /**
   * Capabilities specific to the showMessage request
   * 
   * Since 3.16.0
   */
  public void setShowMessage(final WindowShowMessageRequestCapabilities showMessage) {
    this.showMessage = showMessage;
  }
  
  /**
   * Client capabilities for the show document request.
   * 
   * Since 3.16.0
   */
  @Pure
  public ShowDocumentCapabilities getShowDocument() {
    return this.showDocument;
  }
  
  /**
   * Client capabilities for the show document request.
   * 
   * Since 3.16.0
   */
  public void setShowDocument(final ShowDocumentCapabilities showDocument) {
    this.showDocument = showDocument;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workDoneProgress", this.workDoneProgress);
    b.add("showMessage", this.showMessage);
    b.add("showDocument", this.showDocument);
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
    WindowClientCapabilities other = (WindowClientCapabilities) obj;
    if (this.workDoneProgress == null) {
      if (other.workDoneProgress != null)
        return false;
    } else if (!this.workDoneProgress.equals(other.workDoneProgress))
      return false;
    if (this.showMessage == null) {
      if (other.showMessage != null)
        return false;
    } else if (!this.showMessage.equals(other.showMessage))
      return false;
    if (this.showDocument == null) {
      if (other.showDocument != null)
        return false;
    } else if (!this.showDocument.equals(other.showDocument))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.workDoneProgress== null) ? 0 : this.workDoneProgress.hashCode());
    result = prime * result + ((this.showMessage== null) ? 0 : this.showMessage.hashCode());
    return prime * result + ((this.showDocument== null) ? 0 : this.showDocument.hashCode());
  }
}
