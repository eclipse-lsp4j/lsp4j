/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import org.eclipse.lsp4j.WorkspaceFoldersChangeEvent;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The workspace/didChangeWorkspaceFolders notification is sent from the client to the server to
 * inform the server about workspace folder configuration changes. The notification is sent by
 * default if both ServerCapabilities/workspace/workspaceFolders and
 * ClientCapabilities/workspace/workspaceFolders are true; or if the server has registered to
 * receive this notification it first.
 */
@SuppressWarnings("all")
public class DidChangeWorkspaceFoldersParams {
  /**
   * The actual workspace folder change event.
   */
  @NonNull
  private WorkspaceFoldersChangeEvent event;
  
  public DidChangeWorkspaceFoldersParams() {
  }
  
  public DidChangeWorkspaceFoldersParams(@NonNull final WorkspaceFoldersChangeEvent event) {
    this.event = event;
  }
  
  /**
   * The actual workspace folder change event.
   */
  @Pure
  @NonNull
  public WorkspaceFoldersChangeEvent getEvent() {
    return this.event;
  }
  
  /**
   * The actual workspace folder change event.
   */
  public void setEvent(@NonNull final WorkspaceFoldersChangeEvent event) {
    this.event = event;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("event", this.event);
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
    DidChangeWorkspaceFoldersParams other = (DidChangeWorkspaceFoldersParams) obj;
    if (this.event == null) {
      if (other.event != null)
        return false;
    } else if (!this.event.equals(other.event))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.event== null) ? 0 : this.event.hashCode());
    return result;
  }
}
