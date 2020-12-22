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

import org.eclipse.lsp4j.DiagnosticsTagSupport;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Capabilities specific to `textDocument/publishDiagnostics`.
 */
@SuppressWarnings("all")
public class PublishDiagnosticsCapabilities {
  /**
   * Whether the client accepts diagnostics with related information.
   */
  private Boolean relatedInformation;
  
  /**
   * Client supports the tag property to provide meta data about a diagnostic.
   * Clients supporting tags have to handle unknown tags gracefully.
   * 
   * This property had been added and implemented as boolean before it was
   * added to the specification as {@link DiagnosticsTagSupport}. In order to
   * keep this implementation compatible with intermediate clients (including
   * vscode-language-client &lt; 6.0.0) we add an either type here.
   * 
   * Since 3.15
   */
  private Either<Boolean, DiagnosticsTagSupport> tagSupport;
  
  /**
   * Whether the client interprets the version property of the
   * `textDocument/publishDiagnostics` notification's parameter.
   * 
   * Since 3.15.0
   */
  private Boolean versionSupport;
  
  /**
   * Client supports a codeDescription property
   * 
   * Since 3.16.0
   */
  private Boolean codeDescriptionSupport;
  
  /**
   * Whether code action supports the `data` property which is
   * preserved between a `textDocument/publishDiagnostics` and
   * `textDocument/codeAction` request.
   * 
   * Since 3.16.0
   */
  private Boolean dataSupport;
  
  public PublishDiagnosticsCapabilities() {
  }
  
  public PublishDiagnosticsCapabilities(final Boolean relatedInformation) {
    this.relatedInformation = relatedInformation;
  }
  
  public PublishDiagnosticsCapabilities(final Boolean relatedInformation, final DiagnosticsTagSupport tagSupport) {
    this(relatedInformation);
    this.setTagSupport(tagSupport);
  }
  
  public PublishDiagnosticsCapabilities(final Boolean relatedInformation, final DiagnosticsTagSupport tagSupport, final Boolean versionSupport) {
    this(relatedInformation, tagSupport);
    this.versionSupport = versionSupport;
  }
  
  /**
   * Whether the client accepts diagnostics with related information.
   */
  @Pure
  public Boolean getRelatedInformation() {
    return this.relatedInformation;
  }
  
  /**
   * Whether the client accepts diagnostics with related information.
   */
  public void setRelatedInformation(final Boolean relatedInformation) {
    this.relatedInformation = relatedInformation;
  }
  
  /**
   * Client supports the tag property to provide meta data about a diagnostic.
   * Clients supporting tags have to handle unknown tags gracefully.
   * 
   * This property had been added and implemented as boolean before it was
   * added to the specification as {@link DiagnosticsTagSupport}. In order to
   * keep this implementation compatible with intermediate clients (including
   * vscode-language-client &lt; 6.0.0) we add an either type here.
   * 
   * Since 3.15
   */
  @Pure
  public Either<Boolean, DiagnosticsTagSupport> getTagSupport() {
    return this.tagSupport;
  }
  
  /**
   * Client supports the tag property to provide meta data about a diagnostic.
   * Clients supporting tags have to handle unknown tags gracefully.
   * 
   * This property had been added and implemented as boolean before it was
   * added to the specification as {@link DiagnosticsTagSupport}. In order to
   * keep this implementation compatible with intermediate clients (including
   * vscode-language-client &lt; 6.0.0) we add an either type here.
   * 
   * Since 3.15
   */
  public void setTagSupport(final Either<Boolean, DiagnosticsTagSupport> tagSupport) {
    this.tagSupport = tagSupport;
  }
  
  public void setTagSupport(final Boolean tagSupport) {
    if (tagSupport == null) {
      this.tagSupport = null;
      return;
    }
    this.tagSupport = Either.forLeft(tagSupport);
  }
  
  public void setTagSupport(final DiagnosticsTagSupport tagSupport) {
    if (tagSupport == null) {
      this.tagSupport = null;
      return;
    }
    this.tagSupport = Either.forRight(tagSupport);
  }
  
  /**
   * Whether the client interprets the version property of the
   * `textDocument/publishDiagnostics` notification's parameter.
   * 
   * Since 3.15.0
   */
  @Pure
  public Boolean getVersionSupport() {
    return this.versionSupport;
  }
  
  /**
   * Whether the client interprets the version property of the
   * `textDocument/publishDiagnostics` notification's parameter.
   * 
   * Since 3.15.0
   */
  public void setVersionSupport(final Boolean versionSupport) {
    this.versionSupport = versionSupport;
  }
  
  /**
   * Client supports a codeDescription property
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getCodeDescriptionSupport() {
    return this.codeDescriptionSupport;
  }
  
  /**
   * Client supports a codeDescription property
   * 
   * Since 3.16.0
   */
  public void setCodeDescriptionSupport(final Boolean codeDescriptionSupport) {
    this.codeDescriptionSupport = codeDescriptionSupport;
  }
  
  /**
   * Whether code action supports the `data` property which is
   * preserved between a `textDocument/publishDiagnostics` and
   * `textDocument/codeAction` request.
   * 
   * Since 3.16.0
   */
  @Pure
  public Boolean getDataSupport() {
    return this.dataSupport;
  }
  
  /**
   * Whether code action supports the `data` property which is
   * preserved between a `textDocument/publishDiagnostics` and
   * `textDocument/codeAction` request.
   * 
   * Since 3.16.0
   */
  public void setDataSupport(final Boolean dataSupport) {
    this.dataSupport = dataSupport;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("relatedInformation", this.relatedInformation);
    b.add("tagSupport", this.tagSupport);
    b.add("versionSupport", this.versionSupport);
    b.add("codeDescriptionSupport", this.codeDescriptionSupport);
    b.add("dataSupport", this.dataSupport);
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
    PublishDiagnosticsCapabilities other = (PublishDiagnosticsCapabilities) obj;
    if (this.relatedInformation == null) {
      if (other.relatedInformation != null)
        return false;
    } else if (!this.relatedInformation.equals(other.relatedInformation))
      return false;
    if (this.tagSupport == null) {
      if (other.tagSupport != null)
        return false;
    } else if (!this.tagSupport.equals(other.tagSupport))
      return false;
    if (this.versionSupport == null) {
      if (other.versionSupport != null)
        return false;
    } else if (!this.versionSupport.equals(other.versionSupport))
      return false;
    if (this.codeDescriptionSupport == null) {
      if (other.codeDescriptionSupport != null)
        return false;
    } else if (!this.codeDescriptionSupport.equals(other.codeDescriptionSupport))
      return false;
    if (this.dataSupport == null) {
      if (other.dataSupport != null)
        return false;
    } else if (!this.dataSupport.equals(other.dataSupport))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.relatedInformation== null) ? 0 : this.relatedInformation.hashCode());
    result = prime * result + ((this.tagSupport== null) ? 0 : this.tagSupport.hashCode());
    result = prime * result + ((this.versionSupport== null) ? 0 : this.versionSupport.hashCode());
    result = prime * result + ((this.codeDescriptionSupport== null) ? 0 : this.codeDescriptionSupport.hashCode());
    return prime * result + ((this.dataSupport== null) ? 0 : this.dataSupport.hashCode());
  }
}
