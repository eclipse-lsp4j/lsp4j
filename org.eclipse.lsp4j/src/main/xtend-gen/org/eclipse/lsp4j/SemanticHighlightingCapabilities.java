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
 * Capabilities specific to {@code textDocument/semanticHighlighting}.
 * 
 * <p>
 * <b>Note:</b> the <a href=
 * "https://github.com/Microsoft/vscode-languageserver-node/pull/367">{@code textDocument/semanticHighlighting}
 * language feature</a> is not yet part of the official LSP specification.
 */
@Beta
@SuppressWarnings("all")
public class SemanticHighlightingCapabilities {
  /**
   * The client supports semantic highlighting.
   */
  private Boolean semanticHighlighting;
  
  public SemanticHighlightingCapabilities() {
  }
  
  public SemanticHighlightingCapabilities(final Boolean semanticHighlighting) {
    this.semanticHighlighting = semanticHighlighting;
  }
  
  /**
   * The client supports semantic highlighting.
   */
  @Pure
  public Boolean getSemanticHighlighting() {
    return this.semanticHighlighting;
  }
  
  /**
   * The client supports semantic highlighting.
   */
  public void setSemanticHighlighting(final Boolean semanticHighlighting) {
    this.semanticHighlighting = semanticHighlighting;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("semanticHighlighting", this.semanticHighlighting);
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
    SemanticHighlightingCapabilities other = (SemanticHighlightingCapabilities) obj;
    if (this.semanticHighlighting == null) {
      if (other.semanticHighlighting != null)
        return false;
    } else if (!this.semanticHighlighting.equals(other.semanticHighlighting))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.semanticHighlighting== null) ? 0 : this.semanticHighlighting.hashCode());
  }
}
