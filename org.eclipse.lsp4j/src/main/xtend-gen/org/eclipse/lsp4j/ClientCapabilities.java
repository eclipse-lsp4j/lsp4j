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

import com.google.gson.annotations.JsonAdapter;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.WindowClientCapabilities;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.lsp4j.jsonrpc.json.adapters.JsonElementTypeAdapter;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * `ClientCapabilities` now define capabilities for dynamic registration, workspace and text document features the client supports.
 * The `experimental` can be used to pass experimental capabilities under development.
 * For future compatibility a `ClientCapabilities` object literal can have more properties set than currently defined.
 * Servers receiving a `ClientCapabilities` object literal with unknown properties should ignore these properties.
 * A missing property should be interpreted as an absence of the capability.
 * If a property is missing that defines sub properties all sub properties should be interpreted as an absence of the capability.
 * 
 * Client capabilities got introduced with the version 3.0 of the protocol. They therefore only describe capabilities that got introduced in 3.x or later.
 * Capabilities that existed in the 2.x version of the protocol are still mandatory for clients. Clients cannot opt out of providing them.
 * So even if a client omits the `ClientCapabilities.textDocument.synchronization`
 * it is still required that the client provides text document synchronization (e.g. open, changed and close notifications).
 */
@SuppressWarnings("all")
public class ClientCapabilities {
  /**
   * Workspace specific client capabilities.
   */
  private WorkspaceClientCapabilities workspace;
  
  /**
   * Text document specific client capabilities.
   */
  private TextDocumentClientCapabilities textDocument;
  
  /**
   * Window specific client capabilities.
   */
  private WindowClientCapabilities window;
  
  /**
   * Experimental client capabilities.
   */
  @JsonAdapter(JsonElementTypeAdapter.Factory.class)
  private Object experimental;
  
  public ClientCapabilities() {
  }
  
  public ClientCapabilities(final WorkspaceClientCapabilities workspace, final TextDocumentClientCapabilities textDocument, final Object experimental) {
    this.workspace = workspace;
    this.textDocument = textDocument;
    this.experimental = experimental;
  }
  
  public ClientCapabilities(final WorkspaceClientCapabilities workspace, final TextDocumentClientCapabilities textDocument, final WindowClientCapabilities window, final Object experimental) {
    this.workspace = workspace;
    this.textDocument = textDocument;
    this.window = window;
    this.experimental = experimental;
  }
  
  /**
   * Workspace specific client capabilities.
   */
  @Pure
  public WorkspaceClientCapabilities getWorkspace() {
    return this.workspace;
  }
  
  /**
   * Workspace specific client capabilities.
   */
  public void setWorkspace(final WorkspaceClientCapabilities workspace) {
    this.workspace = workspace;
  }
  
  /**
   * Text document specific client capabilities.
   */
  @Pure
  public TextDocumentClientCapabilities getTextDocument() {
    return this.textDocument;
  }
  
  /**
   * Text document specific client capabilities.
   */
  public void setTextDocument(final TextDocumentClientCapabilities textDocument) {
    this.textDocument = textDocument;
  }
  
  /**
   * Window specific client capabilities.
   */
  @Pure
  public WindowClientCapabilities getWindow() {
    return this.window;
  }
  
  /**
   * Window specific client capabilities.
   */
  public void setWindow(final WindowClientCapabilities window) {
    this.window = window;
  }
  
  /**
   * Experimental client capabilities.
   */
  @Pure
  public Object getExperimental() {
    return this.experimental;
  }
  
  /**
   * Experimental client capabilities.
   */
  public void setExperimental(final Object experimental) {
    this.experimental = experimental;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("workspace", this.workspace);
    b.add("textDocument", this.textDocument);
    b.add("window", this.window);
    b.add("experimental", this.experimental);
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
    ClientCapabilities other = (ClientCapabilities) obj;
    if (this.workspace == null) {
      if (other.workspace != null)
        return false;
    } else if (!this.workspace.equals(other.workspace))
      return false;
    if (this.textDocument == null) {
      if (other.textDocument != null)
        return false;
    } else if (!this.textDocument.equals(other.textDocument))
      return false;
    if (this.window == null) {
      if (other.window != null)
        return false;
    } else if (!this.window.equals(other.window))
      return false;
    if (this.experimental == null) {
      if (other.experimental != null)
        return false;
    } else if (!this.experimental.equals(other.experimental))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.workspace== null) ? 0 : this.workspace.hashCode());
    result = prime * result + ((this.textDocument== null) ? 0 : this.textDocument.hashCode());
    result = prime * result + ((this.window== null) ? 0 : this.window.hashCode());
    return prime * result + ((this.experimental== null) ? 0 : this.experimental.hashCode());
  }
}
