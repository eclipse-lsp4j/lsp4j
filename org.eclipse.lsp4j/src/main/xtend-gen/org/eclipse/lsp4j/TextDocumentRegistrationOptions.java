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

import java.util.List;
import org.eclipse.lsp4j.DocumentFilter;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Since most of the registration options require to specify a document selector there is
 * a base interface that can be used.
 */
@SuppressWarnings("all")
public class TextDocumentRegistrationOptions {
  /**
   * A document selector to identify the scope of the registration. If set to null
   * the document selector provided on the client side will be used.
   */
  private List<DocumentFilter> documentSelector;
  
  public TextDocumentRegistrationOptions() {
  }
  
  public TextDocumentRegistrationOptions(final List<DocumentFilter> documentSelector) {
    this.documentSelector = documentSelector;
  }
  
  /**
   * A document selector to identify the scope of the registration. If set to null
   * the document selector provided on the client side will be used.
   */
  @Pure
  public List<DocumentFilter> getDocumentSelector() {
    return this.documentSelector;
  }
  
  /**
   * A document selector to identify the scope of the registration. If set to null
   * the document selector provided on the client side will be used.
   */
  public void setDocumentSelector(final List<DocumentFilter> documentSelector) {
    this.documentSelector = documentSelector;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("documentSelector", this.documentSelector);
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
    TextDocumentRegistrationOptions other = (TextDocumentRegistrationOptions) obj;
    if (this.documentSelector == null) {
      if (other.documentSelector != null)
        return false;
    } else if (!this.documentSelector.equals(other.documentSelector))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.documentSelector== null) ? 0 : this.documentSelector.hashCode());
  }
}
