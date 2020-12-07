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
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Params to show a document.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class ShowDocumentParams {
  /**
   * The document uri to show.
   */
  @NonNull
  private String uri;
  
  /**
   * Indicates to show the resource in an external program.
   * To show for example <a href="https://www.eclipse.org/">
   * https://www.eclipse.org/</a>
   * in the default WEB browser set to {@code true}.
   */
  private Boolean external;
  
  /**
   * An optional property to indicate whether the editor
   * showing the document should take focus or not.
   * Clients might ignore this property if an external
   * program is started.
   */
  private Boolean takeFocus;
  
  /**
   * An optional selection range if the document is a text
   * document. Clients might ignore the property if an
   * external program is started or the file is not a text
   * file.
   */
  private Range selection;
  
  public ShowDocumentParams() {
  }
  
  public ShowDocumentParams(@NonNull final String uri) {
    this.uri = Preconditions.<String>checkNotNull(uri, "uri");
  }
  
  /**
   * The document uri to show.
   */
  @Pure
  @NonNull
  public String getUri() {
    return this.uri;
  }
  
  /**
   * The document uri to show.
   */
  public void setUri(@NonNull final String uri) {
    this.uri = Preconditions.checkNotNull(uri, "uri");
  }
  
  /**
   * Indicates to show the resource in an external program.
   * To show for example <a href="https://www.eclipse.org/">
   * https://www.eclipse.org/</a>
   * in the default WEB browser set to {@code true}.
   */
  @Pure
  public Boolean getExternal() {
    return this.external;
  }
  
  /**
   * Indicates to show the resource in an external program.
   * To show for example <a href="https://www.eclipse.org/">
   * https://www.eclipse.org/</a>
   * in the default WEB browser set to {@code true}.
   */
  public void setExternal(final Boolean external) {
    this.external = external;
  }
  
  /**
   * An optional property to indicate whether the editor
   * showing the document should take focus or not.
   * Clients might ignore this property if an external
   * program is started.
   */
  @Pure
  public Boolean getTakeFocus() {
    return this.takeFocus;
  }
  
  /**
   * An optional property to indicate whether the editor
   * showing the document should take focus or not.
   * Clients might ignore this property if an external
   * program is started.
   */
  public void setTakeFocus(final Boolean takeFocus) {
    this.takeFocus = takeFocus;
  }
  
  /**
   * An optional selection range if the document is a text
   * document. Clients might ignore the property if an
   * external program is started or the file is not a text
   * file.
   */
  @Pure
  public Range getSelection() {
    return this.selection;
  }
  
  /**
   * An optional selection range if the document is a text
   * document. Clients might ignore the property if an
   * external program is started or the file is not a text
   * file.
   */
  public void setSelection(final Range selection) {
    this.selection = selection;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("uri", this.uri);
    b.add("external", this.external);
    b.add("takeFocus", this.takeFocus);
    b.add("selection", this.selection);
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
    ShowDocumentParams other = (ShowDocumentParams) obj;
    if (this.uri == null) {
      if (other.uri != null)
        return false;
    } else if (!this.uri.equals(other.uri))
      return false;
    if (this.external == null) {
      if (other.external != null)
        return false;
    } else if (!this.external.equals(other.external))
      return false;
    if (this.takeFocus == null) {
      if (other.takeFocus != null)
        return false;
    } else if (!this.takeFocus.equals(other.takeFocus))
      return false;
    if (this.selection == null) {
      if (other.selection != null)
        return false;
    } else if (!this.selection.equals(other.selection))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.uri== null) ? 0 : this.uri.hashCode());
    result = prime * result + ((this.external== null) ? 0 : this.external.hashCode());
    result = prime * result + ((this.takeFocus== null) ? 0 : this.takeFocus.hashCode());
    return prime * result + ((this.selection== null) ? 0 : this.selection.hashCode());
  }
}
