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
import java.util.List;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;
import org.eclipse.lsp4j.util.Preconditions;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * The legend used by the server
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class SemanticTokensLegend {
  /**
   * The token types that the client supports.
   */
  @NonNull
  private List<String> tokenTypes;
  
  /**
   * The token modifiers that the client supports.
   */
  @NonNull
  private List<String> tokenModifiers;
  
  public SemanticTokensLegend(@NonNull final List<String> tokenTypes, @NonNull final List<String> tokenModifiers) {
    this.tokenTypes = Preconditions.<List<String>>checkNotNull(tokenTypes, "tokenTypes");
    this.tokenModifiers = Preconditions.<List<String>>checkNotNull(tokenModifiers, "tokenModifiers");
  }
  
  /**
   * The token types that the client supports.
   */
  @Pure
  @NonNull
  public List<String> getTokenTypes() {
    return this.tokenTypes;
  }
  
  /**
   * The token types that the client supports.
   */
  public void setTokenTypes(@NonNull final List<String> tokenTypes) {
    this.tokenTypes = Preconditions.checkNotNull(tokenTypes, "tokenTypes");
  }
  
  /**
   * The token modifiers that the client supports.
   */
  @Pure
  @NonNull
  public List<String> getTokenModifiers() {
    return this.tokenModifiers;
  }
  
  /**
   * The token modifiers that the client supports.
   */
  public void setTokenModifiers(@NonNull final List<String> tokenModifiers) {
    this.tokenModifiers = Preconditions.checkNotNull(tokenModifiers, "tokenModifiers");
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("tokenTypes", this.tokenTypes);
    b.add("tokenModifiers", this.tokenModifiers);
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
    SemanticTokensLegend other = (SemanticTokensLegend) obj;
    if (this.tokenTypes == null) {
      if (other.tokenTypes != null)
        return false;
    } else if (!this.tokenTypes.equals(other.tokenTypes))
      return false;
    if (this.tokenModifiers == null) {
      if (other.tokenModifiers != null)
        return false;
    } else if (!this.tokenModifiers.equals(other.tokenModifiers))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.tokenTypes== null) ? 0 : this.tokenTypes.hashCode());
    return prime * result + ((this.tokenModifiers== null) ? 0 : this.tokenModifiers.hashCode());
  }
}
