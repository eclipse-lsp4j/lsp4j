/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import com.google.common.annotations.Beta;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Semantic highlighting server capabilities.
 */
@Beta
@SuppressWarnings("all")
public class SemanticHighlightingServerCapabilities {
  /**
   * A "lookup table" of semantic highlighting <a href="https://manual.macromates.com/en/language_grammars">TextMate scopes</a>
   * supported by the language server. If not defined or empty, then the server does not support the semantic highlighting
   * feature. Otherwise, clients should reuse this "lookup table" when receiving semantic highlighting notifications from
   * the server.
   */
  private List<List<String>> scopes;
  
  public SemanticHighlightingServerCapabilities() {
  }
  
  public SemanticHighlightingServerCapabilities(final List<List<String>> scopes) {
    this.scopes = scopes;
  }
  
  /**
   * A "lookup table" of semantic highlighting <a href="https://manual.macromates.com/en/language_grammars">TextMate scopes</a>
   * supported by the language server. If not defined or empty, then the server does not support the semantic highlighting
   * feature. Otherwise, clients should reuse this "lookup table" when receiving semantic highlighting notifications from
   * the server.
   */
  @Pure
  public List<List<String>> getScopes() {
    return this.scopes;
  }
  
  /**
   * A "lookup table" of semantic highlighting <a href="https://manual.macromates.com/en/language_grammars">TextMate scopes</a>
   * supported by the language server. If not defined or empty, then the server does not support the semantic highlighting
   * feature. Otherwise, clients should reuse this "lookup table" when receiving semantic highlighting notifications from
   * the server.
   */
  public void setScopes(final List<List<String>> scopes) {
    this.scopes = scopes;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("scopes", this.scopes);
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
    SemanticHighlightingServerCapabilities other = (SemanticHighlightingServerCapabilities) obj;
    if (this.scopes == null) {
      if (other.scopes != null)
        return false;
    } else if (!this.scopes.equals(other.scopes))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.scopes== null) ? 0 : this.scopes.hashCode());
  }
}
