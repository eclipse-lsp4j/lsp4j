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
 * Server supports providing semantic tokens for a full document.
 * 
 * Since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class SemanticTokensServerFull {
  /**
   * The server supports deltas for full documents.
   */
  private Boolean delta;
  
  public SemanticTokensServerFull() {
  }
  
  public SemanticTokensServerFull(final Boolean delta) {
    this.delta = delta;
  }
  
  /**
   * The server supports deltas for full documents.
   */
  @Pure
  public Boolean getDelta() {
    return this.delta;
  }
  
  /**
   * The server supports deltas for full documents.
   */
  public void setDelta(final Boolean delta) {
    this.delta = delta;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("delta", this.delta);
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
    SemanticTokensServerFull other = (SemanticTokensServerFull) obj;
    if (this.delta == null) {
      if (other.delta != null)
        return false;
    } else if (!this.delta.equals(other.delta))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.delta== null) ? 0 : this.delta.hashCode());
  }
}
