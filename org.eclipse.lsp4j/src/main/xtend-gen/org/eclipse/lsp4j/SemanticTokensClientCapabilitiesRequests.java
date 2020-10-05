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
import org.eclipse.lsp4j.SemanticTokensClientCapabilitiesRequestsFull;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * @since 3.16.0
 */
@Beta
@SuppressWarnings("all")
public class SemanticTokensClientCapabilitiesRequests {
  /**
   * The client will send the `textDocument/semanticTokens/range` request if
   * the server provides a corresponding handler.
   */
  private Either<Boolean, Object> range;
  
  /**
   * The client will send the `textDocument/semanticTokens/full` request if
   * the server provides a corresponding handler.
   */
  private Either<Boolean, SemanticTokensClientCapabilitiesRequestsFull> full;
  
  public SemanticTokensClientCapabilitiesRequests() {
  }
  
  public SemanticTokensClientCapabilitiesRequests(final Boolean full) {
    this.setFull(full);
  }
  
  public SemanticTokensClientCapabilitiesRequests(final SemanticTokensClientCapabilitiesRequestsFull full) {
    this.setFull(full);
  }
  
  public SemanticTokensClientCapabilitiesRequests(final Boolean full, final Boolean range) {
    this.setFull(full);
    this.setRange(range);
  }
  
  public SemanticTokensClientCapabilitiesRequests(final SemanticTokensClientCapabilitiesRequestsFull full, final Boolean range) {
    this.setFull(full);
    this.setRange(range);
  }
  
  /**
   * The client will send the `textDocument/semanticTokens/range` request if
   * the server provides a corresponding handler.
   */
  @Pure
  public Either<Boolean, Object> getRange() {
    return this.range;
  }
  
  /**
   * The client will send the `textDocument/semanticTokens/range` request if
   * the server provides a corresponding handler.
   */
  public void setRange(final Either<Boolean, Object> range) {
    this.range = range;
  }
  
  public void setRange(final Boolean range) {
    if (range == null) {
      this.range = null;
      return;
    }
    this.range = Either.forLeft(range);
  }
  
  public void setRange(final Object range) {
    if (range == null) {
      this.range = null;
      return;
    }
    this.range = Either.forRight(range);
  }
  
  /**
   * The client will send the `textDocument/semanticTokens/full` request if
   * the server provides a corresponding handler.
   */
  @Pure
  public Either<Boolean, SemanticTokensClientCapabilitiesRequestsFull> getFull() {
    return this.full;
  }
  
  /**
   * The client will send the `textDocument/semanticTokens/full` request if
   * the server provides a corresponding handler.
   */
  public void setFull(final Either<Boolean, SemanticTokensClientCapabilitiesRequestsFull> full) {
    this.full = full;
  }
  
  public void setFull(final Boolean full) {
    if (full == null) {
      this.full = null;
      return;
    }
    this.full = Either.forLeft(full);
  }
  
  public void setFull(final SemanticTokensClientCapabilitiesRequestsFull full) {
    if (full == null) {
      this.full = null;
      return;
    }
    this.full = Either.forRight(full);
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("range", this.range);
    b.add("full", this.full);
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
    SemanticTokensClientCapabilitiesRequests other = (SemanticTokensClientCapabilitiesRequests) obj;
    if (this.range == null) {
      if (other.range != null)
        return false;
    } else if (!this.range.equals(other.range))
      return false;
    if (this.full == null) {
      if (other.full != null)
        return false;
    } else if (!this.full.equals(other.full))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.range== null) ? 0 : this.range.hashCode());
    return prime * result + ((this.full== null) ? 0 : this.full.hashCode());
  }
}
